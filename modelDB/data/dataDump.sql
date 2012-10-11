CREATE DATABASE  IF NOT EXISTS `model` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `model`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: model
-- ------------------------------------------------------
-- Server version	5.5.25

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
-- Table structure for table `model_code`
--

DROP TABLE IF EXISTS `model_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `model_code` (
  `model_code_id` int(11) NOT NULL AUTO_INCREMENT,
  `code_name` varchar(45) DEFAULT NULL,
  `algorithm` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`model_code_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `model_code`
--

LOCK TABLES `model_code` WRITE;
/*!40000 ALTER TABLE `model_code` DISABLE KEYS */;
INSERT INTO `model_code` VALUES (1,'XAL','EnvelopeTracker');
/*!40000 ALTER TABLE `model_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gold_lattice`
--

DROP TABLE IF EXISTS `gold_lattice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gold_lattice` (
  `gold_id` int(11) NOT NULL AUTO_INCREMENT,
  `lattice_id` int(11) DEFAULT NULL,
  `machine_mode_id` varchar(45) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `updated_by` varchar(45) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `gold_status_ind` int(11) DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`gold_id`),
  KEY `FK_gold_lattice_id_idx` (`lattice_id`),
  CONSTRAINT `FK_gold_lattice_id` FOREIGN KEY (`lattice_id`) REFERENCES `lattice` (`lattice_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gold_lattice`
--

LOCK TABLES `gold_lattice` WRITE;
/*!40000 ALTER TABLE `gold_lattice` DISABLE KEYS */;
/*!40000 ALTER TABLE `gold_lattice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `machine_mode`
--

DROP TABLE IF EXISTS `machine_mode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `machine_mode` (
  `machine_mode_id` int(11) NOT NULL AUTO_INCREMENT,
  `machine_mode_name` varchar(45) DEFAULT NULL,
  `machine_mode_description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`machine_mode_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `machine_mode`
--

LOCK TABLES `machine_mode` WRITE;
/*!40000 ALTER TABLE `machine_mode` DISABLE KEYS */;
INSERT INTO `machine_mode` VALUES (1,'DESIGN','design model'),(2,'EXTANT','live model'),(3,'USER','user defined model');
/*!40000 ALTER TABLE `machine_mode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `beamline_sequence`
--

DROP TABLE IF EXISTS `beamline_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `beamline_sequence` (
  `beamline_sequence_id` int(11) NOT NULL AUTO_INCREMENT,
  `sequence_name` varchar(45) DEFAULT NULL,
  `first_element_name` varchar(45) DEFAULT NULL,
  `last_element_name` varchar(45) DEFAULT NULL,
  `predecessor_sequence` varchar(255) DEFAULT NULL,
  `sequence_length` double DEFAULT NULL,
  `sequence_description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`beamline_sequence_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `beamline_sequence`
--

LOCK TABLES `beamline_sequence` WRITE;
/*!40000 ALTER TABLE `beamline_sequence` DISABLE KEYS */;
INSERT INTO `beamline_sequence` VALUES (1,'SEQ1','SEQ1_START','SEQ1_END','null',3.125345,NULL),(2,'SEQ2','SEQ2_START','SEQ2_END','SEQ1',6.587689218,NULL),(3,'SEQ3','SEQ3_START','SEQ3_END','SEQ2',2.099134,NULL),(4,'SEQ4','SEQ4_START','SEQ4_END','SEQ3',3.0834748,NULL),(5,'LINAC','LINAC_START','LINAC_END','null',11.74826,NULL),(6,'SEQ5','SEQ5_START','SEQ5_END','LINAC',1.4710667,NULL),(7,'SEQ6','SEQ6_START','SEQ6_END','LINAC',6.252455424,NULL),(8,'SRC2','SRC2_START','SRC2_END','null',0.993667065,NULL),(9,'SRC3','SRC3_START','SRC3_END','null',1.876411977,NULL);
/*!40000 ALTER TABLE `beamline_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `model_line`
--

DROP TABLE IF EXISTS `model_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `model_line` (
  `model_line_id` int(11) NOT NULL AUTO_INCREMENT,
  `model_line_name` varchar(45) DEFAULT NULL,
  `model_line_description` varchar(255) DEFAULT NULL,
  `start_position` double DEFAULT NULL,
  `end_position` double DEFAULT NULL,
  `start_marker` varchar(45) DEFAULT NULL,
  `end_marker` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`model_line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `model_line`
--

LOCK TABLES `model_line` WRITE;
/*!40000 ALTER TABLE `model_line` DISABLE KEYS */;
INSERT INTO `model_line` VALUES (1,'1','test line',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `model_line` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `element`
--

DROP TABLE IF EXISTS `element`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `element` (
  `element_id` int(11) NOT NULL AUTO_INCREMENT,
  `lattice_id` int(11) DEFAULT NULL,
  `element_type_id` int(11) DEFAULT NULL,
  `element_name` varchar(45) DEFAULT NULL,
  `element_order` int(11) DEFAULT NULL,
  `insert_date` datetime DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `s` double DEFAULT NULL,
  `len` double DEFAULT NULL,
  `dx` double DEFAULT '0',
  `dy` double DEFAULT '0',
  `dz` double DEFAULT '0',
  `pitch` double DEFAULT '0',
  `yaw` double DEFAULT '0',
  `roll` double DEFAULT '0',
  `sequence_id` int(11) DEFAULT NULL,
  `pos` double DEFAULT '0',
  PRIMARY KEY (`element_id`),
  KEY `FK_lattice_element_idx` (`lattice_id`),
  KEY `FK_element_type_idx` (`element_type_id`),
  KEY `FK_sequence_idx` (`sequence_id`),
  CONSTRAINT `FK_element_type` FOREIGN KEY (`element_type_id`) REFERENCES `element_type` (`element_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_lattice_element` FOREIGN KEY (`lattice_id`) REFERENCES `lattice` (`lattice_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_sequence` FOREIGN KEY (`sequence_id`) REFERENCES `beamline_sequence` (`beamline_sequence_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `element`
--

LOCK TABLES `element` WRITE;
/*!40000 ALTER TABLE `element` DISABLE KEYS */;
INSERT INTO `element` VALUES (1,1,15,'SEQ1_START',1,NULL,NULL,85.12185698,0,0,0,0,0,0,0,1,0),(2,1,37,'REA_BTS10:GV_D0851',2,NULL,NULL,85.13935698,0.035,0,0,0,0,0,0,1,0.0175),(3,1,36,'REA_BTS10:ATP_D0852',3,NULL,NULL,85.23592778,0,0,0,0,0,0,0,1,0.1140708),(4,1,23,'REA_BTS10:CAM_D0853',4,NULL,NULL,85.27428178,0,0,0,0,0,0,0,1,0.1524248),(5,1,42,'REA_BTS10:VD_D0853',5,NULL,NULL,85.27428178,0,0,0,0,0,0,0,1,0.1524248),(6,1,8,'REA_BTS10:FC_D0853',6,NULL,NULL,85.29211278,0,0,0,0,0,0,0,1,0.1702558),(7,1,9,'REA_BTS10:DCH_D0854',7,NULL,NULL,85.36031158,0.06,0,0,0,0,0,0,1,0.2384546),(8,1,10,'REA_BTS10:DCV_D0854',8,NULL,NULL,85.36031158,0.06,0,0,0,0,0,0,1,0.2384546),(9,1,14,'REA_BTS10:QVE_D0856',9,NULL,NULL,85.60827908,0.15,0,0,0,0,0,0,1,0.4864221);
/*!40000 ALTER TABLE `element` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `model_geometry`
--

DROP TABLE IF EXISTS `model_geometry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `model_geometry` (
  `model_geometry_id` int(11) NOT NULL AUTO_INCREMENT,
  `model_geometry_name` varchar(45) DEFAULT NULL,
  `model_geometry_description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`model_geometry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `model_geometry`
--

LOCK TABLES `model_geometry` WRITE;
/*!40000 ALTER TABLE `model_geometry` DISABLE KEYS */;
/*!40000 ALTER TABLE `model_geometry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lattice`
--

DROP TABLE IF EXISTS `lattice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lattice` (
  `lattice_id` int(11) NOT NULL AUTO_INCREMENT,
  `model_line_id` int(11) DEFAULT NULL,
  `machine_mode_id` int(11) DEFAULT NULL,
  `model_geometry_id` int(11) DEFAULT NULL,
  `lattice_name` varchar(255) DEFAULT NULL,
  `lattice_description` varchar(255) DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `updated_by` varchar(45) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`lattice_id`),
  KEY `FK_machine_mode_idx` (`machine_mode_id`),
  KEY `FK_model_line_idx` (`model_line_id`),
  KEY `FK_model_geometry_idx` (`model_geometry_id`),
  CONSTRAINT `FK_machine_mode` FOREIGN KEY (`machine_mode_id`) REFERENCES `machine_mode` (`machine_mode_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_model_geometry` FOREIGN KEY (`model_geometry_id`) REFERENCES `model_geometry` (`model_geometry_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_model_line` FOREIGN KEY (`model_line_id`) REFERENCES `model_line` (`model_line_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lattice`
--

LOCK TABLES `lattice` WRITE;
/*!40000 ALTER TABLE `lattice` DISABLE KEYS */;
INSERT INTO `lattice` VALUES (1,1,1,NULL,'test',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `lattice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gold_model`
--

DROP TABLE IF EXISTS `gold_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gold_model` (
  `gold_model_id` int(11) NOT NULL AUTO_INCREMENT,
  `model_id` int(11) DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `updated_by` varchar(45) DEFAULT NULL,
  `update_date` date DEFAULT NULL,
  `gold_status_ind` int(11) DEFAULT NULL,
  PRIMARY KEY (`gold_model_id`),
  KEY `FK_gold_model` (`model_id`),
  CONSTRAINT `FK_gold_model` FOREIGN KEY (`model_id`) REFERENCES `model` (`model_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gold_model`
--

LOCK TABLES `gold_model` WRITE;
/*!40000 ALTER TABLE `gold_model` DISABLE KEYS */;
/*!40000 ALTER TABLE `gold_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `element_type`
--

DROP TABLE IF EXISTS `element_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `element_type` (
  `element_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `element_type` varchar(45) DEFAULT NULL,
  `element_type_description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`element_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `element_type`
--

LOCK TABLES `element_type` WRITE;
/*!40000 ALTER TABLE `element_type` DISABLE KEYS */;
INSERT INTO `element_type` VALUES (1,'DH','horizontal bend dipole'),(2,'DV','vertical bend dipole'),(3,'SBH','horizontal sector bend dipole'),(4,'SBV','vertical sector bend dipole'),(5,'CBH','horizontal cylindrical bend dipole'),(6,'CBV','vertical cylindrical bend dipole'),(7,'BPM','beam position monitor'),(8,'FC','Faraday Cup'),(9,'DCH','horizontal dipole corrector'),(10,'DCV','vertical dipole corector'),(11,'QH','horizontal quadrupole'),(12,'QV','vertical quadrupole'),(13,'QHE','horizontal electrostatic quadrupole'),(14,'QVE','vertical electrostatic quadrupole'),(15,'MARK','marker'),(16,'SH','horizontal sextupole'),(17,'SV','vertical sextupole'),(18,'SSH','horizontal skew sextupole'),(19,'SSV','vertical skew sextupole'),(20,'QTH','horizontal trimmed quadrupole'),(21,'QTV','vertical trimmed quadrupole'),(22,'marker','marker'),(23,'CAM','camera'),(24,'SLH','horizontal slit'),(25,'SLV','vertical slit'),(26,'TID','timing detector'),(27,'MCPV',NULL),(28,'SDC',NULL),(29,'MHB',NULL),(30,'AP','aperture'),(31,'MCP','multi channel plate'),(32,'AND','anode'),(33,'EXT','extraction'),(34,'ELCT',NULL),(35,'WF','Wien filter'),(36,'ATP','Attenuation Plate (sieve to reduce beam intensity)'),(37,'GV','gate valve'),(38,'EIN','Einzel lens'),(39,'FIL','Filament'),(40,'FSD',NULL),(41,'HVP','High Voltage Platform'),(42,'VD','View Detector');
/*!40000 ALTER TABLE `element_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `element_prop`
--

DROP TABLE IF EXISTS `element_prop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `element_prop` (
  `element_prop_id` int(11) NOT NULL AUTO_INCREMENT,
  `element_id` int(11) DEFAULT NULL,
  `element_type_prop_id` int(11) DEFAULT NULL,
  `element_prop_string` varchar(255) DEFAULT NULL,
  `element_prop_int` int(11) DEFAULT NULL,
  `element_prop_double` double DEFAULT NULL,
  `element_prop_index` int(11) DEFAULT NULL,
  `prop_category` varchar(45) DEFAULT NULL,
  `element_prop_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`element_prop_id`),
  KEY `FK_element_id_idx` (`element_id`),
  KEY `FK_element_prop_type` (`element_type_prop_id`),
  CONSTRAINT `FK_element_id` FOREIGN KEY (`element_id`) REFERENCES `element` (`element_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_element_prop_type` FOREIGN KEY (`element_type_prop_id`) REFERENCES `element_type_prop` (`element_type_prop_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `element_prop`
--

LOCK TABLES `element_prop` WRITE;
/*!40000 ALTER TABLE `element_prop` DISABLE KEYS */;
INSERT INTO `element_prop` VALUES (1,9,NULL,NULL,NULL,-2.646994073,NULL,'magnet','dfltMagFld'),(2,9,NULL,NULL,NULL,0.15,NULL,'magnet','len'),(3,9,NULL,NULL,-1,NULL,NULL,'magnet','polarity'),(4,9,NULL,NULL,NULL,0.05,NULL,'aperture','x');
/*!40000 ALTER TABLE `element_prop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `model`
--

DROP TABLE IF EXISTS `model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `model` (
  `model_id` int(11) NOT NULL AUTO_INCREMENT,
  `lattice_id` int(11) DEFAULT NULL,
  `model_code_id` int(11) DEFAULT NULL,
  `model_name` varchar(255) DEFAULT NULL,
  `model_desc` varchar(255) DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `updated_by` varchar(45) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `tune_x` double DEFAULT NULL,
  `tune_y` double DEFAULT NULL,
  `chrome_x_0` double DEFAULT NULL,
  `chrome_x_1` double DEFAULT NULL,
  `chrome_x_2` double DEFAULT NULL,
  `chrome_y_0` double DEFAULT NULL,
  `chrome_y_1` double DEFAULT NULL,
  `chrome_y_2` double DEFAULT NULL,
  `final_beam_energy` double DEFAULT NULL,
  PRIMARY KEY (`model_id`),
  KEY `FK_model_code_idx` (`model_code_id`),
  KEY `FK_lattice_idx` (`lattice_id`),
  CONSTRAINT `FK_lattice` FOREIGN KEY (`lattice_id`) REFERENCES `lattice` (`lattice_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_model_code` FOREIGN KEY (`model_code_id`) REFERENCES `model_code` (`model_code_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `model`
--

LOCK TABLES `model` WRITE;
/*!40000 ALTER TABLE `model` DISABLE KEYS */;
/*!40000 ALTER TABLE `model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `beam_parameter`
--

DROP TABLE IF EXISTS `beam_parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `beam_parameter` (
  `twiss_id` int(11) NOT NULL AUTO_INCREMENT,
  `element_id` int(11) DEFAULT NULL,
  `model_id` int(11) DEFAULT NULL,
  `pos` double DEFAULT NULL,
  `alpha_x` double DEFAULT NULL,
  `beta_x` double DEFAULT NULL,
  `nu_x` double DEFAULT NULL,
  `eta_x` double DEFAULT NULL,
  `etap_x` double DEFAULT NULL,
  `alpha_y` double DEFAULT NULL,
  `beta_y` double DEFAULT NULL,
  `nu_y` double DEFAULT NULL,
  `eta_y` double DEFAULT NULL,
  `etap_y` double DEFAULT NULL,
  `transfer_matrix` varchar(2047) DEFAULT NULL,
  `co_x` double DEFAULT NULL,
  `co_y` double DEFAULT NULL,
  `index_slice_chk` int(11) DEFAULT NULL,
  `energy` double DEFAULT NULL,
  `particle_species` varchar(45) DEFAULT NULL,
  `particle_mass` double DEFAULT NULL,
  `particle_charge` int(11) DEFAULT NULL,
  `beam_charge_density` double DEFAULT NULL,
  `beam_current` double DEFAULT '0',
  `x` double DEFAULT NULL,
  `xp` double DEFAULT NULL,
  `y` double DEFAULT NULL,
  `yp` double DEFAULT NULL,
  `z` double DEFAULT NULL,
  `zp` double DEFAULT NULL,
  `emit_x` double DEFAULT NULL,
  `emit_y` double DEFAULT NULL,
  `emit_z` double DEFAULT NULL,
  `psi_x` double DEFAULT NULL,
  `psi_y` double DEFAULT NULL,
  `nu_s` double DEFAULT NULL,
  PRIMARY KEY (`twiss_id`),
  KEY `FK_element_id_idx` (`element_id`),
  KEY `FK_twiss_model_id_idx` (`model_id`),
  KEY `FK_element` (`element_id`),
  KEY `FK_model` (`model_id`),
  CONSTRAINT `FK_element` FOREIGN KEY (`element_id`) REFERENCES `element` (`element_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_model` FOREIGN KEY (`model_id`) REFERENCES `model` (`model_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `beam_parameter`
--

LOCK TABLES `beam_parameter` WRITE;
/*!40000 ALTER TABLE `beam_parameter` DISABLE KEYS */;
/*!40000 ALTER TABLE `beam_parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `element_type_prop`
--

DROP TABLE IF EXISTS `element_type_prop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `element_type_prop` (
  `element_type_prop_id` int(11) NOT NULL AUTO_INCREMENT,
  `element_type_id` int(11) DEFAULT NULL,
  `element_type_prop_name` varchar(45) DEFAULT NULL,
  `element_type_prop_description` varchar(255) DEFAULT NULL,
  `element_type_prop_default` varchar(255) DEFAULT NULL,
  `element_type_prop_unit` varchar(45) DEFAULT NULL,
  `element_type_prop_datatype` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`element_type_prop_id`),
  KEY `FK_element_type_idx` (`element_type_id`),
  CONSTRAINT `FK_element_type_id` FOREIGN KEY (`element_type_id`) REFERENCES `element_type` (`element_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `element_type_prop`
--

LOCK TABLES `element_type_prop` WRITE;
/*!40000 ALTER TABLE `element_type_prop` DISABLE KEYS */;
INSERT INTO `element_type_prop` VALUES (1,1,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Bend',NULL,'String'),(2,2,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Bend',NULL,'String'),(3,3,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Bend',NULL,'String'),(4,4,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Bend',NULL,'String'),(5,5,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Bend',NULL,'String'),(6,6,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Bend',NULL,'String'),(7,7,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.BPM',NULL,'String'),(8,8,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Marker',NULL,'String'),(9,9,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.HDipoleCorr',NULL,'String'),(10,10,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.VDipoleCorr',NULL,'String'),(11,11,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Quadrupole',NULL,'String'),(12,12,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Quadrupole',NULL,'String'),(13,13,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.EQuad',NULL,'String'),(14,14,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.EQuad',NULL,'String'),(15,15,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Marker',NULL,'String'),(16,16,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Sextupole',NULL,'String'),(17,17,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Sextupole',NULL,'String'),(18,18,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Sextupole',NULL,'String'),(19,19,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Sextupole',NULL,'String'),(20,20,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.TrimmedQuadrupole',NULL,'String'),(21,21,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.TrimmedQuadrupole',NULL,'String'),(22,22,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Marker',NULL,'String'),(23,23,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Marker',NULL,'String'),(24,24,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Marker',NULL,'String'),(25,25,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Marker',NULL,'String'),(26,26,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Marker',NULL,'String'),(27,27,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Marker',NULL,'String'),(28,28,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Marker',NULL,'String'),(29,29,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Marker',NULL,'String'),(30,30,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Marker',NULL,'String'),(31,31,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Marker',NULL,'String'),(32,32,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Marker',NULL,'String'),(33,33,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Marker',NULL,'String'),(34,34,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Marker',NULL,'String'),(35,35,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Marker',NULL,'String'),(36,36,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Marker',NULL,'String'),(37,37,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Marker',NULL,'String'),(38,38,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Marker',NULL,'String'),(39,39,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Marker',NULL,'String'),(40,40,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Marker',NULL,'String'),(41,41,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Marker',NULL,'String'),(42,42,'XAL_class_mapping','map device type to modeling type','gov.sns.xal.smf.impl.Marker',NULL,'String');
/*!40000 ALTER TABLE `element_type_prop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `element_install_device`
--

DROP TABLE IF EXISTS `element_install_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `element_install_device` (
  `element__install_id` int(11) NOT NULL AUTO_INCREMENT,
  `element_id` int(11) DEFAULT NULL,
  `install_id` int(11) DEFAULT NULL,
  `slice` int(11) DEFAULT NULL,
  `index` int(11) DEFAULT NULL,
  PRIMARY KEY (`element__install_id`),
  KEY `FK_element_id_idx` (`element_id`),
  CONSTRAINT `FK_element_install` FOREIGN KEY (`element_id`) REFERENCES `element` (`element_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `element_install_device`
--

LOCK TABLES `element_install_device` WRITE;
/*!40000 ALTER TABLE `element_install_device` DISABLE KEYS */;
/*!40000 ALTER TABLE `element_install_device` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-10-09 23:34:10
