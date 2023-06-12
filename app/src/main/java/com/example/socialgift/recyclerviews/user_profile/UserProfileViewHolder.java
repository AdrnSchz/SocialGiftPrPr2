package com.example.socialgift.recyclerviews.user_profile;

import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.socialgift.R;

public class UserProfileViewHolder extends ViewHolder {

    private TextView listName;
    private ImageButton deleteListButton;

    public UserProfileViewHolder(View view){
        super(view);
        listName = view.findViewById(R.id.cvuser_profile_list_name);
        deleteListButton = view.findViewById(R.id.cvdelete_list);
    }

    void bindData(final UserProfileListComponent userProfileItem) {
        listName.setText(userProfileItem.getListName());
    }
}
