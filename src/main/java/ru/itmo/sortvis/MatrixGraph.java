package ru.itmo.sortvis;

import org.apache.commons.lang3.tuple.Pair;

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
    public String getData(long i) {
        return "abd";
    }

    @Override
    public int getEdge(long i, long j) {
        return matrix[Math.toIntExact(i)][Math.toIntExact(j)];
    }

    @Override
    public long[] getNeighbours(long i) {
        List<Long> neighbourList = new ArrayList<>();
        for (int j = 0; j < countOfNodes; j++) {
            if (matrix[(int) i][j] >= 1) {
                neighbourList.add((long) j);
            }
        }
        return neighbourList.stream().mapToLong(k -> k).toArray();
    }

    @Override
    public Set<Long> getAllIds() {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Pair<Long, Long>> getEdges() {
        // TODO implement
        throw new UnsupportedOperationException();
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