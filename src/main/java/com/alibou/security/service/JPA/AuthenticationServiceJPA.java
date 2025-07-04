package com.alibou.security.service.JPA;

import com.alibou.security.entity.Role;
import com.alibou.security.entity.Token;
import com.alibou.security.entity.User;
import com.alibou.security.enums.TokenType;
import com.alibou.security.model.request.AuthenticationRequest;
import com.alibou.security.model.request.RegisterRequest;
import com.alibou.security.model.response.AuthenticationResponse;
import com.alibou.security.repository.RoleRepository;
import com.alibou.security.repository.TokenRepository;
import com.alibou.security.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceJPA {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceJPA.class);
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceJPA jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }

        if (repository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already in use");
        }

        String roleName;
        if (request.getRole() != null && ("ADMIN".equalsIgnoreCase(request.getRole()) || "MANAGER".equalsIgnoreCase(request.getRole()))) {
            roleName = request.getRole().toUpperCase();
        } else {
            roleName = "USER";
        }

        Role userRole = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role " + roleName + " not found"));

        var user = User.builder()
                .username(request.getUsername())
//                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .dateOfBirth(Date.valueOf(request.getYear() + "-" + request.getMonth() + "-" + request.getDay()))
                .phone(request.getPhone())
                .createdAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime())
                .updatedAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime())
                .role(userRole)
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user, user.getId(), user.getRole());
        var refreshToken = jwtService.generateRefreshToken(user, user.getId(),user.getRole());
        saveUserToken(savedUser, jwtToken);
        logger.info("User registered successfully: {}", request.getEmail());
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        String usernameOrEmail = request.getUsernameOrEmail();
        User user = repository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or email"));
        if (!user.isStatus()) {
            logger.info("User is blocked.");
            throw new IllegalArgumentException("Tài khoản của bạn đã bị khóa.");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        request.getPassword()
                )
        );

        var jwtToken = jwtService.generateToken(user, user.getId(), user.getRole());
        var refreshToken = jwtService.generateRefreshToken(user, user.getId(),user.getRole());
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        logger.info("User authenticated successfully: {}", request.getUsernameOrEmail());
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(Math.toIntExact(user.getId()));
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user, user.getId(), user.getRole());
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                logger.info("Token refreshed successfully for user: {}", userEmail);
            }
        }
    }
}