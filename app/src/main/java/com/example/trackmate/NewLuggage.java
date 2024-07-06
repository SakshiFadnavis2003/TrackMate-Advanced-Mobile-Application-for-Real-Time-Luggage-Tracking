package com.example.trackmate;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NewLuggage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_luggage);

        // Find the "Add luggage" button
        Button addLuggageButton = findViewById(R.id.buttonAddLuggage);

        // Set an OnClickListener to the button
        addLuggageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the LuggageEntries activity
                Intent intent = new Intent(NewLuggage.this, LuggageEntries.class);

                // Start the LuggageEntries activity
                startActivity(intent);
            }
        });

        // Find the "Check here!" TextView
        TextView checkHereTextView = findViewById(R.id.textViewCheckHere);

        // Set an OnClickListener to the TextView
        checkHereTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the DisplayInfo activity
                Intent intent = new Intent(NewLuggage.this, SafeguardCustom.class);

                // Start the DisplayInfo activity
                startActivity(intent);
            }
        });

        TextView textViewHeadtoSupport = findViewById(R.id.textViewHeadtoSupport);

        // Set an OnClickListener to the TextView
        textViewHeadtoSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the DisplayInfo activity
                Intent intent = new Intent(NewLuggage.this, SupportActivity.class);

                // Start the DisplayInfo activity
                startActivity(intent);
            }
        });
    }
}
