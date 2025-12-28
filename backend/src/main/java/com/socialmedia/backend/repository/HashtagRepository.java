package com.socialmedia.backend.repository;

import com.socialmedia.backend.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Integer> {
    
    // Tag adına göre hashtag bul
    Optional<Hashtag> findByTagName(String tagName);
    
    // Hashtag var mı kontrolü
    boolean existsByTagName(String tagName);
}