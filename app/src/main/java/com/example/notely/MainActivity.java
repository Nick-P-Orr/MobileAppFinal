package com.example.notely;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@TargetApi(27)

public class MainActivity extends AppCompatActivity {

    EditText mEditText;

    private TextView mTextMessage;

    private TextView titleEditText;

    private TextView dateText;

    private EditText endDate;

    DatePickerDialog picker;

    Date date = Calendar.getInstance().getTime();

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM_dd_yyyy");

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

        mEditText = findViewById(R.id.noteText);

        titleEditText = findViewById(R.id.title_text);

        dateText = findViewById(R.id.dateView);

        endDate = findViewById(R.id.compDate);

        String formattedDate = dateFormat.format(date);

        dateText.setText(formattedDate);

        // Set the path and database name
        String path = "/data/data/" + getPackageName() + "/sample.db";

        // Open the database. If it doesn't exist, create it.
        SQLiteDatabase db;
        db = SQLiteDatabase.openOrCreateDatabase(path, null);

        // Create a table - notes
        String sql = "CREATE TABLE IF NOT EXISTS notes" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, start_date DATE, end_date DATE, file_path TEXT, category TEXT );";
        db.execSQL(sql);

        db.close();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Button search = findViewById(R.id.button2);

        Button save = findViewById(R.id.save_button);

        endDate.setShowSoftInputOnFocus(false);

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                endDate.setText((monthOfYear + 1) + "/" +  dayOfMonth + "/" + year);

                            }
                        }, year, month, day);
                picker.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(v);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivity();
            }

        });

    }

    public void switchActivity() {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    public void save(View v) {
        String FILE_NAME = titleEditText.getText().toString();
        String title = FILE_NAME;
        if(FILE_NAME.isEmpty()){
            titleEditText.setError("Title cannot be empty");
            return;
        }

        String end_Date = endDate.getText().toString();
        if(end_Date.isEmpty()){
            endDate.setError("End date cannot be empty");
            return;
        }

        String formattedDate = dateFormat.format(date);

        FILE_NAME = FILE_NAME + "_" + formattedDate;

        String text = mEditText.getText().toString();
        FileOutputStream outStream = null;

        try {
            outStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            outStream.write(text.getBytes());

            mEditText.getText().clear();
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String path = "/data/data/" + getPackageName() + "/sample.db";

        SQLiteDatabase db;
        db = SQLiteDatabase.openOrCreateDatabase(path, null);

        // Add Data
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("start_date", formattedDate);
        values.put("end_date", end_Date);
        values.put("file_path", path);
        values.put("category", "Test");
        db.insert("notes", null, values);

        //Close the database


    }

}
