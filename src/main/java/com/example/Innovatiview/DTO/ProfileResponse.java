package com.example.Innovatiview.DTO;

public class ProfileResponse {
    private String id;
    private String fullName;
    private String email;
    private String fatherName;
    private String mobileNumber;
    private String aadharNumber;
    private String centerCode;
    private String message;
    private boolean success;

    // Default constructor
    public ProfileResponse() {
    }

    // Constructor for success response
    public ProfileResponse(String id, String fullName, String email, String fatherName,
            String mobileNumber, String aadharNumber, String centerCode,
            String message, boolean success) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.fatherName = fatherName;
        this.mobileNumber = mobileNumber;
        this.aadharNumber = aadharNumber;
        this.centerCode = centerCode;
        this.message = message;
        this.success = success;
    }

    // Constructor for error response
    public ProfileResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getCenterCode() {
        return centerCode;
    }

    public void setCenterCode(String centerCode) {
        this.centerCode = centerCode;
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
}
