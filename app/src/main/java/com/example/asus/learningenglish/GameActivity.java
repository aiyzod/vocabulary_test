package com.example.asus.learningenglish;

import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private int right;
    private int q_number = 1;
    private TextView q_index;
    private TextView q_question;
    private TextView answer1;
    private TextView answer2;
    private TextView answer3;
    private TextView answer4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();

        q_index = this.findViewById(R.id.q_index);
        q_index.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));
        q_question = this.findViewById(R.id.q_question);
        answer1 = this.findViewById(R.id.answer1);
        answer2 = this.findViewById(R.id.answer2);
        answer3 = this.findViewById(R.id.answer3);
        answer4 = this.findViewById(R.id.answer4);


        right = questionSelect(q_question, answer1, answer2, answer3, answer4);

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (right == 0) {
                    Toast.makeText(GameActivity.this, "correct", Toast.LENGTH_SHORT).show();
                    right = questionSelect(q_question, answer1, answer2, answer3, answer4);
                    q_index.setText("Q" + Integer.toString(++q_number) + ".");
                } else {
                    Toast.makeText(GameActivity.this, "you lose", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (right == 1) {
                    Toast.makeText(GameActivity.this, "correct", Toast.LENGTH_SHORT).show();
                    right = questionSelect(q_question, answer1, answer2, answer3, answer4);
                    q_index.setText("Q" + Integer.toString(++q_number) + ".");
                } else {
                    Toast.makeText(GameActivity.this, "you lose", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (right == 2) {
                    Toast.makeText(GameActivity.this, "correct", Toast.LENGTH_SHORT).show();
                    right = questionSelect(q_question, answer1, answer2, answer3, answer4);
                    q_index.setText("Q" + Integer.toString(++q_number) + ".");
                } else {
                    Toast.makeText(GameActivity.this, "you lose", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (right == 3) {
                    Toast.makeText(GameActivity.this, "correct", Toast.LENGTH_SHORT).show();
                    right = questionSelect(q_question, answer1, answer2, answer3, answer4);
                    q_index.setText("Q" + Integer.toString(++q_number) + ".");
                } else {
                    Toast.makeText(GameActivity.this, "you lose", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


    }

    private int questionSelect(TextView q_question, TextView answer1, TextView answer2, TextView answer3, TextView answer4) {
        String wrong_index;
        String[] wrong_answer = new String[3];
        String right_answer = null;

        Cursor cursor = MainActivity.db.rawQuery("select * from " + DBOpenHelper.DATABASE_TABLE, null);
        cursor.moveToLast();
        int db_length = cursor.getInt(cursor.getColumnIndex("id"));
        String index = RandomNumber(db_length);
        cursor = MainActivity.db.query("myWord", new String[]{"id", "english", "chinese"},
                "id=?", new String[]{index}, null, null, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String word = cursor.getString(1);
            right_answer = cursor.getString(2);
            q_question.setText(word);
            Log.v("GameQuestion", id + " " + word + " " + right_answer + " " + index);
            if (right_answer != null) {
                break;
            }
        }

        for (int i = 0; i < 3; i++) {
            do {
                wrong_index = RandomNumber(db_length);
            } while (wrong_index.equals(index));

            cursor = MainActivity.db.query("myWord", new String[]{"id", "chinese"},
                    "id=?", new String[]{wrong_index}, null, null, null);

            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                wrong_answer[i] = cursor.getString(1);
                Log.v("GameAnswer", id + " " + wrong_answer[i]);
            }
        }

        int place = Integer.parseInt(RandomNumber(4));
        switch (place) {
            case 0:
                answer1.setText(right_answer);
                answer2.setText(wrong_answer[0]);
                answer3.setText(wrong_answer[1]);
                answer4.setText(wrong_answer[2]);
                break;
            case 1:
                answer1.setText(wrong_answer[0]);
                answer2.setText(right_answer);
                answer3.setText(wrong_answer[1]);
                answer4.setText(wrong_answer[2]);
                break;
            case 2:
                answer1.setText(wrong_answer[0]);
                answer2.setText(wrong_answer[1]);
                answer3.setText(right_answer);
                answer4.setText(wrong_answer[2]);
                break;
            case 3:
                answer1.setText(wrong_answer[0]);
                answer2.setText(wrong_answer[1]);
                answer3.setText(wrong_answer[2]);
                answer4.setText(right_answer);
                break;
            default:
                Log.v("MY_ERROR_MESSAGE", "Answer's place error!");
                break;
        }
        cursor.close();
        return place;
    }

    private String RandomNumber(int range) {
        Random random = new Random();
        return Integer.toString(random.nextInt(range));
    }
}
