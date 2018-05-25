package ru.itmo.sortvis;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

public class DepthFirstSearch<T> implements GraphWalker_Interface {
    private GraphModel<T> graphModel;
    private HashMap<Long, Color> color = new HashMap<>();
    private HashMap<Long, Long> from = new HashMap<>();
    private Set<Long> nodes = new HashSet<>();
    private long startNode;

    private final List<GraphWalkerListener> listeners;

    DepthFirstSearch(GraphModel<T> graphModel, long startNode) {
        this.graphModel = graphModel;
        nodes = graphModel.getAllIds();
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
        from.put(startNode, -1L);
        depthFirstSearch(startNode);
        if (color.size() < nodes.size()) {
            for (long obj : nodes) {
                if (!color.containsKey(obj)) {
                    System.out.println("---New connectivity component---");
                    from.put(obj, -1L);
                    depthFirstSearch(obj);
                    from.clear();
                    color.clear();
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
                    Thread.sleep(100);
                    notify(l -> l.edgeForward(u, obj));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                from.put(obj, u);
                depthFirstSearch(obj);
            }
        }

        try {
            Thread.sleep(100);
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

    @Override
    public HashMap<String, Object> statistics() {
        HashMap<String, Object> stat = new HashMap<>();
        stat.put("Size of Color", color.size());
        return stat;
    }

    public void addListener(GraphWalkerListener l) {
        listeners.add(l);
    }

    private void notify(Consumer<GraphWalkerListener> consumer) {
        listeners.forEach(consumer::accept);
    }

}
