package ru.itmo.sortvis.ui;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;

public class DisplayGraph {
    public Graph graph;

    public DisplayGraph(Graph graph) {
        this.graph = graph;
    }

    public void display(int i, int N) {
        int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();

        JFrame jFrame = new JFrame(i + "");
        jFrame.setSize(width / 2, height / 2);
        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        View view = viewer.addDefaultView(false);   // false indicates "no JFrame".
        jFrame.add(viewer.getDefaultView());

        // 2x2 for now
        int x = (i % 2 == 0) ? 0 : width / 2;
        int y = (i >= 2) ? 0 : height/ 2;
        jFrame.setLocation(x, y);
        jFrame.setVisible(true);

//        Viewer viewer = graph.display(false);
//        viewer.addDefaultView(false);
//        viewer.getDefaultView().resizeFrame();
//        View view = viewer.getDefaultView();
//        viewer.disableAutoLayout();
//        view.getCamera().setViewCenter( 23 * 1000000, 60.0556000 * 1000000, 0);
////        view.getCamera().setViewPercent(1.5);
////        view.getCamera().resetView();
//        System.out.println(view.getCamera());


    }
}
