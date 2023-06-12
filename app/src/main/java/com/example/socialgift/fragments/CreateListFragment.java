package com.example.socialgift.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.socialgift.R;
import com.example.socialgift.api.APIClient;

import org.json.JSONObject;

public class CreateListFragment extends Fragment {

    private EditText name, description, date;

    private Button saveButton;

    private ImageButton backButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_list, container, false);
        AppCompatActivity thisActivity = (AppCompatActivity) getContext();

        name = view.findViewById(R.id.create_list_name);
        description = view.findViewById(R.id.create_list_description);
        date = view.findViewById(R.id.create_list_date);
        saveButton = view.findViewById(R.id.create_list_save);
        backButton = view.findViewById(R.id.create_list_back);

        backButton.setOnClickListener(view1 -> {
            thisActivity.getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container_view, new ProfileFragment()).commit();
        });

        saveButton.setOnClickListener(view1 -> {
            String name = this.name.getText().toString();
            String description = this.description.getText().toString();
            String date = this.date.getText().toString();

            JSONObject json = new JSONObject();
            try {
                json.put("name", name);
                json.put("description", description);
                json.put("end_date", date);
                APIClient.makePOSTRequest(getContext(), "wishlists", json, response -> {
                    thisActivity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_container_view, new ProfileFragment()).commit();
                }, error -> {
                    error.printStackTrace();
                    Toast.makeText(getContext(), "Error creating list", Toast.LENGTH_SHORT).show();
                });

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Error creating list", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
