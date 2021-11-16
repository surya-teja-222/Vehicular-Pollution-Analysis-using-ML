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


    androidx.appcompat.widget.AppCompatButton button;


    androidx.appcompat.widget.Toolbar title;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_created);

        title = findViewById(R.id.title);
        title = findViewById(R.id.title);

        setSupportActionBar(title);

        button = findViewById(R.id.butt) ;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(accountCreated.this , userDashboard.class));
                finish();
            }
        });





    }
}


//balance works in signIn , signUp -> password reset , signup with google.