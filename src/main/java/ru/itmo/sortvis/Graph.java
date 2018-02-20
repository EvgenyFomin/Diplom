package ru.itmo.sortvis;

import java.util.List;

public interface Graph<T> {
    void initGraph();

    int getVertexCount();

    T getData(int i);

    int getEdge(int i, int j);

    void addModelListener(GraphModelListener gr);

    List<Integer> getNeighbours(int i);
}
