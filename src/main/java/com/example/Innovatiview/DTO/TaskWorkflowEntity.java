package com.example.Innovatiview.DTO;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "task_workflow")
public class TaskWorkflowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private boolean videoWatched = false;
    private boolean agreementChecked = false;

    private String operatorSelfiePath;
    private LocalDateTime operatorSelfieTime;

    private String csrSheetPath;
    private LocalDateTime csrSheetTime;

    private String exitSelfiePath;
    private LocalDateTime exitSelfieTime;

    // ✅ Auto-handled timestamps
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public TaskWorkflowEntity() {
    }

    public TaskWorkflowEntity(String userId) {
        this.userId = userId;
    }

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isVideoWatched() {
        return videoWatched;
    }

    public void setVideoWatched(boolean videoWatched) {
        this.videoWatched = videoWatched;
    }

    public boolean isAgreementChecked() {
        return agreementChecked;
    }

    public void setAgreementChecked(boolean agreementChecked) {
        this.agreementChecked = agreementChecked;
    }

    public String getOperatorSelfiePath() {
        return operatorSelfiePath;
    }

    public void setOperatorSelfiePath(String operatorSelfiePath) {
        this.operatorSelfiePath = operatorSelfiePath;
    }

    public LocalDateTime getOperatorSelfieTime() {
        return operatorSelfieTime;
    }

    public void setOperatorSelfieTime(LocalDateTime operatorSelfieTime) {
        this.operatorSelfieTime = operatorSelfieTime;
    }

    public String getCsrSheetPath() {
        return csrSheetPath;
    }

    public void setCsrSheetPath(String csrSheetPath) {
        this.csrSheetPath = csrSheetPath;
    }

    public LocalDateTime getCsrSheetTime() {
        return csrSheetTime;
    }

    public void setCsrSheetTime(LocalDateTime csrSheetTime) {
        this.csrSheetTime = csrSheetTime;
    }

    public String getExitSelfiePath() {
        return exitSelfiePath;
    }

    public void setExitSelfiePath(String exitSelfiePath) {
        this.exitSelfiePath = exitSelfiePath;
    }

    public LocalDateTime getExitSelfieTime() {
        return exitSelfieTime;
    }

    public void setExitSelfieTime(LocalDateTime exitSelfieTime) {
        this.exitSelfieTime = exitSelfieTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
