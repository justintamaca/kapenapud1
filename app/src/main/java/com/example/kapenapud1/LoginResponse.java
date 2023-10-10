package com.example.kapenapud1;

import android.service.autofill.UserData;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("error")
    private String error;

    @SerializedName("data")
    private UserData userData; // Assuming you receive user data upon successful login

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public UserData getUserData() {
        return userData;
    }

    // You can include additional getters for specific user data fields if needed
}

