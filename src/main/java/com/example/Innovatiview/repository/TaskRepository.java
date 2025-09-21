package com.example.Innovatiview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Innovatiview.DTO.TaskEntity;
import com.example.Innovatiview.DTO.UserEntity;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    TaskEntity findTopByUserOrderByIdDesc(UserEntity user);
}
