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

public class Profile_page extends AppCompatActivity {

    Toolbar toolbar;
    TextInputLayout name, email, phone;
    TextView mName, mEmail;

    String _name, _email, _mobile;

    DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
//        toolbar
        toolbar = (Toolbar)findViewById(com.pollutionmonitor.R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener((View.OnClickListener)(new View.OnClickListener() {
            public final void onClick(View it) {
                Profile_page.super.onBackPressed();
            }
        }));


        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getUid());


        // Hooks
        name = findViewById(R.id.profile_nameField);
        email = findViewById(R.id.profile_emailField);
        phone = findViewById(R.id.profile_mobileField);
        mName = findViewById(R.id.profile_name);
        mEmail = findViewById(R.id.profile_email);


        //ShowAllData
        showAllUserData();


    }

    private void showAllUserData() {

        Intent intent = getIntent();
        _name = intent.getStringExtra("uname");
        _email = intent.getStringExtra("email");
        _mobile = intent.getStringExtra("mobile");

        mName.setText(_name.toUpperCase());
        mEmail.setText(_email.toUpperCase());
        name.getEditText().setText(_name);
        email.getEditText().setText(_email);
        phone.getEditText().setText(_mobile);


    }

    public void update(View view) {
        if (isNameChanged() || isMobileChanged()) {
            mName.setText(name.getEditText().getText().toString());
            Snackbar.make(view, "Profile Updated", Snackbar.LENGTH_LONG).show();
        } else
            Snackbar.make(view, "Nothing to Change, profile Not Updated", Snackbar.LENGTH_LONG).show();
    }


    private boolean isMobileChanged() {
        if (!_mobile.equals(phone.getEditText().getText().toString())) {
            reference.child("mobile").setValue(phone.getEditText().getText().toString());
            return true;

        } else {
            return false;
        }
    }


    private boolean isNameChanged() {
        if (!_name.equals(name.getEditText().getText().toString())) {
            reference.child("name").setValue(name.getEditText().getText().toString());
            return true;

        } else {
            return false;
        }

    }
}