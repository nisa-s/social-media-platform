package com.socialmedia.backend.exception;

/**
 * Kullanıcı takip edilmiyor olduğunda fırlatılan exception.
 * Örnek: Takip etmediğiniz birini unfollow etmeye çalıştığınızda.
 */
public class NotFollowingException extends RuntimeException {
    
    public NotFollowingException(String username) {
        super("You are not following: " + username);
    }
    
    public NotFollowingException(Long followerId, Long followingId) {
        super("User " + followerId + " is not following user " + followingId);
    }
}