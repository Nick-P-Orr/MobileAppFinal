package com.example.notely;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Switch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Calendar extends AppCompatActivity {
    String input = "";
    int toggle = 0;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        toolbar = getSupportActionBar();
        toolbar.setTitle("Calendar");

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_allnotes);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                toolbar.setTitle("Notes");
                                switchActivity(2);
                                break;
                            case R.id.navigation_allnotes:
                                //ALL NOTES
                                break;
                            case R.id.navigation_categories:
                                toolbar.setTitle("Categories");
                                switchActivity(1);
                                break;
                            case R.id.navigation_search:
                                //SEARCH
                                toolbar.setTitle("Search");
                                switchActivity(0);
                                break;
                            case R.id.navigation_setting:
                                toolbar.setTitle("Settings");
                                switchActivity(1);
                                break;
                        }
                        return true;
                    }
                });

        Switch toggleDate = findViewById(R.id.switch2);
        toggleDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggle == 0)
                    toggle = 1;
                else
                    toggle = 0;
                updateListView(input, toggle);
            }
        });

        CalendarView myCalendar = findViewById(R.id.calendarView2); // get the reference of CalendarView
        myCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                String slash = "/";
                input = (month+1) + slash + dayOfMonth + slash + year;
                System.out.println(input);
                updateListView(input, toggle);
            }
        });
    }

    public void updateListView(String input, int toggle) {
        if(input.isEmpty())
            return;
        // Set the path and database name
        String path = "/data/data/" + getPackageName() + "/Notely.db";
        // Open the database. If it doesn't exist, create it.
        SQLiteDatabase db;
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        // Create image & place it at /res/drawable
        Bitmap defaultImage;
        defaultImage =
                BitmapFactory.decodeResource(getResources(), R.drawable.note);
        // Create list entries
        List<ListItem> list = new ArrayList<ListItem>();
        // Create Cursor to traverse notes
        Cursor cursorNotes;

        if(toggle == 0)
            cursorNotes = db.rawQuery("SELECT * from Notes WHERE StartDate = ?", new String[]{input});
        else
            cursorNotes = db.rawQuery("SELECT * from Notes WHERE EndDate = ?", new String[]{input});

        // Create items of notes and add them to list
        while (cursorNotes.moveToNext()) {
            ListItem item = new ListItem();
            item.Image = defaultImage;
            item.NoteID = cursorNotes.getString(cursorNotes.getColumnIndex("NoteID"));
            item.Title = cursorNotes.getString(cursorNotes.getColumnIndex("Title"));
            item.Category = cursorNotes.getString(cursorNotes.getColumnIndex("Category"));
            item.StartDate = cursorNotes.getString(cursorNotes.getColumnIndex("StartDate"));
            item.EndDate = cursorNotes.getString(cursorNotes.getColumnIndex("EndDate"));
            item.FilePath = cursorNotes.getString(cursorNotes.getColumnIndex("FilePath"));
            item.LastEdit = cursorNotes.getString(cursorNotes.getColumnIndex("LastEdit"));
            list.add(item);
        }
        // Close the cursor and database
        cursorNotes.close();
        db.close();
        // Create ListItemAdapter
        ListItemAdapter adapter = new ListItemAdapter(list, this);
        // Assign ListItemAdapter to ListView
        final ListView listView = findViewById(R.id.noteListView);
        listView.setAdapter(adapter);
    }

    public void switchActivity(int activity) {
        Intent intent = new Intent(this, MainActivity.class);
        switch (activity) {
            case (0):
                intent = new Intent(this, Search.class);
                break;
            case (1):
                intent = new Intent(this, Categories.class);
                break;
            case (2):
                intent = new Intent(this, MainActivity.class);
                break;
            case (3):
                intent = new Intent(this, AllNotes.class);
                break;
        }
        startActivity(intent);
    }

}
