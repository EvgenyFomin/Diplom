package ru.itmo.sortvis;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.tuple.Pair;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import ru.itmo.sortvis.XMLMapParser.Node;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class GsGraphAdapter implements GraphModel<Node>, GraphWalkerListener {
    private Graph gsGraph;
    private GraphModel<Node> delegateGraph;
    private HashMap<String, Object> stat;
    private UpdateStatistics updateStatistics;
    private boolean isOrientedGraph;
    private String startNode, endNode;

    public void setStartNode(String startNode) {
        this.startNode = startNode;
    }

    public void setEndNode(String endNode) {
        this.endNode = endNode;
    }

    private Statistics statistics;

    private final List<StatisticsListener> statisticsListeners = new CopyOnWriteArrayList<>();

    public void addStatisticsListener(StatisticsListener listener) {
        statisticsListeners.add(listener);
    }

    public static class Statistics {
        public long nodeIn = 0;
        public long nodeOut = 0;
        public long edgeForward = 0;
        public long edgeBackward = 0;

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                    .append("nodeIn", nodeIn)
                    .append("nodeOut", nodeOut)
                    .append("edgeForward", edgeForward)
                    .append("edgeBackward", edgeBackward)
                    .toString();
        }
    }

    public GsGraphAdapter(GraphModel<Node> delegateGraph) {
        this.delegateGraph = delegateGraph;
        this.statistics = new Statistics();
    }

    // Ниже я определяю цвета ребер и вершин.
    // Крашу их в какой-то цвет, когда иду вперед/назад по ребру, вхожу в вершину или выхожу из нее
    // Используются css файлы из папки resources
    // Пока вызываю эти методы из класса GraphWalker

    public void edgeForward(long i, long j) {
        statistics.edgeForward++;
        if (gsGraph.getEdge(getGsEdgeId(i, j)) != null)
            gsGraph.getEdge(getGsEdgeId(i, j)).addAttribute("ui.class", "forward");
        else gsGraph.getEdge(getGsEdgeId(j, i)).addAttribute("ui.class", "forward");
        updateStat();
    }

    public void edgeBack(long i, long j) {
        statistics.edgeBackward++;
        if (gsGraph.getEdge(getGsEdgeId(i, j)) != null)
            gsGraph.getEdge(getGsEdgeId(i, j)).addAttribute("ui.class", "back");
        else gsGraph.getEdge(getGsEdgeId(j, i)).addAttribute("ui.class", "back");
        updateStat();
    }

    public void nodeIn(long node) {
        statistics.nodeIn++;
        gsGraph.getNode(Long.toString(node)).addAttribute("ui.class", "in");
        updateStat();
    }

    public void nodeOut(long node) {
        statistics.nodeOut++;
        gsGraph.getNode(Long.toString(node)).addAttribute("ui.class", "out");
        updateStat();
    }

    public void clearStatistics() {
        statistics = new Statistics();
    }

    @Override
    public void paintNode(long node, Color color) {
        gsGraph.getNode(Long.toString(node)).addAttribute("ui.style",
                "fill-color: rgb(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + "); ");
        updateStat();
    }

    public Statistics getStatistics() {
        return statistics;
    }

    @Override
    public void paintEdge(long node1, long node2, Color color) {
        if (gsGraph.getEdge(getGsEdgeId(node1, node2)) != null)
            gsGraph.getEdge(getGsEdgeId(node1, node2)).addAttribute("ui.style",
                    "fill-color: rgb(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + "); ");
        else gsGraph.getEdge(getGsEdgeId(node2, node1)).addAttribute("ui.style",
                "fill-color: rgb(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + "); ");
        updateStat();
    }

    @Override
    public void initGraph() {
        gsGraph = new SingleGraph("Simple Graph");

        gsGraph.addAttribute("ui.quality");
        gsGraph.addAttribute("ui.antialias");

        File cssFile = new File("src/main/resources/type.css");
        String cssFileFullPath = cssFile.getAbsolutePath();

        gsGraph.addAttribute("ui.stylesheet", "url('file:///" + cssFileFullPath + "')");

        isOrientedGraph = delegateGraph.isOrientedGraph();

        try {
            for (long id : delegateGraph.getAllIds()) {
                String nodeId = Long.toString(id);
                double x = delegateGraph.getData(id).getLon() * 1000000;
                double y = delegateGraph.getData(id).getLat() * 1000000;
//            System.out.println(delegateGraph.getData(id).getLat() + " " + delegateGraph.getData(id).getLon());
//            System.out.printf("Adding node %s: (%d, %d)%n", nodeId, x, y);
                gsGraph.addNode(nodeId).setAttribute("xyz", x, y, 0);
            }

            for (Pair<Long, Long> edge : delegateGraph.getEdges()) {
                if (gsGraph.getEdge(edge.getRight() + "-" + edge.getLeft()) != null) {
//                System.out.println("Inverted edge exists, not adding: " + edge.getLeft() + "-" + edge.getRight());
                    continue;
                }
//            System.out.println("Adding edge " + edge.getLeft() + "-" + edge.getRight());
                gsGraph.addEdge(edge.getLeft() + "-" + edge.getRight(), Long.toString(edge.getLeft()), Long.toString(edge.getRight()), false);
            }
        } catch (ClassCastException e) {
            for (long id : delegateGraph.getAllIds()) {
                gsGraph.addNode(Long.toString(id));
            }

            if (isOrientedGraph) {
                for (Pair<Long, Long> edge : delegateGraph.getEdges()) {
                    gsGraph.addEdge(edge.getLeft() + "-" + edge.getRight(), Long.toString(edge.getLeft()), Long.toString(edge.getRight()), true);
                }
            } else {
                for (Pair<Long, Long> edge : delegateGraph.getEdges()) {
                    if (gsGraph.getEdge(edge.getLeft() + "-" + edge.getRight()) == null &&
                            gsGraph.getEdge(edge.getRight() + "-" + edge.getLeft()) == null)
                        gsGraph.addEdge(edge.getLeft() + "-" + edge.getRight(), Long.toString(edge.getLeft()), Long.toString(edge.getRight()));
                }
            }
        }

        if (Launcher.enableMarker) {
            SpriteManager spriteManager = new SpriteManager(gsGraph);
            Sprite spriteStart = spriteManager.addSprite(startNode);
            spriteStart.attachToNode(startNode);
            if (endNode != null && !endNode.equals("")) {
                Sprite spriteEnd = spriteManager.addSprite(endNode);
                spriteEnd.attachToNode(endNode);
            }
        }

        if (Launcher.enableNodesLabel) {
            for (long id : delegateGraph.getAllIds()) {
                gsGraph.getNode(Long.toString(id)).addAttribute("ui.label", "Node " + id);
            }
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

    @Override
    public boolean isOrientedGraph() {
        return delegateGraph.isOrientedGraph();
    }

    public void setStat(UpdateStatistics updateStatistics) {
        this.updateStatistics = updateStatistics;
    }

    private void updateStat() {
        stat = updateStatistics.getStat();
        if (Launcher.enableDebugOutput) {
            if (stat != null) {
                for (Map.Entry<String, Object> obj : stat.entrySet()) {
                    System.out.println("                Statistics: " + obj.getKey() + " = " + obj.getValue());
                }
            }
        }
        for (StatisticsListener statisticsListener : statisticsListeners) {
            statisticsListener.statsUpdated(new StatisticsListener.AllStats(updateStatistics, statistics));
        }
    }
}
