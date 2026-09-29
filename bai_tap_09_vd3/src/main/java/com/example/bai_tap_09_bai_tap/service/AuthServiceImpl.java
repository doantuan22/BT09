package com.example.bai_tap_09_bai_tap.service;

import com.example.bai_tap_09_bai_tap.dto.RegisterDTO;
import com.example.bai_tap_09_bai_tap.entity.Role;
import com.example.bai_tap_09_bai_tap.entity.User;
import com.example.bai_tap_09_bai_tap.repository.RoleRepository;
import com.example.bai_tap_09_bai_tap.repository.UserRepository;
import java.util.Locale;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, OtpService otpService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpService = otpService;
    }

    @Override
    public void register(RegisterDTO dto) {
        String username = dto.getUsername().trim();
        String email = dto.getEmail().trim().toLowerCase(Locale.ROOT);
        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new IllegalArgumentException("Username đã tồn tại.");
        }
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException("Email đã tồn tại.");
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Mật khẩu xác nhận không đúng.");
        }

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Chưa có ROLE_USER."));
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFullName(dto.getFullName().trim());
        user.setEnabled(false);
        user.setRole(role);
        userRepository.save(user);
        otpService.sendRegisterOtp(email);
    }

    @Override
    public boolean verifyRegister(String email, String otp) {
        if (!otpService.verifyRegisterOtp(email, otp)) {
            return false;
        }
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("User không tồn tại."));
        user.setEnabled(true);
        return true;
    }

    @Override
    public void forgotPassword(String email) {
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("Email không tồn tại."));
        otpService.sendResetPasswordOtp(user.getEmail());
    }

    @Override
    public boolean verifyResetOtp(String email, String otp) {
        return otpService.verifyResetPasswordOtp(email, otp);
    }

    @Override
    public void resetPassword(String email, String password) {
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("Email không tồn tại."));
        user.setPassword(passwordEncoder.encode(password));
    }
}
