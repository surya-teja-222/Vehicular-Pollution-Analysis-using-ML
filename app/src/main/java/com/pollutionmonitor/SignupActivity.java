package com.pollutionmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    Toolbar toolbar;

    private FirebaseAuth mAuth;


    TextInputEditText email ;
    TextInputEditText name ;
    TextInputEditText mobile ;
    EditText passOne ;
    EditText passTwo ;

    Button signUpBtn ;

    TextView errorLine ;


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(SignupActivity.this , UserDashboard.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.pollutionmonitor.R.layout.activity_signup);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        mAuth = FirebaseAuth.getInstance();

//        initialise all fields ;
        email = findViewById(R.id.et_email);
        name = findViewById(R.id.et_username);
        mobile = findViewById(R.id.et_phone);
        passOne =  findViewById(R.id.et_password);
        passTwo = findViewById(R.id.et_confirm_password);
        signUpBtn = findViewById(R.id.button_signin);
        errorLine = findViewById(R.id.textView5);



        signUpBtn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
//        Verifying that no field is null ;
                if(email.getText().toString().equals(null))   {
                    errorLine.setText("*Enter a valid Email ");
                    errorLine.setVisibility(View.VISIBLE);
                }
                else if(name.getText().toString().equals(null)){
                    errorLine.setText("*Enter a valid Name ");
                    errorLine.setVisibility(View.VISIBLE);
                }
                else if(mobile.getText().toString().equals(null)){
                    errorLine.setText("*Enter a valid Name ");
                    errorLine.setVisibility(View.VISIBLE);
                }
                else if(passOne.getText().toString().equals(null)){
                    errorLine.setText("*Enter a valid Password ");
                    errorLine.setVisibility(View.VISIBLE);
                }
                else if(passTwo.getText().toString().equals(null)){
                    errorLine.setText("*Enter Password 2nd Time");
                    errorLine.setVisibility(View.VISIBLE);
                }
                else if(! passOne.getText().toString().equals(passTwo.getText().toString())){
                    errorLine.setText("*Password Mismatch ");
                    errorLine.setVisibility(View.VISIBLE);
                }
                else{
                    errorLine.setVisibility(View.GONE);
                    signUpBtn.setText("Validating...");
                    Intent verifyMob = new Intent(getApplicationContext() , verifyMobile.class);
                    verifyMob.putExtra("email" , email.getText().toString());
                    verifyMob.putExtra("name" , name.getText().toString());
                    verifyMob.putExtra("mobile" , mobile.getText().toString());
                    verifyMob.putExtra("pass" , passOne.getText().toString());
                    startActivity(verifyMob);
                    signUpBtn.setText("Sign Up");
                }


//                mAuth.createUserWithEmailAndPassword(email.getText().toString(), passOne.getText().toString())
//                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
//
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
//
//                                    FirebaseUser user = mAuth.getCurrentUser();
////                                    updateUI(user);
//                                } else {
//                                    // If sign in fails, display a message to the user.
////                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                                    Toast.makeText(SignupActivity.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
//                                    updateUI(null);
//                                }
//                            }
//                        });

            }
        });





    }

    public void updateUI(FirebaseUser account){

        if(account != null){
            Toast.makeText(this,"You Signed In successfully",Toast.LENGTH_LONG).show();


        }else {
            Toast.makeText(this,"You Didnt signed in",Toast.LENGTH_LONG).show();
        }

    }
}
