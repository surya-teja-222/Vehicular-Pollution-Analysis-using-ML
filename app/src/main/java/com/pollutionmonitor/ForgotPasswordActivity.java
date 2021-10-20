package com.pollutionmonitor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import kotlin.jvm.internal.Intrinsics;

public class ForgotPasswordActivity  extends AppCompatActivity {


    TextInputEditText email ;
    Button send;
    Toolbar toolbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.pollutionmonitor.R.layout.activity_forgot_password);
        toolbar = (Toolbar)findViewById(com.pollutionmonitor.R.id.toolbar);
        toolbar.setTitle("Password Reset");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener((View.OnClickListener)(new View.OnClickListener() {
         public final void onClick(View it) {
            ForgotPasswordActivity.super.onBackPressed();
         }
        }));
        this.setStatusBarWhite((AppCompatActivity)this);
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.et_email);
        send  = findViewById(R.id.button_send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.sendPasswordResetEmail(email.getText().toString()
                ).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Snackbar.make(v, "Email sent Successfully", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }else{
                            Snackbar.make(v, task.getException().getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }


                    }
                });
            }
        });


    }





       public final void setStatusBarWhite(@NotNull AppCompatActivity activity) {
           Intrinsics.checkNotNullParameter(activity, "activity");

           Window var10000 = activity.getWindow();
           Intrinsics.checkNotNullExpressionValue(var10000, "activity.window");
           View var2 = var10000.getDecorView();
           Intrinsics.checkNotNullExpressionValue(var2, "activity.window.decorView");
           var2.setSystemUiVisibility(8192);
           var10000 = activity.getWindow();
           Intrinsics.checkNotNullExpressionValue(var10000, "activity.window");
           ((Window) var10000).setStatusBarColor(-1);
       }
}
