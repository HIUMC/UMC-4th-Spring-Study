<h1>JPA 표준</h1>
<h2>section1</h2>
JPA: 과거에는 객체를 데이터 베이스에 저장하려면 복잡한 JDBC,Mybatis,JDBC template 사용
<br>
이제는 JPA 라는 트랙터로 밀어버리면 된다(SQL없이)
<br>
<li>개발자는 SQL 매퍼이다. 매핑이란 객체랑 RDB를 연결하는 것
</li>
<li>데이터 베이스 테이블에는 상속관계가 없다<br>풀 수 있는 방법:
<br>부모같은 테이블 만들고, 자식같은 테이블 만들어서 필요할 떄는 join 해서 가져오는 것</li>
슈퍼타입, 서브타입 관계 
<li>객체는 참조로, 테이블은 외래키를 이용해서 JOIN 해서 다른 객체 및 테이블을 사용</li>
<h3>JPA 필요성</h3>
<b>상속, 연관관계, 데이터 타입, 데이터 식별 방법 </b>이 객체와 관계형 db의 차이점이 있다
<br>이 차이를 매꾸면서 객체를 자바 컬렉션 저장 하듯이 DB에 저장 하는 것이 JPA기술이다.
<h3>ORM</h3>
객체와 관계형 DB를 매핑하는 것.
<h3>1차 캐쉬의 이점</h3>
1) 모아서 보내는 것:Buffer Writing 기능<br>
2) 캐시: 조회할때 DB에서 가지고 오지 않고 쉽게 가져올 수 있다.<br>
3) 동일성 보장: 같은 데이터베이스 트랜잭션 안에서 동일성을 보장해 준다(==비교가 가능하다)
<h2>Section2</h2>
<h3>h2 데이터베이스 </h3>
DB에 따라서 SEQUENCE,AUTO INCREMENT 가 다른데 H2는 둘다 지원<br>
테스트 용도로 많이 쓴다.
<h3>스프링에 맞는 JPA 를 찾으려면...</h3>
spring.io->projects->spring boot->: 내가 사용하는 버젼과 Reference DOC에서 Dependency versions 에서 ORG.Hibernate 찾으면 어떤 버젼 사용하는지 나온다.
<h3>쿼리 콘솔창에 보이기</h3>
<li>show_sql: 쿼리 콘솔에 보이기</li>
<li>format_sql: 이쁘게 포맷팅 시켜줌</li>
<li>use_sql_comments:쿼리가 왜 나오는지 적어줌</li>
<h3>JPA 구동 방식</h3>
1)Persistence 가 persistence.xml 을 참고해서 EntityManagerFactor 를 생성한다
<br>
2)필요할 떄마다 EntityManager 를 생성한다.
<h3>EntityManagerFactory</h3>
EntityManagerFactory는 애플리케이션 로딩시점에 딱 하나만 만들어 놔야 함
<h3>Transaction</h3>
JPA 에서는 데이터를 변경하는 모든 작업은 트랜잭션 안에서 작업해야 한다.<br>
 Transaction은 한 개 이상의 데이터베이스 연산을 묶어서 하나의 작업으로 처리하는 것을 의미
<h3>영속성 관리</h3>
<li> 고객의 요청이 올떄마다 EntityManager 를 사용한다</li>
<li>엔티티매니저는 DB커넥션을 사용한다. </li>
<li>스프링 프레임워크 같은 컨테이너 환경에서는 엔티티 매니저와 영속성 컨텍스트가 N:1 이다</li>
<li>entityManager 안에 영속성 컨텍스트가 있다고 생각하자</li>
<h3>em.persist()</h3>
<li>em.persist는 DB에 저장을 의미하는게 아니라 엔티티를 영속화 한다는 뜻이다</li>
<li>em.persist(member)는 영속성 컨텍스트에서 멤버를 관리하게 하는 것이다. 아직 DB에 저장하지 않았고
transaction commit 시점에 쿼리를 보낸다 .</li>
<h3>영속성 컨텍스트의 이점</h3>
<li>1차 캐쉬: em.find 하면 먼저 1차 캐쉬를 조회한 후 없다면 DB로 select 쿼리를 날린다</li>
<li>동일성 보장: 단 같은 transaction 안에서만 </li>
<li>트랜잭션을 지원하는 쓰기 지연: transaction.commit -> 축적한 후 한번에 보낸다. BATCHSIZE로 묶어서 보내기가 가능(Buffer 모아서 Writing 가능) 이렇게 하면 최적
화 할 수 있는 여지가 있다.</li>
<li>변경 감지:데이터 베이스에서 객체를 찾아온 다음에 데이터의 값을 수정하면 자동으로 update 쿼리가 나간다. 
그 방식은 트랜잭션 커밋 시점에 엔티티와 엔티티의 이전 초기 상태였던 스냅샷을 비교하여 변한게 있다면 그 때 쓰기 지연 저장소에 update 쿼리를 넣고 
flush를 수행하는 식이다. </li>
<h3>엔티티를 삭제할 떄는?</h3>
em.remove()로 가능하다.
<h3>준영속상태?</h3>
em.detach(member) 등으로 회원 엔티티를 영속성 컨텍스트에서 분리한 상태를 준영속 상태라고 한다
em.detach()를 트랜잭션이 커밋되기 전에 수행한다면 그 전에 수정이나 persist 마구마구 수행해도 전혀 바뀌지 않는다
<br>
그렇다면 영속성 컨텍스트가 제공하는 기능(더티체킹) 을 사용하지 못한다.
<li>준영속상태로 만드는 방법 </li>
1)em.detach(entity)<br>
2)em.clear()->영속성 컨텍스트를 완전 초기화<br>
3)em.close()
<h3>플러쉬</h3>
보통 데이터 베이스 트랜잭션이 커밋될 때 flush가 일어난다.
<br>
플러쉬가 발생한다면<br>
1) 변경 감지 수행<br>
2) 수정된 엔티티를 쓰기 지연 SQL 저장소에 등록<br>
3) 쓰기 지연 SQL 저장소의 쿼리를 db에 전송한다.
<li>영속성 컨텍스트를 flush하는 법</li>
1)em.flush()<br>2)트랜잭션 커밋<br>3)JPQL 쿼리 실행<br>
3번은 그럴 수 밖에 없다. em.persist()를 하고 JPQL 을 실행하면 맥락상 persist 한게 들어갔다고 보는게 편리할 것 같기 때문이다
<br>em.flush()한다고 1차 캐쉬가 지워진다는 망상은 하지 말자!




