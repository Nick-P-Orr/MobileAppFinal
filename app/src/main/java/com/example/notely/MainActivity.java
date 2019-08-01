package com.example.notely;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private static final String TAG = "MyActivity";

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
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);

        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        final String date = df.format(Calendar.getInstance().getTime());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);

        Button save_btn = (Button)findViewById(R.id.save_note);
        Button change_font = (Button)findViewById(R.id.change_font);

        ((TextView)findViewById(R.id.current_date)).setText(date);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



        change_font.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("myApp", "Activity1 button is clicked");
                Intent intent2 = new Intent();
                intent2.setClass(MainActivity.this, ChangeFont.class);

                Bundle bundle = new Bundle();
                intent2.putExtras(bundle);

                Log.v("myApp", "Switching to font changer");
                startActivity(intent2);
                Log.v("myApp", "Start Activity hit");


            }
        });



        save_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent();

                String note_field = ((EditText)findViewById(R.id.note_field)).getText().toString();
                String category_field = ((EditText)findViewById(R.id.category_field)).getText().toString();
                String date_field = ((EditText)findViewById(R.id.current_date)).getText().toString();
                String title_field = ((EditText)findViewById(R.id.title_field)).getText().toString();
                String completion_date = ((EditText)findViewById(R.id.completion_field)).getText().toString();




                intent.setClass(MainActivity.this, MainActivity.class);

                Log.v("myApp", "Save Note button is clicked");


            }

        });

    }

}
