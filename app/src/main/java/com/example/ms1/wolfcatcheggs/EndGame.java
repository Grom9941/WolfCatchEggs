package com.example.ms1.wolfcatcheggs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class EndGame extends AppCompatActivity {
    Byte forCheck2 = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_activity_screen);

        TextView textEnd = findViewById(R.id.textViewTextEnd);
        textEnd.setText("Твой счет: " + String.valueOf(Game.account));

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
