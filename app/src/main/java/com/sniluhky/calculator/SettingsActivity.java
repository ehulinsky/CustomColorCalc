package com.sniluhky.calculator;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    ColorChanger colorChanger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
        PreferenceManager.setDefaultValues(this,R.xml.preferences,false);
        colorChanger=new ColorChanger(this);
        colorChanger.updateColors();
    }
    public void onResume() {
        super.onResume();
        colorChanger.updateColors();
    }

}
