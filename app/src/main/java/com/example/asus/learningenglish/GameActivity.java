package com.example.asus.learningenglish;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.Random;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class GameActivity extends AppCompatActivity {

    private int right;
    private int q_number = 1;
    private TextView q_index;
    private TextView q_question;
    private TextView answer1;
    private TextView answer2;
    private TextView answer3;
    private TextView answer4;
    private TextView game_count;
    private TextView cheat;
    private CountDownTimer count;
    private GifImageView gif_normal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Objects.requireNonNull(getSupportActionBar()).hide();

        q_index = findViewById(R.id.q_index);
        q_index.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));
        q_question = findViewById(R.id.q_question);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        game_count = findViewById(R.id.game_count);
        game_count.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));
        gif_normal = findViewById(R.id.gif_normal);
        cheat = findViewById(R.id.cheat);
        cheat.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));

        count = new CountDownTimer(11000, 1000) {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long millisUntilFinished) {
                game_count.setText(String.format("%2d", millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                loseScreen();
            }
        }.start();
        right = questionCreate();

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (right == 1) {
                    winScreen();
                } else {
                    loseScreen();
                }
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (right == 2) {
                    winScreen();
                } else {
                    loseScreen();
                }
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (right == 3) {
                    winScreen();
                } else {
                    loseScreen();
                }
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (right == 4) {
                    winScreen();
                } else {
                    loseScreen();
                }
            }
        });

        gif_normal.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {       //小雞給提示
                try {
                    GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.chikenshock);
                    gifDrawable.setLoopCount(1);
                    gif_normal.setImageDrawable(gifDrawable);
                    int random = Integer.parseInt(randomNumber(8));
                    switch (random) {
                        case 1:
                            cheat.setText("1");
                            break;
                        case 2:
                            cheat.setText("2");
                            break;
                        case 3:
                            cheat.setText("3");
                            break;
                        case 4:
                            cheat.setText("4");
                            break;
                        default:
                            cheat.setText(String.format("%d", right));
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private int questionCreate() {
        String wrong_index;
        String[] wrong_answer = new String[3];
        String right_answer = null;

        Cursor cursor = MainActivity.db.rawQuery("select * from " + DBOpenHelper.DATABASE_TABLE, null);
        cursor.moveToLast();
        int db_length = cursor.getInt(cursor.getColumnIndex("id"));

        String index = randomNumber(db_length);
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
                wrong_index = randomNumber(db_length);
            } while (wrong_index.equals(index));

            cursor = MainActivity.db.query("myWord", new String[]{"id", "chinese"},
                    "id=?", new String[]{wrong_index}, null, null, null);

            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                wrong_answer[i] = cursor.getString(1);
                Log.v("GameAnswer", id + " " + wrong_answer[i]);
            }
        }

        int place = Integer.parseInt(randomNumber(4));
        switch (place) {
            case 1:
                answer1.setText(String.format("1. %s", right_answer));
                answer2.setText(String.format("2. %s", wrong_answer[0]));
                answer3.setText(String.format("3. %s", wrong_answer[1]));
                answer4.setText(String.format("4. %s", wrong_answer[2]));
                break;
            case 2:
                answer1.setText(String.format("1. %s", wrong_answer[0]));
                answer2.setText(String.format("2. %s", right_answer));
                answer3.setText(String.format("3. %s", wrong_answer[1]));
                answer4.setText(String.format("4. %s", wrong_answer[2]));
                break;
            case 3:
                answer1.setText(String.format("1. %s", wrong_answer[0]));
                answer2.setText(String.format("2. %s", wrong_answer[1]));
                answer3.setText(String.format("3. %s", right_answer));
                answer4.setText(String.format("4. %s", wrong_answer[2]));
                break;
            case 4:
                answer1.setText(String.format("1. %s", wrong_answer[0]));
                answer2.setText(String.format("2. %s", wrong_answer[1]));
                answer3.setText(String.format("3. %s", wrong_answer[2]));
                answer4.setText(String.format("4. %s", right_answer));
                break;
            default:
                Log.v("MY_ERROR_MESSAGE", "Answer's place error!");
                break;
        }
        cursor.close();
        return place;
    }   //產生題目

    private void winScreen() {
        cheat.setText("");
        right = questionCreate();
        q_index.setText(String.format("Q%s.", Integer.toString(++q_number)));
        count.start();
    }   //答對執行的動作

    private void loseScreen() {
        count.cancel();
        checkScore();
        Toast.makeText(this,"答對了" + (q_number-1) + "題",Toast.LENGTH_LONG).show();
        finish();
    }   //答錯執行的動作

    private String randomNumber(int range) {
        Random random = new Random();
        return Integer.toString(random.nextInt(range) + 1);
    }   //獲得字串隨機數

    private void checkScore() {
        Cursor cursor = MainActivity.db.rawQuery("select * from " + DBOpenHelper.DATABASE_TABLE ,null);
        cursor.moveToFirst();
        int score = cursor.getInt(cursor.getColumnIndex("chinese"));
        if(q_number > score) {
            MainActivity.db.execSQL("update " + DBOpenHelper.DATABASE_TABLE + " set chinese='" + String.valueOf(q_number - 1) + "' where id='0'");

        }
        Log.v("BEST SCORE",String.valueOf(score));
        cursor.close();
    }   //更新最佳紀錄

    @Override
    protected void onPause() {
        super.onPause();
        count.cancel();
    }   //若中途離開則關閉計時器
}
