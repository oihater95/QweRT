배포하는 법 : tag를 생성하면 배포가 됩니다.

![image-20210809054528126](참고.assets/image-20210809054528126.png)

http://13.209.16.153:9090/

에 들어가셔서 아이디랑 비밀번호로 admin, 3333 입력 후

![image-20210809054634288](참고.assets/image-20210809054634288.png)

spring-vue-deploy 누르면 잘 배포되는지 확인 가능합니다.

![image-20210809054707364](참고.assets/image-20210809054707364.png)

잘 되면 맨 위처럼 초록색, 문제가 있으면 빨간색이 뜹니다.



SQL query문에 한글이 안 들어가지지만

실제 사용에서는 한글을 쓸 수 있습니다.

'공룡'을 닉네임으로 설정할 수 있고

회원가입 후 공룡을 닉네임으로 쓰겠다고 하면

이미 사용 중이라는 게 뜨는 걸 확인했습니다.

다만 DB에는 물음표로 뜹니다.

![image-20210809054851396](참고.assets/image-20210809054851396.png)



DB에 변동사항이 생기면 모바엑스텀으로 들어가셔서

```
docker exec -it mysqldb bash
mysql -uroot -proot
use qwertdb
```

입력 후 수정하시거나 저에게 연락주세요

탭 문제 조심 (이런 식으로 공백을 한 칸씩 해줘야 잘 됩니다.)

```
drop database if exists qwertdb;
create database qwertdb;
use qwertdb;

DROP TABLE IF EXISTS user;
	
CREATE TABLE user (
  user_id int NOT NULL auto_increment,
  email varchar(128) NOT NULL,
  password varchar(128) NOT NULL,
  nickname varchar(128) NOT NULL,
  introduction varchar(1000),
  profile_img varchar(256),
  create_date datetime DEFAULT current_timestamp(),
  PRIMARY KEY (user_id),
  UNIQUE KEY user_idx_unique_email (email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS category;

CREATE TABLE category
(
    category_id INT NOT NULL AUTO_INCREMENT, 
    category_name VARCHAR(45) NOT NULL, 
    CONSTRAINT PK_category PRIMARY KEY (category_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO qwertdb.category (category_name) 
VALUES
('animal'), ('object'), ('landscape'), ('people'), ('architecture'), ('plant'), ('character'), ('etc');

DROP TABLE IF EXISTS posting;

CREATE TABLE posting
(
    posting_id INT NOT NULL AUTO_INCREMENT, 
    title VARCHAR(128) NOT NULL, 
    content VARCHAR(1000) NOT NULL, 
    posting_img VARCHAR(256) NOT NULL, 
    create_date DATETIME DEFAULT current_timestamp(), 
    update_date DATETIME NULL, 
    masterpiece_flag bit(1) NULL, 
    user_id INT NOT NULL, 
    category_id INT NOT NULL, 
    like_cnt INT NULL,
    PRIMARY KEY (posting_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE posting
    ADD CONSTRAINT FK_posting_user FOREIGN KEY (user_id) REFERENCES user (user_id);

ALTER TABLE posting
    ADD CONSTRAINT FK_posting_category FOREIGN KEY (category_id) REFERENCES category (category_id);
    
DROP TABLE IF EXISTS `like`;

CREATE TABLE `like`
(
    like_id INT NOT NULL AUTO_INCREMENT, 
    user_id INT NOT NULL, 
    posting_id INT NOT NULL, 
    uploader_id INT NOT NULL, 
    CONSTRAINT PK_like PRIMARY KEY (like_id)
);

ALTER TABLE `like`
    ADD CONSTRAINT FK_like_user FOREIGN KEY (user_id) REFERENCES user (user_id);

ALTER TABLE `like`
    ADD CONSTRAINT FK_like_posting FOREIGN KEY (posting_id) REFERENCES posting (posting_id);

use qwertdb;
```



설정이 create-drop으로 되어있어서 서버가 다시 켜지면 db가 모두 초기화됩니다.



명세서에 보면 히스토리 모드 가능하게 하려면 Maven 관련 설정을 변경하라고 하는데

선택을 해야할 것 같습니다.

영찬님이 구현하시거나 아니면 네브바로 연결이 모두 가능하도록 하기