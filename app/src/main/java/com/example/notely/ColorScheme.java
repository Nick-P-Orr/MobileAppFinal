package com.example.notely;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.lang.reflect.Field;

public class ColorScheme extends AppCompatActivity{

    @TargetApi(27)
    private ActionBar toolbar;
    private Switch mySwitch;
   // SharedPreferences sharedPreferences, app_preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
        }
        else setTheme(R.style.LightTheme);

        super.onCreate(savedInstanceState);

      //  app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
       // Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.dark_mode);

        toolbar = getSupportActionBar();
        toolbar.setTitle("Theme");

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_allnotes);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                toolbar.setTitle("Notely");
                                switchActivity(2);
                                break;
                            case R.id.navigation_allnotes:
                                //ALL NOTES
                                break;
                            case R.id.navigation_categories:
                                //SUBJECTS
                                toolbar.setTitle("Categories");
                                switchActivity(3);
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

        mySwitch=(Switch)findViewById(R.id.theme_switch);
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            mySwitch.setChecked(true);
        }
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    restartApp();
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    restartApp();

                }
            }
        });

    }

    public void restartApp() {
        Intent i = new Intent(getApplicationContext(), ColorScheme.this.getClass());
        startActivity(i);
    }


    public void switchActivity(int activity) {
        Intent intent = new Intent(this, MainActivity.class);
        switch (activity) {
            case (0):
                intent = new Intent(this, Categories.class);
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


