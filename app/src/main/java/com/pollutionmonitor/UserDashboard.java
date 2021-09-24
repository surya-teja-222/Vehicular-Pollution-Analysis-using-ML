package com.pollutionmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;



public class UserDashboard extends AppCompatActivity {


    SharedPreferences isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        isLoggedIn = getSharedPreferences("isLoggedIn" , MODE_PRIVATE);
        boolean isLogged = isLoggedIn.getBoolean("isLoggedIn" , false);
        if(!isLogged){
            Intent intent = new Intent(this ,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}