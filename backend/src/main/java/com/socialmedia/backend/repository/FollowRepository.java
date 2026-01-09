package com.socialmedia.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.socialmedia.backend.entity.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {
    
    // Takip ilişkisi var mı?
    Optional<Follow> findByFollower_UserIdAndFollowing_UserId(Integer followerId, Integer followingId);
    
    // Takip ediyor mu kontrolü
    boolean existsByFollower_UserIdAndFollowing_UserId(Integer followerId, Integer followingId);
    
    // Takipçi sayısı
    long countByFollowing_UserId(Integer userId);
    
    // Takip edilen sayısı
    long countByFollower_UserId(Integer userId);

      // Bir kullanıcının takipçileri (onu takip edenler)
    List<Follow> findByFollowing_UserId(Integer userId);
    
    // Bir kullanıcının takip ettikleri
    List<Follow> findByFollower_UserId(Integer userId);
}