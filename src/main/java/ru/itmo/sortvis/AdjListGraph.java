package ru.itmo.sortvis;

import java.util.*;

public class AdjListGraph implements Graph {
    private static final int N = 6;
    private LinkedList<Integer>[] adjList;
    private final List<GraphModelListener> listenerList;
    private Map<Integer, Integer> weight;

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
                    weight.put(hashing(i, j), n);
                } else weight.put(hashing(i, j), 0);
            }
        }
        graphInitialized();
    }

    @Override
    public int getVertexCount() {
        return N;
    }

    @Override
    public int getEdge(int i, int j) {
        return weight.get(hashing(i, j));
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

    // не додумался, как хранить вес ребер, поэтому использую хеш-функцию. Проверил для 1000 ребер - дает разные значения

    private Integer hashing(int i, int j) {
        return (i < j) ? 47 * i + 53 * j + 57 : 47 * j + 53 * i + 57;
    }
}
