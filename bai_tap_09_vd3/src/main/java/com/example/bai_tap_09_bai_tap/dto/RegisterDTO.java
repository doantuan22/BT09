package com.example.bai_tap_09_bai_tap.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public class RegisterDTO {
 @NotBlank(message="Username không được để trống") private String username;
 @NotBlank(message="Email không được để trống") @Email(message="Email không hợp lệ") private String email;
 @NotBlank(message="Mật khẩu không được để trống") @Size(min=6,message="Mật khẩu tối thiểu 6 ký tự") private String password;
 @NotBlank(message="Xác nhận mật khẩu") private String confirmPassword;
 @NotBlank(message="Họ tên không được để trống") private String fullName;
 public String getUsername(){return username;} public void setUsername(String v){username=v;} public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public String getPassword(){return password;} public void setPassword(String v){password=v;} public String getConfirmPassword(){return confirmPassword;} public void setConfirmPassword(String v){confirmPassword=v;}
 public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;}
}
