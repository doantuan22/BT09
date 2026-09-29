package com.example.bai_tap_09_bai_tap.repository;
import com.example.bai_tap_09_bai_tap.entity.OtpToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OtpTokenRepository extends JpaRepository<OtpToken,Long> {
 Optional<OtpToken> findTopByEmailAndTypeAndUsedFalseOrderByCreatedAtDesc(String email,String type);
 void deleteByEmailAndType(String email,String type);
 Optional<OtpToken> findTopByEmailAndTypeOrderByCreatedAtDesc(String email,String type);
}
