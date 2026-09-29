package com.example.bai_tap_09_bai_tap.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public class VerifyOtpDTO { @NotBlank @Email private String email; @NotBlank @Size(min=6,max=6) private String otp;
 public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getOtp(){return otp;} public void setOtp(String v){otp=v;} }
