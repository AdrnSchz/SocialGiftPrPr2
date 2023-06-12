package com.example.socialgift.recyclerviews.user_profile_no_bin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgift.R;
import com.example.socialgift.recyclerviews.user_profile.UserProfileListComponent;
import com.example.socialgift.recyclerviews.user_profile.UserProfileViewHolder;

import java.util.ArrayList;
import java.util.List;

public class NoBinListAdapter extends RecyclerView.Adapter<NoBinListViewHolder> {
    private List<NoBinListComponent> data;
    private LayoutInflater layoutInflater;

    public NoBinListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = new ArrayList<>();
    }

    @NonNull
    @Override
    public NoBinListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cardview_no_bin_list, parent, false);
        return new NoBinListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoBinListViewHolder holder, int position) {
        holder.bindData(data.get(position));
    }

    @Override
    public int getItemCount(){
        return data.size();
    }

    public void addItem(NoBinListComponent item) {
        data.add(item);
    }
}
