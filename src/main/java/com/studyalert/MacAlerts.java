package com.studyalert;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MacAlerts {
    private static final List<Process> activeAlerts = new ArrayList<>();

    public static void notify(String title, String message) {
        run("osascript", "-e",
                "display notification \"" + escape(message) + "\" with title \"" + escape(title) + "\"");
    }
    public static void dialog(String message) {
        run("osascript", "-e",
                "display dialog \"" + escape(message) + "\" buttons {\"OK\"} default button \"OK\" with title \"Study Alert\"");
    }
    public static void speak(String message) {
        run("say", message);
    }
    public static void playSound() {
        run("afplay", "/System/Library/Sounds/Sosumi.aiff");
    }
    public static void stopAll() {
        for (Process alert : activeAlerts) {
            alert.destroy();
        }
        activeAlerts.clear();
    }
    private static void run(String... command) {
        try {
            Process alert = new ProcessBuilder(command).start();
            activeAlerts.add(alert);
        } catch (IOException e) {
            System.err.println("Could not run: " + String.join(" ", command));
            e.printStackTrace();
        }
    }
    private static String escape(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}