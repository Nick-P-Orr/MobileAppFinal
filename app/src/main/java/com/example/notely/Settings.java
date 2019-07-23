package com.example.notely;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);





        Bundle bundle = this.getIntent().getExtras();
        String OpenedPage = bundle.getString("page");
        int bool = bundle.getInt("bool");

        Log.v("myApp", "name = " + OpenedPage + "; boolean = " + bool);


        ///////go to All Notes
        Button allnotes_btn = (Button) findViewById(R.id.goto_all_notes_btn);

        allnotes_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Log.v("myApp", "Activity button is clicked");
                Intent intent_allnotes = new Intent();
                intent_allnotes.setClass( Settings.this, AllNotes.class);

                Bundle bundle_allnotes = new Bundle();
                bundle_allnotes.putString("Page","All Notes");
                bundle_allnotes.putInt("bool",1);

                intent_allnotes.putExtras(bundle_allnotes);
                startActivity(intent_allnotes);
            }

        });


        ///////go to Categories button
        Button categories_btn = (Button) findViewById(R.id.goto_categories_btn);

        categories_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Log.v("myApp", "Activity button is clicked");
                Intent intent_categories = new Intent();
                intent_categories.setClass( Settings.this, Categories.class);

                Bundle bundle_categories = new Bundle();
                bundle_categories.putString("Page","Categories");
                bundle_categories.putInt("bool",1);

                intent_categories.putExtras(bundle_categories);
                startActivity(intent_categories);
            }

        });



    }
}
