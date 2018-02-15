package ru.itmo.sortvis;

import ru.itmo.sortvis.ui.SwingVisualisationPanel;

import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Graph graph = new MatrixGraph();
//                Graph graph = new AdjListGraph();
                graph.initGraph();
                GraphWalker graphWalker = new GraphWalker(graph);

                SwingVisualisationPanel swingVisualisationPanel = new SwingVisualisationPanel(graph);
                JFrame frame = new JFrame();
                frame.setSize(640, 480);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationByPlatform(true);
                frame.setTitle("Graph Visualisation");

                frame.add(swingVisualisationPanel);
                frame.setVisible(true);

                graph.addModelListener(swingVisualisationPanel);

                // обход в глубину
//                graphWalker.dfs(0);
                // обход в ширину
//                graphWalker.bfs(0, 3);
            }
        });
    }
}
