package com.example.vsmtifitness;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class WorkoutDetailFragment extends Fragment {
    private int workoutId;

    public WorkoutDetailFragment() {

    }

    public void setWorkout(long id) {
        this.workoutId = (int) id;
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



            WorkoutActivity activity = (WorkoutActivity) getActivity();
            int value_user_id = activity.getData();


            Log.i(String.valueOf(value_user_id), "today button hit...");
            String[] namesCount = new String[WorkoutData.workouts.size()];
            int counter = 0;
            for (int i = 0; i < namesCount.length; i++) {
                if (WorkoutData.workouts.get(i).getUser_id() == 0 || WorkoutData.workouts.get(i).getUser_id() == value_user_id) {
                    counter++;
                }
            }
            WorkoutData[] names = new WorkoutData[counter];
            int counter2 = 0;
            for (int i2 = 0; i2 < namesCount.length; i2++){
                if(WorkoutData.workouts.get(i2).getUser_id() == 0 || WorkoutData.workouts.get(i2).getUser_id() == value_user_id) {
                    names[counter2] = WorkoutData.workouts.get(i2);
                    counter2++;
                }
            }


            WorkoutData workout = names[workoutId];
            title.setText(workout.getName());
            TextView description = (TextView) view.findViewById(R.id.textDescription);
            description.setText(workout.getDescription());
            WorkoutActivity.workoutIDSelected = names[workoutId].getID();
            WorkoutActivity.workoutPosition = workoutId;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong("workoutId", workoutId);
    }
}
