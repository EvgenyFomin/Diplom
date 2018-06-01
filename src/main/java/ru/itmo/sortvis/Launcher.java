package ru.itmo.sortvis;

import ru.itmo.sortvis.XMLMapParser.JAXBReader;
import ru.itmo.sortvis.XMLMapParser.XMLParser;
import ru.itmo.sortvis.ui.DisplayGraph;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher {
    private static final GraphParserService graphParserService = new GraphParserService();

    private static final ExecutorService pool = Executors.newFixedThreadPool(10);

    public static int stepSleepTime;
    public static boolean displayStatistics;
    public static boolean enableDebugOutput;

    public static void launch(int stepSleepTime, boolean displayStatistics) {
        Launcher.stepSleepTime = stepSleepTime;
        Launcher.displayStatistics = displayStatistics;

        Notifier notifier = new Notifier();

        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");


        runTaskInBG(new Runnable() {
            @Override
            public void run() {
                try {
                    JAXBReader reader = new JAXBReader();
                    GraphModel graphModel = null;
                    int N = 4;
                    GraphModel[] graphModels = new GraphModel[N];
                    try {
                        // Плохой код
//                        graphModels[0] = graphParserService.parse("src/main/resources/Graph.txt");
//                        graphModels[1] = graphParserService.parse("src/main/resources/Graph.txt");
//                        graphModels[2] = graphParserService.parse("src/main/resources/Graph.txt");
//                        graphModels[3] = graphParserService.parse("src/main/resources/Graph.txt");
                        graphModels[0] = reader.parse("src/main/resources/o-kotlin-north.osm");
                        graphModels[1] = reader.parse("src/main/resources/o-kotlin-north.osm");
                        graphModels[2] = reader.parse("src/main/resources/o-kotlin-north.osm");
                        graphModels[3] = reader.parse("src/main/resources/o-kotlin-north.osm");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // плохо что такой путь передаём

//        GraphModel graphModel = graphParserService.parse(new File("src/main/resources/Graph.txt"));
//        graphModel.initGraph();
//
//        GraphModel graphModel = new AdjListGraph();

                    GsGraphAdapter[] gsGraphAdapters = new GsGraphAdapter[N];
                    for (int i = 0; i < N; i++) {
                        gsGraphAdapters[i] = new GsGraphAdapter(graphModels[i]);
                        gsGraphAdapters[i].initGraph();
                    }

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < N; i++) {
                                DisplayGraph displayGraph = new DisplayGraph(gsGraphAdapters[i].getGsGraph());
                                displayGraph.display(i, N);
                            }
                        }
                    });


//                // обход в глубину
                    runTaskInBG(new Runnable() {
                        @Override
                        public void run() {
                            DepthFirstSearch depthFirstSearch = new DepthFirstSearch(gsGraphAdapters[0], 892238166);
                            depthFirstSearch.addListener(gsGraphAdapters[0]);
                            UpdateStatistics updateStatistics0 = new UpdateStatistics(depthFirstSearch);
                            gsGraphAdapters[0].setStat(updateStatistics0);
                            depthFirstSearch.algorithm();
                            System.out.println(gsGraphAdapters[0].getStatistics());
                        }
                    });
//
//                    runTaskInBG(new Runnable() {
//                        @Override
//                        public void run() {
//                            BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch(gsGraphAdapters[1], 892238166, 1N55269899);
//                            breadthFirstSearch.addListener(gsGraphAdapter);
//                            UpdateStatistics updateStatistics = new UpdateStatistics(breadthFirstSearch);
//                            gsGraphAdapter.setStat(updateStatistics);
//                            breadthFirstSearch.algorithm();
//
//                        }
//                    });
//                    runTaskInBG(new Runnable() {
//                        @Override
//                        public void run() {
//                            Dijkstra dijkstra = new Dijkstra(gsGraphAdapters[2], 892238166);
//                            dijkstra.addListener(gsGraphAdapters[2]);
//                            UpdateStatistics updateStatistics = new UpdateStatistics(dijkstra);
//                            gsGraphAdapters[2].setStat(updateStatistics);
//                            dijkstra.algorithm();
//                        }
//                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void runTaskInBG(Runnable task) {
        try {
            pool.submit(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
