package com.example.ms1.wolfcatcheggs;

import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import static android.content.ContentValues.TAG;

class DeleteEggextends extends AppCompatActivity {

    protected Void doInBackground(final View view1, final Byte wolfPosition, final RelativeLayout relativeLayout) {

        try {
            Log.i(TAG, "Going to sleep");
            wait(1000);
//
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Rect rc1 = new Rect();
        view1.getDrawingRect(rc1);
        Rect rc2 = new Rect();
        ImageView imageView = findViewById(R.id.imageViewWolf);
        imageView.getDrawingRect(rc2);
        if (Rect.intersects(rc1, rc2) && wolfPosition == 1) {
            relativeLayout.removeView(view1);
        }
        return null;
    }

}
