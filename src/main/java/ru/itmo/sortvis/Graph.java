package ru.itmo.sortvis;

import java.util.Random;

public class Graph {
    private byte[][] matrix = new byte[20][20];
    private byte[] countOfNeighbours = new byte[20];

    void initGraph() {
        for (int i = 0; i < 20; i++) {
            matrix[i][i] = -1;
            for (int j = i + 1; j < 20; j++) {
                if (countOfNeighbours[i] == 4) {
                    matrix[i][i] = -1;
                    break;

                } else if (countOfNeighbours[j] < 4) {
                    matrix[i][j] = (byte) new Random().nextInt(2);
                    matrix[j][i] = matrix[i][j];
                    countOfNeighbours[i] += matrix[i][j];
                    countOfNeighbours[j] += matrix[i][j];

                }


            }

        }


    }

    public void print() {
        int countOfMinusOne = 0;
        int countOfVertexWithGT4Nb = 0;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                System.out.print(matrix[i][j] + " ");
                if (matrix[i][j] == -1) countOfMinusOne++;

            }
            System.out.println();

        }

        System.out.println();
        System.out.println(countOfMinusOne);
        for (byte o: countOfNeighbours) {
            if (o > 4) countOfVertexWithGT4Nb++;

        }

        System.out.println("Число вершин с соседями больше 4 = " + countOfVertexWithGT4Nb);

    }

}
