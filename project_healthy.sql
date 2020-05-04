-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: project_healthy
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Table structure for table `cele`
--

DROP TABLE IF EXISTS `cele`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cele` (
  `uzytkownik` char(30) NOT NULL,
  `dzien` int NOT NULL,
  `kalorie` float NOT NULL,
  `bialko` float NOT NULL,
  `weglowodany` float NOT NULL,
  `tluszcze` float NOT NULL,
  `nawodnienie` int DEFAULT NULL,
  KEY `uzytkownik` (`uzytkownik`),
  CONSTRAINT `cele_ibfk_1` FOREIGN KEY (`uzytkownik`) REFERENCES `uzytkownicy` (`uzytkownik`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cele`
--

LOCK TABLES `cele` WRITE;
/*!40000 ALTER TABLE `cele` DISABLE KEYS */;
INSERT INTO `cele` VALUES ('Marcin',0,3400,180,200,100,3000),('Marcin',1,3400,160,100,125,2000),('Marcin',2,3400,180,200,100,3000),('Marcin',3,3400,160,100,125,2000),('Marcin',4,3400,180,200,100,3000),('Marcin',5,3400,160,100,125,2000),('Marcin',6,3400,160,100,125,2000),('Karolina',0,2000,75,25,180,2000),('Karolina',1,2000,75,25,180,2000),('Karolina',2,2000,100,75,100,2500),('Karolina',3,2000,75,25,180,2000),('Karolina',4,2000,75,25,180,2000);
/*!40000 ALTER TABLE `cele` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historia`
--

DROP TABLE IF EXISTS `historia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historia` (
  `uzytkownik` char(30) NOT NULL,
  `data` date NOT NULL,
  `nazwa` char(30) NOT NULL,
  `ilosc` int NOT NULL,
  KEY `uzytkownik` (`uzytkownik`),
  KEY `nazwa` (`nazwa`),
  CONSTRAINT `historia_ibfk_1` FOREIGN KEY (`uzytkownik`) REFERENCES `uzytkownicy` (`uzytkownik`) ON DELETE CASCADE,
  CONSTRAINT `historia_ibfk_2` FOREIGN KEY (`nazwa`) REFERENCES `produkty` (`nazwa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historia`
--

LOCK TABLES `historia` WRITE;
/*!40000 ALTER TABLE `historia` DISABLE KEYS */;
INSERT INTO `historia` VALUES ('Marcin','2020-05-02','kurczak',200),('Marcin','2020-05-02','ziemniak',150),('Marcin','2020-05-01','schab',120),('Marcin','2020-05-01','ziemniak',200),('Kamil','2020-05-02','ser',75);
/*!40000 ALTER TABLE `historia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `powiadomienia`
--

DROP TABLE IF EXISTS `powiadomienia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `powiadomienia` (
  `uzytkownik` char(30) NOT NULL,
  `wlaczone` tinyint(1) NOT NULL,
  `dzien` int NOT NULL,
  `godzina` time NOT NULL,
  `tresc` char(255) NOT NULL,
  KEY `uzytkownik` (`uzytkownik`),
  CONSTRAINT `powiadomienia_ibfk_1` FOREIGN KEY (`uzytkownik`) REFERENCES `uzytkownicy` (`uzytkownik`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `powiadomienia`
--

LOCK TABLES `powiadomienia` WRITE;
/*!40000 ALTER TABLE `powiadomienia` DISABLE KEYS */;
INSERT INTO `powiadomienia` VALUES ('Marcin',1,0,'17:00:00','Trening'),('Marcin',1,2,'17:00:00','Trening'),('Marcin',1,4,'17:00:00','Trening'),('Karolina',1,2,'09:00:00','Bieganie'),('Kamil',0,5,'08:00:00','Rozciaganie');
/*!40000 ALTER TABLE `powiadomienia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produkty`
--

DROP TABLE IF EXISTS `produkty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produkty` (
  `nazwa` char(30) NOT NULL,
  `kalorie` float NOT NULL,
  `bialko` float NOT NULL,
  `weglowodany` float NOT NULL,
  `tluszcze` float NOT NULL,
  `woda` float DEFAULT NULL,
  PRIMARY KEY (`nazwa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produkty`
--

LOCK TABLES `produkty` WRITE;
/*!40000 ALTER TABLE `produkty` DISABLE KEYS */;
INSERT INTO `produkty` VALUES ('kurczak',98,21,0,1,NULL),('mleko',51,3.3,4.9,2,250),('schab',129,23,0,4,NULL),('ser',383,28.2,0.1,29.7,NULL),('ziemniak',92,2,17,2,NULL);
/*!40000 ALTER TABLE `produkty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uzytkownicy`
--

DROP TABLE IF EXISTS `uzytkownicy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `uzytkownicy` (
  `uzytkownik` char(30) NOT NULL,
  `haslo` char(32) NOT NULL,
  PRIMARY KEY (`uzytkownik`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uzytkownicy`
--

LOCK TABLES `uzytkownicy` WRITE;
/*!40000 ALTER TABLE `uzytkownicy` DISABLE KEYS */;
INSERT INTO `uzytkownicy` VALUES ('Kamil','96e79218965eb72c92a549dd5a330112'),('Karolina','5a268af21caf3b452a7c2a2d17bab989'),('Marcin','e10adc3949ba59abbe56e057f20f883e'),('Paulina','6d932c406fa15164ee48ff5a52f81dae'),('Piotr','d8578edf8458ce06fbc5bb76a58c5ca4');
/*!40000 ALTER TABLE `uzytkownicy` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-03 14:25:10
