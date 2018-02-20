package ru.itmo.sortvis;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

public class MatrixGraph implements Graph<String> {
    private static final int N = 6;
    private int[][] matrix;
    private final String[] data;
    private final List<GraphModelListener> listenerList;

    public MatrixGraph() {
        this.listenerList = new ArrayList<>();
        this.data = new String[N];
    }

    @Override
    public void initGraph() {
        matrix = new int[N][N];
        for (int i = 0; i < N; i++) {
            matrix[i][i] = -1;
            data[i] = RandomStringUtils.randomAlphabetic(3).toUpperCase();
            for (int j = i + 1; j < N; j++) {
                matrix[i][j] = new Random().nextInt(20);
                matrix[j][i] = matrix[i][j];
            }
        }

        graphInitialized();
        print();
    }

    public void addModelListener(GraphModelListener gr) {
        listenerList.add(gr);
    }

    @Override
    public int getVertexCount() {
        return N;
    }

    @Override
    public String getData(int i) {
        return data[i];
    }

    @Override
    public int getEdge(int i, int j) {
        return matrix[i][j];
    }

    @Override
    public LinkedList<Integer> getNeighbours(int i) {
        LinkedList<Integer> neighbourList = new LinkedList<>();
        for (int j = 0; j < N; j++) {
            if (matrix[i][j] >= 1) {
                neighbourList.add(j);
            }
        }
        return neighbourList;
    }

    private void graphInitialized() {
        for (GraphModelListener obj : listenerList) {
            obj.modelChanged();
        }
    }

    void print() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.printf("%4d", matrix[i][j]);
            }
            System.out.println();
        }
    }
}
