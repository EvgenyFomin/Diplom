package ru.itmo.sortvis.ui;

import ru.itmo.sortvis.GraphModelListener;

import javax.swing.*;
import java.awt.*;

public class SwingVisualisationPanel extends JPanel implements GraphModelListener {

    private static final int N = 20;
    private byte[][] matrix;
    private double[] coord;

    @Override
    public void someChanges(byte[][] matrix, double[] coord) {
        this.matrix = matrix;
        this.coord = coord;
        repaint();
    }


    @Override
    public void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;

        int x0 = getSize().width / 2, y0 = getSize().height / 2;

        g.setColor(Color.LIGHT_GRAY);

        // Двумерная сетка

        for (int i = 0; i < getSize().height; i += N) {
            g.drawLine(0, i, getSize().width, i);
        }

        for (int i = 0; i < getSize().width; i += N) {
            g.drawLine(i, 0, i, getSize().height);
        }

        // Граф

        g.setColor(Color.BLACK);

        // Определение ближайших начальных координат, которые делятся на N

        while (x0 % N != 0) {
            x0 += 1;
        }

        while (y0 % N != 0) {
            y0 += 1;
        }
        
        g.setStroke(new BasicStroke(3));

        for (int i = 0; i < coord.length / 2; i++) {
            for (int j = 0; j < coord.length / 2; j++) {
                if (matrix[i][j] == 1) {
                    g.drawLine(getCoordX(i) * N + x0, getCoordY(i) * N + y0,
                            getCoordX(j) * N + x0, getCoordY(j) * N + y0);
                }
            }
        }
    }

    private int getCoordX(int vertex) {
        return (int) Math.round(coord[2 * vertex]);
    }

    private int getCoordY(int vertex) {
        return (int) Math.round(coord[2 * vertex + 1]);
    }
}