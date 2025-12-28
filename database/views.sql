USE social_media;

-- En çok beğenilen postlar
CREATE VIEW MostLikedPosts AS
SELECT 
    p.post_id,
    p.content,
    u.username,
    COUNT(l.like_id) as like_count
FROM Posts p
JOIN Users u ON p.user_id = u.user_id
LEFT JOIN Likes l ON p.post_id = l.post_id
GROUP BY p.post_id
ORDER BY like_count DESC;

-- Kullanıcı istatistikleri
CREATE VIEW UserStats AS
SELECT 
    u.user_id,
    u.username,
    COUNT(DISTINCT p.post_id) as post_count,
    COUNT(DISTINCT l.like_id) as likes_given,
    COUNT(DISTINCT f.following_id) as following_count,
    COUNT(DISTINCT f2.follower_id) as follower_count
FROM Users u
LEFT JOIN Posts p ON u.user_id = p.user_id
LEFT JOIN Likes l ON u.user_id = l.user_id
LEFT JOIN Follows f ON u.user_id = f.follower_id
LEFT JOIN Follows f2 ON u.user_id = f2.following_id
GROUP BY u.user_id;

-- Trend hashtagler (Son 7 gün)
CREATE VIEW TrendingHashtags AS
SELECT 
    h.tag_name,
    COUNT(ph.post_id) as usage_count,
    MAX(p.created_at) as last_used
FROM Hashtags h
JOIN Post_Hashtags ph ON h.hashtag_id = ph.hashtag_id
JOIN Posts p ON ph.post_id = p.post_id
WHERE p.created_at >= DATE_SUB(NOW(), INTERVAL 7 DAY)
GROUP BY h.hashtag_id
ORDER BY usage_count DESC
LIMIT 10;