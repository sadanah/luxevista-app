package com.example.luxevista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ViewBookingsActivity extends AppCompatActivity {

    private Button bookDeluxeButton, bookFamilyFiestaButton, bookSecretGetawayButton, bookParadiseLostButton, bookRelaxedStayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_bookings);

        BottomNavHelper.setupBottomNavigation(this);
    }

}
