package com.pollutionmonitor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pollutionmonitor.helperclass.urlModel;
import com.pollutionmonitor.helperclass.vehiclePrimary;

public class postSignUp2 extends AppCompatActivity {

    private String vehicleClass, isVehicleMaintained, dateOfPurchase, vehicleNumber  ;
    Button picker ;
    EditText num;
    ProgressBar bar;

    FirebaseDatabase rootNode;
    DatabaseReference reference ;
    FirebaseAuth mAuth;
    StorageReference ref;

    Boolean success = false;

    private Uri uri;


    int requestCode =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_sign_up2);

        picker = findViewById(R.id.button2);
        num = findViewById(R.id.editTextTextPersonName);
        bar = findViewById(R.id.progressBar2);
        bar.setVisibility(View.INVISIBLE);
//        getting data from previous intents
        Bundle extras = getIntent().getExtras();
        vehicleClass = extras.getString("Vehicle Class");
        isVehicleMaintained = extras.getString("checked");
        dateOfPurchase = extras.getString("Date of Buy");


        TextView tAndC = findViewById(R.id.textView20);
        tAndC.setText(Html.fromHtml("'Your data will be processed according to <a href=\"www.suryateja222.me\"><u>terms and conditions</u></a> of Pollumeter'"));

        tAndC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://pollumeter.suryateja222.me"));
                startActivity(browserIntent);
            }
        });


        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vehicleNumber = num.getText().toString();

                addDetailsToDatabase(vehicleClass , isVehicleMaintained , dateOfPurchase , vehicleNumber);
//                REQUIRED:
//                ADD THE IMAGE TO STORAGE AND LINK TO DATABASE.
//                INTENT TO NEXT SCREEN.
                if(uri != null){
                    uploadToFirebase(uri);
                    if(success = true){
                        Intent intent = new Intent(getApplicationContext(), accountCreated.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please select a file", Toast.LENGTH_SHORT).show();
                }

            }


        });






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
            uri = intent.getData() ;

        }
    }

    private void filePicker() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent , requestCode );
    }

    private void addDetailsToDatabase(String vehicleClass, String isVehicleMaintained, String dateOfPurchase, String vehicleNumber) {
        vehiclePrimary vehicle = new vehiclePrimary(vehicleClass, isVehicleMaintained, dateOfPurchase, vehicleNumber );
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("vehicle-details");
        mAuth = FirebaseAuth.getInstance();
        String uid  = mAuth.getUid();
        reference.child(uid).setValue(vehicle);


    }

    private void uploadToFirebase(Uri uri) {
        mAuth = FirebaseAuth.getInstance();
        String uid  = mAuth.getUid();
        ref = FirebaseStorage.getInstance().getReference().child(uid+"."+getfileext(uri));
        ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        bar.setVisibility(View.GONE);
                        urlModel  url = new urlModel(uri.toString());
                        rootNode = FirebaseDatabase.getInstance();
                        reference = rootNode.getReference("user-rc");
                        reference.child(uid).setValue(url);
                        Toast.makeText(getApplicationContext(), "Success!!", Toast.LENGTH_SHORT).show();
                        success = true;
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                bar.setVisibility(View.VISIBLE);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed To Upload data,Try Again!", Toast.LENGTH_SHORT).show();
                bar.setVisibility(View.INVISIBLE);
            }
        });

    }
    private String getfileext(Uri uri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

}


//to database:
//Vehicle details :     1) Vehicle class    2)Vehicle maintained   3)date Of purchase   4)Vehicle Number
//Vehicle RC:           1)Pic of Vehicle RC.