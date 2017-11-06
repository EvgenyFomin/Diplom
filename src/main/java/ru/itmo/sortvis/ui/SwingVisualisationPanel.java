package ru.itmo.sortvis.ui;

import ru.itmo.sortvis.ArrayModelListener;

import javax.swing.*;
import java.awt.*;

public class SwingVisualisationPanel extends JPanel implements ArrayModelListener {

    private static final int MAX_VALUE = 100;
    private int[] currentArray;

    @Override
    public void arrayChanged(int[] newArray) {
        currentArray = newArray;
        repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (currentArray == null) {
            return;
        }

        int height = getHeight();
        int width = getWidth();

        int columnWidth = width / currentArray.length;

        for (int idx = 0; idx < currentArray.length; idx++) {
            int value = currentArray[idx];

            int columnHeight = (int) (height * ((double) value / MAX_VALUE));

            g.fillRect(idx * columnWidth, height - columnHeight, columnWidth, columnHeight);
        }
    }
}