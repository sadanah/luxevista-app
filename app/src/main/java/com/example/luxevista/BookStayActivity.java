package com.example.luxevista;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
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

        if(Objects.equals(stayType, "Deluxe")){
            price = 75000;
        } else if(Objects.equals(stayType, "Secret Getaway")){
            price = 45000;
        } else if(Objects.equals(stayType, "Paradise Lost")){
            price = 28000;
        } else if(Objects.equals(stayType, "Family Fiesta")){
            price = 35000;
        } else if(Objects.equals(stayType, "Relaxed Stay")){
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
    }

    private void addRoomNumberButtons() {
        // Room numbers 101-109 and 201-209
        for (int i = 101; i <= 109; i++) {
            createRoomButton(i);
        }
        for (int i = 201; i <= 209; i++) {
            createRoomButton(i);
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
        } else {
            // Proceed to payment (You can replace this Toast with real payment activity launch)
            Toast.makeText(this, "Proceeding to payment for rooms: " + selectedRooms.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
