package com.example.Innovatiview.DTO;

public class SessionResponse {
    private String message;
    private String userId;
    private String type;
    private String shift;
    private String centreCode;
    private String centreName;

    public SessionResponse(String message, String userId, String type, String shift,
            String centreCode, String centreName) {
        this.message = message;
        this.userId = userId;
        this.type = type;
        this.shift = shift;
        this.centreCode = centreCode;
        this.centreName = centreName;
    }

    public String getMessage() {
        return message;
    }

    public String getUserId() {
        return userId;
    }

    public String getType() {
        return type;
    }

    public String getShift() {
        return shift;
    }

    public String getCentreCode() {
        return centreCode;
    }

    public String getCentreName() {
        return centreName;
    }
}
