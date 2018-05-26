package ru.itmo.sortvis;

import org.apache.commons.lang3.tuple.Pair;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import ru.itmo.sortvis.XMLMapParser.Node;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GsGraphAdapter implements GraphModel<Node>, GraphWalkerListener {
    private Graph gsGraph;
    private GraphModel<Node> delegateGraph;
    private HashMap<String, Object> stat;
    private UpdateStatistics updateStatistics;

    public GsGraphAdapter(GraphModel<Node> delegateGraph) {
        this.delegateGraph = delegateGraph;
    }

    public void edgeForward(long i, long j) {
        if (gsGraph.getEdge(getGsEdgeId(i, j)) != null)
            gsGraph.getEdge(getGsEdgeId(i, j)).addAttribute("ui.class", "forward");
        else gsGraph.getEdge(getGsEdgeId(j, i)).addAttribute("ui.class", "forward");
        updateStat();
    }

    public void edgeBack(long i, long j) {
        if (gsGraph.getEdge(getGsEdgeId(i, j)) != null)
            gsGraph.getEdge(getGsEdgeId(i, j)).addAttribute("ui.class", "back");
        else gsGraph.getEdge(getGsEdgeId(j, i)).addAttribute("ui.class", "back");
        updateStat();
    }

    public void nodeIn(String node) {
        gsGraph.getNode(node).addAttribute("ui.class", "in");
        updateStat();
    }

    public void nodeOut(String node) {
        gsGraph.getNode(node).addAttribute("ui.class", "out");
        updateStat();
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

        gsGraph.addAttribute("ui.quality");
        gsGraph.addAttribute("ui.antialias");

        File cssFile = new File("src/main/resources/type.css");
        String cssFileFullPath = cssFile.getAbsolutePath();

        gsGraph.addAttribute("ui.stylesheet", "url('file:///" + cssFileFullPath + "')");

        for (long id : delegateGraph.getAllIds()) {
            String nodeId = Long.toString(id);
            gsGraph.addNode(nodeId).addAttribute("ui.label", "Node "     + id);
            double x = delegateGraph.getData(id).getLon() * 1000000;
            double y = delegateGraph.getData(id).getLat() * 1000000;
//            System.out.println(delegateGraph.getData(id).getLat() + " " + delegateGraph.getData(id).getLon());
//            System.out.printf("Adding node %s: (%d, %d)%n", nodeId, x, y);
            gsGraph.getNode(nodeId).setAttribute("xyz", x, y, 0);
            gsGraph.getNode(nodeId).setAttribute("xyz", x, y, 0);
        }

        for (Pair<Long, Long> edge : delegateGraph.getEdges()) {
            if (gsGraph.getEdge(edge.getRight() + "-" + edge.getLeft()) != null) {
//                System.out.println("Inverted edge exists, not adding: " + edge.getLeft() + "-" + edge.getRight());
                continue;
            }
//            System.out.println("Adding edge " + edge.getLeft() + "-" + edge.getRight());
            gsGraph.addEdge(edge.getLeft() + "-" + edge.getRight(), Long.toString(edge.getLeft()), Long.toString(edge.getRight()), false);
        }
    }

    @Override
    public int getCountOfNodes() {
        return delegateGraph.getCountOfNodes();
    }

    @Override
    public Node getData(long i) {
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

    @Override
    public Set<Long> getAllIds() {
        return delegateGraph.getAllIds();
    }

    @Override
    public Set<Pair<Long, Long>> getEdges() {
        return delegateGraph.getEdges();
    }

    private String getGsEdgeId(long i, long j) {
        return i + "-" + j;
    }

    public Graph getGsGraph() {
        return gsGraph;
    }

    public void setStat(UpdateStatistics updateStatistics) {
        this.updateStatistics = updateStatistics;
    }

    private void updateStat() {
        stat = updateStatistics.getStat();
        if (stat != null) {
            for (Map.Entry<String, Object> obj : stat.entrySet()) {
                System.out.println("                Statistics: " + obj.getKey() + " = " + obj.getValue());
            }
        }
    }
}
