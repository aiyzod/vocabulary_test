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
    private Button search;
    private EditText vocabulary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        Objects.requireNonNull(getSupportActionBar()).hide();

        q_search = this.findViewById(R.id.q_search);
        q_search.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));

        q_chinese = this.findViewById(R.id.q_chinese);
        q_chinese.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));

        search = findViewById(R.id.btn_search);
        search.setOnClickListener(findvocabulary);


        vocabulary = findViewById(R.id.et_vocabulary);
    }
    private View.OnClickListener findvocabulary = new View. OnClickListener() {

        @Override

        public void onClick(View v) {
            String input_str = vocabulary.getText().toString().toLowerCase();

            Cursor cursor = MainActivity.db.rawQuery("select * from " + DBOpenHelper.DATABASE_TABLE + " where english ='" + input_str + "';", null);

            cursor.moveToFirst();

            Cursor mcursor = cursor;

            if( mcursor!=null && mcursor.getCount() > 0) {

                String word_english = cursor.getString(1);
                String word_chinese = cursor.getString(2);
                q_search.setText(word_english);
                q_chinese.setText(word_chinese);

            } else {

                q_chinese.setText("查無此單字");
                q_search.setText("");
            }



        }
    };

}