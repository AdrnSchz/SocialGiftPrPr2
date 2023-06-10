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
import com.example.socialgift.R;
import com.example.socialgift.api.APIClient;

import org.json.JSONException;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        APIClient.makeGETRequest(getContext(), "wishlists", response -> {
            try {
                RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new AdapterList(getContext(), response.getJSONArray("wishlists")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(getContext(), R.string.error_no_wishlists, Toast.LENGTH_LONG).show();
        });

        AdapterList adapterList = new AdapterList(tasks, this);
        RecyclerView recyclerView = view.findViewById(R.id.gift_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterList);

        return view;
    }

}
