package com.example.Innovatiview.service;

import com.example.Innovatiview.DTO.*;
import com.example.Innovatiview.repository.SessionRepository;
import com.example.Innovatiview.repository.TaskRepository;
import com.example.Innovatiview.repository.UserRepository;
import com.example.Innovatiview.util.CustomIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WorkflowService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CentreService centreService;

    private static final int SESSION_DURATION_HOURS = 24;

    /**
     * Create a new session after successful login
     */
    @Transactional
    public SessionEntity createSession(String userId) {
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        UserEntity user = userOpt.get();

        // Check if user already has an active session
        Optional<SessionEntity> existingSession = sessionRepository.findByUser(user);
        if (existingSession.isPresent() && !existingSession.get().isExpired()) {
            return existingSession.get();
        }

        // Create new session
        String sessionId = CustomIdGenerator.generateId(16);
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(SESSION_DURATION_HOURS);

        SessionEntity session = new SessionEntity(sessionId, user, expiresAt);
        return sessionRepository.save(session);
    }

    /**
     * Get current session status
     */
    public Map<String, Object> getSessionStatus(String userId) {
        Optional<SessionEntity> sessionOpt = sessionRepository.findByUserId(userId)
                .stream()
                .filter(s -> !s.isExpired())
                .findFirst();

        Map<String, Object> response = new HashMap<>();

        if (sessionOpt.isPresent()) {
            SessionEntity session = sessionOpt.get();
            response.put("active", true);
            response.put("sessionId", session.getSessionId());
            response.put("currentStep", session.getCurrentStep());
            response.put("expiresAt", session.getExpiresAt());
            response.put("currentTaskId", session.getCurrentTaskId());
            response.put("selectedService", session.getSelectedService());
            response.put("selectedCenter", session.getSelectedCenter());
        } else {
            response.put("active", false);
            response.put("message", "No active session found");
        }

        return response;
    }

    /**
     * Create a new task for the user
     */
    @Transactional
    public Map<String, Object> createTask(String userId, String taskDescription) {
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        UserEntity user = userOpt.get();

        // Create new task
        TaskEntity task = new TaskEntity(taskDescription, user);
        TaskEntity savedTask = taskRepository.save(task);

        // Update session
        Optional<SessionEntity> sessionOpt = sessionRepository.findByUser(user);
        if (sessionOpt.isPresent()) {
            SessionEntity session = sessionOpt.get();
            session.setCurrentStep(SessionEntity.WorkflowStep.TASK_CREATION);
            session.setCurrentTaskId(savedTask.getId().toString());
            sessionRepository.save(session);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Task created successfully");
        response.put("taskId", savedTask.getId());
        response.put("nextStep", "service_selection");
        response.put("availableServices", getAvailableServices());

        return response;
    }

    /**
     * Select a service for the current task
     */
    @Transactional
    public Map<String, Object> selectService(String userId, String serviceName) {
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        UserEntity user = userOpt.get();

        // Validate service selection
        if (!isValidService(serviceName)) {
            throw new RuntimeException("Invalid service selected");
        }

        // Update session
        Optional<SessionEntity> sessionOpt = sessionRepository.findByUser(user);
        if (sessionOpt.isPresent()) {
            SessionEntity session = sessionOpt.get();
            session.setCurrentStep(SessionEntity.WorkflowStep.SERVICE_SELECTION);
            session.setSelectedService(serviceName);
            sessionRepository.save(session);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Service selected: " + serviceName);
        response.put("nextStep", "center_selection");
        response.put("availableCenters", getAvailableCenters());

        return response;
    }

    /**
     * Select a center for the service
     */
    @Transactional
    public Map<String, Object> selectCenter(String userId, String centerCode) {
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        UserEntity user = userOpt.get();

        // Validate center selection
        if (!isValidCenter(centerCode)) {
            throw new RuntimeException("Invalid center selected");
        }

        // Update session
        Optional<SessionEntity> sessionOpt = sessionRepository.findByUser(user);
        if (sessionOpt.isPresent()) {
            SessionEntity session = sessionOpt.get();
            session.setCurrentStep(SessionEntity.WorkflowStep.CENTER_SELECTION);
            session.setSelectedCenter(centerCode);
            sessionRepository.save(session);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Center selected: " + centerCode);
        response.put("nextStep", "workflow_complete");
        response.put("workflowStatus", "ready_for_processing");

        return response;
    }

    /**
     * Get workflow progress
     */
    public Map<String, Object> getWorkflowProgress(String userId) {
        Optional<SessionEntity> sessionOpt = sessionRepository.findByUserId(userId)
                .stream()
                .filter(s -> !s.isExpired())
                .findFirst();

        Map<String, Object> response = new HashMap<>();

        if (sessionOpt.isPresent()) {
            SessionEntity session = sessionOpt.get();

            response.put("userId", userId);
            response.put("currentStep", session.getCurrentStep());
            response.put("sessionId", session.getSessionId());
            response.put("taskId", session.getCurrentTaskId());
            response.put("selectedService", session.getSelectedService());
            response.put("selectedCenter", session.getSelectedCenter());

            // Add next steps based on current step
            switch (session.getCurrentStep()) {
                case LOGIN_SUCCESS:
                    response.put("nextStep", "create_task");
                    response.put("message", "Ready to create a new task");
                    break;
                case TASK_CREATION:
                    response.put("nextStep", "select_service");
                    response.put("message", "Task created, please select a service");
                    break;
                case SERVICE_SELECTION:
                    response.put("nextStep", "select_center");
                    response.put("message", "Service selected, please select a center");
                    break;
                case CENTER_SELECTION:
                    response.put("nextStep", "complete");
                    response.put("message", "Ready for processing");
                    break;
                case WORKFLOW_COMPLETE:
                    response.put("nextStep", "complete");
                    response.put("message", "Workflow completed successfully");
                    break;
            }
        } else {
            response.put("error", "No active session found");
        }

        return response;
    }

    /**
     * Get available services
     */
    private List<String> getAvailableServices() {
        return List.of("BIO", "FRISKING", "CSO", "DOCUMENT_VERIFICATION", "PHOTO_CAPTURE");
    }

    /**
     * Get available centers
     */
    private List<String> getAvailableCenters() {
        return List.of("CENTER001", "CENTER002", "CENTER003", "CENTER004", "CENTER005");
    }

    /**
     * Validate service selection
     */
    private boolean isValidService(String serviceName) {
        return getAvailableServices().contains(serviceName.toUpperCase());
    }

    /**
     * Validate center selection
     */
    private boolean isValidCenter(String centerCode) {
        return getAvailableCenters().contains(centerCode.toUpperCase());
    }

    /**
     * Clean up expired sessions
     */
    @Transactional
    public void cleanupExpiredSessions() {
        sessionRepository.deleteByExpiresAtBefore(LocalDateTime.now());
    }
}
