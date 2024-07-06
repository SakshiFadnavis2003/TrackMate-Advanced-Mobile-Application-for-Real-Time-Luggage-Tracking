package com.example.trackmate;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    public static String BASE_URL = "http://192.168.29.212:5432/TrackMate_api/";

    private static Retrofit retrofit;

    private RetrofitClient() {
        // Initialize retrofit only if it's null
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public static synchronized Retrofit getInstance() {
        if (retrofit == null) {
            // Corrected line: Assign the newly created instance to retrofit
            retrofit = new RetrofitClient().retrofit;
        }
        return retrofit;
    }

    public API getApi() {
        return retrofit.create(API.class);
    }
}
