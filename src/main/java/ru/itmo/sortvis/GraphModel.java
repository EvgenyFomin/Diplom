package ru.itmo.sortvis;

import java.util.List;

public interface GraphModel<T> {
    void initGraph();

    int getCountOfNodes();

    T getData(int i);

    int getEdge(int i, int j);

    int[] getNeighbours(int i);
}
