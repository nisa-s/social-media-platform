package com.socialmedia.backend.exception;

/**
 * Kullanıcı giriş bilgileri hatalı olduğunda fırlatılan exception.
 * Örnek: Yanlış şifre veya kullanıcı adı.
 */
public class InvalidCredentialsException extends RuntimeException {
    
    public InvalidCredentialsException() {
        super("Invalid username or password");
    }
    
    public InvalidCredentialsException(String message) {
        super(message);
    }
}