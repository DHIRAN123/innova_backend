package com.example.Innovatiview.service;

import com.example.Innovatiview.DTO.RegisterUser;
import com.example.Innovatiview.DTO.UserEntity;
import com.example.Innovatiview.repository.UserRepository;
import com.example.Innovatiview.util.CustomIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    /**
     * Register a new operator with the provided details
     * 
     * @param registerUser The registration details
     * @return The created UserEntity
     */
    @Transactional
    public UserEntity registerOperator(RegisterUser registerUser) {
        // Check if user already exists by email, mobile, or Aadhar
        if (userRepository.findByEmail(registerUser.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }

        if (userRepository.findByMobileNumber(registerUser.getMobileNumber()).isPresent()) {
            throw new RuntimeException("User with this mobile number already exists");
        }

        if (userRepository.findByAadharNumber(registerUser.getAadharNumber()).isPresent()) {
            throw new RuntimeException("User with this Aadhar number already exists");
        }

        // Generate unique user ID based on registration fields
        String userId = generateUserId(registerUser);

        // Create new UserEntity
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setFullName(registerUser.getFullName());
        userEntity.setEmail(registerUser.getEmail());
        userEntity.setFatherName(registerUser.getFatherName());
        userEntity.setMobileNumber(registerUser.getMobileNumber());
        userEntity.setAadharNumber(registerUser.getAadharNumber());
        userEntity.setCenterCode("DEFAULT"); // You can modify this based on your business logic
        userEntity.setPassword("defaultPassword"); // You should implement proper password handling

        // Save to database
        UserEntity savedUser = userRepository.save(userEntity);

        // Update the RegisterUser object with generated userId
        registerUser.setUserId(userId);

        // Send registration confirmation email
        try {
            emailService.sendRegistrationConfirmationEmail(savedUser.getEmail(), userId);
        } catch (Exception e) {
            // Log the error but don't fail registration
            System.err.println("Failed to send registration confirmation email: " + e.getMessage());
        }

        return savedUser;
    }

    /**
     * Generate a unique user ID based on registration fields
     * 
     * @param registerUser The registration details
     * @return Generated user ID
     */
    private String generateUserId(RegisterUser registerUser) {
        // Create a base string using key fields
        String baseString = registerUser.getFullName().substring(0, Math.min(3, registerUser.getFullName().length())) +
                registerUser.getMobileNumber().substring(Math.max(0, registerUser.getMobileNumber().length() - 3)) +
                registerUser.getAadharNumber().substring(Math.max(0, registerUser.getAadharNumber().length() - 4));

        // Generate a unique ID using the custom ID generator
        String uniqueId = CustomIdGenerator.generateId(8);

        // Combine base string with unique ID to ensure uniqueness
        return baseString.toUpperCase() + uniqueId;
    }

    /**
     * Find user by email
     * 
     * @param email Email address
     * @return Optional UserEntity
     */
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Find user by mobile number
     * 
     * @param mobileNumber Mobile number
     * @return Optional UserEntity
     */
    public Optional<UserEntity> findByMobileNumber(String mobileNumber) {
        return userRepository.findByMobileNumber(mobileNumber);
    }

    /**
     * Find user by Aadhar number
     * 
     * @param aadharNumber Aadhar number
     * @return Optional UserEntity
     */
    public Optional<UserEntity> findByAadharNumber(String aadharNumber) {
        return userRepository.findByAadharNumber(aadharNumber);
    }
}
