package com.example.Innovatiview.DTO;

public class TaskResponse {
    private String message;
    private String userId;
    private String task;

    public TaskResponse(String message, String userId, String task) {
        this.message = message;
        this.userId = userId;
        this.task = task;
    }

    public String getMessage() {
        return message;
    }

    public String getUserId() {
        return userId;
    }

    public String getTask() {
        return task;
    }
}
