package com.example.notely;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        // load the Note_first fragment by default
        toolbar.setTitle("Notely");


        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.nav_view);

    bottomNavigationView.setOnNavigationItemSelectedListener
            (new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment loadFragment = null;

                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            //NEW NOTES
                            toolbar.setTitle("Notes");
                            loadFragment = new Note_first();
                            break;
                        case R.id.navigation_allnotes:
                            //ALL NOTES
                            toolbar.setTitle("All Notes");
                            loadFragment = new AllNotes_frag();
                            break;
                        case R.id.navigation_subjects:
                            //SUBJECTS
                            toolbar.setTitle("Categories");
                            loadFragment = new Subjects();
                            break;
                        case R.id.navigation_search:
                            //SEARCH
                            toolbar.setTitle("Search");
                            loadFragment = new Search_frag();
                            break;
                        case R.id.navigation_setting:
                            //SETTINGS
                            toolbar.setTitle("Settings");
                            loadFragment = new Setting();
                            break;
                    }
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, loadFragment);
                    transaction.commit();
                    return true;
                }
            });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, Note_first.newInstance());
        transaction.commit();
    }

}
