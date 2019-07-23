package com.example.notely;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Categories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);




        Bundle bundle_categories = this.getIntent().getExtras();
        String OpenedPage = bundle_categories.getString("page");
        int bool = bundle_categories.getInt("bool");

        Log.v("myApp", "name = " + OpenedPage + "; boolean = " + bool);
    }
}
