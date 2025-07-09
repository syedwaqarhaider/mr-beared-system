package com.beared.userservice.controller;
import com.beared.userservice.model.UserAccount;
import com.beared.userservice.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserAccount user) {
        UserAccount created = service.registerUser(user);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        Optional<UserAccount> userOpt = service.login(email, password);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }


    @PostMapping("/request-auth-code")
    public ResponseEntity<?> requestAuthCode(@RequestParam String email) {
        return service.requestAuthCode(email)
                .map(u -> ResponseEntity.ok("Auth code sent"))
                .orElse(ResponseEntity.badRequest().body("User not found"));
    }

    @PostMapping("/verify-auth-code")
    public ResponseEntity<?> verifyAuthCode(@RequestParam String email, @RequestParam String code) {
        if (service.verifyAuthCode(email, code)) {
            return ResponseEntity.ok("Auth code valid");
        } else {
            return ResponseEntity.status(401).body("Invalid or expired auth code");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email,
                                           @RequestParam String authCode,
                                           @RequestParam String newPassword) {
        if (service.resetPassword(email, authCode, newPassword)) {
            return ResponseEntity.ok("Password reset successful");
        } else {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
    }
}
