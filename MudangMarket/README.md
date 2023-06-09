# 가천대학교 2023년 소프트웨어 공학 팀프로젝트
## 무당마켓

#### 프로젝트 개발 환경
- 운영체제 : 윈도우
- 통합개발환경(IDE) : IntelliJ
- JDK 버전 : JDK 11
- 스프링 부트 버전 : 2.7.7
- 데이터 베이스 : H2 Database
- 빌드 툴 : Gradle
- 관리 툴 : GitHub


#### 기술 스택
- 프론트엔드 : HTML, CSS, Thymeleaf
- 백엔드 : Spring, Springboot, Spring Security

## ERD 다이어그램
<img src="https://github.com/Hyunstone/software_engineering/assets/96871857/836d82ed-9edc-466b-9078-04fd9e08f349">

## 엔티티 관계도
<img src="https://github.com/Hyunstone/software_engineering/assets/96871857/b3aab1c3-f78a-41a9-9952-67bd42cf4bf2">

## 기능
### 1. 회원가입 및 로그인 기능
- 스프링 시큐리티를 이용하여 로그인 구현
- 이메일, 비밀번호, 이름, 핸드폰번호, 닉네임 입력 시 회원가입 진행

### 2. 상품 등록 기능
- 제목, 카테고리, 가격, 본문을 입력 받음

### 3. 상품 상세 페이지
- 게시시간 치환 : 1시간 전 = n분 전, 하루 전 = n 시간 전, 1년 전 = n 달 전, 1년 후 = n년 후
- 시간을 다룰 땐, Duration 이용. 날짜를 다룰 땐, Period 이용.

### 4. 마이페이지
- 프로필 수정 가능
- 상품 상태별(판매중/거래완료) 판매 상품 목록 조회 가능
- 판매 내역에서 상품 상태 수정 가능
- 상품 상세 페이지에서 상품 상태 변경 가능
- 관심 목록 조회(내가 하트를 누른 판매 상품 목록)

### 5. 1:1 채팅 기능
- 채팅 기능
- 상대방의 메세지 확인 가능
