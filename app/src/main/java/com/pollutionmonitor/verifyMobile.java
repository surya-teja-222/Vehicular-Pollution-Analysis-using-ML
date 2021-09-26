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

public class verifyMobile extends AppCompatActivity {
    private FirebaseAuth mAuth ;

    Button verify ;
    EditText otp ;
    ProgressBar bar ;
    String Vercode ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mobile);
        mAuth = FirebaseAuth.getInstance();

        verify = findViewById(R.id.verifyPhone) ;
        otp    = findViewById(R.id.verifyM) ;
        bar    = findViewById(R.id.progressBar) ;

        

        Bundle extras = getIntent().getExtras();
        String email = extras.getString("email");
        String name = extras.getString("name");
        String mobile = extras.getString("mobile");

        

        sendVerificationCodeToUser(mobile) ;


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
        signInByCredential(credential) ;
    }

    private void signInByCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(verifyMobile.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Intent i = new Intent(getApplicationContext() , UserDashboard.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK) ;
                            startActivity(i);

                        }
                        else{
                            Toast.makeText(verifyMobile.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }
}