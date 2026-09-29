package com.example.bai_tap_09_bai_tap.service;
import com.example.bai_tap_09_bai_tap.dto.RegisterDTO;
import com.example.bai_tap_09_bai_tap.dto.ResetPasswordDTO;
public interface AuthService { void register(RegisterDTO form); void resendRegistrationOtp(String email); void verifyRegistration(String email,String otp); void requestPasswordReset(String email); void resetPassword(ResetPasswordDTO form); }
