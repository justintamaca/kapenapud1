package com.example.kapenapud1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

    private TextView userNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userNameTextView = findViewById(R.id.userfname); // Make sure you have a TextView in your layout with this ID

        // Add an Edit button to allow the user to navigate to the Edit Profile screen
        TextView editProfileText = findViewById(R.id.editprofilehyperlink);
        SpannableString editProfileSpannable = new SpannableString("Edit Profile");
        editProfileSpannable.setSpan(new UnderlineSpan(), 0, editProfileSpannable.length(), 0);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(Profile.this, EditProfile.class);
                startActivity(intent);
                finish();
            }
        };

        editProfileSpannable.setSpan(clickableSpan, 0, editProfileSpannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editProfileText.setText(editProfileSpannable);
        editProfileText.setMovementMethod(LinkMovementMethod.getInstance());
        editProfileText.setHighlightColor(Color.TRANSPARENT);
        editProfileText.setText(editProfileSpannable);

        BottomNavigationView bottomNavigationView = findViewById(R.id.botNatView);
        bottomNavigationView.setSelectedItemId(R.id.bot_profile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bot_home) {
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
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
            }  else if (item.getItemId() == R.id.bot_profile) {
                return true;
            }
            return false;
        });

        // Retrieve the user's name from shared preferences
        String userName = retrieveUserNameFromSharedPreferences();

        // Display the user's name
        if (userName != null) {
            userNameTextView.setText(userName);
        } else {
            userNameTextView.setText("Name not found"); // Handle the case when the name is not in shared preferences
        }
    }

    private String retrieveUserNameFromSharedPreferences() {
        SharedPreferences sharedPref = getSharedPreferences("userData", Context.MODE_PRIVATE);
        return sharedPref.getString("name", null); // Use "null" as the default value if the key is not found
    }
}
