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
        questionSelect(q_question,answer1,answer2,answer3,answer4);

    }

    private void questionSelect(TextView q_question,TextView answer1,TextView answer2,TextView answer3,TextView answer4) {
        String[] wrong_answers = new String[3];

        Cursor cursor = MainActivity.db.rawQuery("select * from " + DBOpenHelper.DATABASE_TABLE,null);
        cursor.moveToLast();
        int db_length = cursor.getInt(cursor.getColumnIndex("id"));
        String index = RandomNumber(db_length);
        cursor = MainActivity.db.query("myWord",new String[]{"id","english","chinese"},
                "id=?",new String[]{index},null,null,null);

        while(cursor.moveToNext()) {
            String id = cursor.getString(0);
            String word = cursor.getString(1);
            String right_answer = cursor.getString(2);
            q_question.setText(word);
            Log.v("GameQuestion",id + " " + word + " " + right_answer + " " + index);
        }

        for(int i=0;i<3;i++) {
            do {
                wrong_answers[i] = RandomNumber(db_length);
            } while(wrong_answers[i].equals(index));

            cursor = MainActivity.db.query("myWord",new String[]{"id","chinese"},
                    "id=?",new String[]{wrong_answers[i]},null,null,null);

            while(cursor.moveToNext()) {
                String id = cursor.getString(0);
                String wrong_answer = cursor.getString(1);
                Log.v("GameAnswer",id + " " + wrong_answer);
            }
        }

        int place = Integer.parseInt(RandomNumber(4));
        switch (place) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                Log.v("MY_ERROR_MESSAGE","Answer's place error!");
                break;
        }

        cursor.close();
    }

    private String RandomNumber(int range) {
        Random random = new Random();
        String index = Integer.toString(random.nextInt(range));
        return index;
    }
}
