package com.example.socialgift.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.socialgift.R;
import com.example.socialgift.api.APIClient;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class GiftDetailsFragment extends Fragment {

    private Button addToList, deleteGift;

    private TextView description, link, price, name;
    private ImageView photo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gift_details, container, false);
        AppCompatActivity thisActivity = (AppCompatActivity) getActivity();

        addToList = view.findViewById(R.id.details_add_to_list);
        deleteGift = view.findViewById(R.id.details_delete);
        description = view.findViewById(R.id.details_description);
        link = view.findViewById(R.id.details_link);
        price = view.findViewById(R.id.details_price);
        name = view.findViewById(R.id.details_name);
        photo = view.findViewById(R.id.details_image);

        description.setText(GiftFragment.description);
        link.setText(GiftFragment.link);
        price.setText(String.valueOf(GiftFragment.price));
        name.setText(GiftFragment.name);

        try {
            new URL(GiftFragment.photo).toURI();
            Picasso.get().load(GiftFragment.photo).into(photo);
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Invalid URL", Toast.LENGTH_SHORT).show();
        }

        addToList.setOnClickListener(view1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Choose a list");
        });

        deleteGift.setOnClickListener(view1 -> {
            String endpoint = "products/" + GiftFragment.id;
            APIClient.makeDELETERequest(getContext(), APIClient.PRODUCTS_API, endpoint, response -> {
            }, error -> {
                error.printStackTrace();
                Toast.makeText(getContext(), "Error deleting the gift", Toast.LENGTH_LONG).show();
            } );

            thisActivity.getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container_view, new GiftFragment()).commit();
        });

        return view;

    }
}
