package com.example.kapenapud1;

public class OrderItem {
    private int user_id;
    private int payment_id;
    private double total_price;
    private int product_id;
    private int quantity;
    private double price;

    public OrderItem(int product_id, int quantity, double price) {
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
    }


    // Getters and setters for the fields

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
