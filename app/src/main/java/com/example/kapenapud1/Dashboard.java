package com.example.kapenapud1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        TextView welcomeTextView = findViewById(R.id.welcomeTextView);
        BottomNavigationView bottomNavigationView = findViewById(R.id.botNatView);
        bottomNavigationView.setSelectedItemId(R.id.bot_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bot_home) {
                return true;
            } else if (item.getItemId() == R.id.bot_menu) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.bot_cart) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (item.getItemId() == R.id.bot_profile) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }else
            return false;
        });

        String fullName = getUserData();

        // Split the full name into first name and last name
        String[] nameParts = fullName.split(" ");

        if (nameParts.length > 0) {
            // Display only the first name
            String firstName = nameParts[0];
            welcomeTextView.setText("Welcome, " + firstName);
        } else {
            // Handle the case where the name is not properly formatted
            welcomeTextView.setText("Welcome");
        }
    }

    private String getUserData() {
        SharedPreferences sharedPref = getSharedPreferences("userData", Context.MODE_PRIVATE);
        return sharedPref.getString("name", ""); // The second parameter is the default value if the key is not found
    }
}
