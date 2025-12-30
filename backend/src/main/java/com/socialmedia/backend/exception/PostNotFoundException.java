package com.socialmedia.backend.exception;

/**
 * Post bulunamadığında fırlatılan exception.
 * Örnek: Belirli bir ID'ye sahip post yoksa.
 */
public class PostNotFoundException extends RuntimeException {
    
    public PostNotFoundException(String message) {
        super(message);
    }
    
    public PostNotFoundException(Long postId) {
        super("Post not found with ID: " + postId);
    }
}