package com.example.luxevista;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class ViewBookingsActivity extends AppCompatActivity {

    private LinearLayout bookingsContainer;
    private LinearLayout noBookingsLayout;
    private TextView bookingHeader;
    private Button browseStaysButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_bookings); // your layout filename

        bookingsContainer = findViewById(R.id.bookingsContainer);
        noBookingsLayout = findViewById(R.id.noBookingsLayout);
        bookingHeader = findViewById(R.id.bookingHeader);
        browseStaysButton = findViewById(R.id.browseStaysButton);

        List<Booking> bookings = generateSampleBookings(); // Simulated test data

        if (bookings.isEmpty()) {
            noBookingsLayout.setVisibility(View.VISIBLE);
            bookingHeader.setVisibility(View.GONE);
        } else {
            noBookingsLayout.setVisibility(View.GONE);
            bookingHeader.setVisibility(View.VISIBLE);
            bookingsContainer.setVisibility(View.VISIBLE);

            for (Booking booking : bookings) {
                View card = LayoutInflater.from(this).inflate(R.layout.booking_card_layout, bookingsContainer, false);
                TextView bookingSummary = card.findViewById(R.id.bookingSummary);
                bookingSummary.setText(booking.getDate() + " - " + booking.getRoomType());

                card.setOnClickListener(v -> {
                    new AlertDialog.Builder(ViewBookingsActivity.this)
                            .setTitle("Booking Details")
                            .setMessage("Room Number: " + booking.getRoomNumber() +
                                    "\nCheck-in: " + booking.getCheckIn() +
                                    "\nCheck-out: " + booking.getCheckOut() +
                                    "\nGuests: " + booking.getGuestCount())
                            .setPositiveButton("OK", null)
                            .show();
                });

                bookingsContainer.addView(card);
            }
        }

        browseStaysButton.setOnClickListener(v -> {
            // Handle browse stays button logic here
        });
    }

    private List<Booking> generateSampleBookings() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking("2025-05-10", "Deluxe Room", "101", "2:00 PM", "11:00 AM", 2));
        bookings.add(new Booking("2025-06-01", "Suite", "203", "3:00 PM", "12:00 PM", 3));
        bookings.add(new Booking("2025-07-15", "Standard Room", "305", "1:00 PM", "10:00 AM", 1));
        return bookings;
    }
}
