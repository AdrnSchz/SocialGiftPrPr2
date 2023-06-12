package com.example.socialgift.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.socialgift.R;
import com.example.socialgift.api.APIClient;

import org.json.JSONObject;

public class CreateGift extends Fragment {
    private EditText name, image, link, price, category, description;

    private Button saveButton;

    private ImageButton backButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_gift  , container, false);
        AppCompatActivity thisActivity = (AppCompatActivity) getContext();

        name = view.findViewById(R.id.create_gift_name);
        image = view.findViewById(R.id.create_gift_image);
        link = view.findViewById(R.id.create_gift_link);
        price = view.findViewById(R.id.create_gift_price);
        category = view.findViewById(R.id.create_gift_category);
        description = view.findViewById(R.id.create_gift_description);
        saveButton = view.findViewById(R.id.create_gift_save);
        backButton = view.findViewById(R.id.create_gift_back);

        backButton.setOnClickListener(view1 -> {
            thisActivity.getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container_view, new GiftFragment()).commit();
        });

        saveButton.setOnClickListener(view1 -> {
            String name = this.name.getText().toString();
            String description = this.description.getText().toString();
            String link = this.link.getText().toString();
            String image = this.image.getText().toString();
            float price = Float.parseFloat(this.price.getText().toString());
            int category = Integer.parseInt(this.category.getText().toString());

            JSONObject json = new JSONObject();
            try {
                json.put("name", name);
                json.put("description", description);
                json.put("link", link);
                json.put("photo", image);
                json.put("price", price);
                json.put("category", category);

                APIClient.makePOSTRequest(getContext(), APIClient.PRODUCTS_API,"products", json, response -> {
                    thisActivity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragment_container_view, new ProfileFragment()).commit();
                }, error -> {
                    error.printStackTrace();
                    Toast.makeText(getContext(), "Error creating gift", Toast.LENGTH_SHORT).show();
                });

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Error creating gift", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
