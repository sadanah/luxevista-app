package com.example.luxevista;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
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
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                DBHelper dbHelper = new DBHelper(LoginActivity.this);
                Cursor cursor = dbHelper.getUserData(username, password);

                if (cursor != null && cursor.moveToFirst()) {
                    int loginIdIndex = cursor.getColumnIndex("login_id");
                    int usernameIndex = cursor.getColumnIndex("username");
                    int roleIndex = cursor.getColumnIndex("role");

                    // ✅ Always check if index is valid (not -1)
                    if (loginIdIndex != -1 && usernameIndex != -1 && roleIndex != -1) {
                        int loginId = cursor.getInt(loginIdIndex);
                        String usernameFromDb = cursor.getString(usernameIndex);
                        String role = cursor.getString(roleIndex);

                        // Start session
                        SessionManager sessionManager = new SessionManager(LoginActivity.this);
                        sessionManager.createLoginSession(loginId, usernameFromDb, role);

                        Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 1000);
                    } else {
                        Toast.makeText(LoginActivity.this, "Error reading user data.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }

                if (cursor != null) {
                    cursor.close();
                }

            }
        });
    }
}