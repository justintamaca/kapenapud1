package com.example.kapenapud1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

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
                    // Implement the checkout process (e.g., launch a new activity or fragment)
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

    // Implement a method to calculate the subtotal
    private double calculateSubtotal(List<CartItem> items) {
        if (items == null) {
            return 0.0;
        }

        // Replace with your logic to calculate the subtotal
        double subtotal = 0.0;
        for (CartItem cartItem : items) {
            double price = cartItem.getQuantity() * cartItem.getProductPrice();
            subtotal += price;
        }
        return subtotal;
    }

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
