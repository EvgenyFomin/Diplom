package ru.itmo.sortvis;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.File;

public class Bridge {
    static Graph newGraph;

    public static Graph convertGraph(GraphModel ourGraph) {

        newGraph = new SingleGraph("Simple Graph");
        int vertexCount = ourGraph.getVertexCount();

        for (int i = 0; i < vertexCount; i++) {
            newGraph.addNode(Integer.toString(i));
        }

        for (int i = 0; i < vertexCount; i++) {
            for (int j = i + 1; j < vertexCount; j++) {
                if (ourGraph.getEdge(i, j) > 0) {
                    newGraph.addEdge(Integer.toString(i) + Integer.toString(j), Integer.toString(i), Integer.toString(j));
                }
            }
        }

        File cssFile = new File("src/main/resources/type.css");
        String cssFileFullPath = cssFile.getAbsolutePath();

        newGraph.addAttribute("ui.stylesheet", "url('file://" + cssFileFullPath + "')");
        newGraph.display();

        return newGraph;
    }

    // Ниже я определяю цвета ребер и вершин.
    // Крашу их в какой-то цвет, когда иду вперед/назад по ребру, вхожу в вершину или выхожу из нее
    // Используются css файлы из папки resources
    // Пока вызываю эти методы из класса GraphWalker

    public static void edgeForward(int i, int j) {
        if (newGraph.getEdge(Integer.toString(i) + Integer.toString(j)).isDirected())
            newGraph.getEdge(Integer.toString(i) + Integer.toString(j)).addAttribute("ui.class", "forward");
        else newGraph.getEdge(Integer.toString(j) + Integer.toString(i)).addAttribute("ui.class", "forward");
    }

    public static void edgeBack(int i, int j) {
        if (newGraph.getEdge(Integer.toString(i) + Integer.toString(j)).isDirected())
            newGraph.getEdge(Integer.toString(i) + Integer.toString(j)).addAttribute("ui.class", "back");
        else newGraph.getEdge(Integer.toString(j) + Integer.toString(i)).addAttribute("ui.class", "back");
    }

    public static void nodeIn(String node) {
        newGraph.getNode(node).addAttribute("ui.class", "in");
    }

    public static void nodeOut(String node) {
        newGraph.getNode(node).addAttribute("ui.class", "out");
    }
}
