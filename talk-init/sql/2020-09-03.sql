CREATE SCHEMA `talk_talk` DEFAULT CHARACTER SET utf8mb4 ;

CREATE TABLE `talk_talk`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nickname` VARCHAR(64) NOT NULL,
  `deleted_id` INT NOT NULL DEFAULT 0,
  `add_time` DATETIME NOT NULL DEFAULT now(),
  `update_time` DATETIME NOT NULL DEFAULT now(),
  `fields` BIGINT NOT NULL DEFAULT 0,
  `mobile` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `nickname_index` (`nickname` ASC, `deleted_id` ASC) VISIBLE,
  UNIQUE INDEX `mobile_index` (`mobile` ASC, `deleted_id` ASC) VISIBLE);


