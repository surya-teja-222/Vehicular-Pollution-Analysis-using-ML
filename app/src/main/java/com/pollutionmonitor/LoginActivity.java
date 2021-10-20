package com.pollutionmonitor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pollutionmonitor.helperclass.*;
import org.jetbrains.annotations.NotNull;

import kotlin.jvm.internal.Intrinsics;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN =120 ;
    Button forgotPasswordB ;
    Button signUpB ;
    Button signIn;

    TextInputEditText username ;
    EditText password;
    TextView incorrectPassword ;

    SharedPreferences isLoggedIn;

    FirebaseDatabase firebaseDatabase;

    private FirebaseAuth mAuth ;
    String pNum;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(LoginActivity.this , userDashboard.class));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setStatusBarTransparent(this);

        mAuth = FirebaseAuth.getInstance() ;


//        INTENTS
        forgotPasswordB = findViewById(R.id.button_forgot_password);
        signUpB =findViewById(R.id.button_signup);
        forgotPasswordB.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Bro , u Clicked Password Reset.");
                startActivity(new Intent(v.getContext() , ForgotPasswordActivity.class));


            }

        });
        signUpB.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Bro, U clicked SIgnUp!");
                startActivity(new Intent(v.getContext() , SignupActivity.class));
            }
        });

//        Credential Verification.

        username = (TextInputEditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);
        signIn = findViewById(R.id.button_signin);

        signIn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateEmail() && validatePassword()   )

                    mAuth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Intent i = new Intent(view.getContext() , userDashboard.class);
                                        i.putExtra("existing" ,true);
                                        startActivity(i);


//                                    updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Toast.makeText(LoginActivity.this, task.getException().getMessage().toString(),
                                                Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
            }
        });

        createRequest();

        findViewById(R.id.googleLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });



    }

    private boolean validateEmail() {
        String dat = username.getText().toString();
        if(dat ==null){
            username.setError("Email can't be null");
            return false;
        }
        if(dat.isEmpty()){
            username.setError("Email can't be null");
            return false;
        }
        username.setError(null);
        return true;
    }

    private boolean validatePassword() {
        String dat = password.getText().toString();
        if(dat ==null){
            password.setError("Email can't be null");

            return false;
        }
        if(dat.isEmpty()){
            password.setError("Email can't be null");
            return false;
        }
        password.setError(null);
        return true;

    }

    private void createRequest() {
        // Google signIn pop up builder
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1094361772158-5ffer5l7k3sni5bm6r4l780thp31hoq5.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, e.getMessage().toString() , Toast.LENGTH_LONG ).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "wait, finding what's wrong", Toast.LENGTH_LONG).show();
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent( getApplicationContext(), userDashboard.class));

                        } else {
                            Toast.makeText(LoginActivity.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private final void setStatusBarTransparent(@NonNull AppCompatActivity activity) {
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

}
