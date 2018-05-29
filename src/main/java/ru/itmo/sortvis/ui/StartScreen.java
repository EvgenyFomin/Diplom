package ru.itmo.sortvis.ui;

import ru.itmo.sortvis.Launcher;

import javax.swing.*;
import javax.xml.bind.JAXBException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen {
    private JPanel rootPanel;
    private JTextField stepSleepTimeField;
    private JButton launchButton;
    private JCheckBox displayStatisticsCheckbox;
    private JLabel stepSleepTimeLabel;
    private JLabel displayStatisticsLabel;
    private JCheckBox printDebugInfo;

    public StartScreen() {
        launchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int stepSleepTime = Integer.valueOf(stepSleepTimeField.getText());
                boolean displayStatistics = displayStatisticsCheckbox.isSelected();
                Launcher.enableDebugOutput = printDebugInfo.isSelected();

                Launcher.launch(stepSleepTime, displayStatistics);
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
}
