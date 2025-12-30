package com.socialmedia.backend.service;


import com.socialmedia.backend.dto.FollowDTO;
import com.socialmedia.backend.dto.UserDTO;
import com.socialmedia.backend.entity.Follow;
import com.socialmedia.backend.entity.User;
import com.socialmedia.backend.repository.FollowRepository;
import com.socialmedia.backend.repository.UserRepository;
import com.socialmedia.backend.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Takip işlemlerini yöneten Service sınıfı.
 * Takip etme, takipten çıkma, takipçi ve takip edilen listesi işlemleri.
 */
@Service
public class FollowService {
    
    @Autowired
    private FollowRepository followRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Bir kullanıcıyı takip eder
     * @param followerId Takip eden kullanıcının ID'si
     * @param followingId Takip edilen kullanıcının ID'si
     * @return Oluşturulan takip ilişkisinin DTO'su
     * @throws UserNotFoundException Kullanıcılardan biri bulunamazsa
     * @throws AlreadyFollowingException Zaten takip ediliyorsa
     */
    public FollowDTO followUser(Integer followerId, Integer followingId) {
        // Kendini takip etmeye çalışıyor mu?
        if (followerId.equals(followingId)) {
            throw new IllegalArgumentException("You cannot follow yourself");
        }
        
        // Kullanıcıları kontrol et
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new UserNotFoundException(Long.valueOf(followerId)));
        
        User following = userRepository.findById(followingId)
                .orElseThrow(() -> new UserNotFoundException(Long.valueOf(followingId)));
        
        // Zaten takip ediliyor mu kontrol et
        Optional<Follow> existingFollow = followRepository
                .findByFollowerUserIdAndFollowingUserId(followerId, followingId);
        
        if (existingFollow.isPresent()) {
            throw new AlreadyFollowingException(following.getUsername());
        }
        
        // Yeni takip ilişkisi oluştur
        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);
        follow.setCreatedAt(LocalDateTime.now());
        
        // Veritabanına kaydet
        Follow savedFollow = followRepository.save(follow);
        
        return convertToDTO(savedFollow);
    }
    
    /**
     * Takipten çıkar
     * @param followerId Takip eden kullanıcının ID'si
     * @param followingId Takip edilen kullanıcının ID'si
     * @throws UserNotFoundException Kullanıcılardan biri bulunamazsa
     * @throws NotFollowingException Zaten takip edilmiyorsa
     */
    public void unfollowUser(Integer followerId, Integer followingId) {
        // Kullanıcıları kontrol et
        if (!userRepository.existsById(followerId)) {
            throw new UserNotFoundException(Long.valueOf(followerId));
        }
        if (!userRepository.existsById(followingId)) {
            throw new UserNotFoundException(Long.valueOf(followingId));
        }
        
        // Takip ilişkisini bul
        Follow follow = followRepository
                .findByFollowerUserIdAndFollowingUserId(followerId, followingId)
                .orElseThrow(() -> new NotFollowingException(Long.valueOf(followerId), Long.valueOf(followingId)));
        
        // Takip ilişkisini sil
        followRepository.delete(follow);
    }
    
    /**
     * Bir kullanıcının takipçilerini getirir
     * @param userId Kullanıcının ID'si
     * @return Takipçilerin DTO listesi
     * @throws UserNotFoundException Kullanıcı bulunamazsa
     */
    public List<UserDTO> getFollowers(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(Long.valueOf(userId));
        }
        
        List<Follow> follows = followRepository.findByFollowingUserId(userId);
        
        return follows.stream()
                .map(follow -> convertUserToDTO(follow.getFollower()))
                .collect(Collectors.toList());
    }
    
    /**
     * Bir kullanıcının takip ettiklerini getirir
     * @param userId Kullanıcının ID'si
     * @return Takip edilenlerin DTO listesi
     * @throws UserNotFoundException Kullanıcı bulunamazsa
     */
    public List<UserDTO> getFollowing(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(Long.valueOf(userId));
        }
        
        List<Follow> follows = followRepository.findByFollowerUserId(userId);
        
        return follows.stream()
                .map(follow -> convertUserToDTO(follow.getFollowing()))
                .collect(Collectors.toList());
    }
    
    /**
     * Bir kullanıcının takipçi sayısını getirir
     * @param userId Kullanıcının ID'si
     * @return Takipçi sayısı
     * @throws UserNotFoundException Kullanıcı bulunamazsa
     */
    public long getFollowerCount(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(Long.valueOf(userId));
        }
        
        return followRepository.countByFollowingUserId(userId);
    }
    
    /**
     * Bir kullanıcının takip ettiği kişi sayısını getirir
     * @param userId Kullanıcının ID'si
     * @return Takip edilen sayısı
     * @throws UserNotFoundException Kullanıcı bulunamazsa
     */
    public long getFollowingCount(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(Long.valueOf(userId));
        }
        
        return followRepository.countByFollowerUserId(userId);
    }
    
    /**
     * Bir kullanıcının başka bir kullanıcıyı takip edip etmediğini kontrol eder
     * @param followerId Takip eden kullanıcının ID'si
     * @param followingId Takip edilen kullanıcının ID'si
     * @return Takip ediyorsa true, değilse false
     */
    public boolean isFollowing(Integer followerId, Integer followingId) {
        return followRepository.existsByFollowerUserIdAndFollowingUserId(followerId, followingId);
    }
    
    /**
     * Takip toggle - Takip ediyorsa bırak, etmiyorsa takip et
     * @param followerId Takip eden kullanıcının ID'si
     * @param followingId Takip edilen kullanıcının ID'si
     * @return true = takip edildi, false = takipten çıkıldı
     */
    public boolean toggleFollow(Integer followerId, Integer followingId) {
        if (isFollowing(followerId, followingId)) {
            unfollowUser(followerId, followingId);
            return false; // Takipten çıkıldı
        } else {
            followUser(followerId, followingId);
            return true; // Takip edildi
        }
    }
    
    /**
     * İki kullanıcı birbirini takip ediyor mu? (Karşılıklı takip)
     * @param userId1 Birinci kullanıcının ID'si
     * @param userId2 İkinci kullanıcının ID'si
     * @return Her ikisi de birbirini takip ediyorsa true
     */
    public boolean areMutualFollowers(Integer userId1, Integer userId2) {
        return isFollowing(userId1, userId2) && isFollowing(userId2, userId1);
    }
    
    // ========== HELPER METODLAR ==========
    
    /**
     * Follow Entity'yi FollowDTO'ya dönüştürür
     */
    private FollowDTO convertToDTO(Follow follow) {
        FollowDTO dto = new FollowDTO();
        dto.setId(Long.valueOf(follow.getFollowId()));
        dto.setFollowerId(Long.valueOf(follow.getFollower().getUserId()));
        dto.setFollowerUsername(follow.getFollower().getUsername());
        dto.setFollowingId(Long.valueOf(follow.getFollowing().getUserId()));
        dto.setFollowingUsername(follow.getFollowing().getUsername());
        dto.setFollowedAt(follow.getCreatedAt());
        
        return dto;
    }
    
    /**
     * User Entity'yi basit UserDTO'ya dönüştürür
     */
    private UserDTO convertUserToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(Long.valueOf(user.getUserId()));
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        // dto.setBio(user.getBio()); // Eğer User entity'sinde bio varsa
        dto.setCreatedAt(user.getCreatedAt());
        
        return dto;
    }
}