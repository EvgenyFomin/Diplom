package ru.itmo.sortvis.ui;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

public class DisplayGraph {
    public Graph graph;
    public DisplayGraph(Graph graph) {
        this.graph = graph;
    }
    public void display() {
        Viewer viewer = graph.display(false);
//        View view = viewer.getDefaultView();
//        viewer.disableAutoLayout();
//        view.getCamera().setViewCenter( 23 * 1000000, 60.0556000 * 1000000, 0);
////        view.getCamera().setViewPercent(1.5);
////        view.getCamera().resetView();
//        System.out.println(view.getCamera());


    }
}
