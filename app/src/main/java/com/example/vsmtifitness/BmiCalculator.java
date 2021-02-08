package com.example.vsmtifitness;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class BmiCalculator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);
        if (savedInstanceState != null){
            TextView r = (TextView)findViewById(R.id.Result);
            r.setText(savedInstanceState.getString("result"));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // postavi gumb za nazad
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        TextView r = (TextView)findViewById(R.id.Result);
        String result = r.getText().toString();
        outState.putString("result", result);
        super.onSaveInstanceState(outState);
    }

    public void BmiCalculate (View view){
        if (view.getId() == R.id.calculate){

            // deklariraj varijable
            EditText W = (EditText)findViewById(R.id.wight);
            EditText H = (EditText)findViewById(R.id.hight);
            TextView r = (TextView)findViewById(R.id.Result);

            try {
                // dobij podatke od korisnika
                float UserHeight = Float.parseFloat(H.getText().toString());
                float UserWeight = Float.parseFloat(W.getText().toString());
                float bmiValue = BMI(UserWeight, UserHeight);

                // interpretiraj zanacanje bmi vrijednosti
                String bmiInterpretation = interpretBMI(bmiValue);

                // pokazi rezultat

                r.setText(bmiValue + "   " + bmiInterpretation);
                r.setMovementMethod(new ScrollingMovementMethod());
            } catch (Exception e){}
        }
    }


    static float BMI (float weight, float height) {

        return (float) ( ((weight / height / height )* 10000));
    }

    static float BMR (float weight, float height, float age){

        return (float) (  (10 * weight) + (6.25 * height) - (5 * age));
    }

    private String interpretBMI(float BmiResult) {
        if (BmiResult > 30)
        {
            return " - You are Obese. " +
                    "Obesity is a medical condition in which excess body fat has accumulated to an extent that it may have a negative effect on health.";
        }
        else if (BmiResult > 25)
        {
            return " - You are Overweight." +
                    "Being overweight is having more body fat than is optimally healthy. Being overweight is especially common where lifestyles are sedentary.";
        }
        else if (BmiResult > 18.5)
        {
            return " - You are Normal." +
                    "Keep up the good work!";
        }
        else
        {
            return " - You are Underweight." +
                    "Being underweight is not good for your health. Find out what you can do if you're concerned about yourself or someone else.";
        }
    }
}
