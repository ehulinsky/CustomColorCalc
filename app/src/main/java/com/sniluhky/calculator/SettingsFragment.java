package com.sniluhky.calculator;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        EditTextPreference primaryColor = (EditTextPreference) findPreference(getActivity().getResources().getString(R.string.pref_primary_color));
        primaryColor.setOnPreferenceChangeListener(this);
        EditTextPreference backgroundColor = (EditTextPreference) findPreference(getActivity().getResources().getString(R.string.pref_background_color));
        backgroundColor.setOnPreferenceChangeListener(this);
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
        }
        else if (key == getActivity().getResources().getString(R.string.pref_background_color)) {

            try {
                color = Color.parseColor(newValue.toString());
            } catch (IllegalArgumentException illegal) {
                Toast.makeText(getActivity(), getString(R.string.invalid_color), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}