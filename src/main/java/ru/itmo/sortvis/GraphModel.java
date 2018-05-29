package ru.itmo.sortvis;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Set;

public interface GraphModel<T> {
    void initGraph();

    int getCountOfNodes();

    T getData(long i);

    int getEdge(long i, long j); // тут пока что мы передаем вес ребра

    long[] getNeighbours(long i);

    Set<Long> getAllIds();

    Set<Pair<Long, Long>> getEdges();

    boolean isOrientedGraph();
}
