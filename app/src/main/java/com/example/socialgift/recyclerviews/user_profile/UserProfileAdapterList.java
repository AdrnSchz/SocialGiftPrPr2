package com.example.socialgift.recyclerviews.user_profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.socialgift.R;

import java.util.ArrayList;
import java.util.List;

public class UserProfileAdapterList extends Adapter<UserProfileViewHolder> {

    private List<UserProfileListComponent> data;
    private LayoutInflater layoutInflater;

    public UserProfileAdapterList(Context context) {
        if (context != null) {
            this.layoutInflater = LayoutInflater.from(context);
            this.data = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public UserProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cardview_user_profile, parent, false);
        return new UserProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserProfileViewHolder holder, int position) {
        holder.bindData(data.get(position));
    }

    @Override
    public int getItemCount(){
        return data.size();
    }

    public void addUserProfileItem(UserProfileListComponent userProfileItem) {
        data.add(userProfileItem);
    }
}
