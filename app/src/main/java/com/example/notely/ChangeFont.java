package com.example.notely;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;


public class ChangeFont extends AppCompatActivity implements OnClickListener{

    @TargetApi(27)
    private ActionBar toolbar;
    private TextView mTextMessage;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.onActivityCreateSetTheme(this);

        setContentView(R.layout.font_change);

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


        Log.v("myApp", "In font change");

        findViewById(R.id.roboto_small).setOnClickListener(this);
        findViewById(R.id.roboto_large).setOnClickListener(this);
        findViewById(R.id.roboto_btn).setOnClickListener(this);

        findViewById(R.id.sans_sarif_btn_small).setOnClickListener(this);
        findViewById(R.id.sans_sarif_btn_default).setOnClickListener(this);
        findViewById(R.id.sans_sarif_btn_large).setOnClickListener(this);

        findViewById(R.id.default_small).setOnClickListener(this);
        findViewById(R.id.default1).setOnClickListener(this);
        findViewById(R.id.default_large).setOnClickListener(this);
        Button goback = (Button)findViewById(R.id.goto_main);

        /*


        Button robotoSmall = (Button)findViewById(R.id.roboto_small);
        Button robotoDefault = (Button)findViewById(R.id.roboto_btn);
        Button robotoLarge = (Button)findViewById(R.id.roboto_large);
        Button sanssarifSmall = (Button)findViewById(R.id.sans_sarif_btn_small);
        Button sanssarifDefault = (Button)findViewById(R.id.sans_sarif_btn_default);
        Button sanssarifLarge = (Button)findViewById(R.id.sans_sarif_btn_large);

        robotoSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("myApp", "Activity2 button is clicked");
                Intent intent = new Intent();
                intent.setClass(ChangeFont.this, ChangeFont.class);
                setTheme(R.style.SmallRoboto);

                startActivity(intent);


            }
            */

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("myApp", "Activity2 button is clicked");
                Intent intent1 = new Intent();

                intent1.setClass(ChangeFont.this, MainActivity.class);

                Bundle bundle = new Bundle();

                intent1.putExtras(bundle);
                startActivity(intent1);

            }
        });

        Bundle bundle = this.getIntent().getExtras();


    }

    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        switch (v.getId())
        {
            case R.id.roboto_small:
                Utils.changeToTheme(this, Utils.THEME_RS);
                break;
            case R.id.roboto_btn:
                Utils.changeToTheme(this, Utils.THEME_RD);
                break;
            case R.id.roboto_large:
                Utils.changeToTheme(this, Utils.THEME_RL);
                break;
            case R.id.sans_sarif_btn_small:
                Utils.changeToTheme(this, Utils.THEME_SSS);
                break;
            case R.id.sans_sarif_btn_default:
                Utils.changeToTheme(this, Utils.THEME_SSD);
                break;
            case R.id.sans_sarif_btn_large:
                Utils.changeToTheme(this, Utils.THEME_SSL);
                break;
            case R.id.default_small:
                Utils.changeToTheme(this, Utils.THEME_RS);
                break;
            case R.id.default1:
                Utils.changeToTheme(this, Utils.THEME_RD);
                break;
            case R.id.default_large:
                Utils.changeToTheme(this, Utils.THEME_RL);
                break;
        }
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
