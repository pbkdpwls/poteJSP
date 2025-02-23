**주제** : 투표 시스템

**기간** : 2023.06.01~2023.06.06

**팀원** : 김승우, 박예진, 이유진, 오세훈, 조우현

**사용기술** : JSP, MySQL, AWS EC2, Naver Login API

## 프로젝트 소개
---
JSP를 활용하여 투표 시스템을 제작하였습니다. 투표 게시글 생성, 조회가 가능합니다. 또한 게시글 별 투표 및 투표 취소, 수정, 삭제, 조회 기능을 포함하고 있습니다. 회원 로그인 시에는 Naver 로그인을 활용하여 사용자를 인증한 후, 회원가입을 진행합니다. Database로는 MySQL을 활용하였으며 AWS EC2 서비스를 활용하여 서비스를 배포하였습니다.

## 기능 명세

---

1. **회원가입 & 로그인**
- Naver API 활용, 소셜 로그인 기능 (사용법: [Naver 로그인 단계 정리](https://www.notion.so/80a44be80ad545469abc7156abd88226?pvs=21))
- 입력값 : 이메일, 닉네임, 주소, 나이
- 사용자 중복 확인
- 세션 활용
2. **회원 정보 조회 & 수정**
- 닉네임, 주소, 나이, 이메일
3. **투표 게시글 생성**
- 입력값 : 제목, 약속날짜, 지역, 메뉴 항목
- 메뉴 항목 기본 3개, 5개까지 추가 가능
4. **투표 게시글 조회 & 검색**
- 메인 화면에서 모든 글 조회
- 페이징 기능 (한 화면에 글 5개까지 조회)
- 검색 기능 : 게시글 제목 & 작성자로 검색 가능
5. **투표 하기**
- 메뉴 투표 기능
- 중복 아이템 투표 여부 검증
- 중복 게시글 투표 여부 검증
6. **투표 수정**
- 투표 수정 기능
- 투표 기록 검증 후 수정
7. **투표 취소**
- 투표 취소 기능
- 투표 기록 검증 후 취소
8. **투표 결과 조회**
- 투표 후 항목 옆에 투표 수 확인 가능
- 투표 현황 보기로 투표한 유저 확인 가능
- 투표 종료 시, 가장 높은 투표 수를 얻은 항목 색상 강조
9. **투표 종료**
- 투표 생성자가 투표 종료 가능
- 종료 후, 메뉴 선정 결과 확인
- 투표 수 동점 시, 랜덤 결과 선정
10. **투표 통계**
- 전체 통계 (총 유저수, 지역별 유저수, 총 투표수, 참여횟수, 가장 많이 선정된 메뉴, 가장 많이 투표된 메뉴)
- 회원 개인 통계 (투표 생성/참여 횟수, 선정 횟수, 가장 많이 고른/선정된 메뉴)


**시스템 구성도**
![무제 001](https://github.com/POTE-3306/poteJSP/assets/62551459/b5e11a57-f1ca-422b-9553-8317529f4855)

**ER Diagram**
<img width="728" alt="스크린샷 2023-06-06 오후 10 17 22" src="https://github.com/POTE-3306/poteJSP/assets/62551459/987f040b-9c71-468f-94f7-aae42447d221">
