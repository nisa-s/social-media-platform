package com.socialmedia.backend.exception;

/**
 * Kullanıcı zaten takip ediliyor olduğunda fırlatılan exception.
 * Örnek: Aynı kullanıcıyı tekrar takip etmeye çalışıldığında.
 */
public class AlreadyFollowingException extends RuntimeException {
    
    public AlreadyFollowingException(String username) {
        super("You are already following: " + username);
    }
    
    public AlreadyFollowingException(Long followerId, Long followingId) {
        super("User " + followerId + " is already following user " + followingId);
    }
}