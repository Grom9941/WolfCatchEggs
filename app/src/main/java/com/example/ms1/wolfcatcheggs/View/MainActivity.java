package com.example.ms1.wolfcatcheggs.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ms1.wolfcatcheggs.Model.ChangingVariables;
import com.example.ms1.wolfcatcheggs.Model.Game;
import com.example.ms1.wolfcatcheggs.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity_screen);

    }

    public void clickButton1(View view) {
        new ChangingVariables((byte) 1);

        try {

            Intent intent = new Intent(MainActivity.this, Game.class);
            startActivity(intent);
            finish();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickButton2(View view) {
        new ChangingVariables((byte) 2);

        try {

            Intent intent = new Intent(MainActivity.this, Game.class);
            startActivity(intent);
            finish();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void clickButton3(View view) {
        new ChangingVariables((byte) 3);

        try {

            Intent intent = new Intent(MainActivity.this, Game.class);
            startActivity(intent);
            finish();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
