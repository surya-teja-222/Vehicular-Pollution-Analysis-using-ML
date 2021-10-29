package com.pollutionmonitor;

import android.content.Intent;
import android.graphics.drawable.DrawableWrapper;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.pollutionmonitor.ui.main.SectionsPagerAdapter;
import com.pollutionmonitor.databinding.ActivityUserDashboardBinding;

public class userDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityUserDashboardBinding binding;
    private FirebaseAuth firebaseAuth;

    private FirebaseDatabase firebaseDatabase;

    TextView name;
    private boolean fetched;
    private DatabaseException err;
    private DataSnapshot result;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar title;


//    User data Fields.
    String uname;
    String email;
    String mobile;

//    user data fields
    String veh_num;
    String veh_class;
    String veh_date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);


        /* --------------HOOKS------------- */

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        title = findViewById(R.id.title);

        setSupportActionBar(title);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, title, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.profile);





        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }




        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("vehicle-details").child(firebaseAuth.getUid());
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println(snapshot);
                veh_num = snapshot.child("vehicleNumber").getValue().toString();
                veh_class = snapshot.child("vehicleClass").getValue().toString();
                veh_date = snapshot.child("dateOfPurchase").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        Getting User Data.
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    System.out.println(snapshot);
                    uname  = snapshot.child("name").getValue().toString();
                    email  = snapshot.child("email").getValue().toString();
                    mobile = snapshot.child("mobile").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home :
            default:
                break;
            case R.id.vehicle:
                Intent intent1 = new Intent(getApplicationContext(),Vehicle_details.class);
                intent1.putExtra("veh_num",veh_num);
                intent1.putExtra("veh_class",veh_class);
                intent1.putExtra("veh_date",veh_date);
                startActivity(intent1);
                break;
            case R.id.profile:

                Intent intent = new Intent(getApplicationContext(),Profile_page.class);
                intent.putExtra("uname" , uname);
                intent.putExtra("email" , email);
                intent.putExtra("mobile" , mobile);
                startActivity(intent);
                break;
            case R.id.about:
            case R.id.nav_social:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://suryateja222.me"));
                startActivity(browserIntent);
                break;
            case R.id.termsAndC:
                Intent browserIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://pollumeter.suryateja222.me"));
                startActivity(browserIntent1);
                break;
            case R.id.nav_logout:
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext() , LoginActivity.class));
                finish();
                break;
            case R.id.settings:

                Intent intent3 = new Intent(getApplicationContext() , settings.class);
                intent3.putExtra("uname" , uname);
                intent3.putExtra("email" , email);
                intent3.putExtra("mobile" , mobile);
                startActivity(intent3);



        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}