package com.burnedcaloriescalculator.mehregan.com.burnedcaloriescalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Spinner;



public class BurnedCaloriesCalculator extends ActionBarActivity
        implements OnEditorActionListener {

    //variables for widgets
    //private EditText inputWeight;
    private TextView weightDisplay;
    private TextView heightDisplay;
    private SeekBar weight;
    private Button applyBut;
    private Spinner weightSpin;

    private OnClickListener buttonPress = new OnClickListener() {
        @Override
        public void onClick(View v) {
            CalcAndDisplay();
        }
    };

    //variable for saved values
    private SharedPreferences savedWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_converter);

        //getting reference to the widgets
        //inputWeight = (EditText) findViewById(R.id.inputText);
        weightDisplay = (TextView) findViewById(R.id.weightdisplay);
        heightDisplay = (TextView) findViewById(R.id.heightDisplay);
        weight = (SeekBar) findViewById(R.id.weight);
        savedWeight = getSharedPreferences("Saved Values", MODE_PRIVATE);
        applyBut = (Button) findViewById(R.id.applyButton);
        weightSpin = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adaptWeight = ArrayAdapter.createFromResource(
                this, R.array.weight_array, android.R.layout.simple_spinner_item);
        adaptWeight.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        weightSpin.setAdapter(adaptWeight);

        applyBut.setOnClickListener(buttonPress);
        //inputTemp.setOnEditorActionListener(this);
    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
        if(actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED)
        {
            CalcAndDisplay();
        }
        return false;
    }

    @Override
    public void onPause(){
        Editor edit = savedWeight.edit();
        edit.putString("weigh", weightDisplay.getText().toString());
        edit.putString("height", heightDisplay.getText().toString());
        edit.commit();

        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();

        weightDisplay.setText(savedWeight.getString("weigh",""));
        heightDisplay.setText(savedWeight.getString("height",""));
    }

    private void CalcAndDisplay(){
        //double weigh = Double.parseDouble(inputWeight.getText().toString());
        int weigh = weight.getProgress();
        weightDisplay.setText("" + weight);
        double height = (weigh - 32) * 5/9;

        heightDisplay.setText("" + height);
    }
}
