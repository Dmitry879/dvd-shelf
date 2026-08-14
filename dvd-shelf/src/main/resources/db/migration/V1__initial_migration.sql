CREATE TABLE `dvd_shelf`.`genres` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`));
  
CREATE TABLE `dvd_shelf`.`dvds` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `description` TEXT NOT NULL,
  `bonus_materials` TEXT NOT NULL,
  `num_discs` INT NOT NULL,
  `runtime` VARCHAR(50) NOT NULL,
  `specifications` TEXT NOT NULL,
  `release_date` DATETIME NOT NULL,
  `arrival_date` DATETIME NOT NULL,
  `price` DECIMAL NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `dvd_shelf`.`dvds_genres` (
  `dvd_id` BIGINT NOT NULL,
  `genre_id` INT NOT NULL,
  PRIMARY KEY (`dvd_id`, `genre_id`),
  INDEX `genre_id_idx` (`genre_id` ASC),
  CONSTRAINT `dvd_id`
    FOREIGN KEY (`dvd_id`)
    REFERENCES `dvd_shelf`.`dvds` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `genre_id`
    FOREIGN KEY (`genre_id`)
    REFERENCES `dvd_shelf`.`genres` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
    
CREATE TABLE `dvd_shelf`.`inventory` (
  `dvd_id` BIGINT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`dvd_id`),
  CONSTRAINT `fk_inventory_dvd_id`
    FOREIGN KEY (`dvd_id`)
    REFERENCES `dvd_shelf`.`dvds` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
    
CREATE TABLE `dvd_shelf`.`roles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC));
  
CREATE TABLE `dvd_shelf`.`accounts` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(500) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  INDEX `role_id_idx` (`role_id` ASC),
  CONSTRAINT `role_id`
    FOREIGN KEY (`role_id`)
    REFERENCES `dvd_shelf`.`roles` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE);
    
CREATE TABLE `dvd_shelf`.`customers` (
  `account_id` BIGINT NOT NULL,
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `dob` DATETIME NOT NULL,
  `phone` VARCHAR(50) NOT NULL,
  `address` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`account_id`),
  CONSTRAINT `fk_customers_account`
    FOREIGN KEY (`account_id`)
    REFERENCES `dvd_shelf`.`accounts` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
    
CREATE TABLE `dvd_shelf`.`employees` (
  `account_id` BIGINT NOT NULL,
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `dob` DATETIME NOT NULL,
  `phone` VARCHAR(50) NOT NULL,
  `address` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`account_id`),
  CONSTRAINT `fk_employees_account`
    FOREIGN KEY (`account_id`)
    REFERENCES `dvd_shelf`.`accounts` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
    
