package com.example.bai_tap_09_bai_tap.service;
import com.example.bai_tap_09_bai_tap.entity.OtpToken;
import com.example.bai_tap_09_bai_tap.repository.OtpTokenRepository;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Locale;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service @Transactional
public class OtpServiceImpl implements OtpService {
 private final OtpTokenRepository repository; private final PasswordEncoder encoder; private final EmailService emailService;
 private final SecureRandom random=new SecureRandom();
 public OtpServiceImpl(OtpTokenRepository repository,PasswordEncoder encoder,EmailService emailService){this.repository=repository;this.encoder=encoder;this.emailService=emailService;}
 public void issue(String email,OtpToken.Type type){
  String normalized=email.trim().toLowerCase(Locale.ROOT); repository.deleteByEmailIgnoreCaseAndType(normalized,type);
  String otp=String.format(Locale.ROOT,"%06d",random.nextInt(1_000_000)); OtpToken token=new OtpToken();
  token.setEmail(normalized); token.setOtpHash(encoder.encode(otp)); token.setType(type); token.setExpiresAt(LocalDateTime.now().plusMinutes(5)); token.setAttempts(0); token.setUsed(false); repository.save(token);
  emailService.sendOtp(normalized,otp,type==OtpToken.Type.REGISTER?"đăng ký tài khoản":"đặt lại mật khẩu");
 }
 public boolean verify(String email,String otp,OtpToken.Type type){
  OtpToken token=repository.findFirstByEmailIgnoreCaseAndTypeAndUsedFalseOrderByCreatedAtDesc(email.trim(),type).orElse(null);
  if(token==null||token.getExpiresAt().isBefore(LocalDateTime.now())||token.getAttempts()>=5)return false;
  token.setAttempts(token.getAttempts()+1);
  if(!encoder.matches(otp,token.getOtpHash()))return false;
  token.setUsed(true); return true;
 }
}
