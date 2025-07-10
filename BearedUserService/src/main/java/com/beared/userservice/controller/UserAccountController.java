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
@RequestMapping(StringUtil.BASE_API_URL)
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService service;

    @PostMapping(StringUtil.REGISTER_API_URL)
    public ResponseEntity<ApiResponse<?>> register(@RequestBody UserAccount user) {
        UserAccount created = service.registerUser(user);
        return ResponseEntity.ok(new ApiResponse<>(true, StringUtil.REGISTRATION_SUCCESSFULL, created));
    }

    @PostMapping(StringUtil.LOGIN_API_URL)
    public ResponseEntity<ApiResponse<?>> login(@RequestParam String email, @RequestParam String password) {
        UserAccount user = service.login(email, password);
        return ResponseEntity.ok(new ApiResponse<>(true, StringUtil.LOGIN_SUCCESSFULL, user));
    }

    @PostMapping(StringUtil.REQUEST_AUTH_CODE_API_URL)
    public ResponseEntity<ApiResponse<?>> requestAuthCode(@RequestParam String email) {
        UserAccount user = service.requestAuthCode(email);
        return ResponseEntity.ok(new ApiResponse<>(true, StringUtil.AUTH_REQUEST_SUCCESS, user.getAuthCode()));
    }

    @PostMapping(StringUtil.VERIFY_AUTH_CODE_API_URL)
    public ResponseEntity<ApiResponse<?>> verifyAuthCode(@RequestParam String email, @RequestParam String code) {
        service.verifyAuthCode(email, code);
        return ResponseEntity.ok(new ApiResponse<>(true, StringUtil.AUTH_CODE_SUCCESS, null));
    }

    @PostMapping(StringUtil.RESET_PASSWORD_API_URL)
    public ResponseEntity<ApiResponse<?>> resetPassword(@RequestParam String email,
                                                        @RequestParam String authCode,
                                                        @RequestParam String newPassword) {
        service.resetPassword(email, authCode, newPassword);
        return ResponseEntity.ok(new ApiResponse<>(true, StringUtil.PASSWORD_RESET_SUCCESS, null));
    }

    @DeleteMapping(StringUtil.DELETE_API_URL)
    public ResponseEntity<ApiResponse<?>> deleteAccount(@RequestParam String email, @RequestParam String password) {
        service.deleteAccount(email, password);
        return ResponseEntity.ok(new ApiResponse<>(true, StringUtil.ACCOUNT_DELETE_SUCCESS, null));
    }
}
