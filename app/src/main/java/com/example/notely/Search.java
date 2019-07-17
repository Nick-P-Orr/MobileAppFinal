package com.example.notely;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

        String countQuery = "SELECT  * FROM " + "notes";
        Cursor cursor = db.rawQuery(countQuery, null);
        int entries = cursor.getCount();
        cursor.close();

        // Create image & place it at /res/drawable
            Bitmap defaultImage;
            defaultImage =
                    BitmapFactory.decodeResource(getResources(), R.drawable.note);
        // Create list entries
        List<ListItem> list = new ArrayList<ListItem>();
        for(int i = 0; i < entries; i++) {
            ListItem item = new ListItem();
            item.image = defaultImage;
            item.title = "David";
            item.date = "Boston is not snowing now.";
            list.add(item);
        }
            // Create ListItemAdapter
            ListItemAdapter adapter;
            adapter = new ListItemAdapter(this, 0, list);
            // Assign ListItemAdapter to ListView
            ListView listView = (ListView) findViewById(R.id.listView1);
            listView.setAdapter(adapter);


            Button search = findViewById(R.id.search);

            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //switchActivity();
                }

            });


        }
    }
