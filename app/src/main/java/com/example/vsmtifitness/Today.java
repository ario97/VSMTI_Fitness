package com.example.vsmtifitness;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Today extends Fragment {

    private OnFragmentInteractionListener mListener;
    private TextView date, weight, dailyCals, needCals, mealPlan;
    private ListView list;
    private Button addWorkout;
    int selectedPlan = 0;
    public SharedPreferences sharedPreferences; // dobij pristup u dijeljene preference


    public Today() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        return inflater.inflate(R.layout.fragment_today, container, false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        final String DateTime = DateFormat.getDateFormat(MainActivity.mContext).format(new Date());
        date = getView().findViewById(R.id.date);
        mealPlan = getView().findViewById(R.id.currentPlanText);
        date.setText(DateTime);
        list = getView().findViewById(R.id.workList);
        weight = getView().findViewById(R.id.weight);
        weight.setText(String.valueOf(MainActivity.user.getCur_weight()));
        dailyCals = getView().findViewById(R.id.calorieDisplay);
        needCals = getView().findViewById(R.id.DailyCaloriesText);




        MainActivity activity = (MainActivity) getActivity();
        String name = activity.sharedPreferences.getString("name", null);

        final int[] workoutIDs = DBhandler.returnTodaysRecords(name, DateTime);
        final int[] workoutDeleteIDs = DBhandler.returnTodaysRecordsID(name, DateTime);

        if (workoutIDs != null) {
            float calorieCounter = 0;
            List<String> items = new ArrayList<String>();
            final List<workoutDetail> items2 = new ArrayList<workoutDetail>();
            workoutDetail works;
            for (int i = 0; i < workoutIDs.length; i++) {
                for (int x = 0; x < WorkoutData.workouts.size(); x++) {
                    if (WorkoutData.workouts.get(x).getID() == workoutIDs[i]) {
                        items.add(WorkoutData.workouts.get(x).getName());
                        works = new workoutDetail();
                        works.wName = WorkoutData.workouts.get(x).getName();
                        works.wDescription = WorkoutData.workouts.get(x).getDescription();
                        works.wCalories = WorkoutData.workouts.get(x).getCal_count();
                        works.wMultiplier = WorkoutData.workouts.get(x).getMultiplier();

                        if (works.wMultiplier == 0) calorieCounter = calorieCounter + works.wCalories;

                        else calorieCounter = calorieCounter + (works.wCalories * MainActivity.user.getCur_weight());
                        items2.add(works);
                        works = null;
                    }
                }
            }






            /*
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
            selectedPlan = sharedPreferences.getInt("selectedPlan", 0);

            String p = sharedPreferences.getString("name", null);




            String p = sharedPreferences.getString("selectedPlanDate", null);


                Bundle bundle = this.getArguments();
                if (bundle != null) {
                    selectedPlan = bundle.getInt("selectedPlan", 0);


                    SharedPreferences.Editor editor = sharedPreferences.edit();


                    editor.putInt("selectedPlan", selectedPlan);
                    editor.commit();


                    editor.putString("selectedPlanDate", DateTime);
                    editor.commit();

                }


             */
            selectedPlan = DBhandler.returnMealPlan(name);
            mealPlan.setText(String.valueOf(selectedPlan));


            dailyCals.setText(String.format("%.0f", calorieCounter));
            needCals.setText(String.format("%.0f", MainActivity.user.getCal_needs() +  calorieCounter - selectedPlan) );
            if (MainActivity.user.getCal_needs() <= (calorieCounter + 100)) dailyCals.setTextColor(Color.GREEN);

            else if (MainActivity.user.getCal_needs() <= (calorieCounter + 300)) dailyCals.setTextColor(Color.BLUE);

            else dailyCals.setTextColor(Color.RED);


            ((MainActivity) getActivity()).messageDaily = MainActivity.user.getCal_needs() +  calorieCounter  ;


            ArrayAdapter<String> itemsAdapter =
                    new ArrayAdapter<String>(MainActivity.mContext, android.R.layout.simple_list_item_1, items);

            list.setAdapter(itemsAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    Log.d("today item listener ", "Item selcted");
                    float calories;
                    if (items2.get(position).wMultiplier == 0) // if no multiplier show straight up calories
                        calories = items2.get(position).wCalories;
                    else calories = items2.get(position).wCalories * MainActivity.user.getCur_weight(); // else if there is a multiplier so the math

                    new AlertDialog.Builder(MainActivity.mContext)
                            .setTitle("Workout Review")
                            .setMessage("Workout Desription:\n" + items2.get(position).wDescription + "\n\nCalories: " + String.format("%.0f", calories))
                            .setPositiveButton("Delete from Schedule", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("pp", "position " + position);
                                    DBhandler.deleteFromToday(DateTime, workoutDeleteIDs[position]);
                                    mListener.onFragmentInteraction(position);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
            });
        }
        else{

            float calorieCounter = 0;

            selectedPlan = DBhandler.returnMealPlan(name);
            mealPlan.setText(String.valueOf(selectedPlan));

            needCals.setText(String.format("%.0f", MainActivity.user.getCal_needs()  + calorieCounter  - selectedPlan));

            ((MainActivity) getActivity()).messageDaily = MainActivity.user.getCal_needs() +  calorieCounter  ;

        }
        addWorkout = getView().findViewById(R.id.addWorkoutButton);
        addWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WorkoutActivity.class);
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                intent.putExtra("EXTRA_USER_ID", sharedPreferences.getInt("_id", 0));
                intent.putExtra("EXTRA_USER_NAME", sharedPreferences.getString("name", null));
                startActivity(intent);
            }
        });
    }

    private class workoutDetail {
        public String wName;
        public String wDescription;
        public Float wCalories;
        public int wMultiplier;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
try{
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    interface OnFragmentInteractionListener {
        void onFragmentInteraction(int test);
    }
}