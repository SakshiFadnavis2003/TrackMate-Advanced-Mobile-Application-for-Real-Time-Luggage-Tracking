package com.example.trackmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        TextView HowToProceedTextView = findViewById(R.id.textViewHowToProceed);

        // Set an OnClickListener to the TextView
        HowToProceedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the DisplayInfo activity
                Intent intent = new Intent(SupportActivity.this, HowToProceed.class);

                // Start the DisplayInfo activity
                startActivity(intent);
            }
        });

        TextView FAQsTextView = findViewById(R.id.textViewFAQs);

        // Set an OnClickListener to the TextView
        FAQsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the DisplayInfo activity
                Intent intent = new Intent(SupportActivity.this, faqs.class);

                // Start the DisplayInfo activity
                startActivity(intent);
            }
        });

        TextView ConctactusTextView = findViewById(R.id.textViewContactUs);

        // Set an OnClickListener to the TextView
        ConctactusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the DisplayInfo activity
                Intent intent = new Intent(SupportActivity.this, contactus.class);

                // Start the DisplayInfo activity
                startActivity(intent);
            }
        });
    }
}