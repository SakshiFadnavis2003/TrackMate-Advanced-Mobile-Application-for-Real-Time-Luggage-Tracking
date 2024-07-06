package com.example.trackmate;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;

public class AlarmReceiver extends BroadcastReceiver {
    private MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Handle the alarm event here
        Toast.makeText(context, "Alarm triggered!", Toast.LENGTH_SHORT).show();

        // Play the alarm sound
        playAlarmSound(context);


    }

    private void playAlarmSound(Context context) {
        // Get the default alarm sound URI
        Uri defaultAlarmSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        // Create a MediaPlayer object to play the sound
        mediaPlayer = MediaPlayer.create(context, defaultAlarmSoundUri);

        // Set looping to true if you want the sound to repeat
        mediaPlayer.setLooping(false);

        // Start playing the sound
        mediaPlayer.start();
    }




}
