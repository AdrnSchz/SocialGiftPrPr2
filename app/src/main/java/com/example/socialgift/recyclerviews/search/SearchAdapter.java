package com.example.socialgift.recyclerviews.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.*;

import com.example.socialgift.R;
import com.example.socialgift.fragments.SearchFragment;
import com.example.socialgift.recyclerviews.homepage.ListComponent;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends Adapter<SearchViewHolder> {

    private final List<ListComponent> data;
    private final LayoutInflater layoutInflater;
    private final SearchFragment searchFragment;

    public SearchAdapter(Context context, SearchFragment searchFragment) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = new ArrayList<>();
        this.searchFragment = searchFragment;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int typeView) {

        View view = layoutInflater.inflate(R.layout.cardview_search, parent, false);
        return new SearchViewHolder(view, searchFragment, data.get(data.size() - 1).getId(), data.get(data.size() - 1).getUserImage());
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {

        holder.bindData(data.get(position));
    }

    @Override
    public int getItemCount(){
        return data.size();
    }

    public void addItem(ListComponent item) {
        data.add(item);
    }

    public void clear() {
        data.clear();
        notifyItemRangeRemoved(0, data.size());
    }
}
