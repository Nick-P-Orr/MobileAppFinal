package com.example.notely;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    @TargetApi(27)
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = getSupportActionBar();
        toolbar.setTitle("Search");

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_search);
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
                                switchActivity(3);
                                break;
                            case R.id.navigation_subjects:
                                //SUBJECTS
                                toolbar.setTitle("Categories");
                                switchActivity(0);
                                break;
                            case R.id.navigation_search:
                                //SEARCH
                                toolbar.setTitle("Search");
                                switchActivity(0);
                                break;
                            case R.id.navigation_setting:
                                //SETTINGS
                                toolbar.setTitle("Settings");
                                switchActivity(1);
                                break;
                        }
                        return true;
                    }
                });


        // Set list view to display 10 last edited noted
        String betterQuery = "SELECT * FROM Notes ORDER BY lastEdit DESC";
        updateListView(betterQuery, true);

        //@TODO remove, button is for testing
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =
                        new Intent(Search.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //@TODO SAVE THIS QUERY FOR SAM
        //String query = "SELECT * FROM Notes";
        // Create query to select notes by most recent edit date
        //String betterQuery = "SELECT * FROM notes ORDER BY title DESC";

        // Create search bar onclick  listener and string to hold input
        final EditText searchBar = findViewById(R.id.searchBar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Get user input and update list
                String userQuery = s.toString();
                updateListView(userQuery, false);
            }
        });

        //@TODO GIVE CODE TO SAM FOR CATEGORIES TAB
/*        // Get distinct categories
        String colQuery = "SELECT DISTINCT(category) FROM notes";
        Cursor cursorCnotes = db.rawQuery(colQuery,null);
        while (cursorCnotes.moveToNext()){
            System.out.println(cursorCnotes.getString(cursorCnotes.getColumnIndex("category")));
        }
        // Close the cursor and database
        cursorCnotes.close();
        db.close();*/
    }

    public void updateListView(String query, Boolean DEFAULT) {
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

        // default case populates the list view with the last 10 edited items
        if (DEFAULT) {
            cursorNotes = db.rawQuery(query, null);
        }
        // user search case returns ALL notes with matching title
        else {
            cursorNotes = db.rawQuery("SELECT * from Notes WHERE Title = ?", new String[]{query});
        }

        int noteCount = 0; // Track number of notes
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
            noteCount++;
            if (DEFAULT && noteCount >= 9) // If populating listview in default mode, limit to 10 notes
                break;
        }
        // Close the cursor and database
        cursorNotes.close();
        db.close();
        // Create ListItemAdapter
        ListItemAdapter adapter = new ListItemAdapter(this, 0, list);
        // Assign ListItemAdapter to ListView
        final ListView listView = findViewById(R.id.noteListView);
        listView.setAdapter(adapter);

        // Make list view items clickable and open note in mainActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListItem item = (ListItem) parent.getItemAtPosition(position);
                Intent intent =
                        new Intent(Search.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("NoteID", item.getNoteID());
                System.out.println(item.getNoteID());
                bundle.putString("Title", item.getTitle());
                bundle.putString("Category", item.getCategory());
                bundle.putString("StartDate", item.getStartDate());
                bundle.putString("EndDate", item.getEndDate());
                bundle.putString("FilePath", item.getFilePath());
                bundle.putString("LastEdit", item.getLastEdit());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void switchActivity(int activity) {
        Intent intent = new Intent(this, MainActivity.class);
        switch (activity) {
            case (0):
                intent = new Intent(this, Search.class);
                break;
            case (1):
                intent = new Intent(this, Settings.class);
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


