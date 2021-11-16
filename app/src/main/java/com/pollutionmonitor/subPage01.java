package com.pollutionmonitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pollutionmonitor.databinding.FragmentUserDashboardBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link subPage01#newInstance} factory method to
 * create an instance of this fragment.
 */
public class subPage01 extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    TextView name ;
    TextView vehicleNum;
    Button signOut;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public subPage01() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment subPage01.
     */
    // TODO: Rename and change types and number of parameters
    public static subPage01 newInstance(String param1, String param2) {
        subPage01 fragment = new subPage01();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.fragment_sub_page01, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        name = view.findViewById(R.id.tvName);

        name.setVisibility(View.INVISIBLE);
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setVisibility(View.VISIBLE);
                name.setText("Welcome , "+snapshot.child("name").getValue().toString().toUpperCase());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        vehicleNum = view.findViewById(R.id.textView7);
        DatabaseReference databaseReference2 = firebaseDatabase.getReference("vehicle-details").child(firebaseAuth.getUid());
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vehicleNum.setVisibility(View.VISIBLE);
                vehicleNum.setText("Your Vehicle : "+snapshot.child("vehicleNumber").getValue().toString().toUpperCase());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        LinearLayout linearLayout1 = view.findViewById(R.id.id_green);
        LinearLayout linearLayout2 = view.findViewById(R.id.id_red);
        LinearLayout linearLayout3 = view.findViewById(R.id.id_blue);

        Random rnd = new Random();
        int number = rnd.nextInt(2);
        if(number == 0)
            linearLayout1.setVisibility(View.VISIBLE);
        else if (number == 1)
            linearLayout2.setVisibility(View.VISIBLE);
        else
            linearLayout3.setVisibility(View.VISIBLE);

        return view;
    }


}


