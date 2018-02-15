package ru.itmo.sortvis;

import java.util.LinkedList;
import java.util.Queue;

public class GraphWalker {
    Graph graph;
    private int vertexCount;
    byte[] color;
    int[] distances;

    public GraphWalker(Graph graph) {
        this.graph = graph;
        this.vertexCount = graph.getVertexCount();
        color = new byte[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            color[i] = 0;
        }
        distances = new int[vertexCount];
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

    public void bfs(int u, int v) {
        int[] from = new int[vertexCount];
        LinkedList<Integer> way = new LinkedList<>();
        boolean isWayExists = false;
        Queue<Integer> currantVertexQueue = new LinkedList<>();
        currantVertexQueue.add(u);
        color[u] = 1;
        distances[u] = 0;

        while (!currantVertexQueue.isEmpty()) {
            if (currantVertexQueue.peek() == v) {
                isWayExists = true;
                break;
            }

            for (Integer obj: graph.getNeighbours(currantVertexQueue.peek())) {
                if (color[obj] == 0) {
                    color[obj] = 1;
                    distances[obj] = distances[currantVertexQueue.peek()] + 1;
                    currantVertexQueue.add(obj);
                    from[obj] = currantVertexQueue.peek();
                }
            }

            currantVertexQueue.poll();
        }

        if (isWayExists) {
            way.add(v);
            int tmp = from[v];
            for (int i = 1; i < distances[v]; i++) {
                way.addFirst(tmp);
                tmp = from[tmp];
            }

            System.out.print("The way is [ ");

            for (Integer obj: way) {
                System.out.print(obj + " ");
            }

            System.out.println("]");
            System.out.println("distance = " + way.size());
        }
        else {
            System.out.println("The way doesn't exists");
        }
    }
}
