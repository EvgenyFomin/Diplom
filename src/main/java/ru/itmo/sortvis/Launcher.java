package ru.itmo.sortvis;

import ru.itmo.sortvis.XMLMapParser.JAXBReader;
import ru.itmo.sortvis.algo.DepthFirstSearch;
import ru.itmo.sortvis.ui.DisplayGraph;

import javax.swing.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher {
    public static final String ALGO_PACKAGE = "ru.itmo.sortvis";

    private static final GraphParserService graphParserService = new GraphParserService();

    private static final ExecutorService pool = Executors.newFixedThreadPool(10);

    public static int stepSleepTime;
    public static boolean displayStatistics;
    public static boolean enableDebugOutput;
    public static boolean enableMarker;

    public static void launch(List<Class<? extends GraphWalker>> algorithms, int stepSleepTime, boolean displayStatistics,
                              boolean enableDebugOutput, boolean enableMarker, String startNode, String endNode) {
        Launcher.stepSleepTime = stepSleepTime;
        Launcher.displayStatistics = displayStatistics;
        Launcher.enableDebugOutput = enableDebugOutput;
        Launcher.enableMarker = enableMarker;

        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");


        runTaskInBG(new Runnable() {
            @Override
            public void run() {
                try {
                    JAXBReader reader = new JAXBReader();
                    GraphModel graphModel = null;
                    int N = algorithms.size();
                    GraphModel[] graphModels = new GraphModel[N];
                    try {
                        // Плохой код
//                        graphModels[0] = graphParserService.parse("src/main/resources/Graph.txt");
//                        graphModels[1] = graphParserService.parse("src/main/resources/Graph.txt");
//                        graphModels[2] = graphParserService.parse("src/main/resources/Graph.txt");
//                        graphModels[3] = graphParserService.parse("src/main/resources/Graph.txt");
                        for (int i = 0; i < graphModels.length; i++) {
                            graphModels[i] = reader.parse("src/main/resources/o-kotlin-north.osm");
                        }
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
                        gsGraphAdapters[i].setStartNode(startNode);
                        gsGraphAdapters[i].setEndNode(endNode);
                        gsGraphAdapters[i].initGraph();
                    }

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < N; i++) {
                                DisplayGraph displayGraph = new DisplayGraph(gsGraphAdapters[i]);
                                displayGraph.display(i, N);
                            }
                        }
                    });

                    for (int i = 0; i < algorithms.size(); i++) {
                        Class<? extends GraphWalker> algorithm = algorithms.get(i);
                        runAlgorithm(algorithm, gsGraphAdapters[i], startNode, endNode);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void runAlgorithm(Class<? extends GraphWalker> clazz, GsGraphAdapter gsGraphAdapter, String startNode, String endNode) {
        runTaskInBG(new Runnable() {
            @Override
            public void run() {
                try {
                    GraphWalker graphWalker;
                    Constructor<? extends GraphWalker> constructor = (Constructor<? extends GraphWalker>) clazz.getConstructors()[0];
                    if (endNode.isEmpty()) {
                        graphWalker = constructor.newInstance(gsGraphAdapter, Long.valueOf(startNode));
                    } else {
                        graphWalker = constructor.newInstance(gsGraphAdapter, Long.valueOf(startNode), Long.valueOf(endNode));
                    }

                    graphWalker.addListener(gsGraphAdapter);
                    UpdateStatistics updateStatistics0 = new UpdateStatistics(graphWalker);
                    gsGraphAdapter.setStat(updateStatistics0);
                    graphWalker.algorithm();
                    System.out.println(gsGraphAdapter.getStatistics());
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
