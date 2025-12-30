package com.socialmedia.backend.service;

import com.socialmedia.backend.dto.LikeDTO;
import com.socialmedia.backend.entity.Like;
import com.socialmedia.backend.entity.Post;
import com.socialmedia.backend.entity.User;
import com.socialmedia.backend.repository.LikeRepository;
import com.socialmedia.backend.repository.PostRepository;
import com.socialmedia.backend.repository.UserRepository;
import com.socialmedia.backend.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Beğeni işlemlerini yöneten Service sınıfı.
 * Post beğenme, beğenmekten vazgeçme ve beğeni listesi işlemleri.
 */
@Service
public class LikeService {
    
    @Autowired
    private LikeRepository likeRepository;
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Post'a beğeni ekler
     * @param userId Beğenen kullanıcının ID'si
     * @param postId Beğenilen post'un ID'si
     * @return Oluşturulan beğeni DTO'su
     * @throws UserNotFoundException Kullanıcı bulunamazsa
     * @throws PostNotFoundException Post bulunamazsa
     * @throws AlreadyLikedException Post zaten beğenilmişse
     */
    public LikeDTO likePost(Integer userId, Integer postId) {
        // Kullanıcıyı kontrol et
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(Long.valueOf(userId)));
        
        // Post'u kontrol et
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(Long.valueOf(postId)));
        
        // Zaten beğenilmiş mi kontrol et
        Optional<Like> existingLike = likeRepository.findByUserUserIdAndPostPostId(userId, postId);
        if (existingLike.isPresent()) {
            throw new AlreadyLikedException(Long.valueOf(postId));
        }
        
        // Yeni beğeni oluştur
        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        like.setCreatedAt(LocalDateTime.now());
        
        // Veritabanına kaydet
        Like savedLike = likeRepository.save(like);
        
        return convertToDTO(savedLike);
    }
    
    /**
     * Post'tan beğeniyi kaldırır (unlike)
     * @param userId Kullanıcının ID'si
     * @param postId Post'un ID'si
     * @throws UserNotFoundException Kullanıcı bulunamazsa
     * @throws PostNotFoundException Post bulunamazsa
     */
    public void unlikePost(Integer userId, Integer postId) {
        // Kullanıcı ve post kontrolü
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(Long.valueOf(userId));
        }
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException(Long.valueOf(postId));
        }
        
        // Beğeniyi bul
        Like like = likeRepository.findByUserUserIdAndPostPostId(userId, postId)
                .orElse(null);
        
        // Beğeni yoksa sessizce dön (veya exception fırlat)
        if (like == null) {
            // İsterseniz exception fırlatabilirsiniz
            // throw new NotLikedException(Long.valueOf(postId));
            return;
        }
        
        // Beğeniyi sil
        likeRepository.delete(like);
    }
    
    /**
     * Kullanıcının post'u beğenip beğenmediğini kontrol eder
     * @param userId Kullanıcının ID'si
     * @param postId Post'un ID'si
     * @return Beğenilmişse true, değilse false
     */
    public boolean isPostLikedByUser(Integer userId, Integer postId) {
        return likeRepository.existsByUserUserIdAndPostPostId(userId, postId);
    }
    
    /**
     * Bir post'un toplam beğeni sayısını getirir
     * @param postId Post'un ID'si
     * @return Beğeni sayısı
     * @throws PostNotFoundException Post bulunamazsa
     */
    public long getPostLikeCount(Integer postId) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException(Long.valueOf(postId));
        }
        
        return likeRepository.countByPostPostId(postId);
    }
    
    /**
     * Bir post'u beğenen kullanıcıların listesini getirir
     * @param postId Post'un ID'si
     * @return Beğenen kullanıcıların DTO listesi
     * @throws PostNotFoundException Post bulunamazsa
     */
    public List<LikeDTO> getPostLikes(Integer postId) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException(Long.valueOf(postId));
        }
        
        List<Like> likes = likeRepository.findByPostPostId(postId);
        
        return likes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Bir kullanıcının beğendiği tüm post'ları getirir
     * @param userId Kullanıcının ID'si
     * @return Beğenilen post'ların beğeni DTO listesi
     * @throws UserNotFoundException Kullanıcı bulunamazsa
     */
    public List<LikeDTO> getUserLikes(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(Long.valueOf(userId));
        }
        
        List<Like> likes = likeRepository.findByUserUserId(userId);
        
        return likes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Like toggle - Beğenilmişse kaldır, beğenilmemişse ekle
     * @param userId Kullanıcının ID'si
     * @param postId Post'un ID'si
     * @return true = beğeni eklendi, false = beğeni kaldırıldı
     */
    public boolean toggleLike(Integer userId, Integer postId) {
        if (isPostLikedByUser(userId, postId)) {
            unlikePost(userId, postId);
            return false; // Beğeni kaldırıldı
        } else {
            likePost(userId, postId);
            return true; // Beğeni eklendi
        }
    }
    
    // ========== HELPER METODLAR ==========
    
    /**
     * Like Entity'yi LikeDTO'ya dönüştürür
     */
    private LikeDTO convertToDTO(Like like) {
        LikeDTO dto = new LikeDTO();
        dto.setId(Long.valueOf(like.getLikeId()));
        dto.setUserId(Long.valueOf(like.getUser().getUserId()));
        dto.setUsername(like.getUser().getUsername());
        dto.setPostId(Long.valueOf(like.getPost().getPostId()));
        dto.setLikedAt(like.getCreatedAt());
        
        return dto;
    }
}