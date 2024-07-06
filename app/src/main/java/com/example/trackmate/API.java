package com.example.trackmate;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @GET("login.php")
    Call<String> login(
            @Query("UserID") String userID,
            @Query("Password") String password
    );

    @FormUrlEncoded
    @POST("register.php") // Adjust the endpoint to match the PHP script for user registration
    Call<String> registerUser(
            @Field("UserId") String userID, // Make sure the parameter names match the PHP script
            @Field("Name") String name,
            @Field("Email") String email,
            @Field("Contact") String contact,
            @Field("Password") String password
    );

    @FormUrlEncoded
    @POST("luggage_entry.php")
    Call<String> submitLuggageEntry(
            @Field("UserID") String userID,
            @Field("CompanyName") String companyName,
            @Field("Colour") String colour,
            @Field("Capacity") String capacity,
            @Field("Height") String height,
            @Field("Width") String width
    );

    // Define other API methods here if needed
}
