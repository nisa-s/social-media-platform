package com.socialmedia.backend.repository;

import com.socialmedia.backend.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {
    
    // Takip ilişkisi var mı?
    Optional<Follow> findByFollowerUserIdAndFollowingUserId(Integer followerId, Integer followingId);
    
    // Takip ediyor mu kontrolü
    boolean existsByFollowerUserIdAndFollowingUserId(Integer followerId, Integer followingId);
    
    // Takipçi sayısı
    long countByFollowingUserId(Integer userId);
    
    // Takip edilen sayısı
    long countByFollowerUserId(Integer userId);

      // Bir kullanıcının takipçileri (onu takip edenler)
    List<Follow> findByFollowingUserId(Integer userId);
    
    // Bir kullanıcının takip ettikleri
    List<Follow> findByFollowerUserId(Integer userId);
}