package com.example.socialgift.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgift.AdapterList;
import com.example.socialgift.ListComponent;
import com.example.socialgift.R;
import com.example.socialgift.api.APIClient;
import com.example.socialgift.entities.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        APIClient.makeGETRequest(getContext(), "wishlists",
                response -> {

                    List<ListComponent> wishlists = new ArrayList<>();

                    try {
                        JSONArray jsonWishlists = new JSONArray(response);

                        for (int i = 0; i < jsonWishlists.length(); i++) {

                            JSONObject jsonWishlist = jsonWishlists.getJSONObject(i);

                            User user = getUser(jsonWishlist.getInt("user_id"));

                            if (user == null) continue;

                            wishlists.add(
                                    new ListComponent(
                                            user.getName(),
                                            user.getPhoto(),
                                            jsonWishlist.getString("name")
                                    )
                            );
                        }

                        RecyclerView recyclerView = view.findViewById(R.id.home_recycler_view);
                        //recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(new AdapterList(getContext(), wishlists));
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), R.string.error_wishlists, Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    Toast.makeText(getContext(), R.string.error_no_wishlists, Toast.LENGTH_LONG).show();
                }
        );
        return view;
    }

    private User getUser(int id) throws JSONException {

        AtomicReference<User> user = new AtomicReference<>();

        APIClient.makeGETRequest(getContext(), "users/" + id,
                response -> {

                    try {

                        JSONObject jsonUser = new JSONObject(response);

                        user.set(new User(
                                jsonUser.getString("name"),
                                jsonUser.getString("image")
                        ));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> {}
        );
        return user.get();
    }
}