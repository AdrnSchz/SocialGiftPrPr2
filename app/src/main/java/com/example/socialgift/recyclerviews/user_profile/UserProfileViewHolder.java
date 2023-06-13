package com.example.socialgift.recyclerviews.user_profile;

import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.socialgift.R;
import com.example.socialgift.api.APIClient;
import com.example.socialgift.fragments.ListFragment;
import com.example.socialgift.fragments.ProfileFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class UserProfileViewHolder extends ViewHolder {

    private TextView listName;
    private ImageButton deleteListButton;
    private CardView card;

    public UserProfileViewHolder(View view, ProfileFragment profileFragment){
        super(view);
        listName = view.findViewById(R.id.cvuser_profile_list_name);
        deleteListButton = view.findViewById(R.id.cvdelete_list);
        card = view.findViewById(R.id.cvuser_profile_list);

        card.setOnClickListener(v -> {
            UserProfileListComponent list = (UserProfileListComponent) card.getTag();

            ListFragment listFragment = new ListFragment();
            listFragment.setList(list.getId(), list.getOwnerId());
            listFragment.setOrigin(profileFragment);
            profileFragment.requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, listFragment).commit();
        });

        deleteListButton.setOnClickListener(v -> {
            UserProfileListComponent list = (UserProfileListComponent) card.getTag();

            APIClient.makeDELETERequest(
                    view.getContext(),
                    "wishlists/" + list.getId(),
                    response -> {
                        try {
                            JSONObject res = new JSONObject(response);
                            if (res.getBoolean("success")) {
                                profileFragment.updateLists();
                            }
                            else {
                                Toast.makeText(view.getContext(), R.string.error_profile_listdelete, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(view.getContext(), R.string.error_profile_listdelete, Toast.LENGTH_LONG).show();
                        }
                    },
                    error -> Toast.makeText(view.getContext(), R.string.error_profile_listdelete, Toast.LENGTH_SHORT).show()
            );
        });
    }

    void bindData(final UserProfileListComponent userProfileItem) {
        listName.setText(userProfileItem.getListName());
        card.setTag(userProfileItem);
    }
}