package com.example.Innovatiview.controller;

import com.example.Innovatiview.DTO.RegisterUser;
import com.example.Innovatiview.DTO.UserEntity;
import com.example.Innovatiview.repository.UserRepository;
import com.example.Innovatiview.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://localhost:8081",
        "http://localhost:8080",
        "http://localhost:8082",
        "http://127.0.0.1:8081",
        "http://127.0.0.1:8080",
        "http://127.0.0.1:8082",
        "http://localhost:19006",
        "http://127.0.0.1:19006",
        "http://localhost:19000",
        "http://127.0.0.1:19000",
        "exp://localhost:8081",
        "exp://127.0.0.1:8081"
}, allowCredentials = "true")
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegisterService registerService;

    /**
     * Register a new operator
     * POST /auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerOperator(@RequestBody RegisterUser registerUser) {
        try {
            // Validate required fields
            if (registerUser.getFullName() == null || registerUser.getFullName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("Full name is required"));
            }

            if (registerUser.getEmail() == null || registerUser.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("Email is required"));
            }

            if (registerUser.getFatherName() == null || registerUser.getFatherName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("Father's name is required"));
            }

            if (registerUser.getMobileNumber() == null || registerUser.getMobileNumber().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("Mobile number is required"));
            }

            if (registerUser.getAadharNumber() == null || registerUser.getAadharNumber().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("Aadhar number is required"));
            }

            // Validate mobile number format (10 digits)
            if (!registerUser.getMobileNumber().matches("^[0-9]{10}$")) {
                return ResponseEntity.badRequest().body(createErrorResponse("Mobile number must be exactly 10 digits"));
            }

            // Validate Aadhar number format (12 digits)
            if (!registerUser.getAadharNumber().matches("^[0-9]{12}$")) {
                return ResponseEntity.badRequest().body(createErrorResponse("Aadhar number must be exactly 12 digits"));
            }

            // Validate email format
            if (!registerUser.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                return ResponseEntity.badRequest().body(createErrorResponse("Please provide a valid email address"));
            }

            // Register the operator
            UserEntity savedUser = registerService.registerOperator(registerUser);

            // Create success response
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Operator registered successfully");
            response.put("userId", savedUser.getId());
            response.put("data", createUserResponse(savedUser));

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            // Handle business logic exceptions (like duplicate user)
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("An error occurred while registering the operator"));
        }
    }

    /**
     * Check if email is available
     * GET /auth/check-email/{email}
     */
    @GetMapping("/check-email/{email}")
    public ResponseEntity<?> checkEmailAvailability(@PathVariable String email) {
        try {
            boolean available = userRepository.findByEmail(email).isEmpty();
            Map<String, Object> response = new HashMap<>();
            response.put("available", available);
            response.put("email", email);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error checking email availability"));
        }
    }

    /**
     * Check if mobile number is available
     * GET /auth/check-mobile/{mobile}
     */
    @GetMapping("/check-mobile/{mobile}")
    public ResponseEntity<?> checkMobileAvailability(@PathVariable String mobile) {
        try {
            boolean available = userRepository.findByMobileNumber(mobile).isEmpty();
            Map<String, Object> response = new HashMap<>();
            response.put("available", available);
            response.put("mobile", mobile);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error checking mobile availability"));
        }
    }

    /**
     * Check if Aadhar number is available
     * GET /auth/check-aadhar/{aadhar}
     */
    @GetMapping("/check-aadhar/{aadhar}")
    public ResponseEntity<?> checkAadharAvailability(@PathVariable String aadhar) {
        try {
            boolean available = userRepository.findByAadharNumber(aadhar).isEmpty();
            Map<String, Object> response = new HashMap<>();
            response.put("available", available);
            response.put("aadhar", aadhar);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Error checking Aadhar availability"));
        }
    }

    /**
     * Create error response
     */
    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", message);
        return errorResponse;
    }

    /**
     * Create user response data
     */
    private Map<String, Object> createUserResponse(UserEntity user) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", user.getId());
        userData.put("fullName", user.getFullName());
        userData.put("email", user.getEmail());
        userData.put("fatherName", user.getFatherName());
        userData.put("mobileNumber", user.getMobileNumber());
        userData.put("aadharNumber", user.getAadharNumber());
        userData.put("centerCode", user.getCenterCode());
        return userData;
    }
}
