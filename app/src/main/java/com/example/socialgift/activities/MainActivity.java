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
}