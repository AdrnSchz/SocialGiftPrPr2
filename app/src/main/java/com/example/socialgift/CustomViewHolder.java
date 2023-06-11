package com.example.socialgift;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class CustomViewHolder extends ViewHolder {

    private ImageView userPhoto;
    private TextView userName;
    private TextView listName;

    public CustomViewHolder(View view) {
        super(view);
        userPhoto = view.findViewById(R.id.cvhome_usericon);
        userName = view.findViewById(R.id.cvhome_username);
        listName = view.findViewById(R.id.cvhome_listname);
    }

    void bindData(final ListComponent item) {
        userName.setText(item.getUserName());
        listName.setText(item.getListName());
        if (item.getUserName().length() > 9) {
            userName.setTextSize(34 - (item.getUserName().length() - 9));
        }
        if (item.getListName().length() > 14) {
            userName.setTextSize(24 - (item.getUserName().length() - 14));
        }

        try {
            new URL(item.getUserImage()).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return;
        }
        Picasso.get().load(item.getUserImage()).into(userPhoto);
    }
}