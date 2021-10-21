package com.pollutionmonitor;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.protobuf.StringValue;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    SharedPreferences onBoardingscreen;
    private int INITIAL_LOADING_TIME = 100; // 5100 originally
    private FirebaseAuth firebaseAuth;

    String uid;
    String[] name;
    String email;
    String[] password;
    String[] mobile;
    String accActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Random rnd = new Random();
        int number = rnd.nextInt(100);
        String percent = number + "%";
        boolean check=checkConnection();
        if(check==true){
            Toast.makeText(
                    this,
                    "Loading..." + percent,
                    Toast.LENGTH_LONG).show();
        }

        else{


            Toast.makeText(
                    this,
                    "Check Your Network Connection",
                    Toast.LENGTH_LONG).show();
        }
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//        if(firebaseUser != null)
//        {
////            uid = firebaseUser.getUid();
////            email = firebaseUser.getEmail();
////
////            Handler handler  = new Handler();
////            handler.postDelayed(new Runnable() {
////                @Override
////                public void run() {
////                    firebaseAuth = FirebaseAuth.getInstance();
////                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
////                    DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child(firebaseAuth.getUid());
////                    databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
////                        @Override
////                        public void onComplete(@NonNull Task<DataSnapshot> task) {
////                            if(!task.isSuccessful()){
////                                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
////
////                            }
////                            else{
////                                System.out.println(String.valueOf(task.getResult().child("name").getValue()));
////                                name[0] = String.valueOf(task.getResult().child("name").getValue());
////                                password[0] = String.valueOf(task.getResult().child("password").getValue());
////                                mobile[0] = String.valueOf(task.getResult().child("mobile").getValue());
////
////                            }
////
////                        }
////                    });
////
////                }
////            },3000);
////
//
//            System.out.println(name+" : "+ uid +" : "+email+" : "+ password +" : "+ mobile );
//
//
//
//        }





        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onBoardingscreen = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);

                boolean isFirstTime = onBoardingscreen.getBoolean("firstTime" , true);
                if(isFirstTime){

                    SharedPreferences.Editor editor = onBoardingscreen.edit();
                    editor.putBoolean("firstTime" , false);
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this , onboarding.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    if(firebaseUser == null)
                    {
                        Intent intent = new Intent(MainActivity.this , LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Intent intent = new Intent(MainActivity.this , userDashboard.class);
                        startActivity(intent);
                        finish();



                    }
                }


            }
        } , INITIAL_LOADING_TIME);



    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected boolean checkConnection(){
        ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = conMan.getActiveNetworkInfo();

        final boolean connected = networkInfo != null
                && networkInfo.isAvailable()
                && networkInfo.isConnected();

        if ( !connected) {
            Toast.makeText(
                    this,
                    "Failed to connect to internet.",

                    Toast.LENGTH_LONG).show();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException ignored) {

            }
            this.finishAffinity();

            return false;
        }
        return true;
    }
}