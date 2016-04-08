/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.itsc.graphml;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.itsc.graphmleditor.layout.AbstractNode;
import ru.itsc.graphmleditor.layout.CNode;
import ru.itsc.graphmleditor.layout.Edge;
import ru.itsc.graphmleditor.layout.EdgePoint;
import ru.itsc.graphmleditor.layout.PNode;

/**
 *
 * @author macbookmacbook
 */
public class GraphMLGenerator {

    private static Document document;
    
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
    
    private static final String BUSINESS_SYSTEM_NAME_ID = "id0";
    private static final String BUSINESS_SYSTEM_FULLNAME_ID = "id1";
    private static final String INTEGRATION_SCENARIO_ID = "id2";
    private static final String NODE_GRAPHICS_ELEM_ID = "id3";
    private static final String BUSINESS_SOLUTIONS_ID = "id4";
    private static final String INTERNAL_ID = "id5"; // node internal id 
    private static final String PLATFORM_NAME_ID = "id6";
    private static final String PLATFORM_CODE_ID = "id7";
    private static final String TECHNICAL_SYSTEM_ID = "id8";
    private static final String MODULE_NAMES_ID = "id9";
    private static final String DIRECTION = "id10";
    private static final String EDGE_NAME = "id11";
    private static final String EDGE_NAMESPACE = "id12";
    private static final String EDGE_OBJECTID = "id13";
    private static final String EDGE_OBJECTVERSION_ID = "id14";
    private static final String EDGE_CHANGEDON = "id15";
    private static final String EDGE_CHANGEDBY = "id16";
    private static final String EDGE_RECIEVER = "id17";
    private static final String EDGE_SENDER = "id18";
    private static final String EDGE_GRAPHICS_ELEM_ID = "id19";
    
    public static String getGraphMl(Group group) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        document = documentBuilder.newDocument();
        Element graphMLElem = GraphmlUtil.retrieveGraphMlHeader(document);
        List<Element> keys = createKeyElements(document);
        for (Element element : keys) {
            graphMLElem.appendChild(element);
        }
        Element graphElement = GraphmlUtil.createGraphElement(ROOT_GRAPH, document);
        graphMLElem.appendChild(graphElement);
        
        group.getChildren().forEach(node -> {
            if (node instanceof AbstractNode) {
                Element childNode = createNodeElement((AbstractNode)node);
                graphElement.appendChild(childNode);
            } else if (node instanceof Edge) {
                Edge edge = (Edge)node;
                Element edgeElem = createEdgeElement(edge, document);
                graphElement.appendChild(edgeElem);
            }
        });
        return getStringFromDocument(document);
    }
    
    /**
     * Create a node depending on its type (folder node or leaf node) 
     * @param Element 
     */
    private static Element createNodeElement(AbstractNode node) {
        Element result = null;
        
        if (node instanceof CNode) {
            result = createLeafNodeElement((CNode)node);
        } else if (node instanceof PNode) {
            result = createFolderNodeElement((PNode)node);
        }
        
        return result;
    }
    
    /**
     * Creating a child leaf node 
     * @param node
     * @return Element node
     */
    private static Element createLeafNodeElement(CNode node) {
        
        return createNodeElement(node);
    } 
    
    /**
     * Creating a folder node that may contain child elements
     * 
     * @param node
     * @return Element
     */
    private static Element createFolderNodeElement(PNode node) {
        Element folderNode = createNodeElement(node);
        NodeList nl = folderNode.getElementsByTagName(GRAPH_ELEMENT);
        Node graph = nl.item(0);
        
        // iterating children nodes & invoking createNodeElement() method
        node.getContentPane().getChildren().forEach(elem -> {
            if (elem instanceof AbstractNode) {
                Element childNodeElem = createNodeElement((AbstractNode)elem);
                graph.appendChild(childNodeElem);
            } else if (elem instanceof Edge) {
                Edge edge = (Edge)elem;
                Element edgeElem = createEdgeElement(edge, document);
                graph.appendChild(edgeElem);
            }
        });
        
        return folderNode;
    }
    
    /**
     * Creating a GraphML group-node element 
     * @param parent node 
     * @return Element
     */
    private static Element createNodeElement(PNode node) {
        Element folderNode = GraphmlUtil.createNodeElement(node.getId(), document);
        
        folderNode.setAttribute("yfiles.foldertype", "group");
        Element bsName = GraphmlUtil.createDataValueElement(BUSINESS_SYSTEM_NAME_ID, node.labelProperty.getValue(), document);
        folderNode.appendChild(bsName);
        Element dataNodegraphics = GraphmlUtil.createDataElement(NODE_GRAPHICS_ELEM_ID, document);
        Element proxyAuto = GraphmlUtil.createProxyAutoBoundsNodeElement(document);
        dataNodegraphics.appendChild(proxyAuto);
        String activeState = node.collapseState.get() ? "0" : "1";
        Element realizers = GraphmlUtil.createRealizersElement(activeState, document);
        proxyAuto.appendChild(realizers);
        Element groupNode = GraphmlUtil.createGroupNode(document);
        realizers.appendChild(groupNode);
        String nodeWidth = Double.toString(node.widthProperty.get());
        String nodeHeight = Double.toString(node.heightProperty.get());
        String nodeX = Double.toString(node.getTranslateX());
        String nodeY = Double.toString(node.getTranslateY());
        Element geometry = GraphmlUtil.createGeometryElement( nodeWidth, 
                nodeHeight, 
                nodeX, 
                nodeY, document );
        
        groupNode.appendChild(geometry);
        Element fill = GraphmlUtil.createFillElement(node.fillColorProperty.getValue(), "false", document);
        groupNode.appendChild(fill);
        Element borderStyle = GraphmlUtil.createBorderStyleElement(node.borderColorProperty.getValue(), "line", "1.0", document);
        groupNode.appendChild(borderStyle);
        Element nodeLabel = GraphmlUtil.createNodeLabelElement("right", "node_width", "#404040", "0.0", 
                "Dialog", "16", "plain", 
                "true", "false", "true", "24", "internal", "t", "#FFFFFF", "true", "353", "0.0", "0.0", document);
        nodeLabel.setTextContent(node.labelProperty.getValue());
        groupNode.appendChild(nodeLabel);
        Element shape = GraphmlUtil.createShapeElement("rectangle3d", document);
        groupNode.appendChild(shape);
        Element state = GraphmlUtil.createStateElement("false", "80.0", "100.0", "false", document);
        groupNode.appendChild(state);
        Element insetsElement = GraphmlUtil.createInsetsElement("15", "15.0", "15", "15.0", "15", "15.0", "15", "15.0", document);
        groupNode.appendChild(insetsElement);
        Element borderInsets = GraphmlUtil.createBorderInsets("45", "45.0", "55", "55.0", "135", "135.0", "10", "10.0", document);
        groupNode.appendChild(borderInsets);

        // closed group
        Element groupNode1 = GraphmlUtil.createGroupNode(document);
        realizers.appendChild(groupNode1);
        // closed size
        String closeHeight = "80.0";
        String closeWidth = "100.0";
        geometry = GraphmlUtil.createGeometryElement(
                closeWidth,
                closeHeight,
                nodeX, 
                nodeY, document);
        
        groupNode1.appendChild(geometry);
        fill = GraphmlUtil.createFillElement(node.fillColorProperty.getValue(), "false", document);
        groupNode1.appendChild(fill);
        borderStyle = GraphmlUtil.createBorderStyleElement(node.borderColorProperty.getValue(), "line", "1.0", document);
        groupNode1.appendChild(borderStyle);
        nodeLabel = GraphmlUtil.createNodeLabelElement("right", "node_width", "#404040", "0.0", "Dialog", "16",
                "plain", "true", "false", "true", "4", "internal", "t", "#FFFFFF", "true", "100", "0.0", "0.0", document);
        nodeLabel.setTextContent(node.labelProperty.getValue());
        groupNode1.appendChild(nodeLabel);
        shape = GraphmlUtil.createShapeElement("rectangle3d", document);
        groupNode1.appendChild(shape);
        state = GraphmlUtil.createStateElement("true", closeHeight, closeWidth, "false", document);
        groupNode1.appendChild(state);
        insetsElement = GraphmlUtil.createInsetsElement("15", "15.0", "15", "15.0", "15", "15.0", "15", "15.0", document);
        groupNode1.appendChild(insetsElement);
        borderInsets = GraphmlUtil.createBorderInsets("0", "0.0", "0", "0.0", "0", "0.0", "0", "0.0", document);
        groupNode1.appendChild(borderInsets);

        folderNode.appendChild(dataNodegraphics);
        Element subGraphElement = GraphmlUtil.createGraphElement(node.getId() + "::", document);
        folderNode.appendChild(subGraphElement);
        
        return folderNode;
    }
    
    /**
     * Creating a GraphML child node element
     * 
     * @param node
     * @return Element
     */
    private static Element createNodeElement(CNode node) {

        Graph.GraphNode graphNode = node.getGraphNode();
        Element nodeElem = GraphmlUtil.createNodeElement(node.getId(), document);

        Element directionDataElem = GraphmlUtil.createDataValueElement(DIRECTION, graphNode.getData().get(DIRECTION), document);
        nodeElem.appendChild(directionDataElem);
        Element bsDataElem = GraphmlUtil.createDataValueElement(BUSINESS_SYSTEM_NAME_ID, graphNode.getDataValue(BUSINESS_SYSTEM_NAME_ID), document);
        nodeElem.appendChild(bsDataElem);
        Element bsFullName = GraphmlUtil.createDataValueElement(BUSINESS_SYSTEM_FULLNAME_ID, graphNode.getDataValue(BUSINESS_SYSTEM_FULLNAME_ID), document);
        nodeElem.appendChild(bsFullName);
        StringBuilder solutions = new StringBuilder();

        Element bSolutions = GraphmlUtil.createDataElement(BUSINESS_SOLUTIONS_ID, document);
        bSolutions.setTextContent(graphNode.getDataValue(BUSINESS_SOLUTIONS_ID));
        nodeElem.appendChild(bSolutions);
        Element platformName = GraphmlUtil.createDataValueElement(PLATFORM_NAME_ID, graphNode.getDataValue(PLATFORM_NAME_ID), document);
        nodeElem.appendChild(platformName);
        Element platformCode = GraphmlUtil.createDataValueElement(PLATFORM_CODE_ID, graphNode.getDataValue(PLATFORM_CODE_ID), document);
        nodeElem.appendChild(platformCode);
        
        Element technicalSystem = GraphmlUtil.createDataElement(TECHNICAL_SYSTEM_ID, document);
        technicalSystem.setTextContent(graphNode.getDataValue(TECHNICAL_SYSTEM_ID));
        
        nodeElem.appendChild(technicalSystem);
        Element systemModules = GraphmlUtil.createDataElement(MODULE_NAMES_ID, document);
        systemModules.setTextContent(graphNode.getDataValue(MODULE_NAMES_ID));
        
        nodeElem.appendChild(systemModules);
        Element graphicsElement = GraphmlUtil.createDataElement(NODE_GRAPHICS_ELEM_ID, document);
        Element shapeNode = creteShapeNodeElement(node, document);
        graphicsElement.appendChild(shapeNode);
        nodeElem.appendChild(graphicsElement);
        Element scenarioDataElem = GraphmlUtil.createDataValueElement(INTEGRATION_SCENARIO_ID, graphNode.getDataValue(INTEGRATION_SCENARIO_ID), document);
        nodeElem.appendChild(scenarioDataElem);
        Element internalId = GraphmlUtil.createDataValueElement(INTERNAL_ID, node.getId(), document);
        nodeElem.appendChild(internalId);

        return nodeElem;
    }
    
    	/**
	 * Create key elements at the header for nodes and edges
	 * @param document DOM object
	 * @return List of key elements
	 */
    private static List<Element> createKeyElements(Document document) {
    	List<Element> result = new ArrayList();
    	
    	result.add(GraphmlUtil.createNodeKeyElement(BUSINESS_SYSTEM_NAME_ID, "Бизнес-система", document));
    	result.add(GraphmlUtil.createNodeKeyElement(BUSINESS_SYSTEM_FULLNAME_ID, "Бизнес-система. Полное наименование", document));
    	result.add(GraphmlUtil.createNodeKeyElement(INTEGRATION_SCENARIO_ID, "�?нтеграционный сценарий", document));
    	
    	Element nodeGraphicsElem = GraphmlUtil.createKeyElementForNode(NODE_GRAPHICS_ELEM_ID, document);
    	nodeGraphicsElem.setAttribute(YFILES_TYPE, NODE_GRAPHICS);
    	Element edgeGraphicsElem = GraphmlUtil.createKeyElementForEdge(EDGE_GRAPHICS_ELEM_ID, document);
    	edgeGraphicsElem.setAttribute(YFILES_TYPE, EDGE_GRAPHICS);
    	result.add(nodeGraphicsElem);
    	result.add(edgeGraphicsElem);
    	
    	result.add(GraphmlUtil.createNodeKeyElement(BUSINESS_SOLUTIONS_ID, "Бизнес-решения", document));
    	result.add(GraphmlUtil.createNodeKeyElement(INTERNAL_ID, "�?дентификатор узла", document));
    	result.add(GraphmlUtil.createNodeKeyElement(PLATFORM_NAME_ID, "Платформа разработки", document));
    	result.add(GraphmlUtil.createNodeKeyElement(PLATFORM_CODE_ID, "Код платформы разработки", document));
    	result.add(GraphmlUtil.createNodeKeyElement(TECHNICAL_SYSTEM_ID, "Технические системы", document));
    	result.add(GraphmlUtil.createNodeKeyElement(MODULE_NAMES_ID, "Системные модули", document));
    	result.add(GraphmlUtil.createEdgeKeyElement(EDGE_NAME, "Наименование системы", document));
    	result.add(GraphmlUtil.createEdgeKeyElement(EDGE_NAMESPACE, "Пространство имен", document));
    	result.add(GraphmlUtil.createEdgeKeyElement(EDGE_CHANGEDBY, "�?зменен", document));
    	result.add(GraphmlUtil.createEdgeKeyElement(EDGE_CHANGEDON, "Дата изменения", document));
    	result.add(GraphmlUtil.createEdgeKeyElement(EDGE_OBJECTID, "ObjectID", document));
    	result.add(GraphmlUtil.createEdgeKeyElement(EDGE_OBJECTVERSION_ID, "ObjectVersionID", document));
    	result.add(GraphmlUtil.createEdgeKeyElement(EDGE_RECIEVER, "R", document));
    	result.add(GraphmlUtil.createEdgeKeyElement(EDGE_SENDER, "S", document));
    	result.add(GraphmlUtil.createEdgeKeyElement(DIRECTION, "Direction", document));
    	
    	return result;
    }
    
    /**
     * Create graphics element for node. It assumes customization of node
     * layout, label element.. etc.
     *
     * @param nodeCaption
     * @param docInstance
     * @return
     */
    private static Element creteShapeNodeElement(CNode node, Document docInstance) {
    	Element shapeNode = GraphmlUtil.createShapeNodeElement(docInstance);
        String nodeWidth = Double.toString(node.getNode().getWidth());
        String nodeHeight = Double.toString(node.getNode().getHeight());
        String nodeX = Double.toString(node.getSceneX());
        String nodeY = Double.toString(node.getSceneY());
    	Element geometry = GraphmlUtil.createGeometryElement( 
                nodeWidth, 
                nodeHeight, 
                nodeX, 
                nodeY, docInstance );
    	shapeNode.appendChild(geometry);
        
        String nodeColor = node.getNode().getStyle();
    	Element fill = GraphmlUtil.createFillElement(nodeColor, "false", docInstance);
    	shapeNode.appendChild(fill);
        
    	Element borderStyle = GraphmlUtil.createBorderStyleElement(nodeColor, "line", "1.0", docInstance);
    	shapeNode.appendChild(borderStyle);
        
        String fontFamily = node.getLabel().getFont().getFamily();
        String fontStyle = node.getLabel().getFont().getStyle();
        String fontSize = Double.toString(node.getLabel().getFont().getSize());
        String textColor = GraphmlUtil.colorToString((Color)node.getLabel().getFill());
        String textVisible = node.getLabel().isVisible() ? "true" : "false";
        String textWidth = Double.toString(node.getLabel().getBoundsInLocal().getWidth());
        String textHeight = Double.toString(node.getLabel().getBoundsInLocal().getHeight());
        String textX = Double.toString(node.getLabel().getTranslateX());
        String textY = Double.toString(node.getLabel().getTranslateY());
    	Element nodeLabel = GraphmlUtil.createNodeLabelElement( 
                "center", "content", nodeColor, "0", fontFamily, fontSize, 
                fontStyle, "false", "false", "true", textHeight, 
                "custom", "x", textColor, textVisible, textWidth, 
                textX, 
                textY, docInstance );
    	shapeNode.appendChild(nodeLabel);        
    	nodeLabel.appendChild(docInstance.createCDATASection(node.getLabel().getText()));
        
    	Element labelModel = GraphmlUtil.createLabelModel(docInstance);
    	nodeLabel.appendChild(labelModel);
        
    	Element smartNodeLabelModel = GraphmlUtil.createSmartNodeLabelModel("4.0", docInstance);
    	labelModel.appendChild(smartNodeLabelModel);
        
    	Element modelParameter = GraphmlUtil.createModelParameter(docInstance);
        // SmartNodeLabelModelParameter is not using now and it has constant values
    	Element smartNodeLabelModelParameter = GraphmlUtil.createSmartNodeLabelModelParameter("0.0", "-0.8", "0.0", "0.5", "0.0", "0.0", "0.0", "-1.0", docInstance);
    	modelParameter.appendChild(smartNodeLabelModelParameter);
        
    	nodeLabel.appendChild(modelParameter);
    	Element shape = GraphmlUtil.createShapeElement("roundrectangle", docInstance);
    	shapeNode.appendChild(shape);
    	
    	return shapeNode;
    }

    private static String getStringFromDocument(Document document) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        DOMSource source = new DOMSource(document);
        StringWriter stringWriter = new StringWriter();
        transformer.transform(source, new StreamResult(stringWriter));
        
        return stringWriter.getBuffer().toString();
    }
    
    /**
     * Create edge from source node to target node
     * 
     * @param edgeEnty entry of edge object
     * @param graphElement graph in which new edges will be placed
     * @param docInstance DOM object
     */
    private static Element createEdgeElement(Edge edge, Document docInstance) {
        Graph.GraphEdge graphEdge = edge.getGraphEdge();
    	Element edgeElement = GraphmlUtil.createEdgeElement(edge.getId(), edge.getSource().getId(), edge.getTarget().getId(), docInstance);	
    	edgeElement.appendChild(GraphmlUtil.createDataValueElement(EDGE_NAME, graphEdge.getDataValue(EDGE_NAME), docInstance));
    	edgeElement.appendChild(GraphmlUtil.createDataValueElement(EDGE_NAMESPACE, graphEdge.getDataValue(EDGE_NAMESPACE), docInstance));
    	edgeElement.appendChild(GraphmlUtil.createDataValueElement(EDGE_CHANGEDBY, graphEdge.getDataValue(EDGE_CHANGEDBY), docInstance));
    	edgeElement.appendChild(GraphmlUtil.createDataValueElement(EDGE_CHANGEDON, graphEdge.getDataValue(EDGE_CHANGEDON), docInstance));
    	edgeElement.appendChild(GraphmlUtil.createDataValueElement(EDGE_OBJECTID, graphEdge.getDataValue(EDGE_OBJECTID), docInstance));
    	edgeElement.appendChild(GraphmlUtil.createDataValueElement(EDGE_OBJECTVERSION_ID, graphEdge.getDataValue(EDGE_OBJECTVERSION_ID), docInstance));
    	edgeElement.appendChild(GraphmlUtil.createDataValueElement(EDGE_RECIEVER, graphEdge.getDataValue(EDGE_RECIEVER), docInstance));
    	edgeElement.appendChild(GraphmlUtil.createDataValueElement(EDGE_SENDER, graphEdge.getDataValue(EDGE_SENDER), docInstance));
    	Element dataEdgeGraphics = GraphmlUtil.createDataElement(EDGE_GRAPHICS_ELEM_ID, docInstance);
    	dataEdgeGraphics.appendChild(createEdgeGraphicsElement(edge, docInstance));
    	edgeElement.appendChild(dataEdgeGraphics);
    	
        return edgeElement;
    }
    
    /**
     * Create <y:PolyLineEdge> element to set up line settings
     * @param edge
     * @param docInstance
     * @return
     */
    private static Element createEdgeGraphicsElement(Edge edge, Document docInstance) {
        Element polyLineEdge = GraphmlUtil.createPolyLineEdgeElement(docInstance);
        Element pathElement = GraphmlUtil.createPathElement(edge.getIdentSx(), edge.getScaleY(), edge.getIdentTx(), edge.getIdentTy(), docInstance);
        
        for (EdgePoint edgePoint: edge.getEdgePoints()) {
            Element pathElem = GraphmlUtil.createPointElement(edgePoint.toPoint2D(), docInstance);
            pathElement.appendChild(pathElem);
        }
        
        polyLineEdge.appendChild(pathElement);
        Color lineColor = (Color)edge.getPath().getStroke();
        Element lineStyleElement = GraphmlUtil.createLineStyleElement(lineColor, "line", 1.0, docInstance);
        polyLineEdge.appendChild(lineStyleElement);
        Element arrowElement = GraphmlUtil.createArrowElement("none", "standard", docInstance);
        polyLineEdge.appendChild(arrowElement);
        Element bendElement = GraphmlUtil.createBendStyleElement(edge.getPath().isSmooth(), docInstance);
        polyLineEdge.appendChild(bendElement);
        
        return polyLineEdge;
    }
}
