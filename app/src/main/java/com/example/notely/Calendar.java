package com.example.notely;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Switch;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Calendar extends AppCompatActivity {
    String input = "";
    String category = "";
    int toggle = 0;
    private ActionBar toolbar;
    ArrayList<Date> startEventsCreated = new ArrayList<>();
    ArrayList<Date> endEventsCreated = new ArrayList<>();
    CompactCalendarView compactCalendar;
    SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
    Date currentDateSelection;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatEvent = new SimpleDateFormat("M/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_calendar);
        toolbar = getSupportActionBar();
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);

        try {
            Bundle bundle;
            bundle = this.getIntent().getExtras();
            category = bundle.getString("Category");
        } catch (NullPointerException e) {
        }

        compactCalendar = findViewById(R.id.compactcalendar_view); // get the reference of CalendarView
        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                currentDateSelection = dateClicked;
                input = dateFormat.format(dateClicked);
                System.out.println(input);
                updateListView(input, toggle);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                currentDateSelection = firstDayOfNewMonth;
                if (!category.isEmpty()) {
                    toolbar.setTitle(category + ": " + dateFormatMonth.format(firstDayOfNewMonth));
                } else
                    toolbar.setTitle("All Notes: " + dateFormatMonth.format(firstDayOfNewMonth));
                updateListView(dateFormat.format(firstDayOfNewMonth), toggle);
                createEvents(dateFormatEvent.format(firstDayOfNewMonth));
            }
        });

        Date date = new Date();
        currentDateSelection = date;
        String title = dateFormatMonth.format(date);

        if (!category.equals("")) {
            toolbar.setTitle(category + ": " + title);
            bottomNavigationView.setSelectedItemId(R.id.navigation_categories);
        } else {
            toolbar.setTitle("All Notes: " + title);
            bottomNavigationView.setSelectedItemId(R.id.navigation_allnotes);
        }

        createEvents(dateFormatEvent.format(date));
        updateListView(dateFormat.format(currentDateSelection), toggle);


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
                                toolbar.setTitle("All Notes");
                                switchActivity(3);
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
                                switchActivity(4);
                                break;
                        }
                        return true;
                    }
                });

        Switch toggleDate = findViewById(R.id.switch2);
        toggleDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendar.removeAllEvents();
                startEventsCreated.clear();
                endEventsCreated.clear();
                if (toggle == 0) {
                    toggle = 1;
                } else {
                    toggle = 0;
                }
                updateListView(input, toggle);
                createEvents(dateFormatEvent.format(currentDateSelection));
            }
        });


    }

    public void updateListView(String input, int toggle) {
        if (input.isEmpty())
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

        if (toggle == 0)
            if (!category.equals(""))
                cursorNotes = db.rawQuery("SELECT * from Notes WHERE StartDate = ? and Category = ?", new String[]{input, category});
            else
                cursorNotes = db.rawQuery("SELECT * from Notes WHERE StartDate = ?", new String[]{input});
        else if (!category.equals(""))
            cursorNotes = db.rawQuery("SELECT * from Notes WHERE EndDate = ? and Category = ?", new String[]{input, category});
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


    public void createEvents(String MY) {
        String path = "/data/data/" + getPackageName() + "/Notely.db";
        // Open the database. If it doesn't exist, create it.
        SQLiteDatabase db;
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        // Create Cursor to traverse notes
        Cursor cursorNotes;
        String task;
        String SE;
        int color;
        if (toggle == 0) {
            task = " Started";
            color = Color.GREEN;
            SE = "StartDate";
            if (!category.equals(""))
                cursorNotes = db.rawQuery("SELECT * from Notes WHERE StartMonthYear = ? and Category = ?", new String[]{MY, category});
            else
                cursorNotes = db.rawQuery("SELECT * from Notes WHERE StartMonthYear = ?", new String[]{MY});
        } else {
            task = " Due";
            color = Color.RED;
            SE = "EndDate";
            if (!category.equals("")) {
                cursorNotes = db.rawQuery("SELECT * from Notes WHERE EndMonthYear = ? and Category = ?", new String[]{MY, category});
            } else {
                cursorNotes = db.rawQuery("SELECT * from Notes WHERE EndMonthYear = ?", new String[]{MY});
            }
        }

        while (cursorNotes.moveToNext()) {
            System.out.println("In movetonext Loop");
            String title = cursorNotes.getString(cursorNotes.getColumnIndex("Title"));
            String EventDate = cursorNotes.getString(cursorNotes.getColumnIndex(SE));
            Date date;
            try {
                date = dateFormat.parse(EventDate);
                if (SE.equals("EndDate")) {
                    if (endEventsCreated.contains(date))
                        return;
                    endEventsCreated.add(date);
                } else {
                    if (startEventsCreated.contains(date))
                        return;
                    startEventsCreated.add(date);
                }
            } catch (java.text.ParseException E) {
                break;
            }
            Long time = date.getTime();
            Event ev1 = new Event(color, time, title + task);
            compactCalendar.addEvent(ev1);
        }

        // Close the cursor and database
        cursorNotes.close();
        db.close();

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
            case (4):
                intent = new Intent(this, Settings.class);
                break;
        }
        startActivity(intent);
    }

}
