package com.socialmedia.backend.dto;


import java.time.LocalDateTime;

/**
 * Hashtag bilgilerini ve istatistiklerini taşımak için kullanılan DTO sınıfı.
 * Trend konuları göstermek için kullanılır.
 */
public class HashtagDTO {
    private Long id;
    private String tag; // # işareti olmadan (örn: "java", "programming")
    private int usageCount; // Kaç post'ta kullanıldı
    private LocalDateTime lastUsedAt;
    private boolean isTrending; // Trend mi?

    // Boş constructor
    public HashtagDTO() {
    }

    // Tam constructor
    public HashtagDTO(Long id, String tag, int usageCount, 
                      LocalDateTime lastUsedAt, boolean isTrending) {
        this.id = id;
        this.tag = tag;
        this.usageCount = usageCount;
        this.lastUsedAt = lastUsedAt;
        this.isTrending = isTrending;
    }

    // Basit constructor
    public HashtagDTO(String tag, int usageCount) {
        this.tag = tag;
        this.usageCount = usageCount;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }

    public LocalDateTime getLastUsedAt() {
        return lastUsedAt;
    }

    public void setLastUsedAt(LocalDateTime lastUsedAt) {
        this.lastUsedAt = lastUsedAt;
    }

    public boolean isTrending() {
        return isTrending;
    }

    public void setTrending(boolean trending) {
        isTrending = trending;
    }

    @Override
    public String toString() {
        return "HashtagDTO{" +
                "id=" + id +
                ", tag='#" + tag + '\'' +
                ", usageCount=" + usageCount +
                ", isTrending=" + isTrending +
                '}';
    }
}