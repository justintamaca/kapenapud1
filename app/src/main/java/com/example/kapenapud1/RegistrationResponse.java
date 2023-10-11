package com.example.kapenapud1;

public class RegistrationResponse {
    private boolean success;
    private String message;
    private String token;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
