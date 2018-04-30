package ru.itmo.sortvis;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class GraphParserService {
    public GraphModel parse(File file) throws IOException {
        int countOfNodes;
        int countOfEdges;
        boolean isOrientedGraph;
        int[][] matrix;

        Map<String, Integer> data = new HashMap<>();
        try (BufferedReader bufferedGraphReader = Files.newBufferedReader(file.toPath())) {
            String tmp = bufferedGraphReader.readLine().trim();
            isOrientedGraph = Boolean.parseBoolean(tmp);
            tmp = bufferedGraphReader.readLine();
            countOfNodes = Integer.valueOf(tmp.substring(0, tmp.indexOf(" ")));
            countOfEdges = Integer.valueOf(tmp.substring(tmp.lastIndexOf(" ") + 1));

            tmp = bufferedGraphReader.readLine();
            StringTokenizer nodes = new StringTokenizer(tmp);
            for (int i = 0; i < countOfNodes; i++) {
                data.put(nodes.nextToken(), i);
            }

            matrix = new int[countOfNodes][countOfNodes];

            for (int i = 0; i < countOfNodes; i++) {
                for (int j = 0; j < countOfNodes; j++) {
                    matrix[i][i] = 0;
                }
            }

            if (isOrientedGraph) {
                for (int i = 0; i < countOfEdges; i++) {
                    StringTokenizer currentEdge = new StringTokenizer(bufferedGraphReader.readLine());
                    String node1 = currentEdge.nextToken();
                    String node2 = currentEdge.nextToken();
                    try {
                        matrix[data.get(node1)][data.get(node2)] =
                                Integer.valueOf(currentEdge.nextToken());
                    } catch (NoSuchElementException err) {
                        matrix[data.get(node1)][data.get(node2)] = 1;
                    }
                }
            } else {
                for (int i = 0; i < countOfEdges; i++) {
                    StringTokenizer currentEdge = new StringTokenizer(bufferedGraphReader.readLine());
                    String node1 = currentEdge.nextToken();
                    String node2 = currentEdge.nextToken();
                    try {
                        matrix[data.get(node1)][data.get(node2)] =
                                Integer.valueOf(currentEdge.nextToken());
                        matrix[data.get(node2)][data.get(node1)] =
                                matrix[data.get(node1)][data.get(node2)];
                    } catch (NoSuchElementException err) {
                        matrix[data.get(node1)][data.get(node2)] = 1;
                        matrix[data.get(node2)][data.get(node1)] = 1;
                    }
                }
            }

            // Не могу с ходу понять, "забываются" ли тут строковые имена нодов?
            // Короче, здесь, кажется, надо сделать маппинг String -> int и передать его в StringGraphModel...
//            new StringGraphModel<>(new MatrixGraph(....), stringToIntMap);
            // как-то так

            return new MatrixGraph(countOfNodes, countOfEdges, isOrientedGraph, matrix);
        }
    }
}
