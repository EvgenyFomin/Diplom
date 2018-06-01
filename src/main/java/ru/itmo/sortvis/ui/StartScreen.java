package ru.itmo.sortvis.ui;

import org.reflections.Reflections;
import ru.itmo.sortvis.GraphWalker;
import ru.itmo.sortvis.Launcher;
import ru.itmo.sortvis.Notifier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static ru.itmo.sortvis.Launcher.ALGO_PACKAGE;

public class StartScreen {
    private JPanel rootPanel;
    private JTextField stepSleepTimeField;
    private JButton launchButton;
    private JCheckBox displayStatisticsCheckbox;
    private JLabel stepSleepTimeLabel;
    private JLabel displayStatisticsLabel;
    private JCheckBox printDebugInfo;
    private JList algorithmList;
    private JTextField startNodeField;
    private JTextField endNodeField;


    public StartScreen() {
        launchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int stepSleepTime = Integer.valueOf(stepSleepTimeField.getText());
                boolean displayStatistics = displayStatisticsCheckbox.isSelected();
                Launcher.enableDebugOutput = printDebugInfo.isSelected();

                List selectedValuesList = algorithmList.getSelectedValuesList();
                Launcher.launch(selectedValuesList, stepSleepTime, displayStatistics, startNodeField.getText(), endNodeField.getText());
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Diploma");
        frame.setContentPane(new StartScreen().rootPanel);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        Reflections reflections = new Reflections(ALGO_PACKAGE);
        ArrayList list = new ArrayList(reflections.getSubTypesOf(Notifier.class));
        list.remove(GraphWalker.class);

        this.algorithmList = new JList(list.toArray());
        this.algorithmList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }
}
