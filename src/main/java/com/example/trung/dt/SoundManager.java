package com.example.trung.dt;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SoundManager {
    private SoundPool mShortPlayer= null;
    private int[] tapSounds;
    private Context context;

    public SoundManager(Context context) {
        mShortPlayer = new SoundPool.Builder().setMaxStreams(10).build();
        tapSounds = new int[5];
        tapSounds[0] = mShortPlayer.load(context, R.raw.tap1,1);
        tapSounds[1] = mShortPlayer.load(context, R.raw.tap2,1);
        tapSounds[2] = mShortPlayer.load(context, R.raw.oneup, 1);
        tapSounds[3] = mShortPlayer.load(context, R.raw.miss, 1);
        this.context = context;
    }

    public void makeTapSound() {
        SharedPreferences volumePref = context.getSharedPreferences("volume", Context.MODE_PRIVATE);
        float volume = (float) volumePref.getInt("value", 100) / 100;

        int i = (int) (Math.random() * 2 + 1);

        if (i == 1) {
            Log.d("lolol", "makeTapSound: Played sound1");
            mShortPlayer.play(tapSounds[0], volume, volume, 1, 0, 1);
        } else {
            Log.d("lolol", "makeTapSound: Played sound2");
            mShortPlayer.play(tapSounds[1], volume, volume, 1, 0, 1);
        }
    }

    public void makeOneUpSound() {
        SharedPreferences volumePref = context.getSharedPreferences("volume", Context.MODE_PRIVATE);
        float volume = (float) volumePref.getInt("value", 100) / 100;

        mShortPlayer.play(tapSounds[2], volume, volume, 1, 0, 1);
    }

    public void makeMissSound() {
        SharedPreferences volumePref = context.getSharedPreferences("volume", Context.MODE_PRIVATE);
        float volume = (float) volumePref.getInt("value", 100) / 100;

        mShortPlayer.play(tapSounds[3], volume, volume, 1, 0, 1);
    }

    public void terminate() {
        this.mShortPlayer.release();
        this.mShortPlayer = null;
    }
}
