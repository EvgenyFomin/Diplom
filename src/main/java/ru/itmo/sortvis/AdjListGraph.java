package ru.itmo.sortvis;

import scala.Int;

import java.util.*;

public class AdjListGraph implements GraphModel<String> {
    private static final int N = 6;
    private LinkedList<Integer>[] adjList;
    private final List<GraphModelListener> listenerList;
    private Map<String, Integer> weight;

    public AdjListGraph() {
        listenerList = new ArrayList<>();
        weight = new HashMap<>();
    }

    @Override
    public void initGraph() {
        adjList = new LinkedList[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new LinkedList<>();
        }

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                int n = new Random().nextInt(2);
                if (n == 1) {
                    adjList[i].add(j);
                    adjList[j].add(i);
                    weight.put(Integer.toString(i) + Integer.toString(j), n);
                } else weight.put(Integer.toString(i) + Integer.toString(j), 0);
            }
        }
        graphInitialized();
    }

    @Override
    public int getVertexCount() {
        return N;
    }

    @Override
    public String getData(int i) {
        return null;
    }

    @Override
    public int getEdge(int i, int j) {
        return weight.get(Integer.toString(i) + Integer.toString(j));
    }

    @Override
    public void addModelListener(GraphModelListener gr) {
        listenerList.add(gr);
    }

    @Override
    public LinkedList<Integer> getNeighbours(int i) {
        return adjList[i];
    }

    private void graphInitialized() {
        for (GraphModelListener obj : listenerList) {
            obj.modelChanged();
        }
    }
}
