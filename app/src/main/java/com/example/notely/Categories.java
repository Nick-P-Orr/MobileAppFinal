package com.example.notely;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Categories extends AppCompatActivity {

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);

        setContentView(R.layout.activity_categories);
        toolbar = getSupportActionBar();
        toolbar.setTitle("Categories");

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_categories);
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
                                toolbar.setTitle("All Notes");
                                switchActivity(3);
                                break;
                            case R.id.navigation_categories:
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

    // Get distinct categories
        String colQuery = "SELECT DISTINCT(category) FROM notes";
        updateListView(colQuery );

    }

    public void updateListView(String query) {
        // Set the path and database name
        String path = "/data/data/" + getPackageName() + "/Notely.db";
        // Open the database. If it doesn't exist, create it.
        SQLiteDatabase db;
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        // Create image & place it at /res/drawable
        Bitmap defaultImage;
        defaultImage =
                BitmapFactory.decodeResource(getResources(), R.drawable.category);
        // Create list entries
        List<CategoryListItem> list = new ArrayList<CategoryListItem>();
        // Create Cursor to traverse notes
        Cursor cursorNotes;

        // default case populates the list view with the last 10 edited items
        cursorNotes = db.rawQuery(query, null);

        // Create items of notes and add them to list
        while (cursorNotes.moveToNext()) {
            CategoryListItem item = new CategoryListItem();
            item.Image = defaultImage;
            item.Category = cursorNotes.getString(cursorNotes.getColumnIndex("Category"));
            list.add(item);
        }
        // Close the cursor and database
        cursorNotes.close();
        db.close();
        // Create ListItemAdapter
        CategoryListAdapter adapter = new CategoryListAdapter(this, 0, list);
        // Assign ListItemAdapter to ListView
        final ListView listView = findViewById(R.id.noteListView);
        listView.setAdapter(adapter);

        // Make list view items clickable and open note in mainActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               CategoryListItem item = (CategoryListItem) parent.getItemAtPosition(position);
                Intent intent =
                        new Intent(Categories.this, IndividualCategory.class);
                Bundle bundle = new Bundle();
                bundle.putString("Category", item.getCategory());
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
