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


        System.out.println(binding.getRoot().toString());


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child(firebaseAuth.getUid());


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
                startActivity(intent1);
                break;
            case R.id.profile:
                Intent intent = new Intent(getApplicationContext(),Profile_page.class);
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
                startActivity(new Intent(getApplicationContext() , settings.class));
                finish();


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}