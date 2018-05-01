package ru.itmo.sortvis;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.AbstractGraph;
import org.graphstream.graph.implementations.SingleGraph;
import ru.itmo.sortvis.ui.DisplayGraph;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GsGraphAdapter<T> implements GraphModel<T>, GraphWalkerListener {
    private Graph gsGraph;
    private GraphModel<T> delegateGraph;

    public GsGraphAdapter(GraphModel<T> delegateGraph) {
        this.delegateGraph = delegateGraph;
    }

    // Ниже я определяю цвета ребер и вершин.
    // Крашу их в какой-то цвет, когда иду вперед/назад по ребру, вхожу в вершину или выхожу из нее
    // Используются css файлы из папки resources
    // Пока вызываю эти методы из класса GraphWalker

    public void edgeForward(long i, long j) {
        if (gsGraph.getEdge(getGsEdgeId(i, j)) != null)
            gsGraph.getEdge(getGsEdgeId(i, j)).addAttribute("ui.class", "forward");
        else gsGraph.getEdge(getGsEdgeId(j, i)).addAttribute("ui.class", "forward");
    }

    public void edgeBack(long i, long j) {
        if (gsGraph.getEdge(getGsEdgeId(i, j)) != null)
            gsGraph.getEdge(getGsEdgeId(i, j)).addAttribute("ui.class", "back");
        else gsGraph.getEdge(getGsEdgeId(j, i)).addAttribute("ui.class", "back");
    }

    public void nodeIn(String node) {
        gsGraph.getNode(node).addAttribute("ui.class", "in");
    }

    public void nodeOut(String node) {
        gsGraph.getNode(node).addAttribute("ui.class", "out");
    }

    // Закомментировал - не компилировалось - а пофиксить не могу тк не понимаю что тут происходит
//    public void initNodesData() {
//        for (Node node : gsGraph) {
//            node.removeAttribute("ui.label");
////            nodesData.put(node.getId(), Integer.MAX_VALUE);
//            node.addAttribute("ui.label", node.getId() + " / dist: inf");
//        }
//    }
//
//    public void initEdgesWeight() {
//        for (Edge edge : gsGraph.getEachEdge()) {
//            edge.addAttribute("ui.label", "weight: " + edgesWeight.get(edge.getId()));
//        }
//    }

//    public static void relaxEdge(int i, int j) {
//        if (edgesWeight.get()) {
//            nodesData.put()
//            gsGraph.getEdge(getGsEdgeId(i, j)).addAttribute("ui.label", "weight: 1");
//        }
//        else gsGraph.getEdge(getGsEdgeId(j, i)).addAttribute("ui.label", "weight: 1");
//    }

    @Override
    public void initGraph() {
        gsGraph = new SingleGraph("Simple Graph");
        int countOfNodes = delegateGraph.getCountOfNodes();

        gsGraph.addAttribute("ui.quality");
        gsGraph.addAttribute("ui.antialias");

        File cssFile = new File("src/main/resources/type.css");
        String cssFileFullPath = cssFile.getAbsolutePath();

        gsGraph.addAttribute("ui.stylesheet", "url('file:///" + cssFileFullPath + "')");

        for (int i = 0; i < countOfNodes; i++) {
            gsGraph.addNode(Integer.toString(i)).addAttribute("ui.label", "Node " + i);
        }

        for (int i = 0; i < countOfNodes; i++) {
            for (int j = i + 1; j < countOfNodes; j++) {
                if (delegateGraph.getEdge(i, j) > 0) {
                    gsGraph.addEdge(getGsEdgeId(i, j), i, j);
                }
            }
        }
    }

    @Override
    public int getCountOfNodes() {
        return delegateGraph.getCountOfNodes();
    }

    @Override
    public T getData(long i) {
        return delegateGraph.getData(i);
    }

    @Override
    public int getEdge(long i, long j) {
        return delegateGraph.getEdge(i, j);
    }

    @Override
    public long[] getNeighbours(long i) {
        return delegateGraph.getNeighbours(i);
    }

    private String getGsEdgeId(long i, long j) {
        return i + "-" + j;
    }

    public Graph getGsGraph() {
        return gsGraph;
    }
}
