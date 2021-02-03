package com.example.vsmtifitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, age, password, email, phone, weight, height;
    private TextView BMI, BMR;
    boolean calcComplete = false;
    static DBhandler dBhandler;
    private int mealPlan = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dBhandler = new DBhandler(this);

        name = (EditText)findViewById(R.id.editTextUsername);
        age = (EditText) findViewById(R.id.textAge);
        password = (EditText) findViewById(R.id.editTextPassword);
        email = (EditText) findViewById(R.id.editTextemail);
        phone = (EditText) findViewById(R.id.editTextPhone);
        weight = (EditText) findViewById(R.id.editTextWight);
        height = (EditText) findViewById(R.id.editTextHeight);
        BMI = (TextView) findViewById(R.id.textBMI);
        BMR = (TextView) findViewById(R.id.textBMR);

        weight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                float localHeight;
                int localAge;
                if (!height.getText().toString().equals("")) // ako visina ne postoji postavi je
                    localHeight = Float.valueOf(height.getText().toString());
                else localHeight = 0; // inače visina nije postojala

                if (!age.getText().toString().equals(""))
                    localAge = Integer.valueOf(age.getText().toString());
                else localAge = 0;

                if (!hasFocus && localHeight > 0 ) { // ako izgubi fokus izracunaj ako je tezina unesena
                    BMI.setText(String.valueOf(BmiCalculator.BMI(Float.valueOf(weight.getText().toString()), Float.valueOf(height.getText().toString()))));
                    if (localAge > 0)
                        BMR.setText(String.valueOf(BmiCalculator.BMR(Float.valueOf(weight.getText().toString()),
                                Float.valueOf(height.getText().toString()), Integer.valueOf(age.getText().toString()))));
                }
            }
        });

        height.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                float localWeight;
                int localAge;
                if (!weight.getText().toString().equals(""))
                    localWeight = Float.valueOf(weight.getText().toString());
                else localWeight = 0;

                if (!age.getText().toString().equals(""))
                    localAge = Integer.valueOf(age.getText().toString());
                else localAge = 0;

                if (!hasFocus && localWeight > 0) {
                    BMI.setText(String.valueOf(BmiCalculator.BMI(Float.valueOf(weight.getText().toString()), Float.valueOf(height.getText().toString()))));
                    if (localAge > 0)
                        BMR.setText(String.valueOf(BmiCalculator.BMR(Float.valueOf(weight.getText().toString()),
                                Float.valueOf(height.getText().toString()), Integer.valueOf(age.getText().toString()))));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
    }

    public void calculate(View view){
        Toast toast;
        calcComplete = false;

        if (age.getText().toString().equals("")) {
            toast = Toast.makeText(this, "Must set an age for calculation...", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (weight.getText().toString().equals("")){
            toast = Toast.makeText(this, "Must set a weight for calculation...", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (height.getText().toString().equals("")){
            toast = Toast.makeText(this, "Must set a height for calculation...", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        BMI.setText(String.valueOf(BmiCalculator.BMI(Float.valueOf(weight.getText().toString()), Float.valueOf(height.getText().toString()))));
        BMR.setText(String.valueOf(BmiCalculator.BMR(Float.valueOf(weight.getText().toString()),
                Float.valueOf(height.getText().toString()), Integer.valueOf(age.getText().toString()))));
        calcComplete = true;
    }


    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    public void register(View view){
        Toast toast;

        if (name.getText().toString().equals("")){
            toast = Toast.makeText(this, "Must set a user name...", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        String username = name.getText().toString();
Log.i(username, "nest3333333333333333333333333");
        int userDoesNotExist = dBhandler.checkUserLoginRegistration(username);

        if (userDoesNotExist == 0){

            toast = Toast.makeText(this, "Change username please, this one is already taken...", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }



        if (age.getText().toString().equals("")) {
            toast = Toast.makeText(this, "Must set an age...", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (password.getText().toString().equals("")){
            toast = Toast.makeText(this, "Must set a password...", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }


        if (email.getText().toString().equals("")){
            toast = Toast.makeText(this, "Must set an email...", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (!isValidEmail(email.getText().toString())){
            toast = Toast.makeText(this, "Incorrect email format...", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }


        if (phone.getText().toString().equals("")){
            toast = Toast.makeText(this, "Must set a phone number...", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }


        if (weight.getText().toString().equals("")){
            toast = Toast.makeText(this, "Must set a weight...", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (height.getText().toString().equals("")){
            toast = Toast.makeText(this, "Must set a height...", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }











        MainActivity.user.setName(name.getText().toString());
        MainActivity.user.setPassword(password.getText().toString());
        MainActivity.user.setWeight(Float.valueOf(weight.getText().toString()));
        MainActivity.user.setCur_weight(Float.valueOf(weight.getText().toString()));
        MainActivity.user.setStart_weight(Float.valueOf(weight.getText().toString()));
        MainActivity.user.setHeight(Float.valueOf(height.getText().toString()));
        MainActivity.user.setBMI(Float.valueOf(BMI.getText().toString()));
        MainActivity.user.setBMR(Float.valueOf(BMR.getText().toString()));

        MainActivity.user.setCal_needs(Float.valueOf(BMR.getText().toString()));
        MainActivity.user.setEmail(email.getText().toString());
        MainActivity.user.setPhone(phone.getText().toString());
        MainActivity.user.setMealPlan(mealPlan);
        MainActivity.user.commitUserToDB();
        finish();
    }
}