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

        GraphWalker_NEW<Integer> graphWalker_new = new GraphWalker_NEW(gsGraphAdapter);
        graphWalker_new.addListener(gsGraphAdapter);
        gsGraphAdapter.initGraph();

        SwingUtilities.invokeLater(() -> {
            DisplayGraph displayGraph = new DisplayGraph(gsGraphAdapter.getGsGraph());
            displayGraph.display();
        });

        // обход в глубину
//        dfs(graphWalker_new, 886609116);
//        dfs(graphWalker_new, 1478011807);
//        dfs(graphWalker_new, 5563593582L);
        // обход в ширину
//        bfs(graphWalker_new, 1478011807, 1620097881);
        // Дейкстра
//        dijkstra(graphWalker, 0);
//        checkMarker(graphWalker_new, 1460516946);
    }

    private static void dfs(GraphWalker_NEW graphWalker_new, long startNode) {
        System.out.println("START!");
        for (int i = 10; i > -1; i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
        }
        graphWalker_new.dfs(startNode);
    }

    private static void bfs(GraphWalker_NEW graphWalker_new, int fromNode, int toNode) {
        System.out.println("START!");
        for (int i = 30; i > -1; i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
        }
        graphWalker_new.bfs(fromNode, toNode);
    }

    private static void dijkstra(GraphWalker graphWalker, int startNode) {
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
        graphWalker.dijkstra(startNode);
    }

    private static void checkMarker(GraphWalker_NEW graphWalker_new, long u) {
        graphWalker_new.checkMarker(u);
    }
}
