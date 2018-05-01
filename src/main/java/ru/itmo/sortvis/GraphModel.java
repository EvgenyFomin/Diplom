package ru.itmo.sortvis;

import java.util.List;

public interface GraphModel<T> {
    void initGraph();

    int getCountOfNodes();

    T getData(long i);

    int getEdge(long i, long j); // тут пока что мы передаем вес ребра

    long[] getNeighbours(long i);
}
