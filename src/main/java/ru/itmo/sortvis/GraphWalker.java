package ru.itmo.sortvis;

import java.util.*;
import java.util.function.Consumer;

public class GraphWalker<T> {
    private GraphModel<T> graphModel;
    private byte[] color;
    private int[] distance;
    private int[] from;

    private final List<GraphWalkerListener> listeners;

    private static final byte WHITE = 0;
    private static byte GRAY = 1;
    private static byte BLACK = 2;

    GraphWalker(GraphModel<T> graphModel) {
        this.graphModel = graphModel;
        int vertexCount = graphModel.getVertexCount();

        this.color = new byte[vertexCount];
        Arrays.fill(this.color, WHITE);

        this.distance = new int[vertexCount];
        this.from = new int[vertexCount];

        this.listeners = new ArrayList<>();
    }

    public void dfs(int i) {
        from[i] = -1;
        depthFirstSearch(i);
        for (int j = 0; j < color.length; j++) {
            if (color[j] == WHITE) {
                from[j] = -1;
                depthFirstSearch(j);
            }
        }
    }

    private void depthFirstSearch(int i) {
        notify(l -> l.nodeIn(Integer.toString(i)));

        System.out.println("in " + i);

        color[i] = GRAY;
        int[] neighbours = graphModel.getNeighbours(i);
        for (Integer obj : neighbours) {
            if (color[obj] == WHITE) {
                try {
                    Thread.sleep(1000);
                    notify(l -> l.edgeForward(i, obj));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                from[obj] = i;
                depthFirstSearch(obj);
            }
        }

        try {
            Thread.sleep(1000);
            notify(l -> l.nodeOut(Integer.toString(i)));
            if (from[i] != -1)
                notify(l -> l.edgeBack(i, from[i]));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        color[i] = BLACK;
        System.out.println("out " + i);
    }

    public void bfs(int u, int v) {
        LinkedList<Integer> way; // путь от u до v. Сюда кладем уже верный путь от u до v.
        boolean isWayExists = false;
        Queue<Integer> currentVertexQueue = new LinkedList<>();
        currentVertexQueue.add(u);
        color[u] = GRAY;
        distance[u] = WHITE;

        while (!currentVertexQueue.isEmpty()) {
            if (currentVertexQueue.peek() == v) {
                isWayExists = true;
                break;
            }

            for (int obj : graphModel.getNeighbours(currentVertexQueue.peek())) {
                if (color[obj] == WHITE) {
                    color[obj] = GRAY;
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
            color[currentVertex] = GRAY;
            for (int obj : graphModel.getNeighbours(currentVertex)) {
                if (color[obj] == WHITE) {
                    neighbours.add(obj);
                }
            }

            for (Integer obj : neighbours) {
                if (distance[obj] > distance[currentVertex] + graphModel.getEdge(obj, currentVertex)) {
                    distance[obj] = distance[currentVertex] + graphModel.getEdge(obj, currentVertex);
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

    public void addListener(GraphWalkerListener l) {
        listeners.add(l);
    }

    public void removeListener(GraphWalkerListener l) {
        listeners.remove(l);
    }

    private void notify(Consumer<GraphWalkerListener> consumer) {
        listeners.forEach(consumer::accept);
    }
}