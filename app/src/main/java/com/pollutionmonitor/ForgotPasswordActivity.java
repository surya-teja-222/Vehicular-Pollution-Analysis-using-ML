package com.pollutionmonitor;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.Window;

import androidx.appcompat.widget.Toolbar;

import org.jetbrains.annotations.NotNull;

import kotlin.jvm.internal.Intrinsics;

public class ForgotPasswordActivity  extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.pollutionmonitor.R.layout.activity_forgot_password);
        toolbar = (Toolbar)findViewById(com.pollutionmonitor.R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener((View.OnClickListener)(new View.OnClickListener() {
         public final void onClick(View it) {
            ForgotPasswordActivity.super.onBackPressed();
         }
        }));
        this.setStatusBarWhite((AppCompatActivity)this);

    }

       public final void setStatusBarWhite(@NotNull AppCompatActivity activity) {
           Intrinsics.checkNotNullParameter(activity, "activity");

           Window var10000 = activity.getWindow();
           Intrinsics.checkNotNullExpressionValue(var10000, "activity.window");
           View var2 = var10000.getDecorView();
           Intrinsics.checkNotNullExpressionValue(var2, "activity.window.decorView");
           var2.setSystemUiVisibility(8192);
           var10000 = activity.getWindow();
           Intrinsics.checkNotNullExpressionValue(var10000, "activity.window");
           ((Window) var10000).setStatusBarColor(-1);
       }
}
