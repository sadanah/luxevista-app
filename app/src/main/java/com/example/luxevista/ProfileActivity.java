package com.example.luxevista;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView usernameText, emailText, phoneText;
    private DBHelper dbHelper;
    private int userId;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_options); // Use your profile layout here

        dbHelper = new DBHelper(this);
        sessionManager = new SessionManager(this);
        // Assuming you have saved the user_id in SharedPreferences or a Session
        int userId = sessionManager.getUserId();

        // Initialize TextViews
        usernameText = findViewById(R.id.usernameText);
        emailText = findViewById(R.id.emailText);
        phoneText = findViewById(R.id.phoneText);

        // Fetch and display user details
        displayUserDetails(userId);
    }

    private void displayUserDetails(int userId) {
        Cursor cursor = dbHelper.getUserDetails(userId);

        if (cursor != null && cursor.moveToFirst()) {
            // Check if the column indexes are valid
            int usernameIndex = cursor.getColumnIndex("username");
            int emailIndex = cursor.getColumnIndex("email");
            int phoneIndex = cursor.getColumnIndex("phone");

            if (usernameIndex != -1 && emailIndex != -1 && phoneIndex != -1) {
                String username = cursor.getString(usernameIndex);
                String email = cursor.getString(emailIndex);
                String phone = cursor.getString(phoneIndex);

                // Set the values to the TextViews
                usernameText.setText(username);
                emailText.setText(email);
                phoneText.setText(phone);
            } else {
                // Handle case where one or more columns are missing
                Toast.makeText(this, "Required data missing", Toast.LENGTH_SHORT).show();
            }

            cursor.close();
        } else {
            Toast.makeText(this, "User details not found", Toast.LENGTH_SHORT).show();
        }
    }
}

