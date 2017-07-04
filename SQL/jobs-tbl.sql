CREATE TABLE `job` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `title` varchar(255) NOT NULL,
 `description` text NOT NULL,
 `budget` double(10,2) DEFAULT NULL,
 `type` enum('fixed','hourly') NOT NULL DEFAULT 'fixed',
 `expertize_level` enum('beginner','intermediate','expert') DEFAULT 'beginner',
 `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8