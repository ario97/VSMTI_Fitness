package com.example.vsmtifitness;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;


public class WorkoutDetailFragment extends Fragment {
    private int workoutId;
    private ArrayList<WorkoutData> namesData;
    public WorkoutDetailFragment() {

    }

    public void setWorkout(long id, ArrayList<WorkoutData> names) {

        this.workoutId = (int) id +1  ;

        this.namesData = names;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            workoutId = (int) savedInstanceState.getLong("workoutId");

        }

        return inflater.inflate(R.layout.fragment_workout_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            TextView title = (TextView) view.findViewById(R.id.textTitle);




            WorkoutData workout = new WorkoutData(0,null, null, 0, 0, 0);






           List<WorkoutData> workouts = WorkoutData.getWorkouts();
            for (int i = 0; i < workouts.size(); i++) {


                if (workouts.get(i).getID() == workoutId - 1) {
workoutId = workoutId -1;

                    workout = WorkoutData.getWorkout(workoutId);

                    title.setText(workout.getName());
                    TextView description = (TextView) view.findViewById(R.id.textDescription);
                    description.setText(workout.getDescription());
                    WorkoutActivity.workoutIDSelected = workout.getID();


                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong("workoutId", workoutId);

    }
}
