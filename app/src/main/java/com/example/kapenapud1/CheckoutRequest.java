package com.example.kapenapud1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CheckoutRequest {
    @SerializedName("user_id")
    private int userId;
    @SerializedName("payment_id")
    private int paymentId;
    @SerializedName("total_price")
    private double totalPrice;
    private List<CartItem> cart;

    public CheckoutRequest(int userId, int paymentId, double totalPrice, List<CartItem> cart) {
        this.userId = userId;
        this.paymentId = paymentId;
        this.totalPrice = totalPrice;
        this.cart = cart;
    }
}

