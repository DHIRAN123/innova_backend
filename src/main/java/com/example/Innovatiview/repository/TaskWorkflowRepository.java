package com.example.Innovatiview.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Innovatiview.DTO.TaskWorkflowEntity;

import java.util.Optional;

public interface TaskWorkflowRepository extends JpaRepository<TaskWorkflowEntity, Long> {
    Optional<TaskWorkflowEntity> findFirstByUserIdOrderByCreatedAtDesc(String userId);
}
