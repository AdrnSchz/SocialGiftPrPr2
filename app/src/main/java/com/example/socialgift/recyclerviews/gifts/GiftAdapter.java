package com.example.socialgift.recyclerviews.gifts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgift.R;
import com.example.socialgift.fragments.GiftFragment;

import java.util.ArrayList;
import java.util.List;

public class GiftAdapter extends RecyclerView.Adapter<GiftViewHolder> {
    private final List<GiftComponent> data;
    private final LayoutInflater layoutInflater;
    private final GiftFragment giftFragment;

    public GiftAdapter(Context context, GiftFragment giftFragment) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = new ArrayList<>();
        this.giftFragment = giftFragment;
    }

    @NonNull
    @Override
    public GiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int typeView) {

        View view = layoutInflater.inflate(R.layout.cardview_gift, parent, false);
        return new GiftViewHolder(view, giftFragment, data.get(data.size() - 1));
    }

    @Override
    public void onBindViewHolder(@NonNull GiftViewHolder holder, int position) {
        holder.bindData(data.get(position));
    }

    @Override
    public int getItemCount(){
        return data.size();
    }

    public void addItem(GiftComponent item) {
        data.add(item);
    }

    public void clear() {
        data.clear();
        notifyItemRangeRemoved(0, data.size());
    }
}
