package com.example.Admin.apiresponse;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse {
    private String message;
    private boolean success;
    private LocalDateTime timestamp;

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
}
