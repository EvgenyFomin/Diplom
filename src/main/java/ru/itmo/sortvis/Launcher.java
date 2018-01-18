package ru.itmo.sortvis;

import ru.itmo.sortvis.ui.ConsolePrinter;
import ru.itmo.sortvis.ui.SwingVisualisationPanel;

import javax.swing.*;
import java.util.Random;

public class Launcher {
    public static void main(String[] args) {

//        int[] array = new Random().ints(100, 0, 100).toArray();
//        ArrayModel arrayModel = new ArrayModel(array);
//
//
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                ConsolePrinter consolePrinter = new ConsolePrinter();
//
//                SwingVisualisationPanel swingVisualisationPanel = new SwingVisualisationPanel();
//
//                JFrame frame = new JFrame();
//                frame.setSize(640, 480);
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                frame.setLocationByPlatform(true);
//
//                frame.add(swingVisualisationPanel);
//
//                frame.setVisible(true);
//
//                arrayModel.addArrayListener(swingVisualisationPanel);
////                arrayModel.addArrayListener(consolePrinter);
//            }
//        });
//
//        SortAlgorithm sortAlgo = new SelectionSort();
//
//        sortAlgo.sort(arrayModel);
//
//        arrayModel.println();

        Graph graph = new Graph();
        graph.initGraph();
        graph.print();
    }

}
