package com.example.bai_tap_09_bai_tap.service;
import com.example.bai_tap_09_bai_tap.dto.RegisterDTO;
import com.example.bai_tap_09_bai_tap.dto.ResetPasswordDTO;
import com.example.bai_tap_09_bai_tap.entity.*;
import com.example.bai_tap_09_bai_tap.repository.*;
import java.util.Locale;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service @Transactional
public class AuthServiceImpl implements AuthService {
 private final UserRepository users; private final RoleRepository roles; private final OtpService otps; private final PasswordEncoder encoder;
 public AuthServiceImpl(UserRepository users,RoleRepository roles,OtpService otps,PasswordEncoder encoder){this.users=users;this.roles=roles;this.otps=otps;this.encoder=encoder;}
 public void register(RegisterDTO form){
  String username=form.getUsername().trim(),email=form.getEmail().trim().toLowerCase(Locale.ROOT);
  if(!form.getPassword().equals(form.getConfirmPassword()))throw new IllegalArgumentException("Mật khẩu xác nhận không khớp.");
  if(users.existsByUsernameIgnoreCase(username))throw new IllegalArgumentException("Username đã tồn tại.");
  if(users.existsByEmailIgnoreCase(email))throw new IllegalArgumentException("Email đã tồn tại.");
  User user=new User();user.setUsername(username);user.setEmail(email);user.setFullName(form.getFullName().trim());user.setPassword(encoder.encode(form.getPassword()));user.setEnabled(false);
  user.setRole(roles.findByNameIgnoreCase("ROLE_USER").orElseGet(()->roles.save(new Role("ROLE_USER"))));users.save(user);otps.issue(email,OtpToken.Type.REGISTER);
 }
 public void resendRegistrationOtp(String email){User user=users.findByEmailIgnoreCase(email).orElseThrow(()->new IllegalArgumentException("Không tìm thấy tài khoản."));if(user.isEnabled())throw new IllegalArgumentException("Tài khoản đã được xác thực.");otps.issue(user.getEmail(),OtpToken.Type.REGISTER);}
 public void verifyRegistration(String email,String otp){if(!otps.verify(email,otp,OtpToken.Type.REGISTER))throw new IllegalArgumentException("OTP không hợp lệ, hết hạn hoặc vượt số lần thử.");User user=users.findByEmailIgnoreCase(email).orElseThrow(()->new IllegalArgumentException("Không tìm thấy tài khoản."));user.setEnabled(true);}
 public void requestPasswordReset(String email){User user=users.findByEmailIgnoreCase(email).orElseThrow(()->new IllegalArgumentException("Không tìm thấy email."));otps.issue(user.getEmail(),OtpToken.Type.RESET_PASSWORD);}
 public void resetPassword(ResetPasswordDTO form){if(!form.getPassword().equals(form.getConfirmPassword()))throw new IllegalArgumentException("Mật khẩu xác nhận không khớp.");if(!otps.verify(form.getEmail(),form.getOtp(),OtpToken.Type.RESET_PASSWORD))throw new IllegalArgumentException("OTP không hợp lệ, hết hạn hoặc vượt số lần thử.");User user=users.findByEmailIgnoreCase(form.getEmail()).orElseThrow(()->new IllegalArgumentException("Không tìm thấy tài khoản."));user.setPassword(encoder.encode(form.getPassword()));}
}
