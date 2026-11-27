package com.studyalert;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.FlowLayout;
import java.awt.Font;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    private static final int CHECK_INTERVAL_MS = 60_000;
    private static final int ALERT_INTERVAL_MINUTES = 3;
    private static final int SNOOZE_MINUTES = 5;
    private static final int SNOOZE_COOLDOWN_MINUTES = 60;
    private static StudyTimer timer;
    private static StudySchedule schedule;
    private static final AlertEscalator escalator = new AlertEscalator(() -> FullScreenNag.show(Main::startStudying, Main::skipBlock));
    private static LocalDateTime lastAlertTime = null;
    private static LocalDateTime snoozeUntil = null;
    private static LocalDateTime lastSnoozeTime = null;
    private static boolean skippedThisBlock = false;
    private static JLabel statusLabel;

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
                    () -> {
                        MacAlerts.notify("Study Alert", "Session complete! Take a break.");
                        lastAlertTime = LocalDateTime.now(); // short break before nagging again
                    }
            );
            JButton startButton = new JButton("Start studying");
            startButton.addActionListener(e -> startStudying());
            frame.add(startButton);
            JButton pauseButton = new JButton("Pause");
            pauseButton.addActionListener(e -> timer.pause());
            frame.add(pauseButton);
            JButton resetButton = new JButton("Reset");
            resetButton.addActionListener(e -> timer.reset());
            frame.add(resetButton);
            JButton snoozeButton = new JButton("Snooze " + SNOOZE_MINUTES + " min");
            snoozeButton.addActionListener(e -> snooze(frame));
            frame.add(snoozeButton);
            statusLabel = new JLabel();
            frame.add(statusLabel);
            updateStatus();
            checkSchedule();
            new Timer(CHECK_INTERVAL_MS, e -> checkSchedule()).start();
            frame.setSize(380, 220);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
    private static void startStudying() {
        MacAlerts.stopAll();
        FullScreenNag.hide();
        escalator.reset();
        lastAlertTime = null;
        snoozeUntil = null;
        timer.start();
        updateStatus();
    }
    private static void skipBlock() {
        MacAlerts.stopAll();
        FullScreenNag.hide();
        escalator.reset();
        skippedThisBlock = true;
        updateStatus();
    }
    private static void checkSchedule() {
        LocalDateTime now = LocalDateTime.now();

        if (!schedule.isStudyTime(now)) {
            escalator.reset();
            lastAlertTime = null;
            skippedThisBlock = false;
            updateStatus();
            return;
        }
        if (skippedThisBlock || timer.isRunning()) {
            return;
        }
        if (snoozeUntil != null && now.isBefore(snoozeUntil)) {
            return;
        }
        boolean dueForAlert = lastAlertTime == null
                || Duration.between(lastAlertTime, now).toMinutes() >= ALERT_INTERVAL_MINUTES;
        if (dueForAlert) {
            escalator.escalate();
            lastAlertTime = now;
            updateStatus();
        }
    }
    private static void snooze(JFrame frame) {
        LocalDateTime now = LocalDateTime.now();
        if (lastSnoozeTime != null
                && Duration.between(lastSnoozeTime, now).toMinutes() < SNOOZE_COOLDOWN_MINUTES) {
            LocalDateTime nextSnooze = lastSnoozeTime.plusMinutes(SNOOZE_COOLDOWN_MINUTES);
            String time = nextSnooze.format(DateTimeFormatter.ofPattern("h:mm a"));
            JOptionPane.showMessageDialog(frame, "Snooze already used. Next one available at " + time + ".");
            return;
        }
        MacAlerts.stopAll();
        snoozeUntil = now.plusMinutes(SNOOZE_MINUTES);
        lastSnoozeTime = now;
        updateStatus();
    }
    private static void updateStatus() {
        String text = "Alert level: " + escalator.getLevel() + " / " + AlertEscalator.MAX_LEVEL;
        if (skippedThisBlock) {
            text += " (skipped this block)";
        }
        statusLabel.setText(text);
    }
}