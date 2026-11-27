package com.studyalert;

public class AlertEscalator {
    public static final int MAX_LEVEL = 5;
    private int level = 0;
    private final Runnable showFullScreen;

    public AlertEscalator(Runnable showFullScreen) {
        this.showFullScreen = showFullScreen;
    }
    public void escalate() {
        if (level < MAX_LEVEL) {
            level++;
        }
        switch (level) {
            case 1 -> MacAlerts.notify("Study Alert", "Study time! Click Start studying.");
            case 2 -> {
                MacAlerts.notify("Study Alert", "Still not studying...");
                MacAlerts.playSound();
            }
            case 3 -> {
                MacAlerts.playSound();
                MacAlerts.dialog("You're still not studying. Go study now!");
            }
            case 4 -> {
                MacAlerts.playSound();
                MacAlerts.speak("Time to study. Stop scrolling and start studying.");
            }
            default -> {
                MacAlerts.speak("Go study now.");
                showFullScreen.run();
            }
        }
    }
    public void reset() {
        level = 0;
    }
    public int getLevel() {
        return level;
    }
}