<!-- Export 시 하나의 파일에 모든 테이블 백업함. (Export to Self-Contained File)
스키마 생성 문장까지 삽입했으므로 새로 스키마 생성하지 않아도 됨. (Include Create Schema)

mysql data import할 때 참고하세용 -> https://goodteacher.tistory.com/131 -->

# Database

## 요구사항 분석

| No. | Content |
| :---: | ---- |
| 1 | 온플리(OwnPli)에 회원으로 가입하려면 회원아이디, 비밀번호, 이름, 나이, 성별을 입력해야 한다. |
| 2 | 회원은 회원아이디로 식별한다. |
| 3 | 음악에 대한 음악아이디, 제목, 장르, 분위기, 앨범 정보, 가수, mp3 파일 주소, 가사 파일 주소, 이미지 파일 주소, 나라를 유지해야 한다. |
| 4 | 음악은 여러 분위기를 가질 수 있고, 하나의 분위기는 여러 노래의 속성이 될 수 있다. |
| 5 | 음악은 하나의 장르만을 가질 수 있다. |
| 6 | 음악은 음악아이디로 식별한다 |
| 7 | 회원은 여러 개의 플레이리스트를 생성할 수 있고 플레이리스트 하나는 한 명의 회원만 생성할 수 있다. |
| 8 | 플레이리스트에 대한 플레이리스트 번호, 플레이리스트 이름, 회원아이디, 음악 유지해야한다. |
| 9 | 플레이리스트는 플레이리스트 번호로 식별한다. |
| 10 | 플레이리스트에는 여러 음악을 담을 수 있고, 하나의 음악을 여러 플레이리스트에 담을 수 있다. |

<br>

## ERD

<p align="middle" >
  <img src="https://user-images.githubusercontent.com/84761609/207590192-90f1c509-b425-4571-84fc-6a8a2faefa6d.png" width="400em;" alt=""/>
</p>

<br>

## Schema

<p align="middle" >
  <img src="https://github.com/youbbin/ownpli-project/assets/84761609/3ee3ca00-d215-4a0f-8b41-f7730b97d71b" width="800em;" alt=""/>
</p>

<br>

## 시스템 구성도

<p align="middle" >
  <img src="https://user-images.githubusercontent.com/84761609/207590797-13a738ef-15dd-4e3e-b0dd-ad23223ecec8.png" width="800em;" alt=""/>
</p>

<br>

## 시스템 흐름도

<p align="middle" >
  <img src="https://user-images.githubusercontent.com/84761609/207590997-71a6ded1-5d43-401c-a208-fea5b2ff63b8.png" width="800em;" alt=""/>
</p>

<br>

## CRUD

| 기능 | 내용 |
| :---: | :---: |
| C | 회원가입 <br> 플레이리스트 생성 <br> 플레이리스트에 음악 추가 <br> 음악 좋아요 |
| R | 로그인 <br> 사용자 기반 음악 필터링 <br> 시즌 별 음악 추천 <br> 사용자 정보 보기 |
| U | 플레이리스트 제목 수정 |
| D | 플레이리스트 삭제 <br> 플레이리스트 내 음악 삭제 |