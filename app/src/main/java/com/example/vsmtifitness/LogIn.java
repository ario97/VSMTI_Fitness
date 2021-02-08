package com.example.vsmtifitness;



import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LogIn extends AppCompatActivity {





    private static final String TAG = "Login Activity";

    private EditText name, password;
    private ImageView nameGood, nameBad, passBad;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
        


        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        nameGood = (ImageView) findViewById(R.id.nameGoodImage);
        nameBad = (ImageView) findViewById(R.id.nameBadImage);
        passBad = (ImageView) findViewById(R.id.passwordBadImage);
    }
    public void Register (View view){
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    public void Login (View view){
        Log.d(TAG, "Login Button Selected");
        int loginAnswer;
        loginAnswer = MainActivity.dBhandler.checkUserLogin(name.getText().toString(), password.getText().toString());
        switch (loginAnswer){
            case (0):
                Log.d(TAG, "User and password correct");
                finish();
                break;
            case (1):
                Log.d(TAG, "Username was not found");
                Toast toast = Toast.makeText(this, "User Name was not found", Toast.LENGTH_SHORT);
                toast.show();
                nameBad.setVisibility(View.VISIBLE);
                passBad.setVisibility(View.VISIBLE);
                break;
            case (2):
                Log.d(TAG, "Password was not found");
                toast = Toast.makeText(this, "Password is incorrect", Toast.LENGTH_SHORT);
                toast.show();
                nameBad.setVisibility(View.INVISIBLE);
                nameGood.setVisibility(View.VISIBLE);
                break;
        }
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);

    }

}
