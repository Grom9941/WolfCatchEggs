package com.example.ms1.wolfcatcheggs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Timer;
// todo how compare png and imageview

public class MainActivity extends AppCompatActivity {
    Timer timer;

    Button pause ;
    boolean pause_flg = false;
    long animatorDiraction = 3000;
    Byte wolfPosition = 1;
    Byte life = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer=new Timer();

        final long[] newEgg = {5000};
        final long[] maxTime = {newEgg[0] * 2 + 100};
//        long maxTime = Long.MAX_VALUE;

        final CountDownTimer waitTimer = new CountDownTimer(maxTime[0], newEgg[0]) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (life>0) {
                    spawnEgg();
                } else newEgg[0] =Long.MAX_VALUE;
            }

            @Override
            public void onFinish() {
                newEgg[0]=1000;
                maxTime[0] = newEgg[0]*2+100;
                this.start();
            }
        }.start();

    }



    private void spawnEgg(){
        byte random1 = (byte) (new Random().nextInt(69)+10);
        Integer random2 = new Random().nextInt(4)+1;
        egg((byte) 7,random2);
    }

    @SuppressLint("InflateParams")
    public void egg(final Byte z, final Integer whereTube) {
        final RelativeLayout relativeLayout;
        final View view1;
        if (z<5) {
            LayoutInflater inflater = getLayoutInflater();
            view1 = inflater.inflate(R.layout.egg, null, false);
            relativeLayout = findViewById(R.id.relative);
        } else if (z==5) {
            LayoutInflater inflater = getLayoutInflater();
            view1 = inflater.inflate(R.layout.heart, null, false);
            relativeLayout = findViewById(R.id.relative);
        } else if (z==6){
            LayoutInflater inflater = getLayoutInflater();
            view1 = inflater.inflate(R.layout.current, null, false);
            relativeLayout = findViewById(R.id.relative);
        } else {
            LayoutInflater inflater = getLayoutInflater();
            view1 = inflater.inflate(R.layout.bomb, null, false);
            relativeLayout = findViewById(R.id.relative);
        }

        ObjectAnimator animatorX;
        ObjectAnimator animatorY;
        float x = 0;
        float y = 0;

        switch (whereTube) {
            case 1:
                x = 550f;
                y = 270f;
                view1.setX(findViewById(R.id.imageViewEgg1).getX());
                view1.setY(findViewById(R.id.imageViewEgg1).getY());
                break;
            case 2:
                x = 1150f;
                y = 270f;
                view1.setX(findViewById(R.id.imageViewEgg2).getX());
                view1.setY(findViewById(R.id.imageViewEgg2).getY());
                break;
            case 3:
                x = 600f;
                y = 570f;
                view1.setX(findViewById(R.id.imageViewEgg3).getX());
                view1.setY(findViewById(R.id.imageViewEgg3).getY());
                break;
            case 4:
                x = 1100f;
                y = 570f;
                view1.setX(findViewById(R.id.imageViewEgg4).getX());
                view1.setY(findViewById(R.id.imageViewEgg4).getY());
                break;
        }
        relativeLayout.addView(view1);

        animatorX = ObjectAnimator.ofFloat(view1,"x",x);
        animatorX.setDuration(animatorDiraction);
        animatorY = ObjectAnimator.ofFloat(view1,"y",y);
        animatorY.setDuration(animatorDiraction);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view1, View.ALPHA, 1.0f, 0.0f);
        alphaAnimator.setDuration(animatorDiraction);
        final ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(view1, "rotation", 0f, 360f);
        rotateAnimator.setDuration(animatorDiraction);

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY, rotateAnimator);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                Rect rc1 = new Rect();
                view1.getDrawingRect(rc1);
                Rect rc2 = new Rect();
                findViewById(R.id.imageViewWolf).getDrawingRect(rc2);

                if (whereTube==1) {
                    if (Rect.intersects(rc1, rc2) && wolfPosition == 1) {
                        relativeLayout.removeView(view1);
                        if (z==5) { catchLife(relativeLayout,view1); }
                        if (z==6) { catchCurrent(); }
                        if (z==7) { catchBomb(); }
                    } else {
                        if (z<5) { dontCatch((ImageView) findViewById(R.id.imageViewEgg13Crush)); }
                        else relativeLayout.removeView(view1);
                    }
                } else if (whereTube==2) {
                    if (Rect.intersects(rc1, rc2) && wolfPosition == 2) {
                        relativeLayout.removeView(view1);
                        if (z==5) { catchLife(relativeLayout,view1); }
                        if (z==6) { catchCurrent(); }
                        if (z==7) { catchBomb(); }
                    }else {
                        if (z<5) { dontCatch((ImageView) findViewById(R.id.imageViewEgg24Crush)); }
                        else relativeLayout.removeView(view1);
                    }
                } else if (whereTube==3) {
                    if (Rect.intersects(rc1, rc2) && wolfPosition == 3) {
                        relativeLayout.removeView(view1);
                        if (z==5) { catchLife(relativeLayout,view1); }
                        if (z==6) { catchCurrent(); }
                        if (z==7) { catchBomb(); }
                    } else {
                        if (z<5) { dontCatch((ImageView) findViewById(R.id.imageViewEgg13Crush)); }
                        else relativeLayout.removeView(view1);
                    }
                } else if (whereTube==4) {
                    if (Rect.intersects(rc1, rc2) && wolfPosition == 4) {
                        relativeLayout.removeView(view1);
                        if (z==5) { catchLife(relativeLayout,view1); }
                        if (z==6) { catchCurrent(); }
                    } else {
                        if (z<5) { dontCatch((ImageView) findViewById(R.id.imageViewEgg24Crush)); }
                        else relativeLayout.removeView(view1);
                    }
                }
            }

});

    }

    private void catchBomb() {
        life--;
        if (life==2){
            ((ImageView)findViewById(R.id.imageViewLife1)).setImageResource(0);
        } else ((ImageView)findViewById(R.id.imageViewLife2)).setImageResource(0);

//        ImageView imageView = findViewById(R.id.imageViewWolf);
//        imageView.setBackgroundResource(R.drawable.bombing);
//        AnimationDrawable animation = (AnimationDrawable) imageView.getBackground();
//        animation.setOneShot(false);
//        animation.start();
    }

    private void catchCurrent() {
        final boolean[] clickable = {false};
        CountDownTimer waitTimer = new CountDownTimer(1500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                ((ImageView) findViewById(R.id.imageViewLight)).setImageResource(R.drawable.light);
                findViewById(R.id.imageButton1).setClickable(clickable[0]);
                findViewById(R.id.imageButton2).setClickable(clickable[0]);
                findViewById(R.id.imageButton3).setClickable(clickable[0]);
                findViewById(R.id.imageButton4).setClickable(clickable[0]);
                clickable[0] = true;
            }
            @Override
            public void onFinish() {
                ((ImageView) findViewById(R.id.imageViewLight)).setImageResource(0);
                findViewById(R.id.imageButton1).setClickable(clickable[0]);
                findViewById(R.id.imageButton2).setClickable(clickable[0]);
                findViewById(R.id.imageButton3).setClickable(clickable[0]);
                findViewById(R.id.imageButton4).setClickable(clickable[0]);
            }
        }.start();
    }

    private void catchLife(RelativeLayout relativeLayout, View view1) {
        if (life!=3) { life++; }
        if (life==3){
            ((ImageView)findViewById(R.id.imageViewLife1)).setImageResource(R.drawable.heart);
        } else ((ImageView)findViewById(R.id.imageViewLife2)).setImageResource(R.drawable.heart);
        relativeLayout.removeView(view1);
    }

    private void dontCatch (final ImageView imageView){
        life--;
        if (life==2){
            ((ImageView)findViewById(R.id.imageViewLife1)).setImageResource(0);
        } else ((ImageView)findViewById(R.id.imageViewLife2)).setImageResource(0);
        final int[] l = {1};
        (imageView).setImageResource(R.drawable.egg);

        CountDownTimer waitTimer = new CountDownTimer(1500, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (l[0] !=1) {
                    (imageView).setImageResource(0);
                } else l[0]++;
            }

            @Override
            public void onFinish() {
            }
        }.start();
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
