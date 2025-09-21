package com.example.Innovatiview.DTO;

public class RegisterUser {
    private String fullName;
    private String email;
    private String fatherName;
    private String mobileNumber;
    private String aadharNumber;

    private String userId; // Generated user ID

    // Default constructor
    public RegisterUser() {
    }

    // Constructor with all fields
    public RegisterUser(String fullName, String email, String fatherName, String mobileNumber, String aadharNumber,
            String userId) {
        this.fullName = fullName;
        this.email = email;
        this.fatherName = fatherName;
        this.mobileNumber = mobileNumber;
        this.aadharNumber = aadharNumber;
        this.userId = userId;
    }

    // Getters and Setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
