package com.beard.walletservice.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    public static <T> ApiResponse<T> ok(String msg, T data){ return ApiResponse.<T>builder().success(true).message(msg).data(data).build(); }
    public static <T> ApiResponse<T> fail(String msg){ return ApiResponse.<T>builder().success(false).message(msg).data(null).build(); }
}

