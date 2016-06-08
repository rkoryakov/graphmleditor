/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.itsc.graphmleditor.layout;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import ru.itsc.commons.GeometryHelper;
import ru.itsc.graphml.Graph;
import ru.itsc.graphmleditor.GraphMLEditor;
import ru.itsc.graphmleditor.pane.GraphMLPane;

/**
 * Child node. The node which has not child nodes
 */
public class CNode extends AbstractNode {
    private PNode pNode; // parent node
    private Pane nodePane;

    private Text label;
    private List<CNode> neighbourNodes; // linked nodes
    
    private final List<Edge> sourceEdges = new ArrayList<>(); // link to list of outbound edges
    private final List<Edge> targetEdges = new ArrayList<>(); // link to list of inbound edges
    
//    public StringProperty labelProperty = new SimpleStringProperty();
//    public StringProperty fontStyleProperty = new SimpleStringProperty();
//    public StringProperty fontFamilyProperty = new SimpleStringProperty();
//    public IntegerProperty fontSizeProperty = new SimpleIntegerProperty();
//    public Property<Color> borderColorProperty = new SimpleObjectProperty();
//    public Property<Color> fillColorProperty = new SimpleObjectProperty();

    public CNode(Graph.GraphNode graphNode) {
        super(graphNode);
        this.pNode = null;
        this.setId(graphNode.getId());
        // create link to each other 
        graphNode.setNode(this);
        this.setTranslateX(graphNode.getShapeNode().getGeomtery().getX());
        this.setTranslateY(graphNode.getShapeNode().getGeomtery().getY());
    }

    public CNode(Graph.GraphNode graphNode, PNode pNode) {
        super(graphNode);
        this.pNode = pNode;
        this.setId(graphNode.getId());
        graphNode.setNode(this);
        // convert absolute vertexes to relatives
        setTranslateX(Math.abs(graphNode.getShapeNode().getGeomtery().getX() - pNode.getTranslateX()));
        setTranslateY(Math.abs(graphNode.getShapeNode().getGeomtery().getY() - pNode.getTranslateY()) - PNode.DEFAULT_HEADER_OFFSET);
        
        System.out.println("CNode X= " + getTranslateX() + " Y = " + getTranslateY());
    }

    /**
     * Returns a node list that will constitute graphical representation of this node.
     * This method will be called from AbstractNode() constructor to fill the list of child nodes.
     * @return list of nodes
     */
    @Override
    protected final ObservableList<Node> createNode() {
        ObservableList<Node> nodeList = FXCollections.observableArrayList();
        
        Graph.ShapeNode shapeNode = graphNode.getShapeNode();
        setWidthProperty(shapeNode.getGeomtery().getWidth());
        setHeghtProperty(shapeNode.getGeomtery().getHeight());
        
//        this.labelProperty.set(shapeNode.getNodeLabel().getLabel());
//        this.fontSizeProperty.set(shapeNode.getNodeLabel().getFontSize());
//        this.borderColorProperty.setValue(shapeNode.getBorederStyle().getColor());
//        this.fillColorProperty.setValue(shapeNode.getFill().getColor());
//        this.fontFamilyProperty.set(shapeNode.getNodeLabel().getFontFamily());
//        this.fontStyleProperty.set(shapeNode.getNodeLabel().getFontStyle());
        nodePane = createPane();
        label = createTextLabel();
        nodeList.addAll(nodePane, label);
        
        return nodeList;
    }

    protected final Pane createPane() {
        Pane pane = new Pane();
        //this.setCache(true);
        pane.setMinWidth(this.widthProperty.getValue());
        pane.setMinHeight(this.heightProperty.getValue());
        pane.setMaxWidth(this.widthProperty.getValue());
        pane.setMaxHeight(this.heightProperty.getValue());
        pane.setId(GraphMLPane.CSS_CHILD_NODE_CONTENT);
        makeNodeDraggable(pane, GraphMLEditor.getController().getGraphMLPane().getRootGroup().scaleXProperty());
        return pane;
    }
    
    protected final Text createTextLabel() {
        Text text = new Text();
        Graph.NodeLabel nodeLabel = this.graphNode.getShapeNode().getNodeLabel();
        
        text.setId(GraphMLPane.CSS_CHILD_NODE_LABEL);
        text.setText(nodeLabel.getLabel());
        text.setVisible(nodeLabel.isVisible());
        text.setFont(Font.font(nodeLabel.getFontFamily(), FontPosture.findByName(nodeLabel.getFontStyle()), nodeLabel.getFontSize()));
        // the text label has undefined coordinates
        if (GeometryHelper.equal(nodeLabel.getX(), 0.0) && GeometryHelper.equal(nodeLabel.getY(), 0.0)) {
            text.setWrappingWidth(this.widthProperty.getValue() * 2);
            nodeLabel.setX(this.getTranslateX());
            nodeLabel.setY(this.getTranslateY() + this.heightProperty.getValue());
        }
        
        text.setTranslateX(nodeLabel.getX());
        text.setTranslateY(nodeLabel.getY() + 4.0);
        
        return text;
    }

    @Override
    public boolean hasParentNode() {
        return (this.pNode != null);
    }
    
    public double getHeaderOffset() {
        double offset = 0;
        if (hasParentNode()) {
            offset = this.pNode.getHeaderOffset();
        }
        return offset;
    }
    
    /**
     * Get x coordinate of this node on the Scene. 
     * It is calculated relatively to the parent node
     * if parent node is existing.
     * @return 
     */
    public double getSceneX() {
        double x = getTranslateX();
        if (hasParentNode()) {
            x += this.pNode.getSceneX();
        }
        return x;
    }
    
    /**
     * Get y coordinate of this node on the Scene. 
     * It is calculated relatively to the parent node
     * if parent node is existing.
     * @return 
     */
    public double getSceneY() {
        double y = getTranslateY();
        if (hasParentNode()) {
            y += this.pNode.getSceneY();
        }
        return y;
    }
    
    @Override
    protected void onPressed() {
        this.toFront();
    }

    @Override
    protected void onDropped() {
        onDragg();
    }
    
    @Override
    protected void onDragg() {
        this.sourceEdges.forEach(edge -> {
            edge.refresh();
        });
        this.targetEdges.forEach(edge -> {
            edge.refresh();
        });
    }
    
    public Pane getNode() {
        return this.nodePane;
    }
    
    public Text getLabel() {
        return this.label;
    }
    
    public Graph.GraphNode getGraphNode() {
        return this.graphNode;
    }
    
    public List<Edge> getSourceEdges() {
        return this.sourceEdges;
    }
    
    public List<Edge> getTargetEdges() {
        return this.targetEdges;
    }
    
}
