package com.studyalert;
import javax.swing.Timer;

public class StudyTimer {
    private static final int SESSION_SECONDS = 25 * 60;
    private int remainingSeconds = SESSION_SECONDS;
    private boolean running = false;
    private final Timer timer;
    private final Runnable onTick;
    private final Runnable onFinish;

    public StudyTimer(Runnable onTick, Runnable onFinish) {
        this.onTick = onTick;
        this.onFinish = onFinish;
        this.timer = new Timer(1000, e -> tick());
    }
    private void tick() {
        remainingSeconds--;
        onTick.run();
        if (remainingSeconds <= 0) {
            reset();
            onFinish.run();
        }
    }
    public void start() {
        if (!running) {
            timer.start();
            running = true;
        }
    }
    public void pause() {
        timer.stop();
        running = false;
    }
    public void reset() {
        timer.stop();
        running = false;
        remainingSeconds = SESSION_SECONDS;
        onTick.run();
    }
    public boolean isRunning() {
        return running;
    }
    public String getTimeText() {
        return String.format("%02d:%02d", remainingSeconds / 60, remainingSeconds % 60);
    }
}