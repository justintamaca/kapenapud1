package com.example.kapenapud1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private ApiService apiService;
    private ApiService apiService1;
    private EditText emailEditText, passwordEditText;
    private TextView userInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        TextView registerTextView = findViewById(R.id.registerTextView);

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        // Initialize ApiService
        apiService = ApiClient.getClient().create(ApiService.class);
        apiService1 = RetrofitClient.getClient().create(ApiService.class);

        // Initialize UI elements
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        String enteredPassword = passwordEditText.getText().toString();
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

            if (password.isEmpty()) {
                passwordEditText.setError("Password is required");
                return;
            }

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
                    if (loginResponse != null) {
                        String message = loginResponse.getMessage();
                        if (loginResponse.isSuccess()) {
                            userInfoTextView.setText("Login successful!");

                            // Make a separate API request to get the user's data
                            Call<UserResponse> userDataCall = apiService1.getUserData();
                            userDataCall.enqueue(new Callback<UserResponse>() {
                                @Override
                                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        UserResponse userResponse = response.body();
                                        List<User> users = userResponse.getData();

                                        // Find the user with matching login credentials
                                        User loggedInUser = findUserByEmail(users, email);

                                        if (loggedInUser != null) {
                                            // Store user data in shared preferences
                                            storeUserData(loggedInUser);



                                            // Navigate to the Dashboard
                                            Intent intent = new Intent(Login.this, Dashboard.class);
                                            startActivity(intent);
                                        } else {
                                            // Handle the case when the user is not found
                                            userInfoTextView.setText("User not found.");
                                        }
                                    } else {
                                        // Handle API error
                                        userInfoTextView.setText("Login failed. Check your internet connection.");
                                    }
                                }

                                @Override
                                public void onFailure(Call<UserResponse> call, Throwable t) {
                                    userInfoTextView.setText("Login failed. Check your internet connection.");
                                }
                            });
                        } else {
                            userInfoTextView.setText("Login failed. Error: " + message);
                        }
                    } else {
                        userInfoTextView.setText("Login failed. Please try again later.");
                    }
                } else {
                    userInfoTextView.setText("Login failed. Check your internet connection.");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                userInfoTextView.setText("Login failed. Check your internet connection.");
            }
        });
    }

    private User findUserByEmail(List<User> users, String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null; // User not found
    }

    private void storeUserData(User user) {
        SharedPreferences sharedPref = getSharedPreferences("userData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("userId", user.getUserId());
        editor.putString("name", user.getName());
        editor.putString("email", user.getEmail());
        editor.putString("password", user.getPassword());
        editor.apply();
    }
}
