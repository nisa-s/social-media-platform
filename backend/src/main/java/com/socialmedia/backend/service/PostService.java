package com.socialmedia.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.socialmedia.backend.dto.PostDTO;
import com.socialmedia.backend.dto.UserDTO;
import com.socialmedia.backend.entity.Post;
import com.socialmedia.backend.entity.User;
import com.socialmedia.backend.exception.ApiExceptions;
import com.socialmedia.backend.repository.PostRepository;
import com.socialmedia.backend.repository.UserRepository;

/**
 * Post işlemlerini yöneten Service sınıfı.
 * Post oluşturma, listeleme, silme gibi işlemleri içerir.
 */
@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Yeni post oluşturur
     * @param userId Post'u oluşturan kullanıcının ID'si
     * @param content Post içeriği
     * @return Oluşturulan post'un DTO'su
     * @throws UserNotFoundException Kullanıcı bulunamazsa
     */
    public PostDTO createPost(Integer userId, String content) {
        // Kullanıcıyı bul
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiExceptions.UserNotFound(Long.valueOf(userId)));
        
        // Yeni Post oluştur
        Post post = new Post();
        post.setUser(user);
        post.setContent(content);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        
        // Veritabanına kaydet
        Post savedPost = postRepository.save(post);
        
        // Entity'yi DTO'ya dönüştür
        return convertToDTO(savedPost, userId);
    }
    
    /**
     * Belirli bir post'u ID ile getirir
     * @param postId Post ID'si
     * @param currentUserId Giriş yapan kullanıcının ID'si (beğeni kontrolü için)
     * @return Post DTO'su
     * @throws PostNotFoundException Post bulunamazsa
     */
    public PostDTO getPostById(Integer postId, Integer currentUserId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiExceptions.PostNotFound(Long.valueOf(postId)));
        
        return convertToDTO(post, currentUserId);
    }
    
    /**
     * Belirli bir kullanıcının tüm post'larını getirir
     * @param userId Kullanıcı ID'si
     * @param currentUserId Giriş yapan kullanıcının ID'si
     * @return Post listesi
     * @throws UserNotFoundException Kullanıcı bulunamazsa
     */
    public List<PostDTO> getUserPosts(Integer userId, Integer currentUserId) {
        // Kullanıcının var olduğunu kontrol et
        if (!userRepository.existsById(userId)) {
            throw new ApiExceptions.UserNotFound(Long.valueOf(userId));
        }
        
        // Kullanıcının post'larını getir
        List<Post> posts = postRepository.findByUserUserId(userId);
        
        return posts.stream()
                .map(post -> convertToDTO(post, currentUserId))
                .collect(Collectors.toList());
    }
    
    /**
     * Takip edilen kullanıcıların post'larını getirir (Feed)
     * @param userId Giriş yapan kullanıcının ID'si
     * @return Feed post listesi
     * @throws UserNotFoundException Kullanıcı bulunamazsa
     */
    public List<PostDTO> getFeed(Integer userId) {
        // Kullanıcının var olduğunu kontrol et
        if (!userRepository.existsById(userId)) {
            throw new ApiExceptions.UserNotFound(Long.valueOf(userId));
        }
        
        //  Takip edilen kullanıcıların post'larını getir
        // Şimdilik tüm post'ları getiriyoruz
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        
        return posts.stream()
                .map(post -> convertToDTO(post, userId))
                .collect(Collectors.toList());
    }
    
    /**
     * Tüm post'ları getirir (zaman sırasına göre)
     * @param currentUserId Giriş yapan kullanıcının ID'si
     * @return Tüm post'ların listesi
     */
    public List<PostDTO> getAllPosts(Integer currentUserId) {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        
        return posts.stream()
                .map(post -> convertToDTO(post, currentUserId))
                .collect(Collectors.toList());
    }
    
    /**
     * Post içeriğini günceller
     * @param postId Güncellenecek post ID'si
     * @param userId Güncellemeyi yapan kullanıcının ID'si
     * @param newContent Yeni içerik
     * @return Güncellenmiş post DTO'su
     * @throws PostNotFoundException Post bulunamazsa
     * @throws UnauthorizedException Kullanıcı post sahibi değilse
     */
    public PostDTO updatePost(Integer postId, Integer userId, String newContent) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiExceptions.PostNotFound(Long.valueOf(postId)));
        
        // Yetki kontrolü: Sadece post sahibi güncelleyebilir
        if (!post.getUser().getUserId().equals(userId)) {
            throw new ApiExceptions.Unauthorized();
        }
        
        post.setContent(newContent);
        post.setUpdatedAt(LocalDateTime.now());
        
        Post updatedPost = postRepository.save(post);
        return convertToDTO(updatedPost, userId);
    }
    
    /**
     * Post'u siler
     * @param postId Silinecek post ID'si
     * @param userId Silme işlemini yapan kullanıcının ID'si
     * @throws PostNotFoundException Post bulunamazsa
     * @throws UnauthorizedException Kullanıcı post sahibi değilse
     */
    public void deletePost(Integer postId, Integer userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiExceptions.PostNotFound(Long.valueOf(postId)));
        
        // Yetki kontrolü: Sadece post sahibi silebilir
        if (!post.getUser().getUserId().equals(userId)) {
            throw new ApiExceptions.Unauthorized();
        }
        
        postRepository.delete(post);
    }
    
    /**
     * Belirli bir hashtag'e sahip post'ları getirir
     * @param hashtag Aranacak hashtag (# olmadan)
     * @param currentUserId Giriş yapan kullanıcının ID'si
     * @return Post listesi
     */
    public List<PostDTO> getPostsByHashtag(String hashtag, Integer currentUserId) {
        // Hashtag içeren post'ları bul
        List<Post> posts = postRepository.findAll().stream()
                .filter(post -> post.getContent().contains("#" + hashtag))
                .collect(Collectors.toList());
        
        return posts.stream()
                .map(post -> convertToDTO(post, currentUserId))
                .collect(Collectors.toList());
    }
    
    // ========== HELPER METODLAR ==========
    
    /**
     * Post Entity'yi PostDTO'ya dönüştürür
     * @param post Post entity
     * @param currentUserId Giriş yapan kullanıcının ID'si (beğeni kontrolü için)
     * @return PostDTO
     */
    private PostDTO convertToDTO(Post post, Integer currentUserId) {
        PostDTO dto = new PostDTO();
        dto.setId(Long.valueOf(post.getPostId()));
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        
        // Yazar bilgisi
        UserDTO authorDTO = new UserDTO();
        authorDTO.setId(Long.valueOf(post.getUser().getUserId()));
        authorDTO.setUsername(post.getUser().getUsername());
        dto.setAuthor(authorDTO);
        
        // Beğeni sayısı
        dto.setLikeCount(post.getLikes() != null ? post.getLikes().size() : 0);
        
        // Kullanıcı bu post'u beğenmiş mi?
        if (currentUserId != null && post.getLikes() != null) {
            boolean isLiked = post.getLikes().stream()
                    .anyMatch(like -> like.getUser().getUserId().equals(currentUserId));
            dto.setLikedByCurrentUser(isLiked);
        } else {
            dto.setLikedByCurrentUser(false);
        }
        
        // Hashtag'leri çıkar
        dto.setHashtags(extractHashtags(post.getContent()));
        
        return dto;
    }
    
    /**
     * Post içeriğinden hashtag'leri çıkarır
     * @param content Post içeriği
     * @return Hashtag listesi
     */
    private List<String> extractHashtags(String content) {
        if (content == null) return List.of();
        
        return List.of(content.split("\\s+")).stream()
                .filter(word -> word.startsWith("#") && word.length() > 1)
                .map(word -> word.substring(1)) // # işaretini çıkar
                .collect(Collectors.toList());
    }
}