-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema spring_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema spring_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `spring_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `spring_db` ;



-- -----------------------------------------------------
-- Table `spring_db`.`hibernate_sequence`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `spring_db`.`hibernate_sequence` (
  `next_val` BIGINT(20) NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `spring_db`.`item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `spring_db`.`item` (
  `id` INT(11) NOT NULL,
  `price` FLOAT NOT NULL,
  `name` VARCHAR(200) NOT NULL,
  `pic` VARCHAR(200) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  `description` TEXT NULL DEFAULT NULL,
  `gender` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `spring_db`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `spring_db`.`user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 17
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `spring_db`.`storage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `spring_db`.`storage` (
  `size` FLOAT NOT NULL,
  `volume` INT(11) NOT NULL,
  `item_id` INT(11) NOT NULL,
  `pair_id` INT(11) NOT NULL,
  PRIMARY KEY (`item_id`, `pair_id`),
  UNIQUE INDEX `UK_njdr8893d3coqvmenjiadghnp` (`item_id` ASC, `pair_id` ASC) VISIBLE,
  CONSTRAINT `fk_storage_item`
    FOREIGN KEY (`item_id`)
    REFERENCES `spring_db`.`item` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `spring_db`.`reserved_items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `spring_db`.`reserved_items` (
  `id` INT(11) NOT NULL,
  `date` DATETIME(6) NOT NULL,
  `id_user` INT(11) NOT NULL,
  `storage_item_id` INT(11) NOT NULL,
  `storage_pair_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK11sj18jo7eq7odvwy5yc5rm6k` (`id_user` ASC) VISIBLE,
  INDEX `FKirsk82bykh64966k4dwe7uxlp` (`storage_item_id` ASC, `storage_pair_id` ASC) VISIBLE,
  CONSTRAINT `FK11sj18jo7eq7odvwy5yc5rm6k`
    FOREIGN KEY (`id_user`)
    REFERENCES `spring_db`.`user` (`id`),
  CONSTRAINT `FKirsk82bykh64966k4dwe7uxlp`
    FOREIGN KEY (`storage_item_id` , `storage_pair_id`)
    REFERENCES `spring_db`.`storage` (`item_id` , `pair_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `spring_db`.`sold_items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `spring_db`.`sold_items` (
  `id` INT(11) NOT NULL,
  `date` DATETIME(6) NULL DEFAULT NULL,
  `price` DOUBLE NOT NULL,
  `id_user` INT(11) NULL DEFAULT NULL,
  `storage_item_id` INT(11) NOT NULL,
  `storage_pair_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKj02yoyd2mkmhbivjkk8xcfntr` (`id_user` ASC) VISIBLE,
  INDEX `FK519q3pj1b69i1wl8d7xri0hs7` (`storage_item_id` ASC, `storage_pair_id` ASC) VISIBLE,
  CONSTRAINT `FK519q3pj1b69i1wl8d7xri0hs7`
    FOREIGN KEY (`storage_item_id` , `storage_pair_id`)
    REFERENCES `spring_db`.`storage` (`item_id` , `pair_id`),
  CONSTRAINT `FKj02yoyd2mkmhbivjkk8xcfntr`
    FOREIGN KEY (`id_user`)
    REFERENCES `spring_db`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
