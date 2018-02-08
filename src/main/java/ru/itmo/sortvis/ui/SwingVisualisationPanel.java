package ru.itmo.sortvis.ui;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.Pair;
import ru.itmo.sortvis.Graph;
import ru.itmo.sortvis.GraphModelListener;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SwingVisualisationPanel extends JPanel implements GraphModelListener {

    private static final int N = 20;

    private Graph graph;
    private Map<Integer, Pair<Double, Double>> coord;

    public SwingVisualisationPanel(Graph graph) {
        this.graph = graph;
        this.coord = new HashMap<>();
    }

    private void calculateCoords() {
        // Тут один раз можно сосчитать координаты,
        // записать их в coord. Можно не извращаться с одномерным массивом и использовать,
        // например, конструкцию Map<Integer, Pair<Integer, Integer>>
        // (номеру вершины соответствуют её координаты).

    }

    @Override
    public void modelChanged() {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;

//        ImmutableMap<RenderingHints.Key, Object> rh = ImmutableMap.of(
//                RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON,
//                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
//        );
//        g.setRenderingHints(rh);

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

        g.setStroke(new BasicStroke(1));

        for (int i = 0; i < graph.getVertexCount() / 2; i++) {
            for (int j = 0; j < graph.getVertexCount() / 2; j++) {
                if (graph.getEdge(i, j) == 1) {
                    // обращаться к посчитаным в начале coord
                    g.drawLine(getCoordX(i) * N + x0, getCoordY(i) * N + y0,
                            getCoordX(j) * N + x0, getCoordY(j) * N + y0);
                }
            }
        }
    }

    //

//    private int getCoordX(int vertex) {
//        return (int) Math.round(coord[2 * vertex]);
//    }
//
//    private int getCoordY(int vertex) {
//        return (int) Math.round(coord[2 * vertex + 1]);
//    }
}