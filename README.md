# QweRT

> Welcome to 🎨[QweRT](http://i5a305.p.ssafy.io/)



## 👨‍👨‍👦‍👦 멤버

- 팀장: 오승철(FrontEnd)

  - 큐레이션 페이지, 메인페이지, 검색 페이지 구현 AWS API Gateway, lambda를 통해서 presigned URL을 만들고 이를 이용해서 프론트엔드에서 S3로 이미지를 직접 업로드할 수 있게 함 Jenkins, Docker를 이용해서 Gitlab에 push하면 배포가 되도록 구현 최종발표

    

- 팀원

  - 문성호(FrontEnd)

    - 업로드 페이지, 드래그앤드랍 컴포넌트, 댓글 컴포넌트, 게시글 상세 페이지, 게시글/댓글 CRUD(Front) 구현, 메인페이지(피드/인기/최신)와 댓글 인피니티 스크롤 구현 Canvas API를 활용한 그림판 구현, README 작성

      

  - 이희준(FrontEnd)

    - 회원가입/로그인, 팔로우/언팔로우 구현, 프로필 페이지 구현

      회원 정보 수정 및 탈퇴, 대표작 구현,  프로필 페이지 더보기(내 작품, 좋아요 누른 작품, 내 큐레이션) 조회 구현, 팔로워/팔로잉 리스트 조회 구현

      프로필 페이지 UI 제작, 중간발표, UCC 제작

      

  - 임영찬(BackEnd)

    - JSON Web Token(JWT)를 이용한 인증 방식을 사용하여 stateless 서버 구현 DB 설계 및 Java Persistence API(JPA)를 사용하여 객체와 관계형 DB를 맵핑하고 REST API를 이용한 CRUD 및 SNS 기능 구현



## 📃 프로젝트 요약

### What is QweRT?

- **QweRT**는 **We(we) Curate(QRT)**, 우리가 그린 그림을 큐레이션하는 그림 웹 SNS입니다.

  QweRT에서 제공하는 그림판으로 그림을 그리거나 다른 툴을 이용하여 그린 그림을 업로드하여 서로의 그림을 공유합니다.

  공유된 그림을 통해 사용자들과 소통하고 QweRT만의 도슨트 댓글 기능으로 전문가의 해석을 볼 수 있습니다.

  자신의 그림뿐 아니라 다른 사람의 그림을 컬렉션처럼 모아 자신만의 전시회(큐레이션)를 열 수 있습니다.



### 기간

- 2021.07.12 ~ 2021.08.20



### 결과





## 📖 프로젝트 정보

### 🛠 기술 스택

![기술스택.JPG](docs/README.assets/기술스택.JPG.jpg)



### 💻 이용

#### FrontEnd

- 설치

```bash
$ npm install 
```



- 실행

```bash
$ npm run serve
```



#### BackEnd

- Java (zulu-8)
- Maven (apache-maven-3.8.1)
- STS (sts-3.9.13.RELEASE)



### 🔗 Database ERD

![QweRT_20210816_13_18](docs/README.assets/QweRT_20210816_13_18-16292524687612.png)

[원본이미지](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/6efe82dd-677d-4595-b52a-164fe39eeb34/QweRT_20210816_13_18.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210818%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210818T020634Z&X-Amz-Expires=86400&X-Amz-Signature=d66cd58deab22462039ba20c80321f650c7d9f971d827640e071a66ef52f40fd&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22QweRT_20210816_13_18.png%22)



### 🗨 기능 소개 및 설명

- 큐레이션

  - 자신의 그림 또는 다른 사용자의 그림을 큐레이션에 추가 가능
  - 사용자는 자신이 생성한 큐레이션의 썸네일과 배경, 큐레이션 제목과 설명을 설정 가능

- 도슨트 댓글

  - 일반 댓글과 달리 전문적인 해석을 쓰는 도슨트 댓글 탭 메뉴

- 그림판

  - 다양한 그리기 툴을 사용하여 그리기
  - 사용자가 원하는 색 추가
  - Undo/Redo 기능

- 프로필

  - 자신이 그린 그림을 대표작으로 설정 가능 ⇒ 프로필 페이지에서 대표작이 먼저 보임
  - 사용자의 게시물, 사용자의 좋아요한 게시물, 사용자의 큐레이션 조회
  - 팔로워/팔로잉 리스트 조회

- 검색

  - 카테고리나 키워드로 그림 검색 및 아티스트 검색

- 업로드

  - 파일을 로컬에서 위치를 찾아 업로드 가능
  - 드래그앤드랍 기능 제공
  - QweRT 자체 그림판 사용시 바로 업로드 가능

  

### 🔍 페이지 상세

#### 회원가입/로그인

- 회원가입

  ![회원가입](docs/README.assets/회원가입.gif)

  - 이메일/닉네임 중복검사 실행 => 확인 완료 시 회원가입 가능
  - 올바른 이메일 형식이어야 검증 통과
  - 비밀번호 8자리 이상 통과
  - 모든 검증 통과 + 약관 동의 시 회원가입 버튼이 활성화되어 회원가입이 가능하다.

  

- 로그인

  ![로그인](docs/README.assets/로그인.gif)

  - 이메일과 비밀번호 일치하면 로그인 성공
  - 이메일 저장 기능 및 비밀번호 보이기/숨기기 기능
  - 비로그인 시 네브바 메뉴 회원가입/로그인/검색
  - 로그인 시 네브바 메뉴 큐레이션페이지/메인페이지/업로드 및 새 큐레이션/ 프로필 및 로그아웃 / 검색



#### 메인페이지(피드/인기/최신)

- 피드페이지

  ![피드페이지2](docs/README.assets/피드페이지2.JPG)

  - 자신의 게시물과 자신이 팔로우한 사용자의 게시물이 최근순으로 보여준다.
  - 각 게시물마다 좋아요/댓글/큐레이션에 추가된 수를 보여준다.
  - 게시물과 팔로우한 사람이 없는 경우 '다른 사용자를 팔로우하거나 게시글을 추가해보세요' 문구를 보여준다.
  - 각 게시물 클릭 시 해당 게시물의 상세 페이지로 이동한다.
  - 작성자의 프로필 사진과 닉네임 클릭 시 해당 사용자의 프로필 페이지로 이동한다.
  - 비로그인 사용자는 피드 페이지가 보이지 않는다. (비로그인 사용자는 인기/최신 페이지만 볼 수 있음)
  - 피드/인기/최신 페이지 모두 인피니티 스크롤이 적용되어있다.

  

- 인기페이지/최신페이지

  ![메인페이지](docs/README.assets/메인페이지.gif)

  - 인기 페이지는 좋아요 수가 많은 게시물 순으로 나열된 페이지
  - 최신 페이지는 게시물 생성 시간 순(가장 최신 게시물이 제일 먼저)으로 나열된 페이지
  - 인기/최신 페이지는 비로그인 상태의 사용자도 조회 가능하다.
  - 인기/최신 페이지에서 각 게시물에 커서를 올려놓으면 작성자의 프로필 사진, 닉네임, 게시물 제목과 좋아요 수가 호버링된다.
  - 각 게시물 클릭 시 해당 게시물의 상세 페이지로 이동한다.
  - 작성자의 프로필 사진과 닉네임 클릭 시 해당 사용자의 프로필 페이지로 이동한다.

  



#### 프로필

- 프로필 페이지

  ![프로필](docs/README.assets/프로필.gif)

  

  - 사용자의 프로필 이미지와 닉네임, 팔로워 수/팔로잉 수/게시물 수/좋아요 받은 수/큐레이션에 추가된 수를 보여준다
  - 중앙에는 자신의 그림 중에서 대표작을 최대 3개까지 보여준다.
  - 대표작 설정은 정보 수정에서 할 수 있으며 대표작이 없는 경우 비어 있는 상태로 보인다

  - 하단에 더보기 버튼이 있으며 더보기를 클릭하면 해당 사용자가 작성한 게시물/좋아요 누른 게시물/사용자가 만든 큐레이션을 볼 수 있다.

  

- 프로필 수정 페이지(정보 수정)

  ![프로필수정](docs/README.assets/프로필수정.gif)

  - 프로필 사진, 닉네임, 자기소개, 비밀번호를 변경할 수 있고 회원 탈퇴를 할 수 있다.
  - 프로필 사진 클릭 시 프로필 사진 변경이 가능하다.
  - 해당 페이지에서 대표작을 최대 3개까지 설정할 수 있다.

  

- 팔로워/팔로잉 리스트

  ![프로필페이지_팔로우](docs/README.assets/프로필페이지_팔로우.JPG)

  - 자신이 팔로우한 리스트(팔로잉)와 자신을 팔로우하는 사용자(팔로워) 리스트를 볼 수 있다.

  

  

#### 그림판

![그림판기본사용](docs/README.assets/그림판기본사용.gif)

- 마우스 클릭과 드래그를 바탕으로 그릴 수 있다.

- 색상 선택이 가능하며 select를 눌러 원하는 색상을 추가할 수 있다.

- 추가된 색상에 커서를 올려놓으면 삭제 버튼이 호버링된다.

- Undo/Redo 기능 지원

- Clear 버튼을 누르면 모든 내용이 지워진다.

- 스트로크 굵기는 1~64px까지 지원한다.

- 업로드 버튼을 클릭하면 현재까지 그린 그림을 업로드 페이지로 가져간다.

- 그린 그림이 없다면 업로드 버튼이 활성화되지 않는다.

- 기본적으로 스트로크 모드와 페인트 모드(채우기)를 지원하며 스트로크 모드의 경우에는 NomalPen, Shaded, Fur, Circle 툴을 추가 지원한다.

  - NormalPen: 일반적인 펜 그리기

  - Shaded: 마우스가 그리는 path 주변에 path 시작점을 추가하여 연결한다. 근처에 path가 있다면 선을 추가로 연결한다.

    ![그림판쉐이드](/uploads/4ceb0134cf51d817e59024e655a9aca8/그림판쉐이드.gif)

    - Shaded 활용 예시

    ![그림판쉐이드활용](/uploads/2ad2249ce91fc3c8d732071fa9f703ad/그림판쉐이드활용.gif)

    

    

  - Fur: 마우스가 그리는 path에서 랜덤 방향으로 짧은 여러개의 선을 추가로 그린다.

    ![그림판퍼](docs/README.assets/그림판퍼.gif)

    - Fur 활용 예시

      ![그림판퍼활용](docs/README.assets/그림판퍼활용.gif)

  

  - Circle: 마우스가 그리는 path를 따라 동심원을 가진 원이 그려진다. 빠르게 드래그하면 원이 더 커진다.

    ![그림판서클](docs/README.assets/그림판서클.gif)

    - Circle 활용 예시

  ![그림판서클활용](docs/README.assets/그림판서클활용.gif)
  
  그리기 도구 참고 출처 : https://github.com/mrdoob/harmony



#### 업로드

![업로드](docs/README.assets/업로드.gif)

- 클립 모양 아이콘 클릭 시 로컬에 저장된 이미지를 서치해서 업로드할 수 있다.

- 드래그 앤 드랍 기능을 이용하여 이미지를 업로드 할 수 있다.

- 업로드할 이미지는 미리보기로 보여진다.

- 업로드할 이미지, 제목, 설명이 없거나 카테고리를 선택하지 않으면 업로드할 수 없다.

- 이미지 유형이 아닌 파일을 드래그 앤 드랍하거나 선택하면 미리보기가 주어지지 않으며 업로드 할 수 없다.

- 휴지통 아이콘 선택 시 업로드할 이미지를 삭제한다.

  

- 그림판에서 업로드를 눌러 온 경우

  ![그림판업로드](docs/README.assets/그림판업로드.gif)

  - 파일 찾기 버튼 대신 되돌아가기 버튼이 생성된다.
  - 해당 버튼을 클릭하면 그렸던 그림 그대로 그리기 페이지로 이동한다.

- 업로드 조건을 모두 충족한 뒤에 업로드 버튼을 누르면 해당 그림의 상세 페이지로 이동한다.

  



#### 게시물 상세

![상세페이지2](docs/README.assets/상세페이지2.JPG)

- 게시물 이미지, 제목, 설명, 카테고리, 작성자, 좋아요 여부와 수, 큐레이션에 추가된 수를 조회할 수 있다.

- 해당 게시물 작성자 본인이라면 게시물 수정, 삭제 버튼이 생성된다.

  - 게시물 수정은 제목, 설명, 카테고리를 변경할 수 있다.

- 일반 댓글과 도슨트 댓글을 조회할 수 있으며 탭 메뉴로 분할되어있다.

- 로그인한 사용자만 좋아요, 큐레이션에 추가, 댓글 생성 기능을 사용할 수 있다.

- 비로그인 상태인 사용자도 해당 페이지를 조회할 수 있으나 좋아요나 큐레이션에 추가 버튼 그리고 댓글 생성 버튼을 클릭하면 로그인 페이지로 이동 모달창이 띄워지고 확인 시 로그인 페이지로 이동한다.

  

  ![비로그인상세](docs/README.assets/비로그인상세.gif)

  

  

- 댓글

  ![댓글생성수정삭제](docs/README.assets/댓글생성수정삭제.gif)

  - 댓글은 먼저 생성된 순으로 보여지며 현재 시점에서 얼마 전에 작성되어 있는지 볼 수 있다.
  - 댓글 작성 시 Comment 탭이 활성화된 경우 일반 댓글에 추가되고 Docent탭이 활성화 된 경우 도슨트 댓글에 추가된다.
  - 댓글 작성자 본인만 댓글 수정 및 삭제 버튼을 볼 수 있다.
  - 댓글 컴포넌트에 인피니티 스크롤이 적용되어있다.

  

#### 큐레이션

- 큐레이션 생성

  ![큐레이션생성페이지](docs/README.assets/큐레이션생성페이지.JPG)

  - 큐레이션 제목과 설명은 필수 큐레이션 썸네일은 선택사항

  - 큐레이션 썸네일은 파일 찾기와 드래그 앤 드랍 기능을 제공하며 Clear 버튼 클릭 시 미리보기와 파일이 삭제된다.

    <img src="docs/README.assets/큐레이션 생성페이지22.JPG" alt="큐레이션 생성페이지22" style="zoom:50%;" />

  - 배경색 고르기 체크 박스를 클릭하면 색상 코드로 배경색을 선택할 수 있다. 선택하지 않으면 default로 흰색이 설정된다.

    

- 큐레이션에 추가

  ![큐레이션에추가](docs/README.assets/큐레이션에추가.gif)

  

  - 큐레이션에 추가는 게시물 상세페이지에서 가능하며 사용자가 큐레이션을 생성해 놓았을 때 어느 큐레이션에 추가할 것인지 체크박스가 나온다.

  - 한번에 여러 큐레이션에 추가할 수 있다.

  - 큐레이션이 없을 경우 큐레이션 생성 페이지로 이동 모달창이 나오고 큐레이션 생성 페이지로 이동할 수 있다.

    

- 큐레이션 페이지

  ![큐레이션 페이지3](docs/README.assets/큐레이션 페이지3.JPG)

  -  큐레이션 페이지는 My 큐레이션과 최신 큐레이션 페이지로 구분되어 있다.
  -  My 큐레이션은 사용자 본인이 생성한 큐레이션을 조회할 수 있고 최신 큐레이션은 큐레이션을 생성한 모든 사용자의 큐레이션을 조회할 수 있다.
  -  제목과 설명 그리고 작성자를 조회할 수 있으며 썸네일이 설정되어 있는 경우 썸네일이 보이고 썸네일이 설정되어 있지 않은 경우 큐레이션에 추가된 그림 (먼저 추가된 순으로 최대 3개)이 보인다.
  -  큐레이션을 클릭하면 해당 큐레이션의 상세 페이지로 이동한다.

  -  My 탭에서는 자신의 큐레이션을 수정할 수 있다.
  -  큐레이션 페이지는 로그인한 사용자만 조회 가능하다.

  

- 큐레이션 상세페이지

  ![큐레이션상세](docs/README.assets/큐레이션상세.gif)

  - 큐레이션 상세 페이지는 가로 스크롤이 적용되어 있고 오른쪽 하단에서 해당 게시물의 제목과 내용을 볼 수 있다.
  - 배경색은 큐레이션 생성 또는 수정 시 설정된 배경색으로 적용된다.



- 큐레이션 수정 페이지

  ![큐레이션수정](docs/README.assets/큐레이션수정.gif)

  - 큐레이션 수정은 큐레이션 페이지의 My 탭에서 제공되는 기능이다.
  - 큐레이션에 들어간 그림을 제거할 수 있고 제목과 내용 수정이 가능하고 썸네일과 배경색 설정이 가능하다.







#### 검색

- 검색어 검색

  검색어로 검색 시 작품으로 검색할 것인지 아티스트로 검색할 것인지 탭을 이용해 선택할 수 있다.

  default는 작품으로 설정되어있다.

  - 작품 검색

  <img src="docs/README.assets/검색페이지1.JPG" alt="검색페이지1"/>

    - 작품 제목이나 내용 또는 작성자 닉네임에 해당 검색어가 포함되는 게시물을 나열한다.
    - 게시물이 나열되는 순서는 인기순(좋아요순)이 default이며 최신순(작성일자순)으로 변경할 수 있다.

    

  - 아티스트 검색(사용자 검색)

  <img src="docs/README.assets/검색페이지2-16292769178584.JPG" alt="검색페이지2"/>

    - 사용자 닉네임에 검색어가 포함되어 있는 경우 해당하는 사용자들을 나열한다.
    - 나열되는 순서는 팔로워순이 default 이며 가입일순으로 변경가능하다.

  

- 카테고리 검색

  <img src="docs/README.assets/image-20210818175703545.png" alt="image-20210818175703545" style="zoom:50%;" />

  

  - 검색 결과

  <img src="docs/README.assets/검색페이지3.JPG" alt="검색페이지3"/>

    - 주어진 카테고리를 클릭하면 카테고리 검색이 이루어지고 선택한 카테고리에 해당하는 게시물들이 나열된다.
    - 나열되는 순서는 인기순이 default이며 최신순으로 변경할 수 있다.

  

  





