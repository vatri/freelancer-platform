create table category(
id int not null primary key auto_increment,
`name` VARCHAR(255) not null
);
INSERT INTO `category` (`id`, `name`) VALUES (1, 'Web Development'), (2, 'Web Design');


CREATE TABLE `users` (
  `id` int(11) NOT NULL primary key auto_increment,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `location` VARCHAR(255),
  `created` TIMESTAMP default CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `created`, `email`, `name`, `password`) VALUES
(1, NULL, 'b', 'Boris Trivic', '$2a$04$WdK723i7N7Gql8VeziMjvOudXQzSg43ahCIVK83aB1sZ91X0zkFqq');


drop table if exists job;
CREATE TABLE `job` (
  `id` int(11) NOT NULL primary key auto_increment,
  `title` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `budget` double(10,2) DEFAULT NULL,
  `type` enum('fixed','hourly') NOT NULL DEFAULT 'fixed',
  `expertize_level` enum('beginner','intermediate','expert') DEFAULT 'beginner',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `category_id` int not null,
  `author_id` int not null default 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `job`
--

INSERT INTO `job` (`id`, `title`, `description`, `budget`, `type`, `expertize_level`, `created`, `category_id`) VALUES
(1, 'Need an experienced Java Spring developer', 'Looking for experienced developers in projects with Java backend, Java/GWT, Javascript for on-going project. \r\n\r\n<br><br>\r\n\r\nExperienced with Atlassian tools: Jira, Bitbucket, Bamboo, Git Spanish knowledge will be valued', 40.00, 'hourly', 'intermediate', '2017-07-04 14:47:00', 1),
(2, 'Software using Java, Spring, MongoDB, Javascript and AngularJS', 'Experience in all these technologies is a must:<br><br> Java, Spring, MongoDB, Javascript and AngularJS. Experience with SB Admin template is a plus.', 5000.00, 'fixed', 'expert', '2017-07-04 15:21:40', 1);

update job set category_id = 1;
alter table job add foreign key (category_id) REFERENCES category(id);

