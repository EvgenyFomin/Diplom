package ru.itmo.sortvis;

import java.util.HashMap;
import java.util.Map;

public class StringGraphModel<T> {
    private final GraphModel<T> delegate;
    private final Map<String, Integer> stringToInt;

    public StringGraphModel(GraphModel<T> delegate,  Map<String, Integer> stringToInt) {
        this.delegate = delegate;
        this.stringToInt = stringToInt;
    }


    public void initGraph() {
        delegate.initGraph();
    }

    public int getVertexCount() {
        return delegate.getVertexCount();
    }

    public T getData(String i) {
        // TODO
        return null;
    }

    public int getEdge(String i, String j) {
        // TODO
        return 0;
    }

    public int[] getNeighbours(String i) {
        // TODO
        return new int[0];
    }
}
