package ru.itmo.sortvis;

import java.util.*;

public class Graph {
    private static final int N = 20;
    private byte[][] matrix;
    private final List<GraphModelListener> listenerList;

    public Graph() {
        listenerList = new ArrayList<>();
    }

    void initGraph() {
        matrix = new byte[N][N];
        for (int i = 0; i < N; i++) {
            matrix[i][i] = -1;
            for (int j = i + 1; j < N; j++) {
                matrix[i][j] = (byte) new Random().nextInt(2);
                matrix[j][i] = matrix[i][j];
            }
        }
//        print();
        graphInitialized();
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

    void addModelListener(GraphModelListener gr) {
        listenerList.add(gr);
    }

    private void graphInitialized() {
        for (GraphModelListener obj : listenerList) {
            obj.modelChanged();
        }
    }

    public int getVertexCount() {
        return N;
    }

    public byte getEdge(int i, int j) {
        return matrix[i][j];
    }
}
