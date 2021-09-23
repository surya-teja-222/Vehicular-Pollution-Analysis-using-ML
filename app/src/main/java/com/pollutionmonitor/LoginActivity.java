package com.pollutionmonitor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import kotlin.jvm.internal.Intrinsics;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setStatusBarTransparent(this);
    }

    private final void setStatusBarTransparent(AppCompatActivity activity) {
         activity.getWindow().addFlags(67108864);
         Window var10000 = activity.getWindow();
         Intrinsics.checkNotNullExpressionValue(var10000, "activity.window");
         View var2 = var10000.getDecorView();
         Intrinsics.checkNotNullExpressionValue(var2, "activity.window.decorView");
         var2.setSystemUiVisibility(8192);
         var10000 = activity.getWindow();
         Intrinsics.checkNotNullExpressionValue(var10000, "activity.window");
         var10000.setStatusBarColor(-1);

   }

//   public final void onClick(View view) {
//      Intrinsics.checkNotNullParameter(view, "view");
//      if (view.getId() == R.id.1000186) {
//         this.startActivity(new Intent((Context)this, SignupActivity.class));
//      }
//      else if (view.getId() == 1000417) {
//         this.startActivity(new Intent((Context)this, ForgotPasswordActivity.class));
//      }
//
//   }
}
