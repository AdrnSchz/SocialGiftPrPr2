package com.example.socialgift.recyclerviews.whishlist;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.socialgift.R;
import com.example.socialgift.api.APIClient;
import com.example.socialgift.fragments.ListFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class WishlistViewHolder extends ViewHolder {

    private ImageView priority;
    private TextView giftName;
    private ImageView booked;
    private RelativeLayout layout;
    private int id;

    public WishlistViewHolder(View view, ListFragment listFragment) {
        super(view);
        giftName = view.findViewById(R.id.cvlist_name);
        priority = view.findViewById(R.id.cvlist_priority);
        booked = view.findViewById(R.id.cvlist_checkbox);
        layout = view.findViewById(R.id.cv_list_layout);

        layout.setOnClickListener(v -> {
            listFragment.gotoDetails(id);
        });

        booked.setOnClickListener(v -> {

            if (booked.getTag().equals("CHECKED")) {

                APIClient.makeDELETERequest(
                        view.getContext(),
                        "gifts/" + id + "/book",
                        response -> {
                            try {
                                JSONObject res = new JSONObject(response);
                                if (res.getBoolean("success")) {
                                    booked.setImageResource(R.drawable.uncheckedbox_icon_foreground);
                                    booked.setTag("UNCHECKED");
                                }
                                else {
                                    Toast.makeText(view.getContext(), R.string.error_list_book, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(view.getContext(), R.string.error_list_book, Toast.LENGTH_SHORT).show();
                            }
                        },
                        error -> Toast.makeText(view.getContext(), R.string.error_list_book, Toast.LENGTH_SHORT).show()
                );
            }
            else {
                APIClient.makePOSTRequest(
                        view.getContext(),
                        "gifts/" + id + "/book",
                        null,
                        response -> {
                            try {
                                response.getString("owner_id"); // Will throw exception if error response
                                booked.setImageResource(R.drawable.checkedbox_icon_foreground);
                                booked.setTag("CHECKED");
                            } catch (JSONException e) {
                                Toast.makeText(view.getContext(), R.string.error_list_book, Toast.LENGTH_SHORT).show();
                            }
                        },
                        error -> Toast.makeText(view.getContext(), R.string.error_list_book, Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    void bindData(final WishlistListComponent item) {
        if (item.isBooked()) {
            booked.setImageResource(R.drawable.checkedbox_icon_foreground);
            booked.setTag("CHECKED");
        }
        else {
            booked.setImageResource(R.drawable.uncheckedbox_icon_foreground);
            booked.setTag("UNCHECKED");
        }

        int priorityValue = Math.min(item.getPriority() / 25, 3);

        switch (priorityValue) {
            case 0 -> priority.setImageResource(R.drawable.lowpriority_icon_foreground);
            case 1 -> priority.setImageResource(R.drawable.mediumpriority_icon_foreground);
            case 2 -> priority.setImageResource(R.drawable.highpriority_icon_foreground);
            case 3 -> priority.setImageResource(R.drawable.veryhighpriority_icon_foreground);
        }

        giftName.setText(item.getGiftName());

        if (item.getGiftName().length() > 14) {
            giftName.setTextSize(Math.max(14, 26 - (item.getGiftName().length() - 14)));
        }

        id = item.getId();
    }
}