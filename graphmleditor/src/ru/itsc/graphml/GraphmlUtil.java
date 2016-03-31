package ru.itsc.graphml;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GraphmlUtil {

    public static Element retrieveGraphMlHeader(Document document) {
        // root xml element
        Element graphmlElement = document.createElementNS("http://graphml.graphdrawing.org/xmlns", "graphml");
        document.appendChild(graphmlElement);
        Attr xmlns1 = document.createAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation");
        xmlns1.setPrefix("xsi");
        xmlns1.setValue("http://graphml.graphdrawing.org/xmlns http://www.yworks.com/xml/schema/graphml/1.1/ygraphml.xsd");
        Attr xmlns4 = document.createAttribute("xmlns:y");
        xmlns4.setValue("http://www.yworks.com/xml/graphml");
        Attr xmlns5 = document.createAttribute("xmlns:yed");
        xmlns5.setValue("http://www.yworks.com/xml/yed/3");
        graphmlElement.getAttributes().setNamedItem(xmlns1);//setAttributeNodeNS(xmlns1);
        graphmlElement.getAttributes().setNamedItem(xmlns4);
        graphmlElement.setAttributeNode(xmlns5);

        return graphmlElement;
    }

    public static Element createGraphElement(String attrIdValue, Document docInstance) {
        Element graph = docInstance.createElement("graph");
        graph.setAttribute("id", attrIdValue);
        graph.setAttribute("edgedefault", "directed");
        return graph;
    }

    public static Element createDataElement(String keyValue, Document docInstance) {
        Element data = docInstance.createElement("data");
        data.setAttribute("key", keyValue);
        return data;
    }

    public static Element createDataValueElement(String key, String value, Document docInstance) {
        Element elem = createDataElement(key, docInstance);
        elem.setTextContent(value);
        return elem;
    }

    public static Element createProxyAutoBoundsNodeElement(Document docInstance) {
        Element proxyAutoBounds = docInstance.createElement("y:ProxyAutoBoundsNode");
        return proxyAutoBounds;
    }

    public static Element createRealizersElement(String active, Document docInstance) {
        Element realizersElement = docInstance.createElement("y:Realizers");
        realizersElement.setAttribute("active", active);
        return realizersElement;
    }

    public static Element createGroupNode(Document docInstance) {
        Element groupNode = docInstance.createElement("y:GroupNode");
        return groupNode;
    }

    public static Element createGeometryElement(String width, String height, String x, String y, Document docInstance) {
        Element geometry = docInstance.createElement("y:Geometry");
        geometry.setAttribute("width", width);
        geometry.setAttribute("height", height);
        geometry.setAttribute("x", x);
        geometry.setAttribute("y", y);

        return geometry;
    }

    public static Element createFillElement(String color, String transparent, Document docInstance) {
        Element fillElement = docInstance.createElement("y:Fill");
        fillElement.setAttribute("color", color);
        fillElement.setAttribute("transparent", transparent);

        return fillElement;
    }
    
    public static Element createFillElement(Color color, String transparent, Document docInstance) {
        Element fillElement = docInstance.createElement("y:Fill");
        fillElement.setAttribute("color", colorToString(color));
        fillElement.setAttribute("transparent", transparent);

        return fillElement;
    }

    public static Element createBorderStyleElement(String color, String type, String width, Document docInstance) {
        Element borderStyleElement = docInstance.createElement("y:BorderStyle");
        borderStyleElement.setAttribute("color", color);
        borderStyleElement.setAttribute("type", type);
        borderStyleElement.setAttribute("width", width);

        return borderStyleElement;
    }

    public static Element createBorderStyleElement(Color color, String type, String width, Document docInstance) {
        Element borderStyleElement = docInstance.createElement("y:BorderStyle");
        borderStyleElement.setAttribute("color", colorToString(color));
        borderStyleElement.setAttribute("type", type);
        borderStyleElement.setAttribute("width", width);

        return borderStyleElement;
    }
    
    public static Element createNodeLabelElement(String alignment, String autoSizePolicy, String backgroundColor,
            String borderDistance, String fontFamily, String fontSize, String fontStyle, String hasBackgroundColor, String hasLineColor, String hasText,
            String height, String modelName, String modelPosition, String textColor, String visible, String width,
            String x, String y, Document docInstance) {

        Element nodeLabelElement = docInstance.createElement("y:NodeLabel");
        nodeLabelElement.setAttribute("alignment", alignment);
        nodeLabelElement.setAttribute("autoSizePolicy", autoSizePolicy);
        nodeLabelElement.setAttribute("backgroundColor", backgroundColor);
        nodeLabelElement.setAttribute("borderDistance", borderDistance);
        nodeLabelElement.setAttribute("fontFamily", fontFamily);
        nodeLabelElement.setAttribute("fontSize", fontSize);
        nodeLabelElement.setAttribute("fontStyle", fontStyle);
        nodeLabelElement.setAttribute("hasBackgroundColor", hasBackgroundColor);
        nodeLabelElement.setAttribute("hasLineColor", hasLineColor);
        nodeLabelElement.setAttribute("hasText", hasText);
        nodeLabelElement.setAttribute("height", height);
        nodeLabelElement.setAttribute("width", width);
        nodeLabelElement.setAttribute("modelName", modelName);
        nodeLabelElement.setAttribute("modelPosition", modelPosition);
        nodeLabelElement.setAttribute("textColor", textColor);
        nodeLabelElement.setAttribute("visible", visible);
        nodeLabelElement.setAttribute("x", x);
        nodeLabelElement.setAttribute("y", y);

        return nodeLabelElement;
    }

    public static Element createLabelModel(Document docInstance) {
        Element labelModel = docInstance.createElement("y:LabelModel");
        return labelModel;
    }

    public static Element createSmartNodeLabelModel(String distance, Document docInstance) {
        Element smartNodeLabelModel = docInstance.createElement("y:SmartNodeLabelModel");
        smartNodeLabelModel.setAttribute("distance", distance);
        return smartNodeLabelModel;
    }

    public static Element createModelParameter(Document docInstance) {
        Element modelParameter = docInstance.createElement("y:ModelParameter");
        return modelParameter;
    }

    public static Element createSmartNodeLabelModelParameter(String labelRatioX, String labelRatioY, String nodeRatioX, String nodeRatioY, String offsetX, String offsetY, String upX, String upY, Document docInstance) {
        Element modelParameter = docInstance.createElement("y:SmartNodeLabelModelParameter");
        modelParameter.setAttribute("labelRatioX", labelRatioX);
        modelParameter.setAttribute("labelRatioY", labelRatioY);
        modelParameter.setAttribute("nodeRatioX", nodeRatioX);
        modelParameter.setAttribute("nodeRatioY", nodeRatioY);
        modelParameter.setAttribute("offsetX", offsetX);
        modelParameter.setAttribute("offsetY", offsetY);
        modelParameter.setAttribute("upX", upX);
        modelParameter.setAttribute("upY", upY);
        return modelParameter;
    }

    public static Element createShapeElement(String type, Document docInstance) {
        Element shapeElement = docInstance.createElement("y:Shape");
        shapeElement.setAttribute("type", type);

        return shapeElement;
    }

    public static Element createShapeNodeElement(Document docInstance) {
        Element shapeElement = docInstance.createElement("y:ShapeNode");
        return shapeElement;
    }

    public static Element createStateElement(String closed, String closedHeight, String closedWidth, String innerGraphDisplayEnabled, Document docInstance) {
        Element stateElement = docInstance.createElement("y:State");
        stateElement.setAttribute("closed", closed);
        stateElement.setAttribute("closedHeight", closedHeight);
        stateElement.setAttribute("closedWidth", closedWidth);
        stateElement.setAttribute("innerGraphDisplayEnabled", innerGraphDisplayEnabled);
        return stateElement;
    }

    public static Element createInsetsElement(String bottom, String bottomF, String left, String leftF, String right, String rightF,
            String top, String topF, Document docInstance) {
        Element insetsElement = docInstance.createElement("y:Insets");
        insetsElement.setAttribute("bottom", bottom);
        insetsElement.setAttribute("bottomF", bottomF);
        insetsElement.setAttribute("left", left);
        insetsElement.setAttribute("leftF", leftF);
        insetsElement.setAttribute("right", right);
        insetsElement.setAttribute("rightF", rightF);
        insetsElement.setAttribute("top", top);
        insetsElement.setAttribute("topF", topF);
        return insetsElement;
    }

    public static Element createBorderInsets(String bottom, String bottomF, String left, String leftF, String right,
            String rightF, String top, String topF, Document docInstance) {
        Element borderInsets = docInstance.createElement("y:BorderInsets");
        borderInsets.setAttribute("bottom", bottom);
        borderInsets.setAttribute("bottomF", bottomF);
        borderInsets.setAttribute("left", left);
        borderInsets.setAttribute("leftF", leftF);
        borderInsets.setAttribute("right", right);
        borderInsets.setAttribute("rightF", rightF);
        borderInsets.setAttribute("top", top);
        borderInsets.setAttribute("topF", topF);
        return borderInsets;
    }

    public static Element createNodeElement(String attrIdValue, Document docInstance) {
        Element node = docInstance.createElement("node");
        Attr attr = docInstance.createAttribute("id");
        attr.setValue(attrIdValue);
        node.setAttributeNode(attr);

        return node;
    }

    public static Element createEdgeElement(String id, String source, String target, Document docInstance) {
        Element edgeEl = docInstance.createElement("edge");
        edgeEl.setAttribute("id", id);
        edgeEl.setAttribute("source", source);
        edgeEl.setAttribute("target", target);
        return edgeEl;
    }

    public static Element createPolyLineEdgeElement(Document docInstance) {
        Element elem = docInstance.createElement("y:PolyLineEdge");
        
        return elem;
    }
    
    public static Element createPathElement(double sx, double sy, double tx, double ty, Document docInstance) {
        Element path = docInstance.createElement("y:Path");
        path.setAttribute("sx", Double.toString(sx));
        path.setAttribute("sy", Double.toString(sy));
        path.setAttribute("tx", Double.toString(tx));
        path.setAttribute("ty", Double.toString(ty));
        
        return path;
    }
    
    public static Element createPointElement(Point2D point2D, Document docInstance) {
        Element point = docInstance.createElement("y:Point");
        point.setAttribute("x", Double.toString(point2D.getX()));
        point.setAttribute("y", Double.toString(point2D.getY()));
        
        return point;
    }
    
    public static Element createLineStyleElement(Color color, String type, Double width, Document docInstance) {
        Element lineStyleElement = docInstance.createElement("y:LineStyle");
        lineStyleElement.setAttribute("color", colorToString(color));
        lineStyleElement.setAttribute("type", type);
        lineStyleElement.setAttribute("width", Double.toString(width));
        
        return lineStyleElement;
    }
    
    public static Element createArrowElement(String source, String target, Document docInstance) {
        Element arrow = docInstance.createElement("y:Arrows");
        arrow.setAttribute("source", source);
        arrow.setAttribute("target", target);
        
        return arrow;
    }
    
    public static Element createBendStyleElement(String smoothed, Document docInstance) {
        Element bendStyle = docInstance.createElement("y:BendStyle");
        bendStyle.setAttribute("smoothed", smoothed);
        
        return bendStyle;
    }
    
    public static Element createNodeKeyElement(String id, String attributeName, Document docInstance) {
        Element bsElement = createKeyElementForNode(id, docInstance);
        bsElement.setAttribute("attr.name", attributeName);
        bsElement.setAttribute("attr.type", "string");
        return bsElement;
    }

    public static Element createEdgeKeyElement(String id, String attributeName, Document docInstance) {
        Element bsElement = createKeyElementForEdge(id, docInstance);
        bsElement.setAttribute("attr.name", attributeName);
        bsElement.setAttribute("attr.type", "string");
        return bsElement;
    }

    public static Element createKeyElementForNode(String id, Document docInstance) {
        Element result = docInstance.createElement("key");
        result.setAttribute("for", "node");
        result.setAttribute("id", id);
        return result;
    }

    public static Element createKeyElementForEdge(String id, Document docInstance) {
        Element result = docInstance.createElement("key");
        result.setAttribute("for", "edge");
        result.setAttribute("id", id);
        return result;
    }
    
    public static String colorToString(Color color) {
        int r = (int)Math.round(color.getRed() * 255.0);
        int g = (int)Math.round(color.getGreen() * 255.0);
        int b = (int)Math.round(color.getBlue() * 255.0);
        return String.format("#%02x%02x%02x" , r, g, b);
    }
}
