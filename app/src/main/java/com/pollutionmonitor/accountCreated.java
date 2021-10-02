package com.pollutionmonitor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class accountCreated extends AppCompatActivity {


    ProgressBar bar;
    TextView barPer;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_created);

        bar = findViewById(R.id.progressBar3);
        barPer = findViewById(R.id.textView23);
        bar.setVisibility(View.VISIBLE);
        Random rnd = new Random();
        int i = rnd.nextInt(40) +10;
        bar.setProgress(i,true);
        barPer.setText(i+"%");
        int j = rnd.nextInt(40) + i;


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bar.setProgress(j,true);
                barPer.setText(j+"%");

//                intent actions goes here, tbd after creating userDashboard activity.
                startActivity(new Intent(getApplicationContext() , userDashboard.class));
//
//                bar.setVisibility(View.INVISIBLE);
//                findViewById(R.id.textView22).setVisibility(View.INVISIBLE);
//                finish();
            }
        } , 1500);

    }
}


//balance works in signIn , signUp -> password reset , signup with google.