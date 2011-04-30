SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `rdi1` DEFAULT CHARACTER SET utf8 ;
USE `rdi1` ;

-- -----------------------------------------------------
-- Table `rdi1`.`Patient`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rdi1`.`Patient` ;

CREATE  TABLE IF NOT EXISTS `rdi1`.`Patient` (
  `patient_id` INT NOT NULL AUTO_INCREMENT ,
  `username` VARCHAR(30) NULL ,
  `firstName` VARCHAR(30) NULL ,
  `lastName` VARCHAR(30) NULL ,
  `gender` CHAR(1) NULL ,
  `email` VARCHAR(45) NULL ,
  `phone` VARCHAR(10) NULL ,
  `password` VARCHAR(45) NULL ,
  PRIMARY KEY (`patient_id`) ,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rdi1`.`Doctor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rdi1`.`Doctor` ;

CREATE  TABLE IF NOT EXISTS `rdi1`.`Doctor` (
  `doctor_id` INT NOT NULL AUTO_INCREMENT ,
  `username` VARCHAR(30) NULL ,
  `firstName` VARCHAR(30) NULL ,
  `lastName` VARCHAR(30) NULL ,
  `gender` CHAR(1) NULL ,
  `email` VARCHAR(45) NULL ,
  `phone` VARCHAR(10) NULL ,
  `password` VARCHAR(45) NULL ,
  PRIMARY KEY (`doctor_id`) ,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `rdi1`.`Appointment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rdi1`.`Appointment` ;

CREATE  TABLE IF NOT EXISTS `rdi1`.`Appointment` (
  `appointment_id` INT NOT NULL AUTO_INCREMENT ,
  `doctor_id` INT NOT NULL ,
  `patient_id` INT NOT NULL ,
  `date` DATETIME NULL ,
  `duration` INT NULL ,
  PRIMARY KEY (`appointment_id`) ,
  INDEX `appointment_patient_FK` (`patient_id` ASC) ,
  INDEX `appointment_doctor_FK` (`doctor_id` ASC) ,
  CONSTRAINT `appointment_patient_FK`
    FOREIGN KEY (`patient_id` )
    REFERENCES `rdi1`.`Patient` (`patient_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `appointment_doctor_FK`
    FOREIGN KEY (`doctor_id` )
    REFERENCES `rdi1`.`Doctor` (`doctor_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
