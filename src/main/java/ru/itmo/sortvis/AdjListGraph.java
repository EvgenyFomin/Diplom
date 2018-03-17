package ru.itmo.sortvis;

import java.util.*;

public class AdjListGraph implements GraphModel<String> {
    private static final int N = 6;

    // Не очень разобрался, почему массив списков, а не список массивов.
    private List<Integer>[] adjList;
    private final List<GraphWalkerListener> listenerList;
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

    // Пофиксить, работоспособность не проверял
    public int[] getNeighbours(int i) {
        return adjList[i].stream().mapToInt(k -> k).toArray();
    }
}