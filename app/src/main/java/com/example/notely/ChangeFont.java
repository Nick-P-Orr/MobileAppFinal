package com.example.notely;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;


public class ChangeFont extends AppCompatActivity implements OnClickListener{

    private TextView mTextMessage;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.font_change);
        Log.v("myApp", "In font change");

        Button goback = (Button)findViewById(R.id.goto_main);
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
        });


        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("myApp", "Activity2 button is clicked");
                Intent intent = new Intent();

                intent.setClass(ChangeFont.this, MainActivity.class);

                Bundle bundle = new Bundle();

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        Bundle bundle = this.getIntent().getExtras();


    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.roboto_small:
                Utils.changeToTheme(this, Utils.SmallRoboto);
                break;
            case R.id.button2:
                Utils.changeToTheme(this, Utils.THEME_WHITE);
                break;
            case R.id.button3:
                Utils.changeToTheme(this, Utils.THEME_BLUE);
                break;
        }
    }

}
