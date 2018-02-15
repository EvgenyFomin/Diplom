package ru.itmo.sortvis;

import java.util.LinkedList;

public interface Graph {
    void initGraph();

    int getVertexCount();

    int getEdge(int i, int j);

    void addModelListener(GraphModelListener gr);

    LinkedList<Integer> getNeighbours(int i);
}
