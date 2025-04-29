package com.example.luxevista;// replace with your package name

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class BottomNavHelper {

    public static void setupBottomNavigation(final Activity activity) {
        ImageView homeButton = activity.findViewById(R.id.homeButton);
        ImageView browseButton = activity.findViewById(R.id.browseButton);
        ImageView bookingsButton = activity.findViewById(R.id.bookingsButton);
        ImageView infoButton = activity.findViewById(R.id.infoButton);

        //top nav options
        ImageView profileButton = activity.findViewById(R.id.profileButton);
        Button browseButtonTop = activity.findViewById(R.id.browseButtonTop);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(activity instanceof HomeActivity)) {
                    activity.startActivity(new Intent(activity, HomeActivity.class));
                }
            }
        });

        browseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(activity instanceof BrowseStaysActivity)) {
                    activity.startActivity(new Intent(activity, BrowseStaysActivity.class));
                }
            }
        });

        browseButtonTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(activity instanceof BrowseStaysActivity)) {
                    activity.startActivity(new Intent(activity, BrowseStaysActivity.class));
                }
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(activity instanceof ProfileActivity)) {
                    activity.startActivity(new Intent(activity, ProfileActivity.class));
                }
            }
        });

        bookingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(activity instanceof ViewBookingsActivity)) {
                    activity.startActivity(new Intent(activity, ViewBookingsActivity.class));
                }
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(activity instanceof InfoActivity)) {
                    activity.startActivity(new Intent(activity, InfoActivity.class));
                }
            }
        });
    }
}
