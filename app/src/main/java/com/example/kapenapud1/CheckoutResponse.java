package com.example.kapenapud1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckoutResponse {
    @SerializedName("order_id")
    private int orderId;
    private String message;

    public int getOrderId() {
        return orderId;
    }

    public String getMessage() {
        return message;
    }
}

