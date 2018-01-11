package com.glutanimate.editdistance;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Send button */
    public void onCalculate(View view) {
        // Get input:
        EditText editText1 = (EditText) findViewById(R.id.string1Entry);
        EditText editText2 = (EditText) findViewById(R.id.string2Entry);
        String string1 = editText1.getText().toString();
        String string2 = editText2.getText().toString();

        // Perform calculations
        EditDistance editDistance = new EditDistance(string1, string2);
        EditDistance.DistanceData distanceData = editDistance.calculate();

        // Set edit distance EditText value
        EditText resultNumber = (EditText) findViewById(R.id.resultNumber);
        resultNumber.setText(Integer.toString(distanceData.distance), TextView.BufferType.NORMAL);

        // Set result matrix EditText value
        // TODO: find way to handle longer strings without linebreaks
        EditText resultMatrix = (EditText) findViewById(R.id.resultMatrix);
        resultMatrix.setText(distanceData.matrix, TextView.BufferType.NORMAL);

        // Hide soft keyboard:
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(
                                                                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
