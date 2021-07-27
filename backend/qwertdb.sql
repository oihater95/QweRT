drop database if exists qwertdb;
create database qwertdb;
use qwertdb;

CREATE TABLE `user` (
  `user_id` int NOT NULL auto_increment,
  `email` varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
  `nickname` varchar(128) NOT NULL,
  `introduction` varchar(1000),
  `profile_img` varchar(256),
  `create_date` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_idx_unique_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
