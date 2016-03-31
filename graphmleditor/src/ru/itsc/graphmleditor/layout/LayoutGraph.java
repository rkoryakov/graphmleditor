/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.itsc.graphmleditor.layout;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Parent;
import ru.itsc.graphml.Graph;
import ru.itsc.graphml.Graph.GraphNode;

/**
 * Graphical representation of ru.itsk.graphml.Graph structure
 * 
 * @author koryakov.rv
 */
public class LayoutGraph {
    private static Graph _graph = null;
    private static Group _group = null;
    private static List<Edge> _edges = new ArrayList();
    
    public static void retrieveGraph(Group group, Graph graph) {
        _graph = graph;
        _group = group;
        _group.getChildren().clear();
        buildNodes(_group, _graph);
        buildEdges(_group, _graph);
    }
    
    public static void buildNodes(Parent pNode, Graph g) {
        LinkedHashMap<String, GraphNode> nodeMap = g.getGraphNodes();
        
        // visualization of tree nodes  
        // put the nodes into their places according to their relation to each other and their coords
        nodeMap.forEach((id, node) -> {
            if (node.isGroup()) {
                PNode nextNode = new PNode(node);
                
                if (pNode instanceof PNode) {
                    ((PNode)pNode).addChild(nextNode);
                    //((PNode)pNode).getContentPane().getChildren().add(nextNode);
                } else {
                    ((Group)pNode).getChildren().add(nextNode);
                }
                
                buildNodes(nextNode, node.getSubGraph());
                
            } else if (node.getShapeNode() != null) {
                if (pNode instanceof PNode) {
                   PNode pN = (PNode)pNode;
                   pN.addChild(new CNode(node, pN));
                   //pN.getContentPane().getChildren().add(new CNode(node, pN)); 
                } else {
                    ((Group) pNode).getChildren().add(new CNode(node));
                }
            }
        });
    }
    
    public static void buildEdges(Group group, Graph g) {
        List<Graph.GraphEdge> edges = g.getGraphEdges();
        edges.forEach((e) -> {
            Edge edge = new Edge(e);
            group.getChildren().add(edge);
            _edges.add(edge);
        });
    }
    
    public static List<Edge> getEdges() {
        return _edges;
    }
}
