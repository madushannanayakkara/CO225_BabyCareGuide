package com.example.babydevelopmenttrackingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

public class startSplash extends AppCompatActivity {

    private static int SPLASH_TIMEOUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_splash);

        Animation fadeout = new AlphaAnimation(1,0);
        fadeout.setInterpolator(new AccelerateInterpolator());
        fadeout.setStartOffset(500);
        fadeout.setDuration(5400);

        ImageView image = findViewById(R.id.startIcon);
        image.setAnimation(fadeout);
        TextView txtStart  = findViewById(R.id.txtStartwelcome);
        txtStart.setAnimation(fadeout);
        TextView Name  = findViewById(R.id.txtStartName);
        Name.setAnimation(fadeout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(startSplash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIMEOUT);

    }


}