package ru.itmo.sortvis;

import java.util.*;
import java.util.function.Consumer;

public class GraphWalker<T> {
    private GraphModel<T> graphModel;
    private byte[] color;
    private int[] distance;
    private long[] from;

    private final List<GraphWalkerListener> listeners;

    private static final byte WHITE = 0;
    private static byte GRAY = 1;
    private static byte BLACK = 2;

    GraphWalker(GraphModel<T> graphModel) {
        this.graphModel = graphModel;
        int countOfNodes = graphModel.getCountOfNodes();

        this.color = new byte[countOfNodes];
        Arrays.fill(this.color, WHITE);

        this.distance = new int[countOfNodes];
        this.from = new long[countOfNodes];

        this.listeners = new ArrayList<>();
    }

    void dfs(int i) {
        from[i] = -1;
        depthFirstSearch(i);
        for (int j = 0; j < color.length; j++) {
            if (color[j] == WHITE) {
                from[j] = -1;
                depthFirstSearch(j);
            }
        }
    }

    private void depthFirstSearch(long i) {
        notify(l -> l.nodeIn(String.valueOf(i)));

        System.out.println("in " + i);

        color[(int) i] = GRAY;
        long[] neighbours = graphModel.getNeighbours(i);
        for (long obj : neighbours) {
            if (color[(int) obj] == WHITE) {
                try {
                    Thread.sleep(1000);
                    notify(l -> l.edgeForward(i, obj));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                from[(int) obj] = i;
                depthFirstSearch(obj);
            }
        }

        try {
            Thread.sleep(1000);
            notify(l -> l.nodeOut(String.valueOf(i)));
            if (from[(int) i] != -1)
                notify(l -> l.edgeBack(i, from[(int) i]));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        color[(int) i] = BLACK;
        System.out.println("out " + i);
    }

    void bfs(long u, long v) {
        LinkedList<Long> way; // путь от u до v. Сюда кладем уже верный путь от u до v.
        boolean isWayExists = false;
        Queue<Long> currentVertexQueue = new LinkedList<>();
        currentVertexQueue.add(u);
        color[(int) u] = GRAY;
        distance[(int) u] = WHITE;

        while (!currentVertexQueue.isEmpty()) {
            if (currentVertexQueue.peek() == v) {
                isWayExists = true;
                break;
            }

            for (long obj : graphModel.getNeighbours(currentVertexQueue.peek())) {
                if (color[(int) obj] == WHITE) {
                    color[(int) obj] = GRAY;
                    distance[(int) obj] = distance[Math.toIntExact(currentVertexQueue.peek())] + 1;
                    currentVertexQueue.add(obj);
                    from[(int) obj] = currentVertexQueue.peek();
                }
            }

            currentVertexQueue.poll();
        }

        if (isWayExists) {
            way = wayMaker(u, v, from);

            System.out.print("The way is [ ");

            for (long obj : way) {
                System.out.print(obj + " ");
            }

            System.out.println("]");
            System.out.println("distance = " + distance[(int) v]);
        } else {
            System.out.println("The way doesn't exists");
        }
    }

    void dijkstra(long u) {
        Queue<Long> vertexPriorityQueue = new PriorityQueue<Long>(new CompareByDistance(distance));
        LinkedList<Long> neighbours = new LinkedList<>();
        long[] from = new long[distance.length];
        LinkedList<Long> way;

        for (int i = 0; i < distance.length; i++) {
            distance[i] = Integer.MAX_VALUE;
        }

        distance[(int) u] = 0;
        from[(int) u] = u;
        vertexPriorityQueue.add(u);

        while (!vertexPriorityQueue.isEmpty()) {
            long currentVertex = vertexPriorityQueue.poll();
            color[(int) currentVertex] = GRAY;
            for (long obj : graphModel.getNeighbours(currentVertex)) {
                if (color[(int) obj] == WHITE) {
                    neighbours.add(obj);
                }
            }

            for (long obj : neighbours) {
                if (distance[(int) obj] > distance[(int) currentVertex] + graphModel.getEdge(obj, currentVertex)) {
                    distance[(int) obj] = distance[(int) currentVertex] + graphModel.getEdge(obj, currentVertex);
                    from[(int) obj] = currentVertex;
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

                for (long obj : way) {
                    System.out.print(obj + " ");
                }

                System.out.println("]");
                way.clear();
            }
        }
    }

    private LinkedList<Long> wayMaker(long u, long v, long[] from) {
        LinkedList<Long> way = new LinkedList<>();
        way.add(v);
        long tmp = from[(int) v];
        while (tmp != u) {
            way.addFirst(tmp);
            tmp = from[(int) tmp];
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