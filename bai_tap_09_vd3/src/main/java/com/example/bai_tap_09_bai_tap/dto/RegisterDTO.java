package com.example.bai_tap_09_bai_tap.dto;
import jakarta.validation.constraints.*;
public class RegisterDTO {
 @NotBlank @Size(min=3,max=50) private String username;
 @NotBlank @Email @Size(max=254) private String email;
 @NotBlank @Size(min=6,max=100) private String password;
 @NotBlank private String confirmPassword;
 @NotBlank @Size(max=200) private String fullName;
 public String getUsername(){return username;} public void setUsername(String v){username=v;} public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public String getPassword(){return password;} public void setPassword(String v){password=v;} public String getConfirmPassword(){return confirmPassword;} public void setConfirmPassword(String v){confirmPassword=v;}
 public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;}
}
