package com.example.Innovatiview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Innovatiview.DTO.ProfileRequest;
import com.example.Innovatiview.DTO.ProfileResponse;
import com.example.Innovatiview.DTO.UserEntity;
import com.example.Innovatiview.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Get user profile by user ID
     */
    public ProfileResponse getUserProfile(String userId) {
        try {
            Optional<UserEntity> userOptional = userRepository.findById(userId);

            if (userOptional.isEmpty()) {
                return new ProfileResponse("User not found", false);
            }

            UserEntity user = userOptional.get();
            return new ProfileResponse(
                    user.getId(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getFatherName(),
                    user.getMobileNumber(),
                    user.getAadharNumber(),
                    user.getCenterCode(),
                    "Profile retrieved successfully",
                    true);

        } catch (Exception e) {
            return new ProfileResponse("Error retrieving profile: " + e.getMessage(), false);
        }
    }

    /**
     * Update user profile
     */
    public ProfileResponse updateUserProfile(String userId, ProfileRequest profileRequest) {
        try {
            Optional<UserEntity> userOptional = userRepository.findById(userId);

            if (userOptional.isEmpty()) {
                return new ProfileResponse("User not found", false);
            }

            UserEntity user = userOptional.get();

            // Update fields if provided
            if (profileRequest.getFullName() != null && !profileRequest.getFullName().trim().isEmpty()) {
                user.setFullName(profileRequest.getFullName());
            }

            if (profileRequest.getEmail() != null && !profileRequest.getEmail().trim().isEmpty()) {
                // Check if email is already taken by another user
                Optional<UserEntity> existingUser = userRepository.findByEmail(profileRequest.getEmail());
                if (existingUser.isPresent() && !existingUser.get().getId().equals(userId)) {
                    return new ProfileResponse("Email is already taken by another user", false);
                }
                user.setEmail(profileRequest.getEmail());
            }

            if (profileRequest.getFatherName() != null && !profileRequest.getFatherName().trim().isEmpty()) {
                user.setFatherName(profileRequest.getFatherName());
            }

            if (profileRequest.getMobileNumber() != null && !profileRequest.getMobileNumber().trim().isEmpty()) {
                // Check if mobile number is already taken by another user
                Optional<UserEntity> existingUser = userRepository.findByMobileNumber(profileRequest.getMobileNumber());
                if (existingUser.isPresent() && !existingUser.get().getId().equals(userId)) {
                    return new ProfileResponse("Mobile number is already taken by another user", false);
                }
                user.setMobileNumber(profileRequest.getMobileNumber());
            }

            if (profileRequest.getAadharNumber() != null && !profileRequest.getAadharNumber().trim().isEmpty()) {
                // Check if aadhar number is already taken by another user
                Optional<UserEntity> existingUser = userRepository.findByAadharNumber(profileRequest.getAadharNumber());
                if (existingUser.isPresent() && !existingUser.get().getId().equals(userId)) {
                    return new ProfileResponse("Aadhar number is already taken by another user", false);
                }
                user.setAadharNumber(profileRequest.getAadharNumber());
            }

            // Save updated user
            UserEntity savedUser = userRepository.save(user);

            return new ProfileResponse(
                    savedUser.getId(),
                    savedUser.getFullName(),
                    savedUser.getEmail(),
                    savedUser.getFatherName(),
                    savedUser.getMobileNumber(),
                    savedUser.getAadharNumber(),
                    savedUser.getCenterCode(),
                    "Profile updated successfully",
                    true);

        } catch (Exception e) {
            return new ProfileResponse("Error updating profile: " + e.getMessage(), false);
        }
    }
}
