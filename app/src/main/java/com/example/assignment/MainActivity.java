package com.example.assignment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 3434;
    MediaPlayer bgm,buttonClick;
    ImageView logo;
    long animationDuration=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        bgm = MediaPlayer.create(MainActivity.this,R.raw.bgm);
        buttonClick = MediaPlayer.create(MainActivity.this,R.raw.buttonclick);
        bgm.start();
        logo = findViewById(R.id.logo);

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(logo,"x",350f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(logo,"y",300f);
        ObjectAnimator animatorFadeIn = ObjectAnimator.ofFloat(logo,View.ALPHA,0.0f,1.0f);
        ObjectAnimator animatorRotare = ObjectAnimator.ofFloat(logo,"rotation",0.0f,360f);
        ObjectAnimator animatorZoomX = ObjectAnimator.ofFloat(logo,"scaleX",1,2,1);
        ObjectAnimator animatorZoomY = ObjectAnimator.ofFloat(logo,"scaleY",1,2,1);
        animatorZoomX.setDuration(2000);
        animatorZoomY.setDuration(2000);
        animatorY.setDuration(animationDuration);
        animatorX.setDuration(animationDuration);
        animatorFadeIn.setDuration(animationDuration);
        animatorRotare.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX,animatorY,animatorFadeIn,animatorRotare,animatorZoomX,animatorZoomY);
        animatorSet.start();

        animatorZoomX.setRepeatMode(ValueAnimator.RESTART);
        animatorZoomX.setRepeatCount(Animation.INFINITE);
        animatorZoomY.setRepeatMode(ValueAnimator.RESTART);
        animatorZoomY.setRepeatCount(Animation.INFINITE);
        animatorSet.playTogether(animatorZoomX,animatorZoomY);
        animatorSet.start();
    }

    public void start(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivityForResult(i, REQUEST_CODE);
        buttonClick.start();
        bgm.release();
    }

    public void barChart(View view){
        Intent i = new Intent(this, BarChartActivity.class);
        startActivityForResult(i, REQUEST_CODE);
        buttonClick.start();
        bgm.release();
    }

    public void gameLog(View view){
        Intent i = new Intent(this, GameLogActivity.class);
        startActivityForResult(i, REQUEST_CODE);
        buttonClick.start();
        bgm.release();
    }
}
