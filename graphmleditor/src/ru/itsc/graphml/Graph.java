package ru.itsc.graphml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import ru.itsc.graphmleditor.layout.AbstractNode;

/**
 * @author koryakov.rv
 */
public class Graph {
    // map of all nodes of this graph/sub-graph
    private static final LinkedHashMap<String, GraphNode> nodeMapGlobal = new LinkedHashMap<>();
    // map of linked nodes
    private final LinkedHashMap<String, GraphNode> nodeMap = new LinkedHashMap<>();
    
    private final List<GraphEdge> edgeList = new ArrayList<>();
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public void addNode(GraphNode node) {
        // put the node to global map for convenient using later
        nodeMapGlobal.put(node.getId(), node);
        // put the node to map of linked nodes
        nodeMap.put(node.getId(), node);
    }
    
    /**
     * Get node by Id. 
     * @param id
     * @return if such node exists in linked map then return it, otherwise 
     * trying to get the node from global map.
     */
    public GraphNode getGraphNode(String id) {
        if (nodeMap.containsKey(id))
            return nodeMap.get(id);
        else
            return nodeMapGlobal.get(id);
    }

    public LinkedHashMap<String, GraphNode> getGraphNodes() {
        return nodeMap;
    }

    public List<GraphEdge> getGraphEdges() {
        return edgeList;
    }

    public GraphEdge createGraphEdgeInstance(String edgeId, String sourceId, String targetId) {
        return new GraphEdge(edgeId, sourceId, targetId);
    }
    
    public GraphNode createGraphNodeInstance(String id) {
        return new GraphNode(id);
    }

    public ShapeNode createShapeNodeInstance() {
        return new ShapeNode();
    }

    public Geometry createGeometryInstance() {
        return new Geometry(0, 0, 0, 0);
    }

    public Fill createFillInstance() {
        return new Fill(Color.AQUA, false);
    }

    public BorderStyle createBorderStyleInstance() {
        return new BorderStyle(Color.AQUA, null, 0);
    }

    public NodeLabel createNodeLabelInstance() {
        return new NodeLabel();
    }
    
    public ProxyAutoBoundsNode createProxyAutoBoundsNodeInstance() {
        return new ProxyAutoBoundsNode();
    }

    public Shape createShapeInstance() {
        return new Shape();
    }
    
    public State createStateInstance() {
        return new State();
    }
    
    public Insets createInsetsInstance() {
        return new Insets();
    }
    
    public BorderInsets createBorderInsets() {
        return new BorderInsets();
    }

    public class GraphNode {

        private LinkedHashMap<String, String> dataMap = new LinkedHashMap<>();
        private String id;
        private ShapeNode shapeNode;
        private ProxyAutoBoundsNode autoBoundsNode;
        private Graph subGraph;
        private AbstractNode node; // link to JavaFX implementation of this node

        /**
         * @return link to JavaFX implementation of this node
         */
        public AbstractNode getNode() {
            return node;
        }

        /**
         * 
         * @param node link to JavaFX implementation of this node
         */
        public void setNode(AbstractNode node) {
            this.node = node;
        }
        
        public Graph getSubGraph() {
            return subGraph;
        }

        public boolean isGroup() {
            return getSubGraph() != null;
        }
        
        public void setSubGraph(Graph subGraph) {
            this.subGraph = subGraph;
        }
    
        public GraphNode(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void addData(String key, String value) {
            dataMap.put(key, value);
        }

        public String getDataValue(String key) {
            return dataMap.get(key);
        }

        public LinkedHashMap<String, String> getData() {
            return dataMap;
        }

        public void remove(String key) {
            dataMap.remove(key);
        }

        public void clear() {
            dataMap.clear();
        }


        public void setShapeNode(ShapeNode shapeNode) {
            this.shapeNode = shapeNode;
        }

        public ShapeNode getShapeNode() {
            if (shapeNode == null) {
                shapeNode = new ShapeNode();
                shapeNode.setBorederStyle(new BorderStyle(Color.BLACK, id, 3.0));
                shapeNode.setFill(new Fill(Color.BLUEVIOLET, true));
                shapeNode.setGeomtery(new Geometry(5.0, 5.0, 30.0, 30.0));
                shapeNode.setNodeLabel(new NodeLabel());
                shapeNode.setShape(new Shape());
            }
                
            return shapeNode;
        }

        public ProxyAutoBoundsNode getAutoBoundsNode() {
            return autoBoundsNode;
        }

        public void setAutoBoundsNode(ProxyAutoBoundsNode autoBoundsNode) {
            this.autoBoundsNode = autoBoundsNode;
        }
    }

    public class ProxyAutoBoundsNode {
        private Realizers realizers;

        public Realizers getRealizers() {
            return realizers;
        }

        public void setRealizers(Realizers realizers) {
            this.realizers = realizers;
        }
        
        public Realizers createRealizersInstance() {
            return new Realizers();
        }
        
        public class Realizers {

            private int active;
            private List<GroupNode> groupNodeList = new ArrayList<GroupNode>();
            
            public GroupNode createGroupNodeInstance() {
                return new GroupNode();
            }
            
            public int getIntActive() {
                return active;
            }

            public String getStringActive() {
                return String.valueOf(active);
            }
            
            public void setActive(int active) {
                this.active = active;
            }

            public void setActive(String active) {
                this.active = Integer.parseInt(active);
            }

            public List<GroupNode> getGroupNodeList() {
                return groupNodeList;
            }
            
            public class GroupNode {

                private Geometry geomtery;
                private Fill fill;
                private BorderStyle borederStyle;
                private NodeLabel nodeLabel;
                private Shape shape;
                private State state;
                private Insets insets;
                private BorderInsets borderInsets;

                public Geometry getGeomtery() {
                    return geomtery;
                }

                public void setGeomtery(Geometry geomtery) {
                    this.geomtery = geomtery;
                }

                public Fill getFill() {
                    return fill;
                }

                public void setFill(Fill fill) {
                    this.fill = fill;
                }

                public BorderStyle getBorederStyle() {
                    return borederStyle;
                }

                public void setBorederStyle(BorderStyle borederStyle) {
                    this.borederStyle = borederStyle;
                }

                public NodeLabel getNodeLabel() {
                    return nodeLabel;
                }

                public void setNodeLabel(NodeLabel nodeLabel) {
                    this.nodeLabel = nodeLabel;
                }

                public Shape getShape() {
                    return shape;
                }

                public void setShape(Shape shape) {
                    this.shape = shape;
                }

                public State getState() {
                    return state;
                }

                public void setState(State state) {
                    this.state = state;
                }

                public Insets getInsets() {
                    return insets;
                }

                public void setInsets(Insets insets) {
                    this.insets = insets;
                }

                public BorderInsets getBorderInsets() {
                    return borderInsets;
                }

                public void setBorderInsets(BorderInsets borderInsets) {
                    this.borderInsets = borderInsets;
                }
                
            }
        }
    }

    /**
     * The class represent information from GraphML node <y:ShapeNode>
     */
    public class ShapeNode {

        private Geometry geomtery;
        private Fill fill;
        private BorderStyle borederStyle;
        private NodeLabel nodeLabel;
        private Shape shape;

        public Geometry getGeomtery() {
            return geomtery;
        }

        public void setGeomtery(Geometry geomtery) {
            this.geomtery = geomtery;
        }

        public Fill getFill() {
            return fill;
        }

        public void setFill(Fill fill) {
            this.fill = fill;
        }

        public BorderStyle getBorederStyle() {
            return borederStyle;
        }

        public void setBorederStyle(BorderStyle borederStyle) {
            this.borederStyle = borederStyle;
        }

        public NodeLabel getNodeLabel() {
            return nodeLabel;
        }

        public void setNodeLabel(NodeLabel nodeLabel) {
            this.nodeLabel = nodeLabel;
        }

        public Shape getShape() {
            return shape;
        }

        public void setShape(Shape shape) {
            this.shape = shape;
        }

    }

    public class GraphEdge {
        private LinkedHashMap<String, String> dataMap = new LinkedHashMap<>();
        private Graph graph; // link to graph
        private String edgeId;
        private String sourceId;
        private String targetId;
        private PolyLineEdge polyLineEdge;

        public Graph getGraph() {
            return graph;
        }

        public void setGraph(Graph graph) {
            this.graph = graph;
        }
        
        public GraphEdge(String edgeId, String sourceId, String targetId) {
            this.edgeId = edgeId;
            this.sourceId = sourceId;
            this.targetId = targetId;
        }

        public String getSourceId() {
            return sourceId;
        }

        public void setSourceId(String sourceId) {
            this.sourceId = sourceId;
        }

        public String getTargetId() {
            return targetId;
        }

        public void setTargetId(String targetId) {
            this.targetId = targetId;
        }

        public String getEdgeId() {
            return edgeId;
        }

        public void setEdgeId(String edgeId) {
            this.edgeId = edgeId;
        }
        
        public void addData(String key, String value) {
            dataMap.put(key, value);
        }

        public String getDataValue(String key) {
            return dataMap.get(key);
        }

        public LinkedHashMap<String, String> getData() {
            return dataMap;
        }

        public LinkedHashMap<String, String> getDataMap() {
            return dataMap;
        }

        public void setDataMap(LinkedHashMap<String, String> dataMap) {
            this.dataMap = dataMap;
        }

        public PolyLineEdge getPolyLineEdge() {
            return polyLineEdge;
        }

        public void setPolyLineEdge(PolyLineEdge polyLineEdge) {
            this.polyLineEdge = polyLineEdge;
        }
        
    }

    public class PolyLineEdge {
        private Path path;
        private LineStyle lineStyle;
        private Arrows arrows;
        private BendStyle bendStyle;
        
        public PolyLineEdge() {
//            this.path = new Path();
//            this.lineStyle = new LineStyle();
//            this.arrows = new Arrows();
//            this.bendStyle = new BendStyle();
        }
        
        public Path getPath() {
            return path;
        }

        public void setPath(Path path) {
            this.path = path;
        }

        public LineStyle getLineStyle() {
            return lineStyle;
        }

        public void setLineStyle(LineStyle lineStyle) {
            this.lineStyle = lineStyle;
        }

        public Arrows getArrows() {
            return arrows;
        }

        public void setArrows(Arrows arrows) {
            this.arrows = arrows;
        }

        public BendStyle getBendStyle() {
            return bendStyle;
        }

        public void setBendStyle(BendStyle bendStyle) {
            this.bendStyle = bendStyle;
        }
    }
    
    public class Path {
         
        private double sx;
        private double sy;
        private double tx;
        private double ty;
        
        private List<Point2D> points = new ArrayList<Point2D>();

        public double getSx() {
            return sx;
        }

        public void setSx(double sx) {
            this.sx = sx;
        }

        public void setSx(String sx) {
            this.sx = Double.parseDouble(sx);
        }
        
        public double getSy() {
            return sy;
        }

        public void setSy(double sy) {
            this.sy = sy;
        }

        public void setSy(String sy) {
            this.sy = Double.parseDouble(sy);
        }
        
        public double getTx() {
            return tx;
        }

        public void setTx(double tx) {
            this.tx = tx;
        }

        public void setTx(String tx) {
            this.tx = Double.parseDouble(tx);
        }
        
        public double getTy() {
            return ty;
        }

        public void setTy(double ty) {
            this.ty = ty;
        }

        public void setTy(String ty) {
            this.ty = Double.parseDouble(ty);
        }
        
        public List<Point2D> getPoints() {
            return points;
        }

        public void setPoints(List<Point2D> points) {
            this.points = points;
        }
    }
    
    public class LineStyle {
        private Color color;
        private String type;
        private double width;

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getWidth() {
            return width;
        }

        public void setWidth(double width) {
            this.width = width;
        }
        
        public void setWidth(String width) {
            this.width = Double.parseDouble(width);
        }
    }
    
    public class Arrows {
        private String source;
        private String target;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }
    }
    
    public class BendStyle {
        private boolean smoothed;

        public boolean getSmoothed() {
            return smoothed;
        }

        public void setSmoothed(String smoothed) {
            this.smoothed = Boolean.parseBoolean(smoothed);
        }
    }
    
    public PolyLineEdge createPolyLineEdgeInstance() {
        return new PolyLineEdge();
    }
    
    public Path createPathInstance() {
        return new Path();
    }
    
    public LineStyle createLineStyleInstance() {
        return new LineStyle();
    }
    
    public Arrows createArrowsInstance() {
        return new Arrows();
    }
    
    public BendStyle createBendStyleInstance() {
        return new BendStyle();
    }
    
    public class Geometry {

        private double x;
        private double y;
        private double width;
        private double height;

        public Geometry() {
            this.x = 0.0;
            this.y = 0.0;
            this.width = 0.0;
            this.height = 0.0;
        }
        
        public Geometry(String x, String y, String width, String height) {
            this.x = Double.parseDouble(x);
            this.y = Double.parseDouble(y);
            this.width = Double.parseDouble(width);
            this.height = Double.parseDouble(height);
        }

        public Geometry(double x, double y, double width, double height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public void setX(String x) {
            this.x = Double.parseDouble(x);
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public void setY(String y) {
            this.y = Double.parseDouble(y);
        }

        public double getWidth() {
            return width;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public void setWidth(String width) {
            this.width = Double.parseDouble(width);
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public void setHeight(String height) {
            this.height = Double.parseDouble(height);
        }
    }

    public class Fill {

        private Color color;
        private boolean transparent;

        public Fill(String color, String transparent) {
            this.color = Color.web(color);
            this.transparent = Boolean.parseBoolean(transparent);
        }

        public Fill(Color color, boolean transparent) {
            this.color = color;
            this.transparent = transparent;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = Color.valueOf(color);
        }

        public boolean isTransparent() {
            return transparent;
        }

        public void setTransparent(boolean transparent) {
            this.transparent = transparent;
        }
    }

    public class BorderStyle {

        private Color color;
        private String type;
        private double width;

        public BorderStyle(Color color, String type, double width) {
            this.color = color;
            this.type = type;
            this.width = width;
        }

        public BorderStyle(String color, String type, String width) {
            this.color = Color.web(color);
            this.type = type;
            this.width = Double.parseDouble(width);
        }

        public Color getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = Color.valueOf(color);
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getWidth() {
            return width;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public void setWidth(String width) {
            this.width = Double.parseDouble(width);
        }
    }

public class NodeLabel {

        private String label;
        private String alignment;
        private String autoSizePolicy;
        private boolean hasBackgroundColor;
        private Color backgroundColor;
        private String borderDistance;
        private String fontFamily;
        private double fontSize = 10;
        private String fontStyle;
        private boolean hasLineColor;
        private boolean hasText;
        private Color textColor;
        private double height;
        private double width;
        private String modelName;
        private String modelPosition;
        private boolean visible = true;
        private double x = 0.0;
        private double y = 0.0;
        private LabelModel labelModel;
        private ModelParameter modelParameter;

        public LabelModel createLabelModelInstance() {
            return new LabelModel();
        }

        public ModelParameter createModelParametrInstance() {
            return new ModelParameter();
        }

        public String getLabel() {
            return this.label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getAlignment() {
            return alignment;
        }

        public void setAlignment(String alignment) {
            this.alignment = alignment;
        }

        public String getAutoSizePolicy() {
            return autoSizePolicy;
        }

        public void setAutoSizePolicy(String autoSizePolicy) {
            this.autoSizePolicy = autoSizePolicy;
        }

        public boolean isHasBackgroundColor() {
            return hasBackgroundColor;
        }

        public void setHasBackgroundColor(boolean hasBackgroundColor) {
            this.hasBackgroundColor = hasBackgroundColor;
        }

        public void setHasBackgroundColor(String hasBackgroundColor) {
            this.hasBackgroundColor = Boolean.getBoolean(hasBackgroundColor);
        }

        public Color getBackgroundColor() {
            return backgroundColor;
        }

        public void setBackgroundColor(String backgroundColor) {
            this.backgroundColor = Color.valueOf(backgroundColor);
        }

        public String getBorderDistance() {
            return borderDistance;
        }

        public void setBorderDistance(String borderDistance) {
            this.borderDistance = borderDistance;
        }

        public String getFontFamily() {
            return fontFamily;
        }

        public void setFontFamily(String fontFamily) {
            this.fontFamily = fontFamily;
        }

        public double getFontSize() {
            return fontSize;
        }

        public void setFontSize(int fontSize) {
            this.fontSize = fontSize;
        }

        public void setFontSize(String fontSize) {
            if (fontSize != null && fontSize.length() > 0) {
                this.fontSize = Double.parseDouble(fontSize);
            }
        }

        public String getFontStyle() {
            return fontStyle;
        }

        public void setFontStyle(String fontStyle) {
            this.fontStyle = fontStyle;
        }

        public boolean isHasLineColor() {
            return hasLineColor;
        }

        public void setHasLineColor(boolean hasLineColor) {
            this.hasLineColor = hasLineColor;
        }

        public void setHasLineColor(String hasLineColor) {
            this.hasLineColor = Boolean.getBoolean(hasLineColor);
        }

        public boolean isHasText() {
            return hasText;
        }

        public void setHasText(boolean hasText) {
            this.hasText = hasText;
        }

        public void setHasText(String hasText) {
            this.hasText = Boolean.getBoolean(hasText);
        }

        public Color getTextColor() {
            return textColor;
        }

        public void setTextColor(Color textColor) {
            this.textColor = textColor;
        }

        public void setTextColor(String textColor) {
            this.textColor = Color.valueOf(textColor);
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public void setHeight(String height) {
            try {
                this.height = Double.parseDouble(height);
            } catch (NumberFormatException nfe) {
                System.err.println(nfe.getLocalizedMessage());
            }
        }

        public double getWidth() {
            return width;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public void setWidth(String width) {
            this.width = Double.parseDouble(width);
        }

        public String getModelName() {
            return modelName;
        }

        public void setModelName(String modelName) {
            this.modelName = modelName;
        }

        public String getModelPosition() {
            return modelPosition;
        }

        public void setModelPosition(String modelPosition) {
            this.modelPosition = modelPosition;
        }

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        public void setVisible(String visible) {
            this.visible = Boolean.parseBoolean(visible);
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public void setX(String x) {
            this.x = Double.parseDouble(x);
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public void setY(String y) {
            this.y = Double.parseDouble(y);
        }

        public LabelModel getLabelModel() {
            return labelModel;
        }

        public void setLabelModel(LabelModel labelModel) {
            this.labelModel = labelModel;
        }

        public ModelParameter getModelParameter() {
            return modelParameter;
        }

        public void setModelParameter(ModelParameter modelParameter) {
            this.modelParameter = modelParameter;
        }

        public class LabelModel {

            private SmartNodeLabelModel smartNodeLabelModel;

            public SmartNodeLabelModel createSmartNodeLabelModelInstance() {
                return new SmartNodeLabelModel();
            }

            public SmartNodeLabelModel getSmartNodeLabelModel() {
                return smartNodeLabelModel;
            }

            public void setSmartNodeLabelModel(SmartNodeLabelModel smartNodeLabelModel) {
                this.smartNodeLabelModel = smartNodeLabelModel;
            }

            public class SmartNodeLabelModel {

                private double distance;

                public double getDistance() {
                    return distance;
                }

                public void setDistance(double distance) {
                    this.distance = distance;
                }

                public void setDistance(String distance) {
                    this.distance = Double.parseDouble(distance);
                }
            }
        }

        public class ModelParameter {

            private SmartNodeLabelModelParameter smartNodeLabelModelParameter;

            public SmartNodeLabelModelParameter createSmartNodeLabelModelParameterInstance() {
                return new SmartNodeLabelModelParameter();
            }

            public SmartNodeLabelModelParameter getSmartNodeLabelModelParameter() {
                return smartNodeLabelModelParameter;
            }

            public void setSmartNodeLabelModelParameter(SmartNodeLabelModelParameter smartNodeLabelModelParameter) {
                this.smartNodeLabelModelParameter = smartNodeLabelModelParameter;
            }

            public class SmartNodeLabelModelParameter {

                private double labelRatioX;
                private double labelRatioY;
                private double nodeRatioX;
                private double nodeRatioY;
                private double offsetX;
                private double offsetY;
                private double upX;
                private double upY;

                public double getLabelRatioX() {
                    return labelRatioX;
                }

                public void setLabelRatioX(double labelRatioX) {
                    this.labelRatioX = labelRatioX;
                }

                public void setLabelRatioX(String labelRatioX) {
                    this.labelRatioX = Double.parseDouble(labelRatioX);
                }

                public double getLabelRatioY() {
                    return labelRatioY;
                }

                public void setLabelRatioY(double labelRatioY) {
                    this.labelRatioY = labelRatioY;
                }

                public void setLabelRatioY(String labelRatioY) {
                    this.labelRatioY = Double.parseDouble(labelRatioY);
                }

                public double getNodeRatioX() {
                    return nodeRatioX;
                }

                public void setNodeRatioX(double nodeRatioX) {
                    this.nodeRatioX = nodeRatioX;
                }

                public void setNodeRatioX(String nodeRatioX) {
                    this.nodeRatioX = Double.parseDouble(nodeRatioX);
                }

                public double getNodeRatioY() {
                    return nodeRatioY;
                }

                public void setNodeRatioY(double nodeRatioY) {
                    this.nodeRatioY = nodeRatioY;
                }

                public void setNodeRatioY(String nodeRatioY) {
                    this.nodeRatioY = Double.parseDouble(nodeRatioY);
                }

                public double getOffsetX() {
                    return offsetX;
                }

                public void setOffsetX(double offsetX) {
                    this.offsetX = offsetX;
                }

                public void setOffsetX(String offsetX) {
                    this.offsetX = Double.parseDouble(offsetX);
                }

                public double getOffsetY() {
                    return offsetY;
                }

                public void setOffsetY(double offsetY) {
                    this.offsetY = offsetY;
                }

                public void setOffsetY(String offsetY) {
                    this.offsetY = Double.parseDouble(offsetY);
                }

                public double getUpX() {
                    return upX;
                }

                public void setUpX(double upX) {
                    this.upX = upX;
                }

                public void setUpX(String upX) {
                    this.upX = Double.parseDouble(upX);
                }

                public double getUpY() {
                    return upY;
                }

                public void setUpY(double upY) {
                    this.upY = upY;
                }

                public void setUpY(String upY) {
                    this.upY = Double.parseDouble(upY);
                }
            }
        }
    }

    public class Shape {

        private String type;
        
        Shape() {
            this.type = "";
        }

        Shape(String type) {
            this.type = type;
        }
        
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public class State {

        private boolean closed;
        private double closedHeight;
        private double closedWidth;
        private boolean innerGraphDisplayEnabled;

        public boolean isClosed() {
            return closed;
        }

        public void setClosed(boolean closed) {
            this.closed = closed;
        }

        public void setClosed(String closed) {
            this.closed = Boolean.parseBoolean(closed);
        }

        public double getClosedHeight() {
            return closedHeight;
        }

        public void setClosedHeight(double closedHeight) {
            this.closedHeight = closedHeight;
        }

        public void setClosedHeight(String closedHeight) {
            this.closedHeight = Double.parseDouble(closedHeight);
        }

        public double getClosedWidth() {
            return closedWidth;
        }

        public void setClosedWidth(double closedWidth) {
            this.closedWidth = closedWidth;
        }

        public void setClosedWidth(String closedWidth) {
            this.closedWidth = Double.parseDouble(closedWidth);
        }

        public boolean isInnerGraphDisplayEnabled() {
            return innerGraphDisplayEnabled;
        }

        public void setInnerGraphDisplayEnabled(boolean innerGraphDisplayEnabled) {
            this.innerGraphDisplayEnabled = innerGraphDisplayEnabled;
        }

        public void setInnerGraphDisplayEnabled(String innerGraphDisplayEnabled) {
            this.innerGraphDisplayEnabled = Boolean.parseBoolean(innerGraphDisplayEnabled);
        }
    }

    public class Insets {

        private int bottom;
        private double bottomF;
        private int left;
        private double leftF;
        private int right;
        private double rightF;
        private int top;
        private double topF;

        public int getBottom() {
            return bottom;
        }

        public void setBottom(int bottom) {
            this.bottom = bottom;
        }

        public void setBottom(String bottom) {
            this.bottom = Integer.parseInt(bottom);
        }

        public double getBottomF() {
            return bottomF;
        }

        public void setBottomF(double bottomF) {
            this.bottomF = bottomF;
        }

        public void setBottomF(String bottomF) {
            this.bottomF = Double.parseDouble(bottomF);
        }

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public void setLeft(String left) {
            this.left = Integer.parseInt(left);
        }

        public double getLeftF() {
            return leftF;
        }

        public void setLeftF(double leftF) {
            this.leftF = leftF;
        }

        public void setLeftF(String leftF) {
            this.leftF = Double.parseDouble(leftF);
        }

        public int getRight() {
            return right;
        }

        public void setRight(int right) {
            this.right = right;
        }

        public void setRight(String right) {
            this.right = Integer.parseInt(right);
        }

        public double getRightF() {
            return rightF;
        }

        public void setRightF(double rightF) {
            this.rightF = rightF;
        }

        public void setRightF(String rightF) {
            this.rightF = Double.parseDouble(rightF);
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public void setTop(String top) {
            this.top = Integer.parseInt(top);
        }

        public double getTopF() {
            return topF;
        }

        public void setTopF(double topF) {
            this.topF = topF;
        }

        public void setTopF(String topF) {
            this.topF = Double.parseDouble(topF);
        }
    }

    public class BorderInsets {

        private int bottom;
        private double bottomF;
        private int left;
        private double leftF;
        private int right;
        private double rightF;
        private int top;
        private double topF;

        public int getBottom() {
            return bottom;
        }

        public void setBottom(int bottom) {
            this.bottom = bottom;
        }

        public void setBottom(String bottom) {
            this.bottom = Integer.parseInt(bottom);
        }

        public double getBottomF() {
            return bottomF;
        }

        public void setBottomF(double bottomF) {
            this.bottomF = bottomF;
        }

        public void setBottomF(String bottomF) {
            this.bottomF = Double.parseDouble(bottomF);
        }

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public void setLeft(String left) {
            this.left = Integer.parseInt(left);
        }

        public double getLeftF() {
            return leftF;
        }

        public void setLeftF(double leftF) {
            this.leftF = leftF;
        }

        public void setLeftF(String leftF) {
            this.leftF = Double.parseDouble(leftF);
        }

        public int getRight() {
            return right;
        }

        public void setRight(int right) {
            this.right = right;
        }

        public void setRight(String right) {
            this.right = Integer.parseInt(right);
        }

        public double getRightF() {
            return rightF;
        }

        public void setRightF(double rightF) {
            this.rightF = rightF;
        }

        public void setRightF(String rightF) {
            this.rightF = Double.parseDouble(rightF);
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public void setTop(String top) {
            this.top = Integer.parseInt(top);
        }

        public double getTopF() {
            return topF;
        }

        public void setTopF(double topF) {
            this.topF = topF;
        }

        public void setTopF(String topF) {
            this.topF = Double.parseDouble(topF);
        }
    }
}
