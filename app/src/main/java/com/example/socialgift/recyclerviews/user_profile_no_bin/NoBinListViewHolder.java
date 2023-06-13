package com.example.socialgift.recyclerviews.user_profile_no_bin;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgift.R;
import com.example.socialgift.fragments.ListFragment;
import com.example.socialgift.fragments.ProfileFragment;
import com.example.socialgift.fragments.UserProfileFragment;
import com.example.socialgift.recyclerviews.user_profile.UserProfileListComponent;

public class NoBinListViewHolder extends RecyclerView.ViewHolder {

    private TextView listName;
    private CardView card;

    public NoBinListViewHolder(View view, UserProfileFragment profileFragment) {
        super(view);
        listName = view.findViewById(R.id.cvno_bin_list_name);
        card = view.findViewById(R.id.cvno_bin_list);

        card.setOnClickListener(view1 -> {
            NoBinListComponent list = (NoBinListComponent) card.getTag();

            ListFragment listFragment = new ListFragment();
            listFragment.setList(list.getId(), list.getOwnerId());
            listFragment.setOrigin(profileFragment);
            profileFragment.requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, listFragment).commit();
        });
    }

    void bindData(final NoBinListComponent item) {
        listName.setText(item.getListName());
        card.setTag(item);
    }
}
