CREATE DATABASE  IF NOT EXISTS `persondb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `persondb`;
-- MySQL dump 10.13  Distrib 8.0.41, for macos15 (x86_64)
--
-- Host: localhost    Database: persondb
-- ------------------------------------------------------
-- Server version	9.2.0

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
-- Table structure for table `chat_message`
--

DROP TABLE IF EXISTS `chat_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_message` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sender_id` int NOT NULL,
  `receiver_id` int NOT NULL,
  `message` text NOT NULL,
  `sent_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `sender_id` (`sender_id`),
  KEY `receiver_id` (`receiver_id`),
  CONSTRAINT `chat_message_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `user_account` (`id`) ON DELETE CASCADE,
  CONSTRAINT `chat_message_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `user_account` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_message`
--

LOCK TABLES `chat_message` WRITE;
/*!40000 ALTER TABLE `chat_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise`
--

DROP TABLE IF EXISTS `exercise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text,
  `muscle_group` varchar(50) DEFAULT NULL,
  `level` varchar(20) DEFAULT NULL,
  `calories_burned` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise`
--

LOCK TABLES `exercise` WRITE;
/*!40000 ALTER TABLE `exercise` DISABLE KEYS */;
INSERT INTO `exercise` VALUES (1,'Hít đất','Bài tập đơn giản giúp phát triển cơ ngực và tay sau.','Chest','Beginner',100),(2,'Gập bụng','Tăng cường cơ bụng, dễ thực hiện tại nhà.','Abs','Beginner',80),(3,'Squat','Phát triển cơ đùi, mông và cải thiện sức mạnh chân.','Legs','Intermediate',150),(4,'Plank','Giúp siết cơ bụng và cải thiện sức bền.','Core','Beginner',60),(5,'Deadlift','Tác động toàn thân, đặc biệt là lưng và chân.','Back','Advanced',250),(20,'Bơi','Bơi lội','Chân','Trung bình',400),(28,'Bài tập của Nguyên','Tập theo Nguyên','Toàn thân','Nâng cao',500);
/*!40000 ALTER TABLE `exercise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `health_journal`
--

DROP TABLE IF EXISTS `health_journal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `health_journal` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `date` date NOT NULL,
  `content` text,
  `feeling` text,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `health_journal_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_account` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `health_journal`
--

LOCK TABLES `health_journal` WRITE;
/*!40000 ALTER TABLE `health_journal` DISABLE KEYS */;
INSERT INTO `health_journal` VALUES (2,16,'2025-05-02','Cảm thấy hơi mệt và nhức đầu vào buổi sáng.','Mệt mỏi'),(3,16,'2025-05-03','Tôi ăn uống đầy đủ và tập yoga 30 phút.','Tích cực'),(4,16,'2025-05-03','Không ngủ ngon, bị tỉnh giấc lúc 3 giờ sáng.','Lo lắng'),(5,16,'2025-05-04','Đi khám sức khỏe định kỳ, kết quả tốt.','Yên tâm'),(6,16,'2025-05-05','Thời tiết nóng khiến tôi uể oải, không muốn vận động.','Uể oải'),(12,16,'2025-05-10','Hôm nay tôi đã đi bộ 30 phút vào buổi sáng và ăn sáng lành mạnh với yến mạch\r\n','Cảm thấy khỏe khoắn'),(16,16,'2025-05-15','Hôm nay đã làm được chức năng gợi ý','Mệt mỏi'),(17,23,'2025-05-27','Fix bug mệt quá','Mệt mỏi'),(18,23,'2025-05-27','Mệt\n','Quá mệt');
/*!40000 ALTER TABLE `health_journal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `health_profile`
--

DROP TABLE IF EXISTS `health_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `health_profile` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `height` float DEFAULT NULL,
  `weight` float DEFAULT NULL,
  `bmi` float DEFAULT NULL,
  `heart_rate` int DEFAULT NULL,
  `target` text,
  `steps_per_day` int DEFAULT NULL,
  `water_intake` float DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  CONSTRAINT `health_profile_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_account` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `health_profile`
--

LOCK TABLES `health_profile` WRITE;
/*!40000 ALTER TABLE `health_profile` DISABLE KEYS */;
INSERT INTO `health_profile` VALUES (3,3,150,85,26.2,70,'Gain muscle mass',8000,3.5,'2025-04-05 17:13:44'),(17,19,170,50,23,323,'Tốt',123213,12,'2025-04-15 16:58:38'),(25,16,170,55,19.0311,90,'Giảm cân',6000,6,'2025-05-26 15:19:39'),(27,36,190,70,23,89,'Giam can\r\n',2000,3,'2025-05-26 15:14:19'),(28,23,165,60,22.0386,90,'Giảm cân',10000,4,'2025-05-26 15:20:48');
/*!40000 ALTER TABLE `health_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meal_plan`
--

DROP TABLE IF EXISTS `meal_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meal_plan` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `date` date NOT NULL,
  `meal_type` enum('BREAKFAST','LUNCH','DINNER','SNACK') DEFAULT NULL,
  `description` text,
  `calories` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `meal_plan_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_account` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meal_plan`
--

LOCK TABLES `meal_plan` WRITE;
/*!40000 ALTER TABLE `meal_plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `meal_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reminder`
--

DROP TABLE IF EXISTS `reminder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reminder` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `reminder_type` enum('DRINK_WATER','WORKOUT','REST') DEFAULT NULL,
  `time` time DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `reminder_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_account` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reminder`
--

LOCK TABLES `reminder` WRITE;
/*!40000 ALTER TABLE `reminder` DISABLE KEYS */;
INSERT INTO `reminder` VALUES (3,16,'Nghỉ ','REST','09:01:00',0),(4,16,'Uống nước buổi chiều','DRINK_WATER','16:30:00',0),(6,16,'Thư giãn và thiền','REST','22:00:00',0),(7,16,'Tập thể dục buổi tối','WORKOUT','00:00:00',0),(8,16,'Dậy tập','WORKOUT','08:00:00',0),(10,16,'Uống nước buổi sáng','DRINK_WATER','09:00:00',0),(11,16,'Uống nước sau khi ngủ','DRINK_WATER','22:37:00',1),(14,16,'Uống nước buổi sáng 2','DRINK_WATER','10:20:00',1),(18,23,'Dậy tập thể dục','WORKOUT','10:00:00',1);
/*!40000 ALTER TABLE `reminder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `role` enum('ROLE_USER','ROLE_TRAINER','ROLE_ADMIN') DEFAULT 'ROLE_USER',
  `full_name` varchar(100) DEFAULT NULL,
  `gender` enum('MALE','FEMALE','OTHER') DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (1,'john_doe','$2a$10$NzH3C1Ya4Ju2rFBTJgnAt.4m8DIcKyTmN7kEXtMa1ITG15EZZm9d2','john.doe@example.com','ROLE_USER','John Doe','MALE','1990-01-15','0332636829','https://res.cloudinary.com/dnwyvuqej/image/upload/v1733499646/default_avatar_uv0h7z.jpg','2025-04-05 17:13:44'),(2,'jane_smith','$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO','jane.smith@example.com','ROLE_TRAINER','Jane Smith','FEMALE','1985-07-22','0987654321','https://res.cloudinary.com/dnwyvuqej/image/upload/v1733499646/default_avatar_uv0h7z.jpg','2025-04-05 17:13:44'),(3,'admin_user','$2a$10$v/bPzCpgGZ1PHe7bdJLWRO3a0f2xKIb2EAMqQN.7UtVu5D/LsacGi','admin@example.com','ROLE_ADMIN','ROLEAdmin User','MALE','1980-05-05','0112233445','https://res.cloudinary.com/dnwyvuqej/image/upload/v1733497572/tqazghsjhudjzdz4rhrh.jpg','2025-04-05 17:13:44'),(12,'nguyenphuoc','$2a$10$t6GI0O1UXEEzpdpE1OS81uigrH0E3bM7lctoXIgmHtnp1UZq9.R3q','nguyen.ho04@gmail.com','ROLE_USER','Phước Nguyên','MALE','2004-04-07','0332636829','https://res.cloudinary.com/dnwyvuqej/image/upload/v1744517858/zwqu0ajm0nqx2fclcb9f.jpg',NULL),(16,'nguyen123','$2a$10$8.44DLhS1P.hYEaLnIZYYOZdydolKobfvy6NS35X98RmEvqqloN6O','2251050048nguyen@ou.edu.vn','ROLE_USER','Hồ Chí Nguyên','MALE','1980-05-08','0332636829','https://res.cloudinary.com/dnwyvuqej/image/upload/v1745812167/gwlv99bdkusednidznsd.jpg','2025-04-15 02:18:37'),(19,'dan123','$2a$10$GimK/2D7sNds4OFoK4/tPOg1f9GQG6el.lndrIHgwPYgfsdfR/4HG','nguyen.hochi04@gmail.com','ROLE_TRAINER','Lê Thanh Dân','FEMALE','2004-04-07','0332636822','https://res.cloudinary.com/dnwyvuqej/image/upload/v1744684607/yadaxrog2pys4doevxzm.webp','2025-04-15 02:36:46'),(20,'minh','$2a$10$YH2oWlL7fiZ4QsC520O73.mvKrfLa9ujAgFDVBMLRIUTcgAoRDl6u','nguyendeptraiis@gmail.com','ROLE_USER','minh','MALE','2004-04-07','0332636829','https://res.cloudinary.com/dnwyvuqej/image/upload/v1744685068/ok8riq3et6x90wyhy562.webp','2025-04-15 02:44:26'),(23,'tin123','$2a$10$vO9HC1ebuMbxuHaJjI.JW.g3Az0J.TJMJkUgYniT3v4bBQ7kv73dS','nguyeewrewndeptraiiris@gmail.com','ROLE_USER','Vũ Trọng Tín','OTHER','2004-05-07','0332636829','https://res.cloudinary.com/dnwyvuqej/image/upload/v1744685169/gtesgs98nhr8tkud6adq.webp','2025-04-15 02:46:07'),(36,'nguyen321','$2a$10$JTDcJwRZBAHoe4izlmfzJ.tp4kE0tfPdUf6hgUV5vA7GOkTDmY10u','nguyen123@gmail.com','ROLE_USER','Ho Nguyen','MALE','2005-04-12','0332636829','https://res.cloudinary.com/dnwyvuqej/image/upload/v1748272404/pbrljqlxqaf22wy4vrz6.jpg','2025-05-26 15:13:23');
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_trainer`
--

DROP TABLE IF EXISTS `user_trainer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_trainer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `trainer_id` int NOT NULL,
  `status` enum('PENDING','ACCEPTED','REJECTED') DEFAULT 'PENDING',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`trainer_id`),
  KEY `trainer_id` (`trainer_id`),
  CONSTRAINT `user_trainer_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_account` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_trainer_ibfk_2` FOREIGN KEY (`trainer_id`) REFERENCES `user_account` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_trainer`
--

LOCK TABLES `user_trainer` WRITE;
/*!40000 ALTER TABLE `user_trainer` DISABLE KEYS */;
INSERT INTO `user_trainer` VALUES (6,12,19,'ACCEPTED','2025-05-25 10:21:00'),(47,23,19,'ACCEPTED','2025-05-25 16:12:25');
/*!40000 ALTER TABLE `user_trainer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workout_plan`
--

DROP TABLE IF EXISTS `workout_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workout_plan` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `name` varchar(100) NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `workout_plan_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workout_plan`
--

LOCK TABLES `workout_plan` WRITE;
/*!40000 ALTER TABLE `workout_plan` DISABLE KEYS */;
INSERT INTO `workout_plan` VALUES (14,16,'Kế hoạch tập thể lực tháng 4','2025-04-01','2025-04-30','2025-05-25 04:08:35'),(17,16,'Kế hoạch tập thể lực tháng 5','2025-05-01','2025-05-31','2025-05-25 04:08:53'),(31,16,'Kế hoạch tập thể lực tháng 6','2025-06-01','2025-06-30','2025-05-25 04:08:57'),(39,23,'Kế hoạch tháng 5 của Tín','2025-05-01','2025-05-03','2025-05-26 15:21:31');
/*!40000 ALTER TABLE `workout_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workout_plan_exercise`
--

DROP TABLE IF EXISTS `workout_plan_exercise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workout_plan_exercise` (
  `id` int NOT NULL AUTO_INCREMENT,
  `workout_plan_id` int NOT NULL,
  `exercise_id` int NOT NULL,
  `sets` int DEFAULT NULL,
  `reps` int DEFAULT NULL,
  `duration_minutes` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `workout_plan_id` (`workout_plan_id`),
  KEY `exercise_id` (`exercise_id`),
  CONSTRAINT `workout_plan_exercise_ibfk_1` FOREIGN KEY (`workout_plan_id`) REFERENCES `workout_plan` (`id`),
  CONSTRAINT `workout_plan_exercise_ibfk_2` FOREIGN KEY (`exercise_id`) REFERENCES `exercise` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workout_plan_exercise`
--

LOCK TABLES `workout_plan_exercise` WRITE;
/*!40000 ALTER TABLE `workout_plan_exercise` DISABLE KEYS */;
INSERT INTO `workout_plan_exercise` VALUES (13,14,1,1,10,20),(14,17,2,12,1,30),(52,31,20,2,10,30),(54,39,28,10,1,60);
/*!40000 ALTER TABLE `workout_plan_exercise` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-26 23:03:53
