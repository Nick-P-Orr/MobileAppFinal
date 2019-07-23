package com.example.notely;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AllNotes extends AppCompatActivity {

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_notes);



        Bundle bundle_allnotes = this.getIntent().getExtras();
        String OpenedPage = bundle_allnotes.getString("page");
        int bool = bundle_allnotes.getInt("bool");

        Log.v("myApp", "name = " + OpenedPage + "; boolean = " + bool);


        //needs on click listener to be able to click each note
        lv = findViewById(R.id.allnotes_list);
        String[] arr = {"Groceries", "To-Do", "School Supplies", "Utilities Bill", "Reminders", "Birthdays", "Homework",
                "Lab Projects", "Job Applications", "Wish List", "Random Note", "More Notes", "Extra Notes", "Old Notes",
        "Completed Tasks", "Future Assignments", "Last Note"};

        ArrayAdapter adapter = new ArrayAdapter(AllNotes.this,android.R.layout.simple_list_item_1,arr);
        lv.setAdapter(adapter);



    }

}
