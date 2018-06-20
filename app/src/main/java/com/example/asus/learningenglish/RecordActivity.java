package com.example.asus.learningenglish;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

public class RecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Objects.requireNonNull(getSupportActionBar()).hide();

        TextView record_title = findViewById(R.id.record_title);
        record_title.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));
        TextView record_score = findViewById(R.id.record_score);
        record_score.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));
        TextView record_q_index = findViewById(R.id.record_q_index);
        record_q_index.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));
        TextView record_player = findViewById(R.id.record_player);
        record_player.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));
    }
}
