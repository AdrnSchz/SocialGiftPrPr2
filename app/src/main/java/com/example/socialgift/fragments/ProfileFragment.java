package com.example.socialgift.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgift.R;
import com.example.socialgift.activities.MainActivity;
import com.example.socialgift.api.APIClient;
import com.example.socialgift.recyclerviews.user_profile.UserProfileAdapterList;
import com.example.socialgift.recyclerviews.user_profile.UserProfileListComponent;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ProfileFragment extends Fragment {

    private ImageView userImage;
    private TextView userName;
    private TextView userEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        AppCompatActivity thisActivity = (AppCompatActivity) getContext();

        ImageButton settingsButton = view.findViewById(R.id.user_profile_settings_button);
        ImageButton statsButton = view.findViewById(R.id.user_profile_stats_button);
        userImage = view.findViewById(R.id.user_profile_picture);
        Button createList = view.findViewById(R.id.create_list_button);
        userName = view.findViewById(R.id.user_profile_username);
        userEmail = view.findViewById(R.id.user_profile_email);
        userName.setText(MainActivity.getName());
        userEmail.setText(MainActivity.getEmail());

        settingsButton.setOnClickListener(v ->{
            thisActivity.getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container_view, new SettingsFragment()).commit();
        });

        createList.setOnClickListener(v -> {
            thisActivity.getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container_view, new CreateListFragment()).commit();
        });

        statsButton.setOnClickListener(v -> {
            thisActivity.getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container_view, new StatisticsFragment()).commit();
        });

        APIClient.makeGETRequest(getContext(), "wishlists",
                response -> {

                    RecyclerView recyclerView = view.findViewById(R.id.user_profile_recycler_view);
                    UserProfileAdapterList adapterList = new UserProfileAdapterList(getContext());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    try {
                        JSONArray jsonWishlists = new JSONArray(response);

                        for (int i = 0; i < jsonWishlists.length(); i++) {
                            JSONObject jsonWishlist = jsonWishlists.getJSONObject(i);
                            int userId = jsonWishlist.getInt("user_id");
                            if (userId == MainActivity.getId()) {
                                adapterList.addUserProfileItem(
                                        new UserProfileListComponent(
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

    @Override
    public void onResume(){
        super.onResume();
        userName.setText(MainActivity.getName());
        userEmail.setText(MainActivity.getEmail());
        URI uri;
        try {
            uri = new URL(MainActivity.getImageLink()).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return;
        }
        Picasso.get().load(String.valueOf(uri)).into(userImage);
    }

}
