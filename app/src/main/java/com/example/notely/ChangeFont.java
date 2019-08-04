package com.example.notely;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;


public class ChangeFont extends AppCompatActivity implements OnClickListener{

    private TextView mTextMessage;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.font_change);

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
        Button goback = (Button)findViewById(R.id.goto_Settings);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("myApp", "Activity2 button is clicked");
                Intent intent = new Intent();

                intent.setClass(ChangeFont.this, Settings.class);

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

}
