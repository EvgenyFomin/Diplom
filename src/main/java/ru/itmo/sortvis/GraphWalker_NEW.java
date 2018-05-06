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
                    from.put(obj, -1L);
                    depthFirstSearch(obj);
                }
            }
        }
        color.clear();
    }

    private void depthFirstSearch(long i) {
        notify(l -> l.nodeIn(String.valueOf(i)));

        color.put(i, Color.GRAY);
//        System.out.println("in " + i);
        long[] neighbours = graphModel.getNeighbours(i);
        for (long obj : neighbours) {
            if (!color.containsKey(obj)) {
                try {
                    Thread.sleep(10);
                    notify(l -> l.edgeForward(i, obj));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                from.put(obj, i);
                depthFirstSearch(obj);
            }
        }

        try {
            Thread.sleep(10);
            notify(l -> l.nodeOut(String.valueOf(i)));
            if (from.get(i) != -1L) {
                notify(l -> l.edgeBack(i, from.get(i)));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        color.remove(i);
        color.put(i, Color.BLACK);

//        System.out.println("out " + i);
    }

    public void addListener(GraphWalkerListener l) {
        listeners.add(l);
    }

    private void notify(Consumer<GraphWalkerListener> consumer) {
        listeners.forEach(consumer::accept);
    }
}
