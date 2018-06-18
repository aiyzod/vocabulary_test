package com.example.asus.learningenglish;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private TextView player_name;
    private ImageView btn_game;
    private ImageView btn_study;
    private ImageView btn_record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();

        player_name = findViewById(R.id.player_name);
        player_name.setTypeface(Typeface.createFromAsset(getAssets(),"setofont.ttf"));

        btn_game = findViewById(R.id.btn_game);
        btn_study = findViewById(R.id.btn_study);
        btn_record = findViewById(R.id.btn_record);
        changeActivity(btn_game,0);
        changeActivity(btn_study,1);
        changeActivity(btn_record,2);
    }

    private void changeActivity(ImageView btn_click, final int check_target) {
        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                switch (check_target) {
                    case 0:
                        intent.setClass(MenuActivity.this,GameActivity.class);
                        break;
                    case 1:
                        intent.setClass(MenuActivity.this,StudyActivity.class);
                        break;
                    case 2:
                        intent.setClass(MenuActivity.this,RecordActivity.class);
                        break;
                    default:
                        break;
                }
                startActivity(intent);
            }
        });
    }
}
