# 🌐 Sosyal Medya Mikroblog Platformu

Mikro blog sosyal medya platformu projesi. Spring Boot backend ve MySQL veritabanı kullanılarak geliştirilmiş, tam özellikli bir REST API uygulamasıdır.

## 👥 Proje Ekibi

- **Nisanur Şen** (230229075) - Database + Entity + Repository Katmanı
- **Ayşe Nur Yılmaz** (230229072) - Service + DTO + Exception Katmanı  
- **F. Zehra Ateş** (230229082) - Controller + API + Dokümantasyon

## 📁 Proje Yapısı

```
social-media-mikroblog/
├── database/                    # Veritabanı şemaları ve scriptler
│   ├── ER-Diagram.png         # Entity-Relationship diyagramı
│   ├── schema.sql              # Tablo tanımları
│   ├── sample-data.sql         # Örnek veriler
│   ├── triggers.sql            # Trigger'lar
│   ├── views.sql               # View'lar
│   ├── procedures.sql          # Stored Procedures
│   ├── test-queries.sql        # Test sorguları
│   ├── README.md               # Database dokümantasyonu
│   └── TECHNICAL_DOCS.md       # Teknik detaylar
├── backend/                    # Spring Boot REST API
│   ├── src/main/java/
│   │   ├── entity/            # User, Post, Like, Hashtag, Follow
│   │   ├── repository/        # Spring Data JPA repositories
│   │   ├── dto/               # Data Transfer Objects
│   │   ├── service/           # İş mantığı katmanı
│   │   ├── controller/        # REST API endpoints
│   │   └── exception/         # Custom exception'lar
│   ├── pom.xml
│   └── README.md
└── README.md                  # Bu dosya
```

## 🚀 Hızlı Başlangıç

### Gereksinimler
- Java 17+
- Maven 3.6+
- MySQL 8.0+

### 1. Projeyi Klonla
```bash
git clone https://github.com/nisa-s/social-media-platform.git
cd social-media-platform
```

### 2. Veritabanını Kur
```bash
cd database
mysql -u root -p
CREATE DATABASE social_media;
exit;

mysql -u root -p social_media < schema.sql
mysql -u root -p social_media < views.sql
mysql -u root -p social_media < procedures.sql
mysql -u root -p social_media < triggers.sql
mysql -u root -p social_media < sample-data.sql
```

### 3. Environment Variable Tanımla

**Windows (CMD - Kalıcı):**
```cmd
setx DB_PASSWORD "your_mysql_password"
```

**Windows (PowerShell - Geçici):**
```powershell
$env:DB_PASSWORD="your_mysql_password"
```

**Linux/Mac:**
```bash
export DB_PASSWORD="your_mysql_password"
```

⚠️ **Önemli:** `setx` ile tanımladıktan sonra terminali/IDE'yi yeniden başlatın!

### 4. Backend'i Çalıştır
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Backend `http://localhost:8080` adresinde çalışacak.

## 🛠️ Kullanılan Teknolojiler

### Backend
- **Java 17** - Programlama dili
- **Spring Boot 3.5.9** - Framework
- **Spring Data JPA** - ORM ve veri erişim katmanı
- **Lombok** - Boilerplate kod azaltma
- **Maven** - Dependency management

### Database
- **MySQL 8.0** - İlişkisel veritabanı
- **View'lar** - Karmaşık sorguları basitleştirme
- **Stored Procedures** - İş mantığını veritabanında yönetme
- **Triggers** - Otomatik veri doğrulama ve temizleme

## 📋 Özellikler

### Fonksiyonel Özellikler
- ✅ Kullanıcı kaydı ve profil yönetimi
- ✅ Post paylaşma, düzenleme, silme
- ✅ Beğeni sistemi (like/unlike toggle)
- ✅ Takip sistemi (follow/unfollow)
- ✅ Hashtag otomatik çıkarma ve etiketleme
- ✅ Kullanıcı istatistikleri (post sayısı, takipçi/takip)
- ✅ Trend hashtag analizi (son 7 gün)
- ✅ En çok beğenilen postlar listesi

### Teknik Özellikler
- ✅ Çok katmanlı mimari (Entity-Repository-DTO-Service-Controller)
- ✅ RESTful API tasarımı
- ✅ DTO pattern ile güvenli veri transferi
- ✅ Custom exception handling
- ✅ JPA ilişkileri (OneToMany, ManyToMany, ManyToOne)
- ✅ Transaction yönetimi
- ✅ 3NF normalizasyon

## 🗄️ Veritabanı Tasarımı

### Tablolar
| Tablo | Açıklama | İlişkiler |
|-------|----------|-----------|
| `users` | Kullanıcı bilgileri | 1:N Posts, 1:N Likes, M:N Follows |
| `posts` | Paylaşımlar | N:1 User, 1:N Likes, M:N Hashtags |
| `likes` | Beğeniler | N:1 User, N:1 Post |
| `follows` | Takip ilişkileri | N:1 Follower, N:1 Following |
| `hashtags` | Hashtag'ler | M:N Posts |
| `post_hashtags` | Ara tablo | Post-Hashtag M:N ilişkisi |

### View'lar
- **`mostlikedposts`** - En çok beğenilen postları listeler
- **`userstats`** - Kullanıcı bazlı istatistikler (post, like, follower/following sayıları)
- **`trendinghashtags`** - Son 7 günün trend hashtag'leri

### Stored Procedures
- **`DeletePostWithRelations(post_id)`** - Post ve tüm ilişkilerini güvenli şekilde siler
- **`FollowUser(follower_id, following_id)`** - Takip işlemi (kendini takip engeli ile)
- **`ToggleLike(user_id, post_id)`** - Beğeni durumunu değiştirme (varsa kaldır, yoksa ekle)

### Triggers
- **`before_post_delete`** - Post silinmeden önce hashtag ilişkilerini temizler
- **`before_hashtag_insert`** - Hashtag ekleme sırasında normalizasyon (küçük harf + trim)
- **`before_user_delete`** - Kullanıcı silinmeden önce post kontrolü (varsa engelle)

## 🏗️ Mimari ve Katmanlar

### 1. Entity Katmanı (Nisanur Şen)
- `User.java` - Kullanıcı entity
- `Post.java` - Gönderi entity
- `Like.java` - Beğeni entity
- `Follow.java` - Takip entity
- `Hashtag.java` - Hashtag entity

### 2. Repository Katmanı (Nisanur Şen)
- `UserRepository.java` - Kullanıcı veri erişimi
- `PostRepository.java` - Gönderi veri erişimi
- `LikeRepository.java` - Beğeni veri erişimi
- `FollowRepository.java` - Takip veri erişimi
- `HashtagRepository.java` - Hashtag veri erişimi

### 3. DTO Katmanı (Ayşe Nur Yılmaz)
- `UserDTO.java` - Kullanıcı veri transfer objesi
- `PostDTO.java` - Gönderi veri transfer objesi
- `LikeDTO.java` - Beğeni veri transfer objesi
- `FollowDTO.java` - Takip veri transfer objesi
- `HashtagDTO.java` - Hashtag veri transfer objesi
- `AuthDTO.java` - Kimlik doğrulama (Register/Login)

### 4. Service Katmanı (Ayşe Nur Yılmaz)
- `UserService.java` - Kullanıcı iş mantığı
- `PostService.java` - Gönderi iş mantığı
- `LikeService.java` - Beğeni iş mantığı
- `FollowService.java` - Takip iş mantığı
- Custom Exception'lar ve Global Exception Handler

### 5. Controller Katmanı (F. Zehra Ateş)
- `UserController.java` - Kullanıcı REST API
- `PostController.java` - Gönderi REST API
- `LikeController.java` - Beğeni REST API
- `FollowController.java` - Takip REST API

## 🔌 API Endpoints

### Base URL: `http://localhost:8080/api`

#### User Endpoints
| Method | Endpoint | Açıklama |
|--------|----------|----------|
| POST | `/users/register` | Yeni kullanıcı kaydı |
| POST | `/users/login` | Kullanıcı girişi |
| GET | `/users/{id}` | Kullanıcı profili |
| PUT | `/users/{id}` | Profil güncelleme |
| GET | `/users` | Tüm kullanıcıları listele |
| GET | `/users/search?term={term}` | Kullanıcı ara |

#### Post Endpoints
| Method | Endpoint | Açıklama |
|--------|----------|----------|
| POST | `/posts` | Yeni gönderi oluştur |
| GET | `/posts` | Tüm gönderileri listele |
| GET | `/posts/{id}` | Tekil gönderi getir |
| GET | `/posts/user/{userId}` | Kullanıcının gönderileri |
| PUT | `/posts/{id}` | Gönderi güncelle |
| DELETE | `/posts/{id}` | Gönderi sil |
| GET | `/posts/hashtag/{tag}` | Hashtag'e göre ara |

#### Like Endpoints
| Method | Endpoint | Açıklama |
|--------|----------|----------|
| POST | `/likes/toggle` | Beğeni ekle/kaldır |
| GET | `/likes/post/{postId}` | Post beğenilerini listele |
| GET | `/likes/user/{userId}` | Kullanıcının beğendikleri |
| GET | `/likes/count/{postId}` | Post beğeni sayısı |

#### Follow Endpoints
| Method | Endpoint | Açıklama |
|--------|----------|----------|
| POST | `/follows/follow` | Kullanıcı takip et |
| DELETE | `/follows/unfollow` | Takibi bırak |
| GET | `/follows/followers/{userId}` | Takipçileri listele |
| GET | `/follows/following/{userId}` | Takip edilenleri listele |
| GET | `/follows/count/followers/{userId}` | Takipçi sayısı |
| GET | `/follows/count/following/{userId}` | Takip edilen sayısı |

Detaylı API dokümantasyonu ve request/response örnekleri için `docs/API.md` dosyasına bakın.

## 🧪 Test

### Backend Testleri
```bash
cd backend
mvn test
```

### MySQL Test Sorguları
```bash
cd database
mysql -u root -p social_media < test-queries.sql
```

### Postman ile API Testleri
1. Postman'i açın
2. Import → `docs/postman_collection.json` _(yakında)_
3. Environment variable `base_url=http://localhost:8080/api` olarak ayarlayın
4. Test'leri çalıştırın

### Test Senaryoları
- ✅ Kullanıcı kaydı (duplicate kontrolü)
- ✅ Post oluşturma ve hashtag çıkarma
- ✅ Beğeni sistemi (toggle, duplicate engelleme)
- ✅ Takip sistemi (self-follow engelleme)
- ✅ View'lar (TrendingHashtags, MostLikedPosts)
- ✅ Stored Procedure'ler (ToggleLike, FollowUser)

## 🎯 OOP Prensipleri

Projede uygulanan nesne yönelimli programlama prensipleri:

### 1. Encapsulation (Kapsülleme)
- Tüm field'lar `private`
- Getter/Setter metodları ile kontrollü erişim
- Lombok ile boilerplate kod azaltma

### 2. Abstraction (Soyutlama)
- Repository interface'leri ile veri erişimi soyutlama
- Service katmanı ile iş mantığı soyutlama
- DTO ile entity'leri dış dünyadan izole etme

### 3. Inheritance (Kalıtım)
- Custom Exception sınıfları `RuntimeException`'dan türer
- Ortak davranışlar parent class'ta

### 4. Polymorphism (Çok Biçimlilik)
- Constructor overloading (DTO'larda farklı constructor'lar)
- Method overloading

### 5. Separation of Concerns
- Her katman tek sorumluluğa sahip
- Controller → HTTP işlemleri
- Service → İş mantığı
- Repository → Veri erişimi

### 6. Dependency Injection
- Spring Framework'ün IoC container'ı
- `@Autowired` ile bağımlılık yönetimi
- Loose coupling


## 🔒 Güvenlik

- ✅ Şifreler environment variable ile saklanır
- ✅ `.gitignore` ile hassas dosyalar commit edilmez
- ✅ DTO kullanımı ile entity'ler direkt expose edilmez

## 🐛 Sorun Giderme

### MySQL Bağlantı Hatası
**Hata:** `Communications link failure`

**Çözüm:**
```bash
# MySQL servisini kontrol et
# Windows:
net start MySQL80

# Linux:
sudo systemctl start mysql

# macOS:
brew services start mysql
```

### Environment Variable Tanınmıyor
**Hata:** `DB_PASSWORD` bulunamıyor

**Çözüm:**
```bash
# 1. Terminali/IDE'yi yeniden başlat
# 2. Veya geçici olarak set kullan:
set DB_PASSWORD=your_password  # Windows CMD
$env:DB_PASSWORD="your_password"  # Windows PowerShell
export DB_PASSWORD="your_password"  # Linux/Mac
```

### Port 8080 Kullanımda
**Hata:** `Port 8080 is already in use`

**Çözüm 1:** Farklı port kullan
```properties
# application.properties
server.port=8081
```

**Çözüm 2:** Portu kullanan uygulamayı kapat
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID [PID_NUMARASI] /F

# Linux/Mac
lsof -ti:8080 | xargs kill -9
```

### Maven Build Hatası
**Çözüm:**
```bash
# Cache temizle ve tekrar build et
mvn clean install -U
```

Daha fazla bilgi için:
- [Backend Troubleshooting](backend/README.md#sorun-giderme)
- [Database Issues](database/README.md)

## 📚 Dokümantasyon

- **[Database Technical Docs](database/TECHNICAL_DOCS.md)** - Detaylı veritabanı dokümantasyonu
- **[Backend README](backend/README.md)** - Backend kurulum ve kullanım
- **[ER Diagram](database/ER-Diagram.png)** - Entity-Relationship diyagramı
- **[Proje Raporu](docs/SOSYAL_MEDYA_RAPOR.docx)** - Tam proje raporu

## 📊 Proje İstatistikleri

| Kategori | Sayı |
|----------|------|
| **Entity Sınıfı** | 5 |
| **Repository** | 5 |
| **Service** | 4 |
| **Controller** | 4 |
| **DTO** | 6 |
| **Database Tablosu** | 6 |
| **View** | 3 |
| **Stored Procedure** | 3 |
| **Trigger** | 3 |
| **Custom Exception** | 10 |

## 🤝 Katkıda Bulunma

1. Fork yapın
2. Feature branch oluşturun (`git checkout -b feature/amazing-feature`)
3. Değişikliklerinizi commit edin (`git commit -m 'feat: Add amazing feature'`)
4. Branch'inizi push edin (`git push origin feature/amazing-feature`)
5. Pull Request oluşturun

## 📄 Lisans

Bu proje eğitim amaçlı geliştirilmiştir. Kocaeli Üniversitesi - Java ve Veritabanı Yönetimi dersi dönem projesi.

## 📧 İletişim

Proje hakkında sorularınız için:
- GitHub Issues

---

**Geliştirme Süresi:** 4 hafta  
**Son Güncelleme:** Ocak 2026  
**Durum:** ✅ Tamamlandı