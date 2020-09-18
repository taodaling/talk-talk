CREATE TABLE `group` (
  `id` int NOT NULL,
  `creator` int NOT NULL,
  `size` int NOT NULL,
  `deleted_id` int NOT NULL,
  `add_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `type` tinyint NOT NULL COMMENT '1-talk face to face, 2-group',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `member` (
  `id` int NOT NULL,
  `group_id` int NOT NULL,
  `user_id` int NOT NULL,
  `deleted_id` int NOT NULL,
  `add_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `offset` bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id_index` (`user_id`,`deleted_id`),
  KEY `group_id_index` (`group_id`,`deleted_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `message` (
  `id` int NOT NULL,
  `content` varchar(1024) NOT NULL,
  `group_id` int NOT NULL,
  `send_user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `group_id_index` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nickname` varchar(64) NOT NULL,
  `deleted_id` int NOT NULL DEFAULT '0',
  `add_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fields` bigint NOT NULL DEFAULT '0',
  `mobile` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mobile_index` (`mobile`,`deleted_id`),
  KEY `nickname_index` (`nickname`,`deleted_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

