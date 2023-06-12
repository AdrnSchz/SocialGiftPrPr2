package com.example.socialgift.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgift.recyclerviews.homepage.AdapterList;
import com.example.socialgift.recyclerviews.homepage.ListComponent;
import com.example.socialgift.R;
import com.example.socialgift.api.APIClient;
import com.example.socialgift.entities.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.Consumer;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        APIClient.makeGETRequest(getContext(), "wishlists",
                response -> {

                    RecyclerView recyclerView = view.findViewById(R.id.home_recycler_view);
                    AdapterList adapterList = new AdapterList(getContext());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    try {
                        JSONArray jsonWishlists = new JSONArray(response);

                        for (int i = 0; i < jsonWishlists.length(); i++) {

                            JSONObject jsonWishlist = jsonWishlists.getJSONObject(i);

                            getUser(jsonWishlist.getInt("user_id"), user -> {
                                if (user == null) {
                                    return;
                                }

                                try {
                                    adapterList.addItem(
                                            new ListComponent(
                                                    user.getName(),
                                                    user.getPhoto(),
                                                    jsonWishlist.getString("name")
                                            )
                                    );
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                                recyclerView.setAdapter(adapterList);
                            });
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

    private void getUser(int id, Consumer<User> callback) throws JSONException {

        APIClient.makeGETRequest(getContext(), "users/" + id,
                response -> {

                    try {

                        JSONObject jsonUser = new JSONObject(response);

                        callback.accept(new User(
                                jsonUser.getString("name"),
                                jsonUser.getString("image")
                        ));
                    } catch (JSONException e) {
                        callback.accept(null);
                    }
                },
                error -> {
                    callback.accept(null);
                }
        );
    }
}