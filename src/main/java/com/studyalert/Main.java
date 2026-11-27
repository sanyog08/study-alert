package com.studyalert;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.FlowLayout;
import java.awt.Font;

public class Main {
    private static StudyTimer timer;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Study Alert");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout());
            JLabel timeLabel = new JLabel("25:00");
            timeLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
            frame.add(timeLabel);
            timer = new StudyTimer(
                    () -> timeLabel.setText(timer.getTimeText()),
                    () -> MacAlerts.notify("Study Alert", "Session complete! Take a break.")
            );
            JButton startButton = new JButton("Start studying");
            startButton.addActionListener(e -> {
                MacAlerts.stopAll();
                timer.start();
            });
            frame.add(startButton);
            JButton pauseButton = new JButton("Pause");
            pauseButton.addActionListener(e -> timer.pause());
            frame.add(pauseButton);
            JButton resetButton = new JButton("Reset");
            resetButton.addActionListener(e -> timer.reset());
            frame.add(resetButton);
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
            frame.setSize(360, 240);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}