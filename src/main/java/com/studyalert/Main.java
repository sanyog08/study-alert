package com.studyalert;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class Main {
    private static final int CHECK_INTERVAL_MS = 60_000; // one minute
    private static StudyTimer timer;
    private static StudySchedule schedule;
    private static boolean alertedThisBlock = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                schedule = StudySchedule.load(Path.of("settings.properties"));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Could not read settings.properties:\n" + e.getMessage());
                System.exit(1);
            }
            JFrame frame = new JFrame("Study Alert");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout());
            frame.add(new JLabel(schedule.describe()));
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
            checkSchedule();
            new Timer(CHECK_INTERVAL_MS, e -> checkSchedule()).start();
            frame.setSize(360, 200);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
    private static void checkSchedule() {
        if (!schedule.isStudyTime(LocalDateTime.now())) {
            alertedThisBlock = false;
            return;
        }
        if (timer.isRunning()) {
            return;
        }
        if (!alertedThisBlock) {
            MacAlerts.notify("Study Alert", "Study time! Click Start studying.");
            alertedThisBlock = true;
        }
    }
}