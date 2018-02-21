package ru.itmo.sortvis;

// Сравнение двух вершин по дистанции

import java.util.Comparator;

class CompareByDistance implements Comparator<Integer> {
    int[] distance;

    public CompareByDistance(int[] distance) {
        this.distance = distance;
    }

    @Override
    public int compare(Integer obj1, Integer obj2) {
        return distance[obj1] - distance[obj2];
    }
}