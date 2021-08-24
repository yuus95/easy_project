# easy_project
배운내용으로 기능구현하기 - 금융앱 만들기

- 카카오페이앱을 보면서 필요한기능들을 생각하여
송금과 거래내역 확인및 연령별 평균 지출을 검토해주는 프로젝트를 만들고 있습니다.


- DB 
    - URL : https://aquerytool.com/aquerymain/index/?rurl=b3fa9ab7-f9f1-4c9a-a6fd-a3e9b37bf8cb&
      Password : 254i4z


- 구현목록
    - 로그인(Spring security) 
      - 유저 (계좌 5개 등록 가능
    - 입금, 이체,결제 내역 확인가능
    
    
    
- 추후 생각중인 내용
    - 거래내역은 더미데이터로 보여줄 예정이지만 실시간으로 보여주기엔 부족한 느낌이있다. 계좌번호를 이용한 송금하기 기능을 추가해야겠다.


- 매개 변수에 final을 붙이는경우
    - 때로는 동료 프로그래머 나 유지 보수 담당자가 해당 변수의 값을 변경하면 안되기 떄문이다.
- "yyyy-mm-dd" 문자를 localdataTime으로 변환하기
    ```java
    LocalDateTime stDate = LocalDateTime.of(LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE), LocalTime.of(0,0,0));
    LocalDateTime edDate = LocalDateTime.of(LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE), LocalTime.of(23,59,59));
    ```