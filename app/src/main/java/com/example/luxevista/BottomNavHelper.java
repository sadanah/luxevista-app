package com.example.luxevista;// replace with your package name

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

public class BottomNavHelper {

    public static void setupBottomNavigation(final Activity activity) {
        ImageView homeButton = activity.findViewById(R.id.homeButton);
        ImageView browseButton = activity.findViewById(R.id.browseButton);
        ImageView bookingsButton = activity.findViewById(R.id.bookingsButton);
        ImageView infoButton = activity.findViewById(R.id.infoButton);

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
