package com.example.socialgift.recyclerviews.homepage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.*;

import com.example.socialgift.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterList extends Adapter<CustomViewHolder> {

    private List<ListComponent> data;
    private LayoutInflater layoutInflater;

    public AdapterList(Context context) {
        if (context != null) {
            this.layoutInflater = LayoutInflater.from(context);
            this.data = new ArrayList<>();
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int typeView) {

        View view = layoutInflater.inflate(R.layout.cardview_home, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.bindData(data.get(position));
    }

    @Override
    public int getItemCount(){
        return data.size();
    }

    public void addItem(ListComponent item) {
        if (data != null && item != null) {
            data.add(item);
        }
    }
}