package com.socialmedia.backend.exception;

/**
 * Kullanıcı bulunamadığında fırlatılan exception.
 * Örnek: ID veya username ile arama yapıldığında kullanıcı yoksa.
 */
public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(String message) {
        super(message);
    }
    
    public UserNotFoundException(Long userId) {
        super("User not found with ID: " + userId);
    }
    
    public UserNotFoundException(String field, String value) {
        super("User not found with " + field + ": " + value);
    }
}