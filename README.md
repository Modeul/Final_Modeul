# :pushpin: Modeul
> ![java](https://img.shields.io/badge/Java-17-DEB887?style=flat)
> <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat&logo=SpringBoot&logoColor=white"/>
> <img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=flat&logo=SpringSecurity&logoColor=white"/>
> <img src="https://img.shields.io/badge/mariaDB-003545?style=flat-square&logo=MariaDB&logoColor=white"> 
> <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/>
> <img src="https://img.shields.io/badge/vue.js-4FC08D?style=flat-square&logo=Vue.js&logoColor=white">
> <img src="https://img.shields.io/badge/javascript-F7DF1E?style=flat-square&logo=JavaScript&logoColor=black">
> <img src="https://img.shields.io/badge/html5-E34F26?style=flat-square&logo=HTML5&logoColor=white"> 
> <img src="https://img.shields.io/badge/css-1572B6?style=flat-square&logo=CSS3&logoColor=white">  
> <img src="https://img.shields.io/badge/Vite-646CFF?style=flat-square&logo=Vite&logoColor=white">
> <img src="https://img.shields.io/badge/github-181717?style=flat-square&logo=GitHub&logoColor=white">
> <img src="https://img.shields.io/badge/git-F05032?style=flat-square&logo=Git&logoColor=white">
> <img src="https://img.shields.io/badge/Postman-FF6C37?style=flat-square&logo=Postman&logoColor=white">
> <img src="https://img.shields.io/badge/visualstudiocode-007ACC?style=flat-square&logo=VisualStudioCode&logoColor=white">
> <img src="https://img.shields.io/badge/Figma-F24E1E?style=flat-square&logo=Figma&logoColor=white">
> ![Notion](https://img.shields.io/badge/Notion-606060.svg?style=flat&logo=notion&logoColor=white)    
>
> 

<br>

## 1. 프로젝트 소개

- 지역 주민들이 함께 상품을 구매하여 대량으로 판매하는 물품을 소분함으로써, 1인 가구들을 비롯한 사람들에게 도움을 주고 배달비의 부담으로 인해 혼자서 배달음식을 시키기 어려운 경우에도 함께 시켜 배달비를 나눌 수 있는 협력적인 해결책을 제공하고자 합니다.

<br>

## 2. 제작 기간 & 참여 인원

- 팀 프로젝트
- 2023.02.06 ~ 2023.05.24(3개월)
- 9명(재헌, 선균, 민경, 성민, 충렬, 대현, 지은, 지혜, 석현)

<br>

## 3. 사용 기술

#### `Back-end`
  - Java(JDK 17)
  - Spring Boot 3.0.4
  - Mybatis 3.0.1

#### `Front-end`
  - Vue.js 3.2.47
  - Vite 4.1.4
  - JavaScript
  - HTML5
  - CSS3

#### `Build Tool`
  - Maven 3.9.0

#### `DataBase`
  - MariaDB 10.6.12

#### `IDE & Collaboration`
  - VSCode, EClipse
  - MySQL Workbench
  - Git, GitHub
  - Postman, Notion, Google Drive, Figma, Discord

<br>

## 4. ERD 설계

<img width="110%" src="https://github.com/wogjs0911/Modeul_Backup/assets/37978853/e793326d-4abe-40d9-a06d-22cbdf9b29f2"/>

<br>

## 5. 실행 화면 및 핵심 기능

- 공동구매 서비스는 사용자가 게시글을 작성하여 다른 사용자와 함께 [공동구매, 배달, 대형마트]를 공유할 수 있는 서비스입니다.
서비스를 이용하기 위해서는 로그인이 필수이며, 로그인 없이 회원가입, 로그인 서비스에 한해서만 이용할 수 있습니다.

<details>
<summary><b>핵심 기능 설명 펼치기</b></summary>
<div markdown="1">
</details>
  
<br>
  
### 5.1. 전체 흐름

<br>

### 5.2. 사용자 요청

<br>

### 5.3. Controller

<br>

### 5.4. Service


<br>

### 5.5. Repository



---


<br><br>

## 6. 핵심 트러블 슈팅

<br>

### 6.1. 채팅방 내부의 채팅 메시지를 어떻게 저장해서 채팅방에 다시 불러올 것인가?

<br>

- 문제점 :
  - 채팅방 내부의 채팅 메시지를 어떻게 저장해서 채팅방에 다시 불러올 것인지?
  - 채팅방을 나갔다가 들어오면 채팅 메시지를 불러오는 과정이 필요하다고 판단되어서 채팅방 내부의 메시지를 저장하려고 했다. 초기의 구현 방법은 메시지를 1번 입력할 때마다 DB에 메시지를 저장하고 불러오는 방식으로 구현하고자 했다.

<br>

- 원인 :
  - 하지만, 구현하고보니 서버와 DB에 과부하를 주는 모습을 확인할 수 있어서 성능이 급격히 나빠진 것으로 판단되었다. 이를 수정하기위해 다양한 방식으로 수정하려고 했다.

<br>

- 해결 방법 :
  - 채팅 메시지를 한 번 입력할 때마다 DB에 저장하는 방법과 파일에 저장하는 방법이 있었지만, 팀원들이 모두 채팅 메시지를 공유할 수 있고 DB에서 바로 확인할 수 있기 때문에 DB에 채팅 메시지를 저장하기로 했다.
  - 또한, 채팅 메시지를 한번 입력할 때마다 DB에 바로 저장하기보다는 브라우저에서 벗어날 때마다 DB에 메시지를 저장하기로 했다.
  - 이렇게 구현하면, 서버 요청의 빈번도와 DB의 IO 빈번도를 줄일 수 있어서 성능을 높일 수 있는 결과를 만들었다.

<br>

- 배운점 : 
  - 메시지를 저장하기 위해서는 매번 IO가 일어날 필요도 없었고 서버나 DB의 부하를 최대한 줄이는 방향으로 개발해야한다는 점을 알게 되었다. 

<br>

---

<br><br>

### 6.2. 정산 내역을 DB에 저장하기 위해 @RequestBody를 여러 개 사용할 수 없다는 에러가 발생

<br>

- 문제점 :
  - 정산 시스템 구현에서 dutch, account 칼럼을 포함하는 기존의 구현 방식으로는 정규화가 위반되어 이를 개선하기 위해 DB 테이블 구조 변경
  - 기존 1개의 DB 테이블이 2개의 DB 테이블로 DB 테이블 구조가 변경됨에 따라 DB에 정산 내역을 저장하기 위해서 JSON 데이터 구조도 같이 변경되었다.
  - POST 요청으로 정산 내역을 DB에 저장하기 위해 Spring Boot에서는 JSON 데이터 타입을 @RequestBody에 각각의 객체를 담아서 DB 테이블에 저장하려고 했지만 @RequestBody를 여러 개 사용할 수 없다는 에러가 발생했다. 


<br>

- 원인 :
  - @RequestBody는 JSON 데이터를 HTTP 요청 Body로 받아 자바 객체로 변환하여 Request Stream으로 받기 때문에 단일 객체만을 처리할수 있도록 처리되어 있다.
  - 따라서, @RequestBody를 사용하기 위해서는 다른 방법을 이용해야 한다. 


<br>

- 해결 방법 : 
  - DTO를 추가로 생성하여 처리하거나 JSON 데이터를 여러 객체로 나누어서 처리한다. 
  - DTO를 사용하는 경우는 최초 개발 시점에서만  처리가능하기 때문에 JSON 데이터를 여러 객체로 나누어서 처리하는 방향으로 진행하였다.

<br>

- 배운점 : 
  - 데이터 구조를 설계하고 이용하는 과정이 중요하다는 것을 알게 되었다. 서버에서 다양한 데이터 구조를 처리하기 위해 Map 객체, List 객체 등의 다양한 콜렉션 클래스를 이용하여 데이터 구조를 설계하고 이용하는 방법을 알게 되었다.  

<br>

---

<br><br>

## 7. 개발 후기

- 프로젝트 개발 회고 및 후기:
  

|[재헌](https://github.com/wogjs0911)|[민경](https://github.com/minkyungkimm)|[지혜](https://github.com/icedpodo)|                              [선균](https://github.com/ksg0000)|[대현](https://github.com/dh-song)|
|:---:|:---:|:---:|:---:|:---:|
| <img src="https://github.com/wogjs0911/Modeul_Backup/assets/37978853/57828f80-b031-45b1-943e-c06bc7dedb1c" width="80%" height="80%"/> |<div style="display:flex;"> <div><img src="https://github.com/wogjs0911/Modeul_Backup/assets/37978853/a8298fdf-1cf1-4f98-801a-e4e77f8af974" width="100%" height="100%"/></div> <div><img src=https://github.com/wogjs0911/Modeul_Backup/assets/37978853/48277488-d713-4af9-93be-ce0989d7861b width="100%" height="100%"/></div> </div>| <img src="https://github.com/wogjs0911/Modeul_Backup/assets/37978853/00ff126b-e785-4f60-92e1-9f0c11c13741" width="80%" height="80%"/> | <img src = "https://github.com/wogjs0911/Modeul_Backup/assets/37978853/fbfaed1e-570d-4ccd-97ef-28e4a5e78099" width="100%" height="100%"/>| <img src="https://github.com/wogjs0911/Modeul_Backup/assets/37978853/b94f4033-84d6-4abd-b9d4-a067cbca274a" width="80%" height="80%" />|
|...|.. |힝입니다..|거북이 진화 완.|불완전한 코드의 오류는 <br>즐거웠고, 함께해 준 팀원들에게 감사합니다.|
|[충렬](https://github.com/SONCHUNG)|[석현](https://github.com/Mistercandoit)|[성민](https://github.com/psm901)|[지은](https://github.com/hanjieun94)|
| <img src="https://github.com/wogjs0911/Modeul_Backup/assets/37978853/b7b5525d-11fe-456c-a312-3d6e2f36a7b4" width="80%" height="80%" /> | <img src="https://github.com/wogjs0911/Modeul_Backup/assets/37978853/54c990f8-9299-4659-8633-d41cc60b5b45" width="100%" height="100%"/> | <img src="https://github.com/wogjs0911/Modeul_Backup/assets/37978853/b8e27ccd-10e2-45d3-a0db-d3f7b425bf08" width="100%" height="100%"/> | <img src="https://github.com/wogjs0911/Modeul_Backup/assets/37978853/3ef27e99-a92b-40fd-9c0e-8e8a37b7ae49" width="100%" height="100%" /> |
|노는게 제일 좋아~|모두 고생많았습니다!<br>체력 길러서 코딩 gogo|디버깅…<br>드디어 끝났다..|행ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ복ㅋㅋㅋㅋㅋㅋㅋㅋㅋ|



<!-- |                                [재헌](https://github.com/wogjs0911)                                |                                                              [민경](https://github.com/minkyungkimm)                                                                      |                               [지혜](https://github.com/icedpodo)                               |                              [선균](https://github.com/ksg0000)                               |                                 [대현](https://github.com/dh-song)                                 |                                 [충렬](https://github.com/SONCHUNG)                                  |                              [석현](https://github.com/Mistercandoit)                               |                              [성민](https://github.com/psm901)                               |                              [지은](https://github.com/hanjieun94)                               |
|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|![재헌](https://github.com/wogjs0911/Modeul_Backup/assets/37978853/57828f80-b031-45b1-943e-c06bc7dedb1c)|![민경1](https://github.com/wogjs0911/Modeul_Backup/assets/37978853/a8298fdf-1cf1-4f98-801a-e4e77f8af974) ![민경2](https://github.com/wogjs0911/Modeul_Backup/assets/37978853/48277488-d713-4af9-93be-ce0989d7861b)|![지혜](https://github.com/wogjs0911/Modeul_Backup/assets/37978853/00ff126b-e785-4f60-92e1-9f0c11c13741)|![선균](https://github.com/wogjs0911/Modeul_Backup/assets/37978853/fbfaed1e-570d-4ccd-97ef-28e4a5e78099)|![대현](https://github.com/wogjs0911/Modeul_Backup/assets/37978853/b94f4033-84d6-4abd-b9d4-a067cbca274a)|![충렬](https://github.com/wogjs0911/Modeul_Backup/assets/37978853/b7b5525d-11fe-456c-a312-3d6e2f36a7b4)|![석현](https://github.com/wogjs0911/Modeul_Backup/assets/37978853/54c990f8-9299-4659-8633-d41cc60b5b45)|![성민](https://github.com/wogjs0911/Modeul_Backup/assets/37978853/b8e27ccd-10e2-45d3-a0db-d3f7b425bf08)|![지은](https://github.com/wogjs0911/Modeul_Backup/assets/37978853/3ef27e99-a92b-40fd-9c0e-8e8a37b7ae49)||
|...| |힝입니다..|거북이 진화 완.|불완전한 코드의 오류는 즐거웠고, 함께해 준 팀원들에게 감사합니다.|노는게 제일 좋아~|모두 고생많았습니다! 체력 길러서 코딩 gogo|디버깅…<br>드디어 끝났다..|행ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ복ㅋㅋㅋㅋㅋㅋㅋㅋㅋ| -->

