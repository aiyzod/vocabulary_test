package com.example.asus.learningenglish;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private TextView player_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();

        player_name = this.findViewById(R.id.player_name);
        player_name.setTypeface(Typeface.createFromAsset(getAssets(),"setofont.ttf"));
    }
}
