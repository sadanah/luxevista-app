package com.example.luxevista;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.splash);  // Show the splash screen layout

        // Use a Handler to introduce a delay of 2 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // After 2 seconds, move to LoginActivity
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();  // Close the SplashActivity so it's not shown again when the user presses back
            }
        }, 2000);  // 2000 milliseconds = 2 seconds
    }
}
