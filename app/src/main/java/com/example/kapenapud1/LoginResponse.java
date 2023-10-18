package com.example.kapenapud1;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("token")
    private String token;

    public boolean isSuccess() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }


}
