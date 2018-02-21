package ru.itmo.sortvis.ui;

import com.google.common.collect.ImmutableMap;
import javafx.util.Pair;
import ru.itmo.sortvis.GraphModel;
import ru.itmo.sortvis.GraphModelListener;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SwingVisualisationPanel extends JPanel implements GraphModelListener {

    private static final int sizeOfGrid = 20;

    private GraphModel graphModel;
    private int vertexCount;
    private Map<Integer, Pair<Double, Double>> coord;
    // координаты для вершин, чтобы не залазили на граф, как если использовать координаты самих точек
    private Map<Integer, Pair<Double, Double>> vertexCoord;

    public SwingVisualisationPanel(GraphModel graphModel) {
        this.graphModel = graphModel;
        this.coord = new HashMap<>();
        this.vertexCoord = new HashMap<>();
        this.vertexCount = graphModel.getVertexCount();
        calculateCoords();
    }

    private void calculateCoords() {
        coord.put(0, new Pair(vertexCount / 2.0, vertexCount / 2.0));
        vertexCoord.put(0, new Pair(vertexCount / 2.0 + 0.4, vertexCount / 2.0 + 0.4));
        double angle = 2 * Math.PI / vertexCount;
        for (int i = 1; i < vertexCount; i++) {
            coord.put(i, new Pair<>(Math.cos(angle) * coord.get(i - 1).getKey() - Math.sin(angle) * coord.get(i - 1).getValue(),
                    Math.sin(angle) * coord.get(i - 1).getKey() + Math.cos(angle) * coord.get(i - 1).getValue()));
            vertexCoord.put(i, new Pair<>(Math.cos(angle) * vertexCoord.get(i - 1).getKey() - Math.sin(angle) * vertexCoord.get(i - 1).getValue(),
                    Math.sin(angle) * vertexCoord.get(i - 1).getKey() + Math.cos(angle) * vertexCoord.get(i - 1).getValue()));
        }
    }

    @Override
    public void modelChanged() {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;

        ImmutableMap<RenderingHints.Key, Object> rh = ImmutableMap.of(
                RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON,
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
        );
        g.setRenderingHints(rh);

        int x0 = getSize().width / 2, y0 = getSize().height / 2;

        g.setColor(Color.LIGHT_GRAY);

        // Двумерная сетка

        for (int i = 0; i < getSize().height; i += sizeOfGrid) {
            g.drawLine(0, i, getSize().width, i);
        }

        for (int i = 0; i < getSize().width; i += sizeOfGrid) {
            g.drawLine(i, 0, i, getSize().height);
        }

        g.setColor(Color.BLACK);

        // Определение ближайших начальных координат, которые делятся на N

        while (x0 % sizeOfGrid != 0) {
            x0 += 1;
        }

        while (y0 % sizeOfGrid != 0) {
            y0 += 1;
        }

        // Граф

        g.setStroke(new BasicStroke(1));

        for (int i = 0; i < vertexCount; i++) {
            for (int j = i + 1; j < vertexCount; j++) {
                if (graphModel.getEdge(i, j) != 0) {
                    g.drawLine(
                            (int) Math.round(coord.get(i).getKey() * sizeOfGrid + x0),
                            (int) Math.round(coord.get(i).getValue() * sizeOfGrid + y0),
                            (int) Math.round(coord.get(j).getKey() * sizeOfGrid + x0),
                            (int) Math.round(coord.get(j).getValue() * sizeOfGrid + y0)
                    );
                }
            }
        }

        g.setStroke(new BasicStroke(4));
        g.setColor(Color.RED);

        for (int i = 0; i < vertexCount; i++) {
            g.drawLine(
                    (int) Math.round(coord.get(i).getKey() * sizeOfGrid + x0),
                    (int) Math.round(coord.get(i).getValue() * sizeOfGrid + y0),
                    (int) Math.round(coord.get(i).getKey() * sizeOfGrid + x0),
                    (int) Math.round(coord.get(i).getValue() * sizeOfGrid + y0)
            );
            g.drawString(Integer.toString(i),
                    (int) Math.round(vertexCoord.get(i).getKey() * sizeOfGrid + x0) - 4,
                    (int) Math.round(vertexCoord.get(i).getValue() * sizeOfGrid + y0) + 4
            );
        }
    }
}