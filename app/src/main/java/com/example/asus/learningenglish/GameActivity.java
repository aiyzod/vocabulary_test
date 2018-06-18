package com.example.asus.learningenglish;

import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();

        TextView q_index = this.findViewById(R.id.q_index);
        q_index.setTypeface(Typeface.createFromAsset(getAssets(),"setofont.ttf"));
        TextView q_question = this.findViewById(R.id.q_question);
        //q_question.setTypeface(Typeface.createFromAsset(getAssets(),"setofont.ttf"));
        TextView answer1 = this.findViewById(R.id.answer1);
        TextView answer2 = this.findViewById(R.id.answer2);
        TextView answer3 = this.findViewById(R.id.answer3);
        TextView answer4 = this.findViewById(R.id.answer4);
        int right = questionSelect(q_question,answer1,answer2,answer3,answer4);

    }

    private int questionSelect(TextView q_question,TextView answer1,TextView answer2,TextView answer3,TextView answer4) {
        String wrong_index;
        String[] wrong_answer = new String[3];
        String right_answer = null;

        Cursor cursor = MainActivity.db.rawQuery("select * from " + DBOpenHelper.DATABASE_TABLE,null);
        cursor.moveToLast();
        int db_length = cursor.getInt(cursor.getColumnIndex("id"));
        String index = RandomNumber(db_length);
        cursor = MainActivity.db.query("myWord",new String[]{"id","english","chinese"},
                "id=?",new String[]{index},null,null,null);

        while(cursor.moveToNext()) {
            String id = cursor.getString(0);
            String word = cursor.getString(1);
            right_answer = cursor.getString(2);
            q_question.setText(word);
            Log.v("GameQuestion",id + " " + word + " " + right_answer + " " + index);
            if(right_answer != null) {
                break;
            }
        }

        for(int i=0;i<3;i++) {
            do {
                wrong_index = RandomNumber(db_length);
            } while(wrong_index.equals(index));

            cursor = MainActivity.db.query("myWord",new String[]{"id","chinese"},
                    "id=?",new String[]{wrong_index},null,null,null);

            while(cursor.moveToNext()) {
                String id = cursor.getString(0);
                wrong_answer[i] = cursor.getString(1);
                Log.v("GameAnswer",id + " " + wrong_answer[i]);
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
                Log.v("MY_ERROR_MESSAGE","Answer's place error!");
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
