package ru.itmo.sortvis;

public interface Graph {
    void initGraph();
    int getVertexCount();
    int getEdge(int i, int j);
    void addModelListener(GraphModelListener gr);
}
