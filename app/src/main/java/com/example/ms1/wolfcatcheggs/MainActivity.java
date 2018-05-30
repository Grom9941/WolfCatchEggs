package com.example.ms1.wolfcatcheggs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
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
import android.view.animation.AnimationUtils;
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
    Byte life = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer=new Timer();

        final long[] newEgg = {1000};
        long maxTime = Long.MAX_VALUE;

        final CountDownTimer waitTimer = new CountDownTimer(maxTime, newEgg[0]) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (life>0) {
                    spawnEgg();
                    newEgg[0]-=500;
                } else newEgg[0] =Long.MAX_VALUE;
            }

            @Override
            public void onFinish() {
            }
        }.start();

    }



    private void spawnEgg(){
        byte random1 = (byte) (new Random().nextInt(4)+1);
        egg(random1);
//                egg((byte) 1);
    }

    public void egg(final Byte z) {
        LayoutInflater inflater = getLayoutInflater();
        final View view1 = inflater.inflate(R.layout.egg,null,false);
        final RelativeLayout relativeLayout = findViewById(R.id.relative);

        ObjectAnimator animatorX = new ObjectAnimator();
        ObjectAnimator animatorY = new ObjectAnimator();
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
        } else {
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

                if (z==1) {
                    if (Rect.intersects(rc1, rc2) && wolfPosition == 1) {
                        relativeLayout.removeView(view1);
                    } else {
                        life--;
                        if (life==2){
                            ((ImageView)findViewById(R.id.imageViewLife1)).setImageResource(0);
                        } else ((ImageView)findViewById(R.id.imageViewLife2)).setImageResource(0);
                        final int[] l = {1};
                        relativeLayout.removeView(view1);
                        ((ImageView)findViewById(R.id.imageViewEgg13Crush)).setImageResource(R.drawable.egg);
                        CountDownTimer waitTimer = new CountDownTimer(1500, 500) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                if (l[0] !=1) {
                                    ((ImageView) findViewById(R.id.imageViewEgg13Crush)).setImageResource(0);
                                } else l[0]++;
                            }

                            @Override
                            public void onFinish() {
                            }
                        }.start();

                    }
                } else if (z==2) {
                    if (Rect.intersects(rc1, rc2) && wolfPosition == 2) {
                        relativeLayout.removeView(view1);
                    }else {
                        life--;
                        if (life==2){
                            ((ImageView)findViewById(R.id.imageViewLife1)).setImageResource(0);
                        } else ((ImageView)findViewById(R.id.imageViewLife2)).setImageResource(0);
                        final int[] l = {1};
                        relativeLayout.removeView(view1);
                        ((ImageView)findViewById(R.id.imageViewEgg24Crush)).setImageResource(R.drawable.egg);
                        CountDownTimer waitTimer = new CountDownTimer(1500, 500) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                if (l[0] !=1) {
                                    ((ImageView) findViewById(R.id.imageViewEgg24Crush)).setImageResource(0);
                                } else l[0]++;
                            }

                            @Override
                            public void onFinish() {
                            }
                        }.start();

                    }
                } else if (z==3) {
                    if (Rect.intersects(rc1, rc2) && wolfPosition == 3) {
                        relativeLayout.removeView(view1);
                    } else {
                        life--;
                        if (life==2){
                            ((ImageView)findViewById(R.id.imageViewLife1)).setImageResource(0);
                        } else ((ImageView)findViewById(R.id.imageViewLife2)).setImageResource(0);
                        final int[] l = {1};
                        relativeLayout.removeView(view1);
                        ((ImageView)findViewById(R.id.imageViewEgg13Crush)).setImageResource(R.drawable.egg);
                        CountDownTimer waitTimer = new CountDownTimer(1500, 500) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                if (l[0] !=1) {
                                    ((ImageView) findViewById(R.id.imageViewEgg13Crush)).setImageResource(0);
                                } else l[0]++;
                            }

                            @Override
                            public void onFinish() {
                            }
                        }.start();

                    }
                } else {
                    if (Rect.intersects(rc1, rc2) && wolfPosition == 4) {
                        relativeLayout.removeView(view1);
                    } else {
                        life--;
                        if (life==2){
                            ((ImageView)findViewById(R.id.imageViewLife1)).setImageResource(0);
                        } else ((ImageView)findViewById(R.id.imageViewLife2)).setImageResource(0);
                        final int[] l = {1};
                        relativeLayout.removeView(view1);
                        ((ImageView)findViewById(R.id.imageViewEgg24Crush)).setImageResource(R.drawable.egg);
                        CountDownTimer waitTimer = new CountDownTimer(1500, 500) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                if (l[0] !=1) {
                                    ((ImageView) findViewById(R.id.imageViewEgg24Crush)).setImageResource(0);
                                } else l[0]++;
                            }

                            @Override
                            public void onFinish() {
                            }
                        }.start();

                    }
                }
            }

//    @Override
//    public void onAnimationPause(Animator animation) {
//        super.onAnimationPause(animation);
//    }
//
//    @Override
//    public void onAnimationResume(Animator animation) {
//        super.onAnimationResume(animation);
//    }
});
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
