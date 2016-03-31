
package ru.itsc.graphmleditor.layout;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import ru.itsc.graphml.Graph;

/**
 * AbstractNode class represents common attributes and methods of layout objects 
 * @author RVKoryakov 11.10.15 0:16
 */
public abstract class AbstractNode extends Group {
    protected static final int MIN_HEIGHT = 20;
    protected static final int MIN_WIDTH = 20;
    protected Graph.GraphNode graphNode; // link to GrapnML node
    public BooleanProperty dragModeProperty = new SimpleBooleanProperty(true);
    // width & height of content area
    public DoubleProperty widthProperty = new SimpleDoubleProperty();
    public DoubleProperty heightProperty = new SimpleDoubleProperty();
    
    protected class DragContext {
        private double deltaX;
        private double deltaY;
    }
    
    public AbstractNode() {
        this.graphNode = null;
        this.getChildren().addAll(createNode());
    }
     
    public AbstractNode(Graph.GraphNode graphNode) {
        this.graphNode = graphNode;
        this.getChildren().addAll(createNode());
    }
    
    /**
     * Implement this abstract method to create target node.
     * <p>The implementation has to return a node list that will constitute graphical 
     * representation of this node. This method will be called from AbstractNode() constructor 
     * @return list of nodes
     **/
    protected abstract ObservableList<Node> createNode();
    
    /**
     * Implement this abstract method to realize some preparations before
     * this node will be moved during Drag&Dropp operation. 
     * It may changes visibility for example.
     */
    protected abstract void onPressed();
    
    /**
     * Implement this abstract method to realize some preparations before
     * this node will be dropped during Drag&Dropp operation.
     */
    protected abstract void onDropped();
    
    /**
     * Implement this abstract method to invoke some operations before this node 
     * will be dragged.
     */
    protected abstract void onDragg();
    
    /**
     * @return true if this node has parent node
     */
    public abstract boolean hasParentNode();
    
    /**
     * Register event filters to make this node draggable
     * @param reactNode Node that will react on mouse events. In some cases, 
     * react node and draggable node aren't the same
     */
    protected void makeNodeDraggable(Node reactNode) {
        DragContext dragContext = new DragContext();

        reactNode.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
            if (dragModeProperty.get()) {
                onPressed();
                this.toFront();
                dragContext.deltaX = e.getSceneX() - this.getTranslateX();
                dragContext.deltaY = e.getSceneY() - this.getTranslateY();
            }
        });
        
        reactNode.addEventFilter(MouseEvent.MOUSE_DRAGGED, (e) -> {
            if (dragModeProperty.get()) {
                onDragg();
                double newXPos = e.getSceneX() - dragContext.deltaX;
                double newYPos = e.getSceneY() - dragContext.deltaY;
                this.setTranslateY(newYPos);
                this.setTranslateX(newXPos);
            }
        });
        
        reactNode.addEventFilter(MouseEvent.MOUSE_RELEASED, (e) -> {
            onDropped();
        });
    }
    
    protected void makeNodeDraggable(Node reactNode, DoubleProperty scaleFactor) {
        DragContext dragContext = new DragContext();

        reactNode.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> {
            if (dragModeProperty.get()) {
                onPressed();
                this.toFront();
                dragContext.deltaX = (e.getSceneX() - this.getTranslateX());
                dragContext.deltaY = (e.getSceneY() - this.getTranslateY());
            }
        });
        
        reactNode.addEventFilter(MouseEvent.MOUSE_DRAGGED, (e) -> {
            if (dragModeProperty.get()) {
                onDragg();
                double newXPos = e.getSceneX() - dragContext.deltaX;
                double newYPos = e.getSceneY() - dragContext.deltaY;
                
                this.setTranslateY(newYPos);
                this.setTranslateX(newXPos);
            }
        });
        
        reactNode.addEventFilter(MouseEvent.MOUSE_RELEASED, (e) -> {
            onDropped();
        });
    }
    
    protected void setWidthProperty(double width) {
        if (width < MIN_WIDTH) {
            this.widthProperty.set(MIN_WIDTH);
        } else {
            this.widthProperty.set(width);
        }
    }
    
    protected void setHeghtProperty(double height) {
        if (height < MIN_HEIGHT) {
            this.heightProperty.set(MIN_HEIGHT);
        } else {
            this.heightProperty.set(height);
        }
    }
}
