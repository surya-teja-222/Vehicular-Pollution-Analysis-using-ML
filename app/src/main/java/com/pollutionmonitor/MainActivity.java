package com.pollutionmonitor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    SharedPreferences onBoardingscreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Random rnd = new Random();
        int number = rnd.nextInt(100);
        String percent = number + "%";
        boolean check=checkConnection();
        if(check==true){
            Toast.makeText(
                    this,
                    "Loading..." + percent,
                    Toast.LENGTH_LONG).show();
        }

        else{


            Toast.makeText(
                    this,
                    "Check Your Network Connection",
                    Toast.LENGTH_LONG).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onBoardingscreen = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);

                boolean isFirstTime = onBoardingscreen.getBoolean("firstTime" , true);
                if(isFirstTime){

                    SharedPreferences.Editor editor = onBoardingscreen.edit();
                    editor.putBoolean("firstTime" , false);
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this , onboarding.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(MainActivity.this , userDashboard.class);
                    startActivity(intent);
                    finish();
                }


            }
        } , 5100);



    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected boolean checkConnection(){
        ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = conMan.getActiveNetworkInfo();

        final boolean connected = networkInfo != null
                && networkInfo.isAvailable()
                && networkInfo.isConnected();

        if ( !connected) {
            Toast.makeText(
                    this,
                    "Failed to connect to internet.",

                    Toast.LENGTH_LONG).show();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException ignored) {

            }
            this.finishAffinity();

            return false;
        }
        return true;
    }
}