package com.example.kapenapud1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartActivity extends AppCompatActivity {
    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;
    private Spinner paymentMethodSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Spinner paymentMethodSpinner = findViewById(R.id.paymentMethodSpinner);

        BottomNavigationView bottomNavigationView = findViewById(R.id.botNatView);
        bottomNavigationView.setSelectedItemId(R.id.bot_cart);

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
                // Handle the Cart menu item click (e.g., perform some action)
                return true;
            } else if (item.getItemId() == R.id.bot_profile) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });


        ImageView backButton = findViewById(R.id.cartback);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://kapenapud.com/api/") // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<PaymentResponse> call = apiService.getPaymentOptions();

        call.enqueue(new Callback<PaymentResponse>() {
            @Override
            public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                if (response.isSuccessful()) {
                    PaymentResponse paymentResponse = response.body();
                    if (paymentResponse != null) {
                        List<PaymentMethod> paymentMethods = paymentResponse.getData();

                        // Create an ArrayAdapter and set it to the Spinner
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(CartActivity.this, android.R.layout.simple_spinner_item);
                        for (PaymentMethod method : paymentMethods) {
                            adapter.add(method.getPaymentMethod());
                        }
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        paymentMethodSpinner.setAdapter(adapter);
                    }
                } else {
                    // Handle API call error
                }
            }

            @Override
            public void onFailure(Call<PaymentResponse> call, Throwable t) {
                // Handle API call failure
            }
        });

        // Retrieve the selected products from the Cart class
        List<Product> selectedProducts = Cart.getInstance().getCartItems();

        if (selectedProducts != null && !selectedProducts.isEmpty()) {
            // Convert selectedProducts to cartItems
            cartItems = convertToCartItems(selectedProducts);

            // Initialize and set the CartAdapter with the List<CartItem>
            cartAdapter = new CartAdapter(this, cartItems, this);
            cartRecyclerView = findViewById(R.id.cartRecyclerView);
            cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            cartRecyclerView.setAdapter(cartAdapter);

            // Calculate and display subtotal, sales tax, and total
            updatePrices();

            // Handle the "Proceed to checkout" button
            Button checkoutButton = findViewById(R.id.checkoutbutton);
            checkoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get the selected products
                    List<Product> selectedProducts = Cart.getInstance().getCartItems();

                    // Calculate total price (not subtotal), sales tax, and total price
                    double total = calculateTotalPrice(selectedProducts);
                    double salesTax = 0.12 * total;

                    // Get the selected payment method from the spinner
                    String selectedPaymentMethod = paymentMethodSpinner.getSelectedItem().toString();

                    // Create an Intent to start the CheckoutActivity
                    Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                    intent.putExtra("selectedProducts", new ArrayList<>(selectedProducts));
                    intent.putExtra("subtotal", total); // Changed to "total" as it's the total price
                    intent.putExtra("salesTax", salesTax);
                    intent.putExtra("totalPrice", total);
                    intent.putExtra("paymentMethod", selectedPaymentMethod);

                    // Start the CheckoutActivity
                    startActivity(intent);
                }

            });

        } else {
            // Handle the case when the cart is empty
            // For example, show a message to the user or return to the product list.
            // You can finish this activity or provide appropriate feedback.
        }
    }

    private List<CartItem> convertToCartItems(List<Product> products) {
        List<CartItem> cartItems = new ArrayList<>();
        for (Product product : products) {
            cartItems.add(new CartItem(product.getId(), product.getName(), Double.parseDouble(product.getPrice()), 1));
        }
        return cartItems;
    }

    // Calculate the subtotal based on selected cart items
    private double calculateSubtotal(List<CartItem> cartItems) {
        if (cartItems == null) {
            return 0.0;
        }

        double subtotal = 0.0;
        for (CartItem cartItem : cartItems) {
            double price = cartItem.getQuantity() * cartItem.getProductPrice();
            subtotal += price;
        }
        return subtotal;
    }

    // Calculate the total price based on selected products
    // Calculate the total price based on selected products
    private double calculateTotalPrice(List<Product> selectedProducts) {
        double total = 0.0;

        for (Product product : selectedProducts) {
            // Assuming your Product class has a getPrice() method
            total += Double.parseDouble(product.getPrice());
        }

        return total;
    }



    // Update the displayed prices
    protected void updatePrices() {
        // Calculate and update prices based on cartItems
        double subtotal = calculateSubtotal(cartItems);
        double salesTax = 0.12 * subtotal;
        double total = subtotal + salesTax;

        TextView subtotalTextView = findViewById(R.id.subtotalprice);
        TextView salesTaxTextView = findViewById(R.id.salestaxprice);
        TextView totalTextView = findViewById(R.id.totalprice);

        subtotalTextView.setText("₱" + String.format("%.2f", subtotal));
        salesTaxTextView.setText("₱" + String.format("%.2f", salesTax));
        totalTextView.setText("₱" + String.format("%.2f", total));
    }

}