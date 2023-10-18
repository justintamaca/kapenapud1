package com.example.kapenapud1;

import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    // Add other user-specific fields here
    @SerializedName("password")
    private String password;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Include getters for other fields as needed
}
