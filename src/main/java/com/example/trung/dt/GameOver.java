package com.example.trung.dt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameOver extends AppCompatActivity {
    private ImageView retry;
    private ImageView back;
    private TextView textOutput;
    private Intent intent;
    private SoundManager soundManager;
    private String gameMode;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        retry = findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retryPressed();
            }
        });

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backPressed();
            }
        });

        textOutput = findViewById(R.id.textOutput);

        intent = getIntent();
        textOutput.setText(intent.getStringExtra("extra_output"));
        gameMode = intent.getStringExtra("game_mode");
        score = intent.getIntExtra("score", 0);

        soundManager = new SoundManager(this);

        checkNewHighscore();
    }

    public void retryPressed() {
        soundManager.makeTapSound();
        soundManager.terminate();

        switch (gameMode) {
            case "arc": startActivity(new Intent(this, Arcade.class)); break;
            case "cls": startActivity(new Intent(this, Classic.class)); break;
            default: startActivity(new Intent(this, MainMenu.class));
        }
    }

    public void backPressed() {
        soundManager.makeTapSound();
        soundManager.terminate();

        startActivity(new Intent(this, MainMenu.class));
    }

    public void checkNewHighscore() {
        SharedPreferences highScore = getSharedPreferences("highScore", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = highScore.edit();

        switch (gameMode) {
            case "arc":
                if (score > highScore.getInt("arcScore", 0)) {
                    edit.putInt("arcScore", score);
                    edit.commit();

                    Toast.makeText(this, "New Highscore!", Toast.LENGTH_SHORT).show();
                }
                break;
            case "cls":
                if (score > highScore.getInt("clsScore", 0)) {
                    edit.putInt("clsScore", score);
                    edit.commit();

                    Toast.makeText(this, "New Highscore!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        soundManager.terminate();
        startActivity(new Intent(this, MainMenu.class));
    }
}
