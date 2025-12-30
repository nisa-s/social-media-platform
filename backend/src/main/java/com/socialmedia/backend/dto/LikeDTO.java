package com.socialmedia.backend.dto;


import java.time.LocalDateTime;

/**
 * Beğeni bilgilerini taşımak için kullanılan DTO sınıfı.
 */
public class LikeDTO {
    private Long id;
    private Long userId;
    private String username; // Beğenen kullanıcının adı
    private Long postId;
    private LocalDateTime likedAt;

    // Boş constructor
    public LikeDTO() {
    }

    // Tam constructor
    public LikeDTO(Long id, Long userId, String username, Long postId, LocalDateTime likedAt) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.postId = postId;
        this.likedAt = likedAt;
    }

    // Basit constructor (beğeni oluşturma için)
    public LikeDTO(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
        this.likedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public LocalDateTime getLikedAt() {
        return likedAt;
    }

    public void setLikedAt(LocalDateTime likedAt) {
        this.likedAt = likedAt;
    }

    @Override
    public String toString() {
        return "LikeDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", postId=" + postId +
                ", likedAt=" + likedAt +
                '}';
    }
}