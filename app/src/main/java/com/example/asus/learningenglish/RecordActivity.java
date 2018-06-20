package com.example.asus.learningenglish;

import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class RecordActivity extends AppCompatActivity {

    TextView record_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Objects.requireNonNull(getSupportActionBar()).hide();

        TextView record_title = findViewById(R.id.record_title);
        record_title.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));
        record_score = findViewById(R.id.record_score);
        record_score.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));
        TextView record_q_index = findViewById(R.id.record_q_index);
        record_q_index.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));
        TextView record_player = findViewById(R.id.record_player);
        record_player.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));
        Button btn_reset = findViewById(R.id.btn_reset);

        Cursor cursor = MainActivity.db.rawQuery("select * from " + DBOpenHelper.DATABASE_TABLE ,null);
        cursor.moveToFirst();
        record_score.setText(cursor.getString(cursor.getColumnIndex("chinese")));
        record_player.setText(String.format("by %s", cursor.getString(cursor.getColumnIndex("english"))));
        cursor.close();

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.db.execSQL("update " + DBOpenHelper.DATABASE_TABLE + " set chinese='0' where id='0'");
                record_score.setText("0");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
