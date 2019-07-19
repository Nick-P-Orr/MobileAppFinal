package com.example.notely;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@TargetApi(27)

public class MainActivity extends AppCompatActivity {

    EditText noteEditText; // EditText for note-taking
    private TextView titleEditText; // Title of note
    private TextView category; // Category
    private TextView startDate; // Start date text
    private TextView endDate; // End date text
    String FilePath;
    String SearchTitle;
    boolean EDITMODE = false;

    DatePickerDialog picker; // Used to select date on start/end date click
    SimpleDateFormat fileDateFormat = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss"); // Date format for file name
    SimpleDateFormat editDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Date format for last edit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the path and database name
        String path = "/data/data/" + getPackageName() + "/Notely.db";

        // Open the database. If it doesn't exist, create it.
        SQLiteDatabase db;
        db = SQLiteDatabase.openOrCreateDatabase(path, null);

        // Get text fields
        noteEditText = findViewById(R.id.note_field);
        titleEditText = findViewById(R.id.title_field);
        category = findViewById(R.id.category_field);
        startDate = findViewById(R.id.start_date);
        endDate = findViewById(R.id.completion_field);

        // Ensure keyboard doesn't popup on start/end date click
        endDate.setShowSoftInputOnFocus(false);
        startDate.setShowSoftInputOnFocus(false);

        //db.execSQL("DROP TABLE IF EXISTS Notes");

        // Create a table - notes
        String sql = "CREATE TABLE IF NOT EXISTS Notes" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT, Category TEXT, StartDate TEXT, EndDate TEXT, FilePath TEXT, LastEdit TEXT);";
        db.execSQL(sql);

        // Close DB
        db.close();

        // If there is a bundle from Search, extract the data
        try {
            Bundle bundle;
            bundle = this.getIntent().getExtras();
            SearchTitle = bundle.getString("Title");
            String Category = bundle.getString("Category");
            String StartDate = bundle.getString("StartDate");
            String EndDate = bundle.getString("EndDate");
            FilePath = bundle.getString("FilePath");
            titleEditText.setText(SearchTitle);
            category.setText(Category);
            startDate.setText(StartDate);
            endDate.setText(EndDate);

            //Get and read the text file
            File file = new File(FilePath);
            StringBuilder noteText = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    noteText.append(line);
                    noteText.append('\n');
                }
                br.close();
            } catch (IOException e) {
            }
            //Set the text and EDITMODE to true
            noteEditText.setText(noteText.toString());
            EDITMODE = true;
        } catch (NullPointerException e) {
        }


        //@TODO Buttons for testing, must be removed
        Button search = findViewById(R.id.BUTTON);
        Button save = findViewById(R.id.save_note);

        // Set click listeners for start and end date
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
                                startDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);

                            }
                        }, year, month, day);
                picker.show();
            }
        });

        //@TODO Buttons for testing, must be updated
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

    //@TODO must update to fragment method
    public void switchActivity() {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    //Method to save note to local .txt file and add entry to database
    public void save(View v) {
        //Ensure that all required fields are populated
        String FILE_NAME = titleEditText.getText().toString();
        String title = FILE_NAME;
        FILE_NAME = FILE_NAME.replaceAll(" ", "_");
        if (FILE_NAME.isEmpty()) {
            titleEditText.setError("Title cannot be empty");
            return;
        }
        String category_text = category.getText().toString();
        if (category_text.isEmpty()) {
            category.setError("Category cannot be empty");
            return;
        }
        String start_date = startDate.getText().toString();
        if (start_date.isEmpty()) {
            startDate.setError("Start date cannot be empty");
            return;
        }
        String end_Date = endDate.getText().toString();
        if (end_Date.isEmpty()) {
            endDate.setError("End date cannot be empty");
            return;
        }

        // Open database
        String dbPath = "/data/data/" + getPackageName() + "/Notely.db";
        SQLiteDatabase db;
        db = SQLiteDatabase.openOrCreateDatabase(dbPath, null);

        if (EDITMODE) {
            db.delete("Notes", "FilePath = ?", new String[]{SearchTitle});
            File file = new File(FilePath);
            file.delete();
            EDITMODE = false;
        }

        // Add date to file name
        Date date = new Date();
        String fileDate = fileDateFormat.format(date);
        FILE_NAME = FILE_NAME + fileDate;

        // Get text in note field
        String text = noteEditText.getText().toString();
        FileOutputStream outStream = null;

        // write the note to a local .txt file
        try {
            outStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            outStream.write(text.getBytes());
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

        // Get file path and last edit date
        String path = getFilesDir() + "/" + FILE_NAME;
        String lastEdit = editDateFormat.format(date);

        // Add data to ContentValues
        ContentValues values = new ContentValues();
        values.put("Title", title);
        values.put("Category", category_text);
        values.put("StartDate", start_date);
        values.put("EndDate", end_Date);
        values.put("FilePath", path);
        values.put("LastEdit", lastEdit);
        String table = "Notes";
        // Add entry to DB
        db.insert(table, null, values);
        // Close DB
        db.close();
    }
}
