package com.example.trung.dt;

import android.util.DisplayMetrics;
import android.view.Display;

public class Calculation {
    private Display display;
    private float[][] blockedCoords;
    private int level;

    public Calculation(Display display) {
        this.display = display;
    }

    public int calcDots(int level) {
        if (level > 20) {
            return 40;
        }

        switch(level) {
            case 1: return 5;
            case 2: return 8;
            case 3: return 10;
            case 4: return 12;
            case 5: return 14;
            case 6: return 16;
            case 7: return 17;
            case 8: return 18;
            case 9: return 19;
            default: return 2 * level;
        }
    }

    protected void setBlockedCoords(float[][] blockedCoords) {
        this.blockedCoords = blockedCoords;
    }
    protected float[][] getBlockedCoords() {
        return blockedCoords;
    }

    protected void setLevel(int level) {
        this.level = level;
    }

    protected void blockCoords(int id, float[] coords) {
        blockedCoords[id][0] = coords[0];
        blockedCoords[id][1] = coords[1];
    }

    protected boolean coordsBlocked(float coords[]) {
        boolean output = false;
        for (int i = 0; i < calcDots(level); i++) {
            if ((((blockedCoords[i][0] - 120) <= coords[0]) && ((blockedCoords[i][0] + 120) >= coords[0])) && (((blockedCoords[i][1] - 120) <= coords[1]) && ((blockedCoords[i][1] + 120) >= coords[1]))) {
                output = true;
            }
        }
        return output;
    }

    protected float[] calcCoords() {
        DisplayMetrics metr = new DisplayMetrics();
        display.getMetrics(metr);
        float width = metr.widthPixels;
        float height = metr.heightPixels;

        float x, y;
        x = (float) (Math.random() * (width-300) + 100);
        y = (float) (Math.random() * (height-600) + 120);

        float[] coords = {x, y};

        if (coordsBlocked(coords)) {
            return calcCoords();
        }

        return coords;
    }

    protected int calcSpeed(int x) {
        if (x > 100) {
            return 200;
        }
        return (-8 * x + 1000);
    }

    protected double transformSpeed(int x) {
        double output = 1;

        if (x < 101) {
            for (int i = 0; i < x; i++) {
                output += 0.09;
            }
        } else {
            output = 10.0;
        }
        return output;
    }
}
