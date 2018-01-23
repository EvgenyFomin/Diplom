package ru.itmo.sortvis.ui;

import ru.itmo.sortvis.GraphModelListener;

import javax.swing.*;
import java.awt.*;

public class SwingVisualisationPanel extends JPanel implements GraphModelListener {

    private byte[][] matrix;
    private int[] coord;

    @Override
    public void someChanges(byte[][] matrix, int[] coord) {
        this.matrix = matrix;
        this.coord = coord;
        repaint();

    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        int i = 0, N = 20;
        int x0 = getSize().width / 2, y0 = getSize().height / 2;

        // Двумерная сетка

        while (i < getSize().height) {
            g.drawLine(0, i, getSize().width, i);
            i += N;

        }

        i = 0;

        while (i < getSize().width) {
            g.drawLine(i, 0, i, getSize().height);
            i += N;

        }

        // Граф

        g.setColor(Color.RED);
        while (x0 % N != 0) {
            x0 += 1;

        }

        while (y0 % N != 0) {
            y0 += 1;

        }

        for (i = 0; i < coord.length / 2; i++) {
            for (int j = 0; j < coord.length / 2; j++) {
                if (matrix[i][j] == 1) {
                    g.drawLine(getCoordX(i) * N + x0, getCoordY(i) * N + y0, getCoordX(j) * N + x0, getCoordY(j) * N + y0);

                }

            }

        }

    }

    private int getCoordX(int vertex) {
        return coord[2 * vertex];

    }

    private int getCoordY(int vertex) {
        return coord[2 * vertex + 1];

    }

}