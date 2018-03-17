package ru.itmo.sortvis;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import ru.itmo.sortvis.ui.DisplayGraph;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GsGraphAdapter<T> implements Runnable, GraphModel<T>, GraphWalkerListener {
    private Graph gsGraph;
    private GraphModel<T> delegateGraph;
    private int vertexCount;
    private Map<String, Integer> edgesWeight = new HashMap<>();
    private Map<String, Object> nodesData = new HashMap<>();

    public GsGraphAdapter(GraphModel<T> delegateGraph) {
        this.delegateGraph = delegateGraph;
    }

    // Ниже я определяю цвета ребер и вершин.
    // Крашу их в какой-то цвет, когда иду вперед/назад по ребру, вхожу в вершину или выхожу из нее
    // Используются css файлы из папки resources
    // Пока вызываю эти методы из класса GraphWalker

    public void edgeForward(int i, int j) {
        if (gsGraph.getEdge(Integer.toString(i) + Integer.toString(j)) != null)
            gsGraph.getEdge(Integer.toString(i) + Integer.toString(j)).addAttribute("ui.class", "forward");
        else gsGraph.getEdge(Integer.toString(j) + Integer.toString(i)).addAttribute("ui.class", "forward");
    }

    public void edgeBack(int i, int j) {
        if (gsGraph.getEdge(Integer.toString(i) + Integer.toString(j)) != null)
            gsGraph.getEdge(Integer.toString(i) + Integer.toString(j)).addAttribute("ui.class", "back");
        else gsGraph.getEdge(Integer.toString(j) + Integer.toString(i)).addAttribute("ui.class", "back");
    }

    public void nodeIn(String node) {
        gsGraph.getNode(node).addAttribute("ui.class", "in");
    }

    public void nodeOut(String node) {
        gsGraph.getNode(node).addAttribute("ui.class", "out");
    }

    public void initNodesData() {
        for (Node node : gsGraph) {
            node.removeAttribute("ui.label");
            nodesData.put(node.getId(), Integer.MAX_VALUE);
            node.addAttribute("ui.label", node.getId() + " / dist: inf");
        }
    }

    public void initEdgesWeight() {
        for (Edge edge : gsGraph.getEachEdge()) {
            edge.addAttribute("ui.label", "weight: " + edgesWeight.get(edge.getId()));
        }
    }

//    public static void relaxEdge(int i, int j) {
//        if (edgesWeight.get()) {
//            nodesData.put()
//            gsGraph.getEdge(Integer.toString(i) + Integer.toString(j)).addAttribute("ui.label", "weight: 1");
//        }
//        else gsGraph.getEdge(Integer.toString(j) + Integer.toString(i)).addAttribute("ui.label", "weight: 1");
//    }

    @Override
    public void initGraph() {
        gsGraph = new SingleGraph("Simple Graph");
        vertexCount = delegateGraph.getVertexCount();

        gsGraph.addAttribute("ui.quality");
        gsGraph.addAttribute("ui.antialias");

        File cssFile = new File("src/main/resources/type.css");
        String cssFileFullPath = cssFile.getAbsolutePath();

        gsGraph.addAttribute("ui.stylesheet", "url('file:///" + cssFileFullPath + "')");

        for (int i = 0; i < vertexCount; i++) {
            gsGraph.addNode(Integer.toString(i)).addAttribute("ui.label", "Node " + Integer.toString(i));
        }

        for (int i = 0; i < vertexCount; i++) {
            for (int j = i + 1; j < vertexCount; j++) {
                if (delegateGraph.getEdge(i, j) > 0) {
                    gsGraph.addEdge(Integer.toString(i) + Integer.toString(j), Integer.toString(i), Integer.toString(j));
                    edgesWeight.put(Integer.toString(i) + Integer.toString(j), delegateGraph.getEdge(i, j));
                }
            }
        }

        DisplayGraph displayGraph = new DisplayGraph(gsGraph);
        displayGraph.display();
    }

    @Override
    public int getVertexCount() {
        return vertexCount;
    }

    @Override
    public T getData(int i) {
        return null;
    }

    @Override
    public int getEdge(int i, int j) {
        return edgesWeight.get(Integer.toString(i) + Integer.toString(j));
    }

    @Override
    public int[] getNeighbours(int i) {
        return delegateGraph.getNeighbours(i);
    }

    @Override
    public void run() {
        initGraph();
    }
}
