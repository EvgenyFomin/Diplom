package ru.itmo.sortvis;

import java.util.*;

public class AdjListGraph implements GraphModel<String> {
    private final int countOfNodes;
    private final int countOfEdges;
    private final boolean isOrientedGraph;
    private final List<GraphWalkerListener> listenerList;
    private Map<Long, Set<Long>> adjList; // для хранения вершин под разными типами
    private Map<String, Integer> weight;

    public AdjListGraph(int countOfNodes, int countOfEdges, boolean isOrientedGraph, HashMap adjList, HashMap weight) {
        this.countOfNodes = countOfNodes;
        this.countOfEdges = countOfEdges;
        this.isOrientedGraph = isOrientedGraph;
        listenerList = new ArrayList<>();
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
    public String getData(long i) {
        return null;
    }

    @Override
    public int getEdge(long i, long j) {
        return weight.get(String.valueOf(i) + "-" + String.valueOf(j));
    }

    @Override
    public long[] getNeighbours(long i) {
        long[] neighbours = new long[adjList.get(i).size()];
        int iterator = 0;

        for (long obj : adjList.get(i)) {
            neighbours[iterator++] = obj;
        }

        return neighbours;
    }

    // Пофиксить, работоспособность не проверял
//    public int[] getNeighbours(int i) {
//        return adjList[i].stream().mapToInt(k -> k).toArray();
//    }
}