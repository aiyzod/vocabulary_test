package com.example.asus.learningenglish;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView start_title;
    private TextView start_title_tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        start_title = this.findViewById(R.id.start_title);
        start_title_tip = this.findViewById(R.id.start_title_tip);
        start_title.setTypeface(Typeface.createFromAsset(getAssets(),"setofont.ttf"));
        start_title_tip.setTypeface(Typeface.createFromAsset(getAssets(),"setofont.ttf"));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }
}
