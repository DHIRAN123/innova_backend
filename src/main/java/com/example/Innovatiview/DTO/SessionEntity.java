package com.example.Innovatiview.DTO;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_sessions")
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String sessionId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkflowStep currentStep = WorkflowStep.LOGIN_SUCCESS;

    @Column
    private String currentTaskId;

    @Column
    private String selectedService;

    @Column
    private String selectedCenter;

    @Column
    private String workflowData; // JSON string to store additional workflow data

    public enum WorkflowStep {
        LOGIN_SUCCESS,
        TASK_CREATION,
        SERVICE_SELECTION,
        CENTER_SELECTION,
        WORKFLOW_COMPLETE
    }

    public SessionEntity() {
    }

    public SessionEntity(String sessionId, UserEntity user, LocalDateTime expiresAt) {
        this.sessionId = sessionId;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.expiresAt = expiresAt;
        this.currentStep = WorkflowStep.LOGIN_SUCCESS;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public WorkflowStep getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(WorkflowStep currentStep) {
        this.currentStep = currentStep;
    }

    public String getCurrentTaskId() {
        return currentTaskId;
    }

    public void setCurrentTaskId(String currentTaskId) {
        this.currentTaskId = currentTaskId;
    }

    public String getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(String selectedService) {
        this.selectedService = selectedService;
    }

    public String getSelectedCenter() {
        return selectedCenter;
    }

    public void setSelectedCenter(String selectedCenter) {
        this.selectedCenter = selectedCenter;
    }

    public String getWorkflowData() {
        return workflowData;
    }

    public void setWorkflowData(String workflowData) {
        this.workflowData = workflowData;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}
