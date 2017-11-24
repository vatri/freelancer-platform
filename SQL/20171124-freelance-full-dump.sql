-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Nov 24, 2017 at 11:17 AM
-- Server version: 5.7.19
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `freelance`
--

-- --------------------------------------------------------

--
-- Table structure for table `bid`
--

DROP TABLE IF EXISTS `bid`;
CREATE TABLE IF NOT EXISTS `bid` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `price` double(10,2) DEFAULT NULL,
  `deadline` varchar(255) DEFAULT NULL,
  `proposal` text NOT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `job_id` (`job_id`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `name`) VALUES
(1, 'Web Development'),
(2, 'Web Design');

-- --------------------------------------------------------

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
CREATE TABLE IF NOT EXISTS `job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `budget` double(10,2) DEFAULT NULL,
  `type` enum('fixed','hourly') NOT NULL DEFAULT 'fixed',
  `expertize_level` enum('beginner','intermediate','expert') DEFAULT 'beginner',
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `category_id` int(11) NOT NULL,
  `author_id` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `job`
--

INSERT INTO `job` (`id`, `title`, `description`, `budget`, `type`, `expertize_level`, `created`, `category_id`, `author_id`) VALUES
(1, 'Need an experienced Java Spring developer', 'Looking for experienced developers in projects with Java backend, Java/GWT, Javascript for on-going project. \r\n\r\n<br><br>\r\n\r\nExperienced with Atlassian tools: Jira, Bitbucket, Bamboo, Git Spanish knowledge will be valued', 40.00, 'hourly', 'intermediate', '2017-07-04 12:47:00', 1, 1),
(2, 'Software using Java, Spring, MongoDB, Javascript and AngularJS', 'Experience in all these technologies is a must:<br><br> Java, Spring, MongoDB, Javascript and AngularJS. Experience with SB Admin template is a plus.', 5000.00, 'fixed', 'expert', '2017-07-04 13:21:40', 1, 1),
(3, 'Sap ABAP developer', 'Required ABAP developer for monthly support. More details in PM.\r\n\r\nRegards\r\nBoris', NULL, 'hourly', 'intermediate', NULL, 1, 1),
(4, 'Java Spring developer', 'Experience required...', NULL, 'fixed', 'intermediate', NULL, 1, 1),
(5, 'SAP ABAP developer', 'Certificate TW10 required...', NULL, 'fixed', 'beginner', NULL, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
CREATE TABLE IF NOT EXISTS `profile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `biography` text,
  `linkedin` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `name`, `password`, `created`) VALUES
(1, 'b', 'Boris Trivic', '$2a$04$WdK723i7N7Gql8VeziMjvOudXQzSg43ahCIVK83aB1sZ91X0zkFqq', NULL);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
