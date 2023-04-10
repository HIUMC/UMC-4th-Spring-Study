스프링 DB 접근 기술
===============

1.H2 데이터베이스 설치

개발이나 테스트 용도로 가볍고 편리한 DB, 웹 화면 제공

2.순수 JDBC
개방-폐쇄 원칙(OCP, Open-Closed Principle) 확장에는 열려있고, 수정, 변경에는 닫혀있다.
스프링의 DI (Dependencies Injection)을 사용하면 기존 코드를 전혀 손대지 않고, 설정만으로 구현 클래스를 변경할 수 있다.
회원을 등록하고 DB에 결과가 잘 입력되는지 확인하자.
데이터를 DB에 저장하므로 스프링 서버를 다시 실행해도 데이터가 안전하게 저장된다.

3.스프링 통합 테스트 

SpringBootTest : 스프링 컨테이너와 테스트를 함께 실행한다.
@Transactional : 테스트 케이스에 이 애노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고, 테스트 완료 후에 항상 롤백한다.
이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.

4. 스프링 JdbcTemplate
Jdbc Template 
스프링 JdbcTemplate과 MyBatis 같은 라이브러리는 JDBC API에서 본 반복 코드를 대부분 제거해준다. 하지만 SQL은 직접 작성해야 한다. (실무에서 많이 씀)
Testcase를 이용하면 직접 웹에 가서 데이터를 하나하나 입력하지 않아도 된다.

5. JPA (자바 퍼시스턴스 API)
인터페이스만 제공, 구현은 다른 것에서 이용 
ORM (관계형 매핑)
@Entity JPA가 관리하는 Entity
@Id :pk
@Generation 

JPA는 기존의 반복 코드는 물론이고, 기본적인 SQL도 JPA가 직접 만들어서 실행해준다.
JPA를 사용하면, SQL과 데이터 중심의 설계에서 객체 중심의 설계로 패러다임을 전환을 할 수 있다. JPA를 사용하면 개발 생산성을 크게 높일 수 있다.

spring.jpa.show-sql=true : JPA가 생성하는 SQL을 출력한다.
ddl-auto : JPA는 테이블을 자동으로 생성하는 기능을 제공하는데 none 를 사용하면 해당 기능을 끈다.
create 를 사용하면 엔티티 정보를 바탕으로 테이블도 직접 생성해준다

@commit 하면 DB에 데이터 넣어줌 

6. 스프링 데이터 JPA

스프링 데이터 JPA를 사용하면 리포지토리에 구현 클래스 없이 인터페이스 만으로 개발을 완료할 수 있습니다. 그리고 반복 개발해온 기본 CRUD 기능도 스프링 데이터 JPA가 모두 제공합니다.
 
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

        Optional<Member> findByName(String name);
    }

인터페이스만 만들어놓으면 spring 데이터 JPA가 인터페이스에다가 구현체를 만들어놓고 등록해줌  

스프링 데이터 JPA 제공 기능 인터페이스를 통한 기본적인 CRUD
findByName() , findByEmail() 처럼 메서드 이름 만으로 조회 기능 제공 
페이징 기능 자동 제공
