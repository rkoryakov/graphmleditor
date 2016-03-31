/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.itsc.graphmleditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.itsc.graphmleditor.pane.GraphMLPane;

/**
 *
 * @author koryakov.rv
 */
public class GraphMLEditor extends Application {
    private static final String FXML_RESOURCE = "graphmlEditor.fxml";
    private static final String CSS_RESOURCE = "graphMlPane.css";
    private static GraphMLEditorController controller;
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_RESOURCE));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setStage(stage);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(GraphMLPane.class.getResource(CSS_RESOURCE).toString());
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static GraphMLEditorController getController() {
        return controller;
    }
}
