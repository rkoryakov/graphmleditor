/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.itsc.graphmleditor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.itsc.commons.message.MessageBox;
import ru.itsc.graphml.Graph;
import ru.itsc.graphml.GraphMLParser;
import ru.itsc.graphmleditor.config.PropertiesManager;
import ru.itsc.graphmleditor.config.SettingsDialog;
import ru.itsc.graphmleditor.data.services.LoadGraphmlPlannedService;
import ru.itsc.graphmleditor.data.services.LoadGraphmlPlannedService.PlannedMetamapOperation;
import ru.itsc.graphmleditor.data.services.LoadGraphmlService;
import ru.itsc.graphmleditor.data.services.SaveGraphmlFileService;
import ru.itsc.graphmleditor.pane.GraphMLPane;

/**
 *
 * @author koryakov.rv
 */
public class GraphMLEditorController implements Initializable {
    
    @FXML private Button loadBtn;
    @FXML private GraphMLPane graphMLPane;
    @FXML private StackPane modalDimmer;
    @FXML private StackPane taskDimmer;
    @FXML private ProgressIndicator progress;
    @FXML private Label statusLabel;
    
    private Stage stage;
    private final FileChooser fileChooser = new FileChooser();
    private static final Logger logger = Logger.getLogger(GraphMLEditorController.class.toString());
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    @FXML
    private void handleActionOpen(ActionEvent event) {
        fileChooser.setTitle("Select file");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("GraphML file", "*.graphml"));
        
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                BufferedInputStream is = new BufferedInputStream(new FileInputStream(file));
                byte buf[] = new byte[is.available()];
                is.read(buf);
                String text = new String(buf, "utf-8");
                GraphMLParser parser = new GraphMLParser();
                Graph graph = parser.parse(text);
                graphMLPane.showGraphML(graph);
            } catch(IOException | ParserConfigurationException | SAXException e) {
               logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
    
	@FXML
	private void handleActionSave(ActionEvent event) {
		fileChooser.setTitle("Save");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("GraphML file", "*.graphml"));

		File file = fileChooser.showSaveDialog(stage);
		if (file != null) {
			try {
				SaveGraphmlFileService graphmlService = new SaveGraphmlFileService(file, graphMLPane.getRootGroup());
				statusLabel.textProperty().bind(graphmlService.messageProperty());
				taskDimmer.visibleProperty().bind(graphmlService.runningProperty());
				progress.visibleProperty().bind(graphmlService.runningProperty());

				graphmlService.setOnSucceeded((WorkerStateEvent wse) -> {
					try {
						GraphMLParser parser = new GraphMLParser();
						graphMLPane.showGraphML(parser.parse(wse.getSource().getValue().toString()));
						statusLabel.textProperty().unbind();
						statusLabel.setText("");
					} catch (ParserConfigurationException | SAXException | IOException ex) {
						logger.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
					}
				});
				graphmlService.setOnFailed((WorkerStateEvent wse) -> {
					MessageBox.show(stage, wse.getSource().getException().getLocalizedMessage(), "Error",
							MessageBox.ICON_ERROR | MessageBox.OK);
					logger.log(Level.SEVERE, wse.getSource().getException().getLocalizedMessage(),
							wse.getSource().getException());
					statusLabel.textProperty().unbind();
					statusLabel.setText(wse.getSource().getException().getLocalizedMessage());
				});
				graphmlService.start();
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}
    
    @FXML
    private void handleActionLoad(ActionEvent event) {
        
        try {
     
//            LoadGraphmlService graphmlService = new LoadGraphmlService();
//            graphmlService.setId(PropertiesManager.getPropertyValue(PropertiesManager.METAMAP_ID));
//            graphmlService.setName(PropertiesManager.getPropertyValue(PropertiesManager.METAMAP_NAME));
//            graphmlService.setHost(PropertiesManager.getPropertyValue(PropertiesManager.SERVER_HOST));
            
            LoadGraphmlPlannedService graphmlService = new LoadGraphmlPlannedService();
            graphmlService.setHost(new URI(PropertiesManager.getPropertyValue(PropertiesManager.SERVER_HOST)));
            graphmlService.setOperation(PlannedMetamapOperation.LAST_MAP);
            statusLabel.textProperty().bind(graphmlService.messageProperty());
            taskDimmer.visibleProperty().bind(graphmlService.runningProperty());
            progress.visibleProperty().bind(graphmlService.runningProperty());
            
            graphmlService.setOnSucceeded((WorkerStateEvent wse) -> {
                graphMLPane.showGraphML((Graph)wse.getSource().getValue());
                statusLabel.textProperty().unbind();
                statusLabel.setText("");
            });
            graphmlService.setOnFailed((WorkerStateEvent wse) -> {
                MessageBox.show(stage, wse.getSource().getException().getLocalizedMessage(), "Error", MessageBox.ICON_ERROR | MessageBox.OK);
                logger.log(Level.SEVERE, wse.getSource().getException().getLocalizedMessage(), wse.getSource().getException());
                statusLabel.textProperty().unbind();
                statusLabel.setText(wse.getSource().getException().getLocalizedMessage());
            });
            graphmlService.start();

        } catch (Exception e) {
            MessageBox.show(stage, e.getLocalizedMessage(), "Error", MessageBox.ICON_ERROR | MessageBox.OK);
            logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
            statusLabel.textProperty().unbind();
            statusLabel.setText("Error: " + e.getLocalizedMessage());
        }
    }
    
    @FXML
    private void handleActionSettings(ActionEvent event) {
        try {
            SettingsDialog sd = new SettingsDialog(stage);
            sd.loadSettings();
            showModalMessage(sd);
        } catch (Exception ex) {
            Logger.getLogger(GraphMLEditorController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initModalDimmer();
        initTaskDimmer();    
    }
    
    
    /**
     * Hide any modal message that is shown
     */
    public void hideModalMessage() {
        modalDimmer.setCache(true);
        new Timeline(
                new KeyFrame(Duration.seconds(1), 
                    (event) -> {
                            modalDimmer.setCache(false);
                            modalDimmer.setVisible(false);
                            modalDimmer.getChildren().clear();
                    },
                    new KeyValue(modalDimmer.opacityProperty(),0, Interpolator.EASE_BOTH)
                )
        ).play();
    }
    
    /**
     * Init modal dimmer, to dim screen when showing modal dialogs
     */
    public void initModalDimmer() {
       
        modalDimmer.setOnMouseClicked((mouseEvent) -> {          
                mouseEvent.consume();
                hideModalMessage();   
        });
        modalDimmer.setVisible(false);
    }
    
    public void initTaskDimmer() {
        taskDimmer.setVisible(false);
    }
    
    /**
     * Show the given node as a floating dialog over the whole application, with 
     * the rest of the application dimmed out and blocked from mouse events.
     * 
     * @param message 
     */
    public void showModalMessage(Node message) {
        modalDimmer.getChildren().add(message);
        modalDimmer.setOpacity(0);
        modalDimmer.setVisible(true);
        modalDimmer.setCache(true);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), (ActionEvent t) -> {
                        modalDimmer.setCache(false);
                    },
                new KeyValue(modalDimmer.opacityProperty(), 1, Interpolator.EASE_BOTH)
        ));
        timeline.play();
    }

    public GraphMLPane getGraphMLPane() {
        return graphMLPane;
    }
}

