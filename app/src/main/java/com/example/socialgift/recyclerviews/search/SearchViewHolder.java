package com.example.socialgift.recyclerviews.search;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgift.R;
import com.example.socialgift.recyclerviews.homepage.ListComponent;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class SearchViewHolder extends RecyclerView.ViewHolder {
    private ImageView userPhoto;
    private TextView userName;
    private TextView mail;

    private RelativeLayout relativeLayout;

    public SearchViewHolder(View view) {
        super(view);
        userPhoto = view.findViewById(R.id.cvsearch_usericon);
        userName = view.findViewById(R.id.cvsearch_username);
        mail = view.findViewById(R.id.cvsearch_mail);
        relativeLayout = view.findViewById(R.id.cv_search_layout);

        relativeLayout.setOnClickListener(view1 -> {
            System.out.printf("Clicked on %s's with mail: %s\n", userName.getText(), mail.getText());
            //TODO go into profile of user when clicked in search
        });
    }

    void bindData(final ListComponent item) {
        userName.setText(item.getUserName());
        mail.setText(item.getUserEmail());
        if (item.getUserName().length() > 9) {
            userName.setTextSize(34 - (item.getUserName().length() - 9));
        }
        if (item.getUserEmail().length() > 14) {
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