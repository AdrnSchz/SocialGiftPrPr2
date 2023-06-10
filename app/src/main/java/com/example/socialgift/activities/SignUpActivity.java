package com.example.socialgift.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.socialgift.R;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText emailText = findViewById(R.id.email_signup);
        EditText passwordText = findViewById(R.id.password_signup);
        EditText confirmText = findViewById(R.id.confirm_signup);
        Button button = findViewById(R.id.button_to_signup);

        button.setOnClickListener(v -> {
            switchToInitialScreen();
        });
    }

    private void switchToInitialScreen() {
        Intent gotoInitial = new Intent(this, InitialScreenActivity.class);
        gotoInitial.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        gotoInitial.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(gotoInitial);
        overridePendingTransition (0, 0);
    }
}