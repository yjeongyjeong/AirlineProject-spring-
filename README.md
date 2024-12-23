## ✈️ 항공사 웹사이트 구현(Spring) ✈️

### 목차
* [개요](#-개요)
* [개발 환경](#-개발-환경)
* [조원](#-조원)
* [PPT](#-ppt)
* [기능 명세서](#-기능-명세서)
* [Diagram](#-diagram)
* [주요 기능 설명](#-주요-기능-설명)
* [기능 시연](#-기능-시연)

### ✔ 개요
```
개발 기간 : 2023.11.27 - 2023.12.15
카카오와 아시아나 항공을 참고하여 항공사 웹사이트를 구현하였습니다.
```

***

### ⚙ 개발 환경
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
<br>
![Apache Tomcat](https://img.shields.io/badge/apache%20tomcat-%23F8DC75.svg?style=for-the-badge&logo=apache-tomcat&logoColor=black)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
<br>
![Bootstrap](https://img.shields.io/badge/bootstrap-%238511FA.svg?style=for-the-badge&logo=bootstrap&logoColor=white)
![jQuery](https://img.shields.io/badge/jquery-%230769AD.svg?style=for-the-badge&logo=jquery&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
<br>
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
<img src="https://img.shields.io/badge/springsecurity-6DB33F?style=flat-square&logo=springsecurity&logoColor=white"/>
<img src="https://img.shields.io/badge/JSP-E34F26?style=flat-square&logo=JSP&logoColor=white">
<img src="https://img.shields.io/badge/Mybatis-000000?style=flat&logo=Fluentd&logoColor=white"/>
<br>
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
<br>
![KakaoTalk](https://img.shields.io/badge/kakaotalk-ffcd00.svg?style=for-the-badge&logo=kakaotalk&logoColor=000000)
![Gmail](https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white)

<details>
	<summary>
		API 상세
	</summary>
  
* 카카오 : 카카오 지도, 카카오 로그인, 카카오톡 채널​, 카카오 postcode service(우편번호)
* 메일 전송 : Javax.mail, spring-context-support(JavaMailSender, MimeMessage, MimeMessageHelper)​
* 기타 : Selenium(크롤링), Summernote(게시판 에디터), DateRangePicker(날짜 선택), PortOne(결제), chart.js(그래프), openWeather(날씨)

</details>

```
Mysql 3306 포트로 연결
Tomcat 8081 포트로 연결
```

***

### ✨ 조원
|이름|역할|
|----|------|
|[김민철](https://github.com/alscjf6702)|공지사항 게시판, Q&A 게시판, 카카오상담톡, 회원혜택 페이지, CSS​|
|[김보람](https://github.com/kbr7105)|헤더, 마이페이지(관리자/일반회원), 일반 로그인, 좌석선택 및 결제, contact 페이지, CSS​|
|[이동우](https://github.com/dwdwdw12)|항공 데이터 크롤링, 여행일기 게시판, 이벤트 게시판, 항공편 검색, 메인화면, CSS|
|[정윤정](https://github.com/yjeongyjeong)|회원가입, 아이디 및 패스워드 찾기, 마이페이지(회원정보수정), 카카오 로그인, CSS​|
***
### 📂 PPT
<details>
  <summary>
   개발환경/개발일정/역할분담
  </summary>
  
![Slide1](https://github.com/yjeongyjeong/springProject/assets/147116001/4457e99c-e108-4dd9-a65d-64b6c621338a)

![Slide2](https://github.com/yjeongyjeong/springProject/assets/147116001/0e04c012-f26f-4a7c-b843-104559b6d50f)

![Slide3](https://github.com/yjeongyjeong/springProject/assets/147116001/679fa277-1466-40c1-97a1-7818de74f0eb)

![Slide4](https://github.com/yjeongyjeong/springProject/assets/147116001/b072ce3c-d346-4cb4-a494-a0ce7921e77b)

![Slide5](https://github.com/yjeongyjeong/springProject/assets/147116001/211b5bab-a299-4d16-956b-c34df47f5431)

![Slide6](https://github.com/yjeongyjeong/springProject/assets/147116001/c91c2f13-fc88-4977-892d-886845662bab)

</details>

<details>
  <summary>
    주요기능
  </summary>
  
![Slide7](https://github.com/yjeongyjeong/springProject/assets/147116001/bea8d9d2-d9ec-43ff-85ea-c53baa7efd34)

![Slide8](https://github.com/yjeongyjeong/springProject/assets/147116001/13cddc6d-cf3a-4785-92e3-8ed45cb2fa68)

![Slide9](https://github.com/yjeongyjeong/springProject/assets/147116001/133528f2-3c69-4555-aeef-1db447f424fd)

![Slide10](https://github.com/yjeongyjeong/springProject/assets/147116001/c24fe1ea-0ffe-479b-8fcd-3143dc39ff80)

![Slide11](https://github.com/yjeongyjeong/springProject/assets/147116001/cc0ace2f-74ed-4796-be08-a13b4dce5375)

![Slide12](https://github.com/yjeongyjeong/springProject/assets/147116001/1fdfe2d0-d133-49c3-ab66-aa4d90a9c30d)

![Slide13](https://github.com/yjeongyjeong/springProject/assets/147116001/35311bdc-5afa-4f1a-8094-01567978f8c6)

![Slide14](https://github.com/yjeongyjeong/springProject/assets/147116001/6253c832-1ac2-4193-9ec7-8027bfe9b528)

![Slide15](https://github.com/yjeongyjeong/springProject/assets/147116001/ec000016-de63-4ede-b916-ff16bf80acee)

</details>

<details>
  <summary>
기능요구사항
  </summary>

![Slide16](https://github.com/yjeongyjeong/springProject/assets/147116001/113f57c5-bbad-43b2-866e-28be281c4d18)

![Slide17](https://github.com/yjeongyjeong/springProject/assets/147116001/376d5a9d-b7d1-444f-8ea2-c6b7b75a62b0)

![Slide18](https://github.com/yjeongyjeong/springProject/assets/147116001/571f9bf6-469d-4a9a-9fb4-d28c51cff9ea)

![Slide19](https://github.com/yjeongyjeong/springProject/assets/147116001/7d21b4d4-141a-4e58-adc4-c93e18fd75d6)

![Slide20](https://github.com/yjeongyjeong/springProject/assets/147116001/b1241374-111d-4d52-a287-8923d216dbc5)

![Slide21](https://github.com/yjeongyjeong/springProject/assets/147116001/dcbd3dd4-3971-42d3-9374-e9c21600e450)

![Slide22](https://github.com/yjeongyjeong/springProject/assets/147116001/fb9a2329-e0a0-43e8-9b03-26ce1a172cf6)

![Slide23](https://github.com/yjeongyjeong/springProject/assets/147116001/acea52bd-b1dc-4d89-8fa8-59fc3a0a6110)

![Slide24](https://github.com/yjeongyjeong/springProject/assets/147116001/9e69ef39-007c-44ce-8eb0-094673bddc64)

![Slide25](https://github.com/yjeongyjeong/springProject/assets/147116001/54d2fdc8-9a2c-4a90-b5d3-b648ca6e29e0)

![Slide26](https://github.com/yjeongyjeong/springProject/assets/147116001/bb78e520-8c65-4c7f-b4db-d450d30198f5)

![Slide27](https://github.com/yjeongyjeong/springProject/assets/147116001/38a8c9b7-2c83-43c0-8335-0356508dd976)

![Slide28](https://github.com/yjeongyjeong/springProject/assets/147116001/fbfa6314-64e3-4af0-98c9-9e40a8335d93)

![Slide29](https://github.com/yjeongyjeong/springProject/assets/147116001/5ac7342a-0467-4e82-96c8-fdc662277668)

![Slide30](https://github.com/yjeongyjeong/springProject/assets/147116001/ab55c5e8-5fe5-4107-8e91-bc8b060b4432)

</details>

<details>
  <summary>
Diagram
  </summary>

![Slide31](https://github.com/yjeongyjeong/springProject/assets/147116001/e1986ac3-ea8b-49bd-b326-4b8042df9d21)

![Slide32](https://github.com/yjeongyjeong/springProject/assets/147116001/c1e461d8-de44-4e7e-b577-aaf0f3f41d20)

![Slide33](https://github.com/yjeongyjeong/springProject/assets/147116001/35338f6b-4f9a-4d9b-b2b5-0bdb1c501f6f)

![Slide34](https://github.com/yjeongyjeong/springProject/assets/147116001/94408130-37ee-4832-beba-c3d0e40136fb)

![Slide35](https://github.com/yjeongyjeong/springProject/assets/147116001/326efbb2-04de-403c-ab6a-f707d8a1cd09)

![Slide36](https://github.com/yjeongyjeong/springProject/assets/147116001/da5fd5f8-0291-4e18-a7fc-42b131d224da)

![Slide37](https://github.com/yjeongyjeong/springProject/assets/147116001/2b0265ef-eebb-4549-9be9-baf5324fc227)

![Slide38](https://github.com/yjeongyjeong/springProject/assets/147116001/c2343c03-1e04-41bf-bda9-72935357fb24)

</details>

<details>
  <summary>
화면구현
  </summary>

![Slide39](https://github.com/yjeongyjeong/springProject/assets/147116001/7626d3c9-9764-4a9a-974e-bd8da89c4087)

![Slide40](https://github.com/yjeongyjeong/springProject/assets/147116001/9e0ae236-f502-4f32-9388-a2bebd2af569)

![Slide41](https://github.com/yjeongyjeong/springProject/assets/147116001/466b0db3-0bfb-4a43-ba97-7900da13e3a0)

![Slide42](https://github.com/yjeongyjeong/springProject/assets/147116001/c98706b8-490a-47f5-9951-5ec72f8317dd)

![Slide43](https://github.com/yjeongyjeong/springProject/assets/147116001/3a7353d6-2928-4fe0-a11c-174cac245ae1)

![Slide44](https://github.com/yjeongyjeong/springProject/assets/147116001/4fa7fb34-0610-48b9-a3db-7c7d20fc06a0)

![Slide45](https://github.com/yjeongyjeong/springProject/assets/147116001/fa8b79c9-c5a3-484a-907f-51d16ce850c7)

![Slide46](https://github.com/yjeongyjeong/springProject/assets/147116001/29658e5d-b592-4cfd-ad6f-064e9b11a1c7)

![Slide47](https://github.com/yjeongyjeong/springProject/assets/147116001/7edc7a1e-997a-4b1a-aae5-4c4aca1ad01b)

![Slide48](https://github.com/yjeongyjeong/springProject/assets/147116001/edd46e2f-2030-4b51-ac06-a4ee5936abf5)

![Slide49](https://github.com/yjeongyjeong/springProject/assets/147116001/e1927f07-e676-41ab-a1b1-68d3cbcb729c)

![Slide50](https://github.com/yjeongyjeong/springProject/assets/147116001/1af960af-10dd-406d-90fc-3f82543751a0)

![Slide51](https://github.com/yjeongyjeong/springProject/assets/147116001/d9ece71c-de5d-4eb9-87a2-177918c954c0)

![Slide52](https://github.com/yjeongyjeong/springProject/assets/147116001/199ec181-e0f0-4f58-8394-8155cd1508a4)

![Slide53](https://github.com/yjeongyjeong/springProject/assets/147116001/e3a5bcf1-fd7f-4273-9cab-43f51521034c)

![Slide54](https://github.com/yjeongyjeong/springProject/assets/147116001/5a72fd5d-27ab-4d1b-950a-aa20f97488b4)

![Slide55](https://github.com/yjeongyjeong/springProject/assets/147116001/84049c04-1d9b-4488-bee5-4f442f604d32)

</details>

<details>
  <summary>
소감
  </summary>

![Slide56](https://github.com/yjeongyjeong/springProject/assets/147116001/411ac5d7-8399-4a80-9d9f-595575638b69)

![Slide57](https://github.com/yjeongyjeong/springProject/assets/147116001/d217c41f-3b93-459d-95cb-ad6226b31e76)

![Slide58](https://github.com/yjeongyjeong/springProject/assets/147116001/889b2a38-248f-4152-861d-3c419e5fa40a)

![Slide59](https://github.com/yjeongyjeong/springProject/assets/147116001/67ceb7b8-c5de-464f-98c0-54290a6a87e4)

![Slide60](https://github.com/yjeongyjeong/springProject/assets/147116001/61a43e3d-a96d-4084-a953-83a75c80ff52)

</details>

***

### 📑 기능 명세서
<details>
  <summary>
    기능 명세서
  </summary>

![functionalSpecification](https://github.com/yjeongyjeong/springProject/assets/147116001/cc1b5e2a-8aec-48bb-b705-1669330ddb94)
</details>

***

### 📊 Diagram
<details>
  <summary>
    Usecase
  </summary>

![UsecaseDiagram](https://github.com/yjeongyjeong/springProject/assets/147116001/9bf8257a-47ec-45c4-8615-8b5a4fb0b453)
 </details>
 
<details>
  <summary>
    ER
  </summary>

![ERDiagram](https://github.com/yjeongyjeong/springProject/assets/147116001/bb0f6351-8300-4efd-8fc9-aea2e3d3d569)
 </details>
 
<details>
  <summary>
    Class : home / join / myPage / searchAndReservation / board / Diary
</summary>
	
ClassDiagram_home
![ClassDiagram_home](https://github.com/yjeongyjeong/springProject/assets/147116001/758fb6f0-bd46-462e-b327-d15d23fc89c3)

ClassDiagram_join
![ClassDiagram_join](https://github.com/yjeongyjeong/springProject/assets/147116001/b2235f82-9db8-430c-88f9-ccdacd3a5bf6)

ClassDiagram_myPage
![ClassDiagram_myPage](https://github.com/yjeongyjeong/springProject/assets/147116001/1e9938b7-1984-423e-98b2-3d187e1ed382)

ClassDiagram_searchAndReservation
![ClassDiagram_searchAndReservation](https://github.com/yjeongyjeong/springProject/assets/147116001/32455ab8-6572-4036-b9d8-b9d1950459b8)

ClassDiagram_boardAdmin
![ClassDiagram_boardAdmin](https://github.com/yjeongyjeong/springProject/assets/147116001/9d3eb770-1dc3-4345-b19f-aded48aed66b)

ClassDiagram_boardEventAndDiary
![ClassDiagram_boardEventAndDiary](https://github.com/yjeongyjeong/springProject/assets/147116001/384d8281-7636-4561-9d9e-7868664608d0)
 </details>
 
---
 
### 📍 주요 기능 설명
<details>
  <summary>
    헤더/메인
  </summary>
  
* 헤더의 경우 각 페이지로 이동
* 항공편 검색
* 이벤트 슬라이더 : 기준을 정해서 8개 노출
* 모달 : 긴급공지, 공지사항 등 안내
* 공지사항 : 중요공지 노출
* 지역별 인기 노선 소개

</details>

<details>
  <summary>
    회원가입 및 휴면계정 처리
  </summary>
  
* 일반 회원가입과 카카오 회원 가입으로 구성
* 회원가입 시 입력 정보 유효성 검사
* 회원가입 시 패스워드 인코딩
* 회원가입 시 가입완료 메일 전송
* 휴면계정은 로그인 시 계정 상태 조회
* 랜덤키를 생성하여 유저 체크 후 휴면 비활성화

</details>

<details>
  <summary>
    아이디 찾기 및 비밀번호 찾기
  </summary>
  
* 입력받은 정보로 유저 조회
* 랜덤키를 통한 유저 체크

</details>

<details>
  <summary>
	마이페이지(유저)
  </summary>
  
* 정보 조회 및 변경
* 등급 및 마일리지 상세 조회
* 항공 예약 현황 조회
* 내가 쓴 글 조회
</details>

<details>
  <summary>
	마이페이지(관리자)
  </summary>
  
* 회원 현황 조회 및 휴면 처리
* 일별 매출 그래프 확인
* 항공 예약 현황 조회 및 수정
* 게시판 작성 및 조회
</details>

<details>
  <summary>
항공편
  </summary>
  
* 편도/왕복 선택 검색
* 검색어 자동완성
* 조회 항공편 부재시 가까운 항공편 조회 가능
* 항공편 운항 상태 확인
* 좌석 선택 시 예약 페이지 이동
* 결제 시 마일리지 및 카카오 포인트 사용 가능
* 결제 후 카카오톡으로 메세지 발송 가능
</details>

<details>
  <summary>
    공지사항 게시판
  </summary>
  
* 페이징 및 검색 기능
* 조회수
* 게시글 작성 시 일반, 긴급, 팝업 분류
* 파일 첨부 가능
</details>

<details>
  <summary>
Q&A 게시판
  </summary>
  
* 페이징 및 검색 기능
* 조회수
* 본인만 삭제 가능, 관리자만 답변 가능
</details>

<details>
  <summary>
여행일기 게시판
  </summary>
  
* 페이징 및 검색 기능
* 조회수
* 회원의 경우 댓글과 추천 가능
</details>

<details>
  <summary>
이벤트 게시판
  </summary>
  
* 페이징 및 검색 기능
* 조회수
* 파일 첨부 가능
* 지난 이벤트 확인 가능
* 리스트/그리드형 표시
* 썸네일
</details>

***

### 📽 기능 시연

<details>
  <summary>
헤더 및 메인페이지
  </summary>
  
https://github.com/yjeongyjeong/AirlineProject-eclipse-/assets/147116001/ce8d420c-6eb8-49ef-baa6-28f5ca03fd7e  
  
</details>  

<details>
  <summary>
운임정책, 등급, contact
  </summary>

https://github.com/yjeongyjeong/AirlineProject-eclipse-/assets/147116001/443cef84-f679-480e-b1d6-eda6026c9798  
  
</details>  

<details>
  <summary>
회원가입
  </summary>

https://github.com/yjeongyjeong/AirlineProject-eclipse-/assets/147116001/9622232e-faff-46c3-9a8f-0ede8e5f4ee6  
  
</details>  

<details>
  <summary>
카카오 로그인
  </summary>
  
https://github.com/yjeongyjeong/AirlineProject-eclipse-/assets/147116001/ba67b95a-53e1-4492-a040-2b9233fc125a  
  
</details> 

<details>
  <summary>
상담톡
  </summary>
  
https://github.com/yjeongyjeong/AirlineProject-eclipse-/assets/147116001/47fa47f6-ff49-4f7c-8242-585bc745acfd  
  
</details> 

<details>
  <summary>
휴면계정처리
  </summary>
  
https://github.com/yjeongyjeong/AirlineProject-eclipse-/assets/147116001/9587272a-d565-46f8-a63e-4232a5c10727    
  
</details>

<details>
  <summary>
아이디찾기
  </summary>
  
https://github.com/yjeongyjeong/AirlineProject-eclipse-/assets/147116001/3c2e3fb1-1efa-43be-8de4-f92786e9b2e1  
  
</details>  
 
<details>
  <summary>
마이페이지(유저)
  </summary>

https://github.com/yjeongyjeong/AirlineProject-eclipse-/assets/147116001/6b3fb71c-61de-4cb7-a7f9-91239257ef45  

</details>  

<details>
  <summary>
마이페이지(관리자)
  </summary>

https://github.com/yjeongyjeong/AirlineProject-eclipse-/assets/147116001/1c96d480-273e-4f4a-988c-c1a5123c58a4  
  
</details>  

<details>
  <summary>
항공편 검색
  </summary>

https://github.com/yjeongyjeong/AirlineProject-eclipse-/assets/147116001/26e327ff-d86c-4740-a867-e3ab6ac73ad1  
  
</details>  

<details>
  <summary>
출도착 조회
  </summary>

https://github.com/yjeongyjeong/AirlineProject-eclipse-/assets/147116001/d86d4e28-0a86-40dd-aa80-243fa650c371    
  
</details>  

<details>
  <summary>
항공권 예약 및 결제
  </summary>
  
https://github.com/yjeongyjeong/AirlineProject-eclipse-/assets/147116001/cdb6ee9b-d2fe-435d-ba91-80239cf164c4  
  
</details>  

<details>
  <summary>
게시판 : 공지사항
  </summary>

https://github.com/yjeongyjeong/AirlineProject-eclipse-/assets/147116001/9ab4401f-7dfc-4b1b-91f4-3c49b788bb2f  
  
</details>  

<details>
  <summary>
게시판 : QnA 게시판
  </summary>
  
https://github.com/yjeongyjeong/AirlineProject-eclipse-/assets/147116001/ae88fc97-2313-40a5-8405-24b5310d6627
  
</details>  

<details>
  <summary>
여행일기
  </summary>
  
https://github.com/yjeongyjeong/AirlineProject-eclipse-/assets/147116001/13835b48-3059-4d85-8b16-d42f2a8710c4  
  
</details>  

<details>
  <summary>
이벤트 게시판
  </summary>
  
https://github.com/yjeongyjeong/AirlineProject-eclipse-/assets/147116001/b1e4cf7a-6788-4cc5-b68a-697bf04251e4    
  
</details>  
