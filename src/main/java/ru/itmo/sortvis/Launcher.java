package ru.itmo.sortvis;

import ru.itmo.sortvis.XMLMapParser.JAXBReader;
import ru.itmo.sortvis.XMLMapParser.XMLParser;
import ru.itmo.sortvis.ui.DisplayGraph;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

public class Launcher {
    private static final GraphParserService graphParserService = new GraphParserService();

    public static void main(String[] args) throws IOException, JAXBException {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        JAXBReader reader = new JAXBReader();
        GraphModel graphModel = reader.parse("src/main/resources/o-kotlin-north.osm");

        // плохо что такой путь передаём

//        GraphModel graphModel = graphParserService.parse(new File("src/main/resources/Graph.txt"));
//        graphModel.initGraph();
//
//        GraphModel graphModel = new AdjListGraph();

        GsGraphAdapter gsGraphAdapter = new GsGraphAdapter(graphModel);
//        GraphWalker graphWalker = new GraphWalker(gsGraphAdapter);
//        graphWalker.addListener(gsGraphAdapter);

        GraphWalker_NEW graphWalker_new = new GraphWalker_NEW(gsGraphAdapter);
        graphWalker_new.addListener(gsGraphAdapter);
        gsGraphAdapter.initGraph();

        SwingUtilities.invokeLater(() -> {
            DisplayGraph displayGraph = new DisplayGraph(gsGraphAdapter.getGsGraph());
            displayGraph.display();
        });

        // обход в глубину
//        dfs(graphWalker_new, 886609116);
        // обход в ширину
//        bfs(graphWalker, 0, 3);
        // Дейкстра
//        dijkstra(graphWalker, 0);
    }

    private static void dfs(GraphWalker_NEW graphWalker_new, long startVertex) {
        System.out.println("START!");
        for (int i = 5; i > -1; i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
        }
        graphWalker_new.dfs(startVertex);
    }

    private static void bfs(GraphWalker graphWalker, int fromVertex, int toVertex) {
        System.out.println("START!");
        for (int i = 5; i > -1; i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
        }
        graphWalker.bfs(fromVertex, toVertex);
    }

    private static void dijkstra(GraphWalker graphWalker, int startVertex) {
//        GsGraphAdapter.initNodesData();
//        GsGraphAdapter.initEdgesWeight();
        System.out.println("START!");
        for (int i = 5; i > -1; i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
        }
        graphWalker.dijkstra(startVertex);
    }
}
