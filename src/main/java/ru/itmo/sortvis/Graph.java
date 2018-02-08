package ru.itmo.sortvis;

import java.util.*;

class Graph {
    private static final int N = 5;
    private byte[][] matrix;
    private final List<GraphModelListener> listenerList;
    private double[] coord;

    Graph() {
        listenerList = new ArrayList<>();
        coord = new double[2 * N];
        for (int i = 0; i < coord.length; i++) {
            coord[i] = N + 1;
        }
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
        giveCoordinates();
//        print();
        graphInitialized();
    }

    private void giveCoordinates() {

        // Просто начальные координаты первой вершины. Могут быть любыми, однако в зависимости от них меняется общий размер графа.

        setCoordX(0, 15);
        setCoordY(0, 0);

        // Для равномерного распределения вершин по окружности используется матрица поворота вектора на угол angle.

        double angle = 2 * Math.PI / N;
        for (int i = 1; i < N; i++) {
            setCoordX(i, Math.cos(angle) * getCoordX(i - 1) - Math.sin(angle) * getCoordY(i - 1));
            setCoordY(i, Math.sin(angle) * getCoordX(i - 1) + Math.cos(angle) * getCoordY(i - 1));
        }
    }

    void print() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Косинус pi/2 = " + Math.cos(Math.PI));
        System.out.println();

        for (int i = 0; i < N; i++) {
            System.out.println("Координаты вершины " + i + " (" + getCoordX(i) + ", " + getCoordY(i) + ")");
        }

        System.out.println();

        for (int i = 0; i < N; i++) {
            System.out.println("Координаты вершины " + i + " (" + Math.round(getCoordX(i)) + ", " + Math.round(getCoordY(i)) + ")");
        }
    }

    void addModelListener(GraphModelListener gr) {
        listenerList.add(gr);
    }

    private void graphInitialized() {
        for (GraphModelListener obj : listenerList) {
            obj.someChanges(matrix, coord);
        }
    }

    private double getCoordX(int vertex) {
        return coord[2 * vertex];
    }

    private double getCoordY(int vertex) {
        return coord[2 * vertex + 1];
    }

    private void setCoordX(int vertex, double x) {
        coord[2 * vertex] = x;
    }

    private void setCoordY(int vertex, double y) {
        coord[2 * vertex + 1] = y;
    }

}
