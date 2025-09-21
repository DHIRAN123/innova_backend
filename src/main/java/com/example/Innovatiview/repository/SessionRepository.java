package com.example.Innovatiview.repository;

import com.example.Innovatiview.DTO.SessionEntity;
import com.example.Innovatiview.DTO.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {

    Optional<SessionEntity> findBySessionId(String sessionId);

    Optional<SessionEntity> findByUser(UserEntity user);

    List<SessionEntity> findByUserId(String userId);

    @Query("SELECT s FROM SessionEntity s WHERE s.expiresAt < :now")
    List<SessionEntity> findExpiredSessions(@Param("now") LocalDateTime now);

    void deleteByExpiresAtBefore(LocalDateTime now);

    boolean existsByUserAndCurrentStep(UserEntity user, SessionEntity.WorkflowStep step);
}
