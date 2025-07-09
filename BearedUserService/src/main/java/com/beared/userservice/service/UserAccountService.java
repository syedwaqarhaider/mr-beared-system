package com.beared.userservice.service;

import com.beared.userservice.model.UserAccount;
import com.beared.userservice.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository repository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserAccount registerUser(UserAccount user) {
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return repository.save(user);
    }

    public Optional<UserAccount> login(String email, String password) {
        Optional<UserAccount> userOpt = repository.findByEmail(email);
        if (userOpt.isPresent()) {
            if (passwordEncoder.matches(password, userOpt.get().getPasswordHash())) {
                return userOpt;
            }
        }
        return Optional.empty();
    }

    public Optional<UserAccount> requestAuthCode(String email) {
        Optional<UserAccount> userOpt = repository.findByEmail(email);
        userOpt.ifPresent(user -> {
            String authCode = generateAuthCode();
            user.setAuthCode(authCode);
            user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(10));
            user.setUpdatedAt(LocalDateTime.now());
            repository.save(user);
            // TODO: Send authCode by email or SMS here
        });
        return userOpt;
    }

    public boolean verifyAuthCode(String email, String code) {
        Optional<UserAccount> userOpt = repository.findByEmail(email);
        if (userOpt.isPresent()) {
            UserAccount user = userOpt.get();
            return code.equals(user.getAuthCode()) && LocalDateTime.now().isBefore(user.getResetTokenExpiry());
        }
        return false;
    }

    public boolean resetPassword(String email, String authCode, String newPassword) {
        Optional<UserAccount> userOpt = repository.findByEmail(email);
        if (userOpt.isPresent()) {
            UserAccount user = userOpt.get();
            if (authCode.equals(user.getAuthCode()) && LocalDateTime.now().isBefore(user.getResetTokenExpiry())) {
                user.setPasswordHash(passwordEncoder.encode(newPassword));
                user.setAuthCode(null);
                user.setResetTokenExpiry(null);
                user.setUpdatedAt(LocalDateTime.now());
                repository.save(user);
                return true;
            }
        }
        return false;
    }

    private String generateAuthCode() {
        return UUID.randomUUID().toString().substring(0, 4); // Simple 4-char code
    }
}
