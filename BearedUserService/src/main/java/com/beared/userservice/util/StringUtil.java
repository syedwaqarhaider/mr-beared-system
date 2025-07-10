package com.beared.userservice.util;

public class StringUtil {
    public static final String REGISTRATION_SUCCESSFULL="Your account has been created";
    public static final String LOGIN_SUCCESSFULL="Login successful.";
    public static final String AUTH_REQUEST_SUCCESS="Authentication code sent to your registred email";
    public static final String AUTH_CODE_SUCCESS="Authentication Success";
    public static final String PASSWORD_RESET_SUCCESS="Password reset successfully.";
    public static final String ACCOUNT_DELETE_SUCCESS="User account deleted successfully.";
    public static final String EMAIL_NOT_FOUND="User with this email does not exist.";
    public static final String ACCOUNT_NOT_ACTIVATED="Account Not Activated";
    public static final String INVALID_CREDENTIALS="Username or Password is incorrect.";
    public static final String INVALID_AUTHENTICATION_CODE="Invalid authentication code.";
    public static final String EXPIRATION_AUTHENTICATION_CODE="Authentication code expired..";

    public static final String BASE_API_URL="/api/users";
    public static final String REGISTER_API_URL="/register";
    public static final String LOGIN_API_URL="/login";
    public static final String REQUEST_AUTH_CODE_API_URL="/request-auth-code";
    public static final String VERIFY_AUTH_CODE_API_URL="/verify-auth-code";
    public static final String RESET_PASSWORD_API_URL="/reset-password";
    public static final String DELETE_API_URL="/delete";




}
