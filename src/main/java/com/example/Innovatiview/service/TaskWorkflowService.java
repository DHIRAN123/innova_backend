package com.example.Innovatiview.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.Innovatiview.DTO.TaskWorkflowEntity;
import com.example.Innovatiview.DTO.TaskWorkflowResponse;
import com.example.Innovatiview.repository.TaskWorkflowRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class TaskWorkflowService {

    private final TaskWorkflowRepository repo;

    public TaskWorkflowService(TaskWorkflowRepository repo) {
        this.repo = repo;
    }

    // Mark video watched
    public TaskWorkflowResponse markVideoWatched(String userId) {
        TaskWorkflowEntity task = getOrCreate(userId);
        task.setVideoWatched(true);
        repo.save(task);
        return buildResponse(task, "✅ Video marked as watched");
    }

    // Mark agreement
    public TaskWorkflowResponse markAgreement(String userId, boolean checked) {
        TaskWorkflowEntity task = getOrCreate(userId);
        task.setAgreementChecked(checked);
        repo.save(task);
        return buildResponse(task, "✅ Agreement updated");
    }

    // Upload Operator Selfie
    public TaskWorkflowResponse uploadOperatorSelfie(String userId, MultipartFile file, double lat, double lng)
            throws IOException {

        System.out.println("==== Upload Operator Selfie Debug Start ====");
        System.out.println("UserId: " + userId);
        System.out.println("Latitude: " + lat + ", Longitude: " + lng);

        if (file == null) {
            System.out.println("❌ MultipartFile is NULL (frontend did not send the file with key 'file').");
            throw new RuntimeException("File is missing in request.");
        } else {
            System.out.println("✅ File object received.");
            System.out.println("Original Filename: " + file.getOriginalFilename());
            System.out.println("Content Type: " + file.getContentType());
            System.out.println("File Size: " + file.getSize());
        }

        // ✅ check geo location within 150m (pseudo logic)
        double allowedLat = 28.6139; // your fixed lat
        double allowedLng = 77.2090; // your fixed lng
        double distance = haversine(lat, lng, allowedLat, allowedLng);
        System.out.println("Calculated distance from allowed location: " + distance + " km");

        if (distance > 0.15) { // in km
            System.out.println("❌ Location validation failed. Outside allowed range.");
            throw new RuntimeException("❌ Not in allowed geo-location range.");
        }

        TaskWorkflowEntity task = getOrCreate(userId);

        // Save file
        String filePath = saveFile(file, "operator_" + userId);
        task.setOperatorSelfiePath(filePath);
        task.setOperatorSelfieTime(LocalDateTime.now());
        repo.save(task);

        System.out.println("✅ File saved at path: " + filePath);
        System.out.println("==== Upload Operator Selfie Debug End ====");

        return buildResponse(task, "✅ Operator Selfie uploaded");
    }

    // Upload CSR Sheet (after 6hrs)
    public TaskWorkflowResponse uploadCsrSheet(String userId, MultipartFile file) throws IOException {
        TaskWorkflowEntity task = getOrCreate(userId);
        if (task.getOperatorSelfieTime() == null ||
                LocalDateTime.now().isBefore(task.getOperatorSelfieTime().plusHours(1))) {
            throw new RuntimeException("❌ CSR Sheet not enabled yet.");
        }
        String filePath = saveFile(file, "csr_" + userId);
        task.setCsrSheetPath(filePath);
        task.setCsrSheetTime(LocalDateTime.now());
        repo.save(task);
        return buildResponse(task, "✅ CSR Sheet uploaded");
    }

    // Upload Exit Selfie (after 7.5 hrs)
    public TaskWorkflowResponse uploadExitSelfie(String userId, MultipartFile file) throws IOException {
        TaskWorkflowEntity task = getOrCreate(userId);
        if (task.getOperatorSelfieTime() == null ||
                LocalDateTime.now().isBefore(task.getOperatorSelfieTime().plusMinutes(00))) {
            throw new RuntimeException("❌ Exit Selfie not enabled yet.");
        }
        String filePath = saveFile(file, "exit_" + userId);
        task.setExitSelfiePath(filePath);
        task.setExitSelfieTime(LocalDateTime.now());
        repo.save(task);
        return buildResponse(task, "✅ Exit Selfie uploaded");
    }

    // Get current status
    public TaskWorkflowResponse getStatus(String userId) {
        TaskWorkflowEntity task = getOrCreate(userId);
        return buildResponse(task, "ℹ️ Current status fetched");
    }

   private TaskWorkflowEntity getOrCreate(String userId) {
    return repo.findFirstByUserIdOrderByCreatedAtDesc(userId)
            .orElseGet(() -> new TaskWorkflowEntity(userId));
}

    private String saveFile(MultipartFile file, String prefix) throws IOException {
        String fileName = prefix + "_" + file.getOriginalFilename();
        File dest = new File("uploads/" + fileName);
        dest.getParentFile().mkdirs();
        file.transferTo(dest);
        return dest.getAbsolutePath();
    }

    // Utility: Haversine formula for geo distance
    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth radius km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    private TaskWorkflowResponse buildResponse(TaskWorkflowEntity task, String message) {
        boolean csrEnabled = task.getOperatorSelfieTime() != null &&
                LocalDateTime.now().isAfter(task.getOperatorSelfieTime().plusHours(2));

        boolean exitEnabled = task.getOperatorSelfieTime() != null &&
                LocalDateTime.now().isAfter(task.getOperatorSelfieTime().plusMinutes(00));

        return new TaskWorkflowResponse(
                task.getUserId(),
                task.isVideoWatched(),
                task.isAgreementChecked(),
                true, // Operator selfie is always enabled if geo-location ok
                task.getOperatorSelfiePath(),
                task.getOperatorSelfieTime(),
                csrEnabled,
                task.getCsrSheetPath(),
                task.getCsrSheetTime(),
                exitEnabled,
                task.getExitSelfiePath(),
                task.getExitSelfieTime(),
                message);
    }
}
