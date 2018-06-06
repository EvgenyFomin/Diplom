package ru.itmo.sortvis.ui;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import ru.itmo.sortvis.GsGraphAdapter;
import ru.itmo.sortvis.StatisticsListener;

import javax.swing.*;
import java.awt.*;

public class DisplayGraph {
    private final GsGraphAdapter graph;

    public DisplayGraph(GsGraphAdapter graph) {
        this.graph = graph;
    }

    public void display(int i, int N) {
        int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();

        JFrame jFrame = new JFrame(i + "");
        jFrame.setSize(width / 2, height / 2);
        Viewer viewer = new Viewer(graph.getGsGraph(), Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        View view = viewer.addDefaultView(false);   // false indicates "no JFrame".
        JPanel statPanel = new JPanel();
        JLabel statLabel = new JLabel();
        statPanel.add(statLabel);
        graph.addStatisticsListener(new StatisticsListener() {
            @Override
            public void statsUpdated(AllStats allStats) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        statLabel.setText(allStats.asString());
                    }
                });
            }
        });
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, viewer.getDefaultView(), statPanel);
        splitPane.setDividerLocation(width / 2 - 150);
        jFrame.add(splitPane);

        // 2x2 for now
        int x = (i % 2 == 0) ? 0 : width / 2;
        int y = (i >= 2) ? 0 : height / 2;
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
