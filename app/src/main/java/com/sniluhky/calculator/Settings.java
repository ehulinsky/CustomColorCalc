package com.sniluhky.calculator;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;


/**
 * Created by Hulinsky Family on 7/10/2017.
 */

public class Settings
{
    private SharedPreferences prefs;
    private Context context;

    public Settings(Context ctx)
    {
        context=ctx;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public int getPrimaryColor() {
        int color;
        try {
            color = Color.parseColor(prefs.getString(context.getResources().getString(R.string.pref_primary_color),context.getString(R.string.default_primary_color)));
        }
        catch(IllegalArgumentException illegal)
        {
            color=Color.parseColor(context.getString(R.string.default_primary_color));
        }
        return color;
    }

    public int getBackgroundColor() {
        int color;
        try {
            color = Color.parseColor(prefs.getString(context.getString(R.string.pref_background_color),context.getString(R.string.default_background_color)));
        }
        catch(IllegalArgumentException illegal)
        {
            color=Color.parseColor(context.getString(R.string.default_background_color));
        }
        return color;
    }
}