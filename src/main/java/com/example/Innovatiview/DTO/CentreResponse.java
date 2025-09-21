package com.example.Innovatiview.DTO;

public class CentreResponse {
    private Long id;
    private String centreCode;
    private String centreName;

    public CentreResponse(Long id, String centreCode, String centreName) {
        this.id = id;
        this.centreCode = centreCode;
        this.centreName = centreName;
    }

    public Long getId() {
        return id;
    }

    public String getCentreCode() {
        return centreCode;
    }

    public String getCentreName() {
        return centreName;
    }
}
