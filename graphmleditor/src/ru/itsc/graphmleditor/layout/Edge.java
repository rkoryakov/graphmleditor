/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.itsc.graphmleditor.layout;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import ru.itsc.commons.GeometryHelper;
import ru.itsc.graphml.Graph;

/**
 *
 * @author koryakov.rv
 */
public class Edge extends Parent {
    private final Graph.GraphEdge graphEdge;
    private CNode source;
    private CNode target;
    //private List<Point2D> interPoints; // intermediate vertices
    private final List<EdgePoint> edgePoints; // graphical representation of intermediate vertices
    private Point2D startPoint;
    private Point2D endPoint;
    private Path path = new Path();
    private double identSx;
    private double identSy;
    private double identTx;
    private double identTy;
    
    private Shape arrow;
    
    private static Edge _activeEdge; // current active edge
    private boolean isActiveState = false;
    
    public static final double DEFAULT_ARROW_WIDTH = 6.0;
    public static final double DEFAULT_ARROW_HEIGHT = 8.0;
    public static final Color DEFAULT_ACTIVE_STATE_COLOR = Color.GOLD;
    public static final double DEFAULT_ACTIVE_STATE_WIDTH = 2.2;
    public static final Color DEFAULT_INACTIVE_STATE_COLOR = Color.BLACK;
    public static final double DEFAULT_INACTIVE_STATE_WIDTH = 1.0;
    
    private double currentEdgeWidth = DEFAULT_INACTIVE_STATE_WIDTH;
    private Color currentEdgeColor = DEFAULT_INACTIVE_STATE_COLOR;
    
    public Edge(Graph.GraphEdge e) {
        this.graphEdge = e;
        this.setId(e.getEdgeId());
        this.edgePoints = buildEdgePoints(e);
        this.source = (CNode)e.getGraph().getGraphNode(e.getSourceId()).getNode();
        this.target = (CNode)e.getGraph().getGraphNode(e.getTargetId()).getNode();
        
        if (this.source != null && this.target != null) {
            this.source.getSourceEdges().add(this);
            this.target.getTargetEdges().add(this);
            buildEdge();
            addEventFilter(MouseEvent.MOUSE_ENTERED, this::onMouseOver);
            addEventFilter(MouseEvent.MOUSE_EXITED, this::onMouseOut);
            path.addEventFilter(MouseEvent.MOUSE_CLICKED, this::onMouseClicked);
        } else {
            throw new RuntimeException("Source node of edge " + this.getId() + " is " + this.source + 
                    ", target node of edge " + this.getId() + " is " + this.target);
        }
    }
    
    /**
     * creating edge from source node to target node
     * through the intermediate points
     */
    private void buildEdge() {
        Pane sourcePane = this.getSource().getNode();
        Pane targetPane = this.getTarget().getNode();

        this.getChildren().removeAll(path, arrow);
        double nodeWidth = sourcePane.getMaxWidth();
        double nodeHeight = sourcePane.getMaxHeight();

        double targetNodeWidth = targetPane.getMaxWidth();
        double targetNodeHeight = targetPane.getMaxHeight();

        double sdx = nodeWidth / 2;
        double sdy = nodeHeight / 2;
        double tdx = targetNodeWidth / 2;
        double tdy = targetNodeHeight / 2;

        // to center the vertices of the nodes
        double sx = getSource().getSceneX() + sdx;
        double sy = getSource().getSceneY() + sdy;
        double tx = getTarget().getSceneX() + tdx;
        double ty = getTarget().getSceneY() + tdy;

        Point2D lastP = new Point2D(tx, ty);
        Point2D previousP = new Point2D(sx, sy);
        Point2D firstPoint = new Point2D(sx, sy);
        Point2D secondPoint = new Point2D(tx, ty);

        if (edgePoints.size() > 0) {
            secondPoint = edgePoints.get(0).toPoint2D();
            previousP = edgePoints.get(edgePoints.size() - 1).toPoint2D();
        }

        Point2D targetVector = lastP.subtract(previousP);
        Point2D sourceVector = firstPoint.subtract(secondPoint);
        double targetAngel = targetVector.angle(0, -1);
        double sourceAngel = sourceVector.angle(0, -1);
        arrow = createArrow();
        Bounds boundsArrow = arrow.getBoundsInLocal();
        double offsetForArrow = boundsArrow.getHeight();
        double targetMagnitude = targetVector.magnitude();
        
        
        // we want to draw line-arrow from the source node to the target node
        // thus, we need to define a border edge of the source/target node from/to which 
        // the line-arrow must be drawn
        if (sourceAngel >= 0.0 && sourceAngel < 45.0) {
            sy = sy + sdy + identSy;
        } else if (sourceAngel >= 45.0 && sourceAngel < 135.0) {
            if (sourceVector.getX() < 0) {
                sx = sx + sdx + identSx;
            } else {
                sx = sx - sdx - identSx;
            }
        } else if (sourceAngel >= 135.0 && sourceAngel <= 180.0) {
            sy = sy - sdy - identSy;
        }

        if (targetAngel >= 0.0 && targetAngel < 45.0) {
            ty = ty + tdy + identTy;
            
        } else if (targetAngel >= 45.0 && targetAngel < 135.0) {
            if (targetVector.getX() < 0) {
                tx = tx + tdx + identTx;
            } else {
                tx = tx - tdx - identTx;
            }
        } else if (targetAngel >= 135.0 && targetAngel <= 180.0) {
            ty = ty - tdy - identTy;
        }
        
        double cosT = targetVector.getX() / targetMagnitude;
        double sinT = targetVector.getY() / targetMagnitude;
        Point2D deltaPointArrow = new Point2D(cosT*DEFAULT_ARROW_WIDTH/2, sinT*DEFAULT_ARROW_HEIGHT/2);
        
        startPoint = new Point2D(sx, sy);
        endPoint = new Point2D(tx - deltaPointArrow.getX(), ty - deltaPointArrow.getY());
        path = buildPath();
        
        // top-left or bottom-left sectors
        arrow.setRotate(targetVector.getX() < 0 ? -targetAngel : targetAngel);
        arrow.setTranslateX(endPoint.getX() - boundsArrow.getWidth()/2/* - deltaPointArrow.getX()*/);
        arrow.setTranslateY(endPoint.getY() - boundsArrow.getHeight()/2/* - deltaPointArrow.getY()*/);

        this.getChildren().addAll(path, arrow);
        path.toBack();
        arrow.toBack();
    }
    
    private List<EdgePoint> buildEdgePoints(Graph.GraphEdge edge) {
        List<EdgePoint> result = new ArrayList();

        if (edge.getPolyLineEdge() != null) {
            List<Point2D> points = edge.getPolyLineEdge().getPath().getPoints();
            points.forEach(point -> {
                EdgePoint edgePoint = createEdgePoint(point);
                result.add(edgePoint);
            });
        }

        return result;
    }
    
    private EdgePoint createEdgePoint(Point2D point) {
        EdgePoint edgePoint = new EdgePoint(point, this);
        edgePoint.setFill(Color.CORNFLOWERBLUE);
        return edgePoint;
    }
    
    public Path buildPath() {
        MoveTo moveTo = new MoveTo(startPoint.getX(), startPoint.getY());
        
        path.getElements().clear();
        path.getElements().add(moveTo);
        if (edgePoints.size() > 0) {
            edgePoints.forEach((point) -> {
                path.getElements().add(new LineTo(point.getX(), point.getY()));
            });
        }
        path.getElements().add(new LineTo(endPoint.getX(), endPoint.getY()));
        path.setStroke(currentEdgeColor);
        path.setStrokeWidth(currentEdgeWidth);

        return path;
    }
    
    private Shape createArrow() {
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(new Double[]{
            DEFAULT_ARROW_WIDTH/2, 0.0, 
            DEFAULT_ARROW_WIDTH, DEFAULT_ARROW_HEIGHT,
            0.0, DEFAULT_ARROW_HEIGHT
        });
        polygon.setFill(DEFAULT_INACTIVE_STATE_COLOR);
        //polygon.setStroke(DEFAULT_INACTIVE_STATE_COLOR);
        //polygon.setStrokeWidth(DEFAULT_INACTIVE_STATE_WIDTH);
        return polygon;
    }
    
    private void onMouseClicked(MouseEvent me) {
        System.out.println("Edge " + this.graphEdge.getEdgeId() + " is db clicked");
        if (me.getButton() == MouseButton.PRIMARY) {
            addEdgePoint(new Point2D(me.getX(), me.getY()));
        }
    }
    
    private void onMouseOver(MouseEvent me) {
        setCursor(Cursor.HAND);
        setActiveEdgePoints();
    }
    
    private void onMouseOut(MouseEvent me) {
        setCursor(Cursor.DEFAULT);
    }
    
    public void setStrokeWidth(double width) {
        this.path.setStrokeWidth(width);
        //this.arrow.setStrokeWidth(width);
    }
    
    public void setStrokeColor(Color color) {
        this.path.setStroke(color);
        this.arrow.setStroke(color);
        this.arrow.setFill(color);
    }
    
    public void setActiveEdgePoints() {
        //System.out.println("setActiveState() ");
        if (_activeEdge != this) {
            if (_activeEdge != null) {
                _activeEdge.isActiveState = false;
                _activeEdge.refresh();
                _activeEdge.currentEdgeWidth = DEFAULT_INACTIVE_STATE_WIDTH;
                _activeEdge.setStrokeWidth(currentEdgeWidth);
                _activeEdge.hideEdgePoints();
            }

            _activeEdge = this;
            currentEdgeWidth = DEFAULT_ACTIVE_STATE_WIDTH;
            setStrokeWidth(currentEdgeWidth);
            this.toFront();
            isActiveState = true;
            showEdgePoints();
        }
    }
    
    public Graph.GraphEdge getGraphEdge() {
        return graphEdge;
    }
    
    public void refresh() {
        this.buildEdge();
    }
    
    public CNode getSource() {
        return source;
    }

    public void setSource(CNode source) {
        this.source = source;
    }

    public CNode getTarget() {
        return target;
    }

    public void setTarget(CNode target) {
        this.target = target;
    }
    
    public Point2D getStartPoint() {
        return this.startPoint;
    }
    
    public Point2D getEndPoint() {
        return this.endPoint;
    }
    
    public Path getPath() {
        return this.path;
    }
    
        public double getIdentSx() {
        return identSx;
    }

    public void setIdentSx(double identSx) {
        this.identSx = identSx;
    }

    public double getIdentSy() {
        return identSy;
    }

    public void setIdentSy(double identSy) {
        this.identSy = identSy;
    }

    public double getIdentTx() {
        return identTx;
    }

    public void setIdentTx(double identTx) {
        this.identTx = identTx;
    }

    public double getIdentTy() {
        return identTy;
    }

    public void setIdentTy(double identTy) {
        this.identTy = identTy;
    }
    
    public void addEdgePoint(Point2D point) {
        EdgePoint edgePoint = createEdgePoint(point);
        int pos = getPosOfNewPoint(point);
        hideEdgePoints();
        this.edgePoints.add(pos, edgePoint);
        showEdgePoints();
    }
    
    public void removeEdgePoint(EdgePoint edgePoint) {
        hideEdgePoints();
        this.edgePoints.remove(edgePoint);
        showEdgePoints();
        refresh();
    }
    
    private int getPosOfNewPoint(Point2D point) {
        if (edgePoints.size() > 0) {
            if (edgePoints.size() > 1) {
                for (int i = 1; i < edgePoints.size(); i ++) {
                    if (GeometryHelper.hasLinesegmentPoint(edgePoints.get(i-1).toPoint2D(), 
                            edgePoints.get(i).toPoint2D(), point))
                        return i;
                }
            }
            if (GeometryHelper.hasLinesegmentPoint(edgePoints.get(edgePoints.size()-1).toPoint2D(), 
                            endPoint, point)) {
                return edgePoints.size();
            }
        }
        
        return 0;
    }
    
    public void showEdgePoints() {
        this.getChildren().addAll(edgePoints);
    }
    
    public void hideEdgePoints() {
        this.getChildren().removeAll(edgePoints);
    }
    
    public List<EdgePoint> getEdgePoints() {
        return this.edgePoints;
    }
    
    private Timeline hideTimeline = new Timeline(
                new KeyFrame(Duration.millis(400.0), (ActionEvent t) -> {
                        this.setCache(false);
                    },
                new KeyValue(this.opacityProperty(), 0, Interpolator.LINEAR)
        ));
    private Timeline showTimeline = new Timeline(
                new KeyFrame(Duration.millis(400.0), (ActionEvent t) -> {
                        this.setCache(false);
                    },
                new KeyValue(this.opacityProperty(), 1, Interpolator.LINEAR)
        ));
    public void hideEdge() {
        this.setCache(true);
        if (showTimeline != null)
            showTimeline.stop();
        
        hideTimeline.play();
    }
    
    public void showEdge() {
        this.setCache(true);
 
        if (hideTimeline != null)
            hideTimeline.stop();
        
        showTimeline.play();
    }
}
