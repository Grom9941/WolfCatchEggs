package com.example.ms1.wolfcatcheggs.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.ms1.wolfcatcheggs.Model.Game;
import com.example.ms1.wolfcatcheggs.Model.ObjectGame;
import com.example.ms1.wolfcatcheggs.R;

public class EndGame extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_activity_screen);

        TextView textEnd = findViewById(R.id.textViewTextEnd);
       // savedInstanceState.getByte("account");

        textEnd.setText("Твой счет: " + String.valueOf(getIntent().getIntExtra("account",5)));

    }

    public void restart(View view) {

        try {

            Intent intent = new Intent(EndGame.this, Game.class);
            startActivity(intent);
            finish();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
