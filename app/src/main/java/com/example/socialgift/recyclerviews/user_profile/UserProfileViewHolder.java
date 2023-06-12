package com.example.socialgift.recyclerviews.user_profile;

import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.socialgift.R;
import com.example.socialgift.api.APIClient;

import org.json.JSONException;
import org.json.JSONObject;

public class UserProfileViewHolder extends ViewHolder {

    private TextView listName;
    private ImageButton deleteListButton;
    private TextView id;

    public UserProfileViewHolder(View view){
        super(view);
        listName = view.findViewById(R.id.cvuser_profile_list_name);
        deleteListButton = view.findViewById(R.id.cvdelete_list);
        id = view.findViewById(R.id.cvid);

        deleteListButton.setOnClickListener(v -> {
            APIClient.makeDELETERequest(
                    view.getContext(),
                    "wishlists/" + id,
                    response -> {
                        System.out.println("TFYGU)HGYTCTFYGUHGYCFGVYUHIJGYVCFYUHGYVCVYUHHGYVYUHI");
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            System.out.println(jsonObject);
                            jsonObject.getJSONObject("payload");
                        } catch (JSONException e) {
                            Toast.makeText(view.getContext(), "Error retrieving list", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error ->{
                            System.out.println("ERRORRRRRRRRRRRRRRRRRRRRRRRRRR");
                            Toast.makeText(view.getContext(), "Error retrieving list", Toast.LENGTH_SHORT).show();
                    }

            );
        });
    }

    void bindData(final UserProfileListComponent userProfileItem) {
        listName.setText(userProfileItem.getListName());
        id.setText(userProfileItem.getId());
    }

    public ImageButton getDeleteListButton(){
        return deleteListButton;
    }

    public String getListName(){
        return listName.getText().toString();
    }
    public String getId(){
        return id.getText().toString();
    }
}
