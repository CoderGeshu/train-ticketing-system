-- MySQL dump 10.13  Distrib 5.5.62, for Win64 (AMD64)
--
-- Host: localhost    Database: train
-- ------------------------------------------------------
-- Server version	5.5.62

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `contacts`
--

DROP TABLE IF EXISTS `contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contacts` (
  `userid` varchar(20) NOT NULL,
  `contactname` varchar(10) NOT NULL,
  `contacttel` varchar(20) NOT NULL,
  `contactid` varchar(20) NOT NULL,
  `contactno` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacts`
--

LOCK TABLES `contacts` WRITE;
/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
INSERT INTO `contacts` VALUES ('123456789012345671','联系人1','10086','987654321098765432',1),('123456789012345671','中国联通','100000','1423469953223',2),('20000000000000000','','','',1),('20000000000000000','','','',2),('null','','','',1),('null','','','',2);
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordersheets`
--

DROP TABLE IF EXISTS `ordersheets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ordersheets` (
  `userid` varchar(20) NOT NULL,
  `username` varchar(10) NOT NULL,
  `trainno` varchar(10) NOT NULL,
  `traintypeno` int(11) NOT NULL,
  `startplace` varchar(20) NOT NULL,
  `endplace` varchar(20) NOT NULL,
  `starttime` datetime NOT NULL,
  `endtime` datetime NOT NULL,
  `boxno` int(11) NOT NULL,
  `seatno` int(11) NOT NULL,
  `seatclassno` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `buytime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordersheets`
--

LOCK TABLES `ordersheets` WRITE;
/*!40000 ALTER TABLE `ordersheets` DISABLE KEYS */;
INSERT INTO `ordersheets` VALUES ('123456789012345671','张三','K1514',0,'上海','北京','2020-01-22 01:00:00','2020-01-22 14:00:00',3,15,0,500,'2021-01-23 16:07:41');
/*!40000 ALTER TABLE `ordersheets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seatclassinfo`
--

DROP TABLE IF EXISTS `seatclassinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seatclassinfo` (
  `seatclassno` int(11) NOT NULL,
  `seatcladd` varchar(20) NOT NULL,
  PRIMARY KEY (`seatclassno`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seatclassinfo`
--

LOCK TABLES `seatclassinfo` WRITE;
/*!40000 ALTER TABLE `seatclassinfo` DISABLE KEYS */;
INSERT INTO `seatclassinfo` VALUES (0,'商务座'),(1,'一等座'),(2,'二等座');
/*!40000 ALTER TABLE `seatclassinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seatprice`
--

DROP TABLE IF EXISTS `seatprice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seatprice` (
  `traintypeno` int(11) NOT NULL,
  `businessprice` int(11) DEFAULT NULL,
  `firstprice` int(11) DEFAULT NULL,
  `secondprice` int(11) DEFAULT NULL,
  PRIMARY KEY (`traintypeno`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seatprice`
--

LOCK TABLES `seatprice` WRITE;
/*!40000 ALTER TABLE `seatprice` DISABLE KEYS */;
INSERT INTO `seatprice` VALUES (0,500,300,200),(1,1000,750,500),(2,900,800,600);
/*!40000 ALTER TABLE `seatprice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seats`
--

DROP TABLE IF EXISTS `seats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seats` (
  `trainno` varchar(15) NOT NULL,
  `boxno` int(11) NOT NULL,
  `seatno` int(11) NOT NULL,
  `seatclassno` int(11) NOT NULL,
  `seatstateno` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seats`
--

LOCK TABLES `seats` WRITE;
/*!40000 ALTER TABLE `seats` DISABLE KEYS */;
INSERT INTO `seats` VALUES ('K1517',1,1,0,0);
/*!40000 ALTER TABLE `seats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trains`
--

DROP TABLE IF EXISTS `trains`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trains` (
  `trainno` varchar(15) NOT NULL,
  `traintypeno` int(11) NOT NULL,
  `startplace` varchar(20) NOT NULL,
  `endplace` varchar(20) NOT NULL,
  `starttime` time DEFAULT NULL,
  `endtime` time DEFAULT NULL,
  `runtime` time DEFAULT NULL,
  PRIMARY KEY (`trainno`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trains`
--

LOCK TABLES `trains` WRITE;
/*!40000 ALTER TABLE `trains` DISABLE KEYS */;
INSERT INTO `trains` VALUES ('D211',2,'上海','北京','03:00:00','15:00:00','12:00:00'),('D245',2,'上海','北京','07:00:00','15:00:00','08:00:00'),('D713',2,'上海','青岛','15:00:00','23:00:00','08:00:00'),('G397',1,'上海虹桥','济南','07:00:00','11:45:00','04:45:00'),('G516',1,'上海','北京','13:00:00','22:00:00','09:00:00'),('G713',1,'上海','北京','08:00:00','17:00:00','09:00:00'),('K1514',0,'上海','北京','01:00:00','14:00:00','13:00:00'),('K1517',0,'深圳','上海','07:00:00','22:40:00','15:40:00'),('K1713',0,'上海','北京','18:00:00','14:00:00','18:00:00'),('K2019',0,'上海','北京','10:00:00','24:00:00','14:00:00'),('K2213',0,'上海','北京','09:00:00','18:00:00','09:00:00');
/*!40000 ALTER TABLE `trains` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `traintypeinfo`
--

DROP TABLE IF EXISTS `traintypeinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `traintypeinfo` (
  `traintypeno` int(11) NOT NULL,
  `traintype` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `traintypeinfo`
--

LOCK TABLES `traintypeinfo` WRITE;
/*!40000 ALTER TABLE `traintypeinfo` DISABLE KEYS */;
INSERT INTO `traintypeinfo` VALUES (0,'普通列车'),(1,'高铁'),(2,'动车');
/*!40000 ALTER TABLE `traintypeinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `usertel` varchar(15) NOT NULL,
  `password` varchar(15) NOT NULL,
  `username` varchar(10) NOT NULL,
  `usergender` varchar(5) NOT NULL,
  `usertypeno` int(11) NOT NULL,
  `userid` varchar(30) NOT NULL,
  `idtypeno` int(11) NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('110','123456','张三','男',0,'123456789012345671',0),('120','123456','李四','男',1,'123456789012345672',0),('130','123456','小红','女',0,'123456789012345673',0),('140','123456','玛丽','女',0,'123456789012345674',0),('15754912991','123456','葛东升','男',0,'20000000000000000',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usertypeinfo`
--

DROP TABLE IF EXISTS `usertypeinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usertypeinfo` (
  `usertypeno` int(11) NOT NULL,
  `usertype` varchar(10) NOT NULL,
  PRIMARY KEY (`usertypeno`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usertypeinfo`
--

LOCK TABLES `usertypeinfo` WRITE;
/*!40000 ALTER TABLE `usertypeinfo` DISABLE KEYS */;
INSERT INTO `usertypeinfo` VALUES (0,'乘客'),(1,'管理员');
/*!40000 ALTER TABLE `usertypeinfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-23 16:33:51
