package com.example.socialgift.api;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.socialgift.R;
import com.example.socialgift.activities.InitialScreenActivity;
import com.example.socialgift.activities.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class APIClient {
    public static final String PRODUCTS_API = "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/";
    private static final String DEFAULT_API = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/";
    private static String token = "";

    public static void createUser(Context context, String name, String surname, String email, String password){
        AppCompatActivity appCompatActivity = (AppCompatActivity) context;

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("name", name);
            requestBody.put("last_name", surname);
            requestBody.put("email", email);
            requestBody.put("password", password);
            requestBody.put("image", "");
        } catch (JSONException e) {
            Toast.makeText(appCompatActivity, R.string.error_user_creation, Toast.LENGTH_LONG).show();
        }

        makePOSTRequest(context, "users", requestBody,
                response -> {
                    try {
                        int id = Integer.parseInt(response.getString("id"));
                        if (id >= 0){
                            Toast.makeText(appCompatActivity, R.string.user_created, Toast.LENGTH_LONG).show();
                            Intent gotoInitial = new Intent(appCompatActivity, InitialScreenActivity.class);
                            gotoInitial.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            gotoInitial.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            appCompatActivity.startActivity(gotoInitial);
                            appCompatActivity.overridePendingTransition (0, 0);
                        }
                        else {
                            Toast.makeText(appCompatActivity, R.string.error_user_creation, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(appCompatActivity, R.string.error_user_creation, Toast.LENGTH_LONG).show();
                    }


                },
                error -> {
                    if (error.networkResponse.statusCode == 409){
                        Toast.makeText(appCompatActivity, R.string.email_repeated, Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(appCompatActivity, R.string.error_user_creation, Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public static void authenticate(Context context, String email, String password) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) context;

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("email", email);
            requestBody.put("password", password);
        } catch (JSONException e) {
            throw new RuntimeException("Error creating JSON object");
        }

        makePOSTRequest(context, "users/login", requestBody,
                response -> {
                    try {
                        token = response.getString("accessToken");
                        updateUser(appCompatActivity, email, password);

                        Intent gotoMain = new Intent(appCompatActivity, MainActivity.class);
                        gotoMain.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        gotoMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        appCompatActivity.startActivity(gotoMain);
                        appCompatActivity.overridePendingTransition (0, 0);

                    }
                    catch (JSONException e) {
                        Toast.makeText(appCompatActivity, R.string.error_login, Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    System.out.println(error.getMessage());
                    Toast.makeText(appCompatActivity, R.string.error_login, Toast.LENGTH_LONG).show();
                }
        );
    }

    private static void updateUser(Context context, String email, String password) {
        String endpoint = "users/search?s=" + email;
        APIClient.makeGETRequest(context, endpoint,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = new JSONObject();
                        int i;
                        for (i = 0; i < jsonArray.length(); i++){
                            if (jsonArray.getJSONObject(i).getString("email").matches(email)){
                                jsonObject = jsonArray.getJSONObject(i);
                                break;
                            }
                        }
                        int id = Integer.parseInt(jsonObject.getString("id"));
                        String name = jsonObject.getString("name");
                        String lastName = jsonObject.getString("last_name");
                        String imageLink = jsonObject.getString("image");

                        MainActivity.updateUser(id, name, lastName, email, password, imageLink);
                    }
                    catch (JSONException e) {
                        MainActivity.updateUser(-1, "", "", "", "", "");
                    }
                },
                error -> {
                    MainActivity.updateUser(-1, "", "", "", "", "");
                }
        );
    }

    public static void makeGETRequest(Context context, String baseUrl, String endpoint,
                                      Response.Listener<String> listener,
                                      Response.ErrorListener errorListener) {

        StringRequest stringRequest =
                new StringRequest(
                        Request.Method.GET,
                        baseUrl + endpoint,
                        listener,
                        errorListener
                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Authorization", "Bearer " + token);
                        return headers;
                    }
                };

        VolleySingleton.getInstance(context).addToQueue(stringRequest);
    }

    public static void makeGETRequest(Context context, String endpoint,
                                      Response.Listener<String> listener,
                                      Response.ErrorListener errorListener) {
        makeGETRequest(context, DEFAULT_API, endpoint, listener, errorListener);
    }

    public static void makePOSTRequest(Context context, String baseUrl, String endpoint, JSONObject requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(
                        Request.Method.POST,
                        baseUrl + endpoint,
                        requestBody,
                        listener,
                        errorListener
                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Authorization", "Bearer " + token);
                        return headers;
                    }
                };

        VolleySingleton.getInstance(context).addToQueue(jsonObjectRequest);
    }

    public static void makePOSTRequest(Context context, String endpoint, JSONObject requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        makePOSTRequest(context, DEFAULT_API, endpoint, requestBody, listener, errorListener);
    }
    public static void makePUTRequest(Context context, String endpoint, JSONObject requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(
                        Request.Method.PUT,
                        DEFAULT_API + endpoint,
                        requestBody,
                        listener,
                        errorListener
                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Authorization", "Bearer " + token);
                        return headers;
                    }
                };

        VolleySingleton.getInstance(context).addToQueue(jsonObjectRequest);
    }
}