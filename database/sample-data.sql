-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: social_media
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `follows`
--

LOCK TABLES `follows` WRITE;
/*!40000 ALTER TABLE `follows` DISABLE KEYS */;
INSERT INTO `follows` VALUES (1,1,2,'2025-12-27 21:15:13'),(2,1,3,'2025-12-27 21:15:13'),(3,1,4,'2025-12-27 21:15:13'),(4,2,1,'2025-12-27 21:15:13'),(5,2,4,'2025-12-27 21:15:13'),(6,3,1,'2025-12-27 21:15:13'),(7,3,2,'2025-12-27 21:15:13'),(8,3,5,'2025-12-27 21:15:13'),(9,4,1,'2025-12-27 21:15:13'),(10,4,2,'2025-12-27 21:15:13'),(11,4,5,'2025-12-27 21:15:13'),(12,5,1,'2025-12-27 21:15:13'),(13,5,3,'2025-12-27 21:15:13');
/*!40000 ALTER TABLE `follows` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `hashtags`
--

LOCK TABLES `hashtags` WRITE;
/*!40000 ALTER TABLE `hashtags` DISABLE KEYS */;
INSERT INTO `hashtags` VALUES (1,'vize','2025-12-27 21:15:13'),(2,'sınav','2025-12-27 21:15:13'),(3,'tiktok','2025-12-27 21:15:13'),(4,'java','2025-12-27 21:15:13'),(5,'kitap','2025-12-27 21:15:13'),(6,'uyku','2025-12-27 21:15:13'),(7,'101','2025-12-27 21:15:13'),(8,'proje','2025-12-27 21:15:13'),(9,'sonbahar','2025-12-27 21:15:13'),(10,'kampüs','2025-12-27 21:15:13'),(11,'final','2025-12-27 21:15:13'),(12,'kahve','2025-12-27 21:15:13'),(13,'öğrenci','2025-12-27 21:15:13'),(14,'ders','2025-12-27 21:15:13');
/*!40000 ALTER TABLE `hashtags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `likes`
--

LOCK TABLES `likes` WRITE;
/*!40000 ALTER TABLE `likes` DISABLE KEYS */;
INSERT INTO `likes` VALUES (33,2,1,'2025-12-27 21:15:13'),(34,3,1,'2025-12-27 21:15:13'),(35,4,1,'2025-12-27 21:15:13'),(36,1,2,'2025-12-27 21:15:13'),(37,5,2,'2025-12-27 21:15:13'),(38,1,3,'2025-12-27 21:15:13'),(39,2,3,'2025-12-27 21:15:13'),(40,4,3,'2025-12-27 21:15:13'),(41,2,4,'2025-12-27 21:15:13'),(42,5,4,'2025-12-27 21:15:13'),(43,1,5,'2025-12-27 21:15:13'),(44,2,5,'2025-12-27 21:15:13'),(45,3,5,'2025-12-27 21:15:13'),(46,1,6,'2025-12-27 21:15:13'),(47,2,6,'2025-12-27 21:15:13'),(48,4,6,'2025-12-27 21:15:13'),(49,3,7,'2025-12-27 21:15:13'),(50,4,7,'2025-12-27 21:15:13'),(51,2,8,'2025-12-27 21:15:13'),(52,4,8,'2025-12-27 21:15:13'),(53,5,8,'2025-12-27 21:15:13'),(54,3,9,'2025-12-27 21:15:13'),(55,4,9,'2025-12-27 21:15:13'),(56,1,10,'2025-12-27 21:15:13'),(57,3,10,'2025-12-27 21:15:13'),(58,5,10,'2025-12-27 21:15:13');
/*!40000 ALTER TABLE `likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `post_hashtags`
--

LOCK TABLES `post_hashtags` WRITE;
/*!40000 ALTER TABLE `post_hashtags` DISABLE KEYS */;
INSERT INTO `post_hashtags` VALUES (1,1),(1,2),(10,2),(3,3),(4,4),(5,5),(6,6),(8,7),(9,8),(2,10),(7,11),(10,11),(2,12),(6,13),(9,13),(4,14);
/*!40000 ALTER TABLE `post_hashtags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,1,'Vize haftası başladı, bol şans herkesee','2025-12-27 21:10:12','2025-12-27 21:10:12'),(2,2,'Kampüs kafede yeni açılan mekan çok güzelmiş koşunn','2025-12-27 21:10:12','2025-12-27 21:10:12'),(3,3,'Bugün de ekran süremiz 25 saat','2025-12-27 21:10:12','2025-12-27 21:10:12'),(4,1,'Java lab dersi 3 saat sürdü kafam karıştı ya ?','2025-12-27 21:10:12','2025-12-27 21:10:12'),(5,4,'Yeni kitap önerisi: 1984 - George Orwell. Gerçekten herkese tavsiye ederim -niş bi yazar','2025-12-27 21:10:12','2025-12-27 21:10:12'),(6,5,'Kütüphanede ders çalışıyorum ama uyku basıyor ?','2025-12-27 21:10:12','2025-12-27 21:10:12'),(7,2,'Finaller geldi ve yine bi anda dizi izleyesim geldi...','2025-12-27 21:10:12','2025-12-27 21:10:12'),(8,3,'101 gelen var mı?','2025-12-27 21:10:12','2025-12-27 21:10:12'),(9,1,'Proje teslimi yarın ve daha başlamadım ?','2025-12-27 21:10:12','2025-12-27 21:10:12'),(10,2,'Final haftasına 2 hafta bile kalmadı panik modundayımmm','2025-12-27 21:10:12','2025-12-27 21:10:12'),(11,1,'Vize haftası başladı, bol şans herkesee','2025-12-27 21:15:13','2025-12-27 21:15:13'),(12,2,'Kampüs kafede yeni açılan mekan çok güzelmiş koşunn','2025-12-27 21:15:13','2025-12-27 21:15:13'),(13,3,'Bugün de ekran süremiz 25 saat','2025-12-27 21:15:13','2025-12-27 21:15:13'),(14,1,'Java lab dersi 3 saat sürdü kafam karıştı ya ?','2025-12-27 21:15:13','2025-12-27 21:15:13'),(15,4,'Yeni kitap önerisi: 1984 - George Orwell. Gerçekten herkese tavsiye ederim -niş bi yazar','2025-12-27 21:15:13','2025-12-27 21:15:13'),(16,5,'Kütüphanede ders çalışıyorum ama uyku basıyor ?','2025-12-27 21:15:13','2025-12-27 21:15:13'),(17,2,'Finaller geldi ve yine bi anda dizi izleyesim geldi...','2025-12-27 21:15:13','2025-12-27 21:15:13'),(18,3,'101 gelen var mı?','2025-12-27 21:15:13','2025-12-27 21:15:13'),(19,1,'Proje teslimi yarın ve daha başlamadım ?','2025-12-27 21:15:13','2025-12-27 21:15:13'),(20,2,'Final haftasına 2 hafta bile kalmadı panik modundayımmm','2025-12-27 21:15:13','2025-12-27 21:15:13');
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'emre.kaya','emre.kaya@gmail.com','e10adc3949ba59abbe56e057f20f883e','Emre Kaya','Bilgisayar mühendisliği öğrencisi',NULL,'2025-12-27 21:10:12'),(2,'ayse_demir','aysedemir@hotmail.com','5f4dcc3b5aa765d61d8327deb882cf99','Ayşe Demir','Kahve bağımlısı, dizi izlemeyi seviyorum',NULL,'2025-12-27 21:10:12'),(3,'can.ozturk','canozturk99@outlook.com','098f6bcd4621d373cade4e832627b4f6','Can Öztürk','Futbol ve oyun severim',NULL,'2025-12-27 21:10:12'),(4,'elif_yilmaz','elifylmz@gmail.com','482c811da5d5b4bc6d497ffa98491e38','Elif Yılmaz','Kitap kurdu ?',NULL,'2025-12-27 21:10:12'),(5,'burak123','burak.arslan@gmail.com','5ebe2294ecd0e0f08eab7690d2a6ee69','Burak Arslan',NULL,NULL,'2025-12-27 21:10:12');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-28  0:34:23
