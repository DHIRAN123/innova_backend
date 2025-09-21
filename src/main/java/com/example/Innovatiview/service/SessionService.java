package com.example.Innovatiview.service;

import org.springframework.stereotype.Service;

import com.example.Innovatiview.DTO.CentreEntity;
import com.example.Innovatiview.DTO.SessionEntity;
import com.example.Innovatiview.DTO.SessionRequest;
import com.example.Innovatiview.DTO.SessionResponse;
import com.example.Innovatiview.DTO.UserEntity;
import com.example.Innovatiview.repository.CentreRepository;
import com.example.Innovatiview.repository.SessionRepository;
import com.example.Innovatiview.repository.UserRepository;
import com.example.Innovatiview.util.CustomIdGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final CentreRepository centreRepository;
    private final UserRepository userRepository;

    public SessionService(SessionRepository sessionRepository, CentreRepository centreRepository,
            UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.centreRepository = centreRepository;
        this.userRepository = userRepository;
    }

    public SessionResponse createSession(String userId, SessionRequest request) {
        // Find user by userId
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return new SessionResponse("❌ User not found", userId,
                    request.getType(), request.getShift(), null, null);
        }

        // Find centre by centreCode
        Optional<CentreEntity> centreOpt = centreRepository.findByCentreCode(request.getcentreCode());
        if (centreOpt.isEmpty()) {
            return new SessionResponse("❌ Centre not found", userId,
                    request.getType(), request.getShift(), null, null);
        }

        UserEntity user = userOpt.get();
        CentreEntity centre = centreOpt.get();

        // Generate session ID
        String sessionId = CustomIdGenerator.generateId(10);

        // Set session expiry (e.g., 24 hours from now)
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(24);

        // Create session entity
        SessionEntity session = new SessionEntity(sessionId, user, expiresAt);
        session.setSelectedCenter(centre.getCentreCode());
        session.setSelectedService(request.getType());

        sessionRepository.save(session);

        return new SessionResponse(
                "✅ Session created successfully",
                userId,
                request.getType(),
                request.getShift(),
                centre.getCentreCode(),
                centre.getCentreName());
    }

    public Optional<SessionEntity> getSessionById(String sessionId) {
        return sessionRepository.findBySessionId(sessionId);
    }

    public Optional<SessionEntity> getSessionByUserId(String userId) {
        List<SessionEntity> sessions = sessionRepository.findByUserId(userId);
        return sessions.isEmpty() ? Optional.empty() : Optional.of(sessions.get(0));
    }
}
