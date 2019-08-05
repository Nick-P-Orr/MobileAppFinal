package com.example.notely;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainInt = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(mainInt);
                finish();
            }
        }, SPLASH_TIME_OUT);


        // Set the path and database name
        String path = "/data/data/" + getPackageName() + "/Notely.db";

        // Open the database. If it doesn't exist, create it.
        SQLiteDatabase db;
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        // Create a table - notes
        String sql = "CREATE TABLE IF NOT EXISTS Style" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, Style INTEGER);";
        db.execSQL(sql);
        Cursor cursor;
        String query = "SELECT * FROM Style";
        cursor = db.rawQuery(query, null);
        // if this note does NOT already exist and is not the first entry
        if (cursor.moveToFirst()) {
            int style = cursor.getInt(cursor.getColumnIndex("Style"));
            Utils.setsTheme(style);
        }

        cursor.close();
        // Close DB
        db.close();


    }
}
