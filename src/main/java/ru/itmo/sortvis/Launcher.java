package ru.itmo.sortvis;

import ru.itmo.sortvis.XMLMapParser.JAXBReader;
import ru.itmo.sortvis.ui.DisplayGraph;

import javax.swing.*;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher {

    public static final String ALGO_PACKAGE = "ru.itmo.sortvis";

    private static final GraphParserService graphParserService = new GraphParserService();

    private static final ExecutorService pool = Executors.newFixedThreadPool(10);

    public static int stepSleepTime;
    static boolean displayStatistics;
    public static boolean enableDebugOutput;
    static boolean enableMarker;
    static boolean enableNodesLabel;
    public static boolean isTxtGraph = false;

    public static void launch(String graphPath, List<Class<? extends GraphWalker>> algorithms, int stepSleepTime, boolean displayStatistics,
                              boolean enableDebugOutput, boolean enableMarker, boolean enableNodesLabel, String startNode, String endNode) {
        Launcher.stepSleepTime = stepSleepTime;
        Launcher.displayStatistics = displayStatistics;
        Launcher.enableDebugOutput = enableDebugOutput;
        Launcher.enableMarker = enableMarker;
        Launcher.enableNodesLabel = enableNodesLabel;

        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        runTaskInBG(new Runnable() {
            @Override
            public void run() {
                try {
                    JAXBReader reader = new JAXBReader();
                    int N = algorithms.size();
                    GraphModel[] graphModels = new GraphModel[N];
                    System.out.println(getFileExtension(graphPath));
                    try {
                        if (getFileExtension(graphPath).equals(".txt")) {
                            for (int i = 0; i < graphModels.length; i++) {
                                graphModels[i] = graphParserService.parse(graphPath);
                                isTxtGraph = true;
                            }
                        } else if (getFileExtension(graphPath).equals(".osm") || getFileExtension(graphPath).equals(".xml")) {
                            for (int i = 0; i < graphModels.length; i++) {
                                graphModels[i] = reader.parse(graphPath);
                            }
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
                                String algName = getFileExtension(algorithms.get(i).toString());
                                algName = algName.substring(1);
                                displayGraph.display(i, N, algName);
                            }
                        }
                    });

                    for (int i = 0; i < algorithms.size(); i++) {
                        Class<? extends GraphWalker> algorithm = algorithms.get(i);
                        System.out.println(getFileExtension(algorithm.toString()));
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

    private static String getFileExtension(String mystr) {
        int index = mystr.lastIndexOf('.');
        return index == -1 ? null : mystr.substring(index);
    }
}
