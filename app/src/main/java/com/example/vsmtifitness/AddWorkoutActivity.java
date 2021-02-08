package com.example.vsmtifitness;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;


public class AddWorkoutActivity extends AppCompatActivity {
    private EditText nameInput, descInput, calInput;
    private RadioButton totalCal, perKiloCal;
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//odabiri s radio gumbovima


        nameInput = (EditText) findViewById(R.id.workoutNameEditText);
        descInput = (EditText) findViewById(R.id.workOutEditText);
        calInput = (EditText) findViewById(R.id.calAmountEditText);

        totalCal = (RadioButton) findViewById(R.id.totalCalorieSelected);
        perKiloCal = (RadioButton) findViewById(R.id.perKiloSelected);
    }
    // obavi ovo na klik workout buttona
    public void addWorkoutClicked(View view){
        ContentValues values = new ContentValues();
        values.put("Name", nameInput.getText().toString());
        values.put("Description", descInput.getText().toString());
        values.put("CAL_COUNT", Float.valueOf(calInput.getText().toString()));
        if (totalCal.isChecked()){
            values.put("IS_MULTIPLIER", 0);
        }else {
            values.put("IS_MULTIPLIER", 1);
        }

        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        int name = sharedPreferences.getInt("_id", 0);


        values.put("USER_ID", name );
        int idInserted = MainActivity.dBhandler.insertWorkout(values); // stavi podatke u bazu, vrati korišteni id
        WorkoutData thisData = new WorkoutData(idInserted, nameInput.getText().toString(),
                descInput.getText().toString() , values.getAsFloat("CAL_COUNT"), values.getAsInteger("IS_MULTIPLIER"), values.getAsInteger("USER_ID"));
        WorkoutData.workouts.add(thisData); // dodaj u listu
        finish();
    }
}
