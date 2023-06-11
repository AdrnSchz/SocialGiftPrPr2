package com.example.socialgift.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.socialgift.R;
import com.example.socialgift.api.APIClient;

public class SignUpActivity extends AppCompatActivity {

    private EditText nameText;
    private EditText surnameText;
    private EditText emailText;
    private EditText passwordText;
    private EditText confirmText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameText = findViewById(R.id.name_signup);
        surnameText = findViewById(R.id.surname_signup);
        emailText = findViewById(R.id.email_signup);
        passwordText = findViewById(R.id.password_signup);
        confirmText = findViewById(R.id.confirm_signup);
        button = findViewById(R.id.button_to_signup);

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        button.setOnClickListener(v -> {
            if (validateFields()) {
                imm.hideSoftInputFromWindow(button.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                APIClient.createUser(this,
                        nameText.getText().toString(), surnameText.getText().toString(),
                        emailText.getText().toString(), passwordText.getText().toString());
                nameText.getText().clear();
                surnameText.getText().clear();
                emailText.getText().clear();
                passwordText.getText().clear();
                confirmText.getText().clear();
            }
        });
    }

    private boolean validateFields() {
        if (nameText.getText().toString().matches("") ||
            surnameText.getText().toString().matches("") ||
            passwordText.getText().toString().matches("")){
            Toast.makeText(this, R.string.fields_empty_error, Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!emailText.getText().toString().contains("@")){
            Toast.makeText(this, R.string.email_field_error, Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!passwordText.getText().toString().matches(confirmText.getText().toString())) {
            Toast.makeText(this, R.string.confirmation_field_error, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}