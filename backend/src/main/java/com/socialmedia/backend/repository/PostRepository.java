package com.socialmedia.backend.repository;

import com.socialmedia.backend.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    
    // Kullanıcının tüm postları
    List<Post> findByUserUserId(Integer userId);
    
    // Tarih sırasına göre tüm postlar
    List<Post> findAllByOrderByCreatedAtDesc();
    
    // Bir kullanıcının post sayısı
    long countByUserUserId(Integer userId);
}