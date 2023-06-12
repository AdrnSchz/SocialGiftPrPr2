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
import com.example.socialgift.fragments.StatisticsFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private static int id;
    private static String name;
    private static String lastName;
    private static String email;
    private static String password;
    private static String imageLink;
    private SearchFragment searchFragment;
    private GiftFragment giftFragment;
    private ProfileFragment profileFragment;
    private HomeFragment homeFragment;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationBarView bar = findViewById(R.id.bottom_navigation);

        searchFragment = new SearchFragment();
        giftFragment = new GiftFragment();
        profileFragment = new ProfileFragment();
        homeFragment = new HomeFragment();

        bar.setOnItemSelectedListener(item -> {

            Fragment selection = switch (item.getItemId()) {
                case R.id.search_page -> searchFragment;
                case R.id.gift_page -> giftFragment;
                case R.id.profile_page -> profileFragment;
                default -> homeFragment;
            };
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, selection).commit();
            return true;
        });
    }

    public static void updateUser(int newId, String newName, String newLastName, String newEmail, String newPassword, String newImageLink){
        id = newId;
        name = newName;
        lastName = newLastName;
        email = newEmail;
        password = newPassword;
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

    public static String getPassword(){
        return password;
    }

    public static String getImageLink() {
        return imageLink;
    }

    public static void updateName(String newName){
        name = newName;
    }

    public static void updateEmail(String newEmail){
        email = newEmail;
    }

    public static void updateImageLink(String newImageLink){
        imageLink = newImageLink;
    }

    public static void logOut() {
        id = -1;
        name = "";
        lastName = "";
        email = "";
        password = "";
        imageLink = "";
    }
}