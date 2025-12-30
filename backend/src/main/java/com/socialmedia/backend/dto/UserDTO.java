package com.socialmedia.backend.dto;


import java.time.LocalDateTime;

/**
 * Kullanıcı bilgilerini taşımak için kullanılan DTO sınıfı.
 * Şifre gibi hassas bilgileri içermez.
 */
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String bio;
    private int followerCount;
    private int followingCount;
    private int postCount;
    private LocalDateTime createdAt;

    // Boş constructor
    public UserDTO() {
    }

    // Tüm alanlar için constructor
    public UserDTO(Long id, String username, String email, String bio, 
                   int followerCount, int followingCount, int postCount, 
                   LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
        this.postCount = postCount;
        this.createdAt = createdAt;
    }

    // Basit constructor (profil görüntüleme için)
    public UserDTO(Long id, String username, String bio) {
        this.id = id;
        this.username = username;
        this.bio = bio;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", followerCount=" + followerCount +
                ", followingCount=" + followingCount +
                ", postCount=" + postCount +
                '}';
    }
}
