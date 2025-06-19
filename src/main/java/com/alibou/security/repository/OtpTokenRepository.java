package com.alibou.security.repository;

import com.alibou.security.entity.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpTokenRepository extends JpaRepository<OtpToken, Long> {
    Optional<OtpToken> findByEmailAndCodeAndUsedIsFalse(String email, String code);
}
