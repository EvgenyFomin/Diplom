package ru.itmo.sortvis;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Notifier {
    private final List<GraphWalkerListener> listeners;

    public Notifier() {
        listeners = new ArrayList<>();
    }

    public void addListener(GraphWalkerListener l) {
        listeners.add(l);
    }

    protected void notify(Consumer<GraphWalkerListener> consumer) {
        listeners.forEach(consumer::accept);
    }
}
