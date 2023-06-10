package com.example.socialgift;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

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
        //userName.setTextSize(25);
        //description.setTextSize(20);
    }
}