package com.example.socialgift.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.socialgift.R;

public class LogInActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Button button = findViewById(R.id.button_login);
        EditText emailText = findViewById(R.id.email_login);
        EditText passwordText = findViewById(R.id.password_login);

        button.setOnClickListener(v -> {
            switchToMain();
        });
    }

    private void switchToMain() {
        Intent gotoMain = new Intent(this, MainActivity.class);
        gotoMain.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        gotoMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(gotoMain);
        overridePendingTransition (0, 0);
    }
}
