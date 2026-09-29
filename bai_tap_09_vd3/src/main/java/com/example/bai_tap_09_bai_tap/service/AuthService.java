package com.example.bai_tap_09_bai_tap.service;

import com.example.bai_tap_09_bai_tap.dto.RegisterDTO;

public interface AuthService {
    void register(RegisterDTO dto);
    boolean verifyRegister(String email, String otp);
    void forgotPassword(String email);
    boolean verifyResetOtp(String email, String otp);
    void resetPassword(String email, String password);
}
