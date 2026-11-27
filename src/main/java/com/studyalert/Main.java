package com.studyalert;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.FlowLayout;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Study Alert");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout());
            frame.add(new JLabel("Study Alert is running"));
            frame.add(new JButton("Start studying"));
            JButton bannerButton = new JButton("Test banner");
            bannerButton.addActionListener(e -> MacAlerts.notify("Study Alert", "Time to study!"));
            frame.add(bannerButton);
            JButton soundButton = new JButton("Test sound");
            soundButton.addActionListener(e -> MacAlerts.playSound());
            frame.add(soundButton);
            JButton dialogButton = new JButton("Test dialog");
            dialogButton.addActionListener(e -> MacAlerts.dialog("Go study now!"));
            frame.add(dialogButton);
            JButton voiceButton = new JButton("Test voice");
            voiceButton.addActionListener(e -> MacAlerts.speak("Time to study"));
            frame.add(voiceButton);
            frame.setSize(320, 200);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}