package com.pollutionmonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pollutionmonitor.helperclass.SliderAdapter;

public class onboarding extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dotsLayout;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    Button login;
    Button signup;
    Animation animation;
    int current ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.onboardingactivity);

//        hooks
        viewPager = findViewById(R.id.pager);
        dotsLayout = findViewById(R.id.dots);
        login = findViewById(R.id.loginbtn);
        signup = findViewById(R.id.signupbtn);
//        Call adapter
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
    }

    public void skip(View view){
//        startActivity(new Intent(this , UserDashboard.class));
        viewPager.setCurrentItem(2);
    }
    public void next(View view){
        viewPager.setCurrentItem(current + 1);
    }

    public void setLogin(View view){
        startActivity(new Intent(this , LoginActivity.class));
        finish();
    }
    public void setSignUp(View view){
        startActivity(new Intent(this , SignupActivity.class));
        finish();
    }

    private void addDots(int position) {

        dots = new TextView[3];
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(60);
            dots[i].setTextColor(getResources().getColor(R.color.dot));
            dots[i].setPadding(20, 20, 20, 20);
            dotsLayout.addView(dots[i]);
        }
        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.white));
        }


    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            current = position;
            if (position == 0) {
                login.setVisibility(View.INVISIBLE);
                signup.setVisibility(View.INVISIBLE);
            } else if (position == 1) {
                login.setVisibility(View.INVISIBLE);
                signup.setVisibility(View.INVISIBLE);
            } else if (position == 2) {
                animation = AnimationUtils.loadAnimation(onboarding.this ,R.anim.button_animation);
                animation = AnimationUtils.loadAnimation(onboarding.this ,R.anim.button_animation);
                login.setAnimation(animation);
                signup.setAnimation(animation);
                login.setVisibility(View.VISIBLE);
                signup.setVisibility(View.VISIBLE);

            }


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}