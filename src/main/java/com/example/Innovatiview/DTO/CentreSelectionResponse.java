package com.example.Innovatiview.DTO;

public class CentreSelectionResponse {
    private String userId;
    private String centreCode;
    private String message;
    private int statusCode;

    public CentreSelectionResponse(String userId, String centreCode, String message, int statusCode) {
        this.userId = userId;
        this.centreCode = centreCode;
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getUserId() {
        return userId;
    }

    public String getCentreCode() {
        return centreCode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
 
 