package com.example.Innovatiview.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.Innovatiview.DTO.TaskWorkflowResponse;
import com.example.Innovatiview.service.TaskWorkflowService;

@RestController
@RequestMapping("/workflow")
public class TaskWorkflowController {

    private final TaskWorkflowService service;

    public TaskWorkflowController(TaskWorkflowService service) {
        this.service = service;
    }

    @PostMapping("/videoWatched")
    public ResponseEntity<TaskWorkflowResponse> markVideoWatched(@RequestParam String userId) {
        return ResponseEntity.ok(service.markVideoWatched(userId));
    }

    @PostMapping("/agreement")
    public ResponseEntity<TaskWorkflowResponse> markAgreement(@RequestParam String userId,
            @RequestParam boolean checked) {
        return ResponseEntity.ok(service.markAgreement(userId, checked));
    }

      @PostMapping("/upload/operatorSelfie")
    public ResponseEntity<TaskWorkflowResponse> uploadOperatorSelfie(
           @RequestParam("file") MultipartFile file, 
            @RequestParam String userId,
            @RequestParam double lat,
            @RequestParam double lng
            ) throws Exception {

        System.out.println("==== Controller Debug ====");
        System.out.println("UserId: " + userId);
        System.out.println("Lat: " + lat + ", Lng: " + lng);
        System.out.println("File: " + (file != null ? file.getOriginalFilename() : "NULL"));
        System.out.println("File Size: " + (file != null ? file.getSize() : "NULL"));
        System.out.println("Content Type: " + (file != null ? file.getContentType() : "NULL"));

        return ResponseEntity.ok(service.uploadOperatorSelfie(userId, file, lat, lng));
    }

    @PostMapping("/upload/csrSheet")
    public ResponseEntity<TaskWorkflowResponse> uploadCsrSheet(
            @RequestParam String userId,
            @RequestParam MultipartFile file) throws Exception {
        return ResponseEntity.ok(service.uploadCsrSheet(userId, file));
    }

    @PostMapping("/upload/exitSelfie")
    public ResponseEntity<TaskWorkflowResponse> uploadExitSelfie(
            @RequestParam String userId,
            @RequestParam MultipartFile file) throws Exception {
        return ResponseEntity.ok(service.uploadExitSelfie(userId, file));
    }

    // New: Get current status anytime
    @GetMapping("/status")
    public ResponseEntity<TaskWorkflowResponse> getStatus(@RequestParam String userId) {
        return ResponseEntity.ok(service.getStatus(userId));
    }
}
