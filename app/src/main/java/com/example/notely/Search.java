package com.example.notely;

import android.annotation.TargetApi;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class Search extends AppCompatActivity {

    @TargetApi(27)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Set the path and database name
        String path = "/data/data/" + getPackageName() + "/sample.db";
        // Open the database. If it doesn't exist, create it.
        SQLiteDatabase db;
        db = SQLiteDatabase.openOrCreateDatabase(path, null);

        Button search = findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //switchActivity();
            }

        });


    }
}
