package com.example.vsmtifitness;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Recipies extends Fragment {
    Button b1000,b1200,b1500,b1800,bPlanPicker;
    ImageView jpg;


    public int selectedPlan = 0; //gumb za odabrani plan kalorija



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate  layout za ovaj fragment
        return inflater.inflate(R.layout.fragment_recipies, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle onSavedInstanceState) {
        Toast toast;
        super.onViewCreated(view, onSavedInstanceState);
        jpg = (ImageView) getView().findViewById(R.id.imageView3);
        cal1000();
        cal1200();
        cal1500();
        cal1800();
        calPlanPicker();


    }
    public void cal1000(){
        b1000 = (Button) getView().findViewById(R.id.button1000);
        b1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jpg.setImageResource(R.drawable.image1000);
                jpg.setScaleType(ImageView.ScaleType.FIT_END);
                selectedPlan = 1000;
            }
        });
    }

    public void cal1200(){
        b1200 = (Button) getView().findViewById(R.id.button1200);
        b1200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jpg.setImageResource(R.drawable.image1200);
                jpg.setScaleType(ImageView.ScaleType.FIT_END);
                selectedPlan = 1200;
            }
        });
    }

    public void cal1500(){
        b1500 = (Button) getView().findViewById(R.id.button1500);
        b1500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jpg.setImageResource(R.drawable.image1500);
                jpg.setScaleType(ImageView.ScaleType.FIT_END);
                selectedPlan = 1500;
            }
        });
    }

    public void cal1800(){
        b1800 = (Button) getView().findViewById(R.id.button1800);
        b1800.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jpg.setImageResource(R.drawable.image1800);
                jpg.setScaleType(ImageView.ScaleType.FIT_END);
                selectedPlan = 1800;
            }
        });
    }

    public void calPlanPicker(){
        bPlanPicker = (Button) getView().findViewById(R.id.buttonPlanPicker);
        bPlanPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedPlan == 0){
                    Toast.makeText(getActivity(), "Please choose a plan.", Toast.LENGTH_LONG ).show();
                }
                else if(selectedPlan != 0){
                    Toast.makeText(getActivity(), "You have chosen a plan.", Toast.LENGTH_LONG ).show();



                   MainActivity activity = (MainActivity) getActivity();
                   String name = activity.sharedPreferences.getString("name", null);


                  activity.dBhandler.updateMealPlan(name ,selectedPlan);





/*
                   Bundle bundle = new Bundle();
                   bundle.putInt("selectedPlan", selectedPlan);
                   Today fragment = new Today();
                   fragment.setArguments(bundle);

                   getFragmentManager().beginTransaction().replace(R.id.mainFrame, fragment).commit();

*/

                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
      selectedPlan = 0;
    }





}




