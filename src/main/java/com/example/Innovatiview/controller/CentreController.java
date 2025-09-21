package com.example.Innovatiview.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Innovatiview.DTO.CentreListResponse;
import com.example.Innovatiview.DTO.CentreResponse;
import com.example.Innovatiview.DTO.CentreSelectionResponse;
import com.example.Innovatiview.service.CentreService;

import java.util.List;

@RestController
@RequestMapping("/centres")
public class CentreController {
    private static final Logger logger = LoggerFactory.getLogger(CentreController.class);

    private final CentreService centreService;

    public CentreController(CentreService centreService) {
        this.centreService = centreService;
    }

    // Get all centres (BIO option)
    @GetMapping("/list")
    public ResponseEntity<CentreListResponse> getAllCentres(@RequestParam String userId) {
        logger.info("API called: GET /centres/list with userId={}", userId);
        List<CentreResponse> centres = centreService.getAllCentres();
        logger.info("Returning {} centres for userId={}", centres.size(), userId);
        return ResponseEntity.ok(new CentreListResponse(userId, centres));
    }

    // Search centres (BIO option)
    @GetMapping("/search")
    public ResponseEntity<CentreListResponse> searchCentres(
            @RequestParam String userId,
            @RequestParam String keyword) {
        logger.info("API called: GET /centres/search with userId={}, keyword={}", userId, keyword);
        List<CentreResponse> centres = centreService.searchCentres(keyword);
        logger.info("Returning {} centres for userId={} and keyword={}", centres.size(), userId, keyword);
        return ResponseEntity.ok(new CentreListResponse(userId, centres));
    }

    @PostMapping("/select")
    public ResponseEntity<CentreSelectionResponse> selectCentre(
            @RequestParam String userId,
            @RequestParam(required = false) String centreCode) {
        logger.info("API called: POST /centres/select with userId={}, centreCode={}", userId, centreCode);

        if (centreCode == null || centreCode.isEmpty()) {
            logger.warn("No centre selected for userId={}", userId);
            CentreSelectionResponse response = new CentreSelectionResponse(
                    userId,
                    null,
                    "No centre selected",
                    400);
            return ResponseEntity.badRequest().body(response);
        }

        logger.info("Centre {} selected for userId={}", centreCode, userId);
        CentreSelectionResponse response = new CentreSelectionResponse(
                userId,
                centreCode,
                "Centre selected successfully",
                200);

        return ResponseEntity.ok(response);
    }
}
