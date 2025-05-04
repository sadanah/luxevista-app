package com.example.luxevista;


import android.view.LayoutInflater;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class BookStayActivity extends AppCompatActivity {

    private TextView stayTypeTextView, selectedRoomsTextView, priceTextView;
    private GridLayout roomNumbersGrid;
    private List<Integer> selectedRooms = new ArrayList<>();
    private Button makePaymentButton;
    private int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_stay);

        stayTypeTextView = findViewById(R.id.stay_type_text);
        priceTextView = findViewById(R.id.price_textview);
        selectedRoomsTextView = findViewById(R.id.selected_rooms_textview);
        roomNumbersGrid = findViewById(R.id.room_numbers_grid);
        makePaymentButton = findViewById(R.id.make_payment);

        // Set the stay type from Intent
        String stayType = getIntent().getStringExtra("stay_type");
        if (stayType != null) {
            stayTypeTextView.setText("Room Type: " + stayType);
        }

        EditText startDateEditText = findViewById(R.id.start_date);
        EditText endDateEditText = findViewById(R.id.end_date);

        startDateEditText.setOnClickListener(v -> showDatePickerDialog(startDateEditText));
        endDateEditText.setOnClickListener(v -> showDatePickerDialog(endDateEditText));

        if (Objects.equals(stayType, "Deluxe")) {
            price = 75000;
        } else if (Objects.equals(stayType, "Secret Getaway")) {
            price = 45000;
        } else if (Objects.equals(stayType, "Paradise Lost")) {
            price = 28000;
        } else if (Objects.equals(stayType, "Family Fiesta")) {
            price = 35000;
        } else if (Objects.equals(stayType, "Relaxed Stay")) {
            price = 18000;
        }

        String priceText = getIntent().getStringExtra("stay_type");
        if (priceText != null) {
            priceTextView.setText("Price: " + price);
        }

        // Generate room number buttons
        addRoomNumberButtons();

        // Make Payment Button
        makePaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePayment();
            }
        });

        BottomNavHelper.setupBottomNavigation(this);
    }

    private void showDatePickerDialog(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    String selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
                    editText.setText(selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void addRoomNumberButtons() {
        DBHelper dbHelper = new DBHelper(this);

        // Map stay types to database codes
        String stayTypeText = getIntent().getStringExtra("stay_type");
        String stayTypeCode = mapStayTypeToCode(stayTypeText);

        // Get available room numbers from DB
        List<Integer> availableRooms = dbHelper.getAvailableRooms(stayTypeCode);

        for (int roomNumber : availableRooms) {
            createRoomButton(roomNumber);
        }
    }

    private String mapStayTypeToCode(String stayTypeText) {
        switch (stayTypeText) {
            case "Deluxe": return "D";
            case "Secret Getaway": return "SG";
            case "Paradise Lost": return "PL";
            case "Family Fiesta": return "FF";
            case "Relaxed Stay": return "RS";
            default: return ""; // Unknown type
        }
    }


    private void createRoomButton(final int roomNumber) {
        final Button roomButton = new Button(this);
        roomButton.setText(String.valueOf(roomNumber));
        roomButton.setTag(roomNumber);
        roomButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        // Set layout params if needed
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = GridLayout.LayoutParams.WRAP_CONTENT;
        params.height = GridLayout.LayoutParams.WRAP_CONTENT;
        params.setMargins(8, 8, 8, 8);
        roomButton.setLayoutParams(params);

        roomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedRooms.contains(roomNumber)) {
                    // Deselect
                    selectedRooms.remove(Integer.valueOf(roomNumber));
                    roomButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                } else {
                    // Select
                    selectedRooms.add(roomNumber);
                    roomButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                }
                updateSelectedRoomsText();
            }
        });

        roomNumbersGrid.addView(roomButton);
    }

    private void updateSelectedRoomsText() {
        if (selectedRooms.isEmpty()) {
            selectedRoomsTextView.setText("Selected Rooms: None");
            priceTextView.setText("Price: 0");
        } else {
            selectedRoomsTextView.setText("Selected Rooms: " + selectedRooms.toString().replace("[", "").replace("]", ""));
            int totalPrice = price * selectedRooms.size();
            priceTextView.setText("Price: " + totalPrice);
        }
    }

    private void makePayment() {
        if (selectedRooms.isEmpty()) {
            Toast.makeText(this, "Please select at least one room.", Toast.LENGTH_SHORT).show();
            return;
        }

        EditText startDateEditText = findViewById(R.id.start_date);
        EditText endDateEditText = findViewById(R.id.end_date);

        String checkIn = startDateEditText.getText().toString();
        String checkOut = endDateEditText.getText().toString();

        if (checkIn.isEmpty() || checkOut.isEmpty()) {
            Toast.makeText(this, "Please select check-in and check-out dates.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Calculate days between check-in and check-out
        int days = calculateDaysBetween(checkIn, checkOut);
        if (days <= 0) {
            Toast.makeText(this, "Invalid date range.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Calculate total price
        int totalPrice = calculateTotalPrice(price, days, selectedRooms.size());

        // Show Confirmation Dialog
        showConfirmationDialog(checkIn, checkOut, days, totalPrice);
    }

    private int calculateDaysBetween(String checkIn, String checkOut) {
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            java.util.Date startDate = sdf.parse(checkIn);
            java.util.Date endDate = sdf.parse(checkOut);

            long diff = endDate.getTime() - startDate.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            return (int) days;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int calculateTotalPrice(int basePrice, int days, int roomCount) {
        // Every extra day adds 50% of base price
        int totalPricePerRoom = basePrice + (int)((days - 1) * (basePrice * 0.5));
        return totalPricePerRoom * roomCount;
    }

    private void showConfirmationDialog(String checkIn, String checkOut, int days, int totalPrice) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Confirm Booking");

        // Inflate custom layout
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_confirm_booking, null);
        builder.setView(dialogView);

        // Get references
        TextView bookingDetailsText = dialogView.findViewById(R.id.bookingDetailsText);
        RadioGroup paymentOptionsGroup = dialogView.findViewById(R.id.paymentOptionsGroup);

        // Set booking details text
        String message = "Rooms: " + selectedRooms.toString() + "\n" +
                "Check-in: " + checkIn + "\n" +
                "Check-out: " + checkOut + "\n" +
                "Nights: " + days + "\n" +
                "Total Price: " + totalPrice;
        bookingDetailsText.setText(message);

        final int[] selectedPayIndex = {1}; // default PayPal

        paymentOptionsGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.paypalOption) {
                selectedPayIndex[0] = 0;  // Paypal (index 0)
            } else if (checkedId == R.id.payoneerOption) {
                selectedPayIndex[0] = 1;  // Payoneer (index 1)
            } else if (checkedId == R.id.cardOption) {
                selectedPayIndex[0] = 2;  // Card (index 2)
            }
        });

        builder.setPositiveButton("Confirm", (dialog, which) -> {
            // Save booking with selected payment option
            saveBookingsToDB(checkIn, checkOut, selectedPayIndex[0]);
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }



    private void saveBookingsToDB(String checkIn, String checkOut, int payProviderIndex) {
        DBHelper dbHelper = new DBHelper(this);

        int userId = 1; // Replace with actual logged-in user ID
        int personCount = 2; // Or let user select persons
        String[] paymentProviders = {"PayPal", "Payoneer", "Credit Card"};
        String selectedProvider = paymentProviders[payProviderIndex];

        // Calculate total price
        int days = calculateDaysBetween(checkIn, checkOut);
        int totalPrice = calculateTotalPrice(price, days, selectedRooms.size());

        // 1. Insert Payment record and get pay_id
        int payId = dbHelper.addPayment(totalPrice, selectedProvider);
        if (payId == -1) {
            Toast.makeText(this, "Payment failed. Booking canceled.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2. For each selected room → insert booking + update stay availability
        for (int stayId : selectedRooms) {
            // 2a. Insert booking record
            dbHelper.addBooking(userId, stayId, payId, checkIn, checkOut, personCount);

            // 2b. Update stay availability
            dbHelper.updateStayAvailability(stayId, false); // false = booked
        }

        Toast.makeText(this, "Booking Successful!", Toast.LENGTH_SHORT).show();

        // Optionally: clear selected rooms and reset UI
        selectedRooms.clear();
    }


    private int mapStayTypeToStayId(String stayType) {
        switch (stayType) {
            case "Deluxe": return 1;
            case "Secret Getaway": return 2;
            case "Paradise Lost": return 3;
            case "Family Fiesta": return 4;
            case "Relaxed Stay": return 5;
            default: return 0;
        }
    }


}
