# 🌐 Social Media Platform

Mikro blog sosyal medya platformu projesi. Spring Boot backend ve modern frontend teknolojileri kullanılarak geliştirilmiştir.

## 📁 Proje Yapısı

```
social-media-platform/
├── database/          # Veritabanı şemaları, scriptler ve dökümanlar
│   ├── schema.sql
│   ├── sample-data.sql
│   ├── triggers.sql
│   ├── views.sql
│   ├── procedures.sql
│   └── TECHNICAL_DOCS.md
├── backend/           # Spring Boot REST API
│   ├── src/
│   ├── pom.xml
│   └── README.md
├── frontend/          # Frontend uygulaması (Planlama aşamasında)
│   └── README.md
└── README.md
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
mysql -u root -p < schema.sql
mysql -u root -p social_media_db < sample-data.sql
```

### 3. Environment Variable Tanımla

**Windows:**
```cmd
setx DB_PASSWORD "your_mysql_password"
```

**Linux/Mac:**
```bash
export DB_PASSWORD="your_mysql_password"
```

⚠️ **Önemli:** Environment variable tanımladıktan sonra terminali/IDE'yi yeniden başlatın!

### 4. Backend'i Çalıştır
```bash
cd backend
mvn spring-boot:run
```

Backend http://localhost:8080 adresinde çalışacak.

### 5. Frontend'i Çalıştır
```bash
cd frontend
# (Kurulum talimatları eklenecek)
```

## 🛠️ Teknolojiler

### Backend
- Spring Boot 3.5.9
- Spring Data JPA
- MySQL 8.0
- Lombok
- Maven

### Database
- MySQL 8.0
- Views, Triggers, Stored Procedures

### Frontend
- (Teknoloji seçimi devam ediyor)

## 👥 Ekip ve Görev Dağılımı

### 👤 Kişi 1 - Database + Backend Altyapı
**Durum:** ✅ Tamamlandı

- [x] ER Diagram tasarımı
- [x] SQL şemaları (tablolar, view'lar, trigger'lar, procedure'ler)
- [x] Spring Boot projesi kurulumu
- [x] Entity sınıfları (User, Post, Like, Hashtag, Follow)
- [x] Repository interface'leri
- [x] Environment variable yapılandırması
- [x] Test verileri
- [x] Dokümantasyon

**Öğrenilen Konular:**
- Database tasarımı ve normalizasyon
- Spring Boot proje yapısı
- JPA/Hibernate ORM
- Repository Pattern
- Environment variable güvenliği

---

### 👤 Kişi 2 - Business Logic (Service Katmanı)
**Durum:** 🚧 Devam ediyor

**Görevler:**
- [ ] UserService (kayıt, giriş, profil yönetimi)
- [ ] PostService (post oluşturma, listeleme, silme)
- [ ] LikeService (beğeni/beğenmeme)
- [ ] FollowService (takip/takipten çık)
- [ ] DTO sınıfları
- [ ] Exception handling
- [ ] OOP prensipleri raporu

**Kullanacağı Yapı:**
- Kişi 1'in oluşturduğu Entity'ler
- Kişi 1'in oluşturduğu Repository'ler

**Öğrenilecek Konular:**
- Service Layer Pattern
- Business Logic
- DTO (Data Transfer Object)
- Exception Handling
- OOP prensipleri

---

### 👤 Kişi 3 - API + Frontend + Entegrasyon
**Durum:** ⏳ Bekliyor

**Görevler:**
- [ ] REST Controller'lar (User, Post, Like, Follow)
- [ ] API endpoint'leri
- [ ] Frontend UI tasarımı
- [ ] Backend-Frontend entegrasyonu
- [ ] API testleri (Postman)
- [ ] Demo video hazırlığı

**Kullanacağı Yapı:**
- Kişi 2'nin oluşturduğu Service'ler

**Öğrenilecek Konular:**
- RESTful API design
- HTTP methods (GET, POST, PUT, DELETE)
- Frontend-Backend entegrasyonu
- API testing

## 📋 Özellikler

- ✅ Kullanıcı kaydı ve girişi
- ✅ Post paylaşma
- ✅ Beğeni sistemi
- ✅ Takip sistemi
- ✅ Hashtag desteği
- ✅ Kullanıcı istatistikleri (View)
- ✅ Trend hashtag'ler (View)
- ✅ En çok beğenilen postlar (View)

## 🗄️ Veritabanı

### Tablolar
- **users** - Kullanıcı bilgileri
- **posts** - Paylaşımlar
- **likes** - Beğeniler
- **follows** - Takip ilişkileri
- **hashtags** - Hashtag'ler
- **post_hashtags** - Post-Hashtag ilişkileri (M:N ara tablo)

### View'lar
- **mostlikedposts** - En çok beğenilen postlar
- **userstats** - Kullanıcı istatistikleri
- **trendinghashtags** - Trend hashtag'ler (son 7 gün)

### Stored Procedures
- **DeletePostWithRelations** - Post ve ilişkilerini güvenli şekilde siler
- **FollowUser** - Takip işlemi (kendini takip etmeyi engeller)
- **ToggleLike** - Beğeni durumunu değiştirme (like/unlike)

### Triggers
- **before_post_delete** - Post silinmeden önce hashtag ilişkilerini temizler
- **before_hashtag_insert** - Hashtag eklenirken normalize eder (küçük harf)
- **before_user_delete** - Kullanıcı silinmeden önce post kontrolü yapar

## 📚 Dokümantasyon

- [Database Technical Documentation](database/TECHNICAL_DOCS.md) - Detaylı veritabanı dokümantasyonu
- [Backend README](backend/README.md) - Backend kurulum ve kullanım
- [Frontend README](frontend/README.md) - Frontend kurulum (yakında)
- [API Documentation](docs/API.md) - REST API dokümantasyonu (yakında)

## 🧪 Test

### Backend Test
```bash
cd backend
mvn test
```

### MySQL Test Sorguları
```bash
cd database
mysql -u root -p social_media_db < test-queries.sql
```

### API Test
Postman collection: `docs/postman_collection.json` _(yakında)_

## 🔒 Güvenlik

- ✅ Şifreler environment variable olarak saklanır
- ✅ Git'e hassas bilgiler commit edilmez
- ✅ Her geliştirici kendi yerel yapılandırmasını kullanır
- ⚠️ Production'da güçlü şifreler ve farklı kullanıcılar kullanılmalı

## 📝 Kurulum Notları

- Java 17 veya üzeri gereklidir
- MySQL 8.0 kurulu olmalıdır
- Port 8080 backend için kullanılmaktadır
- Environment variable tanımladıktan sonra IDE yeniden başlatılmalıdır

## 🐛 Sorun Giderme

### Yaygın Sorunlar

#### MySQL bağlantı hatası
```
Çözüm: 
- MySQL servisinin çalıştığından emin olun
- DB_PASSWORD environment variable'ını kontrol edin
- Database adının doğru olduğunu kontrol edin (social_media_db)
```

#### Environment variable tanınmıyor
```
Çözüm:
- setx ile tanımladıysanız terminali/IDE'yi yeniden başlatın
- set komutu sadece o oturum için geçerlidir
```

#### Port çakışması
```
Çözüm:
- application.properties dosyasında farklı port tanımlayın
- Veya 8080 portunu kullanan uygulamayı kapatın
```

Daha fazla bilgi için:
- [Backend Troubleshooting](backend/README.md#sorun-giderme)
- [Database Issues](database/README.md#sorun-giderme)

## 📊 Proje İstatistikleri

- **Toplam Tablo:** 6
- **View:** 3
- **Stored Procedure:** 3
- **Trigger:** 3
- **Entity Sınıfı:** 5
- **Repository:** 5
- **Tahmini Süre:** 4 hafta

## 🤝 Katkıda Bulunma

1. Fork yapın
2. Feature branch oluşturun (`git checkout -b feature/amazing-feature`)
3. Değişikliklerinizi commit edin (`git commit -m 'feat: Add amazing feature'`)
4. Branch'inizi push edin (`git push origin feature/amazing-feature`)
5. Pull Request oluşturun

## 📄 Lisans

Bu proje eğitim amaçlı geliştirilmiştir.

## 📧 İletişim

Sorularınız için issue açabilirsiniz.

---

**Son Güncelleme:** 28 Aralık 2025