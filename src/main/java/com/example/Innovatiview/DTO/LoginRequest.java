package com.example.Innovatiview.DTO;

public class LoginRequest {
    private String email;
    private String otp; // For OTP verification

    // Default constructor
    public LoginRequest() {
    }

    // Constructor with email
    public LoginRequest(String email) {
        this.email = email;
    }

    // Constructor with email and OTP
    public LoginRequest(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
