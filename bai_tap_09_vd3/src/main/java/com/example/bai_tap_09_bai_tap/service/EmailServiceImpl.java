package com.example.bai_tap_09_bai_tap.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class EmailServiceImpl implements EmailService {
 private final JavaMailSender mailSender;
 @Value("${app.mail.from:${spring.mail.username:}}") private String from;
 public EmailServiceImpl(JavaMailSender mailSender){this.mailSender=mailSender;}
 public void sendOtp(String email,String otp,String purpose){
  SimpleMailMessage message=new SimpleMailMessage(); if(from!=null&&!from.isBlank())message.setFrom(from);
  message.setTo(email); message.setSubject("UTEShop - "+purpose);
  message.setText("Mã OTP của bạn là "+otp+". Mã có hiệu lực trong 5 phút và chỉ dùng một lần. Không chia sẻ mã này.");
  mailSender.send(message);
 }
}
