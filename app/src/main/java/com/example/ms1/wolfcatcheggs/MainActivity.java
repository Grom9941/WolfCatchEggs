package com.example.ms1.wolfcatcheggs;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.sql.Driver;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;
// todo how compare png and imageview

public class MainActivity extends AppCompatActivity {
    Timer timer;

    Button pause ;
    boolean pause_flg = false;
    long animatorDiraction = 3000;
    Byte wolfPosition = 1;
    LayoutInflater inflater ;
    View view1 ;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.egg,null,false);
        relativeLayout = findViewById(R.id.relative);

        timer=new Timer();

        long newEgg=10000;
        long maxTime = 300000;

        CountDownTimer waitTimer = new CountDownTimer(maxTime, newEgg) {
            @Override
            public void onTick(long millisUntilFinished) {
                spawnEgg();
            }

            @Override
            public void onFinish() {
//                Rect rc1 = new Rect();
//                view1.getDrawingRect(rc1);
//                Rect rc2 = new Rect();
//                findViewById(R.id.imageViewWolf).getDrawingRect(rc2);
//
//                if (Rect.intersects(rc1, rc2) && wolfPosition == 1) {
//                    relativeLayout.removeView(view1);
//                }

            }
        }.start();

        Log.d("d","d");
    }



    private void spawnEgg(){
        byte random1 = (byte) (new Random().nextInt(4)+1);
        egg(random1);
        //        egg((byte) 1);
    }

    public void egg(final Byte z) {


        ObjectAnimator animatorX;
        ObjectAnimator animatorY;
        float x = 0;
        float y = 0;

        if (z==1) {
            x = 550f;y = 270f;
            view1.setX(findViewById(R.id.imageViewEgg1).getX());
            view1.setY(findViewById(R.id.imageViewEgg1).getY());
            relativeLayout.addView(view1);
        } else if (z==2) {
            x=1150f;y=270f;
            view1.setX(findViewById(R.id.imageViewEgg2).getX());view1.setY(findViewById(R.id.imageViewEgg2).getY());
            relativeLayout.addView(view1);
        } else if (z==3) {
            x=600f;y=570f;
            view1.setX(findViewById(R.id.imageViewEgg3).getX());view1.setY(findViewById(R.id.imageViewEgg3).getY());
            relativeLayout.addView(view1);
        } else if (z==4) {
            x = 1100f;y = 570f;
            view1.setX(findViewById(R.id.imageViewEgg4).getX());view1.setY(findViewById(R.id.imageViewEgg4).getY());
            relativeLayout.addView(view1);
        }

        animatorX = ObjectAnimator.ofFloat(view1,"x",x);
        animatorX.setDuration(animatorDiraction);
        animatorY = ObjectAnimator.ofFloat(view1,"y",y);
        animatorY.setDuration(animatorDiraction);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view1, View.ALPHA, 1.0f, 0.0f);
        alphaAnimator.setDuration(animatorDiraction);
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(view1, "rotation", 0f, 360f);
        rotateAnimator.setDuration(animatorDiraction);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY, rotateAnimator);
        animatorSet.start();

//        CountDownTimer waitTimer = new CountDownTimer(6000,2200) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                Rect rc1 = new Rect();
//                view1.getDrawingRect(rc1);
//                Rect rc2 = new Rect();
//                findViewById(R.id.imageViewWolf).getDrawingRect(rc2);
//                if (Rect.intersects(rc1,rc2) && wolfPosition==1) {
//                    relativeLayout.removeView(view1);
//                }
//            }
//            @Override
//            public void onFinish() {
//
//            }
//        }.start();
//
       /* Rect rc1 = new Rect();
        view1.getDrawingRect(rc1);
        Rect rc2 = new Rect();
        ImageView imageView = findViewById(R.id.imageViewWolf);
        imageView.getDrawingRect(rc2);*/
//        Rect rc1 = new Rect();
//        view1.getDrawingRect(rc1);
//        Rect rc2 = new Rect();
//        findViewById(R.id.imageViewWolf).getDrawingRect(rc2);
//
//        if (Rect.intersects(rc1, rc2) && wolfPosition == 1) {
//            relativeLayout.removeView(view1);
//        }
//        new DeleteEggextends().doInBackground(view1,wolfPosition,relativeLayout);

//        try {
//            Log.i(TAG, "Going to sleep");
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        SystemClock.sleep(1000);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void clickButton1(View view) {
        ImageView imageView = findViewById(R.id.imageViewWolf);
        imageView.setImageResource(R.drawable.wolf1);
        wolfPosition = 1;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void clickButton2(View view) {
        ImageView imageView = findViewById(R.id.imageViewWolf);
        imageView.setImageResource(R.drawable.wolf2);
        wolfPosition = 2;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void clickButton3(View view) {
        ImageView imageView = findViewById(R.id.imageViewWolf);
        imageView.setImageResource(R.drawable.wolf3);
        wolfPosition = 3;

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void clickButton4(View view) {
        ImageView imageView = findViewById(R.id.imageViewWolf);
        imageView.setImageResource(R.drawable.wolf4);
        wolfPosition = 4;
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void pushedPause(View view) {
        pause = findViewById(R.id.button);
        if (!pause_flg) {
            pause_flg = true;

            timer.cancel();
            timer=null;

            findViewById(R.id.imageButton1).setClickable(false);
            findViewById(R.id.imageButton2).setClickable(false);
            findViewById(R.id.imageButton3).setClickable(false);
            findViewById(R.id.imageButton4).setClickable(false);

            pause.setText("Start");
        } else {
            pause_flg = false;

            pause.setText("Pause");

            findViewById(R.id.imageButton1).setClickable(true);
            findViewById(R.id.imageButton2).setClickable(true);
            findViewById(R.id.imageButton3).setClickable(true);
            findViewById(R.id.imageButton4).setClickable(true);

            timer = new Timer();
        }
    }
}
