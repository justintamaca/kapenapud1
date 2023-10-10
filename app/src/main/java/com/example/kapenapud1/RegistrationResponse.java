package com.example.kapenapud1;

import com.google.gson.annotations.SerializedName;

public class RegistrationResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("error")
    private String error;

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }
}
