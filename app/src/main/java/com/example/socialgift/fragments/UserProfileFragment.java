package com.example.socialgift.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialgift.R;
import com.example.socialgift.activities.MainActivity;
import com.example.socialgift.api.APIClient;
import com.example.socialgift.recyclerviews.user_profile_no_bin.NoBinListAdapter;
import com.example.socialgift.recyclerviews.user_profile_no_bin.NoBinListComponent;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

public class UserProfileFragment extends Fragment {

    private ImageButton backButton, statsButton;
    private TextView userName, userEmail;
    private ImageView userImage;
    private RecyclerView recyclerView;
    private NoBinListAdapter adapterList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        AppCompatActivity thisActivity = (AppCompatActivity) getContext();

        backButton = view.findViewById(R.id.user_profile_back);
        statsButton = view.findViewById(R.id.user_statistics_button);
        userName = view.findViewById(R.id.username_text);
        userEmail = view.findViewById(R.id.email_text);
        userImage = view.findViewById(R.id.user_profile_picture_);

        userName.setText(SearchFragment.name);
        userEmail.setText(SearchFragment.email);
        try {
             new URL(SearchFragment.photo).toURI();
             Picasso.get().load(SearchFragment.photo).into(userImage);
        } catch (MalformedURLException | URISyntaxException e) {
            Picasso.get().load(MainActivity.PLACEHOLDER_IMG).into(userImage);
        }

        backButton.setOnClickListener(v -> {
            thisActivity.getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container_view, new SearchFragment()).commit();
        });

        statsButton.setOnClickListener(v -> {
            updateStats(thisActivity);
        });

        recyclerView = view.findViewById(R.id.user_profile_recycler_view);
        adapterList = new NoBinListAdapter(getContext(), this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        APIClient.makeGETRequest(getContext(), "wishlists",
                response -> {

                    try {
                        JSONArray jsonWishlists = new JSONArray(response);

                        for (int i = 0; i < jsonWishlists.length(); i++) {
                            JSONObject jsonWishlist = jsonWishlists.getJSONObject(i);
                            int userId = jsonWishlist.getInt("user_id");
                            if (userId == SearchFragment.id) {
                                adapterList.addItem(
                                        new NoBinListComponent(
                                                jsonWishlist.getString("name"),
                                                jsonWishlist.getInt("id"),
                                                userId
                                        )
                                );
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), R.string.error_wishlists, Toast.LENGTH_LONG).show();
                    }
                    recyclerView.setAdapter(adapterList);
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(getContext(), R.string.error_wishlists, Toast.LENGTH_LONG).show();
                }
        );

        return view;
    }

    public void updateStats(AppCompatActivity thisActivity){
        AtomicInteger gifts = new AtomicInteger();
        AtomicInteger lists = new AtomicInteger();
        int userId = SearchFragment.id;
        String endPoint = "users/" + userId + "/gifts/reserved";

        APIClient.makeGETRequest(getContext(), endPoint,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        gifts.set(jsonArray.length());
                    }
                    catch (JSONException e){
                        gifts.set(0);
                    }
                },
                error -> {
                    gifts.set(0);
                });

        endPoint = "users/" + userId + "/wishlists";
        APIClient.makeGETRequest(getContext(), endPoint,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        lists.set(jsonArray.length());

                        UserStatisticsFragment userStatisticsFragment = new UserStatisticsFragment();
                        userStatisticsFragment.numberOfGifts = Integer.toString(gifts.intValue());
                        userStatisticsFragment.numberOfLists = Integer.toString(lists.intValue());
                        userStatisticsFragment.numberOfPoints = Integer.toString(
                                gifts.intValue() * 5 + lists.intValue() * 20);

                        thisActivity.getSupportFragmentManager().beginTransaction().
                                replace(R.id.fragment_container_view, userStatisticsFragment).commit();
                    }
                    catch (JSONException e){
                        lists.set(0);
                        Toast.makeText(thisActivity, "Error generating stats", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    lists.set(0);
                    Toast.makeText(thisActivity, "Error generating stats", Toast.LENGTH_SHORT).show();
                });
    }
}