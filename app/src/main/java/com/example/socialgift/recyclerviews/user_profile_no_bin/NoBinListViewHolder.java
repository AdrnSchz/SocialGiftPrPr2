package com.example.socialgift.recyclerviews.user_profile_no_bin;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgift.R;
import com.example.socialgift.recyclerviews.user_profile.UserProfileListComponent;

public class NoBinListViewHolder extends RecyclerView.ViewHolder {

    private TextView listName;
    private RelativeLayout layout;

    public NoBinListViewHolder(View view){
        super(view);
        listName = view.findViewById(R.id.cvno_bin_list_name);
        layout = view.findViewById(R.id.cvno_bin_list_layout);

        layout.setOnClickListener(view1 -> {
            //TODO goto fragment list details
        });
    }

    void bindData(final NoBinListComponent item) {
        listName.setText(item.getListName());
    }
}
