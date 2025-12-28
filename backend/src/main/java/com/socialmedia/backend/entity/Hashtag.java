package com.socialmedia.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Hashtags")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hashtag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Integer hashtagId;
    
    @Column(name = "tag_name", unique = true, nullable = false, length = 50)
    private String tagName;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @ManyToMany(mappedBy = "hashtags")
    private List<Post> posts;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        // Trigger benzeri: küçük harfe çevir
        if (tagName != null) {
            tagName = tagName.toLowerCase().trim();
        }
    }
}