
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
);



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
    `like_cnt`			INT				 NULL		 ,
    PRIMARY KEY (posting_id)
);

ALTER TABLE posting
    ADD CONSTRAINT FK_posting_user FOREIGN KEY (user_id) REFERENCES user (user_id);

ALTER TABLE posting
    ADD CONSTRAINT FK_posting_category FOREIGN KEY (category_id) REFERENCES category (category_id);


-- 4. 좋아요 테이블 생성

DROP TABLE IF EXISTS `like`;

CREATE TABLE `like`
(
    `like_id`      INT    NOT NULL    AUTO_INCREMENT, 
    `user_id`      INT    NOT NULL, 
    `posting_id`    INT    NOT NULL, 
    `uploader_id`  INT    NOT NULL, 
    CONSTRAINT PK_like PRIMARY KEY (like_id)
);


ALTER TABLE `like`
    ADD CONSTRAINT FK_like_user FOREIGN KEY (user_id) REFERENCES user (user_id);

ALTER TABLE `like`
    ADD CONSTRAINT FK_like_posting FOREIGN KEY (posting_id) REFERENCES posting (posting_id);


-- 5. 댓글 테이블 생성

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment`
(
    `comment_id`   INT             NOT NULL    AUTO_INCREMENT, 
    `content`      VARCHAR(128)    NOT NULL, 
    `create_date`  DATETIME        DEFAULT current_timestamp(), 
    `update_date`  DATETIME        NULL, 
    `docent_flag`  BIT(1)          NULL, 
    `user_id`      INT             NOT NULL, 
    `posting_id`   INT             NOT NULL, 
    CONSTRAINT PK_comment PRIMARY KEY (comment_id)
);

ALTER TABLE `comment`
    ADD CONSTRAINT FK_comment_posting FOREIGN KEY (posting_id) REFERENCES posting (posting_id);

ALTER TABLE `comment`
    ADD CONSTRAINT FK_comment_user FOREIGN KEY (user_id) REFERENCES user (user_id);



-- 6. 큐레이션 테이블 생성
DROP TABLE IF EXISTS curation;

CREATE TABLE curation
(
    `curation_id`    INT             NOT NULL    AUTO_INCREMENT, 
    `title`          VARCHAR(20)     NOT NULL, 
    `create_date`    DATETIME        DEFAULT current_timestamp(), 
    `content`        VARCHAR(100)    NOT NULL, 
    `thumbnail_img`  VARCHAR(256)    NULL, 
    `color`          VARCHAR(45)     DEFAULT 'ffffff', 
    `user_id`        INT             NOT NULL, 
    CONSTRAINT PK_curation PRIMARY KEY (curation_id)
);


ALTER TABLE curation
    ADD CONSTRAINT FK_curation_user FOREIGN KEY (user_id) REFERENCES user (user_id) ;


-- 7. 큐레이션 게이슬 관계 테이블 생성
DROP TABLE IF EXISTS curation_has_posting;

CREATE TABLE curation_has_posting
(
    `id`           INT         NOT NULL    AUTO_INCREMENT,
    `curation_id`  INT         NOT NULL, 
    `posting_id`    INT         NOT NULL, 
    `curate_date`  DATETIME    DEFAULT current_timestamp(),
    CONSTRAINT PK_curation_and_board PRIMARY KEY (id)
);


ALTER TABLE curation_has_posting
    ADD CONSTRAINT FK_curation_has_posting_curation FOREIGN KEY (curation_id) REFERENCES curation (curation_id);

ALTER TABLE curation_has_posting
    ADD CONSTRAINT FK_curation_has_posting_posting FOREIGN KEY (posting_id) REFERENCES posting (posting_id);


-- 8. 팔로우 테이블 생성
DROP TABLE IF EXISTS follow;

CREATE TABLE follow
(
    `follow_id`     INT    NOT NULL    AUTO_INCREMENT, 
    `from_user_id`  INT    NOT NULL, 
    `to_user_id`    INT    NOT NULL, 
    CONSTRAINT PK_follow PRIMARY KEY (follow_id)
);

ALTER TABLE follow
    ADD CONSTRAINT FK_follow_from_user FOREIGN KEY (from_user_id) REFERENCES user (user_id);

ALTER TABLE follow
    ADD CONSTRAINT FK_follow_to_user FOREIGN KEY (to_user_id) REFERENCES user (user_id);


-- 9. 피드 테이블 생성
DROP TABLE IF EXISTS feed;

CREATE TABLE feed
(
    `feed_id`      INT         NOT NULL    AUTO_INCREMENT, 
    `create_date`  DATETIME    DEFAULT current_timestamp(), 
    `user_id`      INT         NOT NULL, 
    `posting_id`    INT         NOT NULL, 
    CONSTRAINT PK_feed PRIMARY KEY (feed_id)
);

ALTER TABLE feed
    ADD CONSTRAINT FK_feed_user FOREIGN KEY (user_id) REFERENCES user (user_id);

ALTER TABLE feed
    ADD CONSTRAINT FK_feed_posting FOREIGN KEY (posting_id) REFERENCES posting (posting_id);

use qwertdb;
