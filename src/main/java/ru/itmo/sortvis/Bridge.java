package ru.itmo.sortvis;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Bridge implements Runnable {
    static Graph graph;
    static GraphModel oldGraph;
    static private int vertexCount;
    static private Map<String, Integer> edges = new HashMap<>();

    public Bridge(GraphModel graphModel) {
        oldGraph = graphModel;
    }

    public static Graph convertGraph() {

        graph = new SingleGraph("Simple Graph");
        vertexCount = oldGraph.getVertexCount();

        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");

        File cssFile = new File("src/main/resources/type.css");
        String cssFileFullPath = cssFile.getAbsolutePath();

        graph.addAttribute("ui.stylesheet", "url('file://" + cssFileFullPath + "')");

        for (int i = 0; i < vertexCount; i++) {
            graph.addNode(Integer.toString(i)).addAttribute("ui.label", "Node " + Integer.toString(i));
        }

        for (int i = 0; i < vertexCount; i++) {
            for (int j = i + 1; j < vertexCount; j++) {
                if (oldGraph.getEdge(i, j) > 0) {
                    graph.addEdge(Integer.toString(i) + Integer.toString(j), Integer.toString(i), Integer.toString(j));
                    edges.put(Integer.toString(i) + Integer.toString(j), oldGraph.getEdge(i, j));
                }
            }
        }


        graph.display();

        return graph;
    }

    // Ниже я определяю цвета ребер и вершин.
    // Крашу их в какой-то цвет, когда иду вперед/назад по ребру, вхожу в вершину или выхожу из нее
    // Используются css файлы из папки resources
    // Пока вызываю эти методы из класса GraphWalker

    public static void edgeForward(int i, int j) {
        if (graph.getEdge(Integer.toString(i) + Integer.toString(j)) != null)
            graph.getEdge(Integer.toString(i) + Integer.toString(j)).addAttribute("ui.class", "forward");
        else graph.getEdge(Integer.toString(j) + Integer.toString(i)).addAttribute("ui.class", "forward");
    }

    public static void edgeBack(int i, int j) {
        if (graph.getEdge(Integer.toString(i) + Integer.toString(j)) != null)
            graph.getEdge(Integer.toString(i) + Integer.toString(j)).addAttribute("ui.class", "back");
        else graph.getEdge(Integer.toString(j) + Integer.toString(i)).addAttribute("ui.class", "back");
    }

    public static void nodeIn(String node) {
        graph.getNode(node).addAttribute("ui.class", "in");
    }

    public static void nodeOut(String node) {
        graph.getNode(node).addAttribute("ui.class", "out");
    }

    public static void initNodesData() {
        for (Node node : graph) {
            node.removeAttribute("ui.label");
            node.addAttribute("ui.label", node.getId() + " / dist: inf");
        }
    }

    public static void initEdgesWeight() {
        for (Edge edge : graph.getEachEdge()) {
            edge.addAttribute("ui.label", "weight: " + edges.get(edge.getId()));
        }
    }

    public static void relaxEdge(int i, int j) {
        if (graph.getEdge(Integer.toString(i) + Integer.toString(j)) != null)
            graph.getEdge(Integer.toString(i) + Integer.toString(j)).addAttribute("ui.label", "weight: 1");
        else graph.getEdge(Integer.toString(j) + Integer.toString(i)).addAttribute("ui.label", "weight: 1");
    }

    @Override
    public void run() {
        convertGraph();
    }
}
