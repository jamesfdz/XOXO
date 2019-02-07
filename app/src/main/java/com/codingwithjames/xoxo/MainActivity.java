package com.codingwithjames.xoxo;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout mMainLayout;
    Button mPlay;

    ImageView mTitle, mTitleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainLayout = findViewById(R.id.mainLayout);
        mPlay = findViewById(R.id.play);
        mTitle = findViewById(R.id.title);
        mTitleImage = findViewById(R.id.titleImage);

        mTitle.setTranslationY(1000f);
        mTitleImage.setTranslationY(1000f);
        mPlay.setTranslationY(1000f);

        mTitle.animate().translationYBy(-1000f).setDuration(2000);
        mTitleImage.animate().translationYBy(-1000f).setDuration(2000);
        mPlay.animate().translationYBy(-1000f).setDuration(2000);

    }

    public void startGame(View view) {
        Intent gameStartActivity = new Intent(this, GamePage.class);
        startActivity(gameStartActivity);
    }

    protected void onDestroy() {
        //stop service and stop music
        stopService(new Intent(MainActivity.this, MusicService.class));
        super.onDestroy();
    }
}
