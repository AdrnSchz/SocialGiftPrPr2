package com.example.socialgift.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.socialgift.R;
import com.example.socialgift.activities.MainActivity;
import com.example.socialgift.api.APIClient;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class StatisticsFragment extends Fragment {

    private ImageView userImage;
    private TextView userName;
    private TextView userEmail;
    private TextView numGifts;
    private TextView numLists;
    private TextView numPoints;
    public String numberOfGifts;
    public String numberOfLists;
    public String numberOfPoints;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        AppCompatActivity thisActivity = (AppCompatActivity) getContext();

        ImageButton backToListsButton = view.findViewById(R.id.stats_tolist_button);
        ImageButton settingsButton = view.findViewById(R.id.stats_settings_button);
        userImage = view.findViewById(R.id.stats_profile_picture);
        userName = view.findViewById(R.id.stats_username);
        userEmail = view.findViewById(R.id.stats_email);
        numGifts = view.findViewById(R.id.number_gifts);
        numLists = view.findViewById(R.id.number_lists);
        numPoints = view.findViewById(R.id.number_points);

        backToListsButton.setOnClickListener(v ->{
            thisActivity.getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container_view, new ProfileFragment()).commit();
        });

        settingsButton.setOnClickListener(v ->{
            thisActivity.getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container_view, new SettingsFragment()).commit();
        });

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        userName.setText(MainActivity.getName());
        userEmail.setText(MainActivity.getEmail());
        numGifts.setText(String.format(numberOfGifts));
        numLists.setText(String.format(numberOfLists));
        numPoints.setText(String.format(numberOfPoints));
        try {
            URI uri = new URL(MainActivity.getImageLink()).toURI();
            Picasso.get().load(String.valueOf(uri)).into(userImage);
        } catch (MalformedURLException | URISyntaxException e) {
            Picasso.get().load(MainActivity.PLACEHOLDER_IMG).into(userImage);
        }
    }

}
