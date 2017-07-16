package com.sniluhky.calculator;



import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;


public class CalculatorDisplay extends Fragment {


    public CalculatorDisplay() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_calculator_display, container, false);
        display=(TextView) v.findViewById(R.id.display);
        number=0;
        settings=new Settings(getActivity());
        updateColors();
        return v;

    }
    public void onResume() {
        super.onResume();
        updateColors(); //if they were at settings and changed colors
    }

    public void putDigit(int digit) {
        if(!error) {
            if (canAddDigit()) {
                if (!decimalMode) {

                    if (digit <= 9 && digit >= 0) {
                        if(number<0)
                        {
                            number=-(Math.abs(number)*10+digit);
                        }
                        else {
                            number = number * 10 + digit;
                        }
                        reprint();
                    }

                } else {
                    if (digit <= 9 && digit >= 0) {
                        currentFractionDigits += 1;
                        if(number<0)
                        {
                            number = -(Math.abs(number) + (digit / Math.pow(10, currentFractionDigits)));
                        }
                        else {
                            number = number + (digit / Math.pow(10, currentFractionDigits));
                        }
                        reprint();
                        decimalTyped = true;
                    }


                }
            }
        }
    }
    public void toggleDecimalMode() {
    if(!error) {
        if (!decimalTyped) {
            if (decimalMode) {
                decimalMode = false; //we can remove the dot, just not add it
            } else if (canAddDigit()) {
                decimalMode = true;
            }
            reprint(); //changes whether . is showing
        }
        }
    }
    public void toggleNegative() {
        if(!error) {
                if (number<0) {
                    number=Math.abs(number); //we dont have to check if there is room because we are removing it
                }
                else if(number==0)
                {

                }
                else if (canAddDigit()&&number>0)
                {
                    number=-Math.abs(number);
                }
                reprint(); //changes whether - is showing
        }
    }

    public double getDisplayedNumber() {
        return number;
    }

    private void reprint() {
        display.setText(formatDouble(number));
    }

    public void reset() {
        display.setText("0");
        number=0;
        decimalMode=false;
        decimalTyped=false;
        currentFractionDigits=0;
        error=false;
    }
    public void error() {
        display.setText("ERROR");
        number=0;
        decimalMode=false;
        decimalTyped=false;
        currentFractionDigits=0;
        error=true;
    }

    private String formatDouble(double d) {

        if(!decimalMode) {
            return String.valueOf((long) d);
        }
        else {
            String formatString = "0.";
            for(int i=0;i<currentFractionDigits;i++)
            {
                formatString+="0";
            }
            DecimalFormat df = new DecimalFormat(formatString);
            return df.format(d);
        }
    }
    private boolean canAddDigit() {
        return formatDouble(number).length() < MAX_DIGITS;
    }

    private void updateColors() {
        display.setTextColor(settings.getPrimaryColor());
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Drawable border=getResources().getDrawable(R.drawable.back);
            border.setTint(settings.getPrimaryColor());
            display.setBackground(border);
        }
    }

    //constructs a number by putting digits in one at a time.
    //i dont want to make a separate thing to format them
    public void putNumber(double d) {
        reset();
        String formatString="";
        if(MAX_DIGITS>=1)
        {
            formatString+="#";
        }
        if(MAX_DIGITS>=2)
        {
            formatString+=".";
        }
        for(int i=2;i<MAX_DIGITS;i++)
        {
            formatString+="#";
        }
        DecimalFormat df = new DecimalFormat(formatString);
        String str=df.format(d);
        boolean negative=false;
        for(int i=0;i<str.length();i++)
        {
            if(str.codePointAt(i)>=48&&str.codePointAt(i)<=57)
            {
                if(canAddDigit()) {
                    putDigit(Integer.valueOf(String.valueOf(str.charAt(i))));
                }
                else
                {
                    if(!decimalMode) {
                        error();
                        return;
                    }
                }
            }
            else if(str.charAt(i)=='.')
            {
                toggleDecimalMode();
            }
            else if(str.charAt(i)=='-')
            {
                negative=true;
            }
        }
        if(negative)
        {
            toggleNegative(); //we cant do this at beginning because you cant set 0 to negative
        }
    }

    private Settings settings;
    private final int MAX_DIGITS=10;
    private int currentFractionDigits=0;
    private boolean decimalMode=false;
    private boolean decimalTyped=false;
    private TextView display;
    private double number;
    private boolean error=false;


}
