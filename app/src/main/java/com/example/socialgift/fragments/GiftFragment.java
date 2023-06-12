package com.example.socialgift.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgift.R;
import com.example.socialgift.api.APIClient;
import com.example.socialgift.recyclerviews.gifts.GiftAdapter;
import com.example.socialgift.recyclerviews.gifts.GiftComponent;
import com.example.socialgift.recyclerviews.homepage.ListComponent;
import com.example.socialgift.recyclerviews.search.SearchAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GiftFragment extends Fragment {
    private RecyclerView recyclerView;
    private Button addGift;
    private GiftAdapter giftAdapter;
    public static int id;
    public static String name, description, link, photo;
    public static double price;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gift, container, false);
        AppCompatActivity thisActivity = (AppCompatActivity) getActivity();
        addGift = view.findViewById(R.id.gift_add_button);
        recyclerView = view.findViewById(R.id.gift_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        giftAdapter = new GiftAdapter(getContext(), this);

        addGift.setOnClickListener(view1 -> {
            thisActivity.getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container_view, new CreateGift()).commit();
        });
        APIClient.makeGETRequest(getContext(), APIClient.PRODUCTS_API, "products", response -> {
            try {
                JSONArray jsonGifts = new JSONArray(response);

                for (int i = 0; i < jsonGifts.length(); i++) {
                    JSONObject jsonGift = jsonGifts.getJSONObject(i);

                    try {
                        giftAdapter.addItem(
                                new GiftComponent(
                                        jsonGift.getInt("id"),
                                        jsonGift.getString("name"),
                                        jsonGift.getString("description"),
                                        jsonGift.getString("link"),
                                        jsonGift.getString("photo"),
                                        jsonGift.getDouble("price")
                                )
                        );
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    recyclerView.setAdapter(giftAdapter);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, error -> {
            error.printStackTrace();
            Toast.makeText(getContext(), "Error retrieving the gifts", Toast.LENGTH_LONG).show();
        });

        return view;
    }

    public void gotoDetails(int id, String name, String description, String link, String photo, Double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.link = link;
        this.photo = photo;
        this.price = price;

        AppCompatActivity thisActivity = (AppCompatActivity) getContext();

        //TODO goto gift details
        //thisActivity.getSupportFragmentManager().beginTransaction().
        //        replace(R.id.fragment_container_view, new GiftDetailsFragment()).commit();
    }

}
