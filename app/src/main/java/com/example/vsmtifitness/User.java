package com.example.vsmtifitness;



import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class User {
    private final String TAG = "User Class Activity";

    private String name, password, email, phone;
    private float weight, height, BMI, BMR, start_weight, cal_needs, cur_weight;
    private int _id, mealPlan;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private float id;


    public User(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences; // postavlja preferencijalno
        this.editor = sharedPreferences.edit(); // postavlja za uređivanje
    }

    //ova metoda popunjava korisničke podatke od preferencijalnih

    public void fillUserFromPrefs() {
        this.id = sharedPreferences.getInt("_id", 0);
        this.name = sharedPreferences.getString("name", null);
        this.password = sharedPreferences.getString("password", null);
        this.weight = sharedPreferences.getFloat("weight", 0);
        this.height = sharedPreferences.getFloat("height", 0);
        this.BMI = sharedPreferences.getFloat("BMI", 0);
        this.BMR = sharedPreferences.getFloat("BMR", 0);
        this.start_weight = sharedPreferences.getFloat("Starting_Weight", 0);

        this.cal_needs = sharedPreferences.getFloat("Cal_Needs", 0);
        this.cur_weight = sharedPreferences.getFloat("Current_Weight", 0);

        this.email = sharedPreferences.getString("Email", null);
        this.phone = sharedPreferences.getString("Phone", null);
        this.mealPlan = sharedPreferences.getInt("MealPlan", 0);
    }


    public void buildDummy(SQLiteDatabase db){
        // postavi upit za izvršavanje
        String query = "SELECT * FROM Users;";
        // postavi kursor da obavi upit
        Cursor c = db.rawQuery(query, null);
        // pomakni kursor na prvi red
        c.moveToFirst();
        // počisti preferencije
        editor.clear();
        // postavi preferencije i user elemente
        editor.putInt("_id", _id = c.getInt(0));
        editor.putString("name", name = c.getString(1));
        editor.putString("password", password = c.getString(2));
        editor.putFloat("weight", weight = c.getFloat(3));
        editor.putFloat("height", height = c.getFloat(4));
        editor.putFloat("BMI", BMI = c.getFloat(5));
        editor.putFloat("BMR", BMR = c.getFloat(6));
        editor.putFloat("Starting_Weight", start_weight = c.getFloat(7));

        editor.putFloat("Cal_Needs", cal_needs = c.getFloat(8));
        editor.putFloat("Current_Weight", cur_weight = c.getFloat(9));

        editor.putString("Email", email = c.getString(10));
        editor.putString("Phone", phone = c.getString(11));
        editor.putInt("MealPlan", mealPlan = c.getInt(12));
        // komitaj promjene u preferencijalne
        editor.commit();
    }
    // makni usera iz preferencijalnih
    public void logOut(){
        editor.clear().commit();
    }

    public void commitUserToDB(){
        DBhandler.InsertUser(this);
        commitUserToPrefs();
    }

    public void commitUserToPrefs(){
        editor.clear();
        editor.putInt("_id", _id);
        editor.putString("name", name);
        editor.putString("password", password);
        editor.putFloat("weight", weight);
        editor.putFloat("height", height);
        editor.putFloat("BMI", BMI);
        editor.putFloat("BMR", BMR);
        editor.putFloat("Starting_Weight", start_weight);

        editor.putFloat("Cal_Needs", cal_needs);
        editor.putFloat("Current_Weight", cur_weight);

        editor.putString("Email", email);
        editor.putString("Phone", phone);
        editor.putInt("MealPlan", mealPlan);
        editor.commit();
    }
    public void setID(int id){this._id = id;}

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) { this.password = password; }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setBMI(float BMI) {
        this.BMI = BMI;
    }

    public void setBMR(float BMR) {
        this.BMR = BMR;
    }

    public void setStart_weight(float start_weight) {
        this.start_weight = start_weight;
    }

    public void setCal_needs(float cal_needs) {
        this.cal_needs = cal_needs;
    }

    public void setCur_weight(float cur_weight) {
        this.cur_weight = cur_weight;
    }

    public void setMealPlan(int mealPlan){this.mealPlan = mealPlan;}


    public String getName() {
        return name;
    }

    public String getPassword() { return password; }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public float getWeight() {
        return weight;
    }

    public float getHeight() {
        return height;
    }

    public float getBMI() {
        return BMI;
    }

    public float getBMR() {
        return BMR;
    }

    public float getStart_weight() {
        return start_weight;
    }

    public float getCal_needs() {
        return cal_needs;
    }

    public float getCur_weight() {
        return cur_weight;
    }

    public int getMealPlan(){return mealPlan;}


}
