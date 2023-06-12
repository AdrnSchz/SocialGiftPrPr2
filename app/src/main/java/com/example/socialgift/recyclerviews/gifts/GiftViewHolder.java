package com.example.socialgift.recyclerviews.gifts;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgift.R;
import com.example.socialgift.fragments.GiftFragment;
import com.example.socialgift.recyclerviews.homepage.ListComponent;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class GiftViewHolder extends RecyclerView.ViewHolder {
    private ImageView giftPhoto;
    private TextView name;
    private GiftComponent gift;
    private RelativeLayout relativeLayout;

    public GiftViewHolder(View view, GiftFragment giftFragment, GiftComponent item) {
        super(view);
        giftPhoto = view.findViewById(R.id.cvgift_icon);
        name = view.findViewById(R.id.cvgift_name);
        relativeLayout = view.findViewById(R.id.cv_gift_layout);
        this.gift = item;

        relativeLayout.setOnClickListener(view1 -> {
            giftFragment.gotoDetails(gift.getId(), gift.getGiftName(), gift.getDescritiption(), gift.getLink(),
                    gift.getImage(), gift.getPrice());
        });
    }

    void bindData(final GiftComponent item) {
        name.setText(item.getGiftName());
        if (item.getGiftName().length() > 9) {
            name.setTextSize(34 - (item.getGiftName().length() - 9));
        }

        try {
            new URL(item.getImage()).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return;
        }
        Picasso.get().load(item.getImage()).into(giftPhoto);
    }

}
