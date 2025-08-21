package com.beared.userservice.service;

import com.beared.userservice.dto.UserDTO;
import com.beared.userservice.model.UserAccount;
import com.beared.userservice.repository.UserAccountRepository;
import com.beared.userservice.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
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

    public UserAccount login(String email, String password) {
        Optional<UserAccount> userOpt = repository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException(StringUtil.EMAIL_NOT_FOUND);
        }
        UserAccount user = userOpt.get();
        if(user.getIsActive()=='N')
        {
            throw new IllegalArgumentException(StringUtil.ACCOUNT_NOT_ACTIVATED);
        }
        else if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new IllegalArgumentException(StringUtil.INVALID_CREDENTIALS);
        }
        return user;
    }

    public UserAccount requestAuthCode(String email) {
        Optional<UserAccount> userOpt = repository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException(StringUtil.EMAIL_NOT_FOUND);
        }
        UserAccount user = userOpt.get();
        String authCode = generateAuthCode();
        user.setAuthCode(authCode);
        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(10));
        user.setUpdatedAt(LocalDateTime.now());
        repository.save(user);
        // TODO: Send auth code via email/SMS
        return user;
    }

    public boolean verifyAuthCode(String email, String code) {
        Optional<UserAccount> userOpt = repository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException(StringUtil.EMAIL_NOT_FOUND);
        }
        UserAccount user = userOpt.get();
        if (!code.equals(user.getAuthCode())) {
            throw new IllegalArgumentException(StringUtil.INVALID_AUTHENTICATION_CODE);
        }
        if (LocalDateTime.now().isAfter(user.getResetTokenExpiry())) {
            throw new IllegalArgumentException(StringUtil.EXPIRATION_AUTHENTICATION_CODE);
        }
        user.setAuthCode(null);
        user.setResetTokenExpiry(null);
        user.setIsActive('A');
        user.setUpdatedAt(LocalDateTime.now());
        repository.save(user);
        return true;
    }

    public boolean resetPassword(String email, String authCode, String newPassword) {
        Optional<UserAccount> userOpt = repository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException(StringUtil.EMAIL_NOT_FOUND);
        }
        UserAccount user = userOpt.get();
        if (!authCode.equals(user.getAuthCode())) {
            throw new IllegalArgumentException(StringUtil.INVALID_AUTHENTICATION_CODE);
        }
        if (LocalDateTime.now().isAfter(user.getResetTokenExpiry())) {
            throw new IllegalArgumentException(StringUtil.EXPIRATION_AUTHENTICATION_CODE);
        }
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        user.setAuthCode(null);
        user.setResetTokenExpiry(null);
        user.setUpdatedAt(LocalDateTime.now());
        repository.save(user);
        return true;
    }

    public boolean deleteAccount(String email, String password) {
        Optional<UserAccount> userOpt = repository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException(StringUtil.EMAIL_NOT_FOUND);
        }
        UserAccount user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new IllegalArgumentException(StringUtil.INVALID_CREDENTIALS);
        }
        repository.delete(user);
        return true;
    }

    public UserDTO getUserById(Long userId)
    {
        Optional<UserAccount> userOpt = repository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User Not Found");
        }
        UserDTO currentUser= new UserDTO();
        currentUser.setUserId(userOpt.get().getUserId());
        currentUser.setFullName(userOpt.get().getFullName());
        currentUser.setEmail(userOpt.get().getEmail());
        return currentUser;
    }

    private String generateAuthCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000);
        return String.valueOf(code);
    }

}
