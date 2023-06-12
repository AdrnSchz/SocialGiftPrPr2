package com.example.socialgift.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.socialgift.R;
import com.example.socialgift.activities.InitialScreenActivity;
import com.example.socialgift.activities.MainActivity;
import com.example.socialgift.api.APIClient;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        AppCompatActivity thisActivity = (AppCompatActivity) getContext();

        ImageButton backButton = view.findViewById(R.id.settings_arrow_back);
        TextInputEditText userNameField = view.findViewById(R.id.settings_username);
        TextInputEditText emailField = view.findViewById(R.id.settings_email);
        TextInputEditText passwordField = view.findViewById(R.id.settings_password);
        TextInputEditText imageField = view.findViewById(R.id.settings_photo);
        Button saveButton = view.findViewById(R.id.settings_save);
        Button logOutButton = view.findViewById(R.id.settings_logout);

        backButton.setOnClickListener(v ->{
            thisActivity.getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container_view, new ProfileFragment()).commit();
        });

        InputMethodManager imm = (InputMethodManager)thisActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        saveButton.setOnClickListener(v -> {
            imm.hideSoftInputFromWindow(saveButton.getWindowToken(),
                    InputMethodManager.RESULT_UNCHANGED_SHOWN);
            editUser(userNameField.getText().toString(), emailField.getText().toString(),
                    passwordField.getText().toString(), imageField.getText().toString());
            userNameField.getText().clear();
            emailField.getText().clear();
            passwordField.getText().clear();
            imageField.getText().clear();
        });

        logOutButton.setOnClickListener(v -> {
            MainActivity.logOut();
            switchToInitial(thisActivity);
        });
        return view;
    }

    private void editUser(String name, String email, String password, String imageLink) {
        if (name.matches("")){
            name = MainActivity.getName();
        }
        else {
            MainActivity.updateName(name);
        }
        if (email.matches("")){
            email = MainActivity.getEmail();
        }
        else {
            MainActivity.updateEmail(email);
        }
        if (password.matches("")){
            password = MainActivity.getPassword();
        }
        if (imageLink.matches("")){
            imageLink = MainActivity.getImageLink();
        }
        else {
            MainActivity.updateImageLink(imageLink);
        }

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("name", name);
            requestBody.put("last_name", MainActivity.getLastName());
            requestBody.put("email", email);
            requestBody.put("password", MainActivity.getPassword());
            requestBody.put("image", imageLink);
        } catch (JSONException e) {
            Toast.makeText(getContext(), "Error updating profile", Toast.LENGTH_SHORT).show();
            return;
        }

        APIClient.makePUTRequest(getContext(), "users", requestBody,
                response -> {
                    try {
                        response.getString("id");
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Error updating profile", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(getContext(), "Error updating profile", Toast.LENGTH_SHORT).show();
                });
    }

    private void switchToInitial(AppCompatActivity thisActivity) {
        Intent gotoInitial = new Intent(thisActivity, InitialScreenActivity.class);
        gotoInitial.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        gotoInitial.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        thisActivity.startActivity(gotoInitial);
        thisActivity.overridePendingTransition (0, 0);
    }

}