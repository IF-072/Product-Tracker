-- MySQL Workbench Forward Engineering
-- 26/02/17


SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema if072java
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `if072java` ;

-- -----------------------------------------------------
-- Schema if072java
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `if072java` DEFAULT CHARACTER SET utf8 ;
USE `if072java` ;

-- -----------------------------------------------------
-- Table `role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `role` ;

CREATE TABLE IF NOT EXISTS `role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32) NOT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `name_UNIQUE` ON `role` (`name` ASC);


-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  `email` VARCHAR(64) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `is_enabled` TINYINT(1) NOT NULL,
  `role_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `role_id`),
  CONSTRAINT `user_roleID_fk`
    FOREIGN KEY (`role_id`)
    REFERENCES `role` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `login_UNIQUE` ON `user` (`email` ASC);

CREATE INDEX `user_roleID_idx` ON `user` (`role_id` ASC);


-- -----------------------------------------------------
-- Table `category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `category` ;

CREATE TABLE IF NOT EXISTS `category` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  `user_id` INT(11) NULL DEFAULT NULL,
  `is_enabled` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`, `name`),
  CONSTRAINT `category_userID_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `name_userID_unique` ON `category` (`name` ASC, `user_id` ASC);

CREATE INDEX `userID_fk_idx` ON `category` (`user_id` ASC);


-- -----------------------------------------------------
-- Table `image`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `image` ;

CREATE TABLE IF NOT EXISTS `image` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `image_data` LONGBLOB NOT NULL,
  `content_type` VARCHAR(255) NULL DEFAULT NULL,
  `file_name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `unit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `unit` ;

CREATE TABLE IF NOT EXISTS `unit` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `name_UNIQUE` ON `unit` (`name` ASC);


-- -----------------------------------------------------
-- Table `product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `product` ;

CREATE TABLE IF NOT EXISTS `product` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `image_id` INT(11) NULL DEFAULT NULL,
  `user_id` INT(11) NULL DEFAULT NULL,
  `category_id` INT(11) NULL DEFAULT NULL,
  `unit_id` INT(11) NULL DEFAULT NULL,
  `is_enabled` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`, `name`),
  CONSTRAINT `product_categoryID_fk`
    FOREIGN KEY (`category_id`)
    REFERENCES `category` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `product_imageID_fk`
    FOREIGN KEY (`image_id`)
    REFERENCES `image` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `product_unitID_fk`
    FOREIGN KEY (`unit_id`)
    REFERENCES `unit` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `product_userID_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `userID_fk_idx` ON `product` (`user_id` ASC);

CREATE INDEX `unitID_fk_idx` ON `product` (`unit_id` ASC);

CREATE INDEX `product_image_id_idx` ON `product` (`image_id` ASC);

CREATE INDEX `product_categoryID_fk_idx` ON `product` (`category_id` ASC);


-- -----------------------------------------------------
-- Table `store`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `store` ;

CREATE TABLE IF NOT EXISTS `store` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  `address` VARCHAR(255) NULL DEFAULT NULL,
  `user_id` INT(11) NULL DEFAULT NULL,
  `is_enabled` TINYINT(1) NOT NULL DEFAULT '1',
  `latitude` VARCHAR(45) NULL DEFAULT NULL,
  `longitude` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `store_userID_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `userID_fk_idx` ON `store` (`user_id` ASC);


-- -----------------------------------------------------
-- Table `cart`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cart` ;

CREATE TABLE IF NOT EXISTS `cart` (
  `user_id` INT(11) NOT NULL,
  `product_id` INT(11) NOT NULL,
  `amount` INT(11) NOT NULL,
  `store_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`, `product_id`, `store_id`),
  CONSTRAINT `cart_product_id_fk`
    FOREIGN KEY (`product_id`)
    REFERENCES `product` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `cart_store_id_fk`
    FOREIGN KEY (`store_id`)
    REFERENCES `store` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `cart_user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `product_id_UNIQUE` ON `cart` (`product_id` ASC);

CREATE INDEX `user_id_idx` ON `cart` (`user_id` ASC);

CREATE INDEX `product_id_idx` ON `cart` (`product_id` ASC);

CREATE INDEX `store_id_idx` ON `cart` (`store_id` ASC);


-- -----------------------------------------------------
-- Table `history`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `history` ;

CREATE TABLE IF NOT EXISTS `history` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL,
  `product_id` INT(11) NOT NULL,
  `amount` INT(11) NOT NULL,
  `used_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`, `user_id`, `product_id`),
  CONSTRAINT `analytics_productID_fk`
    FOREIGN KEY (`product_id`)
    REFERENCES `product` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `analytics_userID_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `userID_idx` ON `history` (`user_id` ASC);

CREATE INDEX `productID_idx` ON `history` (`product_id` ASC);


-- -----------------------------------------------------
-- Table `shopping_list`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shopping_list` ;

CREATE TABLE IF NOT EXISTS `shopping_list` (
  `user_id` INT(11) NOT NULL,
  `product_id` INT(11) NOT NULL,
  `amount` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`, `product_id`),
  CONSTRAINT `shopping_list_productid_fk`
    FOREIGN KEY (`product_id`)
    REFERENCES `product` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `shopping_list_userid_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `product_id_UNIQUE` ON `shopping_list` (`product_id` ASC);

CREATE INDEX `userid_fk_idx` ON `shopping_list` (`user_id` ASC);


-- -----------------------------------------------------
-- Table `storage`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `storage` ;

CREATE TABLE IF NOT EXISTS `storage` (
  `user_id` INT(11) NOT NULL,
  `product_id` INT(11) NOT NULL,
  `amount` INT(11) NOT NULL,
  `end_date` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`, `product_id`),
  CONSTRAINT `storage_productID_fk`
    FOREIGN KEY (`product_id`)
    REFERENCES `product` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `storage_userID_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `product_id_UNIQUE` ON `storage` (`product_id` ASC);

CREATE INDEX `userID_fk_idx` ON `storage` (`user_id` ASC);

CREATE INDEX `productID_fk_idx` ON `storage` (`product_id` ASC);


-- -----------------------------------------------------
-- Table `stores_products`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `stores_products` ;

CREATE TABLE IF NOT EXISTS `stores_products` (
  `product_id` INT(11) NOT NULL,
  `store_id` INT(11) NOT NULL,
  PRIMARY KEY (`product_id`, `store_id`),
  CONSTRAINT `stores_products_productID_fk`
    FOREIGN KEY (`product_id`)
    REFERENCES `product` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `stores_products_storeID_fk`
    FOREIGN KEY (`store_id`)
    REFERENCES `store` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `productID_fk_idx` ON `stores_products` (`product_id` ASC);

CREATE INDEX `storeID_fk_idx` ON `stores_products` (`store_id` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
