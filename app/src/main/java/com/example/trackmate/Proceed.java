package com.example.trackmate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.trackmate.ui.Proceed.ProceedFragment;

public class Proceed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceed);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ProceedFragment.newInstance())
                    .commitNow();
        }
    }
}