OOP PRENSİPLERİ VE MİMARİ RAPORU

Sosyal Medya Mikroblog Platformu - Business Logic Katmanı
Proje: Sosyal Medya Platformu (Proje 5)
Geliştirici: Ayşe Nur Yılmaz - Business Logic Katmanı
Tarih: Aralık 2025
Teknolojiler: Java, Spring Boot, JPA

 İÇİNDEKİLER
1.	Proje Genel Bakış
2.	Kullanılan Tasarım Desenleri
3.	OOP Prensiplerinin Uygulanması
4.	Katmanlı Mimari
5.	Exception Handling Stratejisi
6.	Kod Örnekleri ve Açıklamalar
7.	Sonuç ve Değerlendirme

1. PROJE GENEL BAKIŞ
1.1 Proje Tanımı
Bu proje, kullanıcıların gönderi paylaşabildiği, beğeni yapabildiği, birbirini takip edebildiği ve hashtag'ler kullanabildiği bir mikroblog platformunun backend sistemidir.
1.2 Sorumluluk Alanı 
Business Logic katmanının geliştirilmesi:
•	DTO (Data Transfer Object) Sınıfları - 5 dosya
•	Custom Exception Sınıfları - 9 dosya
•	Service Katmanı - 4 ana servis sınıfı
1.3 Geliştirilen Modüller
•	UserService: Kullanıcı yönetimi
•	PostService: Gönderi yönetimi
•	LikeService: Beğeni işlemleri
•	FollowService: Takip işlemleri

2. KULLANILAN TASARIM DESENLERİ

2.1 Service Layer Pattern

Tanım: İş mantığını (business logic) Entity ve Controller katmanlarından ayıran bir tasarım desenidir.
Uygulama:
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public UserDTO register(String username, String email, String password) {
        // İş mantığı burada
        // Validation, business rules, entity manipulation
    }
}

Avantajları:

İş mantığı tek bir yerde toplanır
Controller'lar sadece HTTP isteklerini yönetir
Test edilebilirlik artar
Kod tekrarı azalır

2.2 Data Transfer Object (DTO) Pattern

Tanım: Katmanlar arası veri taşımak için kullanılan basit Java nesneleridir.
Neden Kullandık?

1.	Güvenlik: Entity'lerdeki hassas bilgiler (şifre hash'i) dışarı çıkmaz
// Entity - Tüm veriler
public class User {
    private String passwordHash; // Hassas veri
    private String email;
}

// DTO - Sadece gerekli veriler
public class UserDTO {
    // passwordHash yok! 
    private String email;
}
2.	Esneklik: API response formatını değiştirmek kolay
3.	Performans: Gereksiz veriler taşınmaz
4.	Bağımsızlık: Database değişiklikleri API'yi etkilemez

DTO Örnekleri:
UserDTO: Kullanıcı bilgileri (şifre hariç)
PostDTO: Gönderi + yazar bilgisi + istatistikler
LikeDTO: Beğeni bilgisi
FollowDTO: Takip ilişkisi bilgisi
HashtagDTO: Hashtag ve trend bilgisi

2.3 Repository Pattern (Spring Data JPA)

Tanım: Veritabanı erişim mantığını soyutlayan bir desendir.
Uygulama: Service katmanı Repository interface'lerini kullanır:
@Autowired
private UserRepository userRepository;

// Service metodu içinde
User user = userRepository.findById(userId)
    .orElseThrow(() -> new UserNotFoundException(userId));
Avantajları:
•	Database işlemleri soyutlanmış
•	SQL detayları service'ten gizli
•	Mock'lanabilir (test için)

3. OOP PRENSİPLERİNİN UYGULANMASI


3.1 Encapsulation (Kapsülleme)

Tanım: Veri ve metodları bir arada tutup, dış erişimi kontrol etmek.
Uygulama Örnekleri:
1.	Private Field'lar + Public Getter/Setter:
public class UserDTO {
    private Long id;
    private String username;
    
    // Getter
    public Long getId() { return id; }
    
    // Setter
    public void setId(Long id) { this.id = id; }
}
2.	Private Helper Metodlar:
public class UserService {
    // Public API
    public UserDTO register(...) {
        // ...
        return convertToDTO(savedUser);
    }
    
    // Private helper - dışarıdan erişilmez
    private UserDTO convertToDTO(User user) {
        // Dönüşüm mantığı
    }
}
Faydaları:
•	Veri bütünlüğü korunur
•	İç implementasyon gizlenir
•	Değişiklikler kontrollü yapılır


3.2 Abstraction (Soyutlama)

Tanım: Kompleks işlemleri basit interface'ler arkasına gizlemek.
Uygulama:
1.	Repository Abstraction:
// Kullanıcı sadece metod ismini bilir, SQL detaylarını değil
userRepository.findByUsername(username);
// Arkada SQL: SELECT * FROM users WHERE username = ?
2.	Service Method Abstraction:
// Controller sadece metodu çağırır
UserDTO user = userService.register(username, email, password);

// Service içinde karmaşık iş mantığı:
// - Validation
// - Password hashing
// - Database kayıt
// - DTO dönüşümü
Faydaları:
•	Karmaşıklık gizlenir
•	Kullanımı basitleşir
•	Değişiklik etkisi azalır


3.3 Inheritance (Kalıtım)
Uygulama:
1.	Exception Hierarchy:
// RuntimeException'dan türetme
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("User not found with ID: " + userId);
    }
}
Avantajları:
•	Ortak davranışlar paylaşılır
•	Exception handling kolaylaşır
•	Kod tekrarı azalır


3.4 Polymorphism (Çok Biçimlilik)

Uygulama:
1.	Constructor Overloading:
public class UserDTO {
    // Tam constructor
    public UserDTO(Long id, String username, String email, ...) { }
    
    // Basit constructor
    public UserDTO(Long id, String username, String bio) { }
    
    // Boş constructor
    public UserDTO() { }
}
2.	Exception Overloading:
public class UserNotFoundException extends RuntimeException {
    // Farklı parametrelerle
    public UserNotFoundException(String message) { }
    public UserNotFoundException(Long userId) { }
    public UserNotFoundException(String field, String value) { }
}
Faydaları:
•	Aynı işlem farklı şekillerde yapılabilir
•	Esneklik artar
•	Kullanım kolaylığı sağlar

4. KATMANLI MİMARİ

4.1 Mimari Genel Bakış
┌─────────────────────────────────────────┐
│         Controller Layer (Kişi 3)       │
│   REST API - HTTP Request Handling      │
└──────────────────┬──────────────────────┘
                   │
                   ↓
┌─────────────────────────────────────────┐
│       Service Layer (Kişi 2)            │
│   Business Logic - İş Mantığı           │
│   - UserService                         │
│   - PostService                         │
│   - LikeService                         │
│   - FollowService                       │
└──────────────────┬──────────────────────┘
                   │
                   ↓
┌─────────────────────────────────────────┐
│      Repository Layer (Kişi 1)          │
│   Database Access - Spring Data JPA     │
└──────────────────┬──────────────────────┘
                   │
                   ↓
┌─────────────────────────────────────────┐
│           Database (MySQL)              │
└─────────────────────────────────────────┘


4.2 Katman Sorumlulukları
Service Layer :
•	İş kurallarını uygular
•	Validation yapar
•	Entity ↔ DTO dönüşümü
•	Exception yönetimi
•	Transaction yönetimi
Örnek İş Akışı:
// 1. Controller'dan istek gelir
userService.register(username, email, password)

// 2. Service validation yapar
if (userRepository.existsByUsername(username)) {
    throw new DuplicateUsernameException(username);
}

// 3. Entity oluşturur
User user = new User();
user.setUsername(username);
user.setPasswordHash(hashPassword(password));

// 4. Repository'ye kaydeder
User savedUser = userRepository.save(user);

// 5. DTO'ya dönüştürüp döner
return convertToDTO(savedUser);


4.3 Separation of Concerns (SoC)

Her katmanın tek bir sorumluluğu var:
Katman	Sorumluluk	Bilmediği Şeyler
Controller	HTTP yönetimi	Database detayları
Service	İş mantığı	HTTP detayları, SQL
Repository	Database erişimi	İş kuralları

5. EXCEPTION HANDLING STRATEJİSİ

5.1 Custom Exception Yapısı
Neden Custom Exception?
•	Spesifik hata durumlarını temsil eder
•	Hata mesajları anlamlıdır
•	Exception handling kolaylaşır

5.2 Exception Hiyerarşisi
RuntimeException
    │
    ├── UserNotFoundException
    ├── PostNotFoundException
    ├── InvalidCredentialsException
    ├── DuplicateUsernameException
    ├── DuplicateEmailException
    ├── AlreadyFollowingException
    ├── NotFollowingException
    ├── AlreadyLikedException
    └── UnauthorizedException


5.3 Exception Kullanım Örnekleri
1. UserNotFoundException:
// Kullanıcı bulunamazsa
User user = userRepository.findById(userId)
    .orElseThrow(() -> new UserNotFoundException(userId));
2. DuplicateUsernameException:
// Kullanıcı adı zaten varsa
if (userRepository.existsByUsername(username)) {
    throw new DuplicateUsernameException(username);
}
3. UnauthorizedException:
// Yetkisiz işlem
if (!post.getUser().getUserId().equals(userId)) {
    throw new UnauthorizedException("delete this post", userId);
}

5.4 Exception Handling Avantajları

Okunabilirlik: Hata mesajları açık ve anlaşılır
Maintenance: Yeni exception eklemek kolay
Debugging: Hata kaynağı hemen anlaşılır
User Experience: Kullanıcıya anlamlı mesajlar



6. KOD ÖRNEKLERİ VE AÇIKLAMALAR
6.1 UserService - Kullanıcı Kaydı
public UserDTO register(String username, String email, String password) {
    // 1. İŞ KURALI: Username benzersiz olmalı
    if (userRepository.existsByUsername(username)) {
        throw new DuplicateUsernameException(username);
    }
    
    // 2. İŞ KURALI: Email benzersiz olmalı
    if (userRepository.existsByEmail(email)) {
        throw new DuplicateEmailException(email);
    }
    
    // 3. ENTITY OLUŞTUR
    User user = new User();
    user.setUsername(username);
    user.setEmail(email);
    user.setPasswordHash(hashPassword(password)); // Güvenlik
    
    // 4. VERİTABANINA KAYDET
    User savedUser = userRepository.save(user);
    
    // 5. DTO'YA DÖNÜŞTÜR (Encapsulation)
    return convertToDTO(savedUser);
}
OOP Prensipleri:
Encapsulation: Private helper metodlar
Abstraction: Repository detayları gizli
Single Responsibility: Sadece kayıt işi


6.2 PostService - Post Silme (Yetkilendirme)
public void deletePost(Integer postId, Integer userId) {
    // 1. POST KONTROLÜ
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new PostNotFoundException(postId));
    
    // 2. YETKİ KONTROLÜ (İş Mantığı)
    if (!post.getUser().getUserId().equals(userId)) {
        throw new UnauthorizedException("delete this post", userId);
    }
    
    // 3. SİLME İŞLEMİ
    postRepository.delete(post);
}
İş Mantığı:
•	Sadece post sahibi silebilir
•	Exception ile hata yönetimi
•	Clear ve güvenli kod


6.3 LikeService - Toggle Pattern
public boolean toggleLike(Integer userId, Integer postId) {
    if (isPostLikedByUser(userId, postId)) {
        unlikePost(userId, postId);
        return false; // Beğeni kaldırıldı
    } else {
        likePost(userId, postId);
        return true; // Beğeni eklendi
    }
}
Tasarım Kararı:
•	Kullanıcı deneyimi odaklı
•	Tek metod çağrısıyla toggle
•	Boolean return ile durum bildirimi

6.4 FollowService - Karşılıklı Takip Kontrolü
public boolean areMutualFollowers(Integer user1, Integer user2) {
    return isFollowing(user1, user2) && isFollowing(user2, user1);
}
Kod Kalitesi:
•	Okunabilir ve anlaşılır
•	Yeniden kullanılabilir metodlar
•	Single line, clear logic

6.5 DTO Dönüşümü - Entity to DTO

private PostDTO convertToDTO(Post post, Integer currentUserId) {
    PostDTO dto = new PostDTO();
    dto.setId(post.getPostId());
    dto.setContent(post.getContent());
    
    // Author bilgisi (nested DTO)
    UserDTO author = new UserDTO();
    author.setId(post.getUser().getUserId());
    author.setUsername(post.getUser().getUsername());
    dto.setAuthor(author);
    
    // İstatistikler
    dto.setLikeCount(post.getLikes().size());
    
    // Kullanıcı beğenmiş mi?
    boolean isLiked = post.getLikes().stream()
        .anyMatch(like -> like.getUser().getUserId().equals(currentUserId));
    dto.setLikedByCurrentUser(isLiked);
    
    return dto;
}
OOP Prensipleri:
•	Encapsulation: Private metod
•	Abstraction: Dönüşüm detayları gizli
•	Data Hiding: Entity detayları DTO'da yok


7. SONUÇ VE DEĞERLENDİRME

7.1 Başarılan Hedefler
Temiz Kod: SOLID prensiplere uygun
Modüler Yapı: Her servis bağımsız çalışabilir
Güvenlik: DTO ile hassas veri koruması
Hata Yönetimi: Anlamlı exception'lar
Okunabilirlik: İyi dokümante edilmiş kod
Genişletilebilirlik: Yeni özellik eklemek kolay


7.2 Kullanılan OOP Prensipleri Özet

Prensip     	Uygulama	        Fayda
Encapsulation	Private field'lar,      getter/setter	Veri güvenliği
Abstraction	    Service layer, DTO      pattern	Komplekslik gizleme
Inheritance	    Exception hierarchy	    Kod tekrarı azaltma
Polymorphism	Constructor overloading	 Esneklik



7.3 Tasarım Desenleri Özet
Desen	Amaç	Kullanım Yeri
Service Layer	İş mantığı ayırma	Tüm service sınıfları
DTO Pattern	Veri taşıma	Tüm DTO sınıfları
Repository	Database soyutlama	Spring Data JPA


7.4 Öğrenilen Dersler
1.	Separation of Concerns önemlidir: Her katman kendi işini yapar
2.	Exception handling kod kalitesini artırır: Hata yönetimi merkezi ve anlaşılır
3.	DTO kullanımı güvenliği artırır: Hassas veriler korunur
4.	Helper metodlar kodu temizler: Tekrar eden kod bloklarını önler
5.	İyi dokümantasyon bakımı kolaylaştırır: Javadoc yorumları çok yararlı


7.5 Geliştirilebilir Noktalar
•	Şifreleme: BCryptPasswordEncoder implementasyonu
•	Validation: Bean Validation (@Valid, @NotNull) kullanımı
•	Caching: Sık kullanılan veriler için cache mekanizması
•	Logging: SLF4J ile detaylı loglama
•	Unit Testing: JUnit ile service testleri

7.6 Sonuç
Bu projede, modern Java backend geliştirme best practice'lerini uygulayarak temiz, sürdürülebilir ve güvenli bir Business Logic katmanı geliştirilmiştir. OOP prensipleri ve tasarım desenlerinin doğru kullanımı sayesinde, kod kalitesi yüksek, test edilebilir ve genişletilebilir bir yapı oluşturulmuştur.
Service katmanı, Entity ve Controller katmanları arasında sağlam bir köprü görevi görmekte ve iş mantığını merkezi bir noktada toplamaktadır.
________________________________________
 KAYNAKLAR
•	Spring Boot Documentation: https://spring.io/projects/spring-boot
•	Java OOP Best Practices
•	Design Patterns: Gang of Four
•	Clean Code - Robert C. Martin
•	Effective Java - Joshua Bloch
________________________________________
Geliştirici: Ayşe Nur Yılmaz - Business Logic Katmanı
Proje: Sosyal Medya Mikroblog Platformu
Tarih: Aralık 2025

