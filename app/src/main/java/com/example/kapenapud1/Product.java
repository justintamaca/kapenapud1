package com.example.kapenapud1;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private int id;
    private int stock_id;
    private int category_id;
    private String name;
    private String description;
    private String price;
    private String availability;
    private String image;
    private int quantity;
    private int user_id;
    private int payment_id;// New field for quantity

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock_id() {
        return stock_id;
    }

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

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

    // Parcelable implementation
    protected Product(Parcel in) {
        id = in.readInt();
        stock_id = in.readInt();
        category_id = in.readInt();
        name = in.readString();
        description = in.readString();
        price = in.readString();
        availability = in.readString();
        image = in.readString();
        quantity = in.readInt(); // Include quantity in Parcelable
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(stock_id);
        dest.writeInt(category_id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(price);
        dest.writeString(availability);
        dest.writeString(image);
        dest.writeInt(quantity); // Include quantity in Parcelable
    }
}
