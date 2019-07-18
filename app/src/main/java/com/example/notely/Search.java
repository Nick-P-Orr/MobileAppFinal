package com.example.notely;

import android.annotation.TargetApi;
import android.content.Intent;
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

        // Create image & place it at /res/drawable
        Bitmap defaultImage;
        defaultImage =
                BitmapFactory.decodeResource(getResources(), R.drawable.note);
        // Create list entries
        List<ListItem> list = new ArrayList<ListItem>();

        // Create query to select all notes
        String query = "SELECT * FROM notes";

        String betterQuery = "SELECT * FROM notes ORDER BY datetime(lastEdit) DESC Limit 1";

        // Create Cursor to traverse notes
        Cursor cursorNotes = db.rawQuery(betterQuery,null);

        // Limit number of notes
        int count = 0;

        // Create items of the 10 most recent notes and add them to list
        while (cursorNotes.moveToNext() && count < 10){
            ListItem item = new ListItem();
            item.image = defaultImage;
            item.title = cursorNotes.getString(cursorNotes.getColumnIndex("title"));
            item.category =  cursorNotes.getString(cursorNotes.getColumnIndex("category"));
            item.startDate =  cursorNotes.getString(cursorNotes.getColumnIndex("start_date"));
            item.endDate = cursorNotes.getString(cursorNotes.getColumnIndex("end_date"));
            item.path = cursorNotes.getString(cursorNotes.getColumnIndex("file_path"));
            list.add(item);
            count++;
        }

        // Close the cursor and database
        cursorNotes.close();
        db.close();

        // Create ListItemAdapter
        ListItemAdapter adapter = new ListItemAdapter(this, 0, list);

        // Assign ListItemAdapter to ListView
        ListView listView = findViewById(R.id.listView1);
        listView.setAdapter(adapter);


        Button search = findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switchActivity();
            }

        });

    }

    // Switch back to main
    public void switchActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}


