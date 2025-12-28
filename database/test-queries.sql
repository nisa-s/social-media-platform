USE social_media;

-- TEST 1: Kullanıcı post atabilir mi?
INSERT INTO Posts (user_id, content) VALUES (1, 'Test postu #test');

-- TEST 2: Post beğenilebilir mi?
INSERT INTO Likes (user_id, post_id) VALUES (2, LAST_INSERT_ID());

-- TEST 3: Aynı kullanıcı aynı postu 2 kez beğenebilir mi? (HATA VERMELİ)
-- INSERT INTO Likes (user_id, post_id) VALUES (2, 1);  -- ❌ Duplicate entry hatası

-- TEST 4: Kullanıcı kendini takip edebilir mi? (HATA VERMELİ)
-- INSERT INTO Follows (follower_id, following_id) VALUES (1, 1);  -- ❌ CHECK constraint

-- TEST 5: Post silinince beğenileri de silinir mi?
-- DELETE FROM Posts WHERE post_id = 1;
-- SELECT * FROM Likes WHERE post_id = 1;  -- Boş dönmeli

-- TEST 6: En aktif kullanıcılar
SELECT 
    u.username,
    COUNT(p.post_id) as post_count,
    COUNT(DISTINCT l.like_id) as given_likes
FROM Users u
LEFT JOIN Posts p ON u.user_id = p.user_id
LEFT JOIN Likes l ON u.user_id = l.user_id
GROUP BY u.user_id
ORDER BY post_count DESC;

-- TEST 7: Hashtag kullanım sayısı
SELECT 
    h.tag_name,
    COUNT(ph.post_id) as usage_count
FROM Hashtags h
JOIN Post_Hashtags ph ON h.hashtag_id = ph.hashtag_id
GROUP BY h.hashtag_id
ORDER BY usage_count DESC;

-- TEST 8: Procedure test - Takip et
CALL FollowUser(1, 2);

-- TEST 9: Procedure test - Beğeni toggle
CALL ToggleLike(1, 1);
CALL ToggleLike(1, 1);  -- İkinci kez beğeniyi kaldırır

-- TEST 10: View test - Trend hashtagler
SELECT * FROM TrendingHashtags;

-- TEST 11: View test - Kullanıcı istatistikleri
SELECT * FROM UserStats;

-- TEST 12: View test - En çok beğenilen postlar
SELECT * FROM MostLikedPosts LIMIT 5;
