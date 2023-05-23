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
<h2>엔티티 매핑</h2>
<h3>Entity란?</h3>
1)@Entity 가 붙은 클래스는 JPA가 관리, 엔티티라 한다<br>
2)JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 필수
<h3>Entity 사용시 주의점 </h3>
1)JPA 스펙상 기본 생성자가 필수이다. public 이든 protected 는 사용할 것<br>
2)final 클래스,enum,interface,inner 클래스에는 사용할 수 없다.<br>
3)저장할 필드에 final 사용을 하지 않는다.
<h3>@Table로 테이블 명 바꾸기</h3>
@Table(name="TTT")등으로 하면 insert into TTT 등으로 될 수 있다.
<h3>데이터베이스 스키마 자동 생성</h3>
<li>ddl이란?</li>
data defination language 의 약자로 데이터베이스에서 데이터 구조를 정의하고, 조작하는데 
사용하는 SQL의 하위언어, 예를 들면 CREATE,ALTER,DROP,TRANCATE, RENAME 등이 있다<br>
<br>
JPA에서는 애플리케이션 로딩 시점에 DB table 생성하는 기능을 지원 한다. 조심해야 할 것은<br>
운영단계에서 쓰지말고 개발 단계,로컬 PC에서 개발할때 도움이 된다.
<br>
<br>
<li>자동 생성의 속성 및 주의점</li>
create-drop 은 테스트 케이스 같은 경우에 쓴다<br>
update는 Drop table 말고 alter table 쓴다<br>
<b>운영장비에서는 절대 create,crete-drop, update 쓰지 말자</b>
update로 인해 alter table 나가면 DB락이 걸릴수도 있다. 그로인해 서비스 중단 가능성이 생긴다<br>
개발 초기단계:create,update<br>
테스트 서버:update,<b>validate(권장):엔티티와 테이블이 정상 매핑 되었는지만 확인</b><br>
<li>DDL 생성 기능</li>
@Column(unique=true,length=10)-->은 애플리케이션에 영향을 주는 것이 아니라 DB에 영향을 주는 것이다.
<h3>필드와 컬럼 매핑</h3>
<li>Column</li>
nullable: DDL 생성 시 not null 제약 조건이 붙는다.<br>
unique: @Table의 uniqueConstraints와 같다. @Table의 uniqueConstraints가 더 선호도가 높다.column의 unique는 이름이 표기가 안되기 때문<br>
length:문자 길이 제약 조건,String 타입에만 사용한다<br>
<li>Enumerated</li>
DB에는 ENUMTYPE이 없다. VARCHAR와 매핑된다<br>
순서의 문제때문에 default 값 ordinal 대신 EnumType.STRING 으로 한다(ordinal 시에 맨 앞에 하나 추가되는 경우 순서가 꼬일 우려가 생긴다)
<li>Lob</li>
CLOB:매핑하는 필드 타입이 문자일 경우<br>
BLOB:나머지는 BLOB<br>
VARCHAR를 넘어서는 큰 컨텐츠를 사용할 때 쓴다.
<li>Transient</li>
필드 와 db를 매핑을 안하게 해주는 Colum 조건이다.
<h2>기본 키 매핑</h2>
<li>직접할당</li>
@Id 만 사용:직접 ID를 만들어 할당 할 때 사용.
<li>자동 할당 </li>
@GeneratedValue를 사용한다.<br>
default값은 AUTO로 방언에 따라 자동으로 지정된다.<br>
IDENTITY 는 em.persist()한 순간에 INSERT 쿼리가 날라가기에 성능상의 단점 존재<br>
SEQUENCE 전략에서는 allocationSize로 성능을 더 좋게 할 수 있다.<br>
ID 가 숫자 타입일 때는 왠만하면 LONG 으로 잡자<br>
<b>기본 키 제약 조건은 NULL이 아니여야 하고, 유일 해야 하며, 변하지 않아야 한다</b>
<li>자연키란?</li>
비즈니스 적으로 의미있는 번호인데 . 이 값을 ID 값으로 사용하면 안된다<br>
<li>권장하는 방법은?</li>
LONG형+대체키+키 생성 전략 사용.
<h3>객체와 테이블 매핑 문제점</h3>
테이블은 외래 키로 조인을 사용해서 연관된 테이블을 찾고, 객체는 참조를 사용해서 연관된 객체를 찾음<br>
<h2>단방향 연관관계</h2>
@ManyToOne과 @JoinColumn 을 사용해서 객체를 테이블로 매핑 할 수 있다.<br>
<h2>양방향 연관관계</h2>
객체는 양방향 관계는 사실 서로 다른 단방향 2개이고 테이블에서는 외래키 하나로 두 테이블의 연관관계를 관리한다<br>
그렇다면 객체의 어느 것이 외래 키를 관리해야 하느냐?<br>
member 의 team 값을 바꿀 떄 외래 키값이 업데이트 되느냐? 아니면 Team 에 있는 mebers 를 업데이트 시 외래키 값이 업데이트 되느냐?<br>
연관관계의 주인을 정해서 외래키를 등록, 수정할 수 있게 하자 나머지는 조회만 가능하게 하고<br>
누구를 주인으로 하냐면 외래키가 있는 곳을 주인으로 정한다<br>
관계형 DB에서 외래키가 있는 곳이 다 쪽이고 외래키가 없는 곳이 1로 설정하므로<br>
다쪽이 항상 연관관계의 주인이라고 생각하면 된다.
<li>참고</li>
List<Member> members=new ArrayList<>();<br>
처럼 필드를 선언할 때 초기화도 함께 하주는 관레가 있으며 이로 인해 null pointer 를 방지할 수 있다.<br>
<h2>양방향 매핑 정리</h2>
1)일단 단방향 매핑으로 설계를 끝낸다<br>
2)JPQL에서 역방향으로 탐색할 일이 있거나 그 외에 양방향 매핑이 필요하다 싶으면 양방향 매핑을 추가한다<br>
3)양방향 매핑시에 순수한 객체 관계를 고려하여 항상 양쪽 다 값을 입력해야 한다. 이로 인해 2가지 문제점을 해결할 수 있는데 문제점1) em.find()로 처음에 1차 캐쉬를 뒤지기 때문에 값을 넣었는데도 불구하고 값이 안나오는 경우가 생길수 있고 문제점 2) 테스트 케이스 작성 시에는 java 코드로 하기 때문이다.<br>
4)이를 연관관계 편의 메서드라고 하는데 연관관계 편의 메서드는 1에 넣어도 되고 다에 넣어도 된다. 상황에 따라 다르다.
<h2>다중성</h2>
<h3>주의점</h3>
1)애매할때는 반대쪽으로 생각해 보자<br>
2)@ManyToMany 쓰지 말자
<h2>다대일</h2>
다가 연관관계의 주인인 상황
<h2>1대다</h2>
일단 권장하지 않음<br>
DB에는 무조건 다 쪽에 외래키가 있어서 Team,memmber 의 관계에서 member 값을 바꾸면 다른 테이블의 키를 업데이트 해야 함<br>
team.getMembers.add(member)하면 업데이트 쿼리 하나 더 증가해서<br>
1)성능 손해<br>2)운영 힘드러짐<br>3)엔티티가 관리하는 외래키가 다른 테이블에 있음<br>
<b>따라서 멤버입장에서 팀으로 갈 일이 없어도 강제로 다대일 매핑 하는 것을 권장한다</b>
<h2>1대1</h2>
<li>주테이블에 외래키가 있는 상황</li>
다대일처럼 외래키가 있는 곳이 연관관계의 주인이다<br>
양방향 관계일때 반대편은 mappedBy를 적용한다<br>
<li>대상테이블에 외래키가 있는 상황</li>
단방향은 JPA가 지원을 안한다<br>
대상테이블에 외래키 양방향인 경우는 지원한다
<b><li>둘중에 무엇이 맞을까?</li></b>
테이블에 바뀔수도 있다. 예를 들면 하나의 회원이 여러 개의 locker를 가진다면 locker에 외래키를 넣는 것이 db관점에서 편리할 것이다<br>
그러나 개발자 입장에서는 MEMBER에 LOCKER 있는 것 유리(쿼리 한방으로 MEMBER 의 LOCKER 에 접근이 쉬워서 그렇다)
<h2>다대다</h2>
다대다는 편리해보이지만 실무에서 사용하면 안된다. 중간 테이블을 만들어도 단순히 연결만 하고 끝나는 것이 아니라 추가적인 데이터가 들어가야 할 필요가 있기 떄문이다<br>
<br>
<h3>실무 관점에서...</h3>
모든 테이블에 PK를 의미 없는 값으로 해야 한다. 유연성이 필요하기 때문이다<br>
모든 테이블에 generatedValue로 깔아라
<h2>상속관계</h2>
객체의 상속과 DB의 슈퍼타입,서브타입 관계를 매핑한다<br>
전략 1)조인전략: Insert 쿼리가 2번 나간다. PK,FK로 조인전략->이걸 정석으로 생각한다.
전략 2)단일 테이블 전략: 성능이 좋다. 칼럼을 다 떄려박는다<br>
전략 3) 구현 클래스마다 테이블 전략: 쓰지말자. 부모 클래스를 em.find 로 찾을떄 다 뒤지기 때문에 비효율적이다<br>
@DiscriminatorColumn(name="DTYPE")으로 타입 구분 가능하다<br>
<h2>@MappedSuperclass</h2>
공통 매핑 정보가 필요할 때 사용한다<br>
공통 속성을 적고 클래스 위에다 @MappedSuperClass 붙여준다. @Entity는 안붙여도 된다<br>
직접 생성하지 않으므로 추상 클래스를 사용한다<br>
<b>@Entity 클래스는 엔티티나 @MappedSuperClass로 지정한 클래스만 상속 가능</b>
<h3>주의</h3>
실전에서 상속관계가 더 복잡해 진다면, 억단위가 넘어가면 단순한 테이블이 좋다<br>
<h2>프록시</h2>
em.find는 실제 entity 객체를 가져오고 em.getReference()는 가짜 엔티티 객체를 가져온다. 호출시점에는 쿼리없으나 실제 사용시점에 쿼리를 보낸다.<br>
프록시 객체는 원본 엔티티를 상속받기 때문에 타입 체크 시에는 instance of 를 사용한다.(주의: JPA 를 사용할때는 ==비교보다는 instance of 로 타입 체크 하는 것이 낫다)<br>
영속성 컨텍스트에 찾는 엔티티가 이미 있다면 em.getReference()도 실제 엔티티를 반환하며 그 반대로 em.getReference()이후에 em.find()를 하면 둘다 프록시 객체를 가져온다<br>
그 이유는 성능 최적화와 JPA는 같은 트랜잭션 내에서는 동일성을 보장하려는 특성이 있기 때문<br>
프록시에서는 영속성 컨텍스트를 이용해서 초기화를 하기 때문에 detach,em.close등으로 인해 준영속상태에 있을때 오류가 발생한다<br>
<h3>참고</h3>
<li>JPA 표준은 강제 초기화가 없기 때문에 강제호출(ex.member.getName())으로 초기화한다.</li>
<h2>지연로딩vs즉시로딩</h2>
이론적으로는 member와 team을 함께 사용할 때는 Eager가 좋고(join으로 팀을 가져온다) 그렇지 않은 경우 member만 따로 쓰는 경우에는 지연로딩을 설정하여 쿼리 양을 줄일수 있다(팀은 프록시 객체이다)<br>
지연로딩시: 실제 team을 사용하는 시점에 초기화된다.<br>
<h3>주의점</h3>
N+1문제 발생가능:n+1문제란 JPQL에서 member를 다 가져온다 하면(em.createQueury("select m from Member m",Member.class).getResultList()) 1은 원래 쿼리를 예상했던 값(member),Team은 member를 다 가져온다음에 또 다시 쿼리를 날려서
team을 select 한다. 따라서 쿼리가 더 나간다. 멤버가 여러명이고 팀이 같으면 팀 쿼리 하나만 나가지만 팀이 다양할 경우 팀 쿼리가 여러개 나간다. 이것을 N+1문제라 하며 fetch join으로 동적으로 별개의 쿼리 대신 한 쿼리로 동적으로 원하는 것을 가져올 수 있다(select m from Member m join fetch m.team)<br>
끝이 One으로 끝나는 관계의 경우 기본이 즉시로딩이기 때문에 수동으로 꼭 LAZY설정 할 것!
<h3>그래서?</h3>
지연로딩을 기본적으로 깔되 같이 가져오고 싶은 쿼리가 있을 경우에는 fetch join 등을 활용한다.
<h2>영속성 전이:CASCADE</h2>
즉시로딩,지연로딩과는 관계가 없고 특정 엔티티를 영속 상태로 만들 떄 연관된 엔티티도 함께 영속상태로 만들고 싶을때 사용한다.
1대 다 관계인 parent 와 child가 있을때 em.persist(child1),em.persist(child2),em.persist(parent)보다는 em.persist(parent)로 한번에 영속화 할 수 가 있다.
<h3>언제 쓰는가?</h3>
하나의 부모가 자식들을 관리할때 의미가 있다. ex)첨부 파일 경로는 한 게시물에서만 관리하기 때문에 의미 있다. 그러나 다른 엔티티에서 관리하거나 다른데랑 관련이 있다면 쓰지 않는다.<br>
<b>소유자가 하나일때 쓰자</b>
<h3>CASCADE 종류</h3>
<li>ALL:라이프 사이클을 다 맞추어서 모두 적용할때 사용</li>
<li>PERSIST:저장할때만 life cycle 맞추어서 영속할때 사용</li>
<li>REMOVE: 삭제할떄만 life cycle 맞추어서 삭제할때 사용</li>
등등 MERGE,REFRESH가 있다.
<h2>고아 객체</h2>
부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제하고 orphanRemoval=true로 한다면 parent.getChildren().remove(0)을하면 DELETE 쿼리가 나간다.
이것도 역시 참조하는 곳이 하나일때, 특정 엔티티가 개인 소유할때만 사용해야 하고, OneToOne,OneToMany에서만 가능하다<br>
CASCADETYPE.REMOVE처럼 동작 그러므로 ALL도 이것 포함

<h2>임베디드 타입</h2>
기본 값타입을 모아서 만들어진다. int,String 과 같은 값 타입이라서 복사만 한다.<br>
비슷한 성격을 모아서 클래스를 만들고 @Embeddable은 만든 클래스에, @Embedded 는 값 타입을 사용하는 곳에 붙여준다<br>
<b>기본생성자가 필수이다.</b><br>
<b>해당 값 타입만 사용하는 의미있는 메소드를 만들 수 있다</b><br>
값 타입이라서 엔티티의 생명주기에 의존<br>
<b>테이블과 달리 객체는 데이터 뿐만 아니라 메소드가 있기에 묶었을 떄 장점이 크다</b>
<br>
한 엔티티에서 같은 값 타입을 사용하면 @AttributeOverrides,@AttributeOverride 를 사용햐서 컬럼명을 바꾸어서 사용가능<br>
<h2>값타입은 불변으로!</h2>
임베디드 타입 같은 값 타입을 여러 엔티티에서 공유할 경우 member.getAddress().setCity("new City")등으로 하면 위험하다<br>
엔티티에 들어간 값이 다 바뀌기 때문이다. 이러한 상황을 방지하려면 복사를 해야 하는데 값 타입이 기본 타입이 아니라 객체 타입이라서 =으로 복사하다가 참조가 
공유될 수 있다<br>
<b>그렇기 때문에 set을 없앰으로써 불변 객체로 만들고 그래도 수정이 필요하면 새로 인스턴스를 생성해서 만들어야 함<br></b>
















