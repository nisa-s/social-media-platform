USE social_media;

-- Post silme (cascade yerine procedure ile)
DELIMITER //
CREATE PROCEDURE DeletePostWithRelations(IN p_post_id INT)
BEGIN
    DELETE FROM Post_Hashtags WHERE post_id = p_post_id;
    DELETE FROM Likes WHERE post_id = p_post_id;
    DELETE FROM Posts WHERE post_id = p_post_id;
END //
DELIMITER ;

-- Kullanıcı takip et
DELIMITER //
CREATE PROCEDURE FollowUser(
    IN p_follower_id INT, 
    IN p_following_id INT
)
BEGIN
    -- Kendini takip etmesin
    IF p_follower_id = p_following_id THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Kendini takip edemezsin';
    END IF;
    
    -- Zaten takip ediyorsa hata vermesin
    INSERT IGNORE INTO Follows (follower_id, following_id, created_at)
    VALUES (p_follower_id, p_following_id, NOW());
END //
DELIMITER ;

-- Post beğenme/beğenmeme toggle
DELIMITER //
CREATE PROCEDURE ToggleLike(
    IN p_user_id INT,
    IN p_post_id INT
)
BEGIN
    DECLARE like_exists INT;
    
    SELECT COUNT(*) INTO like_exists 
    FROM Likes 
    WHERE user_id = p_user_id AND post_id = p_post_id;
    
    IF like_exists > 0 THEN
        -- Beğeniyi kaldır
        DELETE FROM Likes 
        WHERE user_id = p_user_id AND post_id = p_post_id;
    ELSE
        -- Beğen
        INSERT INTO Likes (user_id, post_id, created_at)
        VALUES (p_user_id, p_post_id, NOW());
    END IF;
END //
DELIMITER ;