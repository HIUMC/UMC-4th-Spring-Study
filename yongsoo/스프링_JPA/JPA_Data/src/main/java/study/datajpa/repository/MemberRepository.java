package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 인터페이스간 상속은 implements 대신 extends 를 사용한다. 이렇게하면
 * MemberRepositoryCustom 의 구현 메서드를 사용하면 MemberRepositoryImpl 클래스의 메소드가 실행된다.
 * 이는 Java 의 기능이 아니라, 스프링 데이터 JPA 에서 사용 가능한 기능이다.
 *
 * 다만 한가지 규칙이 존재한다. 인터페이스의 이름 --> MemberRepositoryCustom 은 상관 없지만 이를 구현한 클래스의 이름은 반드시
 * MemberRepository (JPA 리파지토리) + Impl 이여야한다.
 * 이를 반드시 기억하도록하자!
 */
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom{

    /**
     * JpaRepository < 객체타입, 객체 PK 타입 >
     * <p>
     * 인터페이스는 인터페이스끼리 상속받을 때 implement 가 아닌 extends 를 사용한다.
     * 해당 인터페이스를 상속받으면 구현체를 정의할 필요 없이 인터페이스 그대로 사용이 가능하다!!
     * <p>
     * 실제로 MemberRepository 의 클래스 타입을 출력하면 proxy 어쩌구가 나온다.
     * 스프링이 인터페이스를 보고 proxy 구현 클래스를 만들어서 꽂아버린거다.
     * 스프링이 구현클래스를 만들어서 DI 를 해준것이다!
     */

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();
    /**
     * new 오퍼레이션을 JPQL 내에 적용시켜야한다. 패키지 경로를 다 써주고 생성자 메서드 파라미터로 매칭시켜줘야 한다.
     */

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);
    /**
     * 컬렉션 파라미터 바인딩도 가능하다.
     */

    List<Member> findListByUsername(String username); // 컬렉션
    Member findMemberByUsername(String username); // 단건
    Optional<Member> findOptionalByUsername(String username); // 단건 Optional

    /**
     *
     * List 타입의 경우 반환 갯수가 0개더라도 null 값이 아닌 empty 컬렉션이 반환된다. 따라서 null 걱정을 할 필요가 없다.
     *
     * 단건 조회의 경우는 문제가 될 수 있는데, 순수 JPA 의 경우 null 값이 들어오면 noResultException 이 터지지만
     * 스프링 데이터 JPA 의 경우 자동으로 try-catch 문으로 감싸 null 값을 리턴하고 예외를 터뜨리지 않는다.
     *
     */


    /**
     * JPA 이고 Mongo 이고간에 페이징 기능을 표준화 시켜놓은 것이다.
     * org.springframework.data...
     * JPA 가 아니라 data 의 기능이다.
     */

    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m.username) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);
    /**
     * Pageable -> 쿼리에 대한 조건이 들어간다.
     * Page -> 반환타입.
     */

    @Modifying(clearAutomatically = true) // 스프링 데이터 JPA 의 @Modifying 이 있어야 업데이트가 된다.
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);
    /**
     * 스프링 데이터 JPA 의 경우 @Modifying 어노테이션이 있어야 벌크 수정 쿼리 업데이트가 가능하다.
     * 벌크 연산에서 주의해야 할 점 -> em.clear() 해주어야하지만, @Modifying 의 clear 옵션으로 처리 가능하다.
     * em.clear() 를 하면 영속성 컨텍스트를 지운다.
     *
     * ----> 왜냐? JPQL 을 직접 사용하면 영속성 컨텍스트 (1차 캐시) 를 지나쳐 DB 에서 바로 값을 가져오게 되는데,
     * 그 값이 영속성 컨텍스트에 있는 값일 경우 버려버리고 영속성 컨텍스트의 값을 사용하기 때문이다.
     */


    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();
    /**
     * Member 를 조회할 때 연관된 team 을 한방 쿼리로 다 끌고온다.
     */

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();


    @Query("select m from Member m")
    @EntityGraph(attributePaths = {"team"})
    List<Member> findMemberEntityGraph();

    /**
     * 이런식으로 쿼리 일부 작성하고 fetch 조인만 @EntityGraph 로 대체 가능하기도 하다.
     * @EntityGraph 는 스프링 데이터 JPA 가 아니라 JPA 에서 제공하는 기능이다.
     */

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m where m.username = :username")
    List<Member> findEntityGraphByUsername(@Param("username") String username);


    /**
     * JPA Hint
     * ReadOnly 기능이 설정되어있으면 조회 목적의 최적화가 일어난다. 내부에서 스냅샷 같은것을 만들지 않는다.
     * 원래는 find 같은 메서드를 통해 객체를 가져오면 스냅샷을 찍어둔다. 그리고 flush 를 할때 스냅샷과 원본이 다를경우 update 쿼리를 날리는 방식이다.
     */
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true")) // string, string
    Member findReadOnlyByUsername(String username);

    /**
     * JPA Lock
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);
}
