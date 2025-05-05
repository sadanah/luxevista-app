package com.example.luxevista;

import android.content.Intent;
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

        // Setup bottom navigation
        BottomNavHelper.setupBottomNavigation(this);

        // Setup logout button
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> logout());
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

    // Logout method
    private void logout() {
        sessionManager.logoutUser(); // Clears session
        // After logout, go back to LoginActivity (or whatever your login screen is)
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
        startActivity(intent);
        finish();
    }
}
