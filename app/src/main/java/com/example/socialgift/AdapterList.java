package com.example.socialgift;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.*;

import java.util.ArrayList;
import java.util.List;

public class AdapterList extends Adapter<CustomViewHolder> {

    private final List<ListComponent> data;
    private final LayoutInflater layoutInflater;

    public AdapterList(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = new ArrayList<>();
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
        /*
        holder.binIcon.setOnClickListener(v -> {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getTask().equals(holder.task.getText().toString())) {
                    data.remove(i);
                    notifyItemRemoved(i);
                    break;
                }
            }
        });

         */
    }

    @Override
    public int getItemCount(){
        return data.size();
    }

    public void addItem(ListComponent item) {
        data.add(item);
    }
}