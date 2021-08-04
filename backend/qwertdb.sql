
-- 기존 DB 삭제 
drop database if exists qwertdb;
create database qwertdb;
use qwertdb;

-- 1. 유저 테이블

-- 기존 유저 테이블 삭제
DROP TABLE IF EXISTS `user`;

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


-- 3. 게시글 테이블

DROP TABLE IF EXISTS posting;

--  게시글 테이블 생성
CREATE TABLE posting
(
    `posting_id`         INT             NOT NULL    AUTO_INCREMENT, 
    `title`             VARCHAR(128)     NOT NULL    , 
    `content`           VARCHAR(1000)    NOT NULL    , 
    `posting_img`       VARCHAR(256)     NOT NULL    , 
    `create_date`       DATETIME         DEFAULT current_timestamp(), 
    `update_date`       DATETIME         NULL        , 
    `masterpiece_flag`  bit(1)           NULL        , 
    `user_id`           INT              NOT NULL    , 
    `category_id`       INT              NOT NULL    , 
    PRIMARY KEY (posting_id)
);

ALTER TABLE posting
    ADD CONSTRAINT FK_posting_user FOREIGN KEY (user_id) REFERENCES user (user_id);

ALTER TABLE posting
    ADD CONSTRAINT FK_posting_category FOREIGN KEY (category_id) REFERENCES category (category_id);



