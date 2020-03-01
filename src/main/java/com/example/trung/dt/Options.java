package com.example.trung.dt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

public class Options extends AppCompatActivity {
    private ImageView apple;
    private ImageView cherry;
    private ImageView peach;
    private ImageView pineapple;
    private ImageView raspberry;
    private ImageView orange;

    private ImageView volumeIcon;

    private SeekBar volume;

    private SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        this.apple = findViewById(R.id.apple);
        apple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDot("apple");
            }
        });

        this.cherry = findViewById(R.id.cherry);
        cherry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDot("cherry");
            }
        });

        this.peach = findViewById(R.id.peach);
        peach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDot("peach");
            }
        });

        this.pineapple = findViewById(R.id.pineapple);
        pineapple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDot("pineapple");
            }
        });

        this.raspberry = findViewById(R.id.raspberry);
        raspberry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDot("raspberry");
            }
        });

        this.orange = findViewById(R.id.orange);
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDot("orange");
            }
        });

        this.volumeIcon = findViewById(R.id.volumeIcon);

        setCurrentDotEnabled();

        this.volume = findViewById(R.id.volume);
        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setVolumeIcon(progress);
                Log.d("lolol", "onProgressChanged: Volume: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarOnStopTrack();
            }
        });

        initVolumeBar();

        this.soundManager = new SoundManager(this);
    }

    public void setDot(String dot) {
        soundManager.makeTapSound();

        SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = settings.edit();

        switch(dot) {
            case "apple":
                edit.putString("dotImage", "apple");

                enableApple();
                disableCherry();
                disablePeach();
                disablePineapple();
                disableRaspberry();
                disableOrange();
                break;
            case "cherry":
                edit.putString("dotImage", "cherry");

                disableApple();
                enableCherry();
                disablePeach();
                disablePineapple();
                disableRaspberry();
                disableOrange();
                break;
            case "peach":
                edit.putString("dotImage", "peach");

                disableApple();
                disableCherry();
                enablePeach();
                disablePineapple();
                disableRaspberry();
                disableOrange();
                break;
            case "pineapple":
                edit.putString("dotImage", "pineapple");

                disableApple();
                disableCherry();
                disablePeach();
                enablePineapple();
                disableRaspberry();
                disableOrange();
                break;
            case "raspberry":
                edit.putString("dotImage", "raspberry");

                disableApple();
                disableCherry();
                disablePeach();
                disablePineapple();
                enableRaspberry();
                disableOrange();
                break;
            case "orange":
                edit.putString("dotImage", "orange");

                disableApple();
                disableCherry();
                disablePeach();
                disablePineapple();
                disableRaspberry();
                enableOrange();
                break;
            default: edit.putString("dotImage", "apple");
        }
        edit.commit();
    }

    public void setCurrentDotEnabled() {
        SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);

        switch(settings.getString("dotImage", "apple")) {
            case "apple": enableApple(); break;
            case "cherry": enableCherry(); break;
            case "peach": enablePeach(); break;
            case "pineapple": enablePineapple(); break;
            case "raspberry": enableRaspberry(); break;
            case "orange": enableOrange(); break;
        }
    }

    public void enableApple() {
        apple.setImageResource(R.mipmap.apple);
    }

    public void disableApple() {
        apple.setImageResource(R.mipmap.apple_unselected);
    }

    public void enableCherry() {
        cherry.setImageResource(R.mipmap.cherries);
    }

    public void disableCherry() {
        cherry.setImageResource(R.mipmap.cherries_unselected);
    }

    public void enablePeach() {
        peach.setImageResource(R.mipmap.peach);
    }

    public void disablePeach() {
        peach.setImageResource(R.mipmap.peach_unselected);
    }

    public void enablePineapple() {
        pineapple.setImageResource(R.mipmap.pineapple);
    }

    public void disablePineapple() {
        pineapple.setImageResource(R.mipmap.pineapple_unselected);
    }

    public void enableRaspberry() {
        raspberry.setImageResource(R.mipmap.raspberry);
    }

    public void disableRaspberry() {
        raspberry.setImageResource(R.mipmap.raspberry_unselected);
    }

    public void enableOrange() {
        orange.setImageResource(R.mipmap.orange);
    }

    public void disableOrange() {
        orange.setImageResource(R.mipmap.orange_unselected);
    }

    public void initVolumeBar() {
        SharedPreferences volumePref = getSharedPreferences("volume", Context.MODE_PRIVATE);
        volume.setProgress(volumePref.getInt("value", 100));
        setVolumeIcon(volumePref.getInt("value", 100));
    }

    public void seekBarOnStopTrack() {
        SharedPreferences volumePref = getSharedPreferences("volume", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = volumePref.edit();

        edit.putInt("value", volume.getProgress());
        edit.commit();

        Log.d("lolol", "onStopTrackingTouch: Saved Volume value: " + volumePref.getInt("value", 100));

        soundManager.makeTapSound();
    }

    /** Icongröße bearbeiten und Listener*/
    public void setVolumeIcon(int progress) {
        if (progress == 0) {
            volumeIcon.setImageResource(R.mipmap.thumbspeakermute);
        } else {
            volumeIcon.setImageResource(R.mipmap.thumbspeakermedium);
        }
    }

    @Override
    public void onBackPressed() {
        soundManager.terminate();
        startActivity(new Intent(this, MainMenu.class));
    }
}
