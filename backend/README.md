# 🚀 Social Media Platform - Backend

Spring Boot tabanlı RESTful API backend uygulaması.

## 📋 Teknolojiler

- **Spring Boot** 3.5.9
- **Spring Data JPA** - ORM
- **MySQL** 8.0 - Veritabanı
- **Lombok** - Boilerplate kod azaltma
- **Maven** - Dependency yönetimi

## 🗄️ Veritabanı Yapısı

### Entity'ler
- `User` - Kullanıcı bilgileri
- `Post` - Kullanıcı gönderileri
- `Like` - Beğeniler
- `Hashtag` - Hashtag'ler
- `Follow` - Takip ilişkileri

### İlişkiler
- User ↔ Post (1:N)
- User ↔ Like (1:N)
- Post ↔ Like (1:N)
- Post ↔ Hashtag (M:N)
- User ↔ User (M:N - Follows)

## ⚙️ Kurulum

### Gereksinimler
- Java 17 veya üzeri
- Maven 3.6+
- MySQL 8.0+

### 1. Projeyi Klonla
```bash
git clone https://github.com/nisa-s/social-media-platform.git
cd social-media-platform/backend
```

### 2. Veritabanını Oluştur
MySQL'de aşağıdaki komutu çalıştırın:

```sql
CREATE DATABASE social_media_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. Environment Variable Ayarla

**⚠️ ÖNEMLİ:** Uygulama MySQL şifresini environment variable'dan okur.

#### Windows (Kalıcı):
```cmd
setx DB_PASSWORD "your_mysql_password"
```

**Sonra terminali/IDE'yi kapatıp yeniden açın!**

#### Windows (Geçici - Sadece Bu Oturum):
```cmd
set DB_PASSWORD=your_mysql_password
```

#### Linux/Mac:
```bash
# .bashrc veya .zshrc dosyasına ekleyin
export DB_PASSWORD="your_mysql_password"

# Veya geçici olarak:
export DB_PASSWORD="your_mysql_password"
```

### 4. Uygulamayı Çalıştır
```bash
mvn spring-boot:run
```

Uygulama http://localhost:8080 adresinde çalışacak.

## 🔧 Alternatif Yapılandırma

Environment variable kullanmak istemiyorsanız, `src/main/resources/application.properties` dosyasında şifreyi direkt yazabilirsiniz:

```properties
spring.datasource.password=your_actual_password
```

⚠️ **Dikkat:** Bu durumda dosyayı Git'e commit etmeyin!

## 📦 Proje Yapısı

```
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/socialmedia/backend/
│   │   │       ├── BackendApplication.java
│   │   │       ├── entity/         # JPA Entity sınıfları
│   │   │       │   ├── User.java
│   │   │       │   ├── Post.java
│   │   │       │   ├── Like.java
│   │   │       │   ├── Hashtag.java
│   │   │       │   └── Follow.java
│   │   │       ├── repository/     # Spring Data JPA Repository'ler
│   │   │       │   ├── UserRepository.java
│   │   │       │   ├── PostRepository.java
│   │   │       │   ├── LikeRepository.java
│   │   │       │   ├── HashtagRepository.java
│   │   │       │   └── FollowRepository.java
│   │   │       ├── service/        # İş mantığı (Kişi 2 - Devam ediyor)
│   │   │       └── controller/     # REST API (Kişi 3 - Beklemede)
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── pom.xml
```

## 🧪 Test

Uygulama çalıştıktan sonra:

### Tarayıcıda Test:
```
http://localhost:8080
```

### MySQL'de Kontrol:
```sql
USE social_media_db;
SHOW TABLES;
```

Şu tabloları görmelisiniz:
- `users`
- `posts`
- `likes`
- `hashtags`
- `follows`
- `post_hashtags`

## 📊 Veritabanı Yapılandırması

`application.properties` dosyası şu şekilde yapılandırılmıştır:

```properties
# Database bağlantısı
spring.datasource.url=jdbc:mysql://localhost:3306/social_media_db
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD:defaultpassword}

# Hibernate ayarları
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

**Not:** `${DB_PASSWORD:defaultpassword}` ifadesi:
- Önce `DB_PASSWORD` environment variable'ını arar
- Bulamazsa `defaultpassword` kullanır (test için)

## 👥 Geliştirici Notları

### Kişi 1 (Database + Entity + Repository) - ✅ TAMAMLANDI
- [x] Spring Boot projesi kurulumu
- [x] Entity sınıfları oluşturuldu
- [x] Repository interface'leri oluşturuldu
- [x] MySQL bağlantısı yapılandırıldı
- [x] Environment variable yapılandırması

### Kişi 2 (Service Katmanı) - 🚧 DEVAM EDİYOR
- [ ] UserService (kayıt, giriş, profil yönetimi)
- [ ] PostService (post oluşturma, listeleme, silme)
- [ ] LikeService (beğeni/beğenmeme)
- [ ] FollowService (takip/takipten çık)
- [ ] DTO sınıfları
- [ ] Exception handling
- [ ] OOP prensipleri raporu

### Kişi 3 (Controller + Frontend) - ⏳ BEKLEMEDE
- [ ] REST Controller'lar
- [ ] API dokümantasyonu
- [ ] Frontend entegrasyonu

## 🐛 Sorun Giderme

### MySQL bağlantı hatası
```
Error: Access denied for user 'root'@'localhost'
```
**Çözüm:** 
- MySQL servisinin çalıştığından emin olun
- `DB_PASSWORD` environment variable'ın doğru tanımlandığından emin olun
- IDE'yi yeniden başlatın

### Environment variable tanınmıyor
```
Access denied for user 'root'@'localhost' (using password: YES)
```
**Çözüm:** 
- `setx` kullandıysanız terminali kapatıp yeniden açın
- Veya geçici olarak: `set DB_PASSWORD=your_password`

### Lombok çalışmıyor
```
Error: Cannot resolve symbol 'getData'
```
**Çözüm:**
- IDE'ye Lombok plugin'i kurun
- Maven dependency'leri yenileyin: `mvn clean install`
- IDE'yi yeniden başlatın

### Port 8080 kullanımda
```
Error: Port 8080 was already in use
```
**Çözüm:**
- `application.properties` dosyasında `server.port=8081` ekleyin
- Veya 8080 portunu kullanan uygulamayı kapatın

## 🔒 Güvenlik Notları

- ✅ MySQL şifresi environment variable'da saklanır
- ✅ `application.properties` dosyası Git'e commit edilir ama şifre içermez
- ✅ Her geliştirici kendi şifresini yerel olarak tanımlar
- ⚠️ Production'da farklı kullanıcı adı ve şifre kullanın (root kullanmayın)

## 📚 Ek Kaynaklar

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [Lombok](https://projectlombok.org/)

## 📝 Lisans

Bu proje eğitim amaçlı geliştirilmiştir.