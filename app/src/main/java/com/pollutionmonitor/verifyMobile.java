package com.pollutionmonitor;

import static com.google.android.gms.tasks.TaskExecutors.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import com.google.firebase.database.DatabaseReference;
import com.pollutionmonitor.helperclass.*;

public class verifyMobile extends AppCompatActivity {
    private FirebaseAuth mAuth ;

    Button verify ;
    EditText otp ;
    TextView resendOTP ;
    ProgressBar bar ;
    String Vercode ;
    String email , name, mobile , password;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mobile);
        mAuth = FirebaseAuth.getInstance();

        verify = findViewById(R.id.verifyPhone) ;
        otp    = findViewById(R.id.verifyM) ;
        bar    = findViewById(R.id.progressBar) ;
        resendOTP = findViewById(R.id.textView14);

        

        Bundle extras = getIntent().getExtras();
        email = extras.getString("email");
        name = extras.getString("name");
        mobile = extras.getString("mobile");
        password = extras.getString("pass");

        

        sendVerificationCodeToUser(mobile) ;

        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent verifyMob = new Intent(getApplicationContext() , verifyMobile.class);
                verifyMob.putExtra("email" , email);
                verifyMob.putExtra("name" , name);
                verifyMob.putExtra("mobile" , mobile);
                verifyMob.putExtra("pass" , password);
                startActivity(verifyMob);
            }
        });


    }

    private void sendVerificationCodeToUser(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile ,
                60 ,
                TimeUnit.SECONDS ,
                this,
                mCallbacks );

    }
    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Vercode = s ;

            verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String code = otp.getText().toString() ;
                    if(code.isEmpty() || code.length() < 6){
                        otp.setError("Wrong OTP entered.");
                        otp.requestFocus();
                        return;
                    }
                    bar.setVisibility(View.VISIBLE);
                    verifyCode(code);
                }
            });
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                bar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(verifyMobile.this, "failed to verify", Toast.LENGTH_SHORT).show();
        }
    };

    private  void verifyCode(String code) {
        PhoneAuthCredential credential  = PhoneAuthProvider.getCredential(Vercode , code);
//        signInByCredential(credential) ;
        signUpWithEmailAndPassword(email , mobile , name , password);

    }

    private void signUpWithEmailAndPassword(String email, String mobile, String name, String password) {
//        creating a user account with email and password.
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(verifyMobile.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                                    Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            System.out.println(user);
                            System.out.println(user);System.out.println(user);
                            startActivity(new Intent(verifyMobile.this , UserDashboard.class));
                        } else {
                            // If sign in fails, display a message to the user.
//                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(verifyMobile.this, task.getException().getMessage().toString().toString(),
                                    Toast.LENGTH_LONG).show();
//                                    updateUI(null);
                        }


                    }
                });

    }


//    private void signInByCredential(PhoneAuthCredential credential) {
//
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        firebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener(verifyMobile.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//
//                        if(task.isSuccessful()){
//                            Intent i = new Intent(getApplicationContext() , UserDashboard.class);
//                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK) ;
//                            startActivity(i);
//
//                        }
//                        else{
//                            Toast.makeText(verifyMobile.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    }
//                });
//    }
}