<h1>프로젝트 환경설정</h1>
<h3>참고할만한 것들</h3>
<li>thymeleaf:모던 서버 사이트 자바 템플렛 엔진 JSP 와 비교된다</li>
JSP 나 그런 것들은 서버를 통해야만 여는 것이 가능했다. 그러나 thymeleaf 는 안그래도 된다.
<li>Model에 데이터를 실어서 view에 넣기 가능</li>
<li>implementation 'org.springframework.boot:spring-boot-devtools'로 <br>
서버를 껐다 켰다 할 필요없이 변경사항을 반영하는 것이 컴파일로만 가능해짐</li>
<li>처음 db 생성: 파일 생성 모드<br>이후에는 TCP네트워크 모드 </li>
<li>Repository에서 저장을 하고 나면 return 값 안만드는게 좋은데<br>
ID 정도 있으면 조회하기 편하다.</li>
<li>엔티티 매니저를 통한 데이터 변경은 항상 transaction 안에서 이루어져야</li>
<li>@Transactional 이 @Test에 있으면 defualt가 rollback이다.</li>
<li>같은 transaction 안에서 저장하고 조회하면 영속성 컨텍스트가 똑같다. 같은 영속성 컨텍스트 안에서는 
아이디 값이 같으면 같은 Entity로 식별(1차 캐쉬)</li>