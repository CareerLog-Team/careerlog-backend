<div align="center">
    <H1>CareerLog Backend</H1>
</div>

---
## 프로젝트 소개
> 직장인의 이력을 관리하고 나아가 커리어 코칭을 통해 개인의 커리어를 쉽고 전문적으로 관리해주기 위한 솔루션 입니다. <br>
> 총 7가지 항목을 통해 사용자의 이력을 작성 및 관리 할 수 있습니다. <br>
> 작성된 내용을 기반으로 이력서를 추출해주는 기능을 제공하고 있습니다.

### 서비스 기획안
![서비스 이미지](https://i.ibb.co/GCQ3Wky/image.png)

### 서비스 대표 기능 화면
<div style="display: flex; justify-content: space-between;">
  <div style="width: 48%;">

#### 1. 이력 관리 대시보드 페이지

  <img src="https://i.ibb.co/F3CbMgp/image.png" alt="이력 관리 대시보드 페이지">
사용자가 입력한 이력의 내용을 조회 및 수정 할 수 있는 페이지 입니다.
  </div>
  <div style="width: 48%;">

#### 2. 업무 기록 관리 페이지
  <img src="https://i.ibb.co/b2YFYgg/image.png" alt="업무 기록 관리 페이지">
사용자의 이력(Career)을 기반으로 업무 기록을 관리 할 수 있는 페이지 입니다. 본 페이지에서는 프로젝트 및 본인의 업무 내용을 작성합니다.
  </div>
</div>

<div style="display: flex; justify-content: space-between;">
  <div style="width: 48%;">

#### 3. 파일 관리 페이지
<img src="https://i.ibb.co/2kYLqr3/image.png" alt="파일 관리 페이지">
사용자의 이력과 관련된 파일을 저장 및 관리하는 페이지 입니다.
  </div>

  <div style="width: 48%;">

#### 4. 이력서 선택 화면
  <img src="https://i.ibb.co/rMLM92p/image.png" alt="이력서 선택 화면">
이력서 제작에 활용할 6가지 템플릿을 선택하는 페이지 입니다.
 </div>
</div>


### 서비스 링크
<div style="display: flex; justify-content: space-between;">
    <div style="width: 48%;">

#### 서비스 URL
[https://careerlog.co.kr](https://careerlog.co.kr)

  </div>
  <div style="width: 48%;">

#### 테스트 계정
Email : [daejeong_test@gmail.com](mailto:daejeong_test@gmail.com) <br>
PW : test1234

</div>
</div>


## 프로젝트 개요
### 개발 팀원 및 역할
<div style="display: flex; justify-content: space-between;">
    <div style="width: 48%;">

#### 김대정 (Backend Engineer)
- 도메인 설계
- 백엔드 기능 개발
  - 회원 관리 기능 개발
  - 이력 관리 기능 개발
  - 업무 기록 관리 기능 개발
  - 파일 관리 기능 개발
  - 이력서 기능 개발
- CI/CD 프로세스 구축
- 서버 모니터링 시스템 구축

  </div>
</div>

### 개발 스택

- Java21, Spring Boot, Spring Security
- Data Jpa, MySQL
- Github, Github Action
- AWS S3, EC2, CodeDeploy, RDS
- Grafana, Prometheus 

<br>

--- 

## 프로젝트 구조

### 서비스 구조
![서비스 구조](https://i.ibb.co/McmB62f/image.png)

[//]: # (### Api 구조도)
[//]: # (![API 구조]&#40;&#41;)

### ERD
![ERD](https://i.ibb.co/ncPNkMy/Career-Log-ERD.png)

## 주요 기능

### 1. 회원 관리 기능

- Email & Password를 활용한 로그인 기능
- JWT를 활용한 Session 관리 진행
- 사용자 Role을 분리하여, Role별 접근 api 분리 (Admin 기능 분리용)

### 2. 파일 관리 기능

- AWS S3를 활용한 파일 관리 수행 
- 실제 파일은 S3에 업로드, 파일 정보는 RDS에 저장하여 다운로드 및 조회 기능 분리

### 3. 이력 관리 기능

- 사용자의 업무 내용 관리 7종 Type A, B로 분리하여 관리
  - Type A : 저장 버튼 클릭 시, 저장 기능을 수행하는 항목
    - Career(이력) / Education(학력) / Activity(활동 & 경험) / Certificate(자격)
  - Type B : 일정 시간에 따라 자동 저장 기능을 수행하는 항목
    - Language(외국어) / Link(링크) / Skills(스킬)
- 기본 CRUD 기능을 간소화하여 조회(Read), 저장(Update) 기능 제공
  - 저장 : Delete > Update > Create 순서로 저장 기능 수행

  
### 4. 업무 이력 관리 기능

- 사용자 업무 이력 관리 CRUD 기능
- Career(이력) 도메인과 OneToMany로 연관관계 형성
  - Career(이력 / 부모) || WorkRecord(업무 이력 / 자식)의 명확한 관계로 cascade.ALL을 활용하여 라이프 사이클 관리

### 5. 이력서 관리 기능

- 사용자의 이력을 기반으로 이력서를 제작하는 기능
- Bubble Api에 세팅된 이력서 제작 엔진에 데이터 전달
