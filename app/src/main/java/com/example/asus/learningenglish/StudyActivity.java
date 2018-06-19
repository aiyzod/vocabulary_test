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

        search = findViewById(R.id.btn_search);
        search.setOnClickListener(findvocabulary);


        vocabulary = findViewById(R.id.et_vocabulary);
    }
    private View.OnClickListener findvocabulary = new View. OnClickListener() {

        @Override

        public void onClick(View v) {
            String input_str = vocabulary.getText().toString().toLowerCase();   //一律為小寫

            // TO 曛綺 : 搜尋資料庫裡面沒有的字會閃退，可以換成用迴圈做字串比對，比對整個資料庫裡的單字在判斷要做甚麼動作

            Cursor cursor = MainActivity.db.rawQuery("select * from " + DBOpenHelper.DATABASE_TABLE + " where english ='" + input_str + "';", null);

            cursor.moveToFirst();

            String word_english = cursor.getString(1);
            String word_chinese = cursor.getString(2);
            q_search.setText(word_english);
            q_chinese.setText(word_chinese);

        }
    };

}
