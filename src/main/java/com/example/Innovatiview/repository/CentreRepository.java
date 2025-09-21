package com.example.Innovatiview.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Innovatiview.DTO.CentreEntity;

import java.util.List;
import java.util.Optional;

public interface CentreRepository extends JpaRepository<CentreEntity, Long> {
    List<CentreEntity> findByCentreNameContainingIgnoreCase(String name);

    Optional<CentreEntity> findByCentreCode(String centreCode);

}
