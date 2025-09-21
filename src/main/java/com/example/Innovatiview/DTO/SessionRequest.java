package com.example.Innovatiview.DTO;

public class SessionRequest {
    private String centreCode;
    private String type; // "exam" / "mock"
    private String shift; // "morning" / "evening"

    public String getcentreCode() {
        return centreCode;
    }

    public void setcentreCode(String centreCode) {
        this.centreCode = centreCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
