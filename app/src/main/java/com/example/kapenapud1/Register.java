package com.example.kapenapud1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private ApiService apiService;
    private EditText fullNameEditText, emailEditText, passwordEditText;
    private TextView userInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView registerTextView = findViewById(R.id.signinredirect);

// Set an OnClickListener to the TextView
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the Register activity
                Intent intent = new Intent(Register.this, Login.class);

                // Start the Register activity
                startActivity(intent);
            }
        });

        // Initialize ApiService
        apiService = ApiClient.getClient().create(ApiService.class);

        // Initialize UI elements
        fullNameEditText = findViewById(R.id.fullname);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        userInfoTextView = findViewById(R.id.userInfoTextView);
        Button signupButton = findViewById(R.id.signup);
        ToggleButton showPasswordToggleButton = findViewById(R.id.showpass);

        // Set a click listener for the signup button
        signupButton.setOnClickListener(view -> {
            // Perform input validations
            String name = fullNameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                userInfoTextView.setText("Please fill in all required fields.");
                return;
            }

            // If input is valid, proceed with registration
            performRegistration(name, email, password);
        });

        // Set a listener to toggle password visibility when the ToggleButton is clicked
        showPasswordToggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Toggle password visibility based on the state of the ToggleButton
            int inputType = isChecked ?
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :
                    InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
            passwordEditText.setInputType(inputType);

            // Move the cursor to the end of the text
            passwordEditText.setSelection(passwordEditText.getText().length());
        });
    }

    private void performRegistration(String fullName, String email, String password) {
        // Check if the password has at least 6 characters
        if (password.length() < 6) {
            userInfoTextView.setText("Password must have at least 6 characters");
            return;
        }

        // Make the API call to register the user
        Call<RegistrationResponse> call = apiService.registerUser(fullName, email, password, 3);

        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.isSuccessful()) {
                    userInfoTextView.setText("Registered successfully!");
                } else {
                    userInfoTextView.setText("Registration failed. Unexpected response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                userInfoTextView.setText("Registration failed. Check your internet connection.");
            }
        });

    }
}