package com.example.socialgift.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.socialgift.R;
import com.example.socialgift.api.APIClient;
import com.example.socialgift.fragments.ProfileFragment;

public class LogInActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Button button = findViewById(R.id.button_login);
        EditText emailText = findViewById(R.id.email_login);
        EditText passwordText = findViewById(R.id.password_login);

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        button.setOnClickListener(v -> {
            imm.hideSoftInputFromWindow(button.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
            APIClient.authenticate(this, emailText.getText().toString(),
                                    passwordText.getText().toString());
            emailText.getText().clear();
            passwordText.getText().clear();
        });
    }
}
