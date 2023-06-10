package com.example.socialgift.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class APIClient {
    private static final String BASE_URL = "https://balandrau.salle.url.edu/i3/socialgift/api/v1/";
    private static String token = "";

    public static void authenticate(Context context, String email, String password) throws RuntimeException {

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
                    } catch (JSONException e) {
                        throw new RuntimeException("Error parsing JSON object");
                    }
                },
                error -> {
                    throw new RuntimeException("Error authenticating");
                }
        );
    }

    public static void makeGETRequest(Context context, String endpoint, Response.Listener<String> listener, Response.ErrorListener errorListener) {

        StringRequest stringRequest =
                new StringRequest(
                        Request.Method.GET,
                        BASE_URL + endpoint,
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

    public static void makePOSTRequest(Context context, String endpoint, JSONObject requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(
                        Request.Method.POST,
                        BASE_URL + endpoint,
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