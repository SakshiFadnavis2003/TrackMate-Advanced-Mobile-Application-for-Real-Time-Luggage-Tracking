package com.example.trackmate;
import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiCalls {

    public interface LoginCallback {
        void onSuccess(String userID);
        void onError(String message);
    }

    public static void loginUser(Context context, String userID, String password, final LoginCallback callback) {
        String url = "http://192.168.29.212:5432/TrackMate_API/login.php?UserID=" + userID + "&Password=" + password;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String userID = jsonObject.getString("UserID");
                            callback.onSuccess(userID);
                        } catch (JSONException e) {
                            callback.onError("Error parsing response");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError("Error connecting to server");
            }
        });

        ApiService.getInstance(context).addToRequestQueue(stringRequest);
    }

    // Similar methods for registerUser and submitLuggage
    public interface RegisterCallback {
        void onSuccess();
        void onError(String message);
    }

    public static void registerUser(Context context, String name, String email, String contactNo, String username, String password, final RegisterCallback callback) {
        String url = "http://192.168.29.212:5432/TrackMate_API/register.php?Name=" + name + "&Email=" + email + "&Contact=" + contactNo + "&Username=" + username + "&Password=" + password;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Assuming your PHP script returns "success" upon successful registration
                        if (response.equals("success")) {
                            callback.onSuccess();
                        } else {
                            callback.onError("Registration failed");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError("Error connecting to server");
            }
        });

        ApiService.getInstance(context).addToRequestQueue(stringRequest);
    }

    public interface LuggageSubmitCallback {
        void onSuccess();
        void onError(String message);
    }

    public static void submitLuggage(Context context, String userID, String companyName, String colour, String capacity, String height, String width, final LuggageSubmitCallback callback) {
        String url = "http://192.168.29.212:5432/TrackMate_API/luggage.php?UserID=" + userID + "&CompanyName=" + companyName + "&Colour=" + colour + "&Capacity=" + capacity + "&Height=" + height + "&Width=" + width;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Assuming your PHP script returns "success" upon successful luggage submission
                        if (response.equals("success")) {
                            callback.onSuccess();
                        } else {
                            callback.onError("Luggage submission failed");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError("Error connecting to server");
            }
        });

        ApiService.getInstance(context).addToRequestQueue(stringRequest);
    }
}
