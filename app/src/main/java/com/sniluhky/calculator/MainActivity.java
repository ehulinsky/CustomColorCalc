package com.sniluhky.calculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display=(CalculatorDisplay)getFragmentManager().
                findFragmentById(R.id.display_fragment);
        settings=new Settings(this);
        colorChanger=new ColorChanger(this);
        updateColors();
    }
    public void onResume() {
        super.onResume();
        //update colors which may have been changed in preferences screen
        updateColors();
    }

    private enum Operation {
        ADD,
        SUBTRACT,
        DIVIDE,
        MULTIPLY,
        POWER,
        MODULUS
    }

    public void enterDigit(View view) {
        if(!equalClicked) {
            if(operatorClicked)
            {
                display.reset();
                operatorClicked=false;
                secondNumDigitEntered=true;
            }
            digitEntered = true;
            if (hilitId != 0) {
                ((Button) findViewById(hilitId)).setBackground(getDrawable(R.drawable.blank));
            }

            display.putDigit(Integer.parseInt((String) ((Button) view).getText()));
        }

    }
    public void deleteDigit(View view) {
        display.removeDigit();
    }
    public void setDecimalMode(View view) {
        if(!equalClicked) {
            if(operatorClicked)
            {
                display.reset();
                operatorClicked=false;
                secondNumDigitEntered=true;
            }
            display.toggleDecimalMode();
        }
    }

    public void setOperation(View view) {
        if(digitEntered) {

            if(!operatorClicked) {
                if (!secondNumEditable) {
                    firstNum = display.getDisplayedNumber();
                    secondNumEditable = true;
                    secondNumDigitEntered=false;
                }
                else if(equalClicked)
                {
                    equalClicked=false;
                    secondNumDigitEntered=false;
                }
                else {
                    secondNum = display.getDisplayedNumber();
                    display.putNumber(getResult(firstNum, operation, secondNum));
                    firstNum = display.getDisplayedNumber();
                    secondNumDigitEntered=false;
                }

            }

            operatorClicked=true;
            if (hilitId != 0) {
                ((Button) findViewById(hilitId)).setBackground(getDrawable(R.drawable.blank));
            }
            hilitId = view.getId();
            ((Button) view).setBackgroundResource(R.drawable.back);
            switch (view.getId()) {
                case R.id.buttonAdd:
                    operation =Operation.ADD;
                    break;
                case R.id.buttonSubtract:
                    operation =Operation.SUBTRACT;
                    break;
                case R.id.buttonMultiply:
                    operation =Operation.MULTIPLY;
                    break;
                case R.id.buttonDivide:
                    operation =Operation.DIVIDE;
                    break;
                case R.id.buttonPower:
                    operation =Operation.POWER;
                    break;
                case R.id.buttonModulus:
                    operation = Operation.MODULUS;
            }

        }
    }
    public void equals(View view) {

        if(digitEntered&&secondNumDigitEntered){

                /*
                only sets the second num the first time because if
                user clicks multiple times it should do same thing
                example they press 1, +, 1, then every time they press
                equals it should add one to the current value
                */
                if (!equalClicked) {
                    secondNum = display.getDisplayedNumber();
                }
                display.putNumber(getResult(firstNum, operation, secondNum));
                firstNum = display.getDisplayedNumber();
        }
        else {
            if (hilitId != 0) {
                ((Button) findViewById(hilitId)).setBackground(getDrawable(R.drawable.blank));
            }
        }
        operatorClicked=false;
        equalClicked = true;
    }

    public void setToPi(View view) {
        if(!equalClicked){
            if(operatorClicked)
            {
                display.reset();
                operatorClicked=false;
                secondNumDigitEntered=true;
            }
            digitEntered = true;
            if (hilitId != 0) {
                ((Button) findViewById(hilitId)).setBackground(getDrawable(R.drawable.blank));
            }

            display.putNumber(Math.PI);
        }
    }

    public void setToEuler(View view) {
        if(!equalClicked){
            if(operatorClicked)
            {
                display.reset();
                operatorClicked=false;
                secondNumDigitEntered=true;
            }
            digitEntered = true;
            if (hilitId != 0) {
                ((Button) findViewById(hilitId)).setBackground(getDrawable(R.drawable.blank));
            }

            display.putNumber(Math.E);
        }

    }

    public void toggleNegative(View view) {
        display.toggleNegative();
    }
    public void clear(View view) {
        display.reset();
        firstNum=0;
        secondNum=0;
        operatorClicked=false;
        secondNumEditable=false;
        digitEntered=false;
        equalClicked=false;
        if (hilitId != 0) {
            ((Button) findViewById(hilitId)).setBackground(getDrawable(R.drawable.blank));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        Drawable drawable;
        for(int i=0;i<menu.size();i++)
        {
            drawable=menu.getItem(i).getIcon();
            drawable.mutate();
            drawable.setColorFilter(settings.getBackgroundColor(),PorterDuff.Mode.SRC_ATOP);
            drawable.setAlpha(255);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_info:
                // User chose the "Info" item, show the app settings UI...
                intent = new Intent(this, InfoActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public double getResult(double n1,Operation op,double n2) {
            switch (op) {
                case ADD:
                    return n1+n2;
                case SUBTRACT:
                    return n1-n2;
                case DIVIDE:
                    return n1/n2;
                case MULTIPLY:
                    return n1*n2;
                case POWER:
                    return Math.pow(n1,n2);
                case MODULUS:
                    return n1%n2;
                default:
                    return n1;
            }
    }

    public void squareRoot(View view) {
        if(display.getDisplayedNumber()>=0) {

            display.putNumber(Math.sqrt(display.getDisplayedNumber()));
            if(!secondNumDigitEntered) {
                firstNum = display.getDisplayedNumber();
            }
            else
            {
                secondNum = display.getDisplayedNumber();
            }
            equalClicked=false;

        }
        else
        {
            display.error();
        }
    }

    public void factorial(View view) {
        if(display.getDisplayedNumber()==(int) display.getDisplayedNumber()) {

            int result=Math.abs((int) display.getDisplayedNumber());
            if(display.getDisplayedNumber()==0)
            {
                result=1;
            }

            else {
                for (int i = Math.abs((int) display.getDisplayedNumber())-1; i > 0; i--) {
                    result *= i;
                }
            }
            if(display.getDisplayedNumber()<0)
            {
                result=-result;
            }
            display.putNumber(result);
            if(!secondNumEditable) {
                firstNum = display.getDisplayedNumber();
            }
            else
            {

                secondNum = display.getDisplayedNumber();
            }
        }
        else
        {
            display.error();
        }
    }

   public void updateColors() {

        ((Button) findViewById(R.id.button0)).setTextColor(settings.getPrimaryColor());
        ((Button) findViewById(R.id.button1)).setTextColor(settings.getPrimaryColor());
        ((Button) findViewById(R.id.button2)).setTextColor(settings.getPrimaryColor());
        ((Button) findViewById(R.id.button3)).setTextColor(settings.getPrimaryColor());
        ((Button) findViewById(R.id.button4)).setTextColor(settings.getPrimaryColor());
        ((Button) findViewById(R.id.button5)).setTextColor(settings.getPrimaryColor());
        ((Button) findViewById(R.id.button6)).setTextColor(settings.getPrimaryColor());
        ((Button) findViewById(R.id.button7)).setTextColor(settings.getPrimaryColor());
        ((Button) findViewById(R.id.button8)).setTextColor(settings.getPrimaryColor());
        ((Button) findViewById(R.id.button9)).setTextColor(settings.getPrimaryColor());
        ((Button) findViewById(R.id.buttonAdd)).setTextColor(settings.getPrimaryColor());
        ((Button) findViewById(R.id.buttonSubtract)).setTextColor(settings.getPrimaryColor());
        ((Button) findViewById(R.id.buttonMultiply)).setTextColor(settings.getPrimaryColor());
        ((Button) findViewById(R.id.buttonDivide)).setTextColor(settings.getPrimaryColor());
        ((Button) findViewById(R.id.buttonDecimal)).setTextColor(settings.getPrimaryColor());
        ((Button) findViewById(R.id.buttonEquals)).setTextColor(settings.getPrimaryColor());
       ((Button) findViewById(R.id.buttonClear)).setTextColor(settings.getPrimaryColor());
       ((Button) findViewById(R.id.buttonFactorial)).setTextColor(settings.getPrimaryColor());
       ((Button) findViewById(R.id.buttonModulus)).setTextColor(settings.getPrimaryColor());
       ((Button) findViewById(R.id.buttonPower)).setTextColor(settings.getPrimaryColor());
       ((Button) findViewById(R.id.buttonSquareRoot)).setTextColor(settings.getPrimaryColor());
       ((Button) findViewById(R.id.buttonPi)).setTextColor(settings.getPrimaryColor());
       ((Button) findViewById(R.id.buttonE)).setTextColor(settings.getPrimaryColor());
       ((Button) findViewById(R.id.buttonNegative)).setTextColor(settings.getPrimaryColor());
       ((Button) findViewById(R.id.buttonDelete)).setTextColor(settings.getPrimaryColor());
       findViewById(android.R.id.content).setBackgroundColor(settings.getBackgroundColor());
        colorChanger.setTopBottomBarsColor(settings.getPrimaryColor());
        colorChanger.setTextAndIconColor(settings.getBackgroundColor());

    }
    private Settings settings;
    private CalculatorDisplay display;
    private ColorChanger colorChanger;

    private Operation operation;
    private double firstNum=0;
    private double secondNum=0;

    private boolean operatorClicked=false;
    private boolean secondNumEditable=false;
    private boolean digitEntered=false;
    private boolean secondNumDigitEntered=false;
    private boolean equalClicked=false;

    private int hilitId=0; //id of operation button that is highlighted
}
