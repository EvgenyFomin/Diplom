package ru.itmo.sortvis;

// Сравнение двух вершин по дистанции

import java.util.Comparator;

class CompareByDistance implements Comparator<Long> {
    double[] distance;

    public CompareByDistance(double[] distance) {
        this.distance = distance;
    }

    @Override
    public int compare(Long obj1, Long obj2) {
        return ((int) distance[Math.toIntExact(obj1)] * 1000000 - (int) distance[Math.toIntExact(obj2)] * 1000000);
    }
}