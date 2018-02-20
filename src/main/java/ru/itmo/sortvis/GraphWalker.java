package ru.itmo.sortvis;

import java.util.*;

// Сравнение двух вершин по дистанции

class CompareByDistance implements Comparator<Integer> {
    int[] distance;

    public CompareByDistance(int[] distance) {
        this.distance = distance;
    }

    @Override
    public int compare(Integer o1, Integer o2) {
        int i = distance[o1];
        int j = distance[o2];
        return i - j;
    }
}

public class GraphWalker<T> {
    private Graph<T> graph;
    private byte[] color;
    private int[] distance;
    private int[] from;

    GraphWalker(Graph<T> graph) {
        this.graph = graph;
        int vertexCount = graph.getVertexCount();
        color = new byte[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            color[i] = 0;
        }
        distance = new int[vertexCount];
        from = new int[vertexCount];
    }

    public void dfs(int i) {
        depthFirstSearch(i);
        for (int j = 0; i < color.length; i++) {
            if (color[j] == 0) {
                depthFirstSearch(j);
            }
        }
    }

    private void depthFirstSearch(int i) {
        System.out.println("in " + i);
        color[i] = 1;
        List<Integer> neighbours = graph.getNeighbours(i);
        for (Integer obj : neighbours) {
            if (color[obj] == 0) {
                depthFirstSearch(obj);
            }
        }
        color[i] = 2;
        System.out.println("out " + i);
    }

    public void bfs(int u, int v) {
        LinkedList<Integer> way; // путь от u до v. Сюда кладем уже верный путь от u до v.
        boolean isWayExists = false;
        Queue<Integer> currentVertexQueue = new LinkedList<>();
        currentVertexQueue.add(u);
        color[u] = 1;
        distance[u] = 0;

        while (!currentVertexQueue.isEmpty()) {
            if (currentVertexQueue.peek() == v) {
                isWayExists = true;
                break;
            }

            for (Integer obj : graph.getNeighbours(currentVertexQueue.peek())) {
                if (color[obj] == 0) {
                    color[obj] = 1;
                    distance[obj] = distance[currentVertexQueue.peek()] + 1;
                    currentVertexQueue.add(obj);
                    from[obj] = currentVertexQueue.peek();
                }
            }

            currentVertexQueue.poll();
        }

        if (isWayExists) {
            way = wayMaker(u, v, from);

            System.out.print("The way is [ ");

            for (Integer obj : way) {
                System.out.print(obj + " ");
            }

            System.out.println("]");
            System.out.println("distance = " + distance[v]);
        } else {
            System.out.println("The way doesn't exists");
        }
    }

    public void dijkstra(int u) {
        Queue<Integer> vertexPriorityQueue = new PriorityQueue<>(new CompareByDistance(distance));
        LinkedList<Integer> neighbours = new LinkedList<>();
        int[] from = new int[distance.length];
        LinkedList<Integer> way;

        for (int i = 0; i < distance.length; i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        distance[u] = 0;
        from[u] = u;
        vertexPriorityQueue.add(u);

        while (!vertexPriorityQueue.isEmpty()) {
            int currentVertex = vertexPriorityQueue.poll();
            color[currentVertex] = 1;
            for (Integer obj : graph.getNeighbours(currentVertex)) {
                if (color[obj] == 0) {
                    neighbours.add(obj);
                }
            }

            for (Integer obj : neighbours) {
                if (distance[obj] > distance[currentVertex] + graph.getEdge(obj, currentVertex)) {
                    distance[obj] = distance[currentVertex] + graph.getEdge(obj, currentVertex);
                    from[obj] = currentVertex;
                }
            }

            vertexPriorityQueue.addAll(neighbours);
            neighbours.clear();
        }

        for (int i = 0; i < distance.length; i++) {
            if (distance[i] == Integer.MAX_VALUE) {
                System.out.println("Расстояние от " + u + " до " + i + " недостижимо");
            } else {
                System.out.println("Расстояние от " + u + " до " + i + " = " + distance[i]);
                System.out.print("Путь = [ ");

                if (i == u) {
                    System.out.println(u + " ]");
                    continue;
                }

                way = wayMaker(u, i, from);

                for (Integer obj : way) {
                    System.out.print(obj + " ");
                }

                System.out.println("]");
                way.clear();
            }
        }
    }

    private LinkedList<Integer> wayMaker(int u, int v, int[] from) {
        LinkedList<Integer> way = new LinkedList<>();
        way.add(v);
        int tmp = from[v];
        while (tmp != u) {
            way.addFirst(tmp);
            tmp = from[tmp];
        }

        way.addFirst(u);
        return way;
    }
}
