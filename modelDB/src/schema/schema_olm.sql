SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `model` DEFAULT CHARACTER SET utf8 ;
USE `model` ;

-- -----------------------------------------------------
-- Table `model`.`element_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `model`.`element_type` ;

CREATE  TABLE IF NOT EXISTS `model`.`element_type` (
  `element_type_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `element_type` VARCHAR(45) NULL DEFAULT NULL ,
  `element_type_description` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`element_type_id`) )
ENGINE = InnoDB
AUTO_INCREMENT = 36
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `model`.`machine_mode`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `model`.`machine_mode` ;

CREATE  TABLE IF NOT EXISTS `model`.`machine_mode` (
  `machine_mode_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `machine_mode_name` VARCHAR(45) NULL DEFAULT NULL ,
  `machine_mode_description` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`machine_mode_id`) )
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `model`.`model_geometry`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `model`.`model_geometry` ;

CREATE  TABLE IF NOT EXISTS `model`.`model_geometry` (
  `model_geometry_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `model_geometry_name` VARCHAR(45) NULL DEFAULT NULL ,
  `model_geometry_description` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`model_geometry_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `model`.`model_line`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `model`.`model_line` ;

CREATE  TABLE IF NOT EXISTS `model`.`model_line` (
  `model_line_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `model_line_name` VARCHAR(45) NULL DEFAULT NULL ,
  `model_line_description` VARCHAR(255) NULL DEFAULT NULL ,
  `start_position` DOUBLE NULL DEFAULT NULL ,
  `end_position` DOUBLE NULL DEFAULT NULL ,
  `start_marker` VARCHAR(45) NULL DEFAULT NULL ,
  `end_marker` VARCHAR(45) NULL DEFAULT NULL ,
  `predecessors` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`model_line_id`) )
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `model`.`lattice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `model`.`lattice` ;

CREATE  TABLE IF NOT EXISTS `model`.`lattice` (
  `lattice_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `model_line_id` INT(11) NULL DEFAULT NULL ,
  `machine_mode_id` INT(11) NULL DEFAULT NULL ,
  `model_geometry_id` INT(11) NULL DEFAULT NULL ,
  `lattice_name` VARCHAR(255) NULL DEFAULT NULL ,
  `lattice_description` VARCHAR(255) NULL DEFAULT NULL ,
  `created_by` VARCHAR(45) NULL DEFAULT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `updated_by` VARCHAR(45) NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`lattice_id`) ,
  INDEX `FK_machine_mode_idx` (`machine_mode_id` ASC) ,
  INDEX `FK_model_line_idx` (`model_line_id` ASC) ,
  INDEX `FK_model_geometry_idx` (`model_geometry_id` ASC) ,
  CONSTRAINT `FK_lattice_machine_mode_id`
    FOREIGN KEY (`machine_mode_id` )
    REFERENCES `model`.`machine_mode` (`machine_mode_id` ),
  CONSTRAINT `FK_lattice_model_geometry_id`
    FOREIGN KEY (`model_geometry_id` )
    REFERENCES `model`.`model_geometry` (`model_geometry_id` ),
  CONSTRAINT `FK_lattice_model_line_id`
    FOREIGN KEY (`model_line_id` )
    REFERENCES `model`.`model_line` (`model_line_id` ))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `model`.`beamline_sequence`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `model`.`beamline_sequence` ;

CREATE  TABLE IF NOT EXISTS `model`.`beamline_sequence` (
  `beamline_sequence_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `sequence_name` VARCHAR(45) NULL DEFAULT NULL ,
  `first_element_name` VARCHAR(45) NULL DEFAULT NULL ,
  `last_element_name` VARCHAR(45) NULL DEFAULT NULL ,
  `predecessor_sequence` VARCHAR(45) NULL DEFAULT NULL ,
  `sequence_length` DOUBLE NULL DEFAULT NULL ,
  `sequence_description` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`beamline_sequence_id`) )
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `model`.`element`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `model`.`element` ;

CREATE  TABLE IF NOT EXISTS `model`.`element` (
  `element_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `lattice_id` INT(11) NULL DEFAULT NULL ,
  `element_type_id` INT(11) NULL DEFAULT NULL ,
  `element_name` VARCHAR(45) NULL DEFAULT NULL ,
  `element_order` INT(11) NULL DEFAULT NULL ,
  `insert_date` DATETIME NULL DEFAULT NULL ,
  `created_by` VARCHAR(45) NULL DEFAULT NULL ,
  `s` DOUBLE NULL DEFAULT NULL ,
  `len` DOUBLE NULL DEFAULT NULL ,
  `dx` DOUBLE NULL DEFAULT '0' ,
  `dy` DOUBLE NULL DEFAULT '0' ,
  `dz` DOUBLE NULL DEFAULT '0' ,
  `pitch` DOUBLE NULL DEFAULT '0' ,
  `yaw` DOUBLE NULL DEFAULT '0' ,
  `roll` DOUBLE NULL DEFAULT '0' ,
  `sequence_id` INT(11) NULL DEFAULT NULL ,
  `pos` INT NULL ,
  PRIMARY KEY (`element_id`) ,
  INDEX `FK_lattice_element_idx` (`lattice_id` ASC) ,
  INDEX `FK_element_type_idx` (`element_type_id` ASC) ,
  INDEX `FK_sequence_idx` (`sequence_id` ASC) ,
  CONSTRAINT `FK_element_element_type_id`
    FOREIGN KEY (`element_type_id` )
    REFERENCES `model`.`element_type` (`element_type_id` ),
  CONSTRAINT `FK_element_lattice_id`
    FOREIGN KEY (`lattice_id` )
    REFERENCES `model`.`lattice` (`lattice_id` ),
  CONSTRAINT `FK_element_sequence_id`
    FOREIGN KEY (`sequence_id` )
    REFERENCES `model`.`beamline_sequence` (`beamline_sequence_id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `model`.`model_code`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `model`.`model_code` ;

CREATE  TABLE IF NOT EXISTS `model`.`model_code` (
  `model_code_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `code_name` VARCHAR(45) NULL DEFAULT NULL ,
  `algorithm` VARCHAR(45) NULL DEFAULT NULL ,
  PRIMARY KEY (`model_code_id`) )
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `model`.`model`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `model`.`model` ;

CREATE  TABLE IF NOT EXISTS `model`.`model` (
  `model_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `lattice_id` INT(11) NULL DEFAULT NULL ,
  `model_code_id` INT(11) NULL DEFAULT NULL ,
  `model_name` VARCHAR(255) NULL DEFAULT NULL ,
  `model_desc` VARCHAR(255) NULL DEFAULT NULL ,
  `created_by` VARCHAR(45) NULL DEFAULT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `updated_by` VARCHAR(45) NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `tune_x` DOUBLE NULL DEFAULT NULL ,
  `tune_y` DOUBLE NULL DEFAULT NULL ,
  `chrome_x_0` DOUBLE NULL DEFAULT NULL ,
  `chrome_x_1` DOUBLE NULL DEFAULT NULL ,
  `chrome_x_2` DOUBLE NULL DEFAULT NULL ,
  `chrome_y_0` DOUBLE NULL DEFAULT NULL ,
  `chrome_y_1` DOUBLE NULL DEFAULT NULL ,
  `chrome_y_2` DOUBLE NULL DEFAULT NULL ,
  `final_beam_energy` DOUBLE NULL DEFAULT NULL ,
  PRIMARY KEY (`model_id`) ,
  INDEX `FK_model_code_idx` (`model_code_id` ASC) ,
  INDEX `FK_lattice_idx` (`lattice_id` ASC) ,
  CONSTRAINT `FK_model_lattice_id`
    FOREIGN KEY (`lattice_id` )
    REFERENCES `model`.`lattice` (`lattice_id` ),
  CONSTRAINT `FK_model_model_code_id`
    FOREIGN KEY (`model_code_id` )
    REFERENCES `model`.`model_code` (`model_code_id` ))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `model`.`beam_parameter`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `model`.`beam_parameter` ;

CREATE  TABLE IF NOT EXISTS `model`.`beam_parameter` (
  `twiss_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `element_id` INT(11) NULL DEFAULT NULL ,
  `model_id` INT(11) NULL DEFAULT NULL ,
  `pos` DOUBLE NULL DEFAULT NULL ,
  `alpha_x` DOUBLE NULL DEFAULT NULL ,
  `beta_x` DOUBLE NULL DEFAULT NULL ,
  `nu_x` DOUBLE NULL DEFAULT NULL ,
  `eta_x` DOUBLE NULL DEFAULT NULL ,
  `etap_x` DOUBLE NULL DEFAULT NULL ,
  `alpha_y` DOUBLE NULL DEFAULT NULL ,
  `beta_y` DOUBLE NULL DEFAULT NULL ,
  `nu_y` DOUBLE NULL DEFAULT NULL ,
  `eta_y` DOUBLE NULL DEFAULT NULL ,
  `etap_y` DOUBLE NULL DEFAULT NULL ,
  `transfer_matrix` VARCHAR(2047) NULL DEFAULT NULL ,
  `co_x` DOUBLE NULL DEFAULT NULL ,
  `co_y` DOUBLE NULL DEFAULT NULL ,
  `index_slice_chk` INT(11) NULL DEFAULT NULL ,
  `energy` DOUBLE NULL DEFAULT NULL ,
  `particle_species` VARCHAR(45) NULL DEFAULT NULL ,
  `particle_mass` DOUBLE NULL DEFAULT NULL ,
  `particle_charge` INT(11) NULL DEFAULT NULL ,
  `beam_charge_density` DOUBLE NULL DEFAULT NULL ,
  `beam_current` DOUBLE NULL DEFAULT '0' ,
  `x` DOUBLE NULL DEFAULT NULL ,
  `xp` DOUBLE NULL DEFAULT NULL ,
  `y` DOUBLE NULL DEFAULT NULL ,
  `yp` DOUBLE NULL DEFAULT NULL ,
  `z` DOUBLE NULL DEFAULT NULL ,
  `zp` DOUBLE NULL DEFAULT NULL ,
  `emit_x` DOUBLE NULL DEFAULT NULL ,
  `emit_y` DOUBLE NULL DEFAULT NULL ,
  `emit_z` DOUBLE NULL DEFAULT NULL ,
  `psi_x` DOUBLE NULL DEFAULT NULL ,
  `psi_y` DOUBLE NULL DEFAULT NULL ,
  `nu_s` DOUBLE NULL DEFAULT NULL ,
  PRIMARY KEY (`twiss_id`) ,
  INDEX `FK_element` (`element_id` ASC) ,
  INDEX `FK_model` (`model_id` ASC) ,
  CONSTRAINT `FK_beam_parameter_element_id`
    FOREIGN KEY (`element_id` )
    REFERENCES `model`.`element` (`element_id` ),
  CONSTRAINT `FK_beam_parameter_model_id`
    FOREIGN KEY (`model_id` )
    REFERENCES `model`.`model` (`model_id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `model`.`element_install_device`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `model`.`element_install_device` ;

CREATE  TABLE IF NOT EXISTS `model`.`element_install_device` (
  `element_install_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `element_id` INT(11) NULL DEFAULT NULL ,
  `install_id` INT(11) NULL DEFAULT NULL ,
  `slice` INT(11) NULL DEFAULT NULL ,
  `index` INT(11) NULL DEFAULT NULL ,
  `device_name` VARCHAR(45) NULL DEFAULT NULL ,
  PRIMARY KEY (`element_install_id`) ,
  INDEX `FK_element_id_idx` (`element_id` ASC) ,
  CONSTRAINT `FK_element_install_device_element_id`
    FOREIGN KEY (`element_id` )
    REFERENCES `model`.`element` (`element_id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `model`.`element_type_prop`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `model`.`element_type_prop` ;

CREATE  TABLE IF NOT EXISTS `model`.`element_type_prop` (
  `element_type_prop_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `element_type_id` INT(11) NULL DEFAULT NULL ,
  `element_type_prop_name` VARCHAR(45) NULL DEFAULT NULL ,
  `element_type_prop_description` VARCHAR(255) NULL DEFAULT NULL ,
  `element_type_prop_default` VARCHAR(255) NULL DEFAULT NULL ,
  `element_type_prop_unit` VARCHAR(45) NULL DEFAULT NULL ,
  `element_type_prop_datatype` VARCHAR(45) NULL DEFAULT NULL ,
  PRIMARY KEY (`element_type_prop_id`) ,
  INDEX `FK_element_type_idx` (`element_type_id` ASC) ,
  CONSTRAINT `FK_element_type_prop_element_type_id`
    FOREIGN KEY (`element_type_id` )
    REFERENCES `model`.`element_type` (`element_type_id` ))
ENGINE = InnoDB
AUTO_INCREMENT = 36
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `model`.`element_prop`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `model`.`element_prop` ;

CREATE  TABLE IF NOT EXISTS `model`.`element_prop` (
  `element_prop_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `element_id` INT(11) NULL DEFAULT NULL ,
  `element_type_prop_id` INT(11) NULL DEFAULT NULL ,
  `element_prop_string` VARCHAR(255) NULL DEFAULT NULL ,
  `element_prop_int` INT(11) NULL DEFAULT NULL ,
  `element_prop_double` DOUBLE NULL DEFAULT NULL ,
  `element_prop_index` INT(11) NULL DEFAULT NULL ,
  `prop_category` VARCHAR(45) NULL ,
  `element_prop_name` VARCHAR(45) NULL ,
  PRIMARY KEY (`element_prop_id`) ,
  INDEX `FK_element_id_idx` (`element_id` ASC) ,
  INDEX `FK_element_prop_type` (`element_type_prop_id` ASC) ,
  CONSTRAINT `FK_element_prop_element_id`
    FOREIGN KEY (`element_id` )
    REFERENCES `model`.`element` (`element_id` ),
  CONSTRAINT `FK_element_prop_element_type_prop_id`
    FOREIGN KEY (`element_type_prop_id` )
    REFERENCES `model`.`element_type_prop` (`element_type_prop_id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `model`.`gold_lattice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `model`.`gold_lattice` ;

CREATE  TABLE IF NOT EXISTS `model`.`gold_lattice` (
  `gold_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `lattice_id` INT(11) NULL DEFAULT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `updated_by` VARCHAR(45) NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `gold_status_ind` INT(11) NULL DEFAULT NULL ,
  `created_by` VARCHAR(45) NULL ,
  PRIMARY KEY (`gold_id`) ,
  INDEX `FK_gold_lattice_id_idx` (`lattice_id` ASC) ,
  CONSTRAINT `FK_gold_lattice_lattice_id`
    FOREIGN KEY (`lattice_id` )
    REFERENCES `model`.`lattice` (`lattice_id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `model`.`gold_model`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `model`.`gold_model` ;

CREATE  TABLE IF NOT EXISTS `model`.`gold_model` (
  `gold_model_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `model_id` INT(11) NULL DEFAULT NULL ,
  `created_by` VARCHAR(45) NULL DEFAULT NULL ,
  `create_date` DATE NULL DEFAULT NULL ,
  `updated_by` VARCHAR(45) NULL DEFAULT NULL ,
  `update_date` DATE NULL DEFAULT NULL ,
  `gold_status_ind` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (`gold_model_id`) ,
  INDEX `FK_gold_model` (`model_id` ASC) ,
  CONSTRAINT `FK_gold_model_model_id`
    FOREIGN KEY (`model_id` )
    REFERENCES `model`.`model` (`model_id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
