package com.example.socialgift.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgift.R;
import com.example.socialgift.activities.MainActivity;
import com.example.socialgift.api.APIClient;
import com.example.socialgift.recyclerviews.user_profile.UserProfileAdapterList;
import com.example.socialgift.recyclerviews.user_profile.UserProfileListComponent;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GiftDetailsFragment extends Fragment {

    private Button addToList, deleteGift;

    private TextView description, link, price, name;
    private ImageView photo;

    private boolean fromList = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gift_details, container, false);
        AppCompatActivity thisActivity = (AppCompatActivity) getActivity();

        checkFather();

        addToList = view.findViewById(R.id.details_add_to_list);
        deleteGift = view.findViewById(R.id.details_delete);
        description = view.findViewById(R.id.details_description);
        link = view.findViewById(R.id.details_link);
        price = view.findViewById(R.id.details_price);
        name = view.findViewById(R.id.details_name);
        photo = view.findViewById(R.id.details_image);

        description.setText(GiftFragment.description);
        link.setText(GiftFragment.link);
        price.setText(String.valueOf(GiftFragment.price));
        name.setText(GiftFragment.name);

        try {
            new URL(GiftFragment.photo).toURI();
            Picasso.get().load(GiftFragment.photo).into(photo);
        } catch (MalformedURLException | URISyntaxException e) {
            Picasso.get().load(MainActivity.PLACEHOLDER_IMG).into(photo);
        }

        deleteGift.setOnClickListener(view1 -> {
            String endpoint = "products/" + GiftFragment.id;
            APIClient.makeDELETERequest(getContext(), APIClient.PRODUCTS_API, endpoint, response -> {
                try {
                    JSONObject res = new JSONObject(response);
                    if (res.getBoolean("success")) {
                        thisActivity.getSupportFragmentManager().beginTransaction().
                                replace(R.id.fragment_container_view, new GiftFragment()).commit();
                    }
                    else {
                        Toast.makeText(view.getContext(), res.getString("detail"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(view.getContext(),  R.string.error_profile_listdelete, Toast.LENGTH_LONG).show();
                }
            }, error -> {
                error.printStackTrace();
                Toast.makeText(getContext(), "Error deleting the gift", Toast.LENGTH_LONG).show();
            } );
        });

        addToList.setOnClickListener(view1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Choose a list");

            APIClient.makeGETRequest(getContext(), "wishlists",
                    response -> {
                        try {
                            JSONArray jsonWishlists = new JSONArray(response);
                            //String[] listNames = new String[jsonWishlists.length()];
                            List<String> listNames = new ArrayList<>();
                            int j = 0;

                            for (int i = 0; i < jsonWishlists.length(); i++) {
                                JSONObject jsonWishlist = jsonWishlists.getJSONObject(i);
                                int userId = jsonWishlist.getInt("user_id");

                                if (userId == MainActivity.getId()) {
                                    listNames.add(jsonWishlist.getString("name"));
                                    j++;
                                }
                            }
                            if (listNames.size() == 0) {
                                Toast.makeText(getContext(), "Create a list before adding items", Toast.LENGTH_LONG).show();
                            }
                            else {
                                builder.setItems(listNames.toArray(new String[0]), (dialog, which) -> {
                                    try {
                                        JSONObject list = jsonWishlists.getJSONObject(which);

                                        AlertDialog.Builder priorityBuilder = new AlertDialog.Builder(getContext());
                                        priorityBuilder.setTitle("Choose a priority");

                                        String[] priorities = {"Low", "Medium", "High", "Very High"};
                                        priorityBuilder.setItems(priorities, (dialog1, priority) -> {
                                            try {
                                                APIClient.makePOSTRequest(getContext(), "gifts",
                                                        new JSONObject()
                                                                .put("wishlist_id", list.getInt("id"))
                                                                .put("product_url", APIClient.PRODUCTS_API + "products/" + GiftFragment.id)
                                                                .put("priority", priority * 25),
                                                        gift_response -> {
                                                            try {
                                                                gift_response.getInt("id"); // Throws exception if operation unsuccessful
                                                                Toast.makeText(getContext(), R.string.success_add_product, Toast.LENGTH_LONG).show();
                                                            } catch (JSONException e) {
                                                                Toast.makeText(getContext(), R.string.error_add_product, Toast.LENGTH_LONG).show();
                                                            }
                                                        },
                                                        error -> {
                                                            Toast.makeText(getContext(), R.string.error_add_product, Toast.LENGTH_LONG).show();
                                                        }
                                                );
                                            } catch (JSONException e) {
                                                Toast.makeText(getContext(), R.string.error_add_product, Toast.LENGTH_LONG).show();
                                            }
                                        });

                                        AlertDialog priorityDialog = priorityBuilder.create();
                                        priorityDialog.show();

                                    } catch (JSONException e) {
                                        Toast.makeText(getContext(), R.string.error_list_process_product, Toast.LENGTH_LONG).show();
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        Toast.makeText(getContext(), R.string.error_wishlists, Toast.LENGTH_LONG).show();
                    }
            );

        });

        return view;

    }

    public void setFromList(boolean bol) {
        fromList = bol;
    }

    private void checkFather() {
        if (fromList) {
            APIClient.makeGETRequest(getContext(), APIClient.PRODUCTS_API, "products/" + ListFragment.gift_id,
                    response -> {
                        try {
                            JSONObject jsonGift = new JSONObject(response);
                            GiftFragment.name = jsonGift.getString("name");
                            GiftFragment.description = jsonGift.getString("description");
                            GiftFragment.link = jsonGift.getString("link");
                            GiftFragment.price = jsonGift.getDouble("price");
                            GiftFragment.photo = jsonGift.getString("photo");
                            GiftFragment.id = jsonGift.getInt("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        error.printStackTrace();
                    });
        }
    }
}
