package com.cavosh.cafe.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private String error;
    private String message;
    private int status;
    private String timestamp;
    private T data;

    public static <T> ApiResponse<T> ok(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .status(200)
                .timestamp(LocalDateTime.now().toString())
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> created(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .status(201)
                .timestamp(LocalDateTime.now().toString())
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(String error, String message, int status) {
        return ApiResponse.<T>builder()
                .success(false)
                .error(error)
                .message(message)
                .status(status)
                .timestamp(LocalDateTime.now().toString())
                .data(null)
                .build();
    }
}