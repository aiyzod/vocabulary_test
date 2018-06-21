package com.example.asus.learningenglish;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class StudyActivity extends AppCompatActivity {

    private TextView q_search;
    private TextView q_chinese;
    private EditText vocabulary;
    private Button word_back;
    private Button word_next;
    private Button word_new;
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
        word_new = findViewById(R.id.word_new);
        word_new.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));

        vocabularyViewer();
        vocabularyEditor();

    }

    private void vocabularyEditor() {
        word_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this,R.layout.study_newword,null);
        final EditText get_english = view.findViewById(R.id.get_english);
        final EditText get_chinese = view.findViewById(R.id.get_chinese);
        get_english.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));
        get_chinese.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));

        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String new_english = get_english.getText().toString().toLowerCase();
                String new_chinese = get_chinese.getText().toString();

                Cursor cursor = MainActivity.db.rawQuery("select * from " + DBOpenHelper.DATABASE_TABLE + " where english ='" + new_english + "';", null);
                cursor.moveToFirst();

                if (cursor.getCount() > 0) {
                    MainActivity.db.execSQL("update " + DBOpenHelper.DATABASE_TABLE + " set chinese='" + new_chinese + "' where english='" + new_english + "'");
                } else {
                    Cursor cursor_insert = MainActivity.db.rawQuery("select * from " + DBOpenHelper.DATABASE_TABLE,null);
                    cursor_insert.moveToLast();
                    int id = cursor_insert.getInt(cursor.getColumnIndex("id")) + 1;
                    MainActivity.db.execSQL("insert into " + DBOpenHelper.DATABASE_TABLE + " values('" + Integer.toString(id) + "','" + new_english + "','" + new_chinese + "');");
                    cursor_insert.close();
                }

                cursor_control = MainActivity.db.rawQuery("select * from " + DBOpenHelper.DATABASE_TABLE,null);
                cursor_control.moveToFirst();

                cursor = MainActivity.db.rawQuery("select * from " + DBOpenHelper.DATABASE_TABLE + " where english ='" + new_english + "';", null);
                cursor.moveToFirst();
                q_search.setText(cursor.getString(1));
                q_chinese.setText(cursor.getString(2));

                cursor.close();
            }
        }).show();
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
                checkCursor(0);
                cursor_control.moveToPrevious();
                q_chinese.setText(cursor_control.getString(cursor_control.getColumnIndex("chinese")));
                q_search.setText(cursor_control.getString(cursor_control.getColumnIndex("english")));
            }
        });

        word_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCursor(1);
                cursor_control.moveToNext();
                q_chinese.setText(cursor_control.getString(cursor_control.getColumnIndex("chinese")));
                q_search.setText(cursor_control.getString(cursor_control.getColumnIndex("english")));
            }
        });
    }   //單字檢視器

    private void checkCursor(int check) {
        Cursor cursor = MainActivity.db.rawQuery("select * from " + DBOpenHelper.DATABASE_TABLE, null);
        cursor.moveToLast();
        if(cursor_control.getString(cursor_control.getColumnIndex("id")).equals("0") ) {
            cursor_control.moveToNext();
            cursor_control.moveToNext();
        } else if (cursor_control.getString(cursor_control.getColumnIndex("id")).equals(cursor.getString(cursor.getColumnIndex("id")))) {
            cursor_control.moveToPrevious();
        } else if (cursor_control.getString(cursor_control.getColumnIndex("id")).equals("1") && check == 0) {
            cursor_control.moveToNext();
        }
        cursor.close();
    }   //檢查curcor是否可以移動

    private View.OnClickListener findVocabulary = new View.OnClickListener() {

        @Override

        public void onClick(View v) {
            String input_str = vocabulary.getText().toString().toLowerCase();

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
    }
}