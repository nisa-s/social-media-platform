package com.socialmedia.backend.exception;

/**
 * Kullanıcı adı zaten sistemde kayıtlı olduğunda fırlatılan exception.
 * Örnek: Kayıt sırasında aynı username varsa.
 */
public class DuplicateUsernameException extends RuntimeException {
    
    public DuplicateUsernameException(String username) {
        super("Username already exists: " + username);
    }
    
    public DuplicateUsernameException(String message, String username) {
        super(message + ": " + username);
    }
}