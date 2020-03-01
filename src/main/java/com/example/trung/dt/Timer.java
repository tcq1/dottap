package com.example.trung.dt;

public class Timer extends Thread {
    private Arcade arcade;
    int time;
    private boolean stop;
    int currentTime;

    public Timer(Arcade arcade, int time) {
        this.arcade = arcade;
        this.time = time;
        this.stop = false;
    }

    @Override
    public void run() {
        loop:
        while(!stop) {
            for (currentTime = 0; currentTime < 1000; currentTime++) {
                try {
                    Thread.sleep(time);
                    arcade.incrTime(1);
                } catch (InterruptedException e) {
                }
                if (stop) {
                    break loop;
                }
            }
            stopThread();
            arcade.endGame();
        }
    }

    public void increaseCurrentTime(int amount) {
        currentTime += amount;
    }

    public void stopThread() {
        stop = true;
        arcade.incrTime(-1000);
    }
}
