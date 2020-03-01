package com.example.trung.dt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HighScore extends AppCompatActivity {
    private SoundManager soundManager;
    private TextView arcadeHS;
    private TextView classicHS;
    private ImageView reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        this.arcadeHS = findViewById(R.id.arcadeHS);
        this.classicHS = findViewById(R.id.classicHS);

        this.reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetScore();
            }
        });

        this.soundManager = new SoundManager(this);

        initHighScore();
    }

    public void initHighScore() {
        SharedPreferences highScore = getSharedPreferences("highScore", Context.MODE_PRIVATE);
        arcadeHS.setText("Arcade Highscore: Level " + highScore.getInt("arcScore", 0));
        classicHS.setText("Classic Highscore: " + highScore.getInt("clsScore", 0) + " Dots clicked");
    }

    public void resetScore() {
        SharedPreferences highScore = getSharedPreferences("highScore", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = highScore.edit();

        edit.putInt("arcScore", 0);
        edit.putInt("clsScore", 0);
        edit.commit();

        initHighScore();
    }

    @Override
    public void onBackPressed() {
        soundManager.terminate();
        startActivity(new Intent(this, MainMenu.class));
    }
}
