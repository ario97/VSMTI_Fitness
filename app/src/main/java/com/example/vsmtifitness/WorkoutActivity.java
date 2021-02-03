package com.example.vsmtifitness;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class WorkoutActivity extends AppCompatActivity implements WorkoutListFragment.WorkoutListListener {
    private static final String TAG = "Workout Activity";
    static int workoutIDSelected;
    static int workoutPosition;
    public static int value_user_id_nest = 0;
    public String value_user_name = "";
    private WorkoutDetailFragment details;
    FragmentManager fragmentManager = getFragmentManager();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

         value_user_id_nest = getIntent().getIntExtra("EXTRA_USER_ID", 0);
        value_user_name = getIntent().getStringExtra("EXTRA_USER_NAME");



        Log.i(String.valueOf(value_user_id_nest), "today button hit222222222222222222222222222222222222222222...");
        Log.i(String.valueOf(value_user_name), "today button hit3333333333333333...");



    }

    public int getData(){
        return getIntent().getIntExtra("EXTRA_USER_ID", 0);

    }




    public void addWorkout(View view){
        Log.d(TAG, "add workout pressed");
        Intent intent = new Intent(this, AddWorkoutActivity.class);
        startActivityForResult(intent, 1);
        Log.d(TAG, "FINISH");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        finish();
        startActivity(getIntent());
    }

    public void removeWorkout(View view){
        Log.d(TAG, "remove workout pressed");

        if (details != null && details.isVisible()){
            new AlertDialog.Builder(this)
                    .setTitle("Confirm Delete")
                    .setMessage("Are you sure you want to delete:\n" + WorkoutData.workouts.get(workoutPosition).getName())
                    .setPositiveButton("yes", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which){
                            Log.d(TAG, "yes button selected");
                            MainActivity.dBhandler.deleteWorkout(workoutIDSelected);
                            WorkoutData.workouts.remove(workoutPosition);

                            finish();
                            startActivity(getIntent());
                        }
                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(TAG, "cancel hit");
                }
            }).show();
        }
        else {
            Toast toast = Toast.makeText(this, "Please Select a Workout to delete", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void scheduleWorkout(View view){
        Log.d(TAG, "schedule workout pressed");
        if (details != null && details.isVisible()) {
            Log.i(String.valueOf(value_user_name), "today button hit3333333333333333...");
            MainActivity.dBhandler.addWorkoutToToday(value_user_name, workoutIDSelected);
        }
        else Toast.makeText(this, "Please Select and item to schedule", Toast.LENGTH_LONG).show();
    }

    @Override
    public void itemClicked(long id) {
        details = new WorkoutDetailFragment();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        details.setWorkout(id);
        ft.replace(R.id.fragment_container, details);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

}
