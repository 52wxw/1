package com.example.dto.response;

public record ApiResponse<T>(
    int code,
    String message,
    T data
) {
    // 成功响应构造器（带数据）
    public ApiResponse(boolean success, String message, T data) {
        this(success ? 200 : 500, message, data);
    }
    
    // 成功响应构造器（不带数据）
    public ApiResponse(boolean success, String message) {
        this(success ? 200 : 500, message, null);
    }
}
