-- MySQL dump 10.13  Distrib 8.0.26, for Linux (x86_64)
--
-- Host: localhost    Database: qwertdb
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(45) NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'?숇Ъ'),(2,'?щЪ'),(3,'?띻꼍'),(4,'?몃Ъ'),(5,'嫄대Ъ'),(6,'?앸Ъ'),(7,'罹먮┃??),(8,'湲고?');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(128) NOT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL,
  `docent_flag` bit(1) DEFAULT NULL,
  `user_id` int NOT NULL,
  `posting_id` int NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `FK_comment_posting` (`posting_id`),
  KEY `FK_comment_user` (`user_id`),
  CONSTRAINT `FK_comment_posting` FOREIGN KEY (`posting_id`) REFERENCES `posting` (`posting_id`),
  CONSTRAINT `FK_comment_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,'媛꾩젅?섍쾶 湲곕룄?대큶?덈떎?솋','2021-08-18 04:22:08',NULL,_binary '\0',4,3),(2,'?곕걟?곕걟??寃?媛숈븘??,'2021-08-18 04:27:19',NULL,_binary '\0',4,6),(3,'?쒖젙???됰났??蹂댁씠?쒕꽕??','2021-08-18 04:38:38',NULL,_binary '\0',2,8),(4,'?뺣쭚 醫뗫꽕??!','2021-08-18 05:26:00',NULL,_binary '\0',7,14),(5,'?낆옣?⑸땲??:)','2021-08-18 05:32:04',NULL,_binary '\0',4,16),(6,'?묓겕?ㅻ━??紐살갭吏!','2021-08-18 05:38:51',NULL,_binary '\0',4,7),(7,'?닿렐?섍퀬 ?띠?留??닿렐?????녿뒗 留덉쓬???댁? ?묓뭹?멸???','2021-08-18 05:39:59',NULL,_binary '\0',4,10),(8,'?뚮줉?щ줉 怨좎뼇???덈Т 洹?ъ썙??!','2021-08-18 06:20:42','2021-08-18 15:21:13',_binary '\0',4,13);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curation`
--

DROP TABLE IF EXISTS `curation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `curation` (
  `curation_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(20) NOT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `content` varchar(100) NOT NULL,
  `thumbnail_img` varchar(256) DEFAULT NULL,
  `color` varchar(45) DEFAULT 'ffffff',
  `user_id` int NOT NULL,
  PRIMARY KEY (`curation_id`),
  KEY `FK_curation_user` (`user_id`),
  CONSTRAINT `FK_curation_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curation`
--

LOCK TABLES `curation` WRITE;
/*!40000 ALTER TABLE `curation` DISABLE KEYS */;
INSERT INTO `curation` VALUES (1,'怨좎뼇???꾩떆??,'2021-08-18 04:24:56','怨좎뼇??洹몃┝ 而щ젆?섏엯?덈떎.',NULL,'',2),(2,'洹?ъ슫 寃?,'2021-08-18 04:26:47','洹?ъ슫 寃껋? 紐살갭吏',NULL,'cfb997',4),(3,'?숇Ъ 洹몃┝??,'2021-08-18 04:55:37','?숇Ъ 洹몃┝ 而щ젆???낅땲??',NULL,'',3),(4,'?띻꼍??紐⑥쓬','2021-08-18 05:19:06','?띻꼍洹몃┝ 而щ젆?섏엯?덈떎.',NULL,'',3);
/*!40000 ALTER TABLE `curation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curation_has_posting`
--

DROP TABLE IF EXISTS `curation_has_posting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `curation_has_posting` (
  `id` int NOT NULL AUTO_INCREMENT,
  `curation_id` int NOT NULL,
  `posting_id` int NOT NULL,
  `curate_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_curation_has_posting_curation` (`curation_id`),
  KEY `FK_curation_has_posting_posting` (`posting_id`),
  CONSTRAINT `FK_curation_has_posting_curation` FOREIGN KEY (`curation_id`) REFERENCES `curation` (`curation_id`),
  CONSTRAINT `FK_curation_has_posting_posting` FOREIGN KEY (`posting_id`) REFERENCES `posting` (`posting_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curation_has_posting`
--

LOCK TABLES `curation_has_posting` WRITE;
/*!40000 ALTER TABLE `curation_has_posting` DISABLE KEYS */;
INSERT INTO `curation_has_posting` VALUES (1,1,6,'2021-08-18 04:25:13'),(2,2,1,'2021-08-18 04:26:59'),(3,1,7,'2021-08-18 04:35:09'),(4,1,9,'2021-08-18 04:36:04'),(5,3,1,'2021-08-18 04:55:50'),(6,3,6,'2021-08-18 04:56:04'),(7,3,11,'2021-08-18 04:56:11'),(8,3,12,'2021-08-18 04:56:23'),(9,4,14,'2021-08-18 05:19:29'),(10,4,2,'2021-08-18 05:19:54'),(11,4,16,'2021-08-18 05:32:17'),(12,4,15,'2021-08-18 05:33:34'),(13,2,17,'2021-08-18 06:07:29'),(14,2,9,'2021-08-18 06:07:33'),(15,2,7,'2021-08-18 06:07:39'),(16,2,11,'2021-08-18 06:07:50'),(17,2,12,'2021-08-18 06:07:59');
/*!40000 ALTER TABLE `curation_has_posting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feed`
--

DROP TABLE IF EXISTS `feed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feed` (
  `feed_id` int NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `user_id` int NOT NULL,
  `posting_id` int NOT NULL,
  PRIMARY KEY (`feed_id`),
  KEY `FK_feed_user` (`user_id`),
  KEY `FK_feed_posting` (`posting_id`),
  CONSTRAINT `FK_feed_posting` FOREIGN KEY (`posting_id`) REFERENCES `posting` (`posting_id`),
  CONSTRAINT `FK_feed_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feed`
--

LOCK TABLES `feed` WRITE;
/*!40000 ALTER TABLE `feed` DISABLE KEYS */;
INSERT INTO `feed` VALUES (1,'2021-08-18 04:18:39',4,1),(2,'2021-08-18 04:20:46',4,2),(3,'2021-08-18 04:20:48',5,3),(4,'2021-08-18 04:21:16',4,4),(6,'2021-08-18 04:23:09',2,6),(7,'2021-08-18 04:35:02',2,7),(8,'2021-08-18 04:35:02',4,7),(9,'2021-08-18 04:35:39',5,8),(10,'2021-08-18 04:35:39',4,8),(11,'2021-08-18 04:35:39',2,8),(12,'2021-08-18 04:35:59',2,9),(13,'2021-08-18 04:36:00',4,9),(14,'2021-08-18 04:50:23',3,10),(15,'2021-08-18 04:51:09',3,11),(16,'2021-08-18 04:52:38',3,12),(17,'2021-08-18 04:54:53',3,13),(18,'2021-08-18 05:14:46',4,14),(19,'2021-08-18 05:14:46',5,14),(20,'2021-08-18 05:14:46',2,14),(21,'2021-08-18 05:14:46',3,14),(22,'2021-08-18 05:23:39',2,15),(23,'2021-08-18 05:23:39',4,15),(24,'2021-08-18 05:23:39',3,15),(25,'2021-08-18 05:23:39',5,15),(26,'2021-08-18 05:31:09',2,16),(27,'2021-08-18 05:31:09',4,16),(28,'2021-08-18 05:31:09',3,16),(29,'2021-08-18 05:31:09',5,16),(30,'2021-08-18 05:37:59',4,17),(31,'2021-08-18 05:37:59',5,17),(32,'2021-08-18 05:37:59',2,17),(33,'2021-08-18 05:37:59',3,17),(34,'2021-08-18 06:18:11',4,18),(35,'2021-08-18 06:18:11',5,18),(36,'2021-08-18 06:18:11',2,18),(37,'2021-08-18 06:18:11',3,18);
/*!40000 ALTER TABLE `feed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follow`
--

DROP TABLE IF EXISTS `follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `follow` (
  `follow_id` int NOT NULL AUTO_INCREMENT,
  `from_user_id` int NOT NULL,
  `to_user_id` int NOT NULL,
  PRIMARY KEY (`follow_id`),
  KEY `FK_follow_from_user` (`from_user_id`),
  KEY `FK_follow_to_user` (`to_user_id`),
  CONSTRAINT `FK_follow_from_user` FOREIGN KEY (`from_user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK_follow_to_user` FOREIGN KEY (`to_user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow`
--

LOCK TABLES `follow` WRITE;
/*!40000 ALTER TABLE `follow` DISABLE KEYS */;
INSERT INTO `follow` VALUES (1,5,4),(2,4,5),(3,2,4),(4,2,5),(5,4,2),(6,2,6),(7,3,2),(8,5,2),(9,3,5),(10,3,4),(11,3,6),(12,2,3),(13,4,3);
/*!40000 ALTER TABLE `follow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `like`
--

DROP TABLE IF EXISTS `like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `like` (
  `like_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `posting_id` int NOT NULL,
  `uploader_id` int NOT NULL,
  PRIMARY KEY (`like_id`),
  KEY `FK_like_user` (`user_id`),
  KEY `FK_like_posting` (`posting_id`),
  CONSTRAINT `FK_like_posting` FOREIGN KEY (`posting_id`) REFERENCES `posting` (`posting_id`),
  CONSTRAINT `FK_like_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `like`
--

LOCK TABLES `like` WRITE;
/*!40000 ALTER TABLE `like` DISABLE KEYS */;
INSERT INTO `like` VALUES (1,5,1,4),(2,4,3,5),(3,2,6,2),(4,2,4,4),(5,4,6,2),(7,2,8,5),(8,5,9,2),(10,3,6,2),(11,3,2,4),(12,4,9,2),(13,3,14,4),(14,4,14,4),(15,3,15,2);
/*!40000 ALTER TABLE `like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posting`
--

DROP TABLE IF EXISTS `posting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posting` (
  `posting_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(128) NOT NULL,
  `content` varchar(1000) NOT NULL,
  `posting_img` varchar(256) NOT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL,
  `masterpiece_flag` bit(1) DEFAULT NULL,
  `user_id` int NOT NULL,
  `category_id` int NOT NULL,
  `like_cnt` int DEFAULT NULL,
  PRIMARY KEY (`posting_id`),
  KEY `FK_posting_user` (`user_id`),
  KEY `FK_posting_category` (`category_id`),
  CONSTRAINT `FK_posting_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
  CONSTRAINT `FK_posting_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posting`
--

LOCK TABLES `posting` WRITE;
/*!40000 ALTER TABLE `posting` DISABLE KEYS */;
INSERT INTO `posting` VALUES (1,'?덈굹?곗?','洹 已묎툔','8925170.jpg','2021-08-18 04:18:39',NULL,_binary '',4,1,1),(2,'諛ㅽ븯??,'諛섏쭩諛섏쭩狩먳윁?,'3500881.jpg','2021-08-18 04:20:46',NULL,_binary '',4,3,1),(3,'cosmos','one of cosmos','4327123.jpg','2021-08-18 04:20:48',NULL,_binary '\0',5,8,1),(4,'?뚮굹臾?,'?섎Т?섎Т ?뚮굹臾댑윁?,'1514084.jpg','2021-08-18 04:21:16',NULL,_binary '\0',4,6,1),(6,'?몃춬移?,'?앸뭇 ?먯꽭??怨좎뼇??洹몃┝?낅땲??','8935267.jpg','2021-08-18 04:23:09',NULL,_binary '',2,1,3),(7,'諛쒕컮??,'?ㅻ━媛숈? 怨좎뼇??諛쒕컮??','68986.jpg','2021-08-18 04:35:02',NULL,_binary '',2,1,0),(8,'?쒖＜而ㅽ뵾','?諛뺣굹?몄슜','9345167.jpg','2021-08-18 04:35:39','2021-08-18 13:47:13',_binary '\0',5,3,1),(9,'?덉튂蹂대뒗 怨좎뼇??,'?ш퀬移섍퀬 吏묒궗???덉튂瑜?蹂대뒗 ?μ씠','8366076.jpg','2021-08-18 04:35:59',NULL,_binary '',2,1,2),(10,'?껋쓬','??.','9591729.jpg','2021-08-18 04:50:23',NULL,_binary '\0',3,4,0),(11,'異섏떇??,'異섏떇??留욎븘??','4730689.jpg','2021-08-18 04:51:09',NULL,_binary '',3,1,0),(12,'?λえ?섏씠??,'?좎옄??怨좎뼇??洹몃┝?낅땲??','3518216.jpg','2021-08-18 04:52:38',NULL,_binary '',3,1,0),(13,'?꾧린 怨좎뼇??,'.','249544.jpg','2021-08-18 04:54:53',NULL,_binary '\0',3,1,0),(14,'?쒖＜??移댄럹','?쒖＜??媛怨좎떢?ㅼ븘','8565450.jpg','2021-08-18 05:14:46',NULL,_binary '',4,3,2),(15,'?곗＜???좊퉬','愿묓솢???곗＜','1179524.jpg','2021-08-18 05:23:39',NULL,_binary '\0',2,3,1),(16,'泥쒖?','諛깅몢??泥쒖??낅땲??','7407755.jpg','2021-08-18 05:31:09',NULL,_binary '\0',2,3,0),(17,'?ㅺ뎄','?ㅻ━?덇뎄由??ㅺ뎄','4932292.jpg','2021-08-18 05:37:59',NULL,_binary '\0',4,7,0),(18,'吏援?,'EARTH','2846290.jpg','2021-08-18 06:18:11',NULL,_binary '\0',4,3,0);
/*!40000 ALTER TABLE `posting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
  `nickname` varchar(128) NOT NULL,
  `introduction` varchar(1000) DEFAULT NULL,
  `profile_img` varchar(256) DEFAULT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `popularity` int DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_idx_unique_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'seung9412@naver.com','qwert1234','helloworld',NULL,NULL,'2021-08-18 02:33:33',0),(2,'lim8662@gmail.com','123123ss','怨좎뼇??,'怨좎뼇??洹몃┝ 醫뗭븘?⑸땲??','https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/3849330.jpg','2021-08-18 02:39:36',3),(3,'ssafy@naver.com','ssafy123','ssafy','?덈뀞?섏꽭??','https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/7484072.jpg','2021-08-18 03:59:21',2),(4,'msh9512@naver.com','moon1504','OiHater','?ㅼ씠???뺣쭚 ?レ뼱??:(','https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/6356111.jpg','2021-08-18 04:15:27',3),(5,'seung@naver.com','qwer1234','?쒕쾭遺?붿옉?숉빐',NULL,NULL,'2021-08-18 04:19:46',3),(6,'heejun2822@hanmail.net','asdf1234','?댄씗以',NULL,NULL,'2021-08-18 04:32:57',2),(7,'se@naver.com','qwert123','se',NULL,NULL,'2021-08-18 05:24:05',0),(8,'abc123123@naver.com','abc123123','abcabc',NULL,NULL,'2021-08-18 05:58:20',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-18  6:32:37
