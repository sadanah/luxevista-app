package com.example.luxevista;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ExploreSFActivity extends AppCompatActivity {

    private Button bookDeluxeButton, bookFamilyFiestaButton, bookSecretGetawayButton, bookParadiseLostButton, bookRelaxedStayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explore_sf);

        BottomNavHelper.setupBottomNavigation(this);

    }

}
