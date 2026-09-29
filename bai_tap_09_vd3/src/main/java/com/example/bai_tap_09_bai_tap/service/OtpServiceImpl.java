package com.example.bai_tap_09_bai_tap.service;

import com.example.bai_tap_09_bai_tap.entity.OtpToken;
import com.example.bai_tap_09_bai_tap.repository.OtpTokenRepository;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Locale;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OtpServiceImpl implements OtpService {
    private static final int MAX_ATTEMPTS = 5;
    private static final int OTP_MINUTES = 5;

    private final OtpTokenRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final SecureRandom random = new SecureRandom();

    public OtpServiceImpl(OtpTokenRepository repository, PasswordEncoder passwordEncoder,
                          EmailService emailService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    private void send(String email, String type, String subject) {
        String normalizedEmail = email.trim().toLowerCase(Locale.ROOT);
        repository.deleteByEmailAndType(normalizedEmail, type);

        String otp = "%06d".formatted(random.nextInt(1_000_000));
        OtpToken token = new OtpToken();
        token.setEmail(normalizedEmail);
        token.setOtpHash(passwordEncoder.encode(otp));
        token.setType(type);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(OTP_MINUTES));
        token.setAttempts(0);
        token.setUsed(false);
        token.setCreatedAt(LocalDateTime.now());
        repository.save(token);
        emailService.sendOtp(normalizedEmail, otp, subject);
    }

    private boolean verify(String email, String otp, String type) {
        String normalizedEmail = email.trim().toLowerCase(Locale.ROOT);
        OtpToken token = repository
                .findTopByEmailAndTypeAndUsedFalseOrderByCreatedAtDesc(normalizedEmail, type)
                .orElse(null);
        if (token == null || token.getExpiresAt().isBefore(LocalDateTime.now())
                || token.getAttempts() >= MAX_ATTEMPTS) {
            return false;
        }

        token.setAttempts(token.getAttempts() + 1);
        if (!passwordEncoder.matches(otp, token.getOtpHash())) {
            repository.save(token);
            return false;
        }
        token.setUsed(true);
        repository.save(token);
        return true;
    }

    @Override
    public void sendRegisterOtp(String email) {
        send(email, "REGISTER", "Shop - Xác nhận đăng ký tài khoản");
    }

    @Override
    public boolean verifyRegisterOtp(String email, String otp) {
        return verify(email, otp, "REGISTER");
    }

    @Override
    public void sendResetPasswordOtp(String email) {
        send(email, "RESET_PASSWORD", "Shop - OTP đặt lại mật khẩu");
    }

    @Override
    public boolean verifyResetPasswordOtp(String email, String otp) {
        return verify(email, otp, "RESET_PASSWORD");
    }
}
