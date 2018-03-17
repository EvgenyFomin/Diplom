package ru.itmo.sortvis;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.*;
import java.util.*;

public class MatrixGraph implements GraphModel<String> {
    private static final int N = 6;
    private int countOfVertex;
    private int countOfEdges;
    private boolean isOrientedGraph;
    private int[][] matrix;
    private Map<String, Object> data;
    private final List<GraphModelListener> listenerList;

    public MatrixGraph() {
        this.listenerList = new ArrayList<>();
        this.data = new HashMap<>();
//        this.data = new String[N];
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

            tmp = bufferedGraphReader.readLine();
            StringTokenizer nodes = new StringTokenizer(tmp);
            for (int i = 0; i < countOfVertex; i++) {
                data.put(nodes.nextToken(), i);
            }

            matrix = new int[countOfVertex][countOfVertex];

            for (int i = 0; i < countOfVertex; i++) {
                for (int j = 0; j < countOfVertex; j++) {
                    matrix[i][i] = 0;
                }
            }

            if (isOrientedGraph) {
                for (int i = 0; i < countOfEdges; i++) {
                    StringTokenizer currentEdge = new StringTokenizer(bufferedGraphReader.readLine());
                    String node1 = currentEdge.nextToken();
                    String node2 = currentEdge.nextToken();
                    try {
                        matrix[(Integer) data.get(node1)][(Integer) data.get(node2)] =
                                Integer.valueOf(currentEdge.nextToken());
                    } catch (NoSuchElementException err) {
                        matrix[(Integer) data.get(node1)][(Integer) data.get(node2)] = 1;
                    }
                }
            } else {
                for (int i = 0; i < countOfEdges; i++) {
                    StringTokenizer currentEdge = new StringTokenizer(bufferedGraphReader.readLine());
                    String node1 = currentEdge.nextToken();
                    String node2 = currentEdge.nextToken();
                    try {
                        matrix[(Integer) data.get(node1)][(Integer) data.get(node2)] =
                                Integer.valueOf(currentEdge.nextToken());
                        matrix[(Integer) data.get(node2)][(Integer) data.get(node1)] =
                                matrix[(Integer) data.get(node1)][(Integer) data.get(node2)];
                    } catch (NoSuchElementException err) {
                        matrix[(Integer) data.get(node1)][(Integer) data.get(node2)] = 1;
                        matrix[(Integer) data.get(node2)][(Integer) data.get(node1)] = 1;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        graphInitialized();
        print();
        GraphAdapter graphAdapter = new GraphAdapter(this);
        graphAdapter.initGraph();
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
    public int getEdge(int i, int j) {
        return matrix[i][j];
    }

    @Override
    public LinkedList<Integer> getNeighbours(int i) {
        LinkedList<Integer> neighbourList = new LinkedList<>();
        for (int j = 0; j < N; j++) {
            if (matrix[i][j] >= 1) {
                neighbourList.add(j);
            }
        }
        return neighbourList;
    }

    private void graphInitialized() {
        for (GraphModelListener obj : listenerList) {
            obj.modelChanged();
        }
    }

    void print() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.printf("%4d", matrix[i][j]);
            }
            System.out.println();
        }
    }

//    private void randomInit() {
//        matrix = new int[N][N];
//        for (int i = 0; i < N; i++) {
//            matrix[i][i] = -1;
//            data[i] = RandomStringUtils.randomAlphabetic(3).toUpperCase();
//            for (int j = i + 1; j < N; j++) {
//                matrix[i][j] = new Random().nextInt(30);
//                matrix[j][i] = matrix[i][j];
//            }
//        }
//
//        graphInitialized();
//        print();
//    }
}
