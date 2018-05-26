package ru.itmo.sortvis;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

public class Dijkstra<T> implements GraphWalker_Interface {
    private GraphModel<T> graphModel;
    private HashMap<Long, Color> color = new HashMap<>();
    private HashMap<Long, Long> from = new HashMap<>();
    private HashMap<Long, Integer> distance = new HashMap<>();
    private long startNode;

    private final List<GraphWalkerListener> listeners;

    Dijkstra(GraphModel<T> graphModel, long startNode) {
        this.graphModel = graphModel;
        this.listeners = new ArrayList<>();
        this.startNode = startNode;
    }

    @Override
    public void algorithm() {
        System.out.println("START!");
        for (int i = 10; i > -1; i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
        }

        Queue<Long> nodePriorityQueue = new PriorityQueue<Long>(new CompareByDistance_NEW(distance));
        LinkedList<Long> neighbours = new LinkedList<>();

        distance.put(startNode, 0);
        nodePriorityQueue.add(startNode);
        from.put(startNode, -1L);

        while (!nodePriorityQueue.isEmpty()) {
            long currentNode = nodePriorityQueue.poll();
            color.put(currentNode, Color.BLACK);
            notify(l -> l.nodeIn(String.valueOf(currentNode)));
            if (from.get(currentNode) != -1L) {
                notify(l -> l.edgeBack(currentNode, from.get(currentNode)));
            }

            for (long currentNeighbour : graphModel.getNeighbours(currentNode)) {
                if (!color.containsKey(currentNeighbour)) {
                    neighbours.add(currentNeighbour);
                    notify(l -> l.edgeForward(currentNode, currentNeighbour));
                    if (from.containsKey(currentNeighbour)) {
                        from.remove(currentNeighbour);
                    }
                    from.put(currentNeighbour, currentNode);
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

            notify(l -> l.nodeOut(String.valueOf(currentNode)));
            
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Map.Entry<Long, Integer> pair : distance.entrySet()) {
            System.out.println("Distance from " + startNode + " to " + pair.getKey() + " = " + pair.getValue());
        }
    }

    @Override
    public HashMap<String, Object> statistics() {
        return null;
    }

    public void addListener(GraphWalkerListener l) {
        listeners.add(l);
    }

    private void notify(Consumer<GraphWalkerListener> consumer) {
        listeners.forEach(consumer::accept);
    }
}
