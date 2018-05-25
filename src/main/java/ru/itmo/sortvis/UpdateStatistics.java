package ru.itmo.sortvis;

import java.util.HashMap;

public class UpdateStatistics {
    GraphWalker_Interface alg;

    public UpdateStatistics(GraphWalker_Interface alg) {
        this.alg = alg;
    }

    public HashMap<String, Object> getStat() {
        return alg.statistics();
    }
}
