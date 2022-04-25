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
-- Table structure for table `gateway_api_plugin`
--

DROP TABLE IF EXISTS `gateway_api_plugin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gateway_api_plugin` (
  `plugin_id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `api_router_id` int DEFAULT NULL COMMENT 'api_id',
  `plugin_name` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '插件英文属性名',
  `plugin_configuration` TEXT DEFAULT NULL COMMENT 'plugin相关配置',
  `plugin_type` tinyint DEFAULT '0' COMMENT 'plugin 定义的类别 0 原生插件 1 自定义插件',
  `plugin_order` tinyint DEFAULT NULL COMMENT '插件顺序',
  `plugin_enabled` tinyint DEFAULT '1' COMMENT '是否启用 0 否 1 是',
  `create_user` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户ID 用于控制API 创建权限',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户ID 用于控制API 修改权限',
  `is_origin` tinyint DEFAULT '0' COMMENT '是否是原生 1 是 0 否',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除 1 是 0 否',
  PRIMARY KEY (`plugin_id`),
  KEY `index_plugin_enabled` (`plugin_enabled`),
  KEY `api_id_index` (`api_router_id`),
  KEY `is_deleted_index` (`is_deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gateway_api_plugin`
--

LOCK TABLES `gateway_api_plugin` WRITE;
/*!40000 ALTER TABLE `gateway_api_plugin` DISABLE KEYS */;
INSERT INTO `gateway_api_plugin` VALUES (1,1,'JsonHandle','{\"extendRequest\": {\"addJsonNodes\": {\"root\": [{\"testRequestAdd\": \"add my Request node\"}]}, \"deleteJsonNodes\": [{\"root\": \"price\"}]}, \"extendResponse\": {\"addJsonNodes\": {\"responseAdd\": [{\"testResponseAdd\": \"add my Response node\"}]}, \"deleteJsonNodes\": [{\"root\": \"tags\"}]}, \"requestJsonSchema\": {\"type\": \"object\", \"title\": \"Product\", \"$schema\": \"http://json-schema.org/draft-04/schema#\", \"required\": [\"id\", \"name\", \"price\"], \"properties\": {\"id\": {\"type\": \"integer\", \"description\": \"The unique identifier for a product\"}, \"name\": {\"type\": \"string\", \"description\": \"Name of the product\"}, \"tags\": {\"type\": \"array\", \"items\": {\"type\": \"string\"}, \"minItems\": 1, \"uniqueItems\": true}, \"price\": {\"type\": \"number\", \"minimum\": 0, \"exclusiveMinimum\": true}}, \"description\": \"A product from Acme\'s catalog\"}}',1,-10,1,NULL,'2022-04-14 03:55:44','2022-04-14 03:55:44',NULL,0,0),(2,1,'SetResponseHeaders','{\"testHeader1\": \"test1\", \"testHeader2\": \"test2\"}',1,10,1,NULL,'2022-04-19 06:30:06','2022-04-19 06:30:06',NULL,0,0);
/*!40000 ALTER TABLE `gateway_api_plugin` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-19 21:41:51
