CREATE DATABASE  IF NOT EXISTS `oasis_gateway` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `oasis_gateway`;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: oasis_gateway
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `gateway_group`
--

DROP TABLE IF EXISTS `gateway_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gateway_group` (
  `api_group_id` int NOT NULL AUTO_INCREMENT,
  `group_name` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '组名称',
  `group_version` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '版本号',
  `create_user` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户ID 用于控制API 创建权限',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户ID 用于控制API 修改权限',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除 1删除 0未删除',
  PRIMARY KEY (`api_group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='api分组表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gateway_group`
--

LOCK TABLES `gateway_group` WRITE;
/*!40000 ALTER TABLE `gateway_group` DISABLE KEYS */;
INSERT INTO `gateway_group` VALUES (1,'test01','v2.11.0',NULL,'2022-04-11 10:18:13','2022-04-11 10:18:13',NULL,0);
/*!40000 ALTER TABLE `gateway_group` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-19 21:41:52
