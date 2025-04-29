package com.example.luxevista;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    private RecyclerView recyclerView;
    private TestimonialsAdapter adapter;
    private List<Testimonial> testimonialList;
    private LinearLayout dotIndicatorLayout;  // Layout for dots indicator

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home);

        ImageView offersButton = findViewById(R.id.imageOffers); // Ensure this ID matches the button in login.xml
        offersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start RegisterActivity when the button is clicked
                Intent intent = new Intent(HomeActivity.this, OffersPromoActivity.class);
                startActivity(intent);
            }
        });

        ImageView browseButton = findViewById(R.id.imageBrowse); // Ensure this ID matches the button in login.xml
        browseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start RegisterActivity when the button is clicked
                Intent intent = new Intent(HomeActivity.this, BrowseStaysActivity.class);
                startActivity(intent);
            }
        });

        // Apply insets for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.testimonialsSlider);
        dotIndicatorLayout = findViewById(R.id.dotIndicator);  // Find the dot indicator layout

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        } else {
            Log.e("HomeActivity", "RecyclerView is null");
        }

        // Initialize testimonial list and add sample data
        testimonialList = new ArrayList<>();
        testimonialList.add(new Testimonial(R.drawable.profile1, "This is a great hotel, very friendly staff!"));
        testimonialList.add(new Testimonial(R.drawable.profile2, "The service was excellent. I loved the nature blending with modern luxury."));
        testimonialList.add(new Testimonial(R.drawable.profile3, "The food was good. The hotel ate and left no crumbs."));

        Log.d("HomeActivity", "Testimonials size: " + testimonialList.size());

        // Initialize and set the adapter after the list is populated
        adapter = new TestimonialsAdapter(testimonialList);
        if (recyclerView != null) {
            recyclerView.setAdapter(adapter);
        }

        // Create the dots based on the number of testimonials
        createDotsIndicator(testimonialList.size());

        // Add a scroll listener to update dots
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int currentPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                updateDots(currentPosition);
            }
        });

        BottomNavHelper.setupBottomNavigation(this);

    }

    // Create the dots indicator based on the number of items in the list
    private void createDotsIndicator(int count) {
        for (int i = 0; i < count; i++) {
            TextView dot = new TextView(this);
            dot.setText("•");
            dot.setTextSize(30);
            dot.setTextColor(getResources().getColor(android.R.color.darker_gray)); // Default color for dots
            dotIndicatorLayout.addView(dot);
        }
    }

    // Update the dots based on the current item in the RecyclerView
    private void updateDots(int position) {
        for (int i = 0; i < dotIndicatorLayout.getChildCount(); i++) {
            TextView dot = (TextView) dotIndicatorLayout.getChildAt(i);
            if (i == position) {
                dot.setTextColor(getResources().getColor(android.R.color.holo_blue_light)); // Highlight the current dot
            } else {
                dot.setTextColor(getResources().getColor(android.R.color.darker_gray)); // Default color
            }
        }
    }
}
