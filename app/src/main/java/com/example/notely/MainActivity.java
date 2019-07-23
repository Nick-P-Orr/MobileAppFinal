package com.example.notely;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    /**
    //example array of strings for notes
    String[] notesList = {"Groceries", "To-Do", "School Supplies", "Utilities Bill", "Reminders", "Birthdays", "Homework",
    "Lab Projects", "Job Applications", "Wishlist", "Random Note"};
    **/
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_notes);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_takeNote);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_subjects);
                    return true;
                case R.id.navigation_settings:
                    mTextMessage.setText(R.string.title_settings);
                    return true;
                case R.id.navigation_search:
                    mTextMessage.setText(R.string.title_search);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //go to Settings button
        Button settings_btn = (Button) findViewById(R.id.goto_settings_btn);

        settings_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Log.v("myApp", "Activity1 button is clicked");
                Intent intent = new Intent();
                intent.setClass( MainActivity.this, Settings.class);

                Bundle bundle = new Bundle();
                bundle.putString("Page","Setitings");
                bundle.putInt("bool",1);

                intent.putExtras(bundle);
                startActivity(intent);
            }

        });

        /**

        //array adapter for listing notes
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.note_list_items,notesList);

        ListView listview = (ListView)findViewById(R.id.allnotes_list);
        listview.setAdapter(adapter);

         **/



    }

}
