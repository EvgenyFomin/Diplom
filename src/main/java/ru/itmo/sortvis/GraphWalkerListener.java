package ru.itmo.sortvis;

public interface GraphWalkerListener {
    void nodeIn(String node);
    void nodeOut(String node);

    void edgeForward(long i, long j);
    void edgeBack(long i, long j);
}
