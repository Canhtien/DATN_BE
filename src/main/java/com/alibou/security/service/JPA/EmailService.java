package com.alibou.security.service.JPA;


import com.alibou.security.config.OAuth2MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final OAuth2MailSender mailSender;

    public void sendOtpEmail(String toEmail, String otpCode) {
        String subject = "OTP Quên Mật Khẩu";
        String body = "<h3>Mã OTP của bạn là: <b>" + otpCode + "</b></h3>";
        mailSender.sendEmail(toEmail, subject, body);
    }
}

