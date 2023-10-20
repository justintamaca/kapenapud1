package com.example.kapenapud1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckoutActivity extends AppCompatActivity {

    private RecyclerView selectedProductsRecyclerView;
    private SelectedProductsAdapter selectedProductsAdapter;
    private List<Product> selectedProducts;
    private List<CartItem> orderItems;
    private String selectedPaymentMethod;
    private double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Initialize back button
        ImageView backButton = findViewById(R.id.checkoutback);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutActivity.this, CartActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Initialize RecyclerView and Adapter for selected products
        selectedProductsRecyclerView = findViewById(R.id.checkoutRecyclerView);
        selectedProductsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve selected products and other data from the intent
        Intent intent = getIntent();
        selectedProducts = intent.getParcelableArrayListExtra("selectedProducts");
        selectedPaymentMethod = intent.getStringExtra("paymentMethod");
        totalPrice = intent.getDoubleExtra("totalPrice", 0.0);

        // Set up the adapter with the selected products
        selectedProductsAdapter = new SelectedProductsAdapter(this, selectedProducts);
        selectedProductsRecyclerView.setAdapter(selectedProductsAdapter);

        // Display total price
        TextView totalPriceTextView = findViewById(R.id.totalprice);
        totalPriceTextView.setText("Total Price: ₱" + String.format("%.2f", totalPrice));

        // Handle the "Place Order" button
        Button placeOrderButton = findViewById(R.id.checkoutButton);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement your order placement logic here
                performCheckout();
            }
        });
    }

    private void performCheckout() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        // Create a Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://kapenapud.com/api/") // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // Create an instance of your ApiService
        ApiService apiService = retrofit.create(ApiService.class);

        // Get the user ID from SharedPreferences and parse it to an int
        int userId = getCurrentUserId();

        // Retrieve the selected payment method based on the user's selection
        int paymentId = getPaymentMethodId(selectedPaymentMethod);

        // Create a list of CartItem objects with the required data
        orderItems = convertToCartItems(selectedProducts);

        // Create a CheckoutRequest object
        CheckoutRequest checkoutRequest = new CheckoutRequest(userId, paymentId, totalPrice, orderItems);

        // Make the POST request to the API
        Call<CheckoutResponse> call = apiService.checkoutOrder(checkoutRequest);

        // Log the data before making the POST request
        Log.d("CheckoutRequest", "User ID: " + userId);
        Log.d("CheckoutRequest", "Payment Method: " + selectedPaymentMethod);
        Log.d("CheckoutRequest", "Total Price: " + totalPrice);
        Log.d("CheckoutRequest", "Selected Products: " + new Gson().toJson(orderItems));

        call.enqueue(new Callback<CheckoutResponse>() {
            @Override
            public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {
                if (response.isSuccessful()) {
                    CheckoutResponse checkoutResponse = response.body();

                    // Log the response data
                    if (checkoutResponse != null) {
                        Log.d("CheckoutResponse", "Order ID: " + checkoutResponse.getOrderId());
                        Log.d("CheckoutResponse", "Message: " + checkoutResponse.getMessage());

                        int orderId = checkoutResponse.getOrderId();
                        String message = checkoutResponse.getMessage();

                        // Handle the successful checkout response (e.g., display order details)
                    }
                } else {
                    // Handle API call error
                    Log.e("CheckoutResponse", "API call failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CheckoutResponse> call, Throwable t) {
                // Handle API call failure
                Log.e("CheckoutResponse", "API call failed with error: " + t.getMessage());
            }
        });
    }

    private int getPaymentMethodId(String selectedPaymentMethod) {
        if ("Cash".equals(selectedPaymentMethod)) {
            return 1;
        } else if ("GCash".equals(selectedPaymentMethod)) {
            return 2;
        }
        return 0; // Replace 0 with a default payment method ID or handle it as needed
    }

    private int getCurrentUserId() {
        SharedPreferences sharedPref = getSharedPreferences("userData", Context.MODE_PRIVATE);
        // Use a default value of 0 in case the user ID is not found or is not an integer
        return sharedPref.getInt("userId", 0);
    }

    private List<CartItem> convertToCartItems(List<Product> products) {
        List<CartItem> cartItems = new ArrayList<>();
        for (Product product : products) {
            cartItems.add(new CartItem(product.getId(), product.getName(),
                    Double.parseDouble(product.getPrice()), product.getQuantity()));
        }
        return cartItems;
    }
}
