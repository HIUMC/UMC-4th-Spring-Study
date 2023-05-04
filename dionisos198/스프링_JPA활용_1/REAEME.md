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
<h1>도메인 분석 설계</h1>
<li>1대N 관계</li>
->연관관계 주인을 정해야 함. -> 외래 키가 있는 주문을 연관관계의 주인으로 정함.
<br>
Member->mapped by:거울 이라 생각, 단순 읽기만 가능 ,조회용<br>
Order-> 연관관계 주인 ,연관관계 주인 쪽에 값을 세팅해야 값이 변경된다
<li>주문 상품과 단방향 관계</li>
ORDER_ITEM 과 ITEM 은 단방향 관계, item 입장에서 나를 주문한 order item을 다 찾아 
이럴 필요가 없기 때문에 단 방향관계로 설정하였다.
<br>
<li>객체는 many to many 가 쉽지만 table 관계는 어렵다.</li>
<b>외래 키가 있는 곳을 연관관계의 주인으로 정해라</b>
<li>1대 N 다시 강조</li>
order 의 회원을 바꿀때 값을 바꿀수도 있고 member 에서 값을 바꿀수도 있따
JPA 는 둘 중에 뭘 믿어야 하는가?
둘 중에 하나만 하자 
다가 연관관계 주인
<h3>CascadeType.ALL</h3>
->persist를 전파한다<br>
예를 들어서 orderItems 에다가 데이터를 넣어두고 order 를 저장하면 orderItems도 같이 저장된다.
<br>em.persist(A); em.persist(B);, em.persist(C);em.persist(order);를 em.persist(order)로 한꺼번에 저장이 가능하다.
<br>ex)Order 가 delivery 관리를 하기 때문에 이럴 경우엔 Cascade 쓰는게 좋다.
<h3>연관관계 편의 메서드</h3>
연관관계 편의 메서드는 Control 하는 쪽에 둔다.<br>
양방향일때 쓰면 좋다고 한다.
<h3>상속 처리</h3>
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
:한 테이블에다가 자식의 모든 column들을 떄려 박는다<br>
JOINED: 정교화된 스타일<br> 
TABLEPERCLASS: 도 존재<br>
@DiscriminatorColumn(name="dtype")으로 자식 구분이 가능하다
<h3>1대1 관계</h3>
order 와 delivery 같은 1대1 관계에서는 주로 access를 많이 하는 곳에 둔다.
delivery를 가지고 order 를 찾는 일은 없을 테니 order 에다가 forein key를 둔다.
<h3>EnumType</h3>
@Enumerater(EnumType.STRING)<br>
EnumType.ORDINAL 은 중간에 다른 상태가 생기면 망한다.
<h3>Embeddable 에서는...</h3>
JPA 가 생성할때 reflection,proxy 가 필요하므로 기본 생성자를 사용한다.
-->protected Address(){} -->GOOD
<h3>설계 주의점 </h3>
<li> 모든 연관 관계는 지연 로딩으로 설정한다.</li>
<li>@ManyToOne, 등 @-toOne은 기본이 즉시 로딩이므로 지연로딩으로 꼭 설정한다(N+1)문제.</li>
<h3>em.persist 에 대해서</h3>
<li>영속성 컨텍스트에 멤버 객체 넣고 트랜잭션이 commit 되는 시점에 DB 반영</li>
같은 Transaction 안에서 ID 값이 똑같으면 같은 영속성 컨텍스트에서 똑같은 애가 관리 
<h3>JPQL 이란?</h3>
<li>SQL 은 테이블 대상으로 쿼리를 날리고, JPQL 은 엔티티 객체를 대상으로 쿼리를 날린다.</li>
<h3>Transaction</h3>
JPA 의 모든 데이터 변경은 transaction 안에서 처리 되어야 한다!
<h3>create</h3>
create:Drop 한 후에 다시 create, create-drop은 create 랑 똑같은데 마지막에 drop --> 자원정리
<h3>비즈니스 로직에 관해서...</h3>
엔티티 자체에서 해결 가능한 그런 것들은 entity 안에 비즈니스 로직을 넣는게 좋다.
그것이 <b>객체지향</b> 이기 때문.<br>
엔티티가 비즈니스 로직을 가지고 객체지향의 특성을 적극 활용하는 것을 -->도메인 모델 패턴
<br>
엔티티에는 비즈니스 로직이 거의 없고 ,서비스 계층에서 대부분의 비즈니스 로직을 처리하는 것을 트랜잭션 스크립트 패턴이라고 한다.

<h3>Order에서 crateOrder 만들기</h3>
<li>Order 클래스에서 static으로 자기 자신을 만들어서 반환하는 메서드</li>
밖에서 order를 new 해서 생성하는 방식이 아니라,set,set,set 방식이 아니라, 생성을 할때 부터 createOrder를 호출하여 
주문 생성에 대한 복잡한 비즈니스로 완결, 주로 생성과 고칠게 있다면 여기만 앞으로 생성하려면, 이거만 변경하면 된다.
<h3>동적 쿼리를 해결하기 위해서는?</h3>
1)JPQL 로 처리<br>
2)JPA Criteria로 처리->치명적 단점 유지보수성 쓰레기 <br> 
3)QueryDSL 로 처리<br>
<h1>웹 계층 개발 </h1>
<h3>@NotEmpty(message="gsgs")</h3>
필드를 입력 안했을때 나오는 메시지를 설정할 수 있다.<br>
값이 비어있으면 오류 나게<br>
회원이름을 필수로 하고 싶기 때문에 @PostMapping 에 @Valid MemberForm form 을 해주고 ,BindingResult result에 오류가 담긴다.
<h3>멤버폼이라는 빈 껍데기를 가져가는 이유?</h3>
->Validation 을 위해서 
<h3>멤버 객체들을 출력하는 화면을 만들때는...</h3>
DTO 를 변환해서 화면에 꼭 필요한 것만 출력하는 것을 권장한다
<h3>타임리프 문법</h3>
th:object=${memberForm}-> 이 안에서 memberForm 객체를 쓰겠다는 것을 의미한다.<br>
th:field="*{name}-> *표시: object를 참고하라는 말->Getter,Setter를 통한 프로퍼티 접근법을 의미한다.<br>
th:field: 이름을 가지고 ID와 Name을 맞춘다 (html에서) id="city" name="city"<br>
<h3>Entity vs 폼 객체</h3>
템플릿 엔진에서는 member의 객체를 막 써도 좋으나<br>
API를 만들때는 Entity를 넘기지 말자. 왜? API 스펙이 변한다.<br>
또한 실무에서 엔티티는 핵심 비즈니스 로직만 가지고 있고,화면을 위한 로직은 없어야 한다<br>
화면이나 API에 맞는 폼객체나 DTO를 사용해도록 하자.
<h3>PathVariable</h3>
<li>사용예: @GetMapping(value="/items/{itemId}/edit"}</li>
<h3>더티체킹이란?</h3>
트랜잭션 안에서 Entity를 조회해야 영속상태로 조회한다. 후에 변화를 추적하는 것을 더티체킹이라 함
<h3>변경감지 vs merge</h3>
컨트롤러에서 어설프게 엔티티 생성하지 말고<br>
<b>트랜잭션이 있는 서비스 계층에서 영속상태의 엔티티를 조회하고 엔티티의 데이터를 직접 변경하라!</b>
<h3>update data가 많다면?</h3>
DTO 생성해서 처리하라.
<h3>컨트롤러안에서는 웹 관련 내용만!</h3>
Controller 안에서 지지고 볶고 해도 되지만 service 에서 Transaction안에서 하는게 할 수 있는게 더 많고,
영속 상태로 흘러 가기 때문에 깔끔한 문제 해결


