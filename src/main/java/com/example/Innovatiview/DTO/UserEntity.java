package com.example.Innovatiview.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "email", "mobile_number" })
})
public class UserEntity {

    @Id
    private String id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "mobile_number", unique = true)
    private String mobileNumber;

    @Column(name = "aadhar_number", unique = true)
    private String aadharNumber;

    @Column(name = "center_code")
    private String centerCode;

    @Column(name = "password")
    private String password;

    public UserEntity() {
    }

    public UserEntity(String id, String fullName, String email, String fatherName, String mobileNumber,
            String aadharNumber, String centerCode, String password) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.fatherName = fatherName;
        this.mobileNumber = mobileNumber;
        this.aadharNumber = aadharNumber;
        this.centerCode = centerCode;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
