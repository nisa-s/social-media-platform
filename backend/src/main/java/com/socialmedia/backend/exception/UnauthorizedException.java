package com.socialmedia.backend.exception;

/**
 * Kullanıcının bir işlemi yapmaya yetkisi olmadığında fırlatılan exception.
 * Örnek: Başkasının post'unu silmeye çalıştığında.
 */
public class UnauthorizedException extends RuntimeException {
    
    public UnauthorizedException() {
        super("You are not authorized to perform this action");
    }
    
    public UnauthorizedException(String message) {
        super(message);
    }
    
    public UnauthorizedException(String action, Long userId) {
        super("User " + userId + " is not authorized to " + action);
    }
}