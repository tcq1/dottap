package com.example.trung.dt;

import android.content.SharedPreferences;
import android.view.View;

public interface GameMode {
    void dotClicked(View dot);
    void initGame();
    void endGame();
    SharedPreferences getSharedPreferences(String pref, int mode);
}
