package com.example.socialgift.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgift.recyclerviews.homepage.AdapterList;
import com.example.socialgift.recyclerviews.homepage.ListComponent;
import com.example.socialgift.R;
import com.example.socialgift.api.APIClient;
import com.example.socialgift.recyclerviews.search.SearchAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchFragment extends Fragment {

    private ImageView searchView;

    private EditText searchInput;

    private RecyclerView recyclerView;

    private SearchAdapter searchAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchView = view.findViewById(R.id.search_icon);
        searchInput = view.findViewById(R.id.search_bar);
        recyclerView = view.findViewById(R.id.search_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchAdapter = new SearchAdapter(getContext());

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAdapter.clear();
                String endpoint = "users/search?s=" + searchInput.getText().toString();
                APIClient.makeGETRequest(getContext(), endpoint, response -> {
                    try {
                        JSONArray jsonUsers = new JSONArray(response);

                        for (int i = 0; i < jsonUsers.length(); i++) {
                            JSONObject jsonUser = jsonUsers.getJSONObject(i);

                            try {
                                searchAdapter.addItem(
                                        new ListComponent(
                                                jsonUser.getString("name"),
                                                jsonUser.getString("image"),
                                                jsonUser.getString("email"),
                                                "void",
                                                "void"
                                        )
                                );
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                            recyclerView.setAdapter(searchAdapter);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, error -> {
                    error.printStackTrace();
                    Toast.makeText(getContext(), R.string.error_search, Toast.LENGTH_LONG).show();
                });
            }
        });

        return view;
    }
}
