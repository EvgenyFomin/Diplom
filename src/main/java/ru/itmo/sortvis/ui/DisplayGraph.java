package ru.itmo.sortvis.ui;

import org.graphstream.graph.Graph;

public class DisplayGraph {
    public Graph graph;
    public DisplayGraph(Graph graph) {
        this.graph = graph;
    }
    public void display() {
        graph.display(false);
    }
}
