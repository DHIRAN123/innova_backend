# Operator Registration API Implementation

## Steps to Complete:

1. [x] Update RegisterUser.java DTO - Add missing fields (Father's Name, Mobile Number) and include userId field
2. [x] Update UserEntity.java - Add the new fields to match the registration requirements
3. [x] Create RegisterService.java - Service layer for business logic
4. [x] Implement RegisterController.java - REST endpoints for operator registration
5. [x] Update UserRepository.java - Add methods for finding users by email/Aadhar
6. [x] Test the API endpoint
7. [x] Verify database operations
8. [x] Check ID generation logic

## OTP-Based Login Implementation:

9. [x] Create OtpService.java - Service for generating and verifying OTPs
10. [x] Create EmailService.java - Service for sending OTP emails (real implementation)
11. [x] Update LoginRequest.java - Change from centerCode/password to email/otp
12. [x] Update LoginResponse.java - Add OTP-related fields and response types
13. [x] Update AuthController.java - Implement OTP-based login flow
14. [x] Update RegisterService.java - Send confirmation email after registration

## Real Email Implementation:

15. [x] Add spring-boot-starter-mail dependency to pom.xml
16. [x] Create MailConfig.java - Configuration for JavaMailSender
17. [x] Update application.properties with email configuration
18. [x] Update EmailService.java to use real email sending

## Current Status:

- RegisterController.java exists but is empty
- RegisterUser.java DTO exists but is incomplete
- UserEntity.java exists for database entity
- UserRepository.java exists for database operations
- CustomIdGenerator.java exists for ID generation
- OtpService.java - Created for OTP generation and verification
- EmailService.java - Updated for real email sending using JavaMailSender
- AuthController.java - Updated with OTP-based login endpoints
- RegisterService.java - Updated to send confirmation emails
- MailConfig.java - Created for email configuration

## API Endpoints:

### Registration:

- POST /auth/register - Register new operator

### Login (OTP-based):

- POST /auth/request-otp - Request OTP for login
- POST /auth/verify-otp - Verify OTP and complete login
- POST /auth/login - Legacy endpoint (backward compatibility)

### Availability Checks:

- GET /auth/check-email/{email}
- GET /auth/check-mobile/{mobile}
- GET /auth/check-aadhar/{aadhar}

## Email Configuration Setup:

### Gmail SMTP Setup:

1. Enable 2-factor authentication on your Gmail account
2. Generate an App Password:
   - Go to Google Account settings
   - Security → 2-Step Verification → App passwords
   - Generate password for "Mail"
3. Update application.properties:
   ```properties
   spring.mail.username=your-email@gmail.com
   spring.mail.password=your-16-character-app-password
   app.email.from=your-email@gmail.com
   ```

### Outlook/Hotmail Setup:

1. Enable SMTP in Outlook settings
2. Use your email and password or app password
3. Update application.properties:
   ```properties
   spring.mail.host=smtp-mail.outlook.com
   spring.mail.username=your-email@outlook.com
   spring.mail.password=your-password
   ```

## Testing:

### Registration Test:

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "John Doe",
    "email": "john.doe@example.com",
    "fatherName": "Jane Doe",
    "mobileNumber": "9876543210",
    "aadharNumber": "123456789012"
  }'
```

### Login Test:

```bash
# Request OTP
curl -X POST http://localhost:8080/auth/request-otp \
  -H "Content-Type: application/json" \
  -d '{"email": "john.doe@example.com"}'

# Verify OTP (check your email for actual OTP)
curl -X POST http://localhost:8080/auth/verify-otp \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john.doe@example.com",
    "otp": "123456"
  }'
```

## Notes:

- ✅ **Real Email Service**: Now sends actual emails using JavaMailSender
- ⏰ **OTP Expiry**: 5 minutes
- 🆔 **User ID Format**: First 3 chars of name + last 3 digits of mobile + last 4 digits of Aadhar + 8-char unique ID
- ✅ **Validation**: All fields required with proper format validation
- 🚫 **Duplicate Prevention**: Checks email, mobile, and Aadhar uniqueness
- 📧 **Email Configuration**: Requires proper SMTP setup (Gmail/Outlook/Yahoo)

## Important Setup Steps:

1. **Configure Email Settings** in application.properties
2. **Enable Less Secure Apps** or use App Passwords for Gmail
3. **Test Email Sending** before using in production
4. **Update Database Configuration** if needed (currently using H2 in-memory)
