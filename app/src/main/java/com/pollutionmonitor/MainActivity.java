package com.pollutionmonitor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        boolean check=checkConnection();
        if(check==true){
            Toast.makeText(
                    this,
                    "Loading...",
                    Toast.LENGTH_LONG).show();
        }

        else{


            Toast.makeText(
                    this,
                    "Check Your Network Connection",
                    Toast.LENGTH_LONG).show();
        }





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
            return false;
        }
        return true;
    }
}