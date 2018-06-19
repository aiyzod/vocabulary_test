package com.example.asus.learningenglish;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StudyActivity extends AppCompatActivity {

    private TextView q_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        getSupportActionBar().hide();

        q_search = this.findViewById(R.id.q_search);
        q_search.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));
    }
}
