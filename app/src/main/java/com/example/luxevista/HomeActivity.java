package com.example.luxevista;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    // Declare RecyclerView, Adapter, and List variables
    private RecyclerView recyclerView;
    private TestimonialsAdapter adapter;
    private List<Testimonial> testimonialList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home);

        // Apply insets for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.testimonialsSlider);
        if (recyclerView != null) {
            // Set the layout manager (HORIZONTAL)
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        } else {
            Log.e("HomeActivity", "RecyclerView is null");
        }

        // Initialize testimonial list and add sample data
        testimonialList = new ArrayList<>();
        testimonialList.add(new Testimonial(R.drawable.logo_icon, "This is a great hotel!"));
        testimonialList.add(new Testimonial(R.drawable.logo_icon, "The service was excellent."));
        testimonialList.add(new Testimonial(R.drawable.logo_icon, "The food was good."));

        Log.d("HomeActivity", "Testimonials size: " + testimonialList.size());

        // Initialize and set the adapter after the list is populated
        adapter = new TestimonialsAdapter(testimonialList);
        if (recyclerView != null) {
            recyclerView.setAdapter(adapter);
        }
    }
}
