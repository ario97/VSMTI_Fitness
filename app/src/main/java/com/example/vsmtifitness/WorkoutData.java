package com.example.vsmtifitness;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkoutData {
    private int ID;
    private String name;
    private String description;
    private float cal_count;
    private int multiplier;
    private int user_id;

    public static List<WorkoutData> workouts = getWorkouts();


   public static List<WorkoutData> getWorkouts(){



        List<WorkoutData> theList = new ArrayList<WorkoutData>();
        WorkoutData temp;
        String query = "SELECT * FROM Workouts;";
        Cursor c = DBhandler.db.rawQuery(query, null);
        c.moveToFirst();
        Log.d("Workout Data Class", "Starting to fill list");

        while (!c.isAfterLast()){
            temp = new WorkoutData(c.getInt(0), c.getString(1), c.getString(2), c.getFloat(3), c.getInt(4),c.getInt(5));
            theList.add(temp);
            temp = null;
            c.moveToNext();
        }
        Log.d("Workout Data Class", "List Filled");
       Collections.reverse(theList);
       return theList;
    }

    public WorkoutData(int id, String name, String description, float cal_count, int multiplier, int user_id) {
        this.ID = id;
        this.name = name;
        this.description = description;
        this.cal_count = cal_count;
        this.multiplier = multiplier;
        this.user_id = user_id;
    }

    public int getUser_id(){return user_id;}

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getID(){ return ID; }

    public float getCal_count() {
        return cal_count;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public String toString() {
        return this.name;
    }
}