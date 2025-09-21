package com.example.Innovatiview.DTO;

public class LoginResponse {
    private String token;
    private String userId;
    private String message;
    private boolean success;
    private boolean requiresOtp; // Indicates if OTP verification is needed

    // Constructor for successful login
    public LoginResponse(String token, String userId) {
        this.token = token;
        this.userId = userId;
        this.success = true;
        this.requiresOtp = false;
    }

    // Constructor for OTP request response
    public LoginResponse(String message, boolean requiresOtp) {
        this.message = message;
        this.requiresOtp = requiresOtp;
        this.success = true;
    }

    // Default constructor
    public LoginResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isRequiresOtp() {
        return requiresOtp;
    }

    public void setRequiresOtp(boolean requiresOtp) {
        this.requiresOtp = requiresOtp;
    }
}
