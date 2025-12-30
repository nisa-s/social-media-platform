package com.socialmedia.backend.exception;

/**
 * Post zaten beğenilmiş olduğunda fırlatılan exception.
 * Örnek: Aynı post'u tekrar beğenmeye çalıştığınızda.
 */
public class AlreadyLikedException extends RuntimeException {
    
    public AlreadyLikedException(Long postId) {
        super("You have already liked post with ID: " + postId);
    }
    
    public AlreadyLikedException(Long userId, Long postId) {
        super("User " + userId + " has already liked post " + postId);
    }
    
    public AlreadyLikedException(String message) {
        super(message);
    }
}