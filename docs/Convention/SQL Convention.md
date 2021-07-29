# SQL Convention

## MySQL 계정

- 아이디: ssafy
- 비밀번호: ssafy

- 계정 생성 + 비밀번호 설정

```sql
mysql> create user ssafy@localhost identified by 'ssafy';
```



- 계정 권한 (사용자 아이디에게 모든 데이터베이스 모든 테이블에 권한 부여)

```sql
mysql> grant all privileges on *.* to ssafy@localhost with grant option;
```



- 변경 내용 적용

```sql
mysql> flush privileges;
```

