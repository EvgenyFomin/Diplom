package ru.itmo.sortvis;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

public class GraphWalker_NEW<T> {
    private GraphModel<T> graphModel;
    private HashMap<Long, Color> color = new HashMap<>();
    private HashMap<Long, Long> from = new HashMap<>();
    private Set<Long> nodes = new HashSet<>();
    private HashMap<Long, Integer> distance = new HashMap<>();
    private LinkedList<Long> way = new LinkedList<>();

    private final List<GraphWalkerListener> listeners;

    GraphWalker_NEW(GraphModel graphModel) {
        this.graphModel = graphModel;
        nodes = graphModel.getAllIds();
        this.listeners = new ArrayList<>();
    }

    public void dfs(long i) {
        from.put(i, -1L);
        depthFirstSearch(i);
        if (color.size() < nodes.size()) {
            for (long obj : nodes) {
                if (!color.containsKey(obj)) {
                    System.out.println("---New connectivity component---");
                    from.put(obj, -1L);
                    depthFirstSearch(obj);
                    from.clear();
                }
            }
        }
        color.clear();
    }

    private void depthFirstSearch(long u) {
        notify(l -> l.nodeIn(String.valueOf(u)));

        color.put(u, Color.GRAY);
        System.out.println("in " + u);
        long[] neighbours = graphModel.getNeighbours(u);
        for (long obj : neighbours) {
            if (!color.containsKey(obj)) {
                try {
                    Thread.sleep(10);
                    notify(l -> l.edgeForward(u, obj));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                from.put(obj, u);
                depthFirstSearch(obj);
            }
        }

        try {
            Thread.sleep(10);
            notify(l -> l.nodeOut(String.valueOf(u)));
            if (from.get(u) != -1L) {
                notify(l -> l.edgeBack(u, from.get(u)));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        color.remove(u);
        color.put(u, Color.BLACK);

        System.out.println("out " + u);
    }

    public void bfs(long u, long v) {
        notify(l -> l.nodeIn(String.valueOf(u)));
        boolean isWayExists = false;
        if (u == v) {
            notify(l -> l.nodeOut(String.valueOf(u)));
            isWayExists = true;
        } else {

            Queue<Long> nodes = new LinkedList<>();
            nodes.add(u);
            color.put(u, Color.BLACK);
            distance.put(u, 0);
            from.put(u, -1L);

            while (!nodes.isEmpty()) {
                long currentNode = nodes.poll();
                for (long currentNeighbour : graphModel.getNeighbours(currentNode)) {
                    if (!color.containsKey(currentNeighbour)) {
                        try {
                            notify(l -> l.edgeForward(currentNode, currentNeighbour));
                            notify(l -> l.nodeIn(String.valueOf(currentNeighbour)));
                            if (from.get(currentNode) != -1L)
                                notify(l -> l.edgeBack(currentNode, from.get(currentNode)));
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        color.put(currentNeighbour, Color.BLACK);
                        distance.put(currentNeighbour, distance.get(currentNode) + 1);
                        nodes.add(currentNeighbour);
                        from.put(currentNeighbour, currentNode);
                    }

                    if (currentNeighbour == v) {
                        isWayExists = true;
                        nodes.clear();
                        notify(l -> l.nodeOut(String.valueOf(currentNeighbour)));
                        break;
                    }
                    notify(l -> l.nodeOut(String.valueOf(currentNode)));
                }
            }
        }

        if (isWayExists) {
            wayMaker(u, v);
            System.out.println("Way:");

            for (long currentNode : way) {
                System.out.println(currentNode);
            }

            System.out.println("Distance = " + distance.get(v));
        } else {
            System.out.println("Node " + v + " is unreachable!");
        }
    }

    public void dijkstra(long u) {
        Queue<Long> nodePriorityQueue = new PriorityQueue<Long>(new CompareByDistance_NEW(distance));
        LinkedList<Long> neighbours = new LinkedList<>();

        distance.put(u, 0);

        while (!neighbours.isEmpty()) {
            long currentNode = neighbours.poll();
            color.put(currentNode, Color.BLACK);

            for (long currentNeighbour : graphModel.getNeighbours(currentNode)) {
                if (!color.containsKey(currentNeighbour)) {
                    neighbours.add(currentNeighbour);
                }
            }

            for (long currentNeighbour : neighbours) {
                if (!distance.containsKey(currentNeighbour)) {
                    distance.put(currentNeighbour, graphModel.getEdge(currentNode, currentNeighbour));
                } else if (distance.get(currentNeighbour) > distance.get(currentNode) + graphModel.getEdge(currentNode, currentNeighbour)) {
                    distance.remove(currentNeighbour);
                    distance.put(currentNeighbour, graphModel.getEdge(currentNode, currentNeighbour));
                }
            }

            nodePriorityQueue.addAll(neighbours);
            neighbours.clear();
        }

        for (Map.Entry<Long, Integer> pair : distance.entrySet()) {
            System.out.println("Distance from " + u + " to " + pair.getKey() + " = " + pair.getValue());
        }
    }

    public void checkMarker(long u) {
        while (true) {
            try {
                notify(l -> l.nodeIn(String.valueOf(u)));
                Thread.sleep(1000);
                notify(l -> l.nodeOut(String.valueOf(u)));
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void wayMaker(long u, long v) {
        long currentNode = v;
        way.add(currentNode);
        while (currentNode != u) {
            currentNode = from.get(currentNode);
            way.addFirst(currentNode);
        }
    }

    public void addListener(GraphWalkerListener l) {
        listeners.add(l);
    }

    private void notify(Consumer<GraphWalkerListener> consumer) {
        listeners.forEach(consumer::accept);
    }
}
