package com.example.Innovatiview.DTO;

public class ProfileRequest {
    private String fullName;
    private String email;
    private String fatherName;
    private String mobileNumber;
    private String aadharNumber;

    // Default constructor
    public ProfileRequest() {
    }

    // Constructor with all fields
    public ProfileRequest(String fullName, String email, String fatherName, String mobileNumber, String aadharNumber) {
        this.fullName = fullName;
        this.email = email;
        this.fatherName = fatherName;
        this.mobileNumber = mobileNumber;
        this.aadharNumber = aadharNumber;
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
}
