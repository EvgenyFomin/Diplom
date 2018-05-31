package ru.itmo.sortvis;

import java.awt.*;

public interface GraphWalkerListener {
    void nodeIn(long node);
    void nodeOut(long node);

    void edgeForward(long i, long j);
    void edgeBack(long i, long j);

    void paintNode(long node, Color color);
    void paintEdge(long node1, long node2, Color color);
}
