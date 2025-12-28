USE social_media;

-- Post silinince hashtag ilişkilerini temizle
DELIMITER //
CREATE TRIGGER before_post_delete
BEFORE DELETE ON Posts
FOR EACH ROW
BEGIN
    DELETE FROM Post_Hashtags WHERE post_id = OLD.post_id;
END //
DELIMITER ;

-- Yeni hashtag eklendiğinde lowercase yap
DELIMITER //
CREATE TRIGGER before_hashtag_insert
BEFORE INSERT ON Hashtags
FOR EACH ROW
BEGIN
    SET NEW.tag_name = LOWER(TRIM(NEW.tag_name));
END //
DELIMITER ;

-- Kullanıcı silme engelleme (postları varsa)
DELIMITER //
CREATE TRIGGER before_user_delete
BEFORE DELETE ON Users
FOR EACH ROW
BEGIN
    DECLARE post_count INT;
    SELECT COUNT(*) INTO post_count FROM Posts WHERE user_id = OLD.user_id;
    
    IF post_count > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Bu kullanıcının postları var, önce onları sil';
    END IF;
END //
DELIMITER ;