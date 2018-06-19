package com.example.asus.learningenglish;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();   //用來隱藏工作列

        TextView start_title = this.findViewById(R.id.start_title);
        TextView start_title_tip = this.findViewById(R.id.start_title_tip);
        start_title.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));  //更換字體
        start_title_tip.setTypeface(Typeface.createFromAsset(getAssets(), "setofont.ttf"));

        DBOpenHelper openHelper = new DBOpenHelper(this);    //創建SQLite資料庫
        db = openHelper.getWritableDatabase();

        if (IsTableExist() == 0) {
            getFromAssets(this, "wordlist.txt");     //單字表寫入資料庫(只在資料庫為空時寫入)
        }

        final Cursor cursor = db.rawQuery("select * from " + DBOpenHelper.DATABASE_TABLE, null);     //印出單字表(測試用)
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            Log.v("SQLite", cursor.getString(cursor.getColumnIndex("id")) +
                    "," + cursor.getString(cursor.getColumnIndex("english")) +
                    "," + cursor.getString(cursor.getColumnIndex("chinese")));
            cursor.moveToNext();
        }
        cursor.close();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, MenuActivity.class);
            startActivity(intent);
        }
        return super.onTouchEvent(event);
    }   //點擊螢幕執行的動作

    public void getFromAssets(Context context, String filename) {
        String temp;
        try {
            InputStream in = context.getAssets().open(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            int id = 0;
            while ((temp = br.readLine()) != null) {
                db.execSQL("insert into " + DBOpenHelper.DATABASE_TABLE + " values(\"" + id + "\",\"" +
                        temp.substring(0, temp.indexOf(",")) + "\",\"" + temp.substring(temp.indexOf(",") + 1) + "\");");
                id++;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   //把單字表(wordlist.txt)寫入SQLite

    private int IsTableExist() {
        Cursor c = db.rawQuery("select * from " + DBOpenHelper.DATABASE_TABLE, null);
        int amount = c.getCount();
        c.close();
        return amount;
    }   //判斷SQLite內是否有資料
}
