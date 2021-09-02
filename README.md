# easy_project


## 스프링 부트로 거래내역 관련 Api 설계 해보기 

- DB 
    - URL : https://aquerytool.com/aquerymain/index/?rurl=b3fa9ab7-f9f1-4c9a-a6fd-a3e9b37bf8cb&
      Password : 254i4z
      
- API설계서
    - https://docs.google.com/spreadsheets/d/1djrh12zUBuDT5T9G95O0IQKgP8AkTWANnEAH0gCrO24/edit?usp=sharing
    


- 구현목록
    - 로그인(Spring security) 
        - 일반 로그인 및  Oauth 로그인 적용
    - 유저 (계좌 5개 등록 가능)
    - 입금, 이체,결제 내역 확인가능
- 실패한 부분
    - EC2 연결 - > QueryDSl을 적용시켰더니 build할 때 프리티어 Cpu를 초과합니다.
    
- 추후 생각중인 내용
    - 거래내역은 더미데이터로 보여줄 예정이지만 실시간으로 보여주기엔 부족한 느낌이있다. 계좌번호를 이용한 송금하기 기능을 추가해야겠다.


- 매개 변수에 final을 붙이는경우
    - 때로는 동료 프로그래머 나 유지 보수 담당자가 해당 변수의 값을 변경하면 안되기 떄문이다.
- "yyyy-mm-dd" 문자를 localdataTime으로 변환하기
    ```java
    LocalDateTime stDate = LocalDateTime.of(LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE), LocalTime.of(0,0,0));
    LocalDateTime edDate = LocalDateTime.of(LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE), LocalTime.of(23,59,59));
    ```