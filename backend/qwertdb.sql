drop database if exists qwertdb;
create database qwertdb;
use qwertdb;


-- 1. 유저 테이블

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


-- 2. 카테고리 테이블

-- 기존 테이블 삭제
DROP TABLE IF EXISTS category;

-- 테이블 생성
CREATE TABLE category
(
    `category_id`    INT            NOT NULL    AUTO_INCREMENT, 
    `category_name`  VARCHAR(45)    NOT NULL, 
    CONSTRAINT PK_category PRIMARY KEY (category_id)
);

-- 레코드 입력
INSERT INTO `qwertdb`.`category` (`category_name`) 
VALUES 
('동물'), ('사물'), ('풍경'), ('인물'), ('건물'), ('식물'), ('캐릭터'), ('기타');