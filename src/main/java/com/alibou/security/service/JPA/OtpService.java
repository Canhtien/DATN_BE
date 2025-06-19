package com.alibou.security.service.JPA;

import com.alibou.security.entity.OtpToken;
import com.alibou.security.repository.OtpTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpTokenRepository otpTokenRepository;
    private final EmailService emailService;

    public void generateAndSendOtp(String email) {
        String otpCode = String.valueOf(100000 + new Random().nextInt(900000)); // OTP 6 số

        OtpToken otpToken = OtpToken.builder()
                .email(email)
                .code(otpCode)
                .expiredAt(LocalDateTime.now().plusMinutes(5))
                .used(false)
                .build();

        otpTokenRepository.save(otpToken);

        emailService.sendOtpEmail(email, otpCode);
    }

    public boolean verifyOtp(String email, String code) {
        Optional<OtpToken> token = otpTokenRepository.findByEmailAndCodeAndUsedIsFalse(email, code);
        if (token.isPresent() && token.get().getExpiredAt().isAfter(LocalDateTime.now())) {
            token.get().setUsed(true);
            otpTokenRepository.save(token.get());
            return true;
        }
        return false;
    }
}
