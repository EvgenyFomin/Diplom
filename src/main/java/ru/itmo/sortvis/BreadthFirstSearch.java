package ru.itmo.sortvis;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

public class BreadthFirstSearch<T> implements GraphWalker_Interface {
    private GraphModel<T> graphModel;
    private HashMap<Long, Color> color = new HashMap<>();
    private HashMap<Long, Long> from = new HashMap<>();
    private Set<Long> nodes = new HashSet<>(); // можно ли его не создавать
    private long startNode, endNode;
    private HashMap<Long, Integer> distance = new HashMap<>();
    private LinkedList<Long> way = new LinkedList<>();

    private final List<GraphWalkerListener> listeners;

    BreadthFirstSearch(GraphModel<T> graphModel, long startNode, long endNode) {
        this.graphModel = graphModel;
        nodes = graphModel.getAllIds();
        this.listeners = new ArrayList<>();
        this.startNode = startNode;
        this.endNode = endNode;
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
        notify(l -> l.nodeIn(String.valueOf(startNode)));
        boolean isWayExists = false;
        if (startNode == endNode) {
            notify(l -> l.nodeOut(String.valueOf(startNode)));
            isWayExists = true;
        } else {

            Queue<Long> nodes = new LinkedList<>();
            nodes.add(startNode);
            color.put(startNode, Color.BLACK);
            distance.put(startNode, 0);
            from.put(startNode, -1L);

            while (!nodes.isEmpty()) {
                long currentNode = nodes.poll();
                for (long currentNeighbour : graphModel.getNeighbours(currentNode)) {
                    if (!color.containsKey(currentNeighbour)) {
                        try {
                            notify(l -> l.edgeForward(currentNode, currentNeighbour));
                            notify(l -> l.nodeIn(String.valueOf(currentNeighbour)));
                            if (from.get(currentNode) != -1L)
                                notify(l -> l.edgeBack(currentNode, from.get(currentNode)));
                            Thread.sleep(30);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        color.put(currentNeighbour, Color.BLACK);
                        distance.put(currentNeighbour, distance.get(currentNode) + 1);
                        nodes.add(currentNeighbour);
                        from.put(currentNeighbour, currentNode);
                    }

                    if (currentNeighbour == endNode) {
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
            wayMaker(startNode, endNode);
            System.out.println("Way:");

            for (long currentNode : way) {
                System.out.println(currentNode);
            }

            System.out.println("Distance = " + distance.get(endNode));
        } else {
            System.out.println("Node " + endNode + " is unreachable!");
        }
    }

    private void wayMaker(long startNode, long endNode) {
        long currentNode = endNode;
        way.add(currentNode);
        while (currentNode != startNode) {
            currentNode = from.get(currentNode);
            way.addFirst(currentNode);
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
