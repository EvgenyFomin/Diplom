package ru.itmo.sortvis;
import ru.itmo.sortvis.ui.SwingVisualisationPanel;

import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SwingVisualisationPanel swingVisualisationPanel = new SwingVisualisationPanel();
                JFrame frame = new JFrame();
                frame.setSize(640, 480);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationByPlatform(true);
                frame.setTitle("Graph Visualisation");

                frame.add(swingVisualisationPanel);
                frame.setVisible(true);

                Graph graph = new Graph();
                graph.addModelListener(swingVisualisationPanel);
                graph.initGraph();
            }
        });
    }
}
