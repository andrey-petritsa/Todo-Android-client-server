-- MySQL dump 10.17  Distrib 10.3.18-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: ToDo
-- ------------------------------------------------------
-- Server version	10.3.18-MariaDB-0+deb10u1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `share`
--

DROP TABLE IF EXISTS `share`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `share` (
  `share_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `task_id` int(11) NOT NULL,
  PRIMARY KEY (`share_id`),
  UNIQUE KEY `share_id` (`share_id`),
  KEY `user_id` (`user_id`),
  KEY `task_id` (`task_id`),
  CONSTRAINT `share_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `share_ibfk_2` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `share`
--

LOCK TABLES `share` WRITE;
/*!40000 ALTER TABLE `share` DISABLE KEYS */;
INSERT INTO `share` VALUES (1,11,7),(2,11,9);
/*!40000 ALTER TABLE `share` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shared_task`
--

DROP TABLE IF EXISTS `shared_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shared_task` (
  `shared_task_id` int(5) NOT NULL AUTO_INCREMENT,
  `share_owner_id` int(5) DEFAULT NULL,
  `share_user_id` int(5) DEFAULT NULL,
  `task_id` int(5) DEFAULT NULL,
  PRIMARY KEY (`shared_task_id`),
  KEY `share_owner_id` (`share_owner_id`),
  KEY `share_user_id` (`share_user_id`),
  KEY `task_id` (`task_id`),
  CONSTRAINT `shared_task_ibfk_1` FOREIGN KEY (`share_owner_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `shared_task_ibfk_2` FOREIGN KEY (`share_user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `shared_task_ibfk_3` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shared_task`
--

LOCK TABLES `shared_task` WRITE;
/*!40000 ALTER TABLE `shared_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `shared_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `task_id` int(5) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(50) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `priority` int(5) DEFAULT NULL,
  `done` tinyint(1) DEFAULT NULL,
  `user_id` int(5) DEFAULT NULL,
  PRIMARY KEY (`task_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `task_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (1,'Написать Новую Повесть','2019-11-05',4,1,1),(2,'Ответить на письмо Татьяны','2019-11-05',3,0,2),(3,'Не Писать Новый Роман','2020-01-02',1,0,2),(6,'Название задачи','2020-01-15',2,0,1),(7,'Задача Сидорова','2020-01-15',3,0,9),(8,'Задача 1','2020-01-15',1,0,10),(9,'Времени Очень Много','2020-01-15',5,0,10),(10,'Пустая Задача','2020-01-16',0,0,10);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(5) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(50) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `patronymic_name` varchar(50) DEFAULT NULL,
  `email_adress` varchar(100) DEFAULT NULL,
  `password` varchar(10) DEFAULT NULL,
  `photo_link` varchar(200) DEFAULT NULL,
  `token` char(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Петрица','Андрей','Викторович','andrey@petritsa.ru','12345',NULL,'8697b9f4b5646c04ce850d4a0199059c'),(2,'Онегин','Евгений','Евгениевич','evgeniy@onegin.ru','yavaslybil',NULL,'a20d3cc048f781c086452f0ef557bebf'),(9,'сидоров','сидор','сидорович','test@mail.ru','1212',NULL,'1f8f82e30020814ebd837b3b564d4745'),(10,'петрица','андрей','васильевич','andrey2@mail.ru','12345',NULL,'ee969de7cda8f2c6c617b268c956053b'),(11,'викторов','виктор','викторович','victor@mail.ru','12345',NULL,'cb87a4d7ab3a209ceac57445863abb27'),(12,'Иван','Иванов','Иванович','123@mail.ru','blablabla',NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `work`
--

DROP TABLE IF EXISTS `work`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `work` (
  `work_id` int(5) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `planned_end_date` date DEFAULT NULL,
  `done` tinyint(1) DEFAULT NULL,
  `user_id` int(5) DEFAULT NULL,
  `task_id` int(5) DEFAULT NULL,
  PRIMARY KEY (`work_id`),
  KEY `user_id` (`user_id`),
  KEY `task_id` (`task_id`),
  CONSTRAINT `work_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `work_ibfk_2` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `work`
--

LOCK TABLES `work` WRITE;
/*!40000 ALTER TABLE `work` DISABLE KEYS */;
INSERT INTO `work` VALUES (1,'122','2019-11-06','2019-11-07',1,1,1),(2,'Выполненная задача','2019-11-07','2019-11-08',1,1,1),(3,'Собраться с мыслями, подумать что написать','2019-12-07','2019-12-08',0,2,2),(4,'Лечь вздревнуть','2019-12-08','2019-12-09',0,2,2),(5,'Подзадача у не писать новый роман','2019-05-02','2019-05-02',0,2,3),(6,NULL,'2020-01-15','2021-05-01',0,1,1),(7,'Подзадача_для_id1','2020-01-15','2021-05-01',0,1,1),(8,'Название задачи','2020-01-15','2023-07-06',0,1,1),(9,'124','2020-01-15','2021-08-03',0,2,3),(10,'Подзадача Сидорова','2020-01-15','3020-03-08',0,9,7),(11,'Выполнить Срочнн','2020-01-15','2020-01-16',0,10,8),(12,'Выполнить Побыстрее','2020-01-15','2020-01-23',1,10,8),(13,'Времени Очень Много','2020-01-15','2020-01-28',0,10,8),(14,'Ещё ','2020-01-15','2020-01-22',1,10,9);
/*!40000 ALTER TABLE `work` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-16  3:07:47
