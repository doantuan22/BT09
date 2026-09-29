package com.example.bai_tap_09_bai_tap.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="otp_tokens", indexes=@Index(name="idx_otp_email_type", columnList="email,type"))
public class OtpToken {
    public enum Type { REGISTER, RESET_PASSWORD }
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=254) private String email;
    @Column(nullable=false,length=128) private String otpHash;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=30) private Type type;
    @Column(nullable=false) private LocalDateTime expiresAt;
    @Column(nullable=false) private int attempts;
    @Column(nullable=false) private boolean used;
    @Column(nullable=false) private LocalDateTime createdAt=LocalDateTime.now();
    public OtpToken() {}
    public Long getId(){return id;} public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getOtpHash(){return otpHash;} public void setOtpHash(String v){otpHash=v;}
    public Type getType(){return type;} public void setType(Type v){type=v;}
    public LocalDateTime getExpiresAt(){return expiresAt;} public void setExpiresAt(LocalDateTime v){expiresAt=v;}
    public int getAttempts(){return attempts;} public void setAttempts(int v){attempts=v;}
    public boolean isUsed(){return used;} public void setUsed(boolean v){used=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}
