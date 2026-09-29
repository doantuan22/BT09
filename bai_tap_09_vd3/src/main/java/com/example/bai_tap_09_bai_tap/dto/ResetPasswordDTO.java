package com.example.bai_tap_09_bai_tap.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public class ResetPasswordDTO {
 @NotBlank @Email private String email;
 @NotBlank @Size(min=6) private String password;
 @NotBlank private String confirmPassword;
 public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public String getPassword(){return password;} public void setPassword(String v){password=v;}
 public String getConfirmPassword(){return confirmPassword;} public void setConfirmPassword(String v){confirmPassword=v;}
}
