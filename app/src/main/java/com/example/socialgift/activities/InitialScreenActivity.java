package com.example.socialgift.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.socialgift.R;

public class InitialScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_screen);

        Button signUpButton = findViewById(R.id.button_to_signup);
        Button loginButton = findViewById(R.id.button_to_login);

        signUpButton.setOnClickListener(v -> {
            switchToSignUp();
        });

        loginButton.setOnClickListener(v -> {
            switchToLogin();
        });
    }

    private void switchToLogin() {
        Intent gotoLogin = new Intent(this, LogInActivity.class);
        gotoLogin.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        gotoLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(gotoLogin);
        overridePendingTransition (0, 0);
    }

    private void switchToSignUp() {
        Intent gotoSignup = new Intent(this, SignUpActivity.class);
        gotoSignup.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        gotoSignup.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(gotoSignup);
        overridePendingTransition (0, 0);
    }
}