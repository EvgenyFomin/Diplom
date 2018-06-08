package ru.itmo.sortvis.ui;

import org.reflections.Reflections;
import ru.itmo.sortvis.GraphWalker;
import ru.itmo.sortvis.Launcher;
import ru.itmo.sortvis.Notifier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static ru.itmo.sortvis.Launcher.ALGO_PACKAGE;

public class StartScreen extends Component {
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
    private JCheckBox Marker;
    private JButton openButton;
    private JTextField graphPath;
    private JFileChooser fileChooser;
    private String path;

    public StartScreen() {
        fileChooser = new JFileChooser();
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Select a file");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int result = fileChooser.showOpenDialog(StartScreen.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    path = fileChooser.getSelectedFile().getAbsolutePath();
                    graphPath.setText(path);
                }
            }
        });

        launchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int stepSleepTime = Integer.valueOf(stepSleepTimeField.getText());
                boolean displayStatistics = displayStatisticsCheckbox.isSelected();
                boolean enableDebugOutput = printDebugInfo.isSelected();
                boolean enableMarker = Marker.isSelected();
                if (path == null) {
                    JOptionPane.showConfirmDialog(StartScreen.this, "Select a graph file",
                            "Error", JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    List selectedValuesList = algorithmList.getSelectedValuesList();
                    Launcher.launch(path, selectedValuesList, stepSleepTime, displayStatistics, enableDebugOutput, enableMarker,
                            startNodeField.getText(), endNodeField.getText());
                }
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
