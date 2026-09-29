package com.example.bai_tap_09_bai_tap.dto;
import jakarta.validation.constraints.*;
public class ForgotPasswordDTO { @NotBlank @Email private String email; public String getEmail(){return email;} public void setEmail(String v){email=v;} }
