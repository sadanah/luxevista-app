package com.example.luxevista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private Button bookDeluxeButton, bookFamilyFiestaButton, bookSecretGetawayButton, bookParadiseLostButton, bookRelaxedStayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_stays);

        // Find buttons
        bookDeluxeButton = findViewById(R.id.book_deluxe);
        bookSecretGetawayButton = findViewById(R.id.book_secret_getaway);
        bookParadiseLostButton = findViewById(R.id.book_paradise_lost);
        bookFamilyFiestaButton = findViewById(R.id.book_family_fiesta);
        bookRelaxedStayButton = findViewById(R.id.book_relaxed_stay);

        // Set click listeners
        bookDeluxeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBookStayActivity("Deluxe");
            }
        });

        bookSecretGetawayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBookStayActivity("Secret Getaway");
            }
        });

        bookParadiseLostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBookStayActivity("Paradise Lost");
            }
        });

        bookFamilyFiestaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBookStayActivity("Family Fiesta");
            }
        });

        bookRelaxedStayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBookStayActivity("Relaxed Stay");
            }
        });

        BottomNavHelper.setupBottomNavigation(this);
    }

    private void openBookStayActivity(String stayType) {
        Intent intent = new Intent(ProfileActivity.this, BookStayActivity.class);
        intent.putExtra("stay_type", stayType);
        startActivity(intent);
    }
}
