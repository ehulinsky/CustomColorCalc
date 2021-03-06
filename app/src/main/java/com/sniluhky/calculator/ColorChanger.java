package com.sniluhky.calculator;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Hulinsky Family on 7/13/2017.
 */

public class ColorChanger {
    Activity activity;
   Settings settings;
    public ColorChanger(Activity act) {
         activity=act;
        settings=new Settings((Context)activity);
    }

    public void setTextAndIconColor(int color) {
        ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
        //change color of action bar title
        Spannable text = new SpannableString(actionBar.getTitle());
        text.setSpan(new ForegroundColorSpan(color), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        actionBar.setTitle(text);
        //change color of back arrow
        Drawable upArrow = activity.getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        ((AppCompatActivity)activity).getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    public void setTopBottomBarsColor(int color) {
        //change color of navigation bar and status bar
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window=activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
            window.setNavigationBarColor(color);

        }

        //change color of action bar
       ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(color));

    }
}
