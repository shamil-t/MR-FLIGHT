package com.shamil.mr_flight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    ImageView logo;
    Animation splash;
    protected static int SPLASH_TIME = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.logo);
        splash = AnimationUtils.loadAnimation(this,R.anim.splash_anim);
        logo.startAnimation(splash);

        new Handler().postDelayed(() -> {
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);
                finish();
        }, SPLASH_TIME);
    }
}