package com.example.vsmtifitness;

import android.app.Activity;
import android.app.ListFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WorkoutListFragment extends ListFragment {

    static interface WorkoutListListener{
        void itemClicked(long id);
    }

    private WorkoutListListener listener;

    public WorkoutListFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        WorkoutData.workouts = WorkoutData.getWorkouts();



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
        /*
            String[] names = new String[counter];
            int counter2 = 0;
            for (int i2 = 0; i2 < namesCount.length; i2++){
                if(WorkoutData.workouts.get(i2).getUser_id() == 0 || WorkoutData.workouts.get(i2).getUser_id() == value_user_id) {
                    names[counter2] = WorkoutData.workouts.get(i2).getName();
counter2++;
                }

        }
*/
        WorkoutData[] names = new WorkoutData[counter];
        int counter2 = 0;
        for (int i2 = 0; i2 < namesCount.length; i2++){
            if(WorkoutData.workouts.get(i2).getUser_id() == 0 || WorkoutData.workouts.get(i2).getUser_id() == value_user_id) {
                names[counter2] = WorkoutData.workouts.get(i2);
                counter2++;
            }
        }





        ArrayAdapter<WorkoutData> adapter = new ArrayAdapter<WorkoutData>(
                inflater.getContext(), android.R.layout.simple_list_item_1, names);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }




    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.listener = (WorkoutListListener)activity;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        if (listener != null){
            listener.itemClicked(id);
        }
    }

}
