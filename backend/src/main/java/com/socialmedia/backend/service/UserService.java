package com.socialmedia.backend.service;

import com.socialmedia.backend.dto.UserDTO;
import com.socialmedia.backend.entity.User;
import com.socialmedia.backend.exception.*;
import com.socialmedia.backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Kullanıcı işlemlerini yöneten Service sınıfı.
 * Kayıt, giriş, profil yönetimi gibi işlemleri içerir.
 */
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Yeni kullanıcı kaydı oluşturur
     * @param username Kullanıcı adı
     * @param email Email adresi
     * @param password Şifre (plain text - hash'lenecek)
     * @return Oluşturulan kullanıcının DTO'su
     * @throws DuplicateUsernameException Username zaten varsa
     * @throws DuplicateEmailException Email zaten varsa
     */
    public UserDTO register(String username, String email, String password) {
        // Validation: Username kontrolü
        if (userRepository.existsByUsername(username)) {
            throw new DuplicateUsernameException(username);
        }
        
        // Validation: Email kontrolü
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(email);
        }
        
        // Yeni User entity oluştur
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(hashPassword(password)); // Şifreyi hash'le
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        // Veritabanına kaydet
        User savedUser = userRepository.save(user);
        
        // Entity'yi DTO'ya dönüştür ve döndür
        return convertToDTO(savedUser);
    }
    
    /**
     * Kullanıcı girişi yapar
     * @param username Kullanıcı adı veya email
     * @param password Şifre
     * @return Giriş yapan kullanıcının DTO'su
     * @throws InvalidCredentialsException Bilgiler hatalıysa
     */
    public UserDTO login(String username, String password) {
        // Username veya email ile kullanıcıyı bul
        User user = userRepository.findByUsername(username)
                .orElseGet(() -> userRepository.findByEmail(username)
                        .orElseThrow(() -> new InvalidCredentialsException()));
        
        // Şifre kontrolü
        if (!verifyPassword(password, user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }
        
        return convertToDTO(user);
    }
    
    /**
     * Kullanıcı ID'sine göre profil getirir
     * @param userId Kullanıcı ID'si
     * @return Kullanıcı profil DTO'su
     * @throws UserNotFoundException Kullanıcı bulunamazsa
     */
    public UserDTO getUserProfile(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(Long.valueOf(userId)));
        
        return convertToDTO(user);
    }
    
    /**
     * Username'e göre kullanıcı getirir
     * @param username Kullanıcı adı
     * @return Kullanıcı DTO'su
     * @throws UserNotFoundException Kullanıcı bulunamazsa
     */
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("username", username));
        
        return convertToDTO(user);
    }
    
    /**
     * Kullanıcı profilini günceller
     * @param userId Güncellenecek kullanıcının ID'si
     * @param email Yeni email (opsiyonel)
     * @param bio Yeni bio (opsiyonel)
     * @return Güncellenmiş kullanıcı DTO'su
     * @throws UserNotFoundException Kullanıcı bulunamazsa
     * @throws DuplicateEmailException Yeni email zaten kullanılıyorsa
     */
    public UserDTO updateProfile(Integer userId, String email, String bio) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(Long.valueOf(userId)));
        
        // Email güncellemesi
        if (email != null && !email.equals(user.getEmail())) {
            if (userRepository.existsByEmail(email)) {
                throw new DuplicateEmailException(email);
            }
            user.setEmail(email);
        }
        
        // Bio güncellemesi (User entity'sinde bio field'ı yoksa eklenmelidir)
        // user.setBio(bio);
        
        user.setUpdatedAt(LocalDateTime.now());
        User updatedUser = userRepository.save(user);
        
        return convertToDTO(updatedUser);
    }
    
    /**
     * Kullanıcı adına göre arama yapar
     * @param searchTerm Arama terimi
     * @return Eşleşen kullanıcıların listesi
     */
    public List<UserDTO> searchUsers(String searchTerm) {
        List<User> users = userRepository.findAll()
                .stream()
                .filter(user -> user.getUsername().toLowerCase()
                        .contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
        
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Tüm kullanıcıları getirir
     * @return Tüm kullanıcıların listesi
     */
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // ========== HELPER METODLAR ==========
    
    /**
     * User Entity'yi UserDTO'ya dönüştürür
     */
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(Long.valueOf(user.getUserId()));
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        // dto.setBio(user.getBio()); // Eğer User entity'sinde bio varsa
        dto.setCreatedAt(user.getCreatedAt());
        
        // İstatistikler (şimdilik 0, sonra hesaplanacak)
        dto.setFollowerCount(0);
        dto.setFollowingCount(0);
        dto.setPostCount(user.getPosts() != null ? user.getPosts().size() : 0);
        
        return dto;
    }
    
    /**
     * Şifreyi hash'ler (basit versiyon - gerçek projede BCrypt kullanın)
     *  Spring Security BCryptPasswordEncoder kullan
     */
    private String hashPassword(String password) {
        // UYARI: Bu basit bir implementasyon!
        // Gerçek projede Spring Security'nin BCryptPasswordEncoder'ını kullanın
        return "HASHED_" + password; // Placeholder
    }
    
    /**
     * Şifre doğrulama (basit versiyon)
     * Spring Security BCryptPasswordEncoder kullan
     */
    private boolean verifyPassword(String plainPassword, String hashedPassword) {
        // UYARI: Bu basit bir implementasyon!
        return hashedPassword.equals("HASHED_" + plainPassword); // Placeholder
    }
}