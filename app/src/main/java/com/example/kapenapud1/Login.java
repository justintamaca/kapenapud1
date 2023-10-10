package com.example.kapenapud1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private ApiService apiService;
    private EditText emailEditText, passwordEditText;
    private TextView userInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        // Find the TextView by its ID
        TextView registerTextView = findViewById(R.id.registerTextView);

        // Set an OnClickListener to the TextView
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the Register activity
                Intent intent = new Intent(Login.this, Register.class);
                // Start the Register activity
                startActivity(intent);
            }
        });

        // Initialize ApiService
        apiService = ApiClient.getClient().create(ApiService.class);

        // Initialize UI elements
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        userInfoTextView = findViewById(R.id.userInfoTextView1);
        Button loginButton = findViewById(R.id.loginButton);

        // Set a click listener for the login button
        loginButton.setOnClickListener(view -> {
            // Perform input validations
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty()) {
                emailEditText.setError("Email is required");
                return;
            }

            if (!isValidEmail(email)) {
                emailEditText.setError("Invalid email address");
                return;
            }

            if (password.isEmpty()) {
                passwordEditText.setError("Password is required");
                return;
            }

            // If input is valid, proceed with login
            performLogin(email, password);
        });
    }

    private void performLogin(String email, String password) {
        // Make the API call to login the user
        Call<LoginResponse> call = apiService.loginUser(email, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null && loginResponse.isSuccess()) {
                        // Login successful
                        // You can access user-specific data here if provided by the API
                        // For example, loginResponse.getUserData() if it's available
                        userInfoTextView.setText("Login successful!");

                        // Navigate to the main application screen or other relevant activity
                        // Intent mainIntent = new Intent(Login.this, MainActivity.class);
                        // startActivity(mainIntent);
                    } else {
                        // Login failed (handle error message from loginResponse)
                        userInfoTextView.setText("Login failed. Error: " + loginResponse.getError());
                    }
                } else {
                    // Handle error (e.g., network error, server error)
                    userInfoTextView.setText("Login failed. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Handle failure (e.g., network error)
                userInfoTextView.setText("Login failed. Check your internet connection.");
            }
        });
    }

    private boolean isValidEmail(String email) {
        // Implement email validation logic here (e.g., using regex)
        // For simplicity, we check if the email contains "@" symbol
        return email.contains("@");
    }
}
