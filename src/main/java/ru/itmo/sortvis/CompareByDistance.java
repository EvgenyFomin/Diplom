package ru.itmo.sortvis;

// Сравнение двух вершин по дистанции

import java.util.Comparator;

class CompareByDistance implements Comparator<Long> {
    int[] distance;

    public CompareByDistance(int[] distance) {
        this.distance = distance;
    }

    @Override
    public int compare(Long obj1, Long obj2) {
        return distance[Math.toIntExact(obj1)] - distance[Math.toIntExact(obj2)];
    }
}