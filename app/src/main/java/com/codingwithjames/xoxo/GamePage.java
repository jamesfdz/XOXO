package com.codingwithjames.xoxo;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class GamePage extends AppCompatActivity {

    int activePlayer = 0; //0 = O and 1 is X
    boolean gameIsActive = true;
    int[] gameState = {2,2,2,2,2,2,2,2,2}; //2 means unplayed
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2
            ,4,6}};
    String winner;
    ConstraintLayout mBaseLayout;
    ImageView mX, mO;
    AdView mGamePageAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        mGamePageAd = findViewById(R.id.gamePageAd);
        AdRequest request = new AdRequest.Builder()
                .addTestDevice("CCFA8FF0F89AD93CC8A593BAE17AF056")
                .build();
        mGamePageAd.loadAd(request);

        mBaseLayout = findViewById(R.id.baseLayout);
        mX = findViewById(R.id.x_image);
        mO = findViewById(R.id.o_image);
    }

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter] == 2 && gameIsActive){ //player has not tapped if it is equal
            gameState[tappedCounter] = activePlayer;
//            counter.setTranslationY(-1000f);

            if(activePlayer == 0){
                counter.setImageResource(R.drawable.o);
                counter.animate().alphaBy(1f).setDuration(300);
                activePlayer = 1;
                mX.setImageResource(R.drawable.x);
                mO.setImageResource(R.drawable.o_disabled);
            }else{
                counter.setImageResource(R.drawable.x);
                counter.animate().alphaBy(1f).setDuration(300);
                activePlayer = 0;
                mO.setImageResource(R.drawable.o);
                mX.setImageResource(R.drawable.x_disabled);
            }
//            counter.animate().translationYBy(1000f).setDuration(300);

            for (int[] winningPosition :winningPositions){
                if(gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2){

                    gameIsActive = false;

                    winner = "Player 1 has Won !!!";

                    if(gameState[winningPosition[0]] == 1){
                        winner = "Player 2 has Won !!!";
                    }

                    showCustomDialog(this, winner);

                }
            }

            boolean gameIsOver = true;

            for(int gameStateCount : gameState){
                if(gameStateCount == 2){
                    gameIsOver = false;
                }
            }

            if(gameIsOver){

                gameIsActive = false;

                winner = "It's a Draw !!!";

                showCustomDialog(this, winner);
            }
        }

    }

    private void showCustomDialog(GamePage gamePage, String winner) {
        final Dialog dialog = new Dialog(gamePage);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView mWinnerMessage = dialog.findViewById(R.id.winnerMessage);
        mWinnerMessage.setText(winner);

        FrameLayout mDialogHome = dialog.findViewById(R.id.frmNo);
        mDialogHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                Intent backHome = new Intent(GamePage.this, MainActivity.class);
                backHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(backHome);
            }
        });

        FrameLayout mDialogPlayAgain = dialog.findViewById(R.id.frmOk);
        mDialogPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                playAgain();
            }
        });

        dialog.show();
    }

    public void playAgain() {

        gameIsActive = true;

        activePlayer = 0;

        for(int i =0; i < gameState.length; i++){
            gameState[i] = 2;
        }

        GridLayout mGridLayout = findViewById(R.id.gridLayout);

        for(int i = 0; i < mGridLayout.getChildCount(); i++){
            ((ImageView) mGridLayout.getChildAt(i)).setAlpha(0f);
        }
    }
}
