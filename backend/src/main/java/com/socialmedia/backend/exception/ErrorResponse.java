package com.socialmedia.backend.exception;

import java.time.LocalDateTime;

/**
 * ErrorResponse sınıfı
 * Tüm hata response'larında kullanılacak standart format
 * 
 * JSON formatında şu şekilde dönecek:
 * {
 *   "timestamp": "2025-01-15T10:30:00",
 *   "status": 404,
 *   "error": "Not Found",
 *   "message": "User with id 123 not found",
 *   "path": "/api/users/123"
 * }
 */
public class ErrorResponse {
    
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    // Constructor
    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    // Default constructor (Jackson için gerekli)
    public ErrorResponse() {
    }

    // Getters and Setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    // toString metodu (debugging için)
    @Override
    public String toString() {
        return "ErrorResponse{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}