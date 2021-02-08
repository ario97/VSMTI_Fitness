package com.example.vsmtifitness;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class WorkoutListFragment extends ListFragment {

    static interface WorkoutListListener{
        void itemClicked(long id,ArrayList<WorkoutData> data );

    }

    private WorkoutListListener listener;

    public WorkoutListFragment() {

    }

   public ArrayAdapter<WorkoutData> adapter;

    ArrayList<WorkoutData> names;
View view;

    ListView lvFirst;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){

        view = inflater.inflate(R.layout.list_workouts, container, false);

        final EditText etSearchbox=(EditText) view.findViewById(R.id.etSearchbox);
         lvFirst=(ListView) view.findViewById(android.R.id.list);
        lvFirst.setTextFilterEnabled(true);

if(names == null) {


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

names = new ArrayList();
    int counter2 = 0;
    for (int i2 = 0; i2 < namesCount.length; i2++) {
        if (WorkoutData.workouts.get(i2).getUser_id() == 0 || WorkoutData.workouts.get(i2).getUser_id() == value_user_id ) {
            names.add(WorkoutData.workouts.get(i2));
            counter2++;
        }
    }


    adapter = new ArrayAdapter<WorkoutData>(
            inflater.getContext(), android.R.layout.simple_list_item_1, names);
    lvFirst.setAdapter(adapter);

    lvFirst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (listener != null){
                listener.itemClicked(names.get(((int)id )).getID(), names);
            }
        }
    });


}










        etSearchbox.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {

               fillAdapter(inflater, s);

           }
       });



        return view;
    }




    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.listener = (WorkoutListListener)activity;
    }




    @Override
    public void onListItemClick(ListView l, View v, int position, long id){

        if (listener != null){
            listener.itemClicked(names.get((int)id).getID(), names);

        }
    }


    public void fillAdapter(LayoutInflater inflater, CharSequence s ){




        WorkoutData.workouts = WorkoutData.getWorkouts();



        WorkoutActivity activity = (WorkoutActivity) getActivity();
        int value_user_id = activity.getData();


        Log.i(String.valueOf(value_user_id), "today button hit...");



        String[] namesCount = new String[WorkoutData.workouts.size()];



        names.clear();

        int counter = 0;
        for (int i = 0; i < namesCount.length; i++) {
            if (WorkoutData.workouts.get(i).getUser_id() == 0 || WorkoutData.workouts.get(i).getUser_id() == value_user_id) {

                counter++;
            }


        }


        int counter2 = 0;
        for (int i2 = 0; i2 < namesCount.length; i2++){
            if(WorkoutData.workouts.get(i2).getUser_id() == 0  || WorkoutData.workouts.get(i2).getUser_id() == value_user_id ) {
                if(WorkoutData.workouts.get(i2).getName().toLowerCase().contains(s) || (WorkoutData.workouts.get(i2).getName().contains(s) )){
                    names.add(WorkoutData.workouts.get(i2));

                    counter2++;
                }
            }
        }

adapter.notifyDataSetChanged();


    }
}
