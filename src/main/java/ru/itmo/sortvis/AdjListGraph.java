package ru.itmo.sortvis;

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
    public Integer getEdge(String i, String j) {
        return 0;
    }

    @Override
    public void addModelListener(GraphModelListener gr) {
        listenerList.add(gr);
    }

    @Override
    public LinkedList<Integer> getNeighbours(String i) {
        return new LinkedList<>();
    }

    @Override
    public String[] getNodes() {
        return new String[0];
    }

    @Override
    public boolean getGraphStatus() {
        return false;
    }

    private void graphInitialized() {
        for (GraphModelListener obj : listenerList) {
            obj.modelChanged();
        }
    }
}
