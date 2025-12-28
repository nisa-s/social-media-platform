# Teknik Veritabanı Dokümantasyonu

## Tablo İlişkileri

### Users ↔ Posts (1:N)
- Bir kullanıcı birden fazla post atabilir
- Foreign Key: Posts.user_id → Users.user_id
- Cascade: ON DELETE CASCADE (kullanıcı silinirse postları da silinir)

### Users ↔ Likes (1:N)
- Bir kullanıcı birden fazla post beğenebilir
- Foreign Key: Likes.user_id → Users.user_id

### Posts ↔ Likes (1:N)
- Bir post birden fazla beğeni alabilir
- Foreign Key: Likes.post_id → Posts.post_id
- Unique Constraint: (user_id, post_id) - Aynı kullanıcı aynı postu 2 kez beğenemez

### Posts ↔ Hashtags (M:N)
- Ara tablo: Post_Hashtags
- Bir post birden fazla hashtag içerebilir
- Bir hashtag birden fazla postta kullanılabilir

### Users ↔ Users (M:N) - Follows
- Kendi kendine ilişki (self-referencing)
- follower_id: Takip eden
- following_id: Takip edilen
- Check Constraint: follower_id ≠ following_id

## Index'ler

### Users Tablosu
- PRIMARY KEY: user_id
- UNIQUE INDEX: username, email (hızlı arama)

### Posts Tablosu
- PRIMARY KEY: post_id
- INDEX: user_id (kullanıcının postlarını hızlı getir)
- INDEX: created_at (tarih sıralaması için)

### Likes Tablosu
- PRIMARY KEY: like_id
- UNIQUE INDEX: (user_id, post_id)
- INDEX: post_id (postun beğeni sayısını hızlı hesapla)

## Performans Optimizasyonları

1. **Index kullanımı**: Sık sorgulanan kolonlarda index var
2. **Normalized tasarım**: 3NF'ye uygun, veri tekrarı yok
3. **Cascade delete**: Manuel silme yerine otomatik temizleme
4. **Timestamp'ler**: created_at, updated_at ile audit trail
5. **View'lar**: Karmaşık sorguları önceden optimize edilmiş şekilde saklar
6. **Stored Procedure'ler**: İş mantığını veritabanı seviyesinde yönetir

## View Detayları

### MostLikedPosts
En çok beğenilen postları listeler. JOIN ve GROUP BY kullanarak her postun beğeni sayısını hesaplar.

**Kullanım:**
```sql
SELECT * FROM MostLikedPosts LIMIT 10;
```

### UserStats
Her kullanıcının istatistiklerini (post sayısı, verdiği beğeniler, takipçi/takip sayısı) hesaplar.

**Kullanım:**
```sql
SELECT * FROM UserStats WHERE username = 'emre.kaya';
```

### TrendingHashtags
Son 7 gündeki en popüler hashtagleri gösterir.

**Kullanım:**
```sql
SELECT * FROM TrendingHashtags;
```

## Stored Procedure Detayları

### DeletePostWithRelations
Post ve tüm ilişkili kayıtları (beğeniler, hashtag ilişkileri) güvenli bir şekilde siler.

**Kullanım:**
```sql
CALL DeletePostWithRelations(1);
```

### FollowUser
Bir kullanıcının diğerini takip etmesini sağlar. Kendini takip etmeyi engeller.

**Kullanım:**
```sql
CALL FollowUser(1, 2);  -- Kullanıcı 1, kullanıcı 2'yi takip eder
```

### ToggleLike
Beğeni durumunu değiştirir (varsa kaldırır, yoksa ekler).

**Kullanım:**
```sql
CALL ToggleLike(1, 5);  -- Kullanıcı 1, post 5'i beğenir/beğenmekten vazgeçer
```

## Trigger Detayları

### before_post_delete
Post silinmeden önce tetiklenir ve Post_Hashtags tablosundaki ilişkili kayıtları temizler.

**Amaç:** Veri bütünlüğünü korumak

### before_hashtag_insert
Yeni hashtag eklenirken tetiklenir ve tag_name'i küçük harfe çevirir, baştaki/sondaki boşlukları temizler.

**Amaç:** Hashtag tutarlılığını sağlamak (#Java ve #java aynı olsun)

### before_user_delete
Kullanıcı silinmeden önce tetiklenir. Eğer kullanıcının postları varsa silme işlemini engeller.

**Amaç:** Yanlışlıkla veri kaybını önlemek

## Güvenlik

- Password'ler hash olarak saklanır (password_hash)
- Email ve username UNIQUE (duplicate hesap yok)
- Foreign Key constraint'ler ile veri bütünlüğü
- Trigger'lar ile otomatik validasyon
- Procedure'ler ile güvenli veri manipülasyonu

## Kapasite Planlaması

- user_id, post_id: INT (2 milyar kayıt kapasitesi)
- content: TEXT (65.535 karakter, mikroblog için yeterli)
- username: VARCHAR(50) (Twitter benzeri)
- email: VARCHAR(100) (standart email uzunluğu)
- password_hash: VARCHAR(255) (bcrypt için yeterli)

## Örnek Sorgular

### En aktif kullanıcılar
```sql
SELECT * FROM UserStats ORDER BY post_count DESC LIMIT 10;
```

### Bir kullanıcının tüm postları ve beğeni sayıları
```sql
SELECT 
    p.content,
    p.created_at,
    COUNT(l.like_id) as like_count
FROM Posts p
LEFT JOIN Likes l ON p.post_id = l.post_id
WHERE p.user_id = 1
GROUP BY p.post_id
ORDER BY p.created_at DESC;
```

### Bir kullanıcının takip ettiği kişilerin postları (feed)
```sql
SELECT 
    p.content,
    u.username,
    p.created_at
FROM Posts p
JOIN Users u ON p.user_id = u.user_id
WHERE p.user_id IN (
    SELECT following_id 
    FROM Follows 
    WHERE follower_id = 1
)
ORDER BY p.created_at DESC;
```

### Hashtag arama
```sql
SELECT 
    p.content,
    u.username,
    p.created_at
FROM Posts p
JOIN Users u ON p.user_id = u.user_id
JOIN Post_Hashtags ph ON p.post_id = ph.post_id
JOIN Hashtags h ON ph.hashtag_id = h.hashtag_id
WHERE h.tag_name = 'java'
ORDER BY p.created_at DESC;
```