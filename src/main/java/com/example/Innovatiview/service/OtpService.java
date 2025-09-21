package com.example.Innovatiview.service;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    // In-memory storage for OTPs (in production, use Redis or database)
    private final Map<String, OtpData> otpStorage = new HashMap<>();

    // OTP expiry time in minutes
    private static final int OTP_EXPIRY_MINUTES = 5;

    /**
     * Generate OTP for email
     */
    public String generateOtp(String email) {
        // Generate 4-digit OTP
        String otp = String.format("%04d", new Random().nextInt(9999));

        // Store OTP with expiry time
        OtpData otpData = new OtpData(otp, LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES));
        otpStorage.put(email, otpData);

        return otp;
    }

    /**
     * Verify OTP for email
     */
    public boolean verifyOtp(String email, String otp) {
        OtpData otpData = otpStorage.get(email);

        if (otpData == null) {
            return false;
        }

        // Check if OTP is expired
        if (otpData.getExpiryTime().isBefore(LocalDateTime.now())) {
            otpStorage.remove(email); // Clean up expired OTP
            return false;
        }

        // Check if OTP matches
        if (otpData.getOtp().equals(otp)) {
            otpStorage.remove(email); // Clean up used OTP
            return true;
        }

        return false;
    }

    /**
     * Check if OTP exists for email
     */
    public boolean hasOtp(String email) {
        return otpStorage.containsKey(email);
    }

    /**
     * Clean up expired OTPs
     */
    public void cleanupExpiredOtps() {
        LocalDateTime now = LocalDateTime.now();
        otpStorage.entrySet().removeIf(entry -> entry.getValue().getExpiryTime().isBefore(now));
    }

    /**
     * Inner class to store OTP data
     */
    private static class OtpData {
        private final String otp;
        private final LocalDateTime expiryTime;

        public OtpData(String otp, LocalDateTime expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }

        public String getOtp() {
            return otp;
        }

        public LocalDateTime getExpiryTime() {
            return expiryTime;
        }
    }
}
