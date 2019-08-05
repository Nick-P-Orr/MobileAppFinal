package com.example.notely;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity implements OnClickListener {

    private ActionBar toolbar;
    Boolean themeColor;
    int lastClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_settings);

        toolbar = getSupportActionBar();
        toolbar.setTitle("Settings");

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_setting);
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
                                toolbar.setTitle("All Notes");
                                switchActivity(3);
                                break;
                            case R.id.navigation_categories:
                                //SUBJECTS
                                toolbar.setTitle("Categories");
                                switchActivity(1);
                                break;
                            case R.id.navigation_search:
                                //SEARCH
                                toolbar.setTitle("Search");
                                switchActivity(0);
                                break;
                            case R.id.navigation_setting:
                                break;
                        }
                        return true;
                    }
                });


        Switch mySwitch;
        mySwitch = findViewById(R.id.dark_mode);

        if(Utils.getCurrentColorTheme().equals("Dark"))
            themeColor = true;
        else
            themeColor = false;

        if(themeColor)
            mySwitch.setChecked(true);

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    themeColor = true;
                    setDark();

                } else {
                    themeColor = false;
                    setDark();
                }
            }
        });


        //FONT
        findViewById(R.id.roboto_small).setOnClickListener(this);
        findViewById(R.id.roboto_large).setOnClickListener(this);
        findViewById(R.id.roboto_btn).setOnClickListener(this);

        findViewById(R.id.sans_sarif_btn_small).setOnClickListener(this);
        findViewById(R.id.sans_sarif_btn_default).setOnClickListener(this);
        findViewById(R.id.sans_sarif_btn_large).setOnClickListener(this);

        findViewById(R.id.default_small).setOnClickListener(this);
        findViewById(R.id.default1).setOnClickListener(this);
        findViewById(R.id.default_large).setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.roboto_small:
                if (themeColor)
                    Utils.changeToTheme(this, Utils.THEME_RS_D);
                else
                    Utils.changeToTheme(this, Utils.THEME_RS);
                break;
            case R.id.roboto_btn:
                if (themeColor)
                    Utils.changeToTheme(this, Utils.THEME_RD_D);
                else
                    Utils.changeToTheme(this, Utils.THEME_RD);
                break;
            case R.id.roboto_large:
                if (themeColor)
                    Utils.changeToTheme(this, Utils.THEME_RL_D);
                else
                    Utils.changeToTheme(this, Utils.THEME_RL);
                break;
            case R.id.sans_sarif_btn_small:
                if (themeColor)
                    Utils.changeToTheme(this, Utils.THEME_SSS_D);
                else
                    Utils.changeToTheme(this, Utils.THEME_SSS);
                break;
            case R.id.sans_sarif_btn_default:
                if (themeColor)
                    Utils.changeToTheme(this, Utils.THEME_SSD_D);
                else
                    Utils.changeToTheme(this, Utils.THEME_SSD);
                break;
            case R.id.sans_sarif_btn_large:
                if (themeColor)
                    Utils.changeToTheme(this, Utils.THEME_SSL_D);
                else
                    Utils.changeToTheme(this, Utils.THEME_SSL);
                break;
            case R.id.default_small:
                if (themeColor)
                    Utils.changeToTheme(this, Utils.THEME_DS_D);
                else
                    Utils.changeToTheme(this, Utils.THEME_DS);
                break;
            case R.id.default1:
                if (themeColor)
                    Utils.changeToTheme(this, Utils.THEME_DD_D);
                else
                    Utils.changeToTheme(this, Utils.THEME_DD);
                break;
            case R.id.default_large:
                if (themeColor)
                    Utils.changeToTheme(this, Utils.THEME_DL_D);
                else
                    Utils.changeToTheme(this, Utils.THEME_DL);
                break;
        }
    }


    void setDark(){
        lastClick = Utils.getCurrentTextTheme();
        switch (lastClick) {
            case 0:
                if (themeColor)
                    Utils.changeToTheme(this, Utils.THEME_RS_D);
                else
                    Utils.changeToTheme(this, Utils.THEME_RS);
                break;
            case 1:
                if (themeColor)
                    Utils.changeToTheme(this, Utils.THEME_RD_D);
                else
                    Utils.changeToTheme(this, Utils.THEME_RD);
                break;
            case 2:
                if (themeColor)
                    Utils.changeToTheme(this, Utils.THEME_RL_D);
                else
                    Utils.changeToTheme(this, Utils.THEME_RL);
                break;
            case 3:
                if (themeColor)
                    Utils.changeToTheme(this, Utils.THEME_SSS_D);
                else
                    Utils.changeToTheme(this, Utils.THEME_SSS);
                break;
            case 4:
                if (themeColor)
                    Utils.changeToTheme(this, Utils.THEME_SSD_D);
                else
                    Utils.changeToTheme(this, Utils.THEME_SSD);
                break;
            case 5:
                if (themeColor)
                    Utils.changeToTheme(this, Utils.THEME_SSL_D);
                else
                    Utils.changeToTheme(this, Utils.THEME_SSL);
                break;
            case 6:
                if (themeColor)
                    Utils.changeToTheme(this, Utils.THEME_DS_D);
                else
                    Utils.changeToTheme(this, Utils.THEME_DS);
                break;
            case 7:
                if (themeColor)
                    Utils.changeToTheme(this, Utils.THEME_DD_D);
                else
                    Utils.changeToTheme(this, Utils.THEME_DD);
                break;
            case 8:
                if (themeColor)
                    Utils.changeToTheme(this, Utils.THEME_DL_D);
                else
                    Utils.changeToTheme(this, Utils.THEME_DL);
                break;
        }
    }
}
