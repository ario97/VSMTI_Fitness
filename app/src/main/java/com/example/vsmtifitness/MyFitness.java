package com.example.vsmtifitness;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyFitness<var> extends Fragment {

    private OnFragmentInteractionListener mListener;
    private TextView name, weight, stWeight, BMI, BMR, calories, lbsLost, dailyCalories;

    public MyFitness() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout ovog  fragmenta
        return inflater.inflate(R.layout.fragment_my_fitness, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // dohvati reference na textviewove
        name = (TextView) getView().findViewById(R.id.nameText);
        stWeight = (TextView) getView().findViewById(R.id.startWeight);
        weight = (TextView) getView().findViewById(R.id.currWeight);
        BMI = (TextView) getView().findViewById(R.id.BMI);
        BMR = (TextView) getView().findViewById(R.id.BMR);
        calories = (TextView) getView().findViewById(R.id.calories);
        lbsLost = (TextView) getView().findViewById(R.id.lostWeight);

        MainActivity activity = (MainActivity) getActivity();
        String nameUser = activity.sharedPreferences.getString("name", null);


        int selectedPlan = activity.dBhandler.returnMealPlan(nameUser);
        String numberDaily = String.format("%.0f", ((MainActivity) getActivity()).messageDaily - selectedPlan);
        // postavi tekstove
        name.setText(MainActivity.user.getName());
        stWeight.setText(String.valueOf(MainActivity.user.getStart_weight()));
        weight.setText(String.valueOf(MainActivity.user.getCur_weight()));
        BMI.setText(String.valueOf(MainActivity.user.getBMI()));
        BMR.setText(String.valueOf(MainActivity.user.getBMR()));
        calories.setText(numberDaily);
        lbsLost.setText(String.valueOf(MainActivity.user.getStart_weight() - MainActivity.user.getCur_weight()));
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
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




    public interface OnFragmentInteractionListener {

        public void onFragmentInteraction(Uri uri);
    }
}