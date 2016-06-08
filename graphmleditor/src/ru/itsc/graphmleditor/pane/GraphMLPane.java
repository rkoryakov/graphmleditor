/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.itsc.graphmleditor.pane;

import ru.itsc.graphmleditor.layout.LayoutGraph;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import ru.itsc.graphml.Graph;
import ru.itsc.graphmleditor.ModelGroup;

/**
 *
 * @author koryakov.rv
 */
public class GraphMLPane extends Pane implements Initializable {
    private static final String FXML_RESOURCE = "graphmlpane.fxml";
    public static final String CSS_COLLAPSE_BUTTON = "collapse-button";
    public static final String CSS_GROUP_NODE_HEADER = "group-node-header";
    public static final String CSS_GROUP_NODE_HEADER_LABEL = "group-node-header-label";
    public static final String CSS_GROUP_NODE_CONTENET = "group-node-content";
    public static final String CSS_CHILD_NODE_CONTENT = "child-node-content";
    public static final String CSS_CHILD_NODE_LABEL = "child-node-label";
    public static final String CSS_MODAL_DIMMER = "modalDimmer";
    
    public static final double SCALE_DELTA = 1.1;
    
    private Group rootModelGroup = new Group();
    private final StackPane scalingPane = new StackPane();
    private final ScrollPane scrollPane = new ScrollPane();
    private final Pane pane = new Pane();
    
    public GraphMLPane() {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource(FXML_RESOURCE));
            fXMLLoader.setRoot(this);
            fXMLLoader.setController(this);
            fXMLLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override 
    public void initialize(URL location, ResourceBundle resources) {  
    	
    	pane.getChildren().add(rootModelGroup);
        this.getChildren().add(pane);

        this.setOnScroll((ScrollEvent event) -> {
            
        	event.consume();
            
        	double scaleFactor = 0;
            double scaleDelta = 0.1;
            if (event.getDeltaY() < 0) {
            	scaleFactor = 1 / SCALE_DELTA;
            	scaleDelta = -scaleDelta;
            } else {
            	scaleFactor = SCALE_DELTA;
            }

            pane.setScaleX(pane.getScaleX()*scaleFactor);
            pane.setScaleY(pane.getScaleY()*scaleFactor);
           
            double paneWidth = pane.getBoundsInLocal().getWidth()*pane.getScaleX();
            double shiftX = (event.getX() - pane.getTranslateX())*scaleDelta;
            double shiftY = (event.getY() - pane.getTranslateY())*scaleDelta;

            pane.setTranslateX( pane.getTranslateX() - shiftX);
            pane.setTranslateY( pane.getTranslateY() - shiftY);

            //System.out.println("getTranslateX = " + pane.getTranslateX() + " event.getX = " + event.getX() + " event.getSceneX = " + event.getSceneX() + " event.getScreenX = " + event.getScreenX() + " pane.getWidth = " + pane.getBoundsInLocal().getWidth() + " pane scale width = " + pane.getBoundsInLocal().getWidth()*pane.getScaleX());
        });
    }

    @Override
    protected void layoutChildren() {
        scrollPane.setPrefViewportHeight(this.getHeight());
        scrollPane.setPrefViewportWidth(this.getWidth());
    }
    
    public Group getRootGroup() {
        return this.rootModelGroup;
    }
    
    public void showGraphML(Graph graph) {
        initContent();
        LayoutGraph.retrieveGraph(rootModelGroup, graph);
        toCenterContent();
    }
    
    private void toCenterContent() {
    	this.pane.setTranslateX(0.0);
    	this.pane.setTranslateY(0.0);
        double contentW = rootModelGroup.getBoundsInLocal().getWidth();
        double contentH = rootModelGroup.getBoundsInLocal().getHeight();
        
        double cx = this.getBoundsInParent().getWidth() / 2;
        double cy = this.getBoundsInParent().getHeight() / 2;
        rootModelGroup.setTranslateX(cx - contentW / 2);
        rootModelGroup.setTranslateY(cy - contentH / 2);
    }
    
    public void autoScale() {
  
        double dh = this.getBoundsInLocal().getHeight() / 
                rootModelGroup.getBoundsInLocal().getHeight();
        rootModelGroup.setScaleX(dh);
        rootModelGroup.setScaleY(dh);
        
        toCenterContent();
    }
    
    public void refreshScale() {
        pane.setScaleX(1.0);
        pane.setScaleY(1.0);
    }
    
    public void initContent() {
        refreshScale();
        rootModelGroup.getChildren().clear();
        this.pane.getChildren().clear();
        rootModelGroup = new Group();
        this.pane.getChildren().add(rootModelGroup);
        //rootModelGroup.
    }
}
