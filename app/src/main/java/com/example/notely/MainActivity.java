package com.example.notely;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@TargetApi(27)

public class MainActivity extends AppCompatActivity {

    EditText noteEditText;

    private TextView mTextMessage;

    private TextView titleEditText;

    private TextView startDate;

    private TextView endDate;

    DatePickerDialog picker;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

    SimpleDateFormat complexDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

        // Set the path and database name
        String path = "/data/data/" + getPackageName() + "/sample.db";

        // Open the database. If it doesn't exist, create it.
        SQLiteDatabase db;
        db = SQLiteDatabase.openOrCreateDatabase(path, null);

        noteEditText = findViewById(R.id.note_field);

        titleEditText = findViewById(R.id.title_field);

        startDate = findViewById(R.id.start_date);

        endDate = findViewById(R.id.completion_field);

        endDate.setShowSoftInputOnFocus(false);

/*        String formattedDate = simpleDateFormat.format(date);

        dateText.setText(formattedDate);*/

        //db.execSQL("DROP TABLE IF EXISTS notes");


        // Create a table - notes
        String sql = "CREATE TABLE IF NOT EXISTS notes" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, category TEXT, start_date TEXT, end_date TEXT, file_path TEXT, lastEdit TEXT);";
        db.execSQL(sql);

        db.close();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Button search = findViewById(R.id.searchButton);

        Button save = findViewById(R.id.save_note);

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
                                endDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);

                            }
                        }, year, month, day);
                picker.show();
            }
        });

        startDate.setOnClickListener(new View.OnClickListener() {
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
                                endDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);

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
        if (FILE_NAME.isEmpty()) {
            titleEditText.setError("Title cannot be empty");
            return;
        }

        String start_date = startDate.getText().toString();
        if (start_date.isEmpty()) {
            endDate.setError("End date cannot be empty");
            return;
        }


        String end_Date = endDate.getText().toString();
        if (end_Date.isEmpty()) {
            endDate.setError("End date cannot be empty");
            return;
        }

        String dbPath = "/data/data/" + getPackageName() + "/sample.db";

        SQLiteDatabase db;
        db = SQLiteDatabase.openOrCreateDatabase(dbPath, null);

        String titleQuery = "SELECT * FROM notes WHERE title = FILE_NAME";

        Cursor cursor = db.rawQuery(titleQuery, null);
        if(cursor.getCount() > 0){
            FILE_NAME = FILE_NAME + "_(" + cursor.getCount() + 1 + ")";
            cursor.close();
        }


        String text = noteEditText.getText().toString();
        FileOutputStream outStream = null;

        try {
            outStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            outStream.write(text.getBytes());

            noteEditText.getText().clear();
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

        String path = getFilesDir() + "/" + FILE_NAME;

        Date date = new Date();

        String lastEdit = complexDateFormat.format(date);

        // Add Data
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("category", "TEST");
        values.put("start_date", start_date);
        values.put("end_date", end_Date);
        values.put("file_path", path);
        values.put("lastEdit", lastEdit);
        String table = "notes";
        db.insert(table, null, values);

        db.close();

    }

}
