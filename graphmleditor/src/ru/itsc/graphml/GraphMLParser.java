/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.itsc.graphml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ru.itsc.graphml.Graph.BorderInsets;
import ru.itsc.graphml.Graph.GraphEdge;
import ru.itsc.graphml.Graph.GraphNode;
import ru.itsc.graphml.Graph.ShapeNode;
import ru.itsc.graphml.Graph.BorderStyle;
import ru.itsc.graphml.Graph.Fill;
import ru.itsc.graphml.Graph.Geometry;
import ru.itsc.graphml.Graph.Insets;
import ru.itsc.graphml.Graph.NodeLabel;
import ru.itsc.graphml.Graph.NodeLabel.LabelModel;
import ru.itsc.graphml.Graph.NodeLabel.LabelModel.SmartNodeLabelModel;
import ru.itsc.graphml.Graph.NodeLabel.ModelParameter;
import ru.itsc.graphml.Graph.NodeLabel.ModelParameter.SmartNodeLabelModelParameter;
import ru.itsc.graphml.Graph.Path;
import ru.itsc.graphml.Graph.PolyLineEdge;
import ru.itsc.graphml.Graph.ProxyAutoBoundsNode;
import ru.itsc.graphml.Graph.ProxyAutoBoundsNode.Realizers;
import ru.itsc.graphml.Graph.ProxyAutoBoundsNode.Realizers.GroupNode;
import ru.itsc.graphml.Graph.Shape;
import ru.itsc.graphml.Graph.State;
import ru.itsc.graphml.Graph.LineStyle;
import ru.itsc.graphml.Graph.Arrows;
import ru.itsc.graphml.Graph.BendStyle;

/**
 *
 * @author koryakov.rv
 */
public class GraphMLParser {
    private static final String KEY_ELEMENT = "key";
    private static final String ROOT_GRAPH = "G";
    private static final String ATTRIBUTE_ID = "id";
    private static final String NODE_ELEMENT = "node";
    private static final String EDGE_ELEMENT = "edge";
    private static final String DATA_ELEMENT = "data";
    private static final String GRAPH_ELEMENT = "graph";
    private static final String YFILES_TYPE = "yfiles.type";
    private static final String NODE_GRAPHICS = "nodegraphics";
    private static final String EDGE_GRAPHICS = "edgegraphics";
    private static final String YGEOMETRY = "y:Geometry";
    private static final String YFILL = "y:Fill";
    private static final String YBRORDER_STYLE = "y:BorderStyle";
    private static final String YNODE_LABEL = "y:NodeLabel";
    private static final String YSHAPE = "y:Shape";
    private static final String YLABELMODEL = "y:LabelModel";
    private static final String YSMARTNODELABELMODEL = "y:SmartNodeLabelModel";
    private static final String YMODELPARAMETER = "y:ModelParameter";
    private static final String YSMARTNODELABELMODELPARAMETR = "y:SmartNodeLabelModelParameter";
    private static final String YSHAPENODE = "y:ShapeNode";
    private static final String YPROXYAUTOBOUNDSNODE = "y:ProxyAutoBoundsNode";
    private static final String YREALIZERS = "y:Realizers";
    private static final String YGROUPNODE = "y:GroupNode";
    private static final String YSTATE = "y:State";
    private static final String YINSETS = "y:Insets";
    private static final String YBORDERINSETS = "y:BorderInsets";
    private static final String YPOLYLINEEDGE = "y:PolyLineEdge";
    private static final String YPATH = "y:Path";
    private static final String SX = "sx";
    private static final String SY = "sy";
    private static final String TX = "tx";
    private static final String TY = "ty";
    private static final String YPOINT = "y:Point";
    private static final String YLINESTYLE = "y:LineStyle";
    private static final String YARROWS = "y:Arrows";
    private static final String YBENDSTYLE = "y:BendStyle";
    
    private static final String SMOOTHED = "smoothed";
    private static final String TARGET = "target";
    private static final String SOURCE = "source";
    private static final String HEGHT = "height";
    private static final String WIDTH = "width";
    private static final String X = "x";
    private static final String Y = "y";
    private static final String COLOR = "color";
    private static final String TRANSPARENT = "transparent";
    private static final String TYPE = "type";
    private static final String ALIGNMENT = "alignment";
    private static final String AUTOSIZEPOLICY = "autoSizePolicy";
    private static final String BACKGROUNDCOLOR = "backgroundColor";
    private static final String BORDERDISTANCE = "borderDistance";
    private static final String FONTFAMILY = "fontFamily";
    private static final String FONTSIZE = "fontSize";
    private static final String FONTSTYLE = "fontStyle";
    private static final String HASBACKGROUNDCOLOR = "hasBackgroundColor";
    private static final String HASLINECOLOR = "hasLineColor";
    private static final String HASTEXT = "hasText";
    private static final String MODELNAME = "modelName";
    private static final String MODELPOSITION = "modelPosition";
    private static final String TEXTCOLOR = "textColor";
    private static final String VISIBLE = "visible";
    private static final String DISTANCE = "distance";
    private static final String LABEL_RATIO_X = "labelRatioX";
    private static final String LABEL_RATIO_Y = "labelRatioY";
    private static final String NODE_RATIO_X = "nodeRatioX";
    private static final String NODE_RATIO_Y = "nodeRatioY";
    private static final String OFFSET_X = "offsetX";
    private static final String OFFSET_Y = "offsetY";
    private static final String UP_X = "upX";
    private static final String UP_Y = "upY";
    private static final String ACTIVE = "active";
    private static final String CLOSED = "closed";
    private static final String CLOSED_HEIGHT = "closedHeight";
    private static final String CLOSED_WIDTH = "closedWidth";
    private static final String INNER_GRAPH_DISPLAY_ENABLED = "innerGraphDisplayEnabled";
    private static final String BOTTOM = "bottom";
    private static final String BOTTOM_F = "bottomF";
    private static final String LEFT = "left";
    private static final String LEFT_F = "leftF";
    private static final String RIGHT = "right";
    private static final String RIGHT_F = "rightF";
    private static final String TOP = "top";
    private static final String TOP_F = "topF";
    
    private final Map<String, Key> keys = new HashMap<String, Key>();
    
    public GraphMLParser () {
    }
    
    /**
     * Main method that should be invoked from external class to convert 
     * GraphMl into internal structure of application.
     * @param graphml
     * @return Graph structure
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException 
     */
    public Graph parse(String graphml) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(System.err, Charset.defaultCharset());
        documentBuilder.setErrorHandler(new GraphMLParserErrorHandler(new PrintWriter(outputStreamWriter)));
        Document document = documentBuilder.parse(new InputSource(new ByteArrayInputStream(graphml.getBytes("utf-8"))));
        document.getDocumentElement().normalize();
        setKeys(document);
        return buildGraph(document);
    }

    private void setKeys(Document document) {
        NodeList list = document.getElementsByTagName(KEY_ELEMENT);
        
        for (int i = 0; i < list.getLength(); i ++) {
            NamedNodeMap namedNodeMap = list.item(i).getAttributes();
            String attrName = getNodeValue(namedNodeMap.getNamedItem("attr.name"));
            String attrType = getNodeValue(namedNodeMap.getNamedItem("attr.type"));
            String attrFor = getNodeValue(namedNodeMap.getNamedItem("for"));
            String yfilesType = getNodeValue(namedNodeMap.getNamedItem(YFILES_TYPE));
            String attrId = getNodeValue(namedNodeMap.getNamedItem(ATTRIBUTE_ID));
            System.out.println("yfilesType = " + yfilesType);
            keys.put(attrId, new Key(attrName, attrType, attrFor, yfilesType, attrId));
        }
    }
    
    /**
     * Entry point method for building graph
     * @param documnent
     * @return 
     */
    private Graph buildGraph(Document documnent) {
        Graph graph = new Graph();
        
        NodeList graphs = documnent.getElementsByTagName(GRAPH_ELEMENT);
        NodeList graphElementList = graphs.item(0).getChildNodes();
        // iterate <node> tags
        for (int i = 0; i < graphElementList.getLength(); i ++) {
            Node node = graphElementList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE ) {
                Element eNode = (Element)node;
                if (NODE_ELEMENT.equals(eNode.getNodeName())) {
                    GraphNode gn = buildGraphNode(graph, eNode);
                    graph.addNode(gn);
                    //graph.getGraphNodes().put(gn.getId(), gn);
                } else if (EDGE_ELEMENT.equals(eNode.getNodeName())) {
                    GraphEdge edge = buildGraphEdge(graph, node);
                    graph.getGraphEdges().add(edge);
                }
            }
        }
        
        return graph;
    }
    
    private Graph buildSubGraph(Element subGraph) {
        Graph graph = new Graph();
        String graphId = subGraph.getAttribute(ATTRIBUTE_ID);
        graph.setId(graphId);
        NodeList graphElementList = subGraph.getChildNodes();
        for (int i = 0; i < graphElementList.getLength(); i ++) {
            Node node = graphElementList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE ) {
                Element eNode = (Element)node;
                if (NODE_ELEMENT.equals(eNode.getNodeName())) {
                    GraphNode gn = buildGraphNode(graph, eNode);
                    graph.addNode(gn);
                    //graph.getGraphNodes().put(gn.getId(), gn);
                } else if (EDGE_ELEMENT.equals(eNode.getNodeName())) {
                    GraphEdge edge = buildGraphEdge(graph, node);
                    graph.getGraphEdges().add(edge);
                }
            }
        }
        
        return graph;
    }
    
    /**
     * Parse GraphML node <node>
     * @param graph
     * @param eNode
     * @return 
     */
    private GraphNode buildGraphNode(Graph graph, Element eNode) {
        NamedNodeMap dataAttr = eNode.getAttributes();
        String nodeId = getNodeValue(dataAttr.getNamedItem(ATTRIBUTE_ID));
        GraphNode graphNode = graph.createGraphNodeInstance(nodeId);
        NodeList dataList = eNode.getChildNodes();
        // iterate <data> tags
        for (int i = 0; i < dataList.getLength(); i++) {
            Node dataNode = dataList.item(i);
            
            if (dataNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eData = (Element) dataNode;
                
                if (DATA_ELEMENT.equals(dataNode.getNodeName())) {
                    String eId = eData.getAttribute(KEY_ELEMENT);
                    String eVal = eData.getTextContent();
                    graphNode.addData(eId, eVal);
                
                    // data graphics node
                    if (NODE_GRAPHICS.equals(keys.get(eId).yfilesType)) {
                        NodeList childNodes = eData.getChildNodes();
                        for (int j = 0; j < childNodes.getLength(); j++) {
                            Node node = childNodes.item(j);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                if (YPROXYAUTOBOUNDSNODE.equals(node.getNodeName())) {
                                    ProxyAutoBoundsNode proxyAutoBoundsNode = buildProxyAutoBoundsNode(graph, node);
                                    graphNode.setAutoBoundsNode(proxyAutoBoundsNode);
                                } else if (YSHAPENODE.equals(node.getNodeName())) {
                                    ShapeNode shapeNode = buildShapeNode(graph, node);
                                    graphNode.setShapeNode(shapeNode);
                                }
                            }
                        }
                    } 
                } else if (GRAPH_ELEMENT.equals(dataNode.getNodeName())) {
                    Graph subGraph = buildSubGraph(eData);
                    graphNode.setSubGraph(subGraph);
                }
            }
        }
        return graphNode;
    }
    
    /**
     * Parse GraphML node <edge>
     * @param graph
     * @return 
     */
    private GraphEdge buildGraphEdge(Graph graph, Node node) {
        
        Element element = (Element)node;
        String edgeId = element.getAttribute(ATTRIBUTE_ID);
        String source = element.getAttribute(SOURCE);
        String target = element.getAttribute(TARGET);
        GraphEdge graphEdge = graph.createGraphEdgeInstance(edgeId, source, target);
        NodeList dataList = node.getChildNodes();
        graphEdge.setGraph(graph);
        
        // iterate <data> tags
        for (int i = 0; i < dataList.getLength(); i++) {
            Node dataNode = dataList.item(i);
            
            if (dataNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eData = (Element) dataNode;
                
                if (DATA_ELEMENT.equals(dataNode.getNodeName())) {
                    String eId = eData.getAttribute(KEY_ELEMENT);
                    String eVal = eData.getTextContent();
                    graphEdge.addData(eId, eVal);
                
                    // edge graphics node
                    if (EDGE_GRAPHICS.equals(keys.get(eId).yfilesType)) {
                        NodeList childNodes = eData.getChildNodes();
                        for (int j = 0; j < childNodes.getLength(); j++) {
                            Node childDataNode = childNodes.item(j);
                            if (childDataNode.getNodeType() == Node.ELEMENT_NODE) {
                                if (YPOLYLINEEDGE.equals(childDataNode.getNodeName())) {
                                    // parsing of <y:PolyLineEdge> element
                                    PolyLineEdge polyLineEdge = buildPolyLineEdgeNode(graph, childDataNode, graphEdge);
                                    graphEdge.setPolyLineEdge(polyLineEdge);
                                }
                            }
                        }
                    } 
                } 
            }
        }
        return graphEdge;
    }
    
    /**
     * Parse a GraphML node <y:PolyLineEdge>
     * @param graph
     * @param node
     * @return 
     */
    private PolyLineEdge buildPolyLineEdgeNode(Graph graph, Node node, GraphEdge graphEdge) {
        PolyLineEdge polyLineEdge = graph.createPolyLineEdgeInstance();
        graphEdge.setPolyLineEdge(polyLineEdge);
        
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i ++) {
            Node nodeItem = nodeList.item(i);
            if (nodeItem.getNodeType() == Node.ELEMENT_NODE) {
                String nodeName = nodeItem.getNodeName();
                
                switch (nodeName) {
                    case YPATH:
                        polyLineEdge.setPath(buildPathNode(graph, nodeItem));
                        break;
                    case YLINESTYLE:
                        polyLineEdge.setLineStyle(buildLineStyleNode(graph, nodeItem));
                        break;
                    case YARROWS:
                        polyLineEdge.setArrows(buildArrowsNode(graph, nodeItem));
                        break;
                    case YBENDSTYLE:
                        polyLineEdge.setBendStyle(buildBendStyleNode(graph, nodeItem));
                        break;
                }
            }
        }
        
        return polyLineEdge;
    }
    
    private LineStyle buildLineStyleNode(Graph graph, Node node) {
        LineStyle lineStyle = graph.createLineStyleInstance();
        Element element = (Element)node;
        String color = element.getAttribute(COLOR);
        String type = element.getAttribute(TYPE);
        String width = element.getAttribute(WIDTH);
        lineStyle.setColor(Color.valueOf(color));
        lineStyle.setWidth(width);
        lineStyle.setType(type);
        
        return lineStyle;
    }
    
    private Arrows buildArrowsNode(Graph graph, Node node) {
        Arrows arrows = graph.createArrowsInstance();
        Element element = (Element)node;
        String source = element.getAttribute(SOURCE);
        String target = element.getAttribute(TARGET);
        arrows.setSource(source);
        arrows.setTarget(target);
        
        return arrows;
    }
    
    private BendStyle buildBendStyleNode(Graph graph, Node node) {
        BendStyle bendStyle = graph.createBendStyleInstance();
        Element element = (Element)node;
        String smothed = element.getAttribute(SMOOTHED);
        bendStyle.setSmoothed(smothed);
        
        return bendStyle;
    }
    
    /**
     * Parse node <y:Path>
     * @param graph
     * @param node
     * @return 
     */
    private Path buildPathNode(Graph graph, Node node) {
        Path path = graph.createPathInstance();
        Element element = (Element)node;
        path.setSx(element.getAttribute(SX));
        path.setSy(element.getAttribute(SY));
        path.setTx(element.getAttribute(TX));
        path.setTy(element.getAttribute(TY));
        
        NodeList nodeList = node.getChildNodes();
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i ++) {
                Node point = nodeList.item(i);
                if (YPOINT.equals(point.getNodeName())) {
                    Point2D point2D = buildPointNode(point);
                    path.getPoints().add(point2D);
                }
            }
        }
        return path;
    }
    
    /**
     * Parse node <y:Point>
     * @param node
     * @return 
     */
    private Point2D buildPointNode(Node node) {
        Element point = (Element)node;
        double x = Double.parseDouble(point.getAttribute(X));
        double y = Double.parseDouble(point.getAttribute(Y));
        Point2D point2D = new Point2D(x, y);
        
        return point2D;
    }
    
    /**
     * Parse a GraphML node <y:ProxyAutoBoundsNode>
     * @param graph
     * @param node
     * @return 
     */
    private ProxyAutoBoundsNode buildProxyAutoBoundsNode(Graph graph, Node node) {
        ProxyAutoBoundsNode proxyAutoBoundsNode = graph.createProxyAutoBoundsNodeInstance();
        Realizers realizers = proxyAutoBoundsNode.createRealizersInstance();
        Node realizersNode = getChildNodeByName(node, YREALIZERS);
        
        if (realizersNode != null) {
            Element realizersElement = (Element) realizersNode;
            String active = realizersElement.getAttribute(ACTIVE);
            realizers.setActive(active);
            proxyAutoBoundsNode.setRealizers(realizers);

            // iterate <y:GroupNode> nodes
            NodeList groupNodeList = realizersNode.getChildNodes();
            for (int i = 0; i < groupNodeList.getLength(); i ++) {
                Node nodeGroupNode = groupNodeList.item(i);
                if (YGROUPNODE.equals(nodeGroupNode.getNodeName()) && nodeGroupNode.getNodeType() == Node.ELEMENT_NODE) {
                    GroupNode groupNode = realizers.createGroupNodeInstance();
                    realizers.getGroupNodeList().add(groupNode);
                    NodeList nodeList = nodeGroupNode.getChildNodes();
                    // iterate layout nodes
                    for (int j = 0; j < nodeList.getLength(); j++) {
                        Node n = nodeList.item(j);
                        if (n.getNodeType() == Node.ELEMENT_NODE) {
                            String nodeName = n.getNodeName();

                            if (nodeName != null) {
                                switch (nodeName) {
                                    case YGEOMETRY:
                                        groupNode.setGeomtery(buildNodeGeometry(graph, n));
                                        break;
                                    case YFILL:
                                        groupNode.setFill(buildNodeFill(graph, n));
                                        break;
                                    case YBRORDER_STYLE:
                                        groupNode.setBorederStyle(buildNodeBorderStyle(graph, n));
                                        break;
                                    case YNODE_LABEL:
                                        groupNode.setNodeLabel(buildNodeNodeLabel(graph, n));
                                        break;
                                    case YSHAPE:
                                        groupNode.setShape(buildNodeShape(graph, n));
                                        break;
                                    case YINSETS:
                                        groupNode.setInsets(buildNodeInsets(graph, n));
                                        break;
                                    case YBORDERINSETS:
                                        groupNode.setBorderInsets(buildNodeBorderInsets(graph, n));
                                        break;
                                    case YSTATE:
                                        groupNode.setState(buildNodeState(graph, n));
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return proxyAutoBoundsNode;
    }
    
    /**
     * Parse GraphML node <y:ShapeNode>
     * @param graph
     * @param node
     * @return 
     */
    private ShapeNode buildShapeNode(Graph graph, Node node) {
        ShapeNode shapeNode = graph.createShapeNodeInstance();
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i ++) {
            Node n = nodeList.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                String nodeName = n.getNodeName();
                
                if (YGEOMETRY.equals(nodeName)) {
                    shapeNode.setGeomtery(buildNodeGeometry(graph, n));
                } else if (YFILL.equals(nodeName)) {
                    shapeNode.setFill(buildNodeFill(graph, n));
                } else if (YBRORDER_STYLE.equals(nodeName)) {
                    shapeNode.setBorederStyle(buildNodeBorderStyle(graph, n));
                } else if (YNODE_LABEL.equals(nodeName)) {
                    shapeNode.setNodeLabel(buildNodeNodeLabel(graph, n));
                } else if (YSHAPE.equals(nodeName)) {
                    shapeNode.setShape(buildNodeShape(graph, n));
                }
            }
        }
        
        return shapeNode;
    }
    
    private Insets buildNodeInsets(Graph graph, Node node) {
        Insets insets = graph.createInsetsInstance();
        Element nodeElement = (Element)node;
        String bottom = nodeElement.getAttribute(BOTTOM);
        String bottomF = nodeElement.getAttribute(BOTTOM_F);
        String left = nodeElement.getAttribute(LEFT);
        String leftF = nodeElement.getAttribute(LEFT_F);
        String right = nodeElement.getAttribute(RIGHT);
        String rightF = nodeElement.getAttribute(RIGHT_F);
        String top = nodeElement.getAttribute(TOP);
        String topF = nodeElement.getAttribute(TOP_F);
        insets.setBottom(bottom);
        insets.setBottomF(bottomF);
        insets.setLeft(left);
        insets.setLeftF(leftF);
        insets.setRight(right);
        insets.setRightF(rightF);
        insets.setTop(top);
        insets.setTopF(topF);
        
        return insets;
    }
    
    private BorderInsets buildNodeBorderInsets(Graph graph, Node node) {
        BorderInsets borderInsets = graph.createBorderInsets();
        Element nodeElement = (Element)node;
        String bottom = nodeElement.getAttribute(BOTTOM);
        String bottomF = nodeElement.getAttribute(BOTTOM_F);
        String left = nodeElement.getAttribute(LEFT);
        String leftF = nodeElement.getAttribute(LEFT_F);
        String right = nodeElement.getAttribute(RIGHT);
        String rightF = nodeElement.getAttribute(RIGHT_F);
        String top = nodeElement.getAttribute(TOP);
        String topF = nodeElement.getAttribute(TOP_F);
        borderInsets.setBottom(bottom);
        borderInsets.setBottomF(bottomF);
        borderInsets.setLeft(left);
        borderInsets.setLeftF(leftF);
        borderInsets.setRight(right);
        borderInsets.setRightF(rightF);
        borderInsets.setTop(top);
        borderInsets.setTopF(topF);
        
        return borderInsets;
    }
    
    private State buildNodeState(Graph graph, Node node) {
        State state = graph.createStateInstance();
        Element nodeElement = (Element)node;
        String closed  = nodeElement.getAttribute(CLOSED);
        String closedWidth  = nodeElement.getAttribute(CLOSED_WIDTH);
        String closedHeight  = nodeElement.getAttribute(CLOSED_HEIGHT);
        String innerGraphDisplayEnabled  = nodeElement.getAttribute(INNER_GRAPH_DISPLAY_ENABLED);
        state.setClosed(closed);
        state.setClosedWidth(closedWidth);
        state.setClosedHeight(closedHeight);
        state.setInnerGraphDisplayEnabled(innerGraphDisplayEnabled);
        
        return state;
    }
    
    /**
     * Parse GraphMl node <y:Geometry>
     * 
     * @param shapeNode parent node of Geometry
     * @param node node <y:Geometry>
     * @return Geometry structure
     */
    private Geometry buildNodeGeometry(Graph graph, Node node) {
        Geometry geometry = graph.createGeometryInstance();
        
        Element eNode = (Element)node;
        String heght = eNode.getAttribute(HEGHT);
        String width = eNode.getAttribute(WIDTH);
        String x = eNode.getAttribute(X);
        String y = eNode.getAttribute(Y);
        geometry.setHeight(heght);
        geometry.setWidth(width);
        geometry.setX(x);
        geometry.setY(y);
        
        return geometry;
    }
    
    /**
     * Parse GraphMl node <y:Fill>
     * 
     * @param shapeNode parent node of Geometry
     * @param node node <y:Fill>
     * @return Fill structure
     */
    private Fill buildNodeFill(Graph graph, Node node) {
        Fill fill = graph.createFillInstance();
        Element eNode = (Element)node;
        String color = getColor(eNode.getAttribute(COLOR));
        String transparent = eNode.getAttribute(TRANSPARENT);
        System.out.println("Color = " + color + ", transparent = " + transparent);
        fill.setColor(color);
        fill.setTransparent(Boolean.getBoolean(transparent));
        
        return fill;
    }
    
    /**
     * Parse GraphMl node <y:BorderStyle>
     * @param shapeNode parent node of BorderStyle
     * @param node node <y:BorderStyle>
     * @return BorderStyle structure
     */
    private BorderStyle buildNodeBorderStyle(Graph graph, Node node) {
        BorderStyle borderStyle = graph.createBorderStyleInstance();
        Element eNode = (Element)node;
        String color = getColor(eNode.getAttribute(COLOR));
        String type = eNode.getAttribute(TYPE);
        String width = eNode.getAttribute(WIDTH);
        System.out.println("Color = " + color + ", Type = " + type + ", width = " + width);
        borderStyle.setColor(color);
        borderStyle.setType(type);
        borderStyle.setWidth(width);
        
        return borderStyle;
    }
    
    /**
     * Parse GraphMl node <y:NodeLabel> and all child nodes
     * @param shapeNode parent node of NodeLabel
     * @param node node <y:NodeLabel>
     * @return NodeLabel structure
     */
    private NodeLabel buildNodeNodeLabel(Graph graph, Node node) {
        NodeLabel nodeLabel = graph.createNodeLabelInstance();
        Element eNode = (Element)node;
        NamedNodeMap attrMap = eNode.getAttributes();
        
        nodeLabel.setAlignment(eNode.getAttribute(ALIGNMENT));
        nodeLabel.setAutoSizePolicy(eNode.getAttribute(AUTOSIZEPOLICY));
        nodeLabel.setBackgroundColor(getColor(eNode.getAttribute(BACKGROUNDCOLOR)));
        nodeLabel.setBorderDistance(eNode.getAttribute(BORDERDISTANCE));
        nodeLabel.setFontFamily(eNode.getAttribute(FONTFAMILY));
        nodeLabel.setFontSize(eNode.getAttribute(FONTSIZE));
        nodeLabel.setFontStyle(eNode.getAttribute(FONTSTYLE));
        nodeLabel.setHasBackgroundColor(eNode.getAttribute(HASBACKGROUNDCOLOR));
        nodeLabel.setHasLineColor(eNode.getAttribute(HASLINECOLOR));
        nodeLabel.setHasText(eNode.getAttribute(HASTEXT));
        nodeLabel.setHeight(eNode.getAttribute(HEGHT));
        nodeLabel.setModelName(eNode.getAttribute(MODELNAME));
        nodeLabel.setModelPosition(eNode.getAttribute(MODELPOSITION));
        nodeLabel.setTextColor(getColor(eNode.getAttribute(TEXTCOLOR)));
        nodeLabel.setVisible(eNode.getAttribute(VISIBLE));
        nodeLabel.setWidth(eNode.getAttribute(WIDTH));
        nodeLabel.setX(eNode.getAttribute(X));
        nodeLabel.setY(eNode.getAttribute(Y));
        
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i ++) {
            Node n = nodeList.item(i);
            if (n.getNodeType() == Node.CDATA_SECTION_NODE) {
                if (n.getNodeValue() != null && n.getNodeValue().trim().length() > 0) {
                    nodeLabel.setLabel(n.getNodeValue());
                }
            } else if (n.getNodeType() == Node.TEXT_NODE) {
                if (n.getNodeValue() != null && n.getNodeValue().trim().length() > 0) {
                    nodeLabel.setLabel(n.getNodeValue());
                }
            } else if (n.getNodeType() == Node.ELEMENT_NODE) {
                if (YLABELMODEL.equals(n.getNodeName())) {
                    LabelModel labelModel = nodeLabel.createLabelModelInstance();
                    nodeLabel.setLabelModel(labelModel);
                    
                    // <y:SmartNodeLabelModel>
                    
                    Node nodeSmartNodeLM = getChildNodeByName(n, YSMARTNODELABELMODEL);
                    if (nodeSmartNodeLM != null) {
                        SmartNodeLabelModel smartNodeLabelModel = labelModel.createSmartNodeLabelModelInstance();
                        Element smartNodeLMElement = (Element)nodeSmartNodeLM;
                        smartNodeLabelModel.setDistance(smartNodeLMElement.getAttribute(DISTANCE));
                        labelModel.setSmartNodeLabelModel(smartNodeLabelModel);
                    }
                } else if (YMODELPARAMETER.equals(n.getNodeName())) {
                    ModelParameter modelParameter = nodeLabel.createModelParametrInstance();
                    nodeLabel.setModelParameter(modelParameter);
                    
                    // <y:SmartNodeLabelModelParameter>
                    
                    Node nodeSmartNodeLMP = getChildNodeByName(n, YSMARTNODELABELMODELPARAMETR);
                    if (nodeSmartNodeLMP != null) {
                        SmartNodeLabelModelParameter labelModelParameter = modelParameter.createSmartNodeLabelModelParameterInstance();
                        Element labelModelParameterElement = (Element)nodeSmartNodeLMP;
                        labelModelParameter.setLabelRatioX(labelModelParameterElement.getAttribute(LABEL_RATIO_X));
                        labelModelParameter.setLabelRatioY(labelModelParameterElement.getAttribute(LABEL_RATIO_Y));
                        labelModelParameter.setNodeRatioX(labelModelParameterElement.getAttribute(NODE_RATIO_X));
                        labelModelParameter.setNodeRatioY(labelModelParameterElement.getAttribute(NODE_RATIO_Y));
                        labelModelParameter.setOffsetX(labelModelParameterElement.getAttribute(OFFSET_X));
                        labelModelParameter.setOffsetY(labelModelParameterElement.getAttribute(OFFSET_Y));
                        labelModelParameter.setUpX(labelModelParameterElement.getAttribute(UP_X));
                        labelModelParameter.setUpY(labelModelParameterElement.getAttribute(UP_Y));
                        modelParameter.setSmartNodeLabelModelParameter(labelModelParameter);
                    }
                }
            }
        }
        return nodeLabel;
    }
    
    /**
     * Parse GraphMl node <y:Shape>
     * @param shapeNode
     * @param node
     * @return Shape structure
     */
    private Shape buildNodeShape(Graph graph, Node node) {
        Shape shape = graph.createShapeInstance();
        Element eNode = (Element)node;
        shape.setType(eNode.getAttribute(TYPE));
        return shape;
    }
    
    private String getColor(String color) {
        String result = color;
        if (color == null || color.length() == 0) {
            result = "#000000";
        }
        
        return result;
    }
    
    public Map<String, Key> getKeys() {
        return keys;
    }
    
    public String getNodeValue(Node node) {
        if (node != null)
            return node.getNodeValue();
        return null;
    }
    
    /**
     * Auxiliary method to get sub node with given name
     * @param parent Parent node whose children elements will be reviewed
     * @param nodeName Node name
     * @return Required node
     */
    public Node getChildNodeByName(Node parent, String nodeName) {
        Node result = null;
        if (parent.getNodeType() == Node.ELEMENT_NODE && nodeName != null) {
            NodeList list = parent.getChildNodes();
            for (int i = 0; i < list.getLength(); i ++) {                
                if (nodeName.equals(list.item(i).getNodeName())) {
                    result = list.item(i);
                    break;
                }
            }
        }
        return result;
    }
    
    public class Key {
        public  String attrName;
        public  String attrType;
        public  String f;
        public  String id;
        public  String yfilesType;
        
        Key (String name, String type, String f, String yfilesType, String id) {
            this.attrName = name;
            this.attrType = type;
            this.f = f;
            this.id = id;
            this.yfilesType = yfilesType;
        }
    }
    
    private  String test = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
            + "<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:y=\"http://www.yworks.com/xml/graphml\" xmlns:yed=\"http://www.yworks.com/xml/yed/3\" xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns http://www.yworks.com/xml/schema/graphml/1.1/ygraphml.xsd\">\n"
            + "  <!--Created by yEd 3.14-->\n"
            + "  <key for=\"port\" id=\"d0\" yfiles.type=\"portgraphics\"/>\n"
            + "  <key for=\"port\" id=\"d1\" yfiles.type=\"portgeometry\"/>\n"
            + "  <key for=\"port\" id=\"d2\" yfiles.type=\"portuserdata\"/>\n"
            + "  <key attr.name=\"Бизнес-система\" attr.type=\"string\" for=\"node\" id=\"d3\"/>\n"
            + "  <key attr.name=\"Бизнес-система. Полное наименование\" attr.type=\"string\" for=\"node\" id=\"d4\"/>\n"
            + "  <key attr.name=\"�?нтеграционный сценарий\" attr.type=\"string\" for=\"node\" id=\"d5\"/>\n"
            + "  <key attr.name=\"Бизнес-решения\" attr.type=\"string\" for=\"node\" id=\"d6\"/>\n"
            + "  <key attr.name=\"�?дентификатор узла\" attr.type=\"string\" for=\"node\" id=\"d7\"/>\n"
            + "  <key attr.name=\"Платформа разработки\" attr.type=\"string\" for=\"node\" id=\"d8\"/>\n"
            + "  <key attr.name=\"Код платформы разработки\" attr.type=\"string\" for=\"node\" id=\"d9\"/>\n"
            + "  <key attr.name=\"Технические системы\" attr.type=\"string\" for=\"node\" id=\"d10\"/>\n"
            + "  <key attr.name=\"Системные модули\" attr.type=\"string\" for=\"node\" id=\"d11\"/>\n"
            + "  <key attr.name=\"url\" attr.type=\"string\" for=\"node\" id=\"d12\"/>\n"
            + "  <key attr.name=\"description\" attr.type=\"string\" for=\"node\" id=\"d13\"/>\n"
            + "  <key for=\"node\" id=\"d14\" yfiles.type=\"nodegraphics\"/>\n"
            + "  <key for=\"graphml\" id=\"d15\" yfiles.type=\"resources\"/>\n"
            + "  <key attr.name=\"Наименование системы\" attr.type=\"string\" for=\"edge\" id=\"d16\"/>\n"
            + "  <key attr.name=\"Пространство имен\" attr.type=\"string\" for=\"edge\" id=\"d17\"/>\n"
            + "  <key attr.name=\"�?зменен\" attr.type=\"string\" for=\"edge\" id=\"d18\"/>\n"
            + "  <key attr.name=\"Дата изменения\" attr.type=\"string\" for=\"edge\" id=\"d19\"/>\n"
            + "  <key attr.name=\"ObjectID\" attr.type=\"string\" for=\"edge\" id=\"d20\"/>\n"
            + "  <key attr.name=\"ObjectVersionID\" attr.type=\"string\" for=\"edge\" id=\"d21\"/>\n"
            + "  <key attr.name=\"R\" attr.type=\"string\" for=\"edge\" id=\"d22\"/>\n"
            + "  <key attr.name=\"S\" attr.type=\"string\" for=\"edge\" id=\"d23\"/>\n"
            + "  <key attr.name=\"Direction\" attr.type=\"string\" for=\"edge\" id=\"d24\"/>\n"
            + "  <key attr.name=\"url\" attr.type=\"string\" for=\"edge\" id=\"d25\"/>\n"
            + "  <key attr.name=\"description\" attr.type=\"string\" for=\"edge\" id=\"d26\"/>\n"
            + "  <key for=\"edge\" id=\"d27\" yfiles.type=\"edgegraphics\"/>\n"
            + "  <graph edgedefault=\"directed\" id=\"G\">\n"
            + "    <node id=\"n0\">\n"
            + "      <data key=\"d3\"/>\n"
            + "      <data key=\"d4\"/>\n"
            + "      <data key=\"d5\"/>\n"
            + "      <data key=\"d6\"/>\n"
            + "      <data key=\"d7\"/>\n"
            + "      <data key=\"d8\"/>\n"
            + "      <data key=\"d9\"/>\n"
            + "      <data key=\"d10\"/>\n"
            + "      <data key=\"d11\"/>\n"
            + "      <data key=\"d13\"/>\n"
            + "      <data key=\"d14\">\n"
            + "        <y:GenericNode configuration=\"com.yworks.bpmn.Activity.withShadow\">\n"
            + "          <y:Geometry height=\"174.0\" width=\"357.0\" x=\"79.0\" y=\"-227.5\"/>\n"
            + "          <y:Fill color=\"#FFFFFFE6\" color2=\"#D4D4D4CC\" transparent=\"false\"/>\n"
            + "          <y:BorderStyle color=\"#123EA2\" type=\"line\" width=\"1.0\"/>\n"
            + "          <y:NodeLabel alignment=\"center\" autoSizePolicy=\"content\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" hasText=\"false\" height=\"4.0\" modelName=\"custom\" textColor=\"#000000\" visible=\"true\" width=\"4.0\" x=\"176.5\" y=\"85.0\">\n"
            + "            <y:LabelModel>\n"
            + "              <y:SmartNodeLabelModel distance=\"4.0\"/>\n"
            + "            </y:LabelModel>\n"
            + "            <y:ModelParameter>\n"
            + "              <y:SmartNodeLabelModelParameter labelRatioX=\"0.0\" labelRatioY=\"0.0\" nodeRatioX=\"0.0\" nodeRatioY=\"0.0\" offsetX=\"0.0\" offsetY=\"0.0\" upX=\"0.0\" upY=\"-1.0\"/>\n"
            + "            </y:ModelParameter>\n"
            + "          </y:NodeLabel>\n"
            + "          <y:StyleProperties>\n"
            + "            <y:Property class=\"java.awt.Color\" name=\"com.yworks.bpmn.icon.line.color\" value=\"#000000\"/>\n"
            + "            <y:Property class=\"com.yworks.yfiles.bpmn.view.TaskTypeEnum\" name=\"com.yworks.bpmn.taskType\" value=\"TASK_TYPE_ABSTRACT\"/>\n"
            + "            <y:Property class=\"java.awt.Color\" name=\"com.yworks.bpmn.icon.fill2\" value=\"#d4d4d4cc\"/>\n"
            + "            <y:Property class=\"java.awt.Color\" name=\"com.yworks.bpmn.icon.fill\" value=\"#ffffffe6\"/>\n"
            + "            <y:Property class=\"com.yworks.yfiles.bpmn.view.BPMNTypeEnum\" name=\"com.yworks.bpmn.type\" value=\"ACTIVITY_TYPE\"/>\n"
            + "            <y:Property class=\"com.yworks.yfiles.bpmn.view.ActivityTypeEnum\" name=\"com.yworks.bpmn.activityType\" value=\"ACTIVITY_TYPE_TRANSACTION\"/>\n"
            + "          </y:StyleProperties>\n"
            + "        </y:GenericNode>\n"
            + "      </data>\n"
            + "    </node>\n"
            + "    <node id=\"n1\" yfiles.foldertype=\"group\">\n"
            + "      <data key=\"d3\"/>\n"
            + "      <data key=\"d4\"/>\n"
            + "      <data key=\"d5\"/>\n"
            + "      <data key=\"d6\"/>\n"
            + "      <data key=\"d7\"/>\n"
            + "      <data key=\"d8\"/>\n"
            + "      <data key=\"d9\"/>\n"
            + "      <data key=\"d10\"/>\n"
            + "      <data key=\"d11\"/>\n"
            + "      <data key=\"d12\"/>\n"
            + "      <data key=\"d13\"/>\n"
            + "      <data key=\"d14\">\n"
            + "        <y:ProxyAutoBoundsNode>\n"
            + "          <y:Realizers active=\"0\">\n"
            + "            <y:GroupNode>\n"
            + "              <y:Geometry height=\"235.0\" width=\"346.0\" x=\"4.99609375\" y=\"76.0\"/>\n"
            + "              <y:Fill color=\"#CAECFF80\" transparent=\"false\"/>\n"
            + "              <y:BorderStyle color=\"#666699\" type=\"dotted\" width=\"1.0\"/>\n"
            + "              <y:NodeLabel alignment=\"right\" autoSizePolicy=\"node_width\" backgroundColor=\"#99CCFF\" borderDistance=\"0.0\" fontFamily=\"Dialog\" fontSize=\"15\" fontStyle=\"plain\" hasLineColor=\"false\" height=\"22.37646484375\" modelName=\"internal\" modelPosition=\"t\" textColor=\"#000000\" visible=\"true\" width=\"346.0\" x=\"0.0\" y=\"0.0\">2</y:NodeLabel>\n"
            + "              <y:Shape type=\"roundrectangle\"/>\n"
            + "              <y:State closed=\"false\" closedHeight=\"50.0\" closedWidth=\"50.0\" innerGraphDisplayEnabled=\"false\"/>\n"
            + "              <y:Insets bottom=\"5\" bottomF=\"5.0\" left=\"5\" leftF=\"5.0\" right=\"5\" rightF=\"5.0\" top=\"5\" topF=\"5.0\"/>\n"
            + "              <y:BorderInsets bottom=\"75\" bottomF=\"75.31201171875\" left=\"60\" leftF=\"59.662109375\" right=\"112\" rightF=\"111.9921875\" top=\"16\" topF=\"16.0\"/>\n"
            + "            </y:GroupNode>\n"
            + "            <y:GroupNode>\n"
            + "              <y:Geometry height=\"180.0\" width=\"273.0\" x=\"189.0\" y=\"-61.0\"/>\n"
            + "              <y:Fill color=\"#CAECFF80\" transparent=\"false\"/>\n"
            + "              <y:BorderStyle color=\"#666699\" type=\"dotted\" width=\"1.0\"/>\n"
            + "              <y:NodeLabel alignment=\"right\" autoSizePolicy=\"node_width\" backgroundColor=\"#99CCFF\" borderDistance=\"0.0\" fontFamily=\"Dialog\" fontSize=\"15\" fontStyle=\"plain\" hasLineColor=\"false\" height=\"22.37646484375\" modelName=\"internal\" modelPosition=\"t\" textColor=\"#000000\" visible=\"true\" width=\"273.0\" x=\"0.0\" y=\"0.0\">2</y:NodeLabel>\n"
            + "              <y:Shape type=\"roundrectangle\"/>\n"
            + "              <y:State closed=\"true\" closedHeight=\"180.0\" closedWidth=\"273.0\" innerGraphDisplayEnabled=\"false\"/>\n"
            + "              <y:Insets bottom=\"5\" bottomF=\"5.0\" left=\"5\" leftF=\"5.0\" right=\"5\" rightF=\"5.0\" top=\"5\" topF=\"5.0\"/>\n"
            + "              <y:BorderInsets bottom=\"0\" bottomF=\"0.0\" left=\"0\" leftF=\"0.0\" right=\"0\" rightF=\"0.0\" top=\"0\" topF=\"0.0\"/>\n"
            + "            </y:GroupNode>\n"
            + "          </y:Realizers>\n"
            + "        </y:ProxyAutoBoundsNode>\n"
            + "      </data>\n"
            + "      <graph edgedefault=\"directed\" id=\"n1:\">\n"
            + "        <node id=\"n1::n0\">\n"
            + "          <data key=\"d3\"><![CDATA[ARIS]]></data>\n"
            + "          <data key=\"d4\"><![CDATA[ARIS IT Designer 7.2]]></data>\n"
            + "          <data key=\"d5\"><![CDATA[HMI_Metamap]]></data>\n"
            + "          <data key=\"d6\"><![CDATA[К�?С УК�?ТА,К�?С БА]]></data>\n"
            + "          <data key=\"d7\"><![CDATA[220204]]></data>\n"
            + "          <data key=\"d8\"><![CDATA[ARIS 7.2]]></data>\n"
            + "          <data key=\"d9\"><![CDATA[250020]]></data>\n"
            + "          <data key=\"d10\"/>\n"
            + "          <data key=\"d11\"/>\n"
            + "          <data key=\"d13\"/>\n"
            + "          <data key=\"d14\">\n"
            + "            <y:ShapeNode>\n"
            + "              <y:Geometry height=\"30.0\" width=\"30.0\" x=\"203.0\" y=\"176.37646484375\"/>\n"
            + "              <y:Fill color=\"#FFCC00\" transparent=\"false\"/>\n"
            + "              <y:BorderStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>\n"
            + "              <y:NodeLabel alignment=\"center\" autoSizePolicy=\"content\" borderDistance=\"0.0\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"18.701171875\" modelName=\"custom\" textColor=\"#000000\" visible=\"true\" width=\"32.0078125\" x=\"-1.00390625\" y=\"35.6103515625\">ARIS\n"
            + "<y:LabelModel>\n"
            + "                  <y:SmartNodeLabelModel distance=\"4.0\"/>\n"
            + "                </y:LabelModel>\n"
            + "                <y:ModelParameter>\n"
            + "                  <y:SmartNodeLabelModelParameter labelRatioX=\"0.0\" labelRatioY=\"-0.8\" nodeRatioX=\"0.0\" nodeRatioY=\"0.5\" offsetX=\"0.0\" offsetY=\"0.0\" upX=\"0.0\" upY=\"-1.0\"/>\n"
            + "                </y:ModelParameter>\n"
            + "              </y:NodeLabel>\n"
            + "              <y:Shape type=\"roundrectangle\"/>\n"
            + "            </y:ShapeNode>\n"
            + "          </data>\n"
            + "        </node>\n"
            + "        <node id=\"n1::n1\">\n"
            + "          <data key=\"d3\"><![CDATA[SAP PO]]></data>\n"
            + "          <data key=\"d4\"><![CDATA[SAP Process Orchestration]]></data>\n"
            + "          <data key=\"d5\"><![CDATA[HMI_Metamap]]></data>\n"
            + "          <data key=\"d6\"><![CDATA[КШД]]></data>\n"
            + "          <data key=\"d7\"><![CDATA[220263]]></data>\n"
            + "          <data key=\"d8\"><![CDATA[SAP NetWeaver Java]]></data>\n"
            + "          <data key=\"d9\"><![CDATA[250036]]></data>\n"
            + "          <data key=\"d10\"/>\n"
            + "          <data key=\"d11\"/>\n"
            + "          <data key=\"d13\"/>\n"
            + "          <data key=\"d14\">\n"
            + "            <y:ShapeNode>\n"
            + "              <y:Geometry height=\"30.0\" width=\"30.0\" x=\"79.0\" y=\"119.37646484375\"/>\n"
            + "              <y:Fill color=\"#FFCC00\" transparent=\"false\"/>\n"
            + "              <y:BorderStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>\n"
            + "              <y:NodeLabel alignment=\"center\" autoSizePolicy=\"content\" borderDistance=\"0.0\" fontFamily=\"Dialog\" fontSize=\"12\" fontStyle=\"plain\" hasBackgroundColor=\"false\" hasLineColor=\"false\" height=\"18.701171875\" modelName=\"custom\" textColor=\"#000000\" visible=\"true\" width=\"48.68359375\" x=\"-9.341796875\" y=\"35.6103515625\">SAP PO\n"
            + "<y:LabelModel>\n"
            + "                  <y:SmartNodeLabelModel distance=\"4.0\"/>\n"
            + "                </y:LabelModel>\n"
            + "                <y:ModelParameter>\n"
            + "                  <y:SmartNodeLabelModelParameter labelRatioX=\"0.0\" labelRatioY=\"-0.8\" nodeRatioX=\"0.0\" nodeRatioY=\"0.5\" offsetX=\"0.0\" offsetY=\"0.0\" upX=\"0.0\" upY=\"-1.0\"/>\n"
            + "                </y:ModelParameter>\n"
            + "              </y:NodeLabel>\n"
            + "              <y:Shape type=\"roundrectangle\"/>\n"
            + "            </y:ShapeNode>\n"
            + "          </data>\n"
            + "        </node>\n"
            + "      </graph>\n"
            + "    </node>\n"
            + "    <edge id=\"n1::e0\" source=\"n1::n1\" target=\"n1::n1\">\n"
            + "      <data key=\"d16\"><![CDATA[SOAP_Sender_HMI]]></data>\n"
            + "      <data key=\"d17\"/>\n"
            + "      <data key=\"d18\"><![CDATA[silantevao]]></data>\n"
            + "      <data key=\"d19\"><![CDATA[2014-11-20T13:21:32]]></data>\n"
            + "      <data key=\"d20\"><![CDATA[1393ac2e796037e9b5224cd7106eaa73]]></data>\n"
            + "      <data key=\"d21\"><![CDATA[da99bf46708511e4bb02000000811d52]]></data>\n"
            + "      <data key=\"d22\"/>\n"
            + "      <data key=\"d23\"><![CDATA[SAP PO]]></data>\n"
            + "      <data key=\"d24\"/>\n"
            + "      <data key=\"d26\"/>\n"
            + "      <data key=\"d27\">\n"
            + "        <y:PolyLineEdge>\n"
            + "          <y:Path sx=\"0.0\" sy=\"0.0\" tx=\"0.0\" ty=\"0.0\">\n"
            + "            <y:Point x=\"94.0\" y=\"94.37646484375\"/>\n"
            + "          </y:Path>\n"
            + "          <y:LineStyle color=\"#000000\" type=\"line\" width=\"1.0\"/>\n"
            + "          <y:Arrows source=\"none\" target=\"standard\"/>\n"
            + "          <y:BendStyle smoothed=\"false\"/>\n"
            + "        </y:PolyLineEdge>\n"
            + "      </data>\n"
            + "    </edge>\n"
            + "  </graph>\n"
            + "  <data key=\"d15\">\n"
            + "    <y:Resources/>\n"
            + "  </data>\n"
            + "</graphml>";
}
