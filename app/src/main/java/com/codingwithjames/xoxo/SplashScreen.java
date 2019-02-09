package com.codingwithjames.xoxo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.ads.MobileAds;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    ConstraintLayout mSplashScreenBaseLayout;
    TextView mCodeText, mChezText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        MobileAds.initialize(this, "ca-app-pub-5397309444919460~5083848058");

        mSplashScreenBaseLayout = findViewById(R.id.splashScreenBaseLayout);
        mSplashScreenBaseLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);


        Intent musicService = new Intent();
        musicService.setClass(this, MusicService.class);
        startService(musicService);


        mCodeText = findViewById(R.id.code_text);
        mChezText = findViewById(R.id.chez_text);

        mCodeText.setTranslationX(-1000f);
        mCodeText.animate().translationXBy(1000f).setDuration(1000);

        mChezText.setTranslationX(1000f);
        mChezText.animate().translationXBy(-1000f).setDuration(1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(homeIntent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
