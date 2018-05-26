package ru.itmo.sortvis;

import java.util.HashMap;

public class UpdateStatistics {
    GraphWalker alg;

    public UpdateStatistics(GraphWalker alg) {
        this.alg = alg;
    }

    public HashMap<String, Object> getStat() {
        return alg.statistics();
    }
}
