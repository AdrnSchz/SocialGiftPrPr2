package com.example.socialgift.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgift.R;
import com.example.socialgift.api.APIClient;
import com.example.socialgift.recyclerviews.homepage.ListComponent;
import com.example.socialgift.recyclerviews.user_profile.UserProfileAdapterList;
import com.example.socialgift.entities.User;
import com.example.socialgift.recyclerviews.user_profile.UserProfileListComponent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        APIClient.makeGETRequest(getContext(), "wishlists",
                response -> {

                    RecyclerView recyclerView = view.findViewById(R.id.user_profile_recycler_view);
                    UserProfileAdapterList adapterList = new UserProfileAdapterList(getContext());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapterList);

                    try {
                        JSONArray jsonWishlists = new JSONArray(response);

                        for (int i = 0; i < jsonWishlists.length(); i++) {

                            JSONObject jsonWishlist = jsonWishlists.getJSONObject(i);
                            adapterList.addUserProfileItem(
                                    new UserProfileListComponent(
                                            jsonWishlist.getString("name")
                                    )
                            );
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), R.string.error_wishlists, Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(getContext(), R.string.error_wishlists, Toast.LENGTH_LONG).show();
                }
        );
        return view;
    }
}
