package com.beared.userservice.controller;
import com.beared.userservice.model.UserAccount;
import com.beared.userservice.response.ApiResponse;
import com.beared.userservice.service.UserAccountService;
import com.beared.userservice.util.StringUtil;
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
    public ResponseEntity<ApiResponse<?>> register(@RequestBody UserAccount user) {
        UserAccount created = service.registerUser(user);
        return ResponseEntity.ok(new ApiResponse<>(true, StringUtil.REGISTRATION_SUCCESSFULL, created));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestParam String email, @RequestParam String password) {
        UserAccount user = service.login(email, password);
        return ResponseEntity.ok(new ApiResponse<>(true, StringUtil.LOGIN_SUCCESSFULL, user));
    }

    @PostMapping("/request-auth-code")
    public ResponseEntity<ApiResponse<?>> requestAuthCode(@RequestParam String email) {
        UserAccount user = service.requestAuthCode(email);
        return ResponseEntity.ok(new ApiResponse<>(true, StringUtil.AUTH_REQUEST_SUCCESS, user.getAuthCode()));
    }

    @PostMapping("/verify-auth-code")
    public ResponseEntity<ApiResponse<?>> verifyAuthCode(@RequestParam String email, @RequestParam String code) {
        service.verifyAuthCode(email, code);
        return ResponseEntity.ok(new ApiResponse<>(true, StringUtil.AUTH_CODE_SUCCESS, null));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<?>> resetPassword(@RequestParam String email,
                                                        @RequestParam String authCode,
                                                        @RequestParam String newPassword) {
        service.resetPassword(email, authCode, newPassword);
        return ResponseEntity.ok(new ApiResponse<>(true, StringUtil.PASSWORD_RESET_SUCCESS, null));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<?>> deleteAccount(@RequestParam String email, @RequestParam String password) {
        service.deleteAccount(email, password);
        return ResponseEntity.ok(new ApiResponse<>(true, StringUtil.ACCOUNT_DELETE_SUCCESS, null));
    }
}
