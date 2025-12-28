package com.socialmedia.backend.repository;

import com.socialmedia.backend.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    
    // Kullanıcı bu postu beğenmiş mi?
    Optional<Like> findByUserUserIdAndPostPostId(Integer userId, Integer postId);
    
    // Bir postun toplam beğeni sayısı
    long countByPostPostId(Integer postId);
    
    // Kullanıcının verdiği toplam beğeni sayısı
    long countByUserUserId(Integer userId);
    
    // Beğeni var mı kontrolü
    boolean existsByUserUserIdAndPostPostId(Integer userId, Integer postId);
}