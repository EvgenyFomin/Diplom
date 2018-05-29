package ru.itmo.sortvis;

import org.apache.commons.lang3.tuple.Pair;
import ru.itmo.sortvis.XMLMapParser.Node;

import java.util.*;

public class AdjListGraph<T> implements GraphModel<T> {
    // Тут похоже куча лишних переменных
//    private final List<GraphWalkerListener> listenerList;
//    private final int countOfNodes;
//    private final int countOfEdges;
    private final boolean isOrientedGraph;
    private final Map<Long, T> nodes;
    private Map<Long, Set<Long>> adjList;
    private Map<Pair<Long, Long>, Integer> weight;

    public AdjListGraph(/*int countOfNodes, int countOfEdges,*/ boolean isOrientedGraph, Map<Long, T> nodes, Map<Long, Set<Long>> adjList, Map<Pair<Long, Long>, Integer> weight) {
//        this.countOfNodes = countOfNodes;
//        this.countOfEdges = countOfEdges;
//        this.listenerList = new ArrayList<>();
        this.isOrientedGraph = isOrientedGraph;
        this.nodes = nodes;
        this.weight = weight;
        this.adjList = adjList;
    }

    @Override
    public void initGraph() {

    }

    @Override
    public int getCountOfNodes() {
        return adjList.size();
    }

    @Override
    public T getData(long i) {
        return nodes.get(i);
    }

    @Override
    public int getEdge(long i, long j) {
        return weight.get(Pair.of(i, j));
    }

    @Override
    public long[] getNeighbours(long i) {
        try {
            long[] neighbours = new long[adjList.get(i).size()];
            int iterator = 0;

            for (long obj : adjList.get(i)) {
                neighbours[iterator++] = obj;
            }

            return neighbours;

        } catch (NullPointerException e) {
            return new long[0];
        }
    }

    @Override
    public Set<Long> getAllIds() {
        return adjList.keySet();
    }

    @Override
    public Set<Pair<Long, Long>> getEdges() {
        return weight.keySet();
    }

    public boolean isOrientedGraph() {
        return isOrientedGraph;
    }

    // Пофиксить, работоспособность не проверял
//    public int[] getNeighbours(int i) {
//        return adjList[i].stream().mapToInt(k -> k).toArray();
//    }
}