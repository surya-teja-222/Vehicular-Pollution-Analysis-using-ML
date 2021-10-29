package com.pollutionmonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class settings extends AppCompatActivity {
    Toolbar toolbar;

//    details from intent
    String _name , _email , _mobile;

//    hooks
    TextView name;
    TextView email;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        toolbar = (Toolbar)findViewById(com.pollutionmonitor.R.id.toolbar);
        toolbar.setTitle("Account & Settings");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener((View.OnClickListener)(new View.OnClickListener() {
            public final void onClick(View it) {
                settings.super.onBackPressed();
            }
        }));



        getFragmentManager().beginTransaction().add(R.id.idFrameLayout, new settingsFragment()).commit();


//        hooks
        name = findViewById(R.id.profile_name);
        email =findViewById(R.id.profile_email);
        layout = findViewById(R.id.relative_layout);
        setProfile();

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Profile_page.class);
                intent.putExtra("uname" , _name);
                intent.putExtra("email" , _email);
                intent.putExtra("mobile" , _mobile);
                startActivity(intent);
            }
        });



    }

    private void setProfile() {

        Intent intent = getIntent();
        _name = intent.getStringExtra("uname");
        _email = intent.getStringExtra("email");
        _mobile = intent.getStringExtra("mobile");

        name.setText(_name);
        email.setText(_email);
    }


}