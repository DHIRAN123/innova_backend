package com.example.Innovatiview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Innovatiview.DTO.LoginRequest;
import com.example.Innovatiview.DTO.LoginResponse;
import com.example.Innovatiview.DTO.UserEntity;
import com.example.Innovatiview.service.OtpService;
import com.example.Innovatiview.service.EmailService;
import com.example.Innovatiview.repository.UserRepository;
import com.example.Innovatiview.util.CustomIdGenerator;
import com.example.Innovatiview.util.JwtUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
public class AuthController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    /**
     * Request OTP for login
     * POST /auth/request-otp
     */
    @PostMapping("/request-otp")
    public ResponseEntity<?> requestOtp(@RequestBody LoginRequest loginRequest) {
        try {
            String email = loginRequest.getEmail();

            // Validate email
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new LoginResponse("Email is required", false));
            }

            // Validate email format
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                return ResponseEntity.badRequest()
                        .body(new LoginResponse("Please provide a valid email address", false));
            }

            // Check if user is registered
            Optional<UserEntity> userOptional = userRepository.findByEmail(email);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new LoginResponse("Email not found. Please register first.", false));
            }

            // Generate OTP
            String otp = otpService.generateOtp(email);

            // Send OTP via email
            System.out.println("🔄 Attempting to send OTP email to: " + email);
            System.out.println("📧 OTP Code: " + otp);

            boolean emailSent = emailService.sendOtpEmail(email, otp);

            System.out.println("📨 Email sent result: " + emailSent);

            if (emailSent) {
                System.out.println("✅ OTP email sent successfully to: " + email);
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "OTP sent to your email address");
                response.put("requiresOtp", true);
                return ResponseEntity.ok(response);
            } else {
                System.err.println("❌ Failed to send OTP email to: " + email);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new LoginResponse("Failed to send OTP. Please try again.", false));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse("An error occurred while processing your request", false));
        }
    }

    /**
     * Verify OTP and login
     * POST /auth/verify-otp
     */
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody LoginRequest loginRequest) {
        try {
            String email = loginRequest.getEmail();
            String otp = loginRequest.getOtp();

            // Validate input
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new LoginResponse("Email is required", false));
            }

            if (otp == null || otp.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new LoginResponse("OTP is required", false));
            }

            // Check if user is registered
            Optional<UserEntity> userOptional = userRepository.findByEmail(email);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new LoginResponse("Email not found. Please register first.", false));
            }

            // Verify OTP
            boolean isOtpValid = otpService.verifyOtp(email, otp);

            if (isOtpValid) {
                UserEntity user = userOptional.get();

                // Generate JWT token
                String token = jwtUtil.generateToken(user.getId());

                // Create response
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Login successful");
                response.put("token", token);
                response.put("userId", user.getId());
                response.put("user", createUserResponse(user));

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new LoginResponse("Invalid or expired OTP", false));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse("An error occurred while verifying OTP", false));
        }
    }

    /**
     * Legacy login endpoint (kept for backward compatibility)
     * POST /auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("🔄 Legacy login endpoint called");
            String email = loginRequest.getEmail();

            System.out.println("📧 Legacy login - Email: " + email);

            // Validate email
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new LoginResponse("Email is required", false));
            }

            // Check if user is registered
            Optional<UserEntity> userOptional = userRepository.findByEmail(email);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new LoginResponse("Email not found. Please register first.", false));
            }

            // Generate OTP for login
            String otp = otpService.generateOtp(email);

            System.out.println("📧 Legacy login - Generated OTP: " + otp);

            // Send OTP via email
            boolean emailSent = emailService.sendOtpEmail(email, otp);

            System.out.println("📨 Legacy login - Email sent result: " + emailSent);

            if (emailSent) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "OTP sent to your email address. Please verify OTP to complete login.");
                response.put("requiresOtp", true);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new LoginResponse("Failed to send OTP. Please try again.", false));
            }

        } catch (Exception e) {
            System.err.println("💥 Legacy login error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse("An error occurred while processing your request", false));
        }
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
