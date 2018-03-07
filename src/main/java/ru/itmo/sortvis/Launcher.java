package ru.itmo.sortvis;

import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {
        System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        GraphModel graphModel = new MatrixGraph();
//                Graph graphModel = new AdjListGraph();
        graphModel.initGraph();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Bridge().convertGraph(graphModel);

//                SwingVisualisationPanel swingVisualisationPanel = new SwingVisualisationPanel(graphModel);
//                JFrame frame = new JFrame();
//                frame.setSize(640, 480);
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                frame.setLocationByPlatform(true);
//                frame.setTitle("Graph Visualisation");
//
//                frame.add(swingVisualisationPanel);
//                frame.setVisible(true);
//
//                graphModel.addModelListener(swingVisualisationPanel);

//                org.graphstream.graph.Graph graph2 = new SingleGraph("Tutorial 1");
//
//                graph2.addNode("A");
//                graph2.addNode("B");
//                graph2.addNode("C");
//                graph2.addEdge("AB", "A", "B");
//                graph2.addEdge("BC", "B", "C");
//                graph2.addEdge("CA", "C", "A");
//
//                Viewer display = graph2.display();
//                Viewer viewer = new Viewer(graph2, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);


            }
        });

        GraphWalker graphWalker = new GraphWalker(graphModel);
        // обход в глубину
        System.out.println("START!");
        for (int i = 5; i > -1; i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
        }

        graphWalker.dfs(0);
        // обход в ширину
//        graphWalker.bfs(0, 3);
        // Дейкстра
//        graphWalker.dijkstra(2);

    }
}
