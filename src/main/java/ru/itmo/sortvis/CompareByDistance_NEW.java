package ru.itmo.sortvis;

import java.util.Comparator;
import java.util.HashMap;

public class CompareByDistance_NEW implements Comparator<Long> {
    HashMap<Long, Integer> distance = new HashMap<>();

    public CompareByDistance_NEW(HashMap<Long, Integer> distance) {
        this.distance = distance;
    }

    @Override
    public int compare(Long o1, Long o2) {
        return distance.get(o1) - distance.get(o1);
    }
}
