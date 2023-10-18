package com.example.kapenapud1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {
    private ApiService apiService;
    private TextView nameEditText;  // EditText for editing name
    private TextView emailEditText;  // EditText for editing email
    private TextView genderEditText;  // EditText for editing email
    private TextView contactEditText;  // EditText for editing email
    private TextView bdayEditText;  // EditText for editing email

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        apiService = RetrofitClient.getClient().create(ApiService.class);

        nameEditText = findViewById(R.id.nameeditprof);  // Initialize EditText
        emailEditText = findViewById(R.id.emaileditprof);  // Initialize EditText
        genderEditText = findViewById(R.id.gendereditprof);
        contactEditText = findViewById(R.id.contacteditprof);
        bdayEditText = findViewById(R.id.bdayeditprof);

        ImageView backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfile.this, Profile.class);
                startActivity(intent);
                finish();
            }
        });  // Close the setOnClickListener method here

        loadUserData();
    }

    private void loadUserData() {
        SharedPreferences sharedPref = getSharedPreferences("userData", Context.MODE_PRIVATE);
        String storedEmail = sharedPref.getString("email", "");
        String storedName = sharedPref.getString("name", "");

        nameEditText.setText(storedName);
        emailEditText.setText(storedEmail);  // Display the retrieved email

        Call<CustomerResponse> customerDataCall = apiService.getCustomers();
        customerDataCall.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CustomerResponse customerResponse = response.body();
                    List<Customer> customers = customerResponse.getData();

                    // Find the customer with a matching email
                    Customer loggedInCustomer = findCustomerByEmail(customers, storedEmail);

                    if (loggedInCustomer != null) {
                        // Extract the specific customer details you want to display
                        String gender = loggedInCustomer.getGender();
                        String birthday = loggedInCustomer.getBirthday();
                        String contactNo = loggedInCustomer.getContact_no();
                        String avatar = loggedInCustomer.getAvatar();

                        Log.d("Debug", "Gender: " + gender);
                        Log.d("Debug", "Birthday: " + birthday);
                        Log.d("Debug", "Contact No: " + contactNo);

                        genderEditText.setText("Gender: " + gender);
                        bdayEditText.setText("Birthday: " + birthday);
                        contactEditText.setText("Contact Number: " + contactNo);
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
                // Handle the API request failure
            }
        });
    }

    private Customer findCustomerByEmail(List<Customer> customers, String email) {
        for (Customer customer : customers) {
            if (customer != null && customer.getEmail() != null && customer.getEmail().equalsIgnoreCase(email)) {
                return customer;
            }
        }
        return null; // Customer not found
    }


    private void saveUserData() {
        // Get the edited name and email from EditText fields
        String updatedName = nameEditText.getText().toString();
        String updatedEmail = emailEditText.getText().toString();

        // Update SharedPreferences with the edited data
        SharedPreferences sharedPref = getSharedPreferences("userData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("name", updatedName);  // Update name
        editor.putString("email", updatedEmail);  // Update email
        editor.apply();
    }

    // Add an onClick method for the "Save" button in your XML layout
    public void onSaveButtonClicked(View view) {
        // Save the edited user data
        saveUserData();

        // Now, you can send an API request to update the user's data on the server
        // Create an instance of UserProfileUpdate with updated data and call the API
        // You can use the performProfileUpdate method as mentioned in a previous response

        // After the profile update is successful, you can display a success message
    }
}
