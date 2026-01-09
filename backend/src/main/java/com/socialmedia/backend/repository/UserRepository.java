package com.socialmedia.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.socialmedia.backend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    // Username ile kullanıcı bul
    Optional<User> findByUsername(String username);
    
    // Email ile kullanıcı bul
    Optional<User> findByEmail(String email);
    
    // Username veya email varlık kontrolü
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}