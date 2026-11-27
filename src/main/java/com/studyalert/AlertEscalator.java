package com.studyalert;
public class AlertEscalator {
    public static final int MAX_LEVEL = 4;
    private int level = 0;
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
            default -> {
                MacAlerts.playSound();
                MacAlerts.speak("Time to study. Stop scrolling and start studying.");
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