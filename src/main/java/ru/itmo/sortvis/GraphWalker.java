package ru.itmo.sortvis;

import java.util.HashMap;

public abstract class GraphWalker extends Notifier {
    public abstract void algorithm();

    public abstract HashMap<String, Object> statistics();
}
