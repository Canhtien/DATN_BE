//package com.alibou.security.config;
//
//import com.sun.mail.smtp.SMTPTransport;
//import jakarta.mail.*;
//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeMessage;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.Properties;
//
//@Component
//public class OAuth2MailSender {
//
//    @Value("${gmail.client.id}")
//    private String clientId;
//
//    @Value("${gmail.client.secret}")
//    private String clientSecret;
//
//    @Value("${gmail.refresh.token}")
//    private String refreshToken;
//
//    @Value("${gmail.email}")
//    private String fromEmail;
//
//    public void sendEmail(String toEmail, String subject, String body) {
//        try {
//            String accessToken = EmailConfig.getAccessToken(clientId, clientSecret, refreshToken);
//            Properties props = new Properties();
//            props.put("mail.smtp.auth", "true");
//            props.put("mail.smtp.starttls.enable", "true");
//
//            Session session = Session.getInstance(props);
//            MimeMessage message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(fromEmail));
//            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
//            message.setSubject(subject);
//            message.setContent(body, "text/html; charset=utf-8");
//
//            SMTPTransport transport = (SMTPTransport) session.getTransport("smtp");
//            transport.connect("smtp.gmail.com", 587, fromEmail, null);
//            String oauth2Token = EmailConfig.generateXOAuth2Token(fromEmail, accessToken);
//            transport.issueCommand("AUTH XOAUTH2 " + oauth2Token, 235);
//            transport.sendMessage(message, message.getAllRecipients());
//            transport.close();
//
//            System.out.println("Email sent successfully.");
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Gửi email thất bại", e);
//        }
//    }
//}


package com.alibou.security.config;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class OAuth2MailSender {

    @Value("${gmail.client.id}")
    private String clientId;

    @Value("${gmail.client.secret}")
    private String clientSecret;

    @Value("${gmail.refresh.token}")
    private String refreshToken;

    @Value("${gmail.email}")
    private String fromEmail;

    public void sendEmail(String toEmail, String subject, String body) {
        try {
            String accessToken = EmailConfig.getAccessToken(clientId, clientSecret, refreshToken);
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.auth.mechanisms", "XOAUTH2");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new OAuth2Authenticator(fromEmail, accessToken));
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Gửi email thất bại", e);
        }
    }

    static class OAuth2Authenticator extends Authenticator {
        private final String email;
        private final String accessToken;

        OAuth2Authenticator(String email, String accessToken) {
            this.email = email;
            this.accessToken = accessToken;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(email, accessToken);
        }
    }
}

