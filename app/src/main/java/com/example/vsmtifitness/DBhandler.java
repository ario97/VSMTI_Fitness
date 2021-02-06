package com.example.vsmtifitness;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class DBhandler extends SQLiteOpenHelper{
    private final static String TAG = "SQL DBhelper Class"; // tag za debagiranje
    public static SQLiteDatabase db;
    private final Context dbContext;  // universal context item

    private static final int DATABASE_VERSION = 1;          //db broj verzije
    private static final String DATABASE_NAME = "fitness";  // ime baze
    private static final String TABLE_USER = "Users";       // ime tablice korisnika
    private static final String TABLE_RECIPIES = "Recipies";// ime za tablicu koja ce drzati podatke o receptima
    private static final String TABLE_WORKOUTS = "Workouts";// ime za tablicu koja ce drzati detalje vježbi
    private static final String TABLE_FITNESS_RECORD = "Record";// tablica da prati po danima
    //javni konstruktor
    public DBhandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.dbContext = context;
        db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        DBhandler.db = db;
        createUserTable(); // napravi tablicu korisnika
        createTestPerson(); // kreiraj testnu osobu
        createWorkoutTable(); // napravi tablicu vježbi
        fillWorkoutTable(); // popuni tablicu vježbi
        createRecordsTable(); // napravi tablicu zapisa vježbi
        fillDates(); // popunjava korisnikove podatke
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createUserTable(){
        Log.d(TAG, "Creating user table...");
        String CMD = "CREATE TABLE IF NOT EXISTS "+ TABLE_USER +
                " ( _ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "Name TEXT NOT NULL, " +
                "Password TEXT NOT NULL, " +
                "Weight FLOAT NOT NULL, " +
                "Height FLOAT NOT NULL, " +
                "BMI FLOAT NOT NULL, " +
                "BMR FLOAT NOT NULL, " +
                "Starting_Weight FLOAT NOT NULL, " +
                "Cal_Needs FLOAT NOT NULL, " +
                "Cur_Weight FLOAT NOT NULL, " +
                "Email TEXT, " +
                "Phone TEXT," +
                "MealPlan INTEGER);";

        db.execSQL(CMD);
    }

    private void createTestPerson(){
        Log.d(TAG, "Creating Test Person");

        ContentValues values = new ContentValues();
        values.put("_id", 1);
        values.put("Name", "Test");
        values.put("Password", "pass");
        values.put("Height", 67);
        values.put("Weight", 200);
        values.put("BMI", 31.32);
        values.put("BMR", 1949);
        values.put("Starting_Weight", 200);
        values.put("Cal_Needs", 3021);
        values.put("Cur_Weight", 200);
        values.put("Email", "test@123.com");
        values.put("Phone", "123-222-2222");
        values.put("MealPlan", "0");

        db.insert(TABLE_USER, null, values);
    }

    public static void InsertUser(User user) {
        ContentValues values = new ContentValues();

        values.put("Name", user.getName());
        values.put("Password", user.getPassword());
        values.put("Height", user.getHeight());
        values.put("Weight", user.getWeight());
        values.put("BMI", user.getBMI());
        values.put("BMR", user.getBMR());
        values.put("Starting_Weight", user.getWeight());
        values.put("Cal_Needs", user.getCal_needs());
        values.put("Cur_Weight", user.getCur_weight());
        values.put("Email", user.getEmail());
        values.put("Phone", user.getPhone());
        values.put("MealPlan", user.getMealPlan());

        db.insert(TABLE_USER, null, values);
    }

    public int checkUserLogin(String name, String password){
        Log.d(TAG, "Check user login operation hit with name " + name + " and password " + password);
        String CMD = "SELECT * FROM " + TABLE_USER + " WHERE Name ='" + name + "';";
        Cursor c = db.rawQuery(CMD, null);
        if (c.getCount() < 1){ return 1; }// name was not found
        else{
            c.moveToFirst();
            if (!c.getString(2).equals(password)){ return 2; } // password was not found
        }
        MainActivity.user.setID(c.getInt(0));
        MainActivity.user.setName(c.getString(1));
        MainActivity.user.setPassword(c.getString(2));
        MainActivity.user.setWeight(c.getFloat(3));
        MainActivity.user.setHeight(c.getFloat(4));
        MainActivity.user.setBMI(c.getFloat(5));
        MainActivity.user.setBMR(c.getFloat(6));
        MainActivity.user.setStart_weight(c.getFloat(7));

        MainActivity.user.setCal_needs(c.getFloat(8));
        MainActivity.user.setCur_weight(c.getFloat(9));

        MainActivity.user.setEmail(c.getString(10));
        MainActivity.user.setPhone(c.getString(11));
        MainActivity.user.setMealPlan(c.getInt(12));

        MainActivity.user.commitUserToPrefs();
        return 0;
    }

    public int checkUserLoginRegistration(String name){

        String CMD = "SELECT * FROM " + TABLE_USER + " WHERE Name ='" + name + "';";
        Cursor c = db.rawQuery(CMD, null);
        if (c.getCount() < 1){ return 1; }// name was not found
        else{


        }

        return 0;
    }

    public void updateUserWeight(String name, float weight, float BMI, float BMR){
        ContentValues values = new ContentValues();
        values.put("Cur_Weight", weight);
        values.put("BMI", BMI);
        values.put("BMR", BMR);
        values.put("Cal_Needs", BMR);
        db.update(TABLE_USER, values, "Name = '" + name + "'", null);
    }

    public void updateMealPlan(String name, int mealPlan){
        ContentValues values = new ContentValues();
        values.put("mealPlan", mealPlan);

        db.update(TABLE_USER, values, "Name = '" + name + "'", null);
    }

    private void createWorkoutTable(){
        Log.d(TAG, "Creating Workout Table");
        String CMD = "CREATE TABLE IF NOT EXISTS " + TABLE_WORKOUTS +
                " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "Name TEXT NOT NULL, " +
                "Description TEXT NOT NULL, "+
                "CAL_COUNT FLOAT NOT NULL, " +
                "IS_MULTIPLIER INTEGER NOT NULL, " +
                "USER_ID INTEGER);";
        db.execSQL(CMD);
    }

    private void fillWorkoutTable(){
        Log.d(TAG, "Filling basic workouts table");
        String name = "Name"; String desc = "Description";
        String count = "CAL_COUNT"; String mult = "IS_MULTIPLIER";
        ContentValues values = new ContentValues();

        values.put(name, "Barbell Shoulder Press");
        values.put(desc, "8 to 10 Reps\n5 Sets\n35 Second Rest");
        values.put(count, 0.41);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Barbell Squat");
        values.put(desc, "15 to 20 Reps\n5 Sets\n60 Second Rest");
        values.put(count, .81);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Bench Press");
        values.put(desc, "10 to 12 Reps\n4 Sets\n60 Second Rest");
        values.put(count, 0.72);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Bent-Over Barbell Row");
        values.put(desc, "8 to 10 Reps\n4 Sets\n50 Second Rest");
        values.put(count, 0.9);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Bent-Over Lateral Raise");
        values.put(desc, "15 Reps\n3 Sets\n45 Second Rest");
        values.put(count, 0.72);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Core Agony");
        values.put(desc, "100 Pull-ups\n100 Push-ups\n100 Sit-ups\n100 Squats");
        values.put(count, 2.2);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Deadlift");
        values.put(desc, "10 Reps\n5 Sets\n60 Second Rest");
        values.put(count, 0.94);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Decline Press");
        values.put(desc, "12 to 15 Reps\n3 Sets\n40 Second Rest");
        values.put(count, 0.57);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Dumbbell Lateral Raise");
        values.put(desc, "8 to 10 Reps\n4 Sets\n30 Second Rest");
        values.put(count, 0.5);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Dumbbell Shoulder Press");
        values.put(desc, "10 Reps\n5 Sets\n30 Second Rest");
        values.put(count, 0.4);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Dumbbell Shrug");
        values.put(desc, "12 to 15 Reps\n3 Sets\n50 Second Rest");
        values.put(count, 0.95);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Dumbbell Walking Lunge");
        values.put(desc, "10 to 12 Reps\n4 Sets\n50 Second Rest");
        values.put(count, 0.72);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Flat-Bench Dumbbell Fly");
        values.put(desc, "15 Reps\n3 Sets\n45 Second Rest");
        values.put(count, 0.7);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Flat-Bench Dumbbell Press");
        values.put(desc, "8 to 10 Reps\n4 Sets\n50 Second Rest");
        values.put(count, 0.64);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Front Lat Pull-Down");
        values.put(desc, "8 to 10 Reps\n4 Sets\n45 Second Rest");
        values.put(count, 0.67);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Hanging Leg Raise");
        values.put(desc, "15 to 20 Reps\n5 Sets\n30 Second Rest");
        values.put(count, 0.72);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Incline-Bench Dumbbell Curl");
        values.put(desc, "10 to 12 Reps\n5 Sets\n30 Second Rest");
        values.put(count, 0.4);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Incline Dumbbell Press");
        values.put(desc, "8 to 10 Reps\n4 Sets\n45 Second Rest");
        values.put(count, 0.65);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Leg Extension");
        values.put(desc, "15 Reps\n3 Sets\n50 Second Rest");
        values.put(count, 0.52);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Lying EZ-Bar French Press");
        values.put(desc, "8 to 10 Reps\n4 Sets\n30 Second Rest");
        values.put(count, 0.51);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Lying Leg Curl");
        values.put(desc, "12 to 15 Reps\n3 Sets\n45 Second Rest");
        values.put(count, 0.49);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Narrow-Grip Pull-up");
        values.put(desc, "10 to 15 Reps\n5 Sets\n60 Second Rest");
        values.put(count, 0.92);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Plate Front Raise");
        values.put(desc, "15 Reps\n3 Sets\n45 Second Rest");
        values.put(count, 0.47);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Preacher Curl");
        values.put(desc, "15 Reps\n3 Sets\n40 Second Rest");
        values.put(count, 0.57);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Reverse-Grip Lat Pull-Down");
        values.put(desc, "15 Reps\n3 Sets\n45 Second Rest");
        values.put(count, 0.72);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Romanian Deadlift");
        values.put(desc, "12 to 15 Reps\n4 Sets\n60 Second Rest");
        values.put(count, 0.63);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Seated Calf Raise");
        values.put(desc, "12 to 15 Reps\n4 Sets\n45 Second Rest");
        values.put(count, 0.72);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Seated Overhead Dumbbell Extension");
        values.put(desc, "8 to 10 Reps\n4 Sets\n40 Second Rest");
        values.put(count, 0.45);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Single-Arm Preacher Curl");
        values.put(desc, "10 to 12 Reps\n5 Sets\n30 Second Rest");
        values.put(count, 0.42);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Single-Arm Skull-Crusher");
        values.put(desc, "8 to 10 Reps\n5 Sets\n30 Second Rest");
        values.put(count, 0.47);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Single Leg Press");
        values.put(desc, "10 to 12 Reps\n4 Sets\n50 Second Rest");
        values.put(count, 0.62);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Standing Barbell Curl");
        values.put(desc, "8 to 10 Reps\n4 Sets\n30 Second Rest");
        values.put(count, 0.47);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Standing Calf Raise");
        values.put(desc, "12 to 15 Reps\n3 Sets\n45 Second Rest");
        values.put(count, 0.37);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Strength and Length");
        values.put(desc, "500 meter run\n21 x 1.5 pood kettlebell swing\n21 x pull-ups");
        values.put(count, 2.4);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "T-Bar Row");
        values.put(desc, "12 to 15 Reps\n3 Sets\n50 Second Rest");
        values.put(count, 0.66);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "The Limb Loosener");
        values.put(desc, "5 Handstand push-ups\n10 1-legged squats\n15 pull-ups");
        values.put(count, 1.2);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "The Wimp Special");
        values.put(desc, "5 Pull-ups\n10 Push-ups\n15 Squats");
        values.put(count, 3.2);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Triceps Dip Machine");
        values.put(desc, "15 Reps\n3 Sets\n30 Second Rest");
        values.put(count, 0.64);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "V-Bar Push-Down");
        values.put(desc, "15 to 20 Reps\n5 Sets\n40 Second Rest");
        values.put(count, 0.54);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

        values.put(name, "Weighted Decline Crunch");
        values.put(desc, "15 to 20 Reps\n4 Sets\n30 Second Rest");
        values.put(count, 0.85);
        values.put(mult, 1);
        db.insert(TABLE_WORKOUTS, null, values);
        values.clear();

    }

    public boolean deleteWorkout(int id){
        Log.d(TAG, "workout with id " + id + "selected for deletion");
        return db.delete(TABLE_WORKOUTS, "_ID" + "=" + id, null) > 0;
    }

    public int insertWorkout(ContentValues values){
        db.insert(TABLE_WORKOUTS, null, values);
        String CMD = "SELECT * FROM " + TABLE_WORKOUTS + " WHERE Name ='" + values.get("Name") + "';";
        Cursor c = db.rawQuery(CMD, null);
        c.moveToFirst();
        return c.getInt(0);
    }
    private void createRecordsTable(){
        Log.d(TAG, "Creating Records table");
        String CMD = "CREATE TABLE IF NOT EXISTS " + TABLE_FITNESS_RECORD +
                " (Date TEXT NOT NULL, " +
                "Workout_ID INT NOT NULL, " +
                "Name TEXT NOT NULL, " +
                "Calories INT NOT NULL, " +
                "IS_MULTIPLIER INT NOT NULL," +
                "USER_NAME TEXT NOT NULL," +
                "RECORD_ID INTEGER PRIMARY KEY AUTOINCREMENT);";
        db.execSQL(CMD);

    }
    private void fillDates(){
        Log.d(TAG, "filling dates");
        ContentValues values = new ContentValues();

        values.put("Date", "11/27/20");
        values.put("Workout_ID", 2);
        values.put("Name", "Test");
        values.put("Calories", 200);
        values.put("IS_MULTIPLIER", 0);
        values.put("USER_NAME", "Test");
        db.insert(TABLE_FITNESS_RECORD, null, values);

        values.put("Date", "11/27/20");
        values.put("Workout_ID", 3);
        values.put("Name", "Test");
        values.put("Calories", 200);
        values.put("IS_MULTIPLIER", 0);
        values.put("USER_NAME", "Test");
        db.insert(TABLE_FITNESS_RECORD, null, values);
    }
    public static void addWorkoutToToday(String name, int id){
        final String DateTime = DateFormat.getDateFormat(MainActivity.mContext).format(new Date());
        ContentValues values = new ContentValues();
        String CMD = "SELECT * FROM " + TABLE_WORKOUTS + " WHERE _ID = " + id + ";";
        Cursor c = db.rawQuery(CMD, null);
        c.moveToFirst();
        values.put("Date", DateTime);
        values.put("Workout_ID", c.getInt(0));
        values.put("Name", c.getString(1));
        values.put("Calories", c.getFloat(3));
        values.put("IS_MULTIPLIER", c.getInt(4));
        values.put("USER_NAME", name);
        Log.d(TAG, "inserting name " + c.getString(1) + " id " + c.getInt(0) + " date " + DateTime);
        db.insert(TABLE_FITNESS_RECORD, null, values);
        Toast.makeText(MainActivity.mContext, c.getString(1) + " added to todays workout list", Toast.LENGTH_LONG).show();
    }

    public static int[] returnTodaysRecords(String name, String date){
        Log.d(TAG, "Return items from date " + date);
        int[] theList;
        String CMD = "SELECT * FROM " + TABLE_FITNESS_RECORD + " WHERE Date ='" + date + "' AND USER_NAME ='" + name + "';";
        Cursor c = db.rawQuery(CMD, null);
        if (c.getCount() <= 0){
            return null;
        }
        c.moveToFirst();
        theList = new int[c.getCount()];
        for (int i = 0; !c.isAfterLast(); i++){
            theList[i] = c.getInt(1);
            c.moveToNext();
        }
        return theList;
    }

    public static int[] returnTodaysRecordsID(String name, String date){
        Log.d(TAG, "Return items from date " + date);
        int[] theList;
        String CMD = "SELECT * FROM " + TABLE_FITNESS_RECORD + " WHERE Date ='" + date + "' AND USER_NAME ='" + name + "';";
        Cursor c = db.rawQuery(CMD, null);
        if (c.getCount() <= 0){
            return null;
        }
        c.moveToFirst();
        theList = new int[c.getCount()];
        for (int i = 0; !c.isAfterLast(); i++){
            theList[i] = c.getInt(6);
            c.moveToNext();
        }
        return theList;
    }


    public static int returnMealPlan(String name){

int value = 0;

if(name != null) {
    String CMD = "SELECT MealPlan FROM " + TABLE_USER + " WHERE Name ='" + name + "';";


    Cursor c = db.rawQuery(CMD, null);

    c.moveToFirst();
    value = c.getInt(0);
}
        return value;

    }


    public static void deleteFromToday(String date, int ID){
        Log.d(TAG, "date passed " + date + "ID passed " + ID);
        db.delete(TABLE_FITNESS_RECORD, "RECORD_ID=" + ID + " AND Date='" + date + "'", null);
    }

    public void destroyDB(){
        dbContext.deleteDatabase(DATABASE_NAME);
    }
}
