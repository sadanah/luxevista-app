package com.example.luxevista;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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
    private DBHelper dbHelper; // Database helper instance
    private SessionManager sessionManager; // For getting user_id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_bookings);

        bookingsContainer = findViewById(R.id.bookingsContainer);
        noBookingsLayout = findViewById(R.id.noBookingsLayout);
        bookingHeader = findViewById(R.id.bookingHeader);
        browseStaysButton = findViewById(R.id.browseStaysButton);

        dbHelper = new DBHelper(this);
        sessionManager = new SessionManager(this);

        int userId = sessionManager.getUserId(); // get the current logged-in user_id
        Log.d("ViewBookings", "Fetched userId: " + userId);

        List<Booking> bookings = fetchUserBookings(userId); // Fetch from database
        Log.d("ViewBookings", "Bookings fetched count: " + bookings.size());

        if (bookings.isEmpty()) {
            noBookingsLayout.setVisibility(View.VISIBLE);
            bookingHeader.setVisibility(View.GONE);
            bookingsContainer.setVisibility(View.GONE);
        } else {
            noBookingsLayout.setVisibility(View.GONE);
            bookingHeader.setVisibility(View.VISIBLE);
            bookingsContainer.setVisibility(View.VISIBLE);

            for (Booking booking : bookings) {
                // Inflate the booking card layout
                View card = LayoutInflater.from(this).inflate(R.layout.booking_card_layout, bookingsContainer, false);

                TextView bookingSummary = card.findViewById(R.id.bookingSummary);
                String stayType = null;
                if(booking.getStayType()=="D"){
                    stayType = "Deluxe Stay";
                }else if(booking.getStayType()=="SG"){
                    stayType = "Secret Getaway";
                }else if(booking.getStayType()=="PL"){
                    stayType = "Paradise Lost";
                }else if(booking.getStayType()=="FF"){
                    stayType = "Family Fiesta";
                }else{
                    stayType = "Relaxed Stay";
                }

                bookingSummary.setText(stayType + " - " + booking.getCheckIn());

                // Locate the View Details button and set the click listener
                Button viewDetailsButton = card.findViewById(R.id.viewDetailsButton);
                viewDetailsButton.setOnClickListener(v -> {
                    // Display the booking details in an AlertDialog
                    new AlertDialog.Builder(ViewBookingsActivity.this)
                            .setTitle("Booking Details")
                            .setMessage("Room Type: " + booking.getStayType() +
                                    "\nCheck-in: " + booking.getCheckIn() +
                                    "\nCheck-out: " + booking.getCheckOut() +
                                    "\nGuests: " + booking.getGuestCount())
                            .setPositiveButton("OK", null)
                            .show();
                });

                // Add the card to the bookings container
                bookingsContainer.addView(card);
            }
        }

        BottomNavHelper.setupBottomNavigation(this);
    }


    // 🔥 Fetch user bookings from DB
    private List<Booking> fetchUserBookings(int userId) {
        List<Booking> bookings = new ArrayList<>();

        // Join query to fetch relevant data from bookings, stays, and payments
        String query = "SELECT b.booking_id, b.check_in, b.check_out, b.person_count, " +
                "s.stay_type, p.pay_amount, p.pay_provider " +
                "FROM bookings b " +
                "JOIN stays s ON b.stay_id = s.stay_id " +
                "JOIN payments p ON b.pay_id = p.pay_id " +
                "WHERE b.user_id = ?";

        // Execute the query
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, new String[]{String.valueOf(userId)});
        Log.d("ViewBookings", "Cursor count: " + cursor.getCount());


        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Create a new Booking object for each result
                Booking booking = new Booking();

                // Get the column index for each column in the result set
                int bookingIdIndex = cursor.getColumnIndex("booking_id");
                int checkInIndex = cursor.getColumnIndex("check_in");
                int checkOutIndex = cursor.getColumnIndex("check_out");
                int guestCountIndex = cursor.getColumnIndex("person_count");
                int stayTypeIndex = cursor.getColumnIndex("stay_type");
                int payAmountIndex = cursor.getColumnIndex("pay_amount");
                int payProviderIndex = cursor.getColumnIndex("pay_provider");

                // Check if the column indexes are valid (i.e., greater than or equal to 0)
                if (bookingIdIndex != -1) {
                    booking.setBookingId(cursor.getInt(bookingIdIndex));
                }
                if (checkInIndex != -1) {
                    booking.setCheckIn(cursor.getString(checkInIndex));
                }
                if (checkOutIndex != -1) {
                    booking.setCheckOut(cursor.getString(checkOutIndex));
                }
                if (guestCountIndex != -1) {
                    booking.setGuestCount(cursor.getInt(guestCountIndex));
                }
                if (stayTypeIndex != -1) {
                    booking.setStayType(cursor.getString(stayTypeIndex));
                }
                if (payAmountIndex != -1) {
                    booking.setPayAmount(cursor.getDouble(payAmountIndex));
                }
                if (payProviderIndex != -1) {
                    booking.setPayProvider(cursor.getString(payProviderIndex));
                }

                // Add the booking to the list
                bookings.add(booking);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return bookings;
    }


}
