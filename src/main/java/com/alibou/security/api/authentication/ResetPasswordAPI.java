package com.alibou.security.api.authentication;

import com.alibou.security.entity.User;
import com.alibou.security.model.request.ResetPasswordRequest;
import com.alibou.security.repository.UserRepository;
import com.alibou.security.service.JPA.OtpService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ResetPasswordAPI {

    private final UserRepository userRepository;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("Email không tồn tại");
        }
        otpService.generateAndSendOtp(email);
        return ResponseEntity.ok("Đã gửi OTP về email");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        boolean valid = otpService.verifyOtp(email, otp);
        if (valid) {
            return ResponseEntity.ok("OTP hợp lệ");
        }
        return ResponseEntity.badRequest().body("OTP không hợp lệ hoặc hết hạn");
    }

    @Transactional
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        boolean valid = otpService.verifyOtp(request.getEmail(), request.getOtp());
        if (!valid) {
            return ResponseEntity.badRequest().body("OTP không hợp lệ hoặc hết hạn");
        }

        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()) {
            user.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user.get());
            return ResponseEntity.ok("Đổi mật khẩu thành công");
        } else {
            return ResponseEntity.badRequest().body("User không tồn tại");
        }
    }
}
