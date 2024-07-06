package com.example.trackmate;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.AlarmClock;
import android.provider.Settings;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.os.Handler;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trackmate.databinding.ActivityMainPageBinding;


public class MainPage extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap myMap;
    private final int FINE_PERMISSION_CODE = 1;
    Location currentLocation;
    private SearchView mapSearchView;
    FusedLocationProviderClient fusedLocationProviderClient;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainPageBinding binding;
    private Button safeguardButton;
    private boolean safeguardEnabled = false;
    private MediaPlayer mediaPlayer;
    private String distance;
    private String time;

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent = getIntent();
        time = intent.getStringExtra("time");
        distance = intent.getStringExtra("distance");

        Location trackerLocation = currentLocation;

        Button directionsButton = findViewById(R.id.directions_button);

        safeguardButton = findViewById(R.id.safeguard_button);
        safeguardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSafeguard();
            }
        });

        // Set OnClickListener to the Button
        directionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call getDirections method with phoneLocation and trackerLocation
                LatLng phoneLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()); // Example phone location
//                LatLng trackerLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                LatLng trackerLocation = new LatLng(21.1499, 79.0864);
                getDirections(phoneLocation, trackerLocation);
            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        setSupportActionBar(binding.appBarMainPage.toolbar);


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_contactus, R.id.nav_faqs,R.id.nav_support,R.id.nav_howtoproceed)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_page);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }


    private void toggleSafeguard() {
        safeguardEnabled = !safeguardEnabled;
        if (safeguardEnabled) {
            startSafeguard();
            safeguardButton.setText("Stop Safeguard");

        } else {
            stopSafeguard();
            safeguardButton.setText("Start Safeguard");

        }
    }

//    private void startSafeguard() {
//        // Implement your safeguard logic here
//        // For example, periodically check the distance between phoneLocation and trackerLocation
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                checkDistance();
//                if (safeguardEnabled) {
//                    // Parse the time string into an integer
//                    int delayMillis = Integer.parseInt(time) * 12000; // Assuming time is in seconds
//
//                    // Repeat this check every 'delayMillis' milliseconds
//                    handler.postDelayed(this, delayMillis);
//                }
//            }
//        }, Integer.parseInt(time) * 12000); // Start after 5 secs (adjust as needed)
//    }

    private void startSafeguard() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkDistance();
                if (safeguardEnabled) {
                    int delayMillis = Integer.parseInt(time) * 12000; // Assuming time is in seconds
                    handler.postDelayed(this, delayMillis);
                }
            }
        }, 5000);
    }



    private void stopSafeguard() {
        // Stop any ongoing safeguard logic
        handler.removeCallbacksAndMessages(null);
    }

    private void checkDistance() {
        if (currentLocation == null) {
            // Handle the case where currentLocation is null
            return;
        }

        // Calculate distance between phone and tracker locations (using dummy values)
        float[] results = new float[1];
        Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(), 21.1499, 79.0864, results);
        float distanceInMeters = results[0];

        if (distance != null) {
            // Parse the distance received from the intent
            float distanceInput = Float.parseFloat(distance.trim());

            if (distanceInMeters > distanceInput) {
                // Trigger alarm or notification
                Toast.makeText(this, "Distance between phone and tracker exceeds!", Toast.LENGTH_SHORT).show();
                startBuzzingAlarm();
                showStopAlarmOption();
            }
        }
    }


    private void showStopAlarmOption() {
        // Here you can implement the logic to show a dialog or a notification with a stop button
        // For example, you can use a AlertDialog to show a dialog with a stop button
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Alarm is buzzing. Do you want to stop it?")
                .setPositiveButton("Stop", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        stopAlarm(); // Call method to stop the alarm
                    }
                })
                .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss(); // Dismiss the dialog
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_page);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void getLastLocation() {
        // Check if location permissions are granted
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request location permissions
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }

        // Check if location services are enabled
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Show a dialog to prompt the user to enable location services
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location Services Disabled");
            builder.setMessage("Please enable location services to use this feature.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Open location settings to allow the user to enable location services
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.create().show();
            return;
        }

        // Get the last known location
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
                    mapFragment.getMapAsync(MainPage.this);
                }
            }
        });
    }


    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(requestCode==FINE_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }
            else {
                Toast.makeText(this,"allow permission access",Toast.LENGTH_SHORT).show();
            }
        }
    }
        @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        // Replace with code to get and display phone and tracker locations
        // For example:
        LatLng phoneLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        LatLng trackerLocation = new LatLng(21.1499, 79.0864);


        MarkerOptions optionsTracker = new MarkerOptions().position(phoneLocation).title("Tracker Location");
        MarkerOptions markerOptions1 = new MarkerOptions().position(trackerLocation)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.briefcase2))
                .title("Tracker");
        myMap.addMarker(markerOptions1);
        MarkerOptions markerOptions2 = new MarkerOptions().position(phoneLocation)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.traveller))
                .title("Tracker");
        myMap.addMarker(markerOptions2);

        // Move camera to show both markers
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(phoneLocation);
        builder.include(trackerLocation);
        LatLngBounds bounds = builder.build();
        myMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));

    }

    private void getDirections(LatLng from, LatLng to) {
        try {
            String fromStr = from.latitude + "," + from.longitude;
            String toStr = to.latitude + "," + to.longitude;
            Uri uri = Uri.parse("https://www.google.com/maps/dir/" + fromStr + "/" + toStr);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }



//    private void startBuzzingAlarm() {
//        // AlarmManager ko initialise karein
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//        // Intent banaye jise AlarmReceiver class ke liye intent diya jayega
//        Intent intent = new Intent(this, AlarmReceiver.class);
//
//        // PendingIntent ko create karein jo AlarmReceiver ko trigger karega
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//    }

    private void startBuzzingAlarm() {
        // Use AlarmManager to schedule a PendingIntent that will start the alarm
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Intent to start the alarm
        Intent intent = new Intent(this, AlarmReceiver.class);

        // Set the default alarm sound
        intent.putExtra(AlarmClock.EXTRA_RINGTONE, AlarmClock.VALUE_RINGTONE_SILENT); // Use the default alarm sound

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Set the alarm to go off immediately
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
    }

    private void stopBuzzingAlarm() {
        // AlarmManager ko initialise karein
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Intent banaye jise AlarmReceiver class ke liye intent diya jayega
        Intent intent = new Intent(this, AlarmReceiver.class);

        // PendingIntent ko create karein jo AlarmReceiver ko trigger karega
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // PendingIntent ko cancel karein
        alarmManager.cancel(pendingIntent);
    }

        private double calculateDistance(LatLng point1, LatLng point2) {
        Location location1 = new Location("");
        location1.setLatitude(point1.latitude);
        location1.setLongitude(point1.longitude);

        Location location2 = new Location("");
        location2.setLatitude(point2.latitude);
        location2.setLongitude(point2.longitude);

        return location1.distanceTo(location2);
    }

    public void stopAlarm() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}

