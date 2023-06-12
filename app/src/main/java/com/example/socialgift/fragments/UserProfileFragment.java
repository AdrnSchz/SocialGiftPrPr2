package com.example.socialgift.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialgift.R;
import com.example.socialgift.activities.MainActivity;
import com.example.socialgift.api.APIClient;
import com.example.socialgift.recyclerviews.user_profile.UserProfileAdapterList;
import com.example.socialgift.recyclerviews.user_profile.UserProfileListComponent;
import com.example.socialgift.recyclerviews.user_profile_no_bin.NoBinListAdapter;
import com.example.socialgift.recyclerviews.user_profile_no_bin.NoBinListComponent;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

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
            e.printStackTrace();
            Toast.makeText(getContext(), "Invalid URL", Toast.LENGTH_SHORT).show();
        }

        //TODO: backButton
        backButton.setOnClickListener(v -> {
            thisActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, new SearchFragment()).commit();
        });

        statsButton.setOnClickListener(v -> {
            //TODO statsButton getActivity().getSupportFragmentManager().beginTransaction().replace(view.getId(), new UserStatisticsFragment()).commit();
        });

        recyclerView = view.findViewById(R.id.user_profile_recycler_view);
        adapterList = new NoBinListAdapter(getContext());
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
                                                jsonWishlist.getString("name")
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
}