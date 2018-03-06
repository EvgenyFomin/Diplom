package ru.itmo.sortvis;

import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.Viewer;
import ru.itmo.sortvis.ui.SwingVisualisationPanel;

import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GraphModel graphModel = new MatrixGraph();
//                Graph graphModel = new AdjListGraph();
                graphModel.initGraph();

                GraphWalker graphWalker = new GraphWalker(graphModel);
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



                // обход в глубину
                graphWalker.dfs(0);
                // обход в ширину
//                graphWalker.bfs(0, 3);
                // Дейкстра
//                graphWalker.dijkstra(2);
            }
        });
    }
}
