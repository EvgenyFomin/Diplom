package ru.itmo.sortvis.algo;

import ru.itmo.sortvis.GraphModel;
import ru.itmo.sortvis.GraphWalker;
import ru.itmo.sortvis.Launcher;
import ru.itmo.sortvis.Notifier;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

public class DepthFirstSearch<T> extends GraphWalker {
    private GraphModel<T> graphModel;
    private HashMap<Long, Color> color = new HashMap<>();
    private HashMap<Long, Long> from = new HashMap<>();
    private Set<Long> nodes; // можно ли его не создавать
    private long startNode;

    public DepthFirstSearch(GraphModel<T> graphModel, long startNode) {
        this.graphModel = graphModel;
        nodes = graphModel.getAllIds();
        this.startNode = startNode;
    }

    @Override
    public void algorithm() {
        System.out.println("START!");
        for (int i = 10; i > -1; i--) {
            try {
                Thread.sleep(Launcher.stepSleepTime);
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
                }
            }
        }
        color.clear();
    }

    private void depthFirstSearch(long u) {
        notify(l -> l.nodeIn(u));

        color.put(u, Color.GRAY);
        if (Launcher.enableDebugOutput)
            System.out.println("in " + u);
        long[] neighbours = graphModel.getNeighbours(u);
        for (long obj : neighbours) {
            if (!color.containsKey(obj)) {
                try {
                    Thread.sleep(Launcher.stepSleepTime);
                    notify(l -> l.edgeForward(u, obj));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                from.put(obj, u);
                depthFirstSearch(obj);
            }
        }

        try {
            Thread.sleep(Launcher.stepSleepTime);
            notify(l -> l.nodeOut(u));
            if (from.get(u) != -1L) {
                notify(l -> l.edgeBack(u, from.get(u)));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        color.remove(u);
        color.put(u, Color.BLACK);

        if (Launcher.enableDebugOutput)
            System.out.println("out " + u);
    }

    @Override
    public HashMap<String, Object> statistics() {
        HashMap<String, Object> stat = new HashMap<>();
        stat.put("Size of Color", color.size());
        return stat;
    }
}
