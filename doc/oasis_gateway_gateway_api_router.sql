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
-- Table structure for table `gateway_api_router`
--

DROP TABLE IF EXISTS `gateway_api_router`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gateway_api_router` (
  `api_router_id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `router_version` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '路由版本',
  `router_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '路由名称',
  `router_path` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '路由Path',
  `request_method` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '请求方法 GET/POST/PUT/DELETE',
  `router_type` tinyint NOT NULL COMMENT '路由类型 1.api 2.service 3.注册中心',
  `router_upstream_path` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '上游服务 Path',
  `router_upstream_url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '上游服务ip+port',
  `router_upstream_service` int DEFAULT NULL COMMENT '上游服务,仅当router_type为2或3时可用,2存gateway_service 表中的ID,3存注册中心对应的服务名',
  `protocol` tinyint DEFAULT NULL COMMENT 'api协议 1.http 2.rpc(dubbo)',
  `consumes_type` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '消费类别',
  `produces_type` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生产类别',
  `service_id` int DEFAULT NULL COMMENT '服务ID',
  `api_group_id` int DEFAULT NULL COMMENT '组ID',
  `router_desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '路由描述',
  `router_request_body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '路由请求体示例',
  `router_response_body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '路由响应体示例',
  `create_user` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户ID 用于控制API 创建权限',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户ID 用于控制API 修改权限',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除 1、删除 0、未删除',
  PRIMARY KEY (`api_router_id`),
  KEY `api_group_id_index` (`api_group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='api路由信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gateway_api_router`
--

LOCK TABLES `gateway_api_router` WRITE;
/*!40000 ALTER TABLE `gateway_api_router` DISABLE KEYS */;
INSERT INTO `gateway_api_router` VALUES (1,'v1.0.0','testRouter','/test','POST',1,'/test','http://127.0.0.1',NULL,1,'application/json','application/json',NULL,1,'测试Router','{\"id\":\"123\",\"name\":\"test_request\",\"price\":\"100\",\"tags\":[\"hello\",\"world\"]}','{\"id\":\"321\",\"name\":\"test_response\",\"price\":\"200\",\"tags\":[\"hello world\",\"world hello\"],\"responseAdd\":{\"res1\":\"1\"}}',NULL,'2022-04-14 03:52:20','2022-04-14 03:52:20',NULL,0);
/*!40000 ALTER TABLE `gateway_api_router` ENABLE KEYS */;
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
