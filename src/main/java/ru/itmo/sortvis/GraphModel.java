package ru.itmo.sortvis;

import java.util.List;
import java.util.Map;

public interface GraphModel<T> {
    void initGraph();

    int getVertexCount();

    T getData(String i);

    Integer getEdge(String i, String j);

    void addModelListener(GraphModelListener gr);

    List getNeighbours(String i);

    String[] getNodes();

    boolean getGraphStatus();
}