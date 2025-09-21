package com.example.Innovatiview.DTO;

import java.time.LocalDateTime;

public class TaskWorkflowResponse {
    private String userId;
    private boolean videoWatched;
    private boolean agreementChecked;

    private boolean operatorSelfieEnabled;
    private String operatorSelfiePath;
    private LocalDateTime operatorSelfieTime;

    private boolean csrSheetEnabled;
    private String csrSheetPath;
    private LocalDateTime csrSheetTime;

    private boolean exitSelfieEnabled;
    private String exitSelfiePath;
    private LocalDateTime exitSelfieTime;

    private String message;

    public TaskWorkflowResponse(String userId, boolean videoWatched, boolean agreementChecked,
            boolean operatorSelfieEnabled, String operatorSelfiePath, LocalDateTime operatorSelfieTime,
            boolean csrSheetEnabled, String csrSheetPath, LocalDateTime csrSheetTime,
            boolean exitSelfieEnabled, String exitSelfiePath, LocalDateTime exitSelfieTime,
            String message) {
        this.userId = userId;
        this.videoWatched = videoWatched;
        this.agreementChecked = agreementChecked;
        this.operatorSelfieEnabled = operatorSelfieEnabled;
        this.operatorSelfiePath = operatorSelfiePath;
        this.operatorSelfieTime = operatorSelfieTime;
        this.csrSheetEnabled = csrSheetEnabled;
        this.csrSheetPath = csrSheetPath;
        this.csrSheetTime = csrSheetTime;
        this.exitSelfieEnabled = exitSelfieEnabled;
        this.exitSelfiePath = exitSelfiePath;
        this.exitSelfieTime = exitSelfieTime;
        this.message = message;
    }

    // Getters only
    public String getUserId() {
        return userId;
    }

    public boolean isVideoWatched() {
        return videoWatched;
    }

    public boolean isAgreementChecked() {
        return agreementChecked;
    }

    public boolean isOperatorSelfieEnabled() {
        return operatorSelfieEnabled;
    }

    public String getOperatorSelfiePath() {
        return operatorSelfiePath;
    }

    public LocalDateTime getOperatorSelfieTime() {
        return operatorSelfieTime;
    }

    public boolean isCsrSheetEnabled() {
        return csrSheetEnabled;
    }

    public String getCsrSheetPath() {
        return csrSheetPath;
    }

    public LocalDateTime getCsrSheetTime() {
        return csrSheetTime;
    }

    public boolean isExitSelfieEnabled() {
        return exitSelfieEnabled;
    }

    public String getExitSelfiePath() {
        return exitSelfiePath;
    }

    public LocalDateTime getExitSelfieTime() {
        return exitSelfieTime;
    }

    public String getMessage() {
        return message;
    }
}
