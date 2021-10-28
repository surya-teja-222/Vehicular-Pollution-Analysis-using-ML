package com.pollutionmonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

public class settings extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        toolbar = (Toolbar)findViewById(com.pollutionmonitor.R.id.toolbar);
        toolbar.setTitle("Settings");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener((View.OnClickListener)(new View.OnClickListener() {
            public final void onClick(View it) {
                settings.super.onBackPressed();
            }
        }));



        getFragmentManager().beginTransaction().add(R.id.idFrameLayout, new settingsFragment()).commit();


    }
}