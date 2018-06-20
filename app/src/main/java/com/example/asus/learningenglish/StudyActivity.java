package com.example.asus.learningenglish;

import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class StudyActivity extends AppCompatActivity {

    private TextView q_search;
    private TextView q_chinese;
    private EditText vocabulary;
    private Button word_back;
    private Button word_next;
    private Cursor cursor_control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        Objects.requireNonNull(getSupportActionBar()).hide();

        q_search = this.findViewById(R.id.q_search);
        q_search.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));

        q_chinese = this.findViewById(R.id.q_chinese);
        q_chinese.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));

        Button search = findViewById(R.id.btn_search);
        search.setOnClickListener(findVocabulary);
        search.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));

        vocabulary = findViewById(R.id.et_vocabulary);

        word_back = findViewById(R.id.word_back);
        word_next = findViewById(R.id.word_next);

        vocabularyViewer();

    }

    private void vocabularyViewer() {
        cursor_control = MainActivity.db.rawQuery("select * from " + DBOpenHelper.DATABASE_TABLE,null);
        cursor_control.moveToFirst();
        cursor_control.moveToNext();
        q_chinese.setText(cursor_control.getString(cursor_control.getColumnIndex("chinese")));
        q_search.setText(cursor_control.getString(cursor_control.getColumnIndex("english")));

        word_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor_control.moveToPrevious();
                checkCursor();
                q_chinese.setText(cursor_control.getString(cursor_control.getColumnIndex("chinese")));
                q_search.setText(cursor_control.getString(cursor_control.getColumnIndex("english")));
            }
        });

        word_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCursor();
                cursor_control.moveToNext();
                q_chinese.setText(cursor_control.getString(cursor_control.getColumnIndex("chinese")));
                q_search.setText(cursor_control.getString(cursor_control.getColumnIndex("english")));
            }
        });
    }   //單字檢視器

    private void checkCursor() {
        Cursor cursor = MainActivity.db.rawQuery("select * from " + DBOpenHelper.DATABASE_TABLE, null);
        cursor.moveToLast();

        if(cursor_control.getString(cursor_control.getColumnIndex("id")).equals("0")) {
            cursor_control.moveToNext();
        } else if (cursor_control.getString(cursor_control.getColumnIndex("id")).equals(cursor.getString(cursor.getColumnIndex("id")))) {
            cursor_control.moveToPrevious();
        }
        cursor.close();
    }   //檢查curcor是否可以移動

    private View.OnClickListener findVocabulary = new View.OnClickListener() {

        @Override

        public void onClick(View v) {
            String input_str = vocabulary.getText().toString().toLowerCase().replaceAll(" ", "");

            Cursor cursor = MainActivity.db.rawQuery("select * from " + DBOpenHelper.DATABASE_TABLE + " where english ='" + input_str + "';", null);

            cursor.moveToFirst();

            if (cursor.getCount() > 0) {
                String word_english = cursor.getString(1);
                String word_chinese = cursor.getString(2);
                q_chinese.setText(word_chinese);
                q_search.setText(word_english);
            } else {
                q_chinese.setText("查無此單字");
                q_search.setText("");
            }

            cursor.close();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        cursor_control.close();
    }
}