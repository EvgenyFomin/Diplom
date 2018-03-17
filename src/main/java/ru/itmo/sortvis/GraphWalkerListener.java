package ru.itmo.sortvis;

public interface GraphWalkerListener {
    void nodeIn(String node);
    void nodeOut(String node);

    void edgeForward(int i, int j);
    void edgeBack(int i, int j);
}
