package com.example.socialgift.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgift.R;
import com.example.socialgift.activities.MainActivity;
import com.example.socialgift.api.APIClient;
import com.example.socialgift.recyclerviews.whishlist.WishlistAdapterList;
import com.example.socialgift.recyclerviews.whishlist.WishlistListComponent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Objects;

public class ListFragment extends Fragment {

    private int id;
    private Fragment origin;
    private boolean canEdit;
    private ImageView backButton;
    private TextView name;
    private TextView createdBy;
    private TextView date;
    private TextView description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;
        if (canEdit) {
            view = inflater.inflate(R.layout.fragment_list_editable, container, false);

            Button addButton = view.findViewById(R.id.list_add);
            addButton.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Choose a product");

                APIClient.makeGETRequest(
                        getContext(),
                        APIClient.PRODUCTS_API,
                        "products",
                        response -> {
                            try {
                                JSONArray products = new JSONArray(response);
                                String[] productNames = new String[products.length()];
                                for (int i = 0; i < products.length(); i++) {
                                    JSONObject product = products.getJSONObject(i);
                                    productNames[i] = product.getString("name");
                                }

                                builder.setItems(productNames, (dialog, which) -> {
                                    try {
                                        JSONObject product = products.getJSONObject(which);

                                        AlertDialog.Builder priorityBuilder = new AlertDialog.Builder(getContext());
                                        priorityBuilder.setTitle("Choose a priority");

                                        String[] priorities = {"Low", "Medium", "High", "Very High"};
                                        priorityBuilder.setItems(priorities, (dialog1, priority) -> {
                                            try {
                                                APIClient.makePOSTRequest(
                                                        getContext(),
                                                        "gifts",
                                                        new JSONObject()
                                                                .put("wishlist_id", id)
                                                                .put("product_url", APIClient.PRODUCTS_API + "products/" + product.getInt("id"))
                                                                .put("priority", priority * 25),
                                                        gift_response -> {
                                                            try {
                                                                gift_response.getInt("id"); // Throws exception if operation unsuccessful
                                                                Toast.makeText(getContext(), R.string.success_add_product, Toast.LENGTH_LONG).show();
                                                                loadData(view);
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
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> {
                            Toast.makeText(getContext(), R.string.error_list_no_products, Toast.LENGTH_LONG).show();
                        }
                );
            });
        }
        else {
            view = inflater.inflate(R.layout.fragment_list, container, false);
        }

        name = view.findViewById(R.id.list_name);
        createdBy = view.findViewById(R.id.list_owner);
        date = view.findViewById(R.id.list_date);
        description = view.findViewById(R.id.list_description);
        backButton = view.findViewById(R.id.list_back);

        backButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, origin).commit();
        });

        loadData(view);
        return view;
    }

    private void loadData(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.list_recycler_view);
        WishlistAdapterList wishlistAdapterList = new WishlistAdapterList(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wishlistAdapterList);

        APIClient.makeGETRequest(getContext(), "wishlists/" + id,
                response -> {

                    try {
                        JSONObject data = new JSONObject(response);

                        name.setText(data.getString("name"));

                        if (data.getString("name").length() > 13) {
                            name.setTextSize(34 - (data.getString("name").length() - 13));
                        }

                        description.setText(data.getString("description"));

                        String dateStr = data.getString("end_date");

                        if (!dateStr.equals("null")) {
                            TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse(dateStr);
                            Instant ins = Instant.from(ta);
                            Date d = Date.from(ins);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                            date.setText(dateFormat.format(d));
                        }
                        else {
                            date.setText(R.string.list_noenddate);
                        }

                        APIClient.makeGETRequest(getContext(), "users/" + data.getInt("user_id"),
                                user_response -> {
                                    try {
                                        JSONObject userObj = new JSONObject(user_response);
                                        createdBy.setText(userObj.getString("name"));
                                    } catch (JSONException e) {
                                        Toast.makeText(getContext(), R.string.error_list_process_owner, Toast.LENGTH_LONG).show();
                                    }
                                },
                                error -> Toast.makeText(getContext(), R.string.error_list_fetch_owner, Toast.LENGTH_LONG).show()
                        );

                        JSONArray giftsArr = data.getJSONArray("gifts");

                        for (int i = 0; i < giftsArr.length(); i++) {
                            JSONObject gift = giftsArr.getJSONObject(i);

                            String productUrl = gift.getString("product_url");

                            APIClient.makeGETRequest(getContext(),
                                    APIClient.PRODUCTS_API,
                                    productUrl.substring(productUrl.indexOf("products")),
                                    product_response -> {
                                        try {
                                            JSONObject product = new JSONObject(product_response);

                                            wishlistAdapterList.addItem(
                                                    new WishlistListComponent(
                                                            product.getString("name"),
                                                            gift.getInt("priority"),
                                                            gift.getInt("booked"),
                                                            gift.getInt("id")
                                                    )
                                            );

                                            wishlistAdapterList.notifyItemInserted(wishlistAdapterList.getItemCount() - 1);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(getContext(), R.string.error_list_process_product, Toast.LENGTH_LONG).show();
                                        }
                                    },
                                    error -> Toast.makeText(getContext(), R.string.error_list_fetch_product, Toast.LENGTH_LONG).show()
                            );

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), R.string.error_list_process, Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    Toast.makeText(getContext(), R.string.error_list_fetch, Toast.LENGTH_LONG).show();
                }
        );
    }

    public void setList(int id, int ownerId) {
        this.id = id;
        canEdit = ownerId == MainActivity.getId();
    }

    public void setOrigin(Fragment origin) {
        this.origin = origin;
    }
}