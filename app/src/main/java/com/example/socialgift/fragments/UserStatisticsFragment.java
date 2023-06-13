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

public class UserStatisticsFragment extends Fragment {


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
        View view = inflater.inflate(R.layout.fragment_user_statistics, container, false);
        AppCompatActivity thisActivity = (AppCompatActivity) getContext();

        ImageButton backToListsButton = view.findViewById(R.id.stats_user_tolist_button);
        userImage = view.findViewById(R.id.stats_user_profile_picture);
        userName = view.findViewById(R.id.stats_user_username);
        userEmail = view.findViewById(R.id.stats_user_email);
        numGifts = view.findViewById(R.id.stats_user_number_gifts);
        numLists = view.findViewById(R.id.stats_user_number_lists);
        numPoints = view.findViewById(R.id.stats_user_number_points);

        backToListsButton.setOnClickListener(v ->{
            thisActivity.getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container_view, new UserProfileFragment()).commit();
        });

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        userName.setText(SearchFragment.name);
        userEmail.setText(SearchFragment.email);
        numGifts.setText(String.format(numberOfGifts));
        numLists.setText(String.format(numberOfLists));
        numPoints.setText(String.format(numberOfPoints));
        try {
            URI uri = new URL(SearchFragment.photo).toURI();
            Picasso.get().load(String.valueOf(uri)).into(userImage);
        } catch (MalformedURLException | URISyntaxException e) {
            Picasso.get().load(MainActivity.PLACEHOLDER_IMG).into(userImage);
        }
    }

}
