package ru.itmo.sortvis;

public class Launcher {
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        GraphModel graphModel = new MatrixGraph();
        graphModel.initGraph();

//        GraphModel graphModel = new AdjListGraph();
        GsGraphAdapter gsGraphAdapter = new GsGraphAdapter(graphModel);
        Thread threadToConvert = new Thread(gsGraphAdapter);
        threadToConvert.start();
        GraphWalker graphWalker = new GraphWalker(gsGraphAdapter);

        graphWalker.addListener(gsGraphAdapter);
        try {
            threadToConvert.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // обход в глубину
        dfs(graphWalker, 0);
        // обход в ширину
//        bfs(graphWalker, 0, 3);
        // Дейкстра
//        dijkstra(graphWalker, 0);
    }

    private static void dfs(GraphWalker graphWalker, int startVertex) {
        System.out.println("START!");
        for (int i = 5; i > -1; i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
        }
        graphWalker.dfs(startVertex);
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
