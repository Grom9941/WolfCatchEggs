package com.example.ms1.wolfcatcheggs.Model;

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

import com.example.ms1.wolfcatcheggs.View.EndGame;
import com.example.ms1.wolfcatcheggs.R;

import java.util.Random;
import java.util.Timer;

public class Game extends AppCompatActivity {

    private Timer timer;

    private boolean pauseFlag = false;
    private final  long[] spawnNewEgg = {ChangingVariables.spawnNewEgg};
    private final long[] maxTimeSpawn = {spawnNewEgg[0] * 2 + 100};

    private static Byte wolfPosition = 1;
    ObjectGame objectGame = new ObjectGame();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer=new Timer();
        startTimer(maxTimeSpawn[0], spawnNewEgg[0]);

//        BroadcastReceiver myReciver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//
//                if (spawnNewEgg[0]>200) {
//                    spawnNewEgg[0] -= delta;
//                    maxTimeSpawn[0] = spawnNewEgg[0] * 2 + 100;
//                }
//                startTimer(maxTimeSpawn[0], spawnNewEgg[0]);
//            }
//        };

    }

    /**
     * Метод для перезапуска таймера создания объекта который катиться
     */
    private void restartTimer(){
        Byte delta = 24; //после спавна объекта уменьшается время спавна на delta

        if (spawnNewEgg[0]>200) {

            spawnNewEgg[0] -= delta;
            maxTimeSpawn[0] = spawnNewEgg[0] * 2 + 100;

        }
        startTimer(maxTimeSpawn[0], spawnNewEgg[0]);
    }

    /**
     * Запускает таймер для объекта который катиться
     * @param maxTime Время работы таймера после которого таймер выключается
     * @param newEgg Период создания объекта
     */
    private void startTimer(long maxTime, final long newEgg){

        final CountDownTimer waitTimer1 = new CountDownTimer(maxTime, newEgg) {

            @Override
            public void onTick(long millisUntilFinished) {

                if (objectGame.getLife() > 0) {
                    spawnEgg();
                } else {


                        try {
                            Intent intent = new Intent(getApplicationContext(), EndGame.class);
                            intent.putExtra("account", objectGame.getAccount());
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        this.cancel();
                    }
                }

            @Override
            public void onFinish() {
                restartTimer();
//                IntentFilter intentFilter = new IntentFilter();
//                intentFilter.addAction("return");
            }
        }.start();
    }

    /**
     * Выбор рандомом какой предмет покатиться и из какой трубы
     */
    private void spawnEgg(){
        byte whatRoll = (byte) (new Random().nextInt(69)+10);
        byte numberTube = (byte) (new Random().nextInt(4)+1);

        egg((byte) (whatRoll/10),numberTube);
    }

    /**
     * Создание и реализация движения объекта а также переход к методам которые отвечают за обработку ловли
      * @param numberChoice Номер объекта 1,2,3,4-egg 5-heart 6-current 7-bomb
     * @param whereTube Номер трубы 1-левая верхняя 2-правая верхняя 3-левая нижняя 4-правая нижняя
     */
    @SuppressLint("InflateParams")
    private void egg(final Byte numberChoice, final Byte whereTube) {

        ObjectAnimator animatorX,animatorY;
        float x = 0,y = 0;
        final RelativeLayout relativeLayout;
        final View viewUse;

        LayoutInflater inflater = getLayoutInflater();

        switch (numberChoice) {
            case 5:
                viewUse = inflater.inflate(R.layout.heart, null, false);break;
            case 6:
                viewUse = inflater.inflate(R.layout.current, null, false);break;
            case 7:
                viewUse = inflater.inflate(R.layout.bomb, null, false);break;

            default: viewUse = inflater.inflate(R.layout.egg, null, false);break;

        }
        relativeLayout = findViewById(R.id.relative);

        switch (whereTube) {
            case 1:
                x = 550f;y = 270f;
                setPosition(viewUse, (ImageView) findViewById(R.id.imageViewEgg1));break;
            case 2:
                x = 1150f;y = 270f;
                setPosition(viewUse, (ImageView) findViewById(R.id.imageViewEgg2));break;
            case 3:
                x = 600f;y = 570f;
                setPosition(viewUse, (ImageView) findViewById(R.id.imageViewEgg3));break;
            case 4:
                x = 1100f;y = 570f;
                setPosition(viewUse, (ImageView) findViewById(R.id.imageViewEgg4));break;
        }
        relativeLayout.addView(viewUse);

        animatorX = ObjectAnimator.ofFloat(viewUse,"x",x);
        long animatorDiraction = ChangingVariables.animatorDiraction;
        animatorX.setDuration(animatorDiraction);
        animatorY = ObjectAnimator.ofFloat(viewUse,"y",y);
        animatorY.setDuration(animatorDiraction);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(viewUse, View.ALPHA, 1.0f, 0.0f);
        alphaAnimator.setDuration(animatorDiraction);
        final ObjectAnimator rotateAnimator =
                ObjectAnimator.ofFloat(viewUse, "rotation", 0f, 360f);
        rotateAnimator.setDuration(animatorDiraction);

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY, rotateAnimator);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                Rect rc1 = new Rect();
                viewUse.getDrawingRect(rc1);
                Rect rc2 = new Rect();
                findViewById(R.id.imageViewWolf).getDrawingRect(rc2);

                relativeLayout.removeView(viewUse);

                if (whereTube==1 || whereTube==3) {
                    animEnd(Rect.intersects(rc1, rc2), whereTube,
                            ((ImageView) findViewById(R.id.imageViewEgg13Crush)), numberChoice, relativeLayout, viewUse);
                } else {
                    animEnd(Rect.intersects(rc1, rc2), whereTube,
                            ((ImageView) findViewById(R.id.imageViewEgg24Crush)), numberChoice, relativeLayout, viewUse);
                }
            }

        });

    }

    /**
     * Реализация конца анимации выбор между пойманным и не пойманным
     * @param intersect Поймал ли волк
     * @param wolfPosition1 Позиция волка поотношению к трубе намера соответствуют номеру трубы(смотреть в описанию к предыдущему методу)
     * @param imageViewCrush Место куда нужно упасть яйцу если оно разбилось
     * @param numberChoice Номер объекта который был создан
     * @param relativeLayout RelativeLayout с объектом
     * @param viewHeart View место для отображения увеличения жизни
     */
    private void animEnd(Boolean intersect, Byte wolfPosition1, ImageView imageViewCrush, Byte numberChoice, RelativeLayout relativeLayout, View viewHeart){

        if (intersect && wolfPosition.equals(wolfPosition1)) {

            switch (numberChoice) {
                case 5:
                    catchLife(relativeLayout, viewHeart);break;
                case 6:
                    catchCurrent();break;
                case 7:
                    catchBomb();break;

                default:((TextView) findViewById(R.id.textView)).setText(String.valueOf(
                        Integer.parseInt((String) ((TextView) findViewById(R.id.textView)).getText()) + 1));
                objectGame.setAccount();
                break;
            }
        } else {
            if (numberChoice < 5) {
                dontCatch(imageViewCrush);
            }
        }
    }

    /**
     * Действия для пойманной бомбы
     */
    private void catchBomb() {

        final int[] anim = {1};

        ImageView imageView = findViewById(R.id.imageViewForAnimation);
        imageView.setBackgroundResource(R.drawable.bombing_animation);

        final AnimationDrawable animation = (AnimationDrawable) imageView.getBackground();
        animation.start();
        final Animation a = AnimationUtils.loadAnimation(this,R.anim.fade);

        CountDownTimer bombTimer = new CountDownTimer(4500, 2000) {
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

    /**
     * Действия для пойманного тока
     */
    private void catchCurrent() {

        final boolean[] clickable = {false};

        CountDownTimer currentTimer = new CountDownTimer(1500, 1000) {
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

    /**
     * Действия для пойманного тока
     * @param relativeLayout RelativeLayout с объектом
     * @param viewHeart View место для отображения увеличения жизни
     */
    private void catchLife(RelativeLayout relativeLayout, View viewHeart) {

        if (objectGame.getLife()!=3) { objectGame.setLife((byte) (objectGame.getLife()+1)); }

        if (objectGame.getLife()==3){
            ((ImageView)findViewById(R.id.imageViewLife1)).setImageResource(R.drawable.heart);
        } else ((ImageView)findViewById(R.id.imageViewLife2)).setImageResource(R.drawable.heart);

        relativeLayout.removeView(viewHeart);
    }

    /**
     * Действия для е пойманного яйца
     * @param imageView ImageView яйца
     */
    private void dontCatch (final ImageView imageView){

        final int[] l = {1};
        objectGame.setLife((byte) (objectGame.getLife()-1));

        if (objectGame.getLife()==2){ ((ImageView)findViewById(R.id.imageViewLife1)).setImageResource(0);
        } else ((ImageView)findViewById(R.id.imageViewLife2)).setImageResource(0);

        (imageView).setImageResource(R.drawable.eggbroken);

        CountDownTimer dontCatchTimer = new CountDownTimer(1500, 500) {
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

    /**
     * Нажатие паузы
     * @param view View паузы
     */
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void pushedPause(View view) {

        Button pause = findViewById(R.id.button);

        if (!pauseFlag) {

            pauseFlag = true;

            timer.cancel();
            timer = null;

            buttonClickable(false);

            pause.setText("Start");
        } else {
            pauseFlag = false;
            pause.setText("Pause");

            buttonClickable(true);

            timer = new Timer();
        }
    }

    /**
     * Установление позиции объекта
     * @param viewUse Используемая view
     * @param imageView ImageView объекта
     */
    private void setPosition(View viewUse, ImageView imageView){

        viewUse.setX(imageView.getX());
        viewUse.setY(imageView.getY());

    }

    /**
     * Установка возможности клика на кнопки отвечающиеся за перемещение позиции волка
     * @param clickable Установить кликабельность или не кликабельность
     */
    private void buttonClickable(Boolean clickable) {

        findViewById(R.id.imageButton1).setClickable(clickable);
        findViewById(R.id.imageButton2).setClickable(clickable);
        findViewById(R.id.imageButton3).setClickable(clickable);
        findViewById(R.id.imageButton4).setClickable(clickable);

    }
}
