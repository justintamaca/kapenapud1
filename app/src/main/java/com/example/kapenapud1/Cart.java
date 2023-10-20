package com.example.kapenapud1;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static Cart instance;
    private List<Product> cartItems;

    private Cart() {
        cartItems = new ArrayList<>();
    }

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    public void addItem(Product product) {
        // Add the product to the cart
        cartItems.add(product);
    }

    public List<Product> getCartItems() {

        return cartItems;
    }

}