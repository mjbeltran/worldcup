-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: spring-security-tutorial
-- ------------------------------------------------------
-- Server version	5.7.11-log

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
-- Table structure for table  role 
--

DROP TABLE IF EXISTS  role ; 
CREATE TABLE  role  (
   role_id  SERIAL,
   role  varchar(255) DEFAULT NULL,
  PRIMARY KEY ( role_id )
);

/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table  user 
--

DROP TABLE IF EXISTS  users ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;    
CREATE TABLE  users  (
   user_id SERIAL,
   active  integer DEFAULT NULL,
   email  varchar(255) NOT NULL,
   last_name  varchar(255) NOT NULL,
   name  varchar(255) NOT NULL,
   password  varchar(255) NOT NULL,
  PRIMARY KEY ( user_id )
) ;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table  user_role 
--
DROP TABLE IF EXISTS  user_role ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE  user_role  (
   user_id  SERIAL,
   role_id  integer NOT NULL,
  PRIMARY KEY ( user_id , role_id ),
  CONSTRAINT FK859n2jvi8ivhui0rl0esws6o FOREIGN KEY ( user_id ) REFERENCES  users  ( user_id ),
  CONSTRAINT  FKa68196081fvovjhkek5m97n3y  FOREIGN KEY ( role_id ) REFERENCES  role  ( role_id )
);
/*!40101 SET character_set_client = @saved_cs_client */;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-16 13:21:44



CREATE TABLE Team
(
    team_id integer NOT NULL DEFAULT nextval('team_id_seq'::regclass),
    flag character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    emoji character varying(255),
    iso2 character varying(255),
    CONSTRAINT team_pkey PRIMARY KEY (team_id)
);

CREATE SEQUENCE team_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;
    
    CREATE TABLE match
(
    match_id integer NOT NULL DEFAULT nextval('match_id_seq'::regclass),
    home_team_id integer NOT NULL,
    away_team_id integer NOT NULL,
    stadium character varying(255) NOT NULL,
    date date NOT NULL,
    result character varying(20),
    CONSTRAINT match_pkey PRIMARY KEY (match_id),
   CONSTRAINT fk_home_team FOREIGN KEY (home_team_id)
        REFERENCES team (team_id) MATCH SIMPLE,
    CONSTRAINT fk_away_team FOREIGN KEY (away_team_id)
        REFERENCES team (team_id) MATCH SIMPLE
)
