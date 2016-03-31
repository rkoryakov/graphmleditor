/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.itsc.graphmleditor.layout;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author macbookmacbook
 */
public class EdgePoint extends AbstractNode {

    private Circle circle;
    private Edge edgePointer;
    
    public static final double DEFAULT_RADIUS = 3.0;
    
    public EdgePoint(Point2D point, Edge edgePointer) {
        this.setTranslateX(point.getX());
        this.setTranslateY(point.getY());
        this.edgePointer = edgePointer;
    }
    
    public void setEdgePointer(Edge edgePointer) {
        this.edgePointer = edgePointer;
    }
    
    @Override
    protected ObservableList<Node> createNode() {
        ObservableList<Node> nodeList = FXCollections.observableArrayList();
        circle = new Circle(DEFAULT_RADIUS);
        nodeList.add(circle);
        makeNodeDraggable(circle);
        addEventFilter(MouseEvent.MOUSE_CLICKED, this::onMouseRightClick);

        return nodeList;
    }

    @Override
    protected void onPressed() {
        setCursor(Cursor.CLOSED_HAND);
    }

    @Override
    protected void onDropped() {
        this.edgePointer.setActiveEdgePoints();
        setCursor(Cursor.OPEN_HAND);
    }

    @Override
    protected void onDragg() {
        edgePointer.refresh();
    }
    
    private void onMouseRightClick(MouseEvent me) {
        if (me.getButton() == MouseButton.SECONDARY) {
            this.edgePointer.removeEdgePoint(this);
        }
    }
    
    @Override
    public boolean hasParentNode() {
        return false;
    }
    
    public void setFill(Color color) {
        this.circle.setFill(color);
    }
    
    public void setRadius(double r) {
        this.circle.setRadius(r);
    }
    
    public Point2D toPoint2D() {
        return new Point2D(getX(), getY());
    }
    
    public double getX() {
        return getTranslateX();
    }
    
    public double getY() {
        return getTranslateY();
    }
}
