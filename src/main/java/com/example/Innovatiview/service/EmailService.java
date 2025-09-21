package com.example.Innovatiview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.email.from}")
    private String fromEmail;

    @Value("${app.email.otp-subject}")
    private String otpSubject;

    @Value("${app.email.registration-subject}")
    private String registrationSubject;

    /**
     * Send OTP to email (real email implementation)
     */
    public boolean sendOtpEmail(String email, String otp) {
        System.out.println("🔄 EmailService.sendOtpEmail called");
        System.out.println("📧 Email: " + email);
        System.out.println("📧 OTP: " + otp);
        System.out.println("📧 From: " + fromEmail);
        System.out.println("📧 Subject: " + otpSubject);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setSubject(otpSubject);
            message.setText(buildOtpEmailBody(otp));

            System.out.println("📨 Attempting to send email via SMTP...");
            mailSender.send(message);

            System.out.println("✅ OTP email sent successfully to: " + email);
            logger.info("OTP email sent successfully to: {}", email);
            return true;

        } catch (Exception e) {
            System.err.println("❌ Failed to send OTP email to: " + email);
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
            logger.error("Failed to send OTP email to: {}", email, e);
            return false;
        }
    }

    /**
     * Send registration confirmation email (real email implementation)
     */
    public boolean sendRegistrationConfirmationEmail(String email, String userId) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setSubject(registrationSubject);
            message.setText(buildRegistrationEmailBody(userId));

            mailSender.send(message);

            logger.info("Registration confirmation email sent to: {}", email);
            return true;

        } catch (Exception e) {
            logger.error("Failed to send registration confirmation email to: {} - {}", email, e.getMessage());
            // Don't throw exception - just log and return false to allow registration to
            // continue
            return false;
        }
    }

    /**
     * Build OTP email body
     */
    private String buildOtpEmailBody(String otp) {
        return String.format(
                "Dear User,\n\n" +
                        "Your 4-digit OTP for login to InnovativeView is: %s\n\n" +
                        "This OTP will expire in 5 minutes.\n\n" +
                        "Please use this OTP to complete your login process.\n\n" +
                        "If you didn't request this OTP, please ignore this email.\n\n" +
                        "Best regards,\n" +
                        "InnovativeView Team",
                otp);
    }

    /**
     * Build registration confirmation email body
     */
    private String buildRegistrationEmailBody(String userId) {
        return String.format(
                "Dear User,\n\n" +
                        "Welcome to InnovativeView!\n\n" +
                        "Your registration has been completed successfully.\n\n" +
                        "Your User ID: %s\n\n" +
                        "You can now login using your email address.\n\n" +
                        "Best regards,\n" +
                        "InnovativeView Team",
                userId);
    }
}
