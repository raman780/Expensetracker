package com.raman.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.raman.expensetracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    /*For fetching firebaseAuth Methods*/
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*infalte is used for binding main and signup Activity*/
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        /*For checking that user logined or not*/
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    try {
                        startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                        finish();
                    } catch (Exception e) {

                    }
                }
            }
        });

        binding.gotoSignupScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent for going to SignUp Activty From MainActivity*/
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                /*for Low speed device in case of app crash while an activity launches*/
                try {
                    startActivity(intent);
                } catch (Exception e) {

                }
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailLogin.getText().toString().trim();
                String password = binding.passwordLogin.getText().toString().trim();
                /*for validation*/
                if (email.length() <= 0 || password.length() <= 0) {
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                try {
                                    startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                                } catch (Exception e) {

                                }

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}