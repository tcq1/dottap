package com.example.trung.dt;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
// import android.os.Vibrator;

public class Test extends AppCompatActivity implements GameMode {
    private ImageView start;
    private ConstraintLayout layout;
    private TextView levelDisplay;
    private ProgressBar progressBar;

    private int level;
    private int dotsClicked;

    private Timer timer;
    private int levelTime = 10;

    private boolean missEnabled;

    // private Vibrator vibrator;

    private ObjectAnimator backGroundFade;

    private SoundManager soundManager;
    private Calculation calc;
    private DotManager dotManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arcade);

        layout = findViewById(R.id.arcade_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewClicked();
            }
        });

        start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initGame();
            }
        });

        levelDisplay = findViewById(R.id.level);

        progressBar = findViewById(R.id.progressBar);
        // timer = new Timer(this, levelTime);

        level = 0;

        // vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        backGroundFade = ObjectAnimator.ofObject(layout, "backgroundColor", new ArgbEvaluator(), Color.parseColor("#f2dd5d"), Color.parseColor("#ffffff"));

        soundManager = new SoundManager(this);
        calc = new Calculation(getWindowManager().getDefaultDisplay());
        dotManager = new DotManager(calc, layout, this, this);
    }

    public void initGame() {
        soundManager.makeTapSound();
        layout.removeView(start);

        levelDisplay.setVisibility(View.VISIBLE);
        levelDisplay.setText("" + level);

        progressBar.setVisibility(View.VISIBLE);

        missEnabled = true;

        test();
    }

    public void test() {
        for (int i = 0; i < 1000; i++) {
            dotManager.createDot(i, this);
        }
    }

    public void dotClicked(View dot) {
        dotsClicked++;
        soundManager.makeTapSound();
        layout.removeView(dot);
    }

    public void viewClicked() {
        if (missEnabled) {
            incrTime(level * 10);
            timer.increaseCurrentTime(level * 10);
            // vibrator.vibrate(70);
            soundManager.makeMissSound();
            //layout.setBackgroundColor(Color.parseColor("#ffffff"));
            backGroundFade.setDuration(1000);
            //backGroundFade.start();
        }
    }

    public void incrTime(int time) {
        progressBar.incrementProgressBy(time);
    }

    public void endGame() {
        soundManager.terminate();

        Intent intent = new Intent(this, GameOver.class);

        intent.putExtra("extra_output", "Reached Level: " + level);
        intent.putExtra("game_mode", "arc");
        intent.putExtra("score", level);

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        soundManager.terminate();
        startActivity(new Intent(this, MainMenu.class));
    }
}
