package com.socialmedia.backend.dto;


import java.time.LocalDateTime;
import java.util.List;

/**
 * Post bilgilerini taşımak için kullanılan DTO sınıfı.
 * Yazar bilgilerini ve istatistikleri içerir.
 */
public class PostDTO {
    private Long id;
    private String content;
    private UserDTO author; // Post'u yazan kullanıcı
    private int likeCount;
    private boolean isLikedByCurrentUser; // Giriş yapan kullanıcı beğenmiş mi?
    private List<String> hashtags; // Post'taki hashtag'ler
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Boş constructor
    public PostDTO() {
    }

    // Tam constructor
    public PostDTO(Long id, String content, UserDTO author, int likeCount, 
                   boolean isLikedByCurrentUser, List<String> hashtags,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.likeCount = likeCount;
        this.isLikedByCurrentUser = isLikedByCurrentUser;
        this.hashtags = hashtags;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Basit constructor (post oluşturma için)
    public PostDTO(String content, UserDTO author) {
        this.content = content;
        this.author = author;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public boolean isLikedByCurrentUser() {
        return isLikedByCurrentUser;
    }

    public void setLikedByCurrentUser(boolean likedByCurrentUser) {
        isLikedByCurrentUser = likedByCurrentUser;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "PostDTO{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", author=" + (author != null ? author.getUsername() : "null") +
                ", likeCount=" + likeCount +
                ", hashtags=" + hashtags +
                ", createdAt=" + createdAt +
                '}';
    }
}