package com.example.socialgift.recyclerviews.whishlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.socialgift.R;
import com.example.socialgift.fragments.ListFragment;

import java.util.ArrayList;
import java.util.List;

public class WishlistAdapterList extends Adapter<WishlistViewHolder> {

    private final List<WishlistListComponent> data;
    private final LayoutInflater layoutInflater;

    private final ListFragment listFragment;

    public WishlistAdapterList(Context context, ListFragment listFragment) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = new ArrayList<>();
        this.listFragment = listFragment;
    }

    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int typeView) {

        View view = layoutInflater.inflate(R.layout.cardview_list, parent, false);
        return new WishlistViewHolder(view, listFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistViewHolder holder, int position) {

        holder.bindData(data.get(position));
    }

    @Override
    public int getItemCount(){
        return data.size();
    }

    public void addItem(WishlistListComponent item) {
        data.add(item);
    }
}