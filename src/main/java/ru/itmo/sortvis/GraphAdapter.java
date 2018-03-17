package ru.itmo.sortvis;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import ru.itmo.sortvis.ui.DisplayGraph;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GraphAdapter implements Runnable, GraphModel {
    private Graph graph;
    private GraphModel oldGraph;
    private int vertexCount;
    private Map<String, Integer> edgesWeight = new HashMap<>();
    private Map<String, Object> nodesData = new HashMap<>();

    public GraphAdapter(GraphModel graphModel) {
        this.oldGraph = graphModel;
    }

    // Ниже я определяю цвета ребер и вершин.
    // Крашу их в какой-то цвет, когда иду вперед/назад по ребру, вхожу в вершину или выхожу из нее
    // Используются css файлы из папки resources
    // Пока вызываю эти методы из класса GraphWalker

    public void edgeForward(int i, int j) {
        if (graph.getEdge(Integer.toString(i) + Integer.toString(j)) != null)
            graph.getEdge(Integer.toString(i) + Integer.toString(j)).addAttribute("ui.class", "forward");
        else graph.getEdge(Integer.toString(j) + Integer.toString(i)).addAttribute("ui.class", "forward");
    }

    public void edgeBack(int i, int j) {
        if (graph.getEdge(Integer.toString(i) + Integer.toString(j)) != null)
            graph.getEdge(Integer.toString(i) + Integer.toString(j)).addAttribute("ui.class", "back");
        else graph.getEdge(Integer.toString(j) + Integer.toString(i)).addAttribute("ui.class", "back");
    }

    public void nodeIn(String node) {
        graph.getNode(node).addAttribute("ui.class", "in");
    }

    public void nodeOut(String node) {
        graph.getNode(node).addAttribute("ui.class", "out");
    }

    public void initNodesData() {
        for (Node node : graph) {
            node.removeAttribute("ui.label");
            nodesData.put(node.getId(), Integer.MAX_VALUE);
            node.addAttribute("ui.label", node.getId() + " / dist: inf");
        }
    }

    public void initEdgesWeight() {
        for (Edge edge : graph.getEachEdge()) {
            edge.addAttribute("ui.label", "weight: " + edgesWeight.get(edge.getId()));
        }
    }

//    public static void relaxEdge(int i, int j) {
//        if (edgesWeight.get()) {
//            nodesData.put()
//            graph.getEdge(Integer.toString(i) + Integer.toString(j)).addAttribute("ui.label", "weight: 1");
//        }
//        else graph.getEdge(Integer.toString(j) + Integer.toString(i)).addAttribute("ui.label", "weight: 1");
//    }

    @Override
    public void initGraph() {
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
                    edgesWeight.put(Integer.toString(i) + Integer.toString(j), oldGraph.getEdge(i, j));
                }
            }
        }

        DisplayGraph displayGraph = new DisplayGraph(graph);
        displayGraph.display();
//        graph.display();
    }

    @Override
    public int getVertexCount() {
        return vertexCount;
    }

    @Override
    public Object getData(int i) {
        return null;
    }

    @Override
    public int getEdge(int i, int j) {
        return edgesWeight.get(Integer.toString(i) + Integer.toString(j));
    }

    @Override
    public void addModelListener(GraphModelListener gr) {

    }

    @Override
    public List<Integer> getNeighbours(int i) {
        List<Integer> neighbourList = new LinkedList<>();
        for (Node currentNode : graph) {
            if (graph.getEdge(Integer.toString(i) + currentNode.getId()) != null)
                neighbourList.add(edgesWeight.get(Integer.toString(i) + currentNode.getId()));
        }
        return null;
    }

    @Override
    public void run() {
        initGraph();
    }
}
