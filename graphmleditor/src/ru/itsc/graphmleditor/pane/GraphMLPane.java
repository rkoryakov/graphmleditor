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
        this.getChildren().add(rootModelGroup);
        
        this.setOnScroll((ScrollEvent event) -> {
            event.consume();
            
            if (event.getDeltaY() == 0) {
                return;
            }
            
            double contentWidth = rootModelGroup.getBoundsInLocal().getWidth();
            double viewWidth = this.getBoundsInLocal().getWidth();
            double d  = (rootModelGroup.getTranslateX() - event.getSceneX());
            
            double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA
                    : 1 / SCALE_DELTA;
            
            // amount of scrolling in each direction in scrollContent coordinate
            // units
            //Point2D scrollOffset = figureScrollOffset(scrollContent, scroller);
            
            rootModelGroup.setScaleX(rootModelGroup.getScaleX() * scaleFactor);
            rootModelGroup.setScaleY(rootModelGroup.getScaleY() * scaleFactor);
            //rootModelGroup.setTranslateX(rootModelGroup.getTranslateX() - (d*scaleFactor - d)/2);
            // move viewport so that old center remains in the center after the
            // scaling
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
        double contentW = rootModelGroup.getBoundsInLocal().getWidth();
        double contentH = rootModelGroup.getBoundsInLocal().getHeight();
        
        double cx = this.getBoundsInLocal().getWidth() / 2;
        double cy = this.getBoundsInLocal().getHeight() / 2;
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
        rootModelGroup.setScaleX(1.0);
        rootModelGroup.setScaleY(1.0);
    }
    
    public void initContent() {
        refreshScale();
        rootModelGroup.getChildren().clear();
        
    }
}
