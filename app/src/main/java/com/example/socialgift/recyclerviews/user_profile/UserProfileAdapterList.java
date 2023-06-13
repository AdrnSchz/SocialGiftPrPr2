package com.example.socialgift.recyclerviews.user_profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.socialgift.R;
import com.example.socialgift.fragments.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class UserProfileAdapterList extends Adapter<UserProfileViewHolder> {

    private List<UserProfileListComponent> data;
    private LayoutInflater layoutInflater;
    private ProfileFragment profileFragment;

    public UserProfileAdapterList(Context context, ProfileFragment profileFragment) {
        if (context != null) {
            this.layoutInflater = LayoutInflater.from(context);
            this.data = new ArrayList<>();
            this.profileFragment = profileFragment;
        }
    }

    @NonNull
    @Override
    public UserProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cardview_user_profile, parent, false);
        return new UserProfileViewHolder(view, profileFragment);
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

    private void removeItem(){

    }
}
