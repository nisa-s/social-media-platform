package com.socialmedia.backend.dto;



import java.time.LocalDateTime;

/**
 * Takip ilişkisi bilgilerini taşımak için kullanılan DTO sınıfı.
 */
public class FollowDTO {
    private Long id;
    private Long followerId; // Takip eden kullanıcının ID'si
    private String followerUsername; // Takip eden kullanıcının adı
    private Long followingId; // Takip edilen kullanıcının ID'si
    private String followingUsername; // Takip edilen kullanıcının adı
    private LocalDateTime followedAt;

    // Boş constructor
    public FollowDTO() {
    }

    // Tam constructor
    public FollowDTO(Long id, Long followerId, String followerUsername, 
                     Long followingId, String followingUsername, 
                     LocalDateTime followedAt) {
        this.id = id;
        this.followerId = followerId;
        this.followerUsername = followerUsername;
        this.followingId = followingId;
        this.followingUsername = followingUsername;
        this.followedAt = followedAt;
    }

    // Basit constructor (takip etme işlemi için)
    public FollowDTO(Long followerId, Long followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
        this.followedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    public String getFollowerUsername() {
        return followerUsername;
    }

    public void setFollowerUsername(String followerUsername) {
        this.followerUsername = followerUsername;
    }

    public Long getFollowingId() {
        return followingId;
    }

    public void setFollowingId(Long followingId) {
        this.followingId = followingId;
    }

    public String getFollowingUsername() {
        return followingUsername;
    }

    public void setFollowingUsername(String followingUsername) {
        this.followingUsername = followingUsername;
    }

    public LocalDateTime getFollowedAt() {
        return followedAt;
    }

    public void setFollowedAt(LocalDateTime followedAt) {
        this.followedAt = followedAt;
    }

    @Override
    public String toString() {
        return "FollowDTO{" +
                "id=" + id +
                ", followerUsername='" + followerUsername + '\'' +
                ", followingUsername='" + followingUsername + '\'' +
                ", followedAt=" + followedAt +
                '}';
    }
}