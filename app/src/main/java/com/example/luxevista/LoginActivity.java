package com.example.luxevista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Force light mode (disable dark mode)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Enable Edge-to-Edge feature
        EdgeToEdge.enable(this);

        // Set content view to login layout
        setContentView(R.layout.login);

        // Apply system bar insets to ensure proper padding
        View rootView = findViewById(R.id.login); // Make sure the root view has this ID in login.xml
        if (rootView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // Find the Register button by its ID and set an OnClickListener
        Button registerButton = findViewById(R.id.registerButton); // Ensure this ID matches the button in login.xml
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start RegisterActivity when the button is clicked
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Find the EditText fields for username and password
        final EditText usernameEditText = findViewById(R.id.loginUsername); // Ensure this ID matches the EditText in login.xml
        final EditText passwordEditText = findViewById(R.id.loginPassword); // Ensure this ID matches the EditText in login.xml

        // Find the login button
        Button loginButton = findViewById(R.id.loginButton); // Ensure this ID matches the button in login.xml
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the username and password from the EditText fields
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Check if username and password match the predefined values
                if (username.equals("user") && password.equals("pass123")) {
                    // If they match, navigate to MainActivity
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish(); // Optional: Close the LoginActivity to prevent the user from returning to it
                } else {
                    // If they don't match, show an error message
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}