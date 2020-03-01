package com.example.trung.dt;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
// import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Classic extends AppCompatActivity implements GameMode{
    private ImageView start;
    private TextView speed;

    private ImageView heart1;
    private ImageView heart2;
    private ImageView heart3;

    private TextView clickedDotsOut;

    private int hp;

    private ConstraintLayout layout;

    // private Vibrator vibrator;
    private Calculation calc;
    private SoundManager soundManager;
    protected DotManager dotManager;

    protected int dotsOnScreen;
    protected int maxDots;
    private int clickedDots;
    private boolean missEnabled;
    private boolean stopTimer;
    private int currentLoop;

    private Handler handler;
    private final int MAKE_DOT = 0;
    private final int END_GAME = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic);

        start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initGame();
            }
        });

        speed = findViewById(R.id.speed);

        layout = findViewById(R.id.classic_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewClicked();
            }
        });

        heart1 = findViewById(R.id.heart1);
        heart2 = findViewById(R.id.heart2);
        heart3 = findViewById(R.id.heart3);

        clickedDotsOut = findViewById(R.id.clickedDotsOut);

        hp = 3;

        dotsOnScreen = 0;
        maxDots = 8;
        clickedDots = 0;

        missEnabled = false;
        stopTimer = false;

        // vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        calc = new Calculation(getWindowManager().getDefaultDisplay());
        soundManager = new SoundManager(this);
        dotManager = new DotManager(calc, layout, this, this);

        handler = new Handler() {
            public void handleMessage(Message msg) {
                final int what = msg.what;
                switch (what) {
                    case MAKE_DOT: makeDot(); break;
                    case END_GAME: endGame(); break;
                }
            }
        };
    }

    public void initGame() {
        soundManager.makeTapSound();
        layout.removeView(start);
        //speed.setVisibility(View.VISIBLE);
        clickedDotsOut.setVisibility(View.VISIBLE);
        calc.setBlockedCoords(new float[1][2]);
        missEnabled = true;

        heart1.setVisibility(View.VISIBLE);
        heart2.setVisibility(View.VISIBLE);
        heart3.setVisibility(View.VISIBLE);

        Runnable r = new Runnable() {
            @Override
            public void run() {
                loop:
                while (!stopTimer) {
                    for (currentLoop = 0; currentLoop < 1000; currentLoop++) {
                        //speed.setText("" + calc.calcSpeed(currentLoop));
                        try {
                            Thread.sleep(calc.calcSpeed(currentLoop));
                            handler.sendEmptyMessage(MAKE_DOT);
                            if (dotsOnScreen > maxDots) {
                                stopTimer = true;
                            }
                            if (stopTimer) {
                                break;
                            }
                        } catch (InterruptedException e) {

                        }
                    }
                }
                if (hp > 0) {
                    handler.sendEmptyMessage(END_GAME);
                }
            }
        };

        Thread timer = new Thread(r);
        timer.start();
    }

    public void makeDot() {
        if (hp < 3) {
            if ((int) (Math.random() * 10 + 1) == 10) {
                final ImageView image = new ImageView(this);
                image.setImageResource(R.mipmap.heartred);
                float[] coords = calc.calcCoords();

                image.setX(coords[0]);
                image.setY(coords[1]);

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (hp < 3) {
                            hp++;
                            setHp();
                            clickedDots++;
                            clickedDotsOut.setText("" + clickedDots);
                            if (!stopTimer) {
                                soundManager.makeOneUpSound();
                            }
                        }
                        // soundManager.makeTapSound();
                        layout.removeView(image);
                    }
                });

                image.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
                layout.addView(image);
            } else {
                dotsOnScreen++;
                dotManager.createDot(0, this);
            }
        } else {
            dotsOnScreen++;
            dotManager.createDot(0, this);
        }
    }

    public void dotClicked(View dot) {
        dotsOnScreen--;
        clickedDots++;
        clickedDotsOut.setText("" + clickedDots);
        if (!stopTimer) {
            soundManager.makeTapSound();
        }
        layout.removeView(dot);
    }

    public void viewClicked() {
        if (missEnabled) {
            hp -= 1;
            setHp();
            // vibrator.vibrate(70);
            soundManager.makeMissSound();
        }

        if (hp <= 0) {
            endGame();
        }
    }

    public void setHp() {
        switch (hp) {
            case 2: heart1.setImageResource(R.mipmap.heartred);
                    heart2.setImageResource(R.mipmap.heartred);
                    heart3.setImageResource(R.mipmap.heartblack); break;
            case 1: heart1.setImageResource(R.mipmap.heartred);
                    heart3.setImageResource(R.mipmap.heartblack);
                    heart2.setImageResource(R.mipmap.heartblack); break;
            case 0: heart1.setImageResource(R.mipmap.heartblack);
                    heart2.setImageResource(R.mipmap.heartblack);
                    heart3.setImageResource(R.mipmap.heartblack); break;
            default:
                heart1.setImageResource(R.mipmap.heartred);
                heart2.setImageResource(R.mipmap.heartred);
                heart3.setImageResource(R.mipmap.heartred);
        }
    }

    public void endGame() {
        stopTimer = true;
        soundManager.terminate();
        Intent intent = new Intent(this, GameOver.class);

        intent.putExtra("extra_output", "Clicked dots: " + clickedDots);
        intent.putExtra("game_mode", "cls");
        intent.putExtra("score", clickedDots);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        soundManager.terminate();
        startActivity(new Intent(this, MainMenu.class));
    }
}
