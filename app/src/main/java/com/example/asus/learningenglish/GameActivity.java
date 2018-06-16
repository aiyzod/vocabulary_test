package com.example.asus.learningenglish;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private TextView q_index;
    private TextView q_question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();

        q_index = this.findViewById(R.id.q_index);
        q_index.setTypeface(Typeface.createFromAsset(getAssets(),"setofont.ttf"));
        q_question = this.findViewById(R.id.q_question);
        q_question.setTypeface(Typeface.createFromAsset(getAssets(),"setofont.ttf"));
    }
}
