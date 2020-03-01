package com.example.trung.dt;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class DotManager extends AppCompatActivity{
    private Calculation calc;
    private ConstraintLayout layout;
    private SoundManager soundManager;
    private GameMode game;

    public DotManager(Calculation calc, ConstraintLayout layout, Context context, GameMode game) {
        this.calc = calc;
        this.layout = layout;
        this.soundManager = new SoundManager(context);
        this.game = game;
    }

    public void createDot(int id, Context context) {
        final ImageView image = new ImageView(context);
        SharedPreferences settings = game.getSharedPreferences("settings", Context.MODE_PRIVATE);

        switch(settings.getString("dotImage", "")) {
            case "apple":
                image.setImageResource(R.mipmap.apple);
                break;
            case "cherry":
                image.setImageResource(R.mipmap.cherries);
                break;
            case "peach":
                image.setImageResource(R.mipmap.peach);
                break;
            case "pineapple":
                image.setImageResource(R.mipmap.pineapple);
                break;
            case "raspberry":
                image.setImageResource(R.mipmap.raspberry);
                break;
            case "orange":
                image.setImageResource(R.mipmap.orange);
                break;
            default: image.setImageResource(R.mipmap.apple);
        }
        float[] coords = calc.calcCoords();

        image.setX(coords[0]);
        image.setY(coords[1]);
        if (game.getClass().equals(Arcade.class)) {
            calc.blockCoords(id, coords);
        }
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.dotClicked(image);
            }
        });
        image.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
        layout.addView(image);
    }
}
