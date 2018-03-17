package ru.itmo.sortvis;

import javafx.util.Pair;

import java.io.*;
import java.util.*;

public class MatrixGraph implements GraphModel<String> {
    private static final int N = 6;
    private int countOfVertex;
    private int countOfEdges;
    private boolean isOrientedGraph;
    private Map<Pair<String, String>, Integer> matrix;
    private String[] nodes;
    private final List<GraphModelListener> listenerList;

    public MatrixGraph() {
        this.listenerList = new ArrayList<>();
        this.matrix = new HashMap<>();
    }

    @Override
    public void initGraph() {
        try {
            FileReader graphReader = new FileReader(new File("src/main/resources/Graph.txt"));
            BufferedReader bufferedGraphReader = new BufferedReader(graphReader);
            String tmp = bufferedGraphReader.readLine().trim();
            isOrientedGraph = Boolean.parseBoolean(tmp);
            tmp = bufferedGraphReader.readLine();
            countOfVertex = Integer.valueOf(tmp.substring(0, tmp.indexOf(" ")));
            countOfEdges = Integer.valueOf(tmp.substring(tmp.lastIndexOf(" ") + 1));

            nodes = new String[countOfVertex];

            tmp = bufferedGraphReader.readLine();
            StringTokenizer nodes = new StringTokenizer(tmp);
            for (int i = 0; i < countOfVertex; i++) {
                String s = nodes.nextToken();
                this.nodes[i] = s;
            }

            if (isOrientedGraph) {
                for (int i = 0; i < countOfEdges; i++) {
                    StringTokenizer currentEdge = new StringTokenizer(bufferedGraphReader.readLine());
                    String node1 = currentEdge.nextToken();
                    String node2 = currentEdge.nextToken();
                    try {
                        int weight = Integer.valueOf(currentEdge.nextToken());
                        matrix.put(new Pair<>(node1, node2), weight);
                    } catch (NoSuchElementException err) {
                        matrix.put(new Pair<>(node1, node2), 1);
                    }
                }
            } else {
                for (int i = 0; i < countOfEdges; i++) {
                    StringTokenizer currentEdge = new StringTokenizer(bufferedGraphReader.readLine());
                    String node1 = currentEdge.nextToken();
                    String node2 = currentEdge.nextToken();
                    try {
                        int weight = Integer.valueOf(currentEdge.nextToken());
                        matrix.put(new Pair<>(node1, node2), weight);
                        matrix.put(new Pair<>(node2, node1), weight);
                    } catch (NoSuchElementException err) {
                        matrix.put(new Pair<>(node1, node2), 1);
                        matrix.put(new Pair<>(node2, node1), 1);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

//        graphInitialized();
        print();
    }

    public void addModelListener(GraphModelListener gr) {
        listenerList.add(gr);
    }

    @Override
    public int getVertexCount() {
        return N;
    }

    @Override
    public String getData(int i) {
        return "abd";
    }

    @Override
    public Integer getEdge(String i, String j) {
        return matrix.get(new Pair<>(i, j));
    }

    @Override
    public LinkedList<String> getNeighbours(String i) {
        LinkedList<String> neighbourList = new LinkedList<>();
        for (int j = 0; j < N; j++) {
            if (getEdge(i, nodes[j]) != null) {
                neighbourList.add(nodes[j]);
            }
        }
        return neighbourList;
    }

    @Override
    public String[] getNodes() {
        return nodes;
    }

    @Override
    public boolean getGraphStatus() {
        return isOrientedGraph;
    }

    private void graphInitialized() {
        for (GraphModelListener obj : listenerList) {
            obj.modelChanged();
        }
    }

    void print() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (getEdge(nodes[i], nodes[j]) != null) {
                    System.out.println(nodes[i] + " " + nodes[j] + " " + getEdge(nodes[i], nodes[j]));
                }
            }
        }
    }
}
