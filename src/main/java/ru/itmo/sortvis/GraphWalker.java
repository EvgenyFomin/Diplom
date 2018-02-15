package ru.itmo.sortvis;

import java.util.LinkedList;

public class GraphWalker {
    Graph graph;
    private int vertexCount;
    byte[] color;

    public GraphWalker(Graph graph) {
        this.graph = graph;
        this.vertexCount = graph.getVertexCount();
        color = new byte[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            color[i] = 0;
        }
    }

    public void dfs(int i) {
        System.out.println("in " + i);
        color[i] = 1;
        LinkedList<Integer> neighbours = graph.getNeighbours(i);
        for (Integer obj: neighbours) {
            if (color[obj] == 0) {
                dfs(obj);
            }
        }
        color[i] = 2;
        System.out.println("out " + i);
    }
}
