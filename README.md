# To-do-list(client)
RESTful api 서버를 호출하는 client web application

# 1.개발도구
## 1) To-do List server
```
- Framework : Spring Boot 2.1.2
- DB : H2(in-memory DB)
- Build Tool : maven
- 사용언어 및 구현 기술 : Java, JPA, Rest api
```
## 2) To-do List client
```
- Framework : Spring Boot 2.1.2
- Build Tool : maven
- 사용언어 및 구현 기술 : Java, Jquery, Javascrpit, ajax, json
```

# 2.개발환경
```
- OS : windows8 64bit
- IDE : Eclipse photon
- JDK 1.8
```

# 3. 실행 방법

## eclipse 사용 시 

> project 마우스 오른쪽 버튼 클릭 > Run As > Spring Boot App

## To-do list server
> Port 변경 시 : ..src/main/resources/application.properties 에서 수정
```
- 로컬호스트 접속 url : http://localhost:8080/task/list
- 기본으로 입력 된 데이터가 나오는지 확인
- 포함되어 있는 sql 문으로 기본 값 세팅
 > task/src/main/resources/schema.sql
 > task/src/main/resources/import.sql
```
## To-do list client
```
- 로컬호스트 접속 url : http://localhost:8090/client/toDoList
```

# 4. 기능
```
* 텍스트로 된 할 일 추가(POST)
  - 할일 추가 시 다른 할 일 참조 가능
* 할일 수정(PUT)
* 할일 목록 조회(GET)
* 사용자는 할일을 완료처리(PUT)
  - 참조가 걸린 완료되지 않은 할 일이 있다면 완료처리 불가 
```

# 5. 문제 해결 전략

> - 빠른 설정을 위한 Spring Boot 사용
> - 경량 DB 사용을 위해 Spring Boot에 내재되어있는 H2 DB 사용
> - JPA 사용을 통해 간결한 쿼리문 생성 및 매핑
> - annotation 활용을 높임
> - 소스에 sql문을 삽입하여 실행 시 DB 생성 및 테스트 data 삽입
> - client 단에서 restTemplate을 사용하여 데이터 전송
```
  1) 할 일 조회
  - server url ex : http://localhost:8080/task/1
  - @GetMapping(value = "/{id}")을 사용하여 URL에서 id 값을 받아오도록 함
  - JPA의 findById를 사용

  2) 할 일 목록 조회
  - server url ex : http://localhost:8080/task/list
  - @GetMapping(value = "/list") 사용
  - 리스트 조회 및 참조 시 같은 리스트를 사용할 수 있도록 함

  3) 할 일 추가
  - server url ex : http://localhost:8080/task 에 POST 데이터 전송
  - @PostMapping 사용
  - 리스트 화면에 같이 보이도록 구성
  - 추가 시 참조할 할 일 여부를 확인하여 한번에 insert
  - @CreationTimestamp, @UpdateTimestamp 등의 annotation 활용
  - schema.sql 에서 AUTO_INCREMENT를 활용하여 id 자동 증가

  4) 할 일 수정
  - server url ex : http://localhost:8080/task/1 에 PUT 방식으로 데이터 전송
  - @RequestMapping(value = "/{id}", method=RequestMethod.PUT)
  - @RequestBody를 사용하여 데이터를 객체로 받음
  - 자신을 참조하고 있는 완료되지 않은 할일을 카운트하여 수정하지 못하도록 막음
    * JPA의 countByParentIdAndFinYn를 활용
```    

# 6. 화면
> 리스트
![Github](https://github.com/ksy90604/To-do-list-server/blob/master/%EB%A6%AC%EC%8A%A4%ED%8A%B8.JPG)

> 할일 입력 후 추가버튼 클릭 > 확인 > 참조 팝업
![Github](https://github.com/ksy90604/To-do-list-server/blob/master/%ED%8C%9D%EC%97%85.JPG)

> 리스트의 제목을 누르면 수정 페이지로 이동
![Github](https://github.com/ksy90604/To-do-list-server/blob/master/%EC%88%98%EC%A0%95.JPG)
