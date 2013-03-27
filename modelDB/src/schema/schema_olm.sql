SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `discs_model` DEFAULT CHARACTER SET utf8 ;
USE `discs_model` ;

-- -----------------------------------------------------
-- Table `discs_model`.`particle_type`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `discs_model`.`particle_type` (
  `particle_type_id` INT(11) NOT NULL ,
  `particle_name` VARCHAR(45) NULL DEFAULT NULL ,
  `particle_mass` DOUBLE NULL DEFAULT NULL ,
  `particle_charge` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (`particle_type_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `discs_model`.`beamline_sequence`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `discs_model`.`beamline_sequence` (
  `beamline_sequence_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `sequence_name` VARCHAR(45) NULL DEFAULT NULL ,
  `first_element_name` VARCHAR(45) NULL DEFAULT NULL ,
  `last_element_name` VARCHAR(45) NULL DEFAULT NULL ,
  `predecessor_sequence` VARCHAR(255) NULL DEFAULT NULL ,
  `sequence_length` DOUBLE NULL DEFAULT NULL ,
  `sequence_description` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`beamline_sequence_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `discs_model`.`element_type`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `discs_model`.`element_type` (
  `element_type_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `element_type` VARCHAR(45) NULL DEFAULT NULL ,
  `element_type_description` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`element_type_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `discs_model`.`element`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `discs_model`.`element` (
  `element_id` INT(11) NOT NULL AUTO_INCREMENT ,
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
  `pos` DOUBLE NULL DEFAULT '0' ,
  `beamline_sequence_id` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (`element_id`) ,
  INDEX `FK_element_type_idx` (`element_type_id` ASC) ,
  INDEX `FK_beamline_sequence_id` (`beamline_sequence_id` ASC) ,
  CONSTRAINT `FK_beamline_sequence_id`
    FOREIGN KEY (`beamline_sequence_id` )
    REFERENCES `discs_model`.`beamline_sequence` (`beamline_sequence_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_element_type`
    FOREIGN KEY (`element_type_id` )
    REFERENCES `discs_model`.`element_type` (`element_type_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `discs_model`.`lattice`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `discs_model`.`lattice` (
  `lattice_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `lattice_name` VARCHAR(255) NULL DEFAULT NULL ,
  `lattice_description` VARCHAR(255) NULL DEFAULT NULL ,
  `created_by` VARCHAR(45) NULL DEFAULT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `updated_by` VARCHAR(45) NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`lattice_id`) )
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `discs_model`.`machine_mode`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `discs_model`.`machine_mode` (
  `machine_mode_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `machine_mode_name` VARCHAR(45) NULL DEFAULT NULL ,
  `machine_mode_description` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`machine_mode_id`) )
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `discs_model`.`model_code`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `discs_model`.`model_code` (
  `model_code_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `code_name` VARCHAR(45) NULL DEFAULT NULL ,
  `algorithm` VARCHAR(45) NULL DEFAULT NULL ,
  PRIMARY KEY (`model_code_id`) )
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `discs_model`.`model_geometry`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `discs_model`.`model_geometry` (
  `model_geometry_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `model_geometry_name` VARCHAR(45) NULL DEFAULT NULL ,
  `model_geometry_description` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`model_geometry_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `discs_model`.`model_line`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `discs_model`.`model_line` (
  `model_line_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `model_line_name` VARCHAR(45) NULL DEFAULT NULL ,
  `model_line_description` VARCHAR(255) NULL DEFAULT NULL ,
  `start_position` DOUBLE NULL DEFAULT NULL ,
  `end_position` DOUBLE NULL DEFAULT NULL ,
  `start_marker` VARCHAR(45) NULL DEFAULT NULL ,
  `end_marker` VARCHAR(45) NULL DEFAULT NULL ,
  PRIMARY KEY (`model_line_id`) )
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `discs_model`.`model`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `discs_model`.`model` (
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
  `model_line_id` INT(11) NULL DEFAULT NULL ,
  `machine_mode_id` INT(11) NULL DEFAULT NULL ,
  `model_geometry_id` INT(11) NULL DEFAULT NULL ,
  `initial_condition_ind` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (`model_id`) ,
  INDEX `FK_model_code_idx` (`model_code_id` ASC) ,
  INDEX `FK_lattice_idx` (`lattice_id` ASC) ,
  INDEX `FK_model_line_id` (`model_line_id` ASC) ,
  INDEX `FK_model_geometry_id` (`model_geometry_id` ASC) ,
  INDEX `FK_machine_model_id` (`machine_mode_id` ASC) ,
  INDEX `FK_model_code_id` (`model_code_id` ASC) ,
  CONSTRAINT `FK_lattice`
    FOREIGN KEY (`lattice_id` )
    REFERENCES `discs_model`.`lattice` (`lattice_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_machine_model_id`
    FOREIGN KEY (`machine_mode_id` )
    REFERENCES `discs_model`.`machine_mode` (`machine_mode_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_model_code_id`
    FOREIGN KEY (`model_code_id` )
    REFERENCES `discs_model`.`model_code` (`model_code_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_model_geometry_id`
    FOREIGN KEY (`model_geometry_id` )
    REFERENCES `discs_model`.`model_geometry` (`model_geometry_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_model_line_id`
    FOREIGN KEY (`model_line_id` )
    REFERENCES `discs_model`.`model_line` (`model_line_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `discs_model`.`beam_parameter`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `discs_model`.`beam_parameter` (
  `twiss_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `element_id` INT(11) NULL DEFAULT NULL ,
  `model_id` INT(11) NULL DEFAULT NULL ,
  `particle_type` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (`twiss_id`) ,
  INDEX `FK_element_id_idx` (`element_id` ASC) ,
  INDEX `FK_twiss_model_id_idx` (`model_id` ASC) ,
  INDEX `FK_element` (`element_id` ASC) ,
  INDEX `FK_model` (`model_id` ASC) ,
  INDEX `FK_particle_type` (`particle_type` ASC) ,
  CONSTRAINT `FK_particle_type`
    FOREIGN KEY (`particle_type` )
    REFERENCES `discs_model`.`particle_type` (`particle_type_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_element`
    FOREIGN KEY (`element_id` )
    REFERENCES `discs_model`.`element` (`element_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_model`
    FOREIGN KEY (`model_id` )
    REFERENCES `discs_model`.`model` (`model_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `discs_model`.`beam_parameter_prop`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `discs_model`.`beam_parameter_prop` (
  `beam_parameter_prop_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `beam_parameter_id` INT(11) NULL DEFAULT NULL ,
  `property_name` VARCHAR(45) NULL DEFAULT NULL ,
  `prop_category` VARCHAR(45) NULL DEFAULT NULL ,
  `property_datatype` VARCHAR(45) NULL DEFAULT NULL ,
  `description` VARCHAR(45) NULL DEFAULT NULL ,
  `beam_parameter_int` INT(11) NULL DEFAULT NULL ,
  `beam_parameter_double` DOUBLE NULL DEFAULT NULL ,
  `beam_parameter_string` VARCHAR(45) NULL DEFAULT NULL ,
  `trnsfer_matrix` VARCHAR(2047) NULL DEFAULT NULL ,
  PRIMARY KEY (`beam_parameter_prop_id`) ,
  INDEX `FK_beam_parameter` (`beam_parameter_id` ASC) ,
  INDEX `FK_beam_parameter_id` (`beam_parameter_id` ASC) ,
  CONSTRAINT `FK_beam_parameter_id`
    FOREIGN KEY (`beam_parameter_id` )
    REFERENCES `discs_model`.`beam_parameter` (`twiss_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 33
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `discs_model`.`blsequence_lattice`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `discs_model`.`blsequence_lattice` (
  `blsequence_lattice_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `beamline_order` INT(11) NULL DEFAULT NULL ,
  `beamline_sequence_id` INT(11) NULL DEFAULT NULL ,
  `lattice_id` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (`blsequence_lattice_id`) ,
  INDEX `FK_blseq_lattice_id` (`lattice_id` ASC) ,
  INDEX `FK_blseq_beamlineSeq_id` (`beamline_sequence_id` ASC) ,
  CONSTRAINT `FK_blseq_beamlineSeq_id`
    FOREIGN KEY (`beamline_sequence_id` )
    REFERENCES `discs_model`.`beamline_sequence` (`beamline_sequence_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_blseq_lattice_id`
    FOREIGN KEY (`lattice_id` )
    REFERENCES `discs_model`.`lattice` (`lattice_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `discs_model`.`element_install_device`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `discs_model`.`element_install_device` (
  `element__install_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `element_id` INT(11) NULL DEFAULT NULL ,
  `install_id` INT(11) NULL DEFAULT NULL ,
  `slice` INT(11) NULL DEFAULT NULL ,
  `index` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (`element__install_id`) ,
  INDEX `FK_element_id_idx` (`element_id` ASC) ,
  CONSTRAINT `FK_element_install`
    FOREIGN KEY (`element_id` )
    REFERENCES `discs_model`.`element` (`element_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `discs_model`.`element_type_prop`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `discs_model`.`element_type_prop` (
  `element_type_prop_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `element_type_id` INT(11) NULL DEFAULT NULL ,
  `element_type_prop_name` VARCHAR(45) NULL DEFAULT NULL ,
  `element_type_prop_description` VARCHAR(255) NULL DEFAULT NULL ,
  `element_type_prop_default` VARCHAR(255) NULL DEFAULT NULL ,
  `element_type_prop_unit` VARCHAR(45) NULL DEFAULT NULL ,
  `element_type_prop_datatype` VARCHAR(45) NULL DEFAULT NULL ,
  PRIMARY KEY (`element_type_prop_id`) ,
  INDEX `FK_element_type_idx` (`element_type_id` ASC) ,
  CONSTRAINT `FK_element_type_id`
    FOREIGN KEY (`element_type_id` )
    REFERENCES `discs_model`.`element_type` (`element_type_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `discs_model`.`element_prop`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `discs_model`.`element_prop` (
  `element_prop_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `element_id` INT(11) NULL DEFAULT NULL ,
  `element_type_prop_id` INT(11) NULL DEFAULT NULL ,
  `element_prop_string` VARCHAR(255) NULL DEFAULT NULL ,
  `element_prop_int` INT(11) NULL DEFAULT NULL ,
  `element_prop_double` DOUBLE NULL DEFAULT NULL ,
  `element_prop_index` INT(11) NULL DEFAULT NULL ,
  `prop_category` VARCHAR(45) NULL DEFAULT NULL ,
  `element_prop_name` VARCHAR(45) NULL DEFAULT NULL ,
  `element_prop_datatype` VARCHAR(45) NULL DEFAULT NULL ,
  `lattice_id` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (`element_prop_id`) ,
  INDEX `FK_element_id_idx` (`element_id` ASC) ,
  INDEX `FK_element_prop_type` (`element_type_prop_id` ASC) ,
  INDEX `FK_lattice_id` (`lattice_id` ASC) ,
  CONSTRAINT `FK_element_id`
    FOREIGN KEY (`element_id` )
    REFERENCES `discs_model`.`element` (`element_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_element_prop_type`
    FOREIGN KEY (`element_type_prop_id` )
    REFERENCES `discs_model`.`element_type_prop` (`element_type_prop_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_lattice_id`
    FOREIGN KEY (`lattice_id` )
    REFERENCES `discs_model`.`lattice` (`lattice_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `discs_model`.`gold_lattice`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `discs_model`.`gold_lattice` (
  `gold_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `lattice_id` INT(11) NULL DEFAULT NULL ,
  `machine_mode_id` VARCHAR(45) NULL DEFAULT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `updated_by` VARCHAR(45) NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `gold_status_ind` INT(11) NULL DEFAULT NULL ,
  `created_by` VARCHAR(45) NULL DEFAULT NULL ,
  PRIMARY KEY (`gold_id`) ,
  INDEX `FK_gold_lattice_id_idx` (`lattice_id` ASC) ,
  CONSTRAINT `FK_gold_lattice_id`
    FOREIGN KEY (`lattice_id` )
    REFERENCES `discs_model`.`lattice` (`lattice_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `discs_model`.`gold_model`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `discs_model`.`gold_model` (
  `gold_model_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `model_id` INT(11) NULL DEFAULT NULL ,
  `created_by` VARCHAR(45) NULL DEFAULT NULL ,
  `create_date` DATE NULL DEFAULT NULL ,
  `updated_by` VARCHAR(45) NULL DEFAULT NULL ,
  `update_date` DATE NULL DEFAULT NULL ,
  `gold_status_ind` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (`gold_model_id`) ,
  INDEX `FK_gold_model` (`model_id` ASC) ,
  CONSTRAINT `FK_gold_model`
    FOREIGN KEY (`model_id` )
    REFERENCES `discs_model`.`model` (`model_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `discs_model`.`rf_gap`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `discs_model`.`rf_gap` (
  `rf_gap_id` INT(11) NOT NULL AUTO_INCREMENT ,
  `cavity_id` INT(11) NULL DEFAULT NULL ,
  `pos` DOUBLE NULL DEFAULT NULL ,
  `TTF` DOUBLE NULL DEFAULT NULL ,
  `ampFactor` DOUBLE NULL DEFAULT NULL ,
  `endCell_ind` INT(11) NULL DEFAULT NULL ,
  `gapOffset` DOUBLE NULL DEFAULT NULL ,
  `len` DOUBLE NULL DEFAULT NULL ,
  `phaseFactor` DOUBLE NULL DEFAULT NULL ,
  `gap_name` VARCHAR(45) NULL DEFAULT NULL ,
  PRIMARY KEY (`rf_gap_id`) ,
  INDEX `FK_cavity_id` (`cavity_id` ASC) ,
  CONSTRAINT `FK_cavity_id`
    FOREIGN KEY (`cavity_id` )
    REFERENCES `discs_model`.`element` (`element_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
