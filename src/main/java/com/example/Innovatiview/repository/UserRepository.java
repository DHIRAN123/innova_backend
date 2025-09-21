package com.example.Innovatiview.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Innovatiview.DTO.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByCenterCodeAndPassword(String centerCode, String password);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByMobileNumber(String mobileNumber);

    Optional<UserEntity> findByAadharNumber(String aadharNumber);

    boolean existsByEmailAndMobileNumber(String email, String mobileNumber);
}
