package com.studyalert;
import java.io.IOException;

public class MacAlerts {
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
    private static void run(String... command) {
        try {
            new ProcessBuilder(command).start();
        } catch (IOException e) {
            System.err.println("Could not run: " + String.join(" ", command));
            e.printStackTrace();
        }
    }
    private static String escape(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}