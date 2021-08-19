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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,'媛꾩젅?섍쾶 湲곕룄?대큶?덈떎?솋','2021-08-18 04:22:08',NULL,_binary '\0',4,3),(2,'?곕걟?곕걟??寃?媛숈븘??,'2021-08-18 04:27:19',NULL,_binary '\0',4,6),(3,'?쒖젙???됰났??蹂댁씠?쒕꽕??','2021-08-18 04:38:38',NULL,_binary '\0',2,8),(4,'?뺣쭚 醫뗫꽕??!','2021-08-18 05:26:00',NULL,_binary '\0',7,14),(5,'?낆옣?⑸땲??:)','2021-08-18 05:32:04',NULL,_binary '\0',4,16),(6,'?묓겕?ㅻ━??紐살갭吏!','2021-08-18 05:38:51',NULL,_binary '\0',4,7),(7,'?닿렐?섍퀬 ?띠?留??닿렐?????녿뒗 留덉쓬???댁? ?묓뭹?멸???','2021-08-18 05:39:59',NULL,_binary '\0',4,10),(8,'?뚮줉?щ줉 怨좎뼇???덈Т 洹?ъ썙??!','2021-08-18 06:20:42','2021-08-18 15:21:13',_binary '\0',4,13),(9,'媛먯궗?⑸땲??:)','2021-08-19 01:10:26',NULL,_binary '\0',4,14),(10,'?쒖＜??媛怨좎떢?댁??ㅼ슂','2021-08-19 01:11:11',NULL,_binary '\0',4,8),(11,'?섍렇由ъ뀲?ㅼ슂 :)','2021-08-19 01:12:07',NULL,_binary '\0',4,15),(14,'?몄긽?곸씤 ?섏씠?쇰━?쇰━利??묓뭹?대꽕?? ?숈뿽???쒕씪?댄븿???앹깮???먭뺨吏묐땲??','2021-08-19 02:42:26',NULL,_binary '',2,30),(15,'? 吏꾩쭨 媛숈븘?? 踰쎌뿉 遺숈씤 遺遺꾩씠 ??蹂댁??ㅻ㈃ ?ъ쭊??以??뚭쿋?댁슂.','2021-08-19 03:01:53',NULL,_binary '\0',3,30),(16,'? ?뗣뀑?뗣뀑 ?섎춪媛吏 二쇱썙?ㅼ떊以??뚯븯?댁슂','2021-08-19 04:18:10',NULL,_binary '\0',4,30),(17,'?숈뿽!!!','2021-08-19 05:55:48',NULL,_binary '\0',3,30),(18,'遊먮룄遊먮룄 ?덉쭏由щ꽕???닿굅','2021-08-19 06:23:29',NULL,_binary '\0',4,30),(19,'湲덉넀?댁떗?덈떎','2021-08-19 06:23:59',NULL,_binary '\0',4,30),(20,'?섎Т?좊쭑怨??숈뿽??寃곗쓣 嫄곗튌寃??쒗쁽?섏뿬 ?쒖링 ??留덈Ⅸ ?먮굦??以띾땲??','2021-08-19 08:32:41',NULL,_binary '',11,30),(21,'?섎Т?좊쭑 洹몃┝??紐낆븫??源딆씠瑜??ㅻⅤ寃??쒗쁽?섏뿬 遺?쇨컧?????쒗쁽?섏뀲?듬땲??','2021-08-19 08:33:17',NULL,_binary '',11,30);
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curation`
--

LOCK TABLES `curation` WRITE;
/*!40000 ALTER TABLE `curation` DISABLE KEYS */;
INSERT INTO `curation` VALUES (1,'怨좎뼇???꾩떆??,'2021-08-18 04:24:56','怨좎뼇??洹몃┝ 而щ젆?섏엯?덈떎.','https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/3797645.jpg','b7cdaa',2),(2,'洹?ъ슫 寃?,'2021-08-18 04:26:47','洹?ъ슫 寃껋? 紐살갭吏','https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/727236.jpg','d48a6a',4),(3,'?숇Ъ 洹몃┝??,'2021-08-18 04:55:37','?숇Ъ 洹몃┝ 而щ젆???낅땲??',NULL,'',3),(5,'?섏갖怨?洹?ъ?','2021-08-19 04:40:17','?섏갖吏留?洹?ъ슫 寃껊뱾',NULL,'c9e967',6),(6,'?띻꼍??,'2021-08-19 04:40:59','?쒖썝???띻꼍??而щ젆??,NULL,'',6),(7,'???몄깮 ??옉','2021-08-19 04:42:30','?먯떊?덇쾶 ?대넃?????덈뒗 ?묓뭹??^^',NULL,'',6),(8,'?띻꼍??紐⑥쓬','2021-08-19 06:00:48','硫뗭엳???띻꼍??紐⑥쓬 ?먮젅?댁뀡?낅땲??',NULL,'25d6ca',3),(9,'?묐갚?ъ쭊??,'2021-08-19 08:13:22','洹??대뼡 洹몃┝蹂대떎 ?붾젮???묐갚?ъ쭊??,'https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/9884236.jpg','',3);
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
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curation_has_posting`
--

LOCK TABLES `curation_has_posting` WRITE;
/*!40000 ALTER TABLE `curation_has_posting` DISABLE KEYS */;
INSERT INTO `curation_has_posting` VALUES (1,1,6,'2021-08-18 04:25:13'),(2,2,1,'2021-08-18 04:26:59'),(3,1,7,'2021-08-18 04:35:09'),(4,1,9,'2021-08-18 04:36:04'),(5,3,1,'2021-08-18 04:55:50'),(6,3,6,'2021-08-18 04:56:04'),(7,3,11,'2021-08-18 04:56:11'),(8,3,12,'2021-08-18 04:56:23'),(13,2,17,'2021-08-18 06:07:29'),(14,2,9,'2021-08-18 06:07:33'),(15,2,7,'2021-08-18 06:07:39'),(16,2,11,'2021-08-18 06:07:50'),(17,2,12,'2021-08-18 06:07:59'),(19,3,17,'2021-08-18 06:39:43'),(20,1,11,'2021-08-19 01:38:21'),(21,1,13,'2021-08-19 01:38:58'),(22,2,6,'2021-08-19 01:41:38'),(23,3,32,'2021-08-19 03:04:59'),(26,5,7,'2021-08-19 04:42:52'),(27,5,17,'2021-08-19 04:43:05'),(28,5,11,'2021-08-19 04:43:24'),(29,5,10,'2021-08-19 04:43:34'),(30,5,9,'2021-08-19 04:43:44'),(31,5,1,'2021-08-19 04:43:53'),(32,5,6,'2021-08-19 04:44:18'),(33,6,31,'2021-08-19 04:44:39'),(34,6,27,'2021-08-19 04:45:00'),(35,6,16,'2021-08-19 04:45:13'),(36,6,14,'2021-08-19 04:45:25'),(37,7,26,'2021-08-19 04:45:51'),(39,7,37,'2021-08-19 04:46:19'),(40,7,31,'2021-08-19 04:46:30'),(41,7,30,'2021-08-19 04:46:40'),(42,7,34,'2021-08-19 04:47:04'),(43,7,38,'2021-08-19 04:47:15'),(45,1,44,'2021-08-19 06:16:50'),(48,1,12,'2021-08-19 06:29:23'),(49,7,53,'2021-08-19 08:00:16'),(50,1,55,'2021-08-19 08:02:06'),(51,8,50,'2021-08-19 08:26:50'),(52,8,31,'2021-08-19 08:27:07'),(53,8,48,'2021-08-19 08:27:28'),(54,8,27,'2021-08-19 08:27:41'),(57,8,30,'2021-08-19 09:06:07');
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
) ENGINE=InnoDB AUTO_INCREMENT=197 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feed`
--

LOCK TABLES `feed` WRITE;
/*!40000 ALTER TABLE `feed` DISABLE KEYS */;
INSERT INTO `feed` VALUES (1,'2021-08-18 04:18:39',4,1),(2,'2021-08-18 04:20:46',4,2),(3,'2021-08-18 04:20:48',5,3),(4,'2021-08-18 04:21:16',4,4),(6,'2021-08-18 04:23:09',2,6),(7,'2021-08-18 04:35:02',2,7),(8,'2021-08-18 04:35:02',4,7),(9,'2021-08-18 04:35:39',5,8),(10,'2021-08-18 04:35:39',4,8),(11,'2021-08-18 04:35:39',2,8),(12,'2021-08-18 04:35:59',2,9),(13,'2021-08-18 04:36:00',4,9),(14,'2021-08-18 04:50:23',3,10),(15,'2021-08-18 04:51:09',3,11),(16,'2021-08-18 04:52:38',3,12),(17,'2021-08-18 04:54:53',3,13),(18,'2021-08-18 05:14:46',4,14),(19,'2021-08-18 05:14:46',5,14),(20,'2021-08-18 05:14:46',2,14),(21,'2021-08-18 05:14:46',3,14),(22,'2021-08-18 05:23:39',2,15),(23,'2021-08-18 05:23:39',4,15),(24,'2021-08-18 05:23:39',3,15),(25,'2021-08-18 05:23:39',5,15),(26,'2021-08-18 05:31:09',2,16),(27,'2021-08-18 05:31:09',4,16),(28,'2021-08-18 05:31:09',3,16),(29,'2021-08-18 05:31:09',5,16),(30,'2021-08-18 05:37:59',4,17),(31,'2021-08-18 05:37:59',5,17),(32,'2021-08-18 05:37:59',2,17),(33,'2021-08-18 05:37:59',3,17),(34,'2021-08-18 06:18:11',4,18),(35,'2021-08-18 06:18:11',5,18),(36,'2021-08-18 06:18:11',2,18),(37,'2021-08-18 06:18:11',3,18),(46,'2021-08-19 01:51:21',6,21),(47,'2021-08-19 01:51:21',2,21),(48,'2021-08-19 01:51:21',3,21),(49,'2021-08-19 01:52:51',6,22),(50,'2021-08-19 01:52:51',2,22),(51,'2021-08-19 01:52:51',3,22),(55,'2021-08-19 01:54:58',6,24),(56,'2021-08-19 01:54:58',2,24),(57,'2021-08-19 01:54:58',3,24),(58,'2021-08-19 01:57:25',6,25),(59,'2021-08-19 01:57:25',2,25),(60,'2021-08-19 01:57:25',3,25),(61,'2021-08-19 01:58:46',6,26),(62,'2021-08-19 01:58:46',2,26),(63,'2021-08-19 01:58:46',3,26),(64,'2021-08-19 02:01:23',6,27),(65,'2021-08-19 02:01:23',2,27),(66,'2021-08-19 02:01:23',3,27),(67,'2021-08-19 02:06:13',6,28),(68,'2021-08-19 02:06:13',2,28),(69,'2021-08-19 02:06:13',3,28),(73,'2021-08-19 02:11:33',6,30),(74,'2021-08-19 02:11:33',2,30),(75,'2021-08-19 02:11:33',3,30),(76,'2021-08-19 02:14:01',6,31),(77,'2021-08-19 02:14:01',2,31),(78,'2021-08-19 02:14:01',3,31),(79,'2021-08-19 02:15:11',6,32),(80,'2021-08-19 02:15:11',2,32),(81,'2021-08-19 02:15:11',3,32),(82,'2021-08-19 02:18:45',6,33),(83,'2021-08-19 02:18:45',2,33),(84,'2021-08-19 02:18:45',3,33),(85,'2021-08-19 02:23:05',6,34),(86,'2021-08-19 02:23:05',2,34),(87,'2021-08-19 02:23:05',3,34),(88,'2021-08-19 02:26:19',6,35),(89,'2021-08-19 02:26:19',2,35),(90,'2021-08-19 02:26:19',3,35),(91,'2021-08-19 02:28:51',6,36),(92,'2021-08-19 02:28:51',2,36),(93,'2021-08-19 02:28:51',3,36),(94,'2021-08-19 02:32:16',6,37),(95,'2021-08-19 02:32:16',2,37),(96,'2021-08-19 02:32:16',3,37),(97,'2021-08-19 02:36:03',6,38),(98,'2021-08-19 02:36:03',2,38),(99,'2021-08-19 02:36:03',3,38),(100,'2021-08-19 02:41:00',6,39),(101,'2021-08-19 02:41:00',2,39),(102,'2021-08-19 02:41:00',3,39),(103,'2021-08-19 03:03:14',3,40),(104,'2021-08-19 03:03:14',2,40),(105,'2021-08-19 03:03:14',4,40),(106,'2021-08-19 04:54:29',4,41),(107,'2021-08-19 04:54:29',5,41),(108,'2021-08-19 04:54:29',2,41),(109,'2021-08-19 04:54:29',3,41),(110,'2021-08-19 04:54:29',10,41),(111,'2021-08-19 04:54:29',6,41),(124,'2021-08-19 05:15:57',4,44),(125,'2021-08-19 05:15:57',5,44),(126,'2021-08-19 05:15:57',2,44),(127,'2021-08-19 05:15:57',3,44),(128,'2021-08-19 05:15:57',10,44),(129,'2021-08-19 05:15:57',6,44),(130,'2021-08-19 05:49:53',3,45),(131,'2021-08-19 05:49:53',2,45),(132,'2021-08-19 05:49:53',4,45),(133,'2021-08-19 05:49:53',6,45),(134,'2021-08-19 06:08:09',10,46),(135,'2021-08-19 07:37:56',3,47),(136,'2021-08-19 07:37:56',2,47),(137,'2021-08-19 07:37:56',4,47),(138,'2021-08-19 07:37:56',6,47),(139,'2021-08-19 07:53:01',3,48),(140,'2021-08-19 07:53:01',2,48),(141,'2021-08-19 07:53:01',4,48),(142,'2021-08-19 07:53:01',6,48),(147,'2021-08-19 07:53:31',3,50),(148,'2021-08-19 07:53:31',2,50),(149,'2021-08-19 07:53:31',4,50),(150,'2021-08-19 07:53:31',6,50),(151,'2021-08-19 07:54:13',3,51),(152,'2021-08-19 07:54:13',2,51),(153,'2021-08-19 07:54:13',4,51),(154,'2021-08-19 07:54:13',6,51),(161,'2021-08-19 08:00:06',6,53),(162,'2021-08-19 08:00:06',2,53),(163,'2021-08-19 08:00:06',3,53),(164,'2021-08-19 08:00:06',4,53),(165,'2021-08-19 08:01:16',6,54),(166,'2021-08-19 08:01:16',2,54),(167,'2021-08-19 08:01:16',3,54),(168,'2021-08-19 08:01:16',4,54),(169,'2021-08-19 08:02:01',2,55),(170,'2021-08-19 08:02:01',4,55),(171,'2021-08-19 08:02:01',3,55),(172,'2021-08-19 08:02:01',5,55),(173,'2021-08-19 08:02:01',10,55),(174,'2021-08-19 08:02:01',6,55),(175,'2021-08-19 08:02:17',4,56),(176,'2021-08-19 08:02:17',5,56),(177,'2021-08-19 08:02:17',2,56),(178,'2021-08-19 08:02:17',3,56),(179,'2021-08-19 08:02:17',10,56),(180,'2021-08-19 08:02:17',6,56),(181,'2021-08-19 08:14:01',4,57),(182,'2021-08-19 08:14:01',5,57),(183,'2021-08-19 08:14:01',2,57),(184,'2021-08-19 08:14:01',3,57),(185,'2021-08-19 08:14:01',10,57),(186,'2021-08-19 08:14:01',6,57),(187,'2021-08-19 08:18:41',2,58),(188,'2021-08-19 08:18:41',4,58),(189,'2021-08-19 08:18:41',3,58),(190,'2021-08-19 08:18:41',5,58),(191,'2021-08-19 08:18:41',10,58),(192,'2021-08-19 08:18:41',6,58),(193,'2021-08-19 09:04:58',3,59),(194,'2021-08-19 09:04:58',2,59),(195,'2021-08-19 09:04:58',4,59),(196,'2021-08-19 09:04:58',6,59);
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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow`
--

LOCK TABLES `follow` WRITE;
/*!40000 ALTER TABLE `follow` DISABLE KEYS */;
INSERT INTO `follow` VALUES (1,5,4),(2,4,5),(3,2,4),(4,2,5),(5,4,2),(6,2,6),(7,3,2),(8,5,2),(9,3,5),(10,3,4),(11,3,6),(12,2,3),(13,4,3),(14,3,7),(15,10,4),(16,10,2),(17,6,2),(18,6,4),(19,6,3),(20,6,5),(21,4,6),(22,2,10);
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
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `like`
--

LOCK TABLES `like` WRITE;
/*!40000 ALTER TABLE `like` DISABLE KEYS */;
INSERT INTO `like` VALUES (1,5,1,4),(2,4,3,5),(3,2,6,2),(4,2,4,4),(7,2,8,5),(8,5,9,2),(10,3,6,2),(11,3,2,4),(12,4,9,2),(13,3,14,4),(14,4,14,4),(15,3,15,2),(17,3,17,4),(19,2,11,3),(21,4,6,2),(22,2,30,6),(23,6,14,4),(24,6,4,4),(25,6,8,5),(26,6,10,3),(27,6,16,2),(28,6,15,2),(29,6,18,4),(30,6,2,4),(32,3,32,6),(33,3,31,6),(34,4,30,6),(36,10,30,6),(38,6,1,4),(39,6,30,6),(41,2,16,2),(43,2,44,4),(44,2,12,3),(45,2,7,2),(46,4,58,2);
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
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posting`
--

LOCK TABLES `posting` WRITE;
/*!40000 ALTER TABLE `posting` DISABLE KEYS */;
INSERT INTO `posting` VALUES (1,'?덈굹?곗?','洹 已묎툔','8925170.jpg','2021-08-18 04:18:39',NULL,_binary '',4,1,2),(2,'諛ㅽ븯??,'諛섏쭩諛섏쭩狩먳윁?,'3500881.jpg','2021-08-18 04:20:46',NULL,_binary '\0',4,3,2),(3,'cosmos','one of cosmos','4327123.jpg','2021-08-18 04:20:48',NULL,_binary '\0',5,8,1),(4,'?뚮굹臾?,'?섎Т?섎Т ?뚮굹臾댑윁?,'1514084.jpg','2021-08-18 04:21:16',NULL,_binary '',4,6,2),(6,'?몃춬移?,'?앸뭇 ?먯꽭??怨좎뼇??洹몃┝?낅땲??','8935267.jpg','2021-08-18 04:23:09',NULL,_binary '',2,1,3),(7,'諛쒕컮??,'?ㅻ━媛숈? 怨좎뼇??諛쒕컮??','68986.jpg','2021-08-18 04:35:02',NULL,_binary '',2,1,1),(8,'?쒖＜而ㅽ뵾','?諛뺣굹?몄슜','9345167.jpg','2021-08-18 04:35:39','2021-08-18 13:47:13',_binary '\0',5,3,2),(9,'?덉튂蹂대뒗 怨좎뼇??,'?ш퀬移섍퀬 吏묒궗???덉튂瑜?蹂대뒗 ?μ씠','8366076.jpg','2021-08-18 04:35:59',NULL,_binary '\0',2,1,2),(10,'?껋쓬','??.','9591729.jpg','2021-08-18 04:50:23',NULL,_binary '\0',3,4,1),(11,'異섏떇??,'異섏떇??留욎븘??','4730689.jpg','2021-08-18 04:51:09',NULL,_binary '\0',3,1,1),(12,'?λえ?섏씠??,'?좎옄??怨좎뼇??洹몃┝?낅땲??','3518216.jpg','2021-08-18 04:52:38',NULL,_binary '\0',3,1,1),(13,'?꾧린 怨좎뼇??,'.','249544.jpg','2021-08-18 04:54:53',NULL,_binary '\0',3,1,0),(14,'?쒖＜??移댄럹','?쒖＜??媛怨좎떢?ㅼ븘','8565450.jpg','2021-08-18 05:14:46',NULL,_binary '',4,3,3),(15,'?곗＜???좊퉬','愿묓솢???곗＜','1179524.jpg','2021-08-18 05:23:39',NULL,_binary '\0',2,3,2),(16,'泥쒖?','諛깅몢??泥쒖??낅땲??','7407755.jpg','2021-08-18 05:31:09',NULL,_binary '\0',2,3,2),(17,'?ㅺ뎄','?ㅻ━?덇뎄由??ㅺ뎄','4932292.jpg','2021-08-18 05:37:59',NULL,_binary '\0',4,7,1),(18,'吏援?,'EARTH','2846290.jpg','2021-08-18 06:18:11',NULL,_binary '\0',4,3,1),(21,'怨??꾩뿉 ??,'怨??꾩뿉 ?먯쓣 ?щ━怨??덈뒗 ?ъ쫰???\n萸붽? ?댁깋?섍쾶 洹몃젮議뚮떎.','6285781.jpg','2021-08-19 01:51:21',NULL,_binary '\0',6,2,0),(22,'?낇겕由??ъ옄','?낇겕由ш퀬 ?덈뒗 紐⑤뜽??洹몃졇??\n?뚯뒪?붾줈 ?꾩껜?곸쑝濡??됱쓣 ??퀬 吏?곌컻濡??됱쓣 ?쒖뼱?섍???湲곕쾿?쇰줈 洹몃졇??','5381757.jpg','2021-08-19 01:52:51',NULL,_binary '\0',6,4,0),(24,'?щ챸??而?,'而듭씠 ?щ챸?댁꽌 洹몃━湲??섎뱾?덈떎.\n鍮쏆쓣 ?쒗쁽?섎뒗 寃??쎌? ?딅떎.','6198913.jpg','2021-08-19 01:54:58',NULL,_binary '\0',6,2,0),(25,'?섎뒛 ?꾩떆愿','肄쒕씪二?湲곕쾿?쇰줈 留뚮뱾?덈떎.\n?щ갑???섎뒛???꾩떆愿??湲고쉷?대뇬??\n泥쒖옣 議곕챸?????섍쾶 ?꾩껜?곸쑝濡?鍮쏅굹??議곕챸???ъ슜??理쒕????섎뒛???덈뒗 寃?媛숈? ?먮굦??二쇰젮怨??덈떎.','102404.jpg','2021-08-19 01:57:25',NULL,_binary '\0',6,8,0),(26,'?먰솕??,'?ㅼ떆媛꾩쑝濡?嫄곗슱??蹂대ŉ 洹몃졇??\n?쏅궇 珥덉긽?붾뱾??????臾댄몴?뺤씤吏 ?????덉뿀??\n洹몃━???꾩쨷???쒖젙??蹂?섍굅??怨좉컻 媛곷룄媛 ??댁?硫????섍린 ?뚮Ц???먯뿰?ㅻ젅 ???臾댄몴?뺤씠 ?섏삤寃??쒕떎.','3238444.jpg','2021-08-19 01:58:46',NULL,_binary '\0',6,4,0),(27,'寃⑥슱 ?몄쓣','寃⑥슱 ?대뒓???섏뾽 ?앸굹怨?嫄대Ъ?먯꽌 ?섏? 留덉＜???λ㈃?대떎.\n?닿? 吏硫??곕룄 ?섎Т????嫄곕찒寃?蹂댁뿬 洹몃━湲곕뒗 ?명뻽?? ??,'5872648.jpg','2021-08-19 02:01:23',NULL,_binary '\0',6,3,0),(28,'?띻꼍 異붿긽??,'?닿? ?앷컖?섎뒗 ?먯뿰??湲곕낯?됱? ?섎뒛?? ?곗깋, 珥덈줉?? 媛덉깋?대떎.\n?대? 諛뷀깢?쇰줈 ?띻꼍?붾? 異붿긽?곸쑝濡?洹몃젮遊ㅻ떎.','7650415.jpg','2021-08-19 02:06:13',NULL,_binary '\0',6,3,0),(30,'?숈뿽','?숈뿽??二쇱썙?ㅺ? 洹몃젮遊ㅻ떎.\n?먮옒 ?됰낫???꾩껜?곸쑝濡???吏꾪븯寃?洹몃젮吏湲??덉?留??섎쫫 留뚯”?쒕떎.\n?섎Т 猿띿쭏?.. 醫 ?꾩돺??','4541185.jpg','2021-08-19 02:11:33',NULL,_binary '\0',6,6,4),(31,'?묎끝','媛??怨듬뱾??洹몃┛ ?묓뭹??寃?媛숇떎.\n臾? 援щ쫫, 鍮쏆쿂???뺥깭媛 ?녿뒗 寃껊뱾???꾩쟾?대낫怨??띠뼱??洹몃━寃??섏뿀??','5837273.jpg','2021-08-19 02:14:01','2021-08-19 17:20:17',_binary '',6,3,1),(32,'硫띾찉??,'移쒓뎄??媛뺤븘吏??\n?숆탳?먯꽌 遺꾪븘濡??댁쭩 洹몃젮遊ㅻ떎.','4242258.jpg','2021-08-19 02:15:11',NULL,_binary '\0',6,1,1),(33,'?섏콈 ?됱뿰??,'移쒓뎄 洹몃┝??洹몃┫ 湲고쉶媛 ?앷꺼???섏콈 ?됱뿰?꾩쓣 ?ъ슜?대뇬??\n?댁쟾???됱뿰?꾨줈留??ъ슜???뚮뒗 紐곕옄?붾뜲 臾쇱쓣 ?욎쑝??諛쒖깋??苑?愿쒖갖??\n臾쇨컧???ъ슜?섎젮硫??명똿??洹李?퀬 ??留뚮뱾湲곕룄 ?대젮?대뜲 ?섏콈 ?됱뿰?꾨줈 媛꾨떒?섍쾶 ?섏콈???먮굦??以????덉뼱??醫뗫떎.','4891697.jpg','2021-08-19 02:18:45',NULL,_binary '\0',6,4,0),(34,'梨낃컝??,'梨낃컝?쇰? 留뚮뱾?ㅺ퀬 洹몃┛ 洹몃┝?대떎.\n?됱뿰?꾩씠 ?앷컖蹂대떎 ?됱씠 ?고빐??醫 ?뱁솴?ㅻ윭?좊떎.\n醫낆씠 臾몄젣?몄? ?됱뿰??臾몄젣?몄? ?닿? 臾몄젣?몄? ??n萸??고븳 ?먮굦???섏걯吏??딆쓣 寃?媛숇떎.','4490199.jpg','2021-08-19 02:23:05',NULL,_binary '\0',6,8,0),(35,'?뚯뒪???낅Ц','?뚯뒪?붿쓣 泥섏쓬 ?묓븷 ??洹몃졇??洹몃┝?댁뿀??寃껋씠??\n?먯쑝濡?臾몄?瑜대ŉ 洹몃━??寃??좉린?섍린???섍퀬 ?異?洹몃젮??萸붽? ?ㅻ쵖???먮굦???쒕뒗 寃?李?留ㅻ젰?곸씤 ?щ즺??','8886259.jpg','2021-08-19 02:26:19',NULL,_binary '\0',6,2,0),(36,'?↔퀬 ?먭볼??梨?,'?섍쾶 ?↔퀬 ?ㅻ옒??梨낆씠?덈떎.\n?먭퍖湲곕룄 援됱옣???먭볼?좊떎..\n?뚯뒪?붾줈 紐낆븫 二쇰뒗 寃??щ컡?덈떎.','2510229.jpg','2021-08-19 02:28:51',NULL,_binary '\0',6,2,0),(37,'?숆탳','誘몃? ?μ긽?먯꽌 ?뚮? 履쎌쓣 諛붾씪遊ㅼ쓣 ?뚯쓽 ?꾧꼍?대떎.\n?뚮윭?ㅽ렂?쇰줈 洹몃졇??\n?곌낵 ?섎Т瑜??쒗쁽?대낫?ㅻ떎 ??李띾뒓??二쎌쓣 六뷀뻽??','6995775.jpg','2021-08-19 02:32:16','2021-08-19 17:32:16',_binary '\0',6,5,0),(38,'?쒕퉴-??,'?닿구 ?꾩꽦?쒗궗 ?좎씠 ?ш퉴 ?띕떎. ?롢뀕\n?먭낵 吏?곌컻留뚯쑝濡쒕뒗 ?몃???臾섏궗媛 ?섎뱾?댁꽌 李고븘?대옉 寃?뺤깋 ?꾨━利덈쭏 ?좎꽦 ?됱뿰?꾨룄 ??붾뜲 ?꾩쭅??誘멸컻遊??곹깭.\n罹≫떞?먭쾶 誘몄븞?섎떎.','8365688.jpg','2021-08-19 02:36:03',NULL,_binary '',6,7,0),(39,'?ъ뭅由?罹?,'?ъ뭅由ъ뒪?⑦듃 罹붿뿉 ?ㅽ떚而??닿쾬?寃?遺숈뿬?볦? 嫄곕떎.\n4B ?고븘濡??됱쓣 ?쇰쭏??吏꾪븯寃??????덈뒗吏 ?????덉뿀??\n?덉쟾?먮뒗 ?댁궡??洹몃젮??洹몃┝???섏씠 ?놁뼱蹂댁??붾뜲 ?됱씠 吏꾪븳 怨녹쓣 ?뺤떎???댁＜?덇퉴 ???좊챸?대낫?몃떎.','7716292.jpg','2021-08-19 02:41:00',NULL,_binary '\0',6,2,0),(40,'?꾨줈','怨좎깮?덉뼱!!','6859345.jpg','2021-08-19 03:03:14',NULL,_binary '\0',3,4,0),(41,'?ㅻ쭏??,':)','7450865.jpg','2021-08-19 04:54:29',NULL,_binary '\0',4,7,0),(44,'媛뺤븘吏? 怨좎뼇??,'?덈Т 洹?쎄퀬 ^-^','1340521.jpg','2021-08-19 05:15:57','2021-08-19 15:12:37',_binary '\0',4,1,1),(45,'?뚮옉','?뚮옉?뚮옉','3343462.jpg','2021-08-19 05:49:53',NULL,_binary '\0',3,8,0),(46,'?묐갚?쒕줈??,'?곗뒿','9911867.jpg','2021-08-19 06:08:09',NULL,_binary '\0',10,7,0),(47,'?곗＜','?붿꽦 媛?먭퀬!','4567783.jpg','2021-08-19 07:37:56',NULL,_binary '\0',3,3,0),(48,'?먮┛ ?덇컻 ??,'硫뗭쭊 ?띻꼍','9888910.jpg','2021-08-19 07:53:01',NULL,_binary '',3,3,0),(50,'?移?鍮뚮뵫','硫뗭엳?ㅼ슂.','5734991.jpg','2021-08-19 07:53:31',NULL,_binary '',3,3,0),(51,'?댁쭏??,'?붿옍???띻꼍','9216024.jpg','2021-08-19 07:54:13',NULL,_binary '\0',3,3,0),(53,'紐⑤뜽 ?ㅼ?移?,'?쇨뎬 遺遺꾩씠 ?대젮??寃?媛숈????ㅽ뻾???댁そ?먯꽌????蹂댁뿬???ㅽ뻾?댁뿀?? ?ъ떎 ?곸쓽媛 ?ㅽ듃?쇱씠??臾대뒳?몃뜲 ?쒓컙???놁뼱 紐?洹몃졇?? ??,'9527231.jpg','2021-08-19 08:00:06',NULL,_binary '\0',6,4,0),(54,'?ш낵留?遊먯＜?몄슂','?닿구 洹몃━怨??섏꽌 \'?섏콈?붾룄 ?????덇쿋??'?쇨퀬 ?앷컖?섍쾶 ?섏뿀?? ?댁쟾源뚯????꾪? 媛먯쓣 紐??≪븘???섏콈?붾뒗 ?꾨땶嫄닿?..?쇨퀬 ?앷컖?섍퀬 ?덉뿀?붾뜲 ?ш낵媛 苑????섏????먯떊媛먯씠 ?앷꼈??','2374464.jpg','2021-08-19 08:01:16',NULL,_binary '\0',6,2,0),(55,'?깅깷??,'鍮꾨쭔 怨좎뼇?댁엯?덈떎.','613559.jpg','2021-08-19 08:02:01',NULL,_binary '\0',2,1,0),(56,'?λ?苑?,'?뙶','4907575.jpg','2021-08-19 08:02:17',NULL,_binary '\0',4,6,0),(57,'?뚰븯瑜대갑','?쇱??듭꽌??~\n洹쇰뜲 萸붽? ?뚰븯瑜대갑???뗢뿃醫뗪뎔 吏ㅼ씠 ?좎삤瑜대뒗嫄?湲곕텇?볦씪源뚯슂..??','9204613.jpg','2021-08-19 08:14:01',NULL,_binary '\0',4,3,0),(58,'?숉솕','?⑥뼱吏??苑껋쓣 蹂대뒗 怨좎뼇??','7922056.jpg','2021-08-19 08:18:41',NULL,_binary '',2,3,1),(59,'?곗＜','?곗＜ ?ы뻾!','3841248.jpg','2021-08-19 09:04:58',NULL,_binary '\0',3,3,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'seung9412@naver.com','qwert1234','helloworld',NULL,NULL,'2021-08-18 02:33:33',0),(2,'lim8662@gmail.com','123123ss','怨좎뼇??,'怨좎뼇??洹몃┝ 醫뗭븘?⑸땲??','https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/3849330.jpg','2021-08-18 02:39:36',5),(3,'ssafy@naver.com','ssafy123','ssafy','?덈뀞?섏꽭??','https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/965046.jpg','2021-08-18 03:59:21',3),(4,'msh9512@naver.com','moon1504','OiHater','?ㅼ씠???뺣쭚 ?レ뼱??:(','https://qwert-bucket.s3.ap-northeast-2.amazonaws.com/6356111.jpg','2021-08-18 04:15:27',5),(5,'seung@naver.com','qwer1234','?쒕쾭遺?붿옉?숉빐',NULL,NULL,'2021-08-18 04:19:46',4),(6,'heejun2822@hanmail.net','asdf1234','?댄씗以','?덈뀞?섏꽭??~ 洹몃┝ ?먯＜ ?щ┫寃뚯슂!',NULL,'2021-08-18 04:32:57',3),(7,'se@naver.com','qwert123','se',NULL,NULL,'2021-08-18 05:24:05',1),(8,'abc123123@naver.com','abc123123','abcabc',NULL,NULL,'2021-08-18 05:58:20',0),(9,'abcabc@naver.com','test1234','123456',NULL,NULL,'2021-08-19 01:17:21',0),(10,'qwert@naver.com','qwert123','qwert',NULL,NULL,'2021-08-19 01:31:00',1),(11,'abcabc123@naver.com','test1234','吏?섍??뷀솕諛?,NULL,NULL,'2021-08-19 01:59:51',0);
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

-- Dump completed on 2021-08-19  9:34:50
