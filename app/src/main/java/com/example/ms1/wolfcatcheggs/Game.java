package com.example.ms1.wolfcatcheggs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;

public class Game extends AppCompatActivity {
    Timer timer;

    long periodUpdate =100;
    long animatorDiraction = 3000;
    Button pause ;
    boolean pause_flg = false;
    static Byte wolfPosition = 1;
    static Byte life = 3;
    Boolean checkEnd = false;
    static Integer account = 0;
    static Integer forCheck=0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer=new Timer();

        final long[] spawnNewEgg = {100};
        final long[] maxTimeSpawn = {spawnNewEgg[0] * 2 + 100};

        final CountDownTimer waitTimer = new CountDownTimer(Long.MAX_VALUE,periodUpdate) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (!checkEnd) {
                    startTimer(maxTimeSpawn[0], spawnNewEgg[0]);
                    if (spawnNewEgg[0]>200) {
                        spawnNewEgg[0] -= 24;
                        maxTimeSpawn[0] = spawnNewEgg[0] * 2 + 100;
                    }
                }
            }

            @Override
            public void onFinish() {
            }
        }.start();

    }

    private void startTimer(long maxTime,long newEgg){

        checkEnd=true;
        final CountDownTimer waitTimer1 = new CountDownTimer(maxTime, newEgg) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (life > 0) {
                    spawnEgg();
                } else {
                    if (forCheck==0) {
                        try {
                            periodUpdate = Long.MAX_VALUE;
                            Intent intent = new Intent(Game.this, EndGame.class);
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        forCheck++;
                    }
                }
            }
            @Override
            public void onFinish() {
                checkEnd=false;
            }
        }.start();
    }

    void spawnEgg(){
        byte random1 = (byte) (new Random().nextInt(69)+10);
        byte random2 = (byte) (new Random().nextInt(4)+1);
        egg((byte) (random1/10),random2);
    }

    @SuppressLint("InflateParams")
    public void egg(final Byte z, final Byte whereTube) {

        ObjectAnimator animatorX,animatorY;
        float x = 0,y = 0;
        final RelativeLayout relativeLayout;
        final View view1;
        LayoutInflater inflater = getLayoutInflater();

        switch (z) {
            case 5:
                view1 = inflater.inflate(R.layout.heart, null, false);break;
            case 6:
                view1 = inflater.inflate(R.layout.current, null, false);break;
            case 7:
                view1 = inflater.inflate(R.layout.bomb, null, false);break;
            default: view1 = inflater.inflate(R.layout.egg, null, false);break;

        }
        relativeLayout = findViewById(R.id.relative);

        switch (whereTube) {
            case 1:
                x = 550f;y = 270f;
                setPosition(view1, (ImageView) findViewById(R.id.imageViewEgg1));break;
            case 2:
                x = 1150f;y = 270f;
                setPosition(view1, (ImageView) findViewById(R.id.imageViewEgg2));break;
            case 3:
                x = 600f;y = 570f;
                setPosition(view1, (ImageView) findViewById(R.id.imageViewEgg3));break;
            case 4:
                x = 1100f;y = 570f;
                setPosition(view1, (ImageView) findViewById(R.id.imageViewEgg4));break;
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

                relativeLayout.removeView(view1);

                if (whereTube==1 || whereTube==3) {
                    animEnd(Rect.intersects(rc1, rc2), whereTube, ((ImageView) findViewById(R.id.imageViewEgg13Crush)), z, relativeLayout, view1);
                } else {
                    animEnd(Rect.intersects(rc1, rc2), whereTube, ((ImageView) findViewById(R.id.imageViewEgg24Crush)), z, relativeLayout, view1);
                }

            }

        });

    }

    private void animEnd(Boolean intersect, Byte wolfPosition1, ImageView imageView, Byte z, RelativeLayout relativeLayout, View view1){
        if (intersect && wolfPosition == wolfPosition1) {
            switch (z) {
                case 5:
                    catchLife(relativeLayout, view1);break;
                case 6:
                    catchCurrent();break;
                case 7:
                    catchBomb();break;
                default:((TextView) findViewById(R.id.textView)).setText(String.valueOf(Integer.parseInt((String) ((TextView) findViewById(R.id.textView)).getText()) + 1));
                account++;break;
            }
        } else {
            if (z < 5) {
                dontCatch(imageView);
            }
        }
    }
    private void catchBomb() {

        ImageView imageView = findViewById(R.id.imageViewForAnimation);
        imageView.setBackgroundResource(R.drawable.bombing_animation);
        final AnimationDrawable animation = (AnimationDrawable) imageView.getBackground();
        animation.start();
        final Animation a = AnimationUtils.loadAnimation(this,R.anim.fade);

        final int[] anim = {1};
        CountDownTimer waitTimer = new CountDownTimer(4500, 2000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (anim[0] ==2) {
                    animation.stop();
                    findViewById(R.id.imageViewForAnimation).startAnimation(a);
                } else anim[0]++;
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void catchCurrent() {
        final boolean[] clickable = {false};
        CountDownTimer waitTimer = new CountDownTimer(1500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                ((ImageView) findViewById(R.id.imageViewLight)).setImageResource(R.drawable.light);
                buttonClickable(clickable[0]);
                clickable[0] = true;
            }
            @Override
            public void onFinish() {
                ((ImageView) findViewById(R.id.imageViewLight)).setImageResource(0);
                buttonClickable(clickable[0]);
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
        (imageView).setImageResource(R.drawable.eggbroken);

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

            buttonClickable(false);

            pause.setText("Start");
        } else {
            pause_flg = false;

            pause.setText("Pause");

            buttonClickable(true);

            timer = new Timer();
        }
    }

    private void setPosition(View view1, ImageView imageView){
        view1.setX(imageView.getX());
        view1.setY(imageView.getY());
    }

    private void buttonClickable(Boolean clickable) {
        findViewById(R.id.imageButton1).setClickable(clickable);
        findViewById(R.id.imageButton2).setClickable(clickable);
        findViewById(R.id.imageButton3).setClickable(clickable);
        findViewById(R.id.imageButton4).setClickable(clickable);
    }
}
