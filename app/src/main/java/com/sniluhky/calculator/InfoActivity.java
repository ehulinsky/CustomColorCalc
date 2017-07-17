package com.sniluhky.calculator;



import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class InfoActivity extends AppCompatActivity {


    private Settings settings;
    private ColorChanger colorChanger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        settings=new Settings(this);
        colorChanger=new ColorChanger(this);
        TextView credits=(TextView) findViewById(R.id.credits);
        credits.setTextColor(settings.getPrimaryColor());
        updateColors();
        //PreferenceManager.setDefaultValues(this,R.xml.preferences,false);
    }

     public void updateColors() {
         TextView credits=(TextView) findViewById(R.id.credits);
         credits.setTextColor(settings.getPrimaryColor());
         findViewById(android.R.id.content).setBackgroundColor(settings.getBackgroundColor());
         colorChanger.setTextAndIconColor(settings.getBackgroundColor());
         colorChanger.setTopBottomBarsColor(settings.getPrimaryColor());
    }
}
