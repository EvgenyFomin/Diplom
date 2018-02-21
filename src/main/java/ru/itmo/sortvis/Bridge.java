package ru.itmo.sortvis;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class Bridge {
    public static Graph convertGraph(GraphModel ourGraph) {
        Graph newGraph = new SingleGraph("Simple Graph");
        int vertexCount = ourGraph.getVertexCount();

        for (int i = 0; i < vertexCount; i++) {
            newGraph.addNode(Integer.toString(i));
        }

        for (int i = 0; i < vertexCount; i++) {
            for (int j = i + 1; j < vertexCount; j++) {
                if (ourGraph.getEdge(i, j) > 0) {
                    newGraph.addEdge(Integer.toString(i) + Integer.toString(j), Integer.toString(i), Integer.toString(j));
                }
            }
        }

        newGraph.display();

        return newGraph;
    }
}
