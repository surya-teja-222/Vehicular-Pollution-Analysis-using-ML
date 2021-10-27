package com.pollutionmonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Vehicle_details extends AppCompatActivity {

    Toolbar toolbar;
    TextInputLayout number, vClass, date;
    TextView mNumber, mClass;

    String _number, _class, _date;

    DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);

//        toolbar
        toolbar = (Toolbar)findViewById(com.pollutionmonitor.R.id.toolbar);
        toolbar.setTitle("Vehicle Details");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener((View.OnClickListener)(new View.OnClickListener() {
            public final void onClick(View it) {
                Vehicle_details.super.onBackPressed();
            }
        }));

        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("vehicle-details").child(firebaseAuth.getUid());


        // Hooks
        number  = findViewById(R.id.vehicle_vehicleNumEdit);
        vClass  = findViewById(R.id.vehicle_class_editable);
        date    = findViewById(R.id.vehicle_date_non_editable);
        mNumber = findViewById(R.id.vehicle_vehicleNum);
        mClass  = findViewById(R.id.vehicle_class);


        showAllUserData();


    }
    private void showAllUserData() {

        Intent intent = getIntent();
        _number = intent.getStringExtra("veh_num");
        _class = intent.getStringExtra("veh_class");
        _date = intent.getStringExtra("veh_date");

        mNumber.setText(_number.toUpperCase());
        mClass.setText(_class.toUpperCase());
        date.getEditText().setText(_date);
        vClass.getEditText().setText(_class);
        number.getEditText().setText(_number);


    }

    public void update(View view) {
        if (isNameChanged() || isMobileChanged()) {
            mNumber.setText(number.getEditText().getText().toString());
            Snackbar.make(view, "Profile Updated", Snackbar.LENGTH_LONG).show();
        } else
            Snackbar.make(view, "Nothing to Change, profile Not Updated", Snackbar.LENGTH_LONG).show();
    }


    private boolean isMobileChanged() {
        if (!_class.equals(vClass.getEditText().getText().toString())) {
            reference.child("vehicleClass").setValue(vClass.getEditText().getText().toString());
            return true;

        } else {
            return false;
        }
    }


    private boolean isNameChanged() {
        if (!_number.equals(number.getEditText().getText().toString())) {
            reference.child("vehicleNumber").setValue(number.getEditText().getText().toString());
            return true;

        } else {
            return false;
        }

    }
}