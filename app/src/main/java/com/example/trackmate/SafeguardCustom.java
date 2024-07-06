package com.example.trackmate;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class SafeguardCustom extends AppCompatActivity {

    EditText timeEditText;
    EditText distanceEditText;
    Button submitButton;
    TextView skipCustomizationTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_safeguard_custom);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        timeEditText = findViewById(R.id.time_edittext);
        distanceEditText = findViewById(R.id.distance_edittext);
        submitButton = findViewById(R.id.subbutton);
        skipCustomizationTextView = findViewById(R.id.skipCustomizationTextView);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = timeEditText.getText().toString();
                String distance = distanceEditText.getText().toString();
                Intent intent = new Intent(SafeguardCustom.this, MainPage.class);
                intent.putExtra("time", time);
                intent.putExtra("distance", distance);
                startActivity(intent);
            }
        });

        skipCustomizationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String defaultTime = "10";
                String defaultDistance = "5";
                Intent intent = new Intent(SafeguardCustom.this, MainPage.class);
                intent.putExtra("time", defaultTime);
                intent.putExtra("distance", defaultDistance);
                startActivity(intent);
            }
        });
    }
}