package com.example.trung.dt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainMenu extends AppCompatActivity {
    private ImageView arcade;
    private ImageView classic;
    private ImageView highscore;
    private ImageView options;
    private ImageView test;

    private SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        this.arcade = findViewById(R.id.arcade);
        arcade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchArcade();
            }
        });

        this.classic = findViewById(R.id.classic);
        classic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchClassic();
            }
        });

        this.highscore = findViewById(R.id.highscore);
        highscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHighscore();
            }
        });

        this.options = findViewById(R.id.options);
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchOptions();
            }
        });

        this.test = findViewById(R.id.test);
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDebug();
            }
        });

        this.soundManager = new SoundManager(this);
    }

    public void launchArcade() {
        soundManager.makeTapSound();
        soundManager.terminate();

        Intent intent = new Intent(this, Arcade.class);
        startActivity(intent);
    }

    public void launchClassic() {
        soundManager.makeTapSound();
        soundManager.terminate();

        Intent intent = new Intent(this, Classic.class);
        startActivity(intent);
    }

    public void launchHighscore() {
        soundManager.makeTapSound();
        soundManager.terminate();

        Intent intent = new Intent(this, HighScore.class);
        startActivity(intent);
    }

    public void launchOptions() {
        soundManager.makeTapSound();
        soundManager.terminate();

        Intent intent = new Intent(this, Options.class);
        startActivity(intent);
    }

    public void launchDebug() {
        soundManager.makeTapSound();
        soundManager.terminate();

        Intent intent = new Intent(this, Test.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }
}
