package com.pollutionmonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class postSignUp2 extends AppCompatActivity {

    Button picker ;
    int requestCode =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_sign_up2);

        picker = findViewById(R.id.button2);




        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(postSignUp2.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
                    //when permission not granted
                    //request for cam permission
                    ActivityCompat.requestPermissions(postSignUp2.this
                        , new String[]{Manifest.permission.CAMERA} , 1);
                }
                else{
                    filePicker();
                }
            }


        });




    }
    public void onActivityResult(int requestCode, int rescode , Intent intent) {
        super.onActivityResult(requestCode, rescode, intent);
        Context context = getApplicationContext();
        if(requestCode == requestCode && rescode  == Activity.RESULT_OK){
            if(intent == null){
                return;

            }
            Uri uri = intent.getData() ;
            Toast.makeText(getApplicationContext(), uri.getPath(), Toast.LENGTH_SHORT).show();
        }
    }

    private void filePicker() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent , requestCode );
    }

}