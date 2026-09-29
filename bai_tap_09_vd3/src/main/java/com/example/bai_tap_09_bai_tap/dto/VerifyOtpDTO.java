package com.example.bai_tap_09_bai_tap.dto;
import jakarta.validation.constraints.*;
public class VerifyOtpDTO { @NotBlank @Email private String email; @NotBlank @Pattern(regexp="\\d{6}") private String otp;
 public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getOtp(){return otp;} public void setOtp(String v){otp=v;} }
