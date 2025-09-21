package com.example.Innovatiview.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Innovatiview.DTO.SessionRequest;
import com.example.Innovatiview.DTO.SessionResponse;
import com.example.Innovatiview.service.SessionService;

@RestController
@RequestMapping("/session")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/create")
    public ResponseEntity<SessionResponse> createSession(
            @RequestParam String userId,
            @RequestBody SessionRequest request) {

        SessionResponse response = sessionService.createSession(userId, request);
        return ResponseEntity.ok(response);
    }
}
