package com.example.trackmate;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class firebaseManager {

    private DatabaseReference trackerLocationRef;
    private LocationListener locationListener;

    public firebaseManager() {
        trackerLocationRef = FirebaseDatabase.getInstance().getReference("gps_data");
    }

    public void setLocationListener(LocationListener listener) {
        this.locationListener = listener;
    }

    public void startLocationUpdates() {
        trackerLocationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    double latitude = dataSnapshot.child("latitude").getValue(Double.class);
                    double longitude = dataSnapshot.child("longitude").getValue(Double.class);
                    locationListener.onLocationReceived(new LatLng(latitude, longitude));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    public interface LocationListener {
        void onLocationReceived(LatLng location);
    }
}

