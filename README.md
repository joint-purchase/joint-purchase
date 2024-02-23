# 🤝Joint_Purchase
해외 배송 시 공동 구매를 통한 비용 절감

# 기획 배경
- 해외에서만 구매할 수 있는 양질의 상품이 있다.
- 하지만 개인이 구매하기에는 비용이 높다.
- 배송비를 포함한 비용을 해결하기 위해 공동구매를 하는 소비자들이 늘어나고 있다.
- 이를 해결하기 위해 편리한 서비스를 제공하고자 한다.

# 해결 컨셉
- 같은 해외 상품을 구매하고자 하는 소비자들을 모아 공동구매를 진행하여 결제부터 배송까지 해결해주는 서비스를 제공한다.

# 기대 효과
- 해외 배송 시 공동 구매를 편리하게 할 수 있다.
- 비용을 절감할 수 있다.

# 프로젝트 기능 및 설계
- 회원 가입
  - 사용자는 회원가입을 할 수 있다. 일반 사용자는 고객 권한, 관리자는 관리자 권한을 가지게 된다.
  - 회원가입 시 이름, 이메일, 비밀번호, 전화번호, 주소, 생년월일, 전화번호를 입력하며 이메일은 unique하다.
  - 회원가입 시 비밀번호 재입력을 통해 비밀번호 일치 여부 검증을 한다.

- 비밀번호 재설정
  - 비밀번호를 잊어버리면 비밀번호 재설정을 할 수 있다.
  - 이름, 이메일을 입력하면 해당 이메일로 비밀번호 재설정 URL을 전송한다.
  - 받은 URL을 통해 비밀번호를 입력하여 재설정이 가능하다.
  - 재설정 시 비밀번호 재입력을 통해 비밀번호 일치 여부 검증을 한다.

- 로그인
  - 사용자는 로그인을 할 수 있다.
  - 로그인 시 입력한 비밀번호는 회원가입 시 작성한 비밀번호와 일치해야 한다.
  - 또한 소셜 로그인(구글)을 통해 편리하게 로그인이 가능하다.

- 상품 등록/삭제
  - 관리자 권한을 가진 사용자는 상품을 등록할 수 있다.
  - 상품 등록 시 상품 이름, 가격, 수량, 상품 설명, 카테고리, 등록 날짜, 수정 날짜를 입력한다.
  - 상품 등록 시 상품의 외형 사진을 등록할 수 있다. 사진은 최대 3개까지 등록 가능하다.
  - 등록된 상품은 고유의 상품 번호를 가지게 되며 상품 번호는 unique하다.
  - 구매하고자 하는 사람이 많을 경우 최대 인원제한을 둔다.
  - 관리자는 등록한 상품을 삭제할 수 있다.
 
- 상품 검색
  - 모든 사용자는 상품 검색을 할 수 있다.
  - 모든 사용자는 상품 카테고리 별 검색을 할 수 있다.
 
- 포인트 환전/환불
  - 고객 권한을 가진 사용자는 현금을 포인트로 환전을 할 수 있으며, 환전 비율은 1:1이다.
  - 남은 포인트는 환불할 수 있다.
  - 포인트 사용에 대한 모든 내역은 로그를 남긴다.
 
- 장바구니
  - 고객 권한을 가진 사용자는 상품을 장바구니에 등록, 삭제할 수 있다.

- 상품 주문
  - 포인트를 사용하여 상품을 주문할 수 있다.
  - 상품 주문 시 배송지에 대한 정보를 입력한다.
  - 상품 주문 시 주문 수량 만큼 상품의 재고 수량이 줄어든다.

- 주문 마감
  - 상품의 등록 날짜로부터 1주일 후 주문 마감한다.
  - 상품의 최소 참여 인원은 4명이고, 주문 마감 기간 전에 참여 인원이 차면 자동 주문을 진행한다.
  - 주문 마감일까지 최소 참여 인원이 모이지 않았을 경우, 현재 참여한 인원으로 주문을 진행할지 이메일로 주문 동의 여부를 확인한다.

- 상품 배송
  - 주문한 상품을 구매한 사용자의 주소로 구매한 수량만큼 배송한다.

 - 상품 리뷰
   - 상품을 구매한 사용자는 상품에 대한 리뷰 할 수 있다.
   - 상품 리뷰 시 사진, 리뷰 내용, 점수를 입력할 수 있다.
   - 리뷰 등록 시 상품을 구매한 사용자의 아이디, 상품 이름, 등록 날짜와 수정 날짜가 추가로 표시된다.
 
# ERD
![스크린샷 2024-02-15 214002](https://github.com/joint-purchase/joint-purchase/assets/112931862/a6a60c25-f6b3-498d-be64-316bce56ab08)
<br><br><br>

  
## 🍪 기술 스택

### 🖥 Backend
| 영역 | 종류 |
| --- | --- |
| Tech Stack | ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white) ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white) ![JPA](https://img.shields.io/badge/JPA-007396?style=for-the-badge&logo=java&logoColor=white) ![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white) |
| Database | ![MYSQL](https://img.shields.io/badge/MYSQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white) ![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white) |
| Programming Language | ![Java](https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white) |
| Build | ![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white) |
| API | ![REST](https://img.shields.io/badge/REST-007396?style=for-the-badge) |
| Search | ![Elasticsearch](https://img.shields.io/badge/elasticsearch-F8DC75?style=for-the-badge&logo=elasticsearch&logoColor=white) ![Kibana](https://img.shields.io/badge/Kibana-005571?style=for-the-badge&logo=kibana&logoColor=white) |
| Version Control | ![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white) ![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white) |
| CICD | ![Github Action](https://img.shields.io/badge/Github_Action-2088FF?style=for-the-badge&logo=github-actions&logoColor=white) |
| DevOps | ![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white) ![AWS EC2](https://img.shields.io/badge/AWS_EC2-FF9900?style=for-the-badge&logo=amazon-aws&logoColor=white) ![AWS S3](https://img.shields.io/badge/AWS_S3-569A31?style=for-the-badge&logo=amazon-s3&logoColor=white) ![AWS RDS](https://img.shields.io/badge/AWS_RDS-527FFF?style=for-the-badge&logo=amazon-rds&logoColor=white) |


<br>

### 🖥 Common
| 영역 | 종류 |
| --- | --- |
| 협업 툴 | ![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white) |
| 소통 | ![Discord](https://img.shields.io/badge/Discord-7289DA?style=for-the-badge&logo=discord&logoColor=white) ![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white) |
<br>

## 👀 개발 기간
#### 2024. 01 ~ 2024. 02
<br>

# 📄 Commit Message Convention

커밋 메시지에는 다음의 규칙을 따라야 합니다:


| 이모지 | 타입     | 설명                                                          |
| ------ | -------- | ------------------------------------------------------------- |
| ✨     | `Feat`   | 새로운 기능을 추가할 때 사용합니다.                           |
| 🐛     | `Fix`    | 버그를 수정할 때 사용합니다.                                  |
| 💄     | `Style`  | 코드의 스타일을 변경할 때 사용합니다(기능이나 로직에 영향을 주는 변경 제외). |
| ♻️     | `Refactor` | 코드를 리팩토링할 때 사용합니다.                             |
| 📝     | `Comment` | 필요한 주석 추가 및 변경할 때 사용합니다.                     |
| 📚     | `Docs`   | 문서를 수정할 때 사용합니다(예: README).                      |
| 🔨     | `Chore`  | 빌드 업무, 패키지 매니저 설정 등 코드와 직접적으로 관련 없는 변경을 할 때 사용합니다. |
| 🔧     | `Rename` | 파일이나 폴더 등의 이름을 수정하거나 이동할 때 사용합니다.    |
| 🗑️     | `Remove` | 파일을 삭제하는 작업을 할 때 사용합니다.                      |
| 🚑     | `HOTFIX` | 긴급하게 문제점을 해결해야 할 경우 사용합니다.  

예시:
- 새로운 로그인 API 추가: `✨ Feat: Add login API endpoint`
