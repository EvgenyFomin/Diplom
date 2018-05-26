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
        gsGraphAdapter.initGraph();

        SwingUtilities.invokeLater(() -> {
            DisplayGraph displayGraph = new DisplayGraph(gsGraphAdapter.getGsGraph());
            displayGraph.display();
        });

        // обход в глубину
//        DepthFirstSearch depthFirstSearch = new DepthFirstSearch(gsGraphAdapter, 4343681984L);
//        depthFirstSearch.addListener(gsGraphAdapter);
//        UpdateStatistics updateStatistics = new UpdateStatistics(depthFirstSearch);
//        gsGraphAdapter.setStat(updateStatistics);
//        depthFirstSearch.algorithm();

        // обход в ширину
//        BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch(gsGraphAdapter, 892238166, 1455269899);
//        breadthFirstSearch.addListener(gsGraphAdapter);
//        UpdateStatistics updateStatistics = new UpdateStatistics(breadthFirstSearch);
//        gsGraphAdapter.setStat(updateStatistics);
//        breadthFirstSearch.algorithm();

        // Дейкстра

        Dijkstra dijkstra = new Dijkstra(gsGraphAdapter, 892238166);
        dijkstra.addListener(gsGraphAdapter);
        UpdateStatistics updateStatistics = new UpdateStatistics(dijkstra);
        gsGraphAdapter.setStat(updateStatistics);
        dijkstra.algorithm();
    }
}
