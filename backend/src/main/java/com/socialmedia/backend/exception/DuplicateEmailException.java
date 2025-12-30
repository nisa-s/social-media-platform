package com.socialmedia.backend.exception;

/**
 * Email adresi zaten sistemde kayıtlı olduğunda fırlatılan exception.
 * Örnek: Kayıt sırasında aynı email varsa.
 */
public class DuplicateEmailException extends RuntimeException {
    
    public DuplicateEmailException(String email) {
        super("Email already exists: " + email);
    }
    
    public DuplicateEmailException(String message, String email) {
        super(message + ": " + email);
    }
}