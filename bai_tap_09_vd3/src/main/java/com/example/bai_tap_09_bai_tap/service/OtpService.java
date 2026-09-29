package com.example.bai_tap_09_bai_tap.service;
import com.example.bai_tap_09_bai_tap.entity.OtpToken;
public interface OtpService { void issue(String email, OtpToken.Type type); boolean verify(String email,String otp,OtpToken.Type type); }
