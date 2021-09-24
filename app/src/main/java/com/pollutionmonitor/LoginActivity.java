package com.pollutionmonitor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import kotlin.jvm.internal.Intrinsics;

public class LoginActivity extends AppCompatActivity {

    Button forgotPasswordB ;
    Button signUpB ;
    Button signIn;

    TextInputEditText username ;
    EditText password;

    SharedPreferences isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setStatusBarTransparent(this);

//        basic verification whether the user is logged in or not:
        isLoggedIn = getSharedPreferences("isLoggedIn" , MODE_PRIVATE);
        boolean isLogged = isLoggedIn.getBoolean("isLoggedIn" , false);
        if(isLogged){
            startActivity(new Intent(this , UserDashboard.class));
            finish();
        }


//        INTENTS
        forgotPasswordB = findViewById(R.id.button_forgot_password);
        signUpB =findViewById(R.id.button_signup);
        forgotPasswordB.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Bro , u Clicked Password Reset.");
                startActivity(new Intent(v.getContext() , ForgotPasswordActivity.class));
            }
        });
        signUpB.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Bro, U clicked SIgnUp!");
                startActivity(new Intent(v.getContext() , SignupActivity.class));
            }
        });

//        Credential Verification.

        username = (TextInputEditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);
        signIn = findViewById(R.id.button_signin);

        signIn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                System.out.println("Bro , u Tried Logging In.\n Let me check if credentials are matching");
                String uname = "surya";
                String enteredEmail = username.getText().toString();
                String enteredPassword = password.getText().toString();
                Boolean boo = (enteredPassword.equals(uname)) ;
                Boolean boo2 = (enteredEmail.equals(uname)) ;

                if(boo & boo2){
                    System.out.println("Bro , Success");
                    isLoggedIn = getSharedPreferences("isLoggedIn" , MODE_PRIVATE);
                    SharedPreferences.Editor editor = isLoggedIn.edit();
                    editor.putBoolean("isLoggedIn" , true);
                    editor.commit();
                    startActivity(new Intent(v.getContext() , UserDashboard.class));
                    finish();


                }
                else{
                    System.out.println("Bro , incorrect e-mail /Password");
                    System.out.println("You Entered: Email :" + username.getText() +" , password :"+password.getText());
                }
            }
        });



    }


    private final void setStatusBarTransparent(AppCompatActivity activity) {
         activity.getWindow().addFlags(67108864);
         Window var10000 = activity.getWindow();
         Intrinsics.checkNotNullExpressionValue(var10000, "activity.window");
         View var2 = var10000.getDecorView();
         Intrinsics.checkNotNullExpressionValue(var2, "activity.window.decorView");
         var2.setSystemUiVisibility(8192);
         var10000 = activity.getWindow();
         Intrinsics.checkNotNullExpressionValue(var10000, "activity.window");
         var10000.setStatusBarColor(-1);

   }

}
