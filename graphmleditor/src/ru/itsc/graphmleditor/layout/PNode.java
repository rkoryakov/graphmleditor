/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.itsc.graphmleditor.layout;

import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ru.itsc.graphml.Graph;
import static ru.itsc.graphmleditor.layout.AbstractNode.MIN_HEIGHT;
import ru.itsc.graphmleditor.pane.GraphMLPane;

/**
 * Parent node. The node that may contains children
 */
public class PNode extends AbstractNode {
    public static final int DEFAULT_HEADER_OFFSET = 33;
    
    private PNode pNode; // parent node
    private Pane contentPane; // content area for children
    private Button collButton; // Collapse button 
    private List<Graph.ProxyAutoBoundsNode.Realizers.GroupNode> groups; // to define layout of node by his state
    private AnchorPane header; // header with caption
    
    public StringProperty labelProperty;
    public StringProperty fontStyleProperty; 
    public StringProperty fontFamilyProperty;
    public DoubleProperty fontSizeProperty; 
    public Property<Color> borderColorProperty;
    public Property<Color> fillColorProperty;
    public BooleanProperty collapseState;

    public PNode(Graph.GraphNode graphNode) {
        super(graphNode);
        this.pNode = null;
        this.setId(graphNode.getId());
        // create links to each other 
        //this.graphNode = graphNode;
        graphNode.setNode(this);
    }

    /**
     * Returns a node list that will constitute graphical representation of this node.
     * This method will be called from AbstractNode() constructor to fill the list of child nodes.
     * @return list of nodes
     */
    @Override
    protected final ObservableList<Node> createNode() {
        ObservableList<Node> nodeList = FXCollections.observableArrayList();
        
        int activeGroup = graphNode.getAutoBoundsNode().getRealizers().getIntActive();
        groups = graphNode.getAutoBoundsNode().getRealizers().getGroupNodeList();

        Graph.ProxyAutoBoundsNode.Realizers.GroupNode metaGroupNode = groups.get(activeGroup);
        boolean isClosed = metaGroupNode.getState().isClosed();
        labelProperty = new SimpleStringProperty(metaGroupNode.getNodeLabel().getLabel());
        fontStyleProperty = new SimpleStringProperty(metaGroupNode.getNodeLabel().getFontStyle());
        fontFamilyProperty = new SimpleStringProperty(metaGroupNode.getNodeLabel().getFontFamily());
        fontSizeProperty = new SimpleDoubleProperty(metaGroupNode.getNodeLabel().getFontSize());
        borderColorProperty = new SimpleObjectProperty<>(metaGroupNode.getBorederStyle().getColor());
        fillColorProperty = new SimpleObjectProperty<>(metaGroupNode.getFill().getColor());
        collapseState = new SimpleBooleanProperty(isClosed);
        
        widthProperty.set(metaGroupNode.getGeomtery().getWidth());
        heightProperty.set(metaGroupNode.getGeomtery().getHeight());

        setTranslateX(metaGroupNode.getGeomtery().getX());
        setTranslateY(metaGroupNode.getGeomtery().getY());
        
        header = new AnchorPane();
        VBox vBox = new VBox();
        contentPane = new Pane();
        contentPane.setId(GraphMLPane.CSS_GROUP_NODE_CONTENET);
        Text headlabel = new Text();
        headlabel.setId(GraphMLPane.CSS_GROUP_NODE_HEADER_LABEL);
        collButton = createCollapseButton();

        header.setId(GraphMLPane.CSS_GROUP_NODE_HEADER);
        header.getChildren().add(collButton);
        header.getChildren().add(headlabel);
        header.setPrefHeight(MIN_HEIGHT);

        AnchorPane.setLeftAnchor(collButton, 1.0);
        AnchorPane.setBottomAnchor(collButton, 1.0);
        AnchorPane.setTopAnchor(collButton, 1.0);
        AnchorPane.setRightAnchor(headlabel, 1.0);
        AnchorPane.setTopAnchor(headlabel, 1.0);
        AnchorPane.setBottomAnchor(headlabel, 1.0);

        Bindings.bindBidirectional(headlabel.textProperty(), labelProperty);
        Bindings.bindBidirectional(contentPane.prefWidthProperty(), widthProperty);
        Bindings.bindBidirectional(contentPane.prefHeightProperty(), heightProperty);
        Bindings.bindBidirectional(header.prefWidthProperty(), widthProperty);

        vBox.getChildren().add(header);
        vBox.getChildren().add(contentPane);
        makeNodeDraggable(header);
        setCache(true);
        
        nodeList.add(vBox);
        return nodeList;
    }

    public Button createCollapseButton() {

        Button button = new Button(this.collapseState.get() ? "+" : "-");
        button.setId(GraphMLPane.CSS_COLLAPSE_BUTTON);

        button.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
            Graph.ProxyAutoBoundsNode.Realizers.GroupNode metaGroupNode;
            // change collapse state on mouse click
            this.collapseState.set(!this.collapseState.get());

            if (this.collapseState.get()) {
                metaGroupNode = this.groups.get(1);
                button.setText("+");
                this.contentPane.getChildren().forEach(child -> {
                    child.setVisible(false);
                });
            } else {
                metaGroupNode = this.groups.get(0);
                button.setText("-");
                this.contentPane.getChildren().forEach(child -> {
                    child.setVisible(true);
                });
            }
            setCollapseState(metaGroupNode);
        });

        return button;
    }

    private void setCollapseState(Graph.ProxyAutoBoundsNode.Realizers.GroupNode metaGroupNode) {
        this.labelProperty.setValue(metaGroupNode.getNodeLabel().getLabel());
        this.widthProperty.setValue(metaGroupNode.getGeomtery().getWidth());
        this.heightProperty.setValue(metaGroupNode.getGeomtery().getHeight());
        this.fontStyleProperty.setValue(metaGroupNode.getNodeLabel().getFontStyle());
        this.fontFamilyProperty.setValue(metaGroupNode.getNodeLabel().getFontFamily());
        this.borderColorProperty.setValue(metaGroupNode.getBorederStyle().getColor());
        this.fillColorProperty.setValue(metaGroupNode.getFill().getColor());
    }
    
    public AnchorPane getHeader() {
        return this.header;
    }
    
    public Graph.GraphNode getGraphNode() {
        return graphNode;
    }

    private void setParentNode(PNode pNode) {
        this.pNode = pNode;
    }
    
    public void setGraphNode(Graph.GraphNode graphNode) {
        this.graphNode = graphNode;
    }
    
    public Pane getContentPane() {
        return contentPane;
    }
    
    public void addChild(CNode cNode) {
        this.contentPane.getChildren().add(cNode);
    }
    
    public void addChild(PNode pNode) {
        this.contentPane.getChildren().add(pNode);
        pNode.setParentNode(this);
    }
    
    @Override
    public boolean hasParentNode() {
        return (this.pNode != null);
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
        double y = getTranslateY() + DEFAULT_HEADER_OFFSET;
        if (hasParentNode()) {
            y += this.pNode.getSceneY();
        }
        return y;
    }
    
    public double getHeaderOffset() {
       // the offset is height of header node, the height is computed automaticly and depends on 
       // header's child nodes. 
       double offset = DEFAULT_HEADER_OFFSET;
       if (hasParentNode()) {
           offset += this.pNode.getHeaderOffset();
       }
       
       return offset;
    }

    @Override
    protected void onPressed() {
        this.opacityProperty().set(0.3);
        LayoutGraph.getEdges().forEach(edge -> {
            edge.hideEdge();
        });
    }

    @Override
    protected void onDropped() {
        this.opacityProperty().set(1.0);
        toBack();
        LayoutGraph.getEdges().forEach(edge -> {
            edge.refresh();
            edge.showEdge();
        });
    }
    
    @Override
    protected void onDragg() {
        
    }
}
