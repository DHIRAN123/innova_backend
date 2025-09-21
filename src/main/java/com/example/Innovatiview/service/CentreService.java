package com.example.Innovatiview.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.Innovatiview.DTO.CentreResponse;
import com.example.Innovatiview.repository.CentreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CentreService {
    private static final Logger logger = LoggerFactory.getLogger(CentreService.class);

    private final CentreRepository centreRepository;

    public CentreService(CentreRepository centreRepository) {
        this.centreRepository = centreRepository;
    }

    // Get all centres
    public List<CentreResponse> getAllCentres() {
        logger.info("Fetching all centres from DB...");
        List<CentreResponse> centres = centreRepository.findAll()
                .stream()
                .map(c -> new CentreResponse(c.getId(), c.getCentreCode(), c.getCentreName()))
                .collect(Collectors.toList());
        logger.info("Fetched {} centres", centres.size());
        return centres;
    }

    // Search centres by name
    public List<CentreResponse> searchCentres(String keyword) {
        logger.info("Searching centres with keyword: {}", keyword);
        List<CentreResponse> centres = centreRepository.findByCentreNameContainingIgnoreCase(keyword)
                .stream()
                .map(c -> new CentreResponse(c.getId(), c.getCentreCode(), c.getCentreName()))
                .collect(Collectors.toList());
        logger.info("Found {} centres for keyword '{}'", centres.size(), keyword);
        return centres;
    }
}
