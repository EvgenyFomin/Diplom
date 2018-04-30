package ru.itmo.sortvis;

import java.util.*;

public class MatrixGraph implements GraphModel<String> {
    private final int countOfNodes;
    private final int countOfEdges;
    private final boolean isOrientedGraph;
    private final int[][] matrix;

    public MatrixGraph(int countOfNodes, int countOfEdges, boolean isOrientedGraph, int[][] matrix) {
        this.countOfNodes = countOfNodes;
        this.countOfEdges = countOfEdges;
        this.isOrientedGraph = isOrientedGraph;
        this.matrix = matrix;
    }

    @Override
    public void initGraph() {
        print();
    }

    @Override
    public int getCountOfNodes() {
        return countOfNodes;
    }

    @Override
    public String getData(int i) {
        return "abd";
    }

    @Override
    public int getEdge(int i, int j) {
        return matrix[Math.toIntExact(i)][Math.toIntExact(j)];
    }

    @Override
    public int[] getNeighbours(int i) {
        List<Integer> neighbourList = new ArrayList<>();
        for (int j = 0; j < countOfNodes; j++) {
            if (matrix[i][j] >= 1) {
                neighbourList.add(j);
            }
        }
        return neighbourList.stream().mapToInt(k -> k).toArray();
    }

    private void print() {
        for (int i = 0; i < countOfNodes; i++) {
            for (int j = 0; j < countOfNodes; j++) {
                System.out.printf("%4d", matrix[i][j]);
            }
            System.out.println();
        }
    }
}