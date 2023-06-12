package com.example.socialgift.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.socialgift.R;
import com.example.socialgift.fragments.GiftFragment;
import com.example.socialgift.fragments.HomeFragment;
import com.example.socialgift.fragments.ProfileFragment;
import com.example.socialgift.fragments.SearchFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private static int id;
    private static String name;
    private static String lastName;
    private static String email;
    private static String imageLink;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationBarView bar = findViewById(R.id.bottom_navigation);

        bar.setOnItemSelectedListener(item -> {

            Fragment selection = switch (item.getItemId()) {
                case R.id.search_page -> new SearchFragment();
                case R.id.gift_page -> new GiftFragment();
                case R.id.profile_page -> new ProfileFragment();
                default -> new HomeFragment();
            };

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, selection).commit();
            return true;
        });
    }

    public static void updateUser(int newId, String newName, String newLastName, String newEmail, String newImageLink){
        id = newId;
        name = newName;
        lastName = newLastName;
        email = newEmail;
        imageLink = newImageLink;
    }

    public static void updateImageLink(String newImageLink){
        imageLink = newImageLink;
    }

    public static int getId() {
        return id;
    }

    public static String getName() {
        return name;
    }

    public static String getLastName() {
        return lastName;
    }

    public static String getEmail() {
        return email;
    }

    public static String getImageLink() {
        return imageLink;
    }
}