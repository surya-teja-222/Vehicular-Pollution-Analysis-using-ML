package com.pollutionmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class postSignUp1 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, day, month;
    private int sYear, sDay, sMonth;
    private String choosenItem;
    private String selectedDate ="";
    String checked;
    CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_sign_up1);
//      check box :
        checkBox = findViewById(R.id.checkBox);

//        dateView = findViewById(R.id.textView14);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<String>();
        categories.add("Choose");
        categories.add("Motorcycles and scooters");
        categories.add("Car/Jeep/Van");
        categories.add("Bus/Truck");
        categories.add("Others");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

//        add a code here to save all the data to the Firestore database.
//        important task due!!!
//        after completing this page, send the user to the page where he need to upload his RC pic for registration.


        ImageView cal = findViewById(R.id.button4);
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) {
                    checked = "true";
                } else
                    checked = "false";

                if (choosenItem.equals("Choose")) {
                    Toast.makeText(postSignUp1.this, "Please select Vehicle Type.", Toast.LENGTH_LONG).show();
                } else if (selectedDate.isEmpty() | selectedDate.equals("")) {
                    Toast.makeText(postSignUp1.this, "Choose a valid date.", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), postSignUp2.class);
                    intent.putExtra("checked", checked);
                    intent.putExtra("Date of Buy", selectedDate);
                    intent.putExtra("Vehicle Class", choosenItem);
                    startActivity(intent);
                }
            }
        });

//        startActivity(new Intent(getApplicationContext() , postSignUp2.class));


    }


    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {

            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
//                    showDate(arg1, arg2+1, arg3);
                    sDay = arg3;
                    sMonth = arg2 + 1;
                    sYear = arg1;
                    System.out.println(sDay + "/" + sMonth + "/" + sYear);

                    selectedDate = sDay + "/" + sMonth + "/" + sYear;

                }
            };

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();

        choosenItem = item;
        if (choosenItem.equals("Choose")) {
            Toast.makeText(postSignUp1.this, "Please choose a Type!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}