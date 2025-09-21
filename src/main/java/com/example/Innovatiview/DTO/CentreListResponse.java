package com.example.Innovatiview.DTO;

import java.util.List;

public class CentreListResponse {
    private String userId;
    private List<CentreResponse> centres;

    public CentreListResponse(String userId, List<CentreResponse> centres) {
        this.userId = userId;
        this.centres = centres;
    }

    public String getUserId() {
        return userId;
    }

    public List<CentreResponse> getCentres() {
        return centres;
    }
}
