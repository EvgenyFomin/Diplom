package ru.itmo.sortvis;

import java.util.*;

public class MatrixGraph implements Graph {
    private static final int N = 20;
    private int[][] matrix;
    private final List<GraphModelListener> listenerList;

    public MatrixGraph() {
        listenerList = new ArrayList<>();
    }

    @Override
    public void initGraph() {
        matrix = new int[N][N];
        for (int i = 0; i < N; i++) {
            matrix[i][i] = -1;
            for (int j = i + 1; j < N; j++) {
                matrix[i][j] = new Random().nextInt(2);
                matrix[j][i] = matrix[i][j];
            }
        }
        graphInitialized();
    }

    public void addModelListener(GraphModelListener gr) {
        listenerList.add(gr);
    }

    @Override
    public int getVertexCount() {
        return N;
    }

    @Override
    public int getEdge(int i, int j) {
        return matrix[i][j];
    }

    private void graphInitialized() {
        for (GraphModelListener obj : listenerList) {
            obj.modelChanged();
        }
    }

    //    void print() {
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
//                System.out.print(matrix[i][j] + " ");
//            }
//            System.out.println();
//        }
//
//        for (int i = 0; i < N; i++) {
//            System.out.println("Координаты вершины " + i + " (" + getCoordX(i) + ", " + getCoordY(i) + ")");
//        }
//
//        System.out.println();
//
//        for (int i = 0; i < N; i++) {
//            System.out.println("Координаты вершины " + i + " (" + Math.round(getCoordX(i)) + ", " + Math.round(getCoordY(i)) + ")");
//        }
//    }
}
