package ru.itmo.sortvis;

import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class GraphParserService {
    private HashMap<Long, Set<Long>> adjList;
    private HashMap<Pair<Long, Long>, Integer> weight;
    private Map<Long, Long> map;
    private int countOfNodes;
    private int countOfEdges;
    private boolean isOrientedGraph;

    public GraphModel parse(String path) throws IOException {
        File file = new File(path);
        adjList = new HashMap<>();
        weight = new HashMap<>();
        map = new HashMap<>();

        try (BufferedReader bufferedGraphReader = Files.newBufferedReader(file.toPath())) {
            String tmp = bufferedGraphReader.readLine().trim();
            isOrientedGraph = Boolean.parseBoolean(tmp);
            tmp = bufferedGraphReader.readLine();
            countOfNodes = Integer.valueOf(tmp.substring(0, tmp.indexOf(" ")));
            countOfEdges = Integer.valueOf(tmp.substring(tmp.lastIndexOf(" ") + 1));

            tmp = bufferedGraphReader.readLine();
            StringTokenizer nodes = new StringTokenizer(tmp);
            for (int i = 0; i < countOfNodes; i++) {
                String nextToken = nodes.nextToken();
                map.put(Long.valueOf(nextToken), Long.valueOf(nextToken));
            }

            if (isOrientedGraph) {
                for (int i = 0; i < countOfEdges; i++) {
                    StringTokenizer currentEdge = new StringTokenizer(bufferedGraphReader.readLine());
                    String node1 = currentEdge.nextToken();
                    String node2 = currentEdge.nextToken();

                    long node1_l = Long.valueOf(node1);
                    long node2_l = Long.valueOf(node2);
                    try {
                        if (adjList.containsKey(node1_l))
                            adjList.get(node1_l).add(node2_l);
                        else {
                            adjList.put(node1_l, new HashSet<>());
                            adjList.get(node1_l).add(node2_l);
                        }

                        String currentWeight = currentEdge.nextToken();

                        weight.put(Pair.of(node1_l, node2_l), Integer.valueOf(currentWeight));
                    } catch (NoSuchElementException err) {
                        weight.put(Pair.of(node1_l, node2_l), 1);
                    }
                }
            } else {
                for (int i = 0; i < countOfEdges; i++) {
                    StringTokenizer currentEdge = new StringTokenizer(bufferedGraphReader.readLine());
                    String node1 = currentEdge.nextToken();
                    String node2 = currentEdge.nextToken();

                    long node1_l = Long.valueOf(node1);
                    long node2_l = Long.valueOf(node2);
                    try {
                        if (adjList.containsKey(node1_l))
                            adjList.get(node1_l).add(node2_l);
                        else {
                            adjList.put(node1_l, new HashSet<>());
                            adjList.get(node1_l).add(node2_l);
                        }

                        if (adjList.containsKey(node2_l))
                            adjList.get(node2_l).add(node1_l);
                        else {
                            adjList.put(node2_l, new HashSet<>());
                            adjList.get(node2_l).add(node1_l);
                        }

                        String currentWeight = currentEdge.nextToken();

                        weight.put(Pair.of(node1_l, node2_l), Integer.valueOf(currentWeight));
                        weight.put(Pair.of(node2_l, node1_l), Integer.valueOf(currentWeight));
                    } catch (NoSuchElementException err) {
                        weight.put(Pair.of(node1_l, node2_l), 1);
                        weight.put(Pair.of(node2_l, node1_l), 1);
                    }
                }
            }



            return new AdjListGraph<Long>(isOrientedGraph, map, adjList, weight);
        }
    }
}
