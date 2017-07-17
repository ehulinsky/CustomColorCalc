package com.sniluhky.calculator;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
    private ColorChanger colorChanger;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        EditTextPreference primaryColor = (EditTextPreference) findPreference(getActivity().getResources().getString(R.string.pref_primary_color));
        primaryColor.setOnPreferenceChangeListener(this);
        EditTextPreference backgroundColor = (EditTextPreference) findPreference(getActivity().getResources().getString(R.string.pref_background_color));
        backgroundColor.setOnPreferenceChangeListener(this);
        colorChanger=new ColorChanger(getActivity());
    }

    public boolean onPreferenceChange (Preference preference, Object newValue) {

        EditTextPreference editTextPreference = (EditTextPreference) preference;
        String key = editTextPreference.getKey();
        String text = editTextPreference.getText();
        int color;
        if (key == getActivity().getResources().getString(R.string.pref_primary_color)) {

            try {
                color = Color.parseColor(newValue.toString());
            } catch (IllegalArgumentException illegal) {
                Toast.makeText(getActivity(), getString(R.string.invalid_color), Toast.LENGTH_SHORT).show();
                return false;
            }
            colorChanger.setTopBottomBarsColor(color);
        }
        else if (key == getActivity().getResources().getString(R.string.pref_background_color)) {

            try {
                color = Color.parseColor(newValue.toString());
            } catch (IllegalArgumentException illegal) {
                Toast.makeText(getActivity(), getString(R.string.invalid_color), Toast.LENGTH_SHORT).show();
                return false;
            }
            colorChanger.setTextAndIconColor(color);
        }

        return true;
    }
}