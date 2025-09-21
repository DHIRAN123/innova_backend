package com.example.Innovatiview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Innovatiview.DTO.ProfileRequest;
import com.example.Innovatiview.DTO.ProfileResponse;
import com.example.Innovatiview.service.ProfileService;
import com.example.Innovatiview.util.JwtUtil;

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
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Get user profile
     * GET /auth/profile
     */
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestHeader(value = "Authorization", required = false) String token) {
        try {
            // Validate token
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ProfileResponse("Authorization token is required", false));
            }

            String jwtToken = token.substring(7); // Remove "Bearer " prefix

            // Extract user ID from token
            String userId = jwtUtil.extractUserId(jwtToken);
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ProfileResponse("Invalid token", false));
            }

            // Get user profile
            ProfileResponse profileResponse = profileService.getUserProfile(userId);

            if (profileResponse.isSuccess()) {
                return ResponseEntity.ok(profileResponse);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(profileResponse);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProfileResponse("Error retrieving profile: " + e.getMessage(), false));
        }
    }

    /**
     * Update user profile
     * PUT /auth/profile
     */
    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody ProfileRequest profileRequest) {
        try {
            // Validate token
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ProfileResponse("Authorization token is required", false));
            }

            String jwtToken = token.substring(7); // Remove "Bearer " prefix

            // Extract user ID from token
            String userId = jwtUtil.extractUserId(jwtToken);
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ProfileResponse("Invalid token", false));
            }

            // Validate request body
            if (profileRequest == null) {
                return ResponseEntity.badRequest()
                        .body(new ProfileResponse("Request body is required", false));
            }

            // Update user profile
            ProfileResponse profileResponse = profileService.updateUserProfile(userId, profileRequest);

            if (profileResponse.isSuccess()) {
                return ResponseEntity.ok(profileResponse);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(profileResponse);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProfileResponse("Error updating profile: " + e.getMessage(), false));
        }
    }
}
