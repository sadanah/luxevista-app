package com.example.luxevista;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register); // Make sure register.xml is properly set up

        Button loginButton = findViewById(R.id.loginButton); // Ensure this ID matches the button in login.xml
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start RegisterActivity when the button is clicked
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Find the EditText fields for username and password
        final EditText emailInput = findViewById(R.id.emailInput); // Ensure this ID matches the EditText in login.xml
        final EditText phoneInput = findViewById(R.id.phoneInput); // Ensure this ID matches the EditText in login.xml
        final EditText usernameInput = findViewById(R.id.usernameInput);
        final EditText passwordInput = findViewById(R.id.passwordInput);
        final EditText passwordConInput = findViewById(R.id.passwordConInput);

        // Find the login button
        Button registerButton = findViewById(R.id.registerButton); // Ensure this ID matches the button in login.xml
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the username and password from the EditText fields
                String email = emailInput.getText().toString();
                String phone = phoneInput.getText().toString();
                String username = usernameInput.getText().toString();
                String password = passwordConInput.getText().toString();
                String passwordCon = passwordConInput.getText().toString();

                boolean success=false;

                // Check if username and password match the predefined values
                if(email.isEmpty() || phone.isEmpty() || username.isEmpty() || password.isEmpty() || passwordCon.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Enter all details", Toast.LENGTH_SHORT).show();
                    return;
                } else if(!isValidEmail(email)){
                    Toast.makeText(RegisterActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!isValidPhone(phone)){
                    Toast.makeText(RegisterActivity.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!(password.equals(passwordCon))) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                } else{
                    success=true;
                }

                if(success == true){
                    // If they match, navigate to MainActivity
                    DBHelper dbHelper = new DBHelper(RegisterActivity.this);
                    dbHelper.addRegistration(email, phone, username, password, "Customer");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            // After 2 seconds, move to LoginActivity
                              // Close the SplashActivity so it's not shown again when the user presses back
                        }
                    }, 2000);

                }

            }
        });

    }
    // Method to check if the email is valid (contains "@" and ".")
    private boolean isValidEmail(String email) {
        // Regex for simple email validation
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Method to check if the phone number is valid (only digits, starts with an optional "+")
    private boolean isValidPhone(String phone) {
        // Regex for phone validation (optional "+" at the start, followed by digits)
        String phonePattern = "^[+]?[0-9]*$";
        Pattern pattern = Pattern.compile(phonePattern);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}
