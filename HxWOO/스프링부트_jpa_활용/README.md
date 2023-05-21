# 스프링 부트와  JPA 활용

두 기술을 합치면 높은 개발 생산성 + 빠르게 개발 가능

목표 : 실무에서 웹 애플리케이션을 제대로 개발 할 수 있게 하는 것

다양한 복잡한 예제가 있음

일단 코딩으로 따라가기!

### **프로젝트 환경설정**

**프로젝트 생성**

- 스프링 부트 스타터로 생성
    - gradle
    - dependency : spring web, Thymeleaf, Spring Data JPA, Lombok
    - Database : H2
- 라이브러리가 많은 이유 → gradle 에서 의존관계에 있는 라이브러리들을 다 땡겨오기 때문

**라이브러리 살펴보기**

- 스프링 부트 스타터 웹
    - 톰캣, 스프링 웹 mvc, …
    - 로깅
        - 로그백
        - slf4j (로거를 찍는 인터페이스의 모음)
    - 스프링 코어, 컨텍스트
- 타임리프
    - 타임리프 버젼 관련 라이브러리 들
- 스프링 데이터 JPA
    - aop, jdbc 관련 라이브러리 들
    - HikariCP : 커넥션 풀
    - 하이버네이트, 스프링 데이터 JPA
- 테스트
    - junit, mockito(목 객체 만듦), assert(테스트 도와줌)
- h2 데이터베이스

**View 환경 설정**

- thymeleaf라는 템플릿 엔진 사용
- 타임리프, 프리마커, 머스타치 등등 사용가능
- 타임리프 → 네츄럴 템플릿 (html 마크업을 깨지 않고 사용가능)
    - 스프링과 사용하기 좋음
    - 단점 : 매뉴얼을 좀 봐야 함 (좀 알아야 함)

```java
	@GetMapping("hello") //url에 hello 들어오면 여기로 옴
  public String hello(Model model){
      model.addAttribute("data", "hello!!!");
      return "hello"; //렌더링할 html 이름
  }
```

index.html은 정적으로 (static)

hello.html은 타임리프 이용해서 (temlplate) 만들어줌

**H2 데이터베이스 설치**

- 설치 후 h2/bin/h2.sh 실행
- 웹에서 뜨고 나면 뒤에 키값 유지한채 로컬호스트로 접속
- jdbc url 에 디비 파일을 생성할 경로 지정
- jpashop.mv.db 생성 확인
- 이후부턴 jdbc url에 jdbc:h2:tcp://localhost/~/jpashop 로 접속 (네트웍 모드)

**JPA와 DB 설정, 동작확인**

설정 파일 → properties 제거 후 yml 로 생성

설정들은 스프링부트 매뉴얼에 들어가서 하나하나 공부하면 됨 (엄청 많음)

EntityManager 이건 전에 했던 스프링 컨테이너랑 비슷한 역할인듯?

jpa를 쓸 수 있게하는 객체 같은 느낌?

커맨드와 쿼리를 분리해라 → 리턴 값을 거의 안 만듦 ( 아이디 정도만 조회)

entitymanager를 통한 모든 변경은 트랜잭션 안에서 일어나야 함

같은 트랜잭션안에서 같은 영속성 커넥션에서 id값이 같으면 같은 엔티티로 인식함

./gradlew clean build 통해서 직접 테스트 실행

→ jar 통해서 실행

![Untitled](/pictures/001.png)

- 이제 스프링 부트 통해 복잡한 설정이 자동화 되어서 스프링부트 매뉴얼을 참고하는게 좋음

쿼리 파라미터 로그 남기기

- 로깅할때 type을 trace로 줌
- 혹은 외부 라이브러리를 씀 (p6spy 사용, 임플리먼트만 추가)
    - 세세한 설정도 가능함
    - 운영, 배포 할땐 고민좀 해봐야 함 (성능을 줄일 수도 있음)

### 도메인 분석 설계

**요구사항 분석**

- 회원 기능
    - 회원 가입, 회원 목록 조회
- 상품 기능
    - 상품 등록, 상품 목록, 수정, 조회
- 주문 기능
    - 상품 주문, 주문 내역, 주문 취소
- 기타
    - 상품은 재고관리 필요
    - 상품 종류는 도서, 음반, 영화
    - 상픔은 카테고리로 구분 가능
    - 상품 주문시 배송정보 입력가능

**도메인 모델과 테이블 설계**

- 회원 ↔ 주문 : 1 대 N
- 주문 ↔ 상품 : N 대 M
    - 주문 ↔ 주문상품 : 1 대 N
    - 주문상품 ↔ 상품 : N 대 1
- 주문 ↔ 배송정보 : 1 대 1
- 상품 : 도서, 음반, 영화 로 나눠질 수 있음
- 카테고리 ↔ 상품 : N 대 M
- 엔티티 분석
    
    ![Untitled](/pictures/002.png)
    
    - 회원 : 주소(임베디드 타입), 주문 리스트를 가짐
    - 주문 : 회원, 주문상품 리스트, 배송 을 가짐
    - 배송 : 주문, 배송지 주소, 상태 를 가짐
    - 상품 : 이름, 가격, 재고, 카테고리 정보 를 가짐
        - 상속으로 앨범, 책, 영화 를 가짐
    - 카테고리 : 계층 구조를 가짐, 부모 여럿 / 자식 하나
    - jpa로 표현 할 수 있는 모든 관계
        - 다 대 다 관계는 운영에서 사용하면 안됨
            - 1 대 다 → 다 대 1 로 풀어내야 함
            - 양방향 관계를 사용 안하는게 좋음
    
    컴퓨터의 입장에서 생각해야 함
    
- 테이블 분석
    
    ![Untitled](/pictures/003.png)
    
    - 아이템 : 싱글 테이블 전략을 사용함
    - 멤버 : 주소의 임베디드 타입 정보가 그대로 들어감
    - 카테고리 ↔ 아이템 : 1 대 N, N 대 1 로 풀어냄
    - 관례 : 대문자 + _    or   소문자 + _    방식 둘 중에 사용함
- 연관관계 분석 **(연관관계 맵핑 공부 필요!)**
    - 회원 ↔ 주문 : 일대다, 다대일 양방향 관계 (FK 가 다 쪽에 있음)
        - ORDERS 에 있는 멤버가 주인이 됨
    - 주문상품 ↔ 주문 : 다대일 양방향 관계,
        - 오더아이템이 주인 (외래키를 오더아이템이 가짐)
    - 주문상품 ↔ 상품 : 다대일 단방향 관계
        - OrderItem.iteml 을 ORDER_ITEM.ITEM_ID 외래키와 매핑
    - 주문 ↔ 배송 : 일대일 단방향 관계
        - Order.deliver 를 ORDER.DELIVERY_ID 외래키와 매핑
    
    **외래키가 가까운 곳에 있는 것을 연관관계 주인으로 잡는다!**
    
    → 비즈니스상 우위에 있다고 무작정 주인으로 정하면 안 됨
    
- 테이블 관계는 many to many 표현 못함

**엔티티 클래스 개발**

- 예제니깐 클래스에 getter, setter 모두 열고, 단순하게 설계
    - 실무에서는 가급적 getter는 열어두고, setter는 필요할때만 사용
- db 컬럼 id 를 테이블명_id 이런식으로 설정 해줌
- 멤버 ↔ 오더 : 어디 값이 변경 됐을 때, 외래키를 바꿔야 할까?
    - 둘 중 하나를 선택함 → 연관관계 주인 (외래키가 가까운 곳)
    - 주인이 아닌 친구한테 `@OneToMany(mappedBy = "필드")` 어노테이션 붙임
    - `@JoinColumn(name = "필드")` 로 외래키 지정
- Item 에 테이블 전략 설정
    - `@Inheritance(strategy = InheritanceType.*SINGLE_TABLE*)` 붙임
- Enum 타입은 `@Enumerated(EnumType.*STRING*)` 이런 방식으로 꼭 스트링 넣어줌
- 오더 ↔ 딜리버리 : 일대일 관계에선 외래키를 어디에 둘까? 고민이 생김
    - 주로 액세스를 많이하는 쪽에 둠 (그래서 오더에 둠)
- 카테고리 ↔ 아이템 : 다대다 관계 매핑은 어떻게 할까?
    
    `@JoinTable(name = "category_item",`
    
    `joinColumns=@JoinColumn(name = "category_id"),        inverseJoinColumns = @JoinColumn(name = "item_id"))`
    
    - 를 이용해 중간 테이블 추가해줌
    - 실무에선 너무 단순하게 밖에 사용 못해서 안 씀
- 계층구조 하는 법
    
    ```java
    @ManyToOne
        @JoinColumn(name = "parent_id")
        private Category parent;
    
        @OneToMany(mappedBy = "parent")
        private List<Category> child = new ArrayList<>();
    ```
    
    - 이런식으로 연관관계 해주면 됨 (똑같이)
- 외래키 걸고말고는 서비스에 따라 다를 수 있음
- 정리
    - 이론적 → 게터, 세터 다 쓰기보단 별도 메서드 사용이 바람직
        - 실무 → 데이터 조회 할 일이 많아서 게터 정돈 쓰는게 좋음
        - 변경용 별도의 메서드 사용
    - 테이블명_id 하는 이유
        - 단순하게 id 라고 하면 명확하게 구분이 잘 안될 때도 많음
            - 테이블은 따로 타입이란게 없음
            - 관례상으로도 많이 사용함
    - 실무 → ManyToMany 사용 X
    - 임베디드 타입 같은 경우 생성 된후 변경 불가능하게 만들어야 함
    - 그래서 생성자 + protected 를 써서 안전하게 함
        - 엔티티도 protected를 쓰는게 더 안전함

**엔티티 설계시 주의점**

- 가급적 Setter를 사용 X
    - 변경 포인트가 너무 많음 → 유지보수가 어려움
    - 예제에서만 열어둘 것임
- 모든 연관관계는 지연로딩으로 설정!
    - 즉시로딩( EAGER ) : 한 객체 조회할때 연관된 녀석들을 한번에 조회 함
        - 예측이 어렵고 어떤 sql이 사용된지 추적 어려움
        - n + 1 문제가 생김 (하나를 가져오기 위해 쿼리가 n번 날라감)
    - 실무 → 지연로딩( LAZY ) 로 설정
    - 꼭 필요하면 fetch.join , 엔티티 그래프 사용
    - ManyToOne, OneToOne 등은 디폴트 값이 EAGER임 (??ㅅToOne 들)
        - 적어도 ManyToOne은 직접 지연으로 바꿔줘야 함
- 컬렉션은 필드에서 초기화
    - null 문제에서 안전해짐
    - 임의의 메서드에서 컬렉션을 잘못 생성하면 하이버네이트 메커니즘에 문제가 생길 수 있음
        - 객체 생성 후 컬렉션 어지간하면 바꾸지 말기
- 테이블, 컬럼명 생성 전략
    - SpringPhysicalNamingStrategy
        - 카멜 케이스 → 언더스코어(memberPoint → member.point)
        - . (점) → _ (언더스코어)
        - 대문자 → 소문자
        - 이 과정을 거침 ( orderDate → order_date ) 이런식
    - 기본 설정을 바꿀 수도 있음
    - 논리명 생성, 물리명 적용 두가지 있음
- casacade : persist 나 delete 를 전파함
- 연관 관계 편입 메서드
    - 양방향이면 양쪽 모두에 값을 세팅해주는게 좋음 (그러기 위한 메서드)
    - 핵심적으로 컨트롤 해주는 쪽에 이 메서드를 설정해주는게 좋음

### 애플리케이션 구현 준비

**구현 요구사항**

- 회원 기능
    - 회원 등록
    - 회원 조회
- 상품 기능
    - 상품 등록
    - 상품 수정
    - 상품 조회
- 주문 기능
    - 상품 주문
    - 주문 내역 조회
    - 주문 취소
- 예제를 단순화 하기 위해 다음 기능은 구현 X
    - 로그인과 권한 관리X
    - 파라미터 검증과 예외 처리X
    - 상품은 도서만 사용
    - 카테고리는 사용X
    - 배송 정보는 사용X

**애플리케이션 아키텍처**

![Untitled](/pictures/004.png)

- 계층형 구조 사용
    - controller, web : 웹 계층
    - service : 비즈니스 로직, 트랜젝션 처리
    - repository : JPA 직접 사용, 엔티티 매니져 사용
    - domain : 엔티티가 모여 있는 계층
- 유연성을 적용함
- 개발 순서 : 서비스 → 레포지토리 작성 후 검증 → 웹 계층 적용

### 회원 도메인 개발

**회원 리포지토리 개발**

- sql은 테이블을 대상으로 쿼리, jpql은 엔티티 멤버를 대상으로 쿼리
    
    ```java
    @Repository
    public class MemberRepository {
    
        @PersistenceContext
        private EntityManager em;
    
        public void save(Member member){
            em.persist(member);
        }
    
        public Member findOne(Long id){
            return em.find(Member.class, id);
        }
    
        public List<Member> findAll(){
            return em.createQuery("select m from Member m", Member.class)   // jpql 사용
                    .getResultList();
    
        }
    
        public List<Member> findByName(String name){
            return em.createQuery("select m from Member m where m.name = :name", Member.class)
                    .setParameter("name", name)
                    .getResultList();
        }
    
    }
    ```
    
    - @PersistenceContext 쓰면 엔티티 매니져를 자동으로 만들어줌
    - @PersistenceUnit 쓰면 엔티티 매니져 팩토리를 직접 주입 가능
    - em.persist 하면 영속성 컨텍스트에 올려줌 → DB에 저장됨

**회원 서비스 개발**

- `jpa에서 어떤 로직들은 다 트랜젝션 안에서 이뤄지는게 바람직`
    - 읽기에는 readonly에 true 해주는게 좋음
    
    ```java
    @Service
    @Transactional(readOnly = true)  //jpa에서 어떤 로직들은 다 트랜젝션 안에서 이뤄져야 함
    @RequiredArgsConstructor //final 붙은 애들 생성자 말들어줌 -> 생성자 인젝션 가능
    public class MemberService {
    
        private final MemberRepository memberRepository;
    
        //회원 가입
        @Transactional
        public Long join(Member member){
            validateDuplicateMember(member);
            memberRepository.save(member);
            return member.getId(); // persist 하면 id가 생김 → id 값이 있다는게 보장됨
        }
    
        private void validateDuplicateMember(Member member) {
            List<Member> findMembers = memberRepository.findByName(member.getName());
            if(!findMembers.isEmpty()){
                throw new IllegalStateException("이미 존재하는 회원입니다.")
            } //진짜 혹시 모를 상황을 대비해 DB에 멤버의 name을 unique조건으로 달아 놓는걸 추천
        }
    
        //회원 전체 조회
        public List<Member> findMembers(){
            return memberRepository.findAll();
        }
    
        public Member findOne(Long memberId){
            return memberRepository.findOne(memberId);
        }
    }
    ```
    

**회원 기능 테스트**

- 테스트는 완전히 격리된 환경에서 하는게 좋음
    
    → 메모리 DB를 따로 만들어서 함
    
    → 스프링 부트는 별도의 설정이 없으면 메모리 DB에서 알아서 돌려줌
    
    ```java
    package jpabook.jpashop.service;
    
    import jakarta.persistence.EntityManager;
    import jpabook.jpashop.Repository.MemberRepository;
    import jpabook.jpashop.domain.Member;
    import org.assertj.core.api.Assertions;
    import org.junit.Test;
    import org.junit.runner.RunWith;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.test.context.junit4.SpringRunner;
    import org.springframework.transaction.annotation.Transactional;
    
    import static org.assertj.core.api.Assertions.*;
    import static org.junit.jupiter.api.Assertions.*;
    
    @RunWith(SpringRunner.class)
    @SpringBootTest
    @Transactional
    class MemberServiceTest {
    
        @Autowired MemberService memberService;
        @Autowired MemberRepository memberRepository;
        @Autowired EntityManager em;
    
        @org.junit.jupiter.api.Test
        public void 회원가입() throws Exception {
            //given
            Member member = new Member();
            member.setName("kim");
    
            //when
            Long saveId = memberService.join(member);
    
            //then
            assertThat(member.getId()).isEqualTo(saveId);
        }
    
        @org.junit.jupiter.api.Test
        public void 회원_중복_예제() throws Exception {
            //given
            Member member1 = new Member();
            member1.setName("kim");
            Member member2 = new Member();
            member2.setName("kim");
    
            //when
            memberService.join(member1);
            //then
            assertThrows(IllegalStateException.class,
                    ()->memberService.join(member2));
        }
    }
    //junit5으로 해서 이렇게 해야했음
    ```
    

### 상품 도메인 개발

**상품 엔티티 개발 (비즈니스 로직 추가)**

- 데이터를 갖고 있는 곳에서 비즈니스 로직이 나가는게 바람직
    
    → 아이템에 로직 추가
    
- 데이터를 바꿀 일이 있으면 비즈니스 로직을 통해 바뀌는게 바람직
    - 세터를 이용해 이러쿵 저러쿵 하는게 아니라
    
    ```java
    //stock 증가
        public void addStock(int quantity){
            this.stockQuantity += quantity;
        }
    
        //stock 감소
        public void removeStock(int quantity){
            int restStock = this.stockQuantity - quantity;
            if (restStock<0){
                throw new NotEnoughStockException("need more stock");
            }
            this.stockQuantity = restStock;
        }
    ```
    
    - NotEnoughStockException는 따로 exception 패키지 만들어서 RuntimeException 상속 받아왔음

**상품 리포지토리 개발**

```java
package jpabook.jpashop.Repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class itemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null){
            em.persist(item);
        } else {
            em.merge(item); //update 함
        }
    }

    public Item find(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select m from Item m", Item.class)
                .getResultList();
    }
}
```

- 중간에 merge는 만약 이미 아이템이 있다면 아이템 데이터를 업데이트 해주는 역할

**상품 서비스 개발**

```java
package jpabook.jpashop.service;

import jpabook.jpashop.Repository.ItemRepository;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.find(itemId);
    }

}
```

- 이렇게 리포지토리에 단순히 위임만 하는 클래스를 만들 필요가 있을까 고민이 필요

### 주문 도메인 개발

구현 기능

- 상품 주문
- 주문 내역 조회
- 주문 취소

*java 에선 파라미터… 붙이면 파라미터로 여러개를 받을 수 있다는 뜻

1. **주문, 주문상품 엔티티 개발**
- Order, OrderItem 의 생성, 비즈니스, 조회 메서드 작성

1. 주문 리포지토리 개발
- 주문 저장, 주문 찾기 메서드 생성

1. 주문 서비스 개발
- 따로따로 모든 친구들을 리포지토리 만들어서 세이브 해준게 아니라 [orderRepository.save](http://orderRepository.save)만 해준 이유?
    - 매핑 할때 Cascade 옵션을 넣어줬기 때문
        
        → 오더가 persist 될때 casacade 달린 친구들도 persist 됨
        
    - Casacade의 범위?
        - 프라이빗한 친구(다른데서도 막 쓰지 않음) 일때
        - 라이프 사이클이 맞을 때
    - 모르겠음 그냥 쓰지말기

- protected 생성자() 로 다른 스타일의 생성 형태 막기
- 롬복의 `@NoArgsConstructor(access = AccessLevel.*PROTECTED*)` 로 동일한 효과 얻을 수 있음

- 원래는 로직을 바꾼후에도 밖에서 또 쿼리를 쏴줘야 함
    - jpa를 활용하면 알아서 변경내역감지해줘서 DB에 업데이트 쿼리가 날아감
    
- 도메인 모델 패턴 : 엔티티에 핵심 비즈니스 로직을 몰아 넣고 서비스에서 호출만함 (jpa 스타일)
- 트랜잭션 스크립트 패턴 : 엔티티엔 단순한 게터,세터 정도만 있고 서비스에서 로직을 다 설정 (일반적 sql 스타일)
    - 각각의 장단점이 있으니 잘 사용할 것

1. 주문 기능 테스트
- 좋은 테스트는 스프링도 없이 순수한 코드로 단위테스트하는게 좋음
- 주문 기능 테스트
- 주문 했을때 수량이 재고 초과했을때 테스트
- 주문 캔슬 테스트
- 도메인 모델 패턴을 사용하면 엔티티에 대해 바로 테스트를 작성할 수 있음
    - 딱 단위테스트로 의미있게

1. 주문 검색 기능 개발
- JPA에서 동적 쿼리를 해결하는 법!
    - 검색할때 회원명을 파라미터로 보내야 해서 동적 쿼리를 쓸 수 밖에 없는 상황
    
    1) jpql을 직접 선언해서 분기문을 통해 문자를 더하고 빼고 함
    
    ```java
    			String jpql = "select o From Order o join o.member m";
          boolean isFirstCondition = true;
    			//주문 상태 검색
    			if (orderSearch.getOrderStatus() != null) {
              if (isFirstCondition) {
                  jpql += " where";
                  isFirstCondition = false;
              } else {
                  jpql += " and";
    					}
              jpql += " o.status = :status";
          }
    			//회원 이름 검색
    			if (StringUtils.hasText(orderSearch.getMemberName())) {
              if (isFirstCondition) {
                  jpql += " where";
                  isFirstCondition = false;
              } else {
                  jpql += " and";
    					}
              jpql += " m.name like :name";
          }
    ```
    
    이런식으로 하면 번거롭고 실수로 버그가 생길 확률이 높음
    
    2) Criteria 이용
    
    - 실무에서 쓰라고 있는게 아님
    - 너무 가독성이 떨어짐 → 유지보수가 안 됨
    
    3) QueryDsl 로 처리
    
    - 그냥 이걸 쓰는게 맞음
    - 스프링 부트 + JPA + QueryDsl 삼위일체로 개발을 깔끔하게 함

### 웹 계층 개발

**홈 화면과 레이아웃 (타임리프 사용)**

- `@Slf4j` 이용해서 로거 사용 가능
- 타임리프 홈페이지서 레이아웃 볼 수 있음
    - 인클루드 : 단순 / 무식, 추가할 때마다 계속 include 해줌
    - 계층형 : include형에서 나오는 중복을 제거해줄 수 있음
- 부트스트랩에서 뷰 리소스를 등록해서 화면 꾸밈
    
    ![Untitled](/pictures/005.png)
    
    *구성된 홈 화면
    

**회원 등록**

- `@NotEmpty` 어노테이션을 사용해서 validation 사용 가능
    - 이게 붙으면 말그대로 비어있으면 안됨
- Model model이란?
    - 컨트롤러 → 뷰 넘어갈때 모델에다 attribute를 실어서 넘어감
- 타임리프 문법을 이용해 html 작성
    - 게터, 세터를  이용한 접근법
- GET, POST 두 방식 다 매핑해야함
    - 겟은 폼 화면을 띄우고 포스트는 실제 데이터를 등록함
- Post
    - form을 validation 가능
        - jakarta.validation 에 있는 기능을 통해
    - validate한 것 다음에 `BindingResult result` 놓으면 result에 에러가 담겨서 진행됨
    
    ```java
    @PostMapping("/members/new")
        public String create(@Valid MemberForm form, BindingResult result){
    
            if(result.hasErrors()){
                return "members/createMemberForm"
            }
    ```
    
    - 이런식으로 쓰면 타임리프와 스프링이 강하게 연동되기 때문에 에러가 났을때 어떤식으로 렌더링 될지 세세하게 정할 수 있음
- 왜 굳이 Member엔티티로 데이터 받지 않고 MemberForm을 새로 만들어서 넣었을까?
    - 멤버엔티티가 화면과 딱 fit 하지 않음
    - validation 하기 귀찮아짐

![Untitled](/pictures/006.png)

**회원 목록 조회**

- 폼을 사용하지 않으면 엔티티가 점점 화면 종속적이 됨
    - 실무에선 엔티티의 순수성을 유지하는게 중요함
    - 화면에 맞는 dto(data transfer object) 로 바꾸는게 좋음
- 지금 실습에선 정말 심플한 상황이라 멤버 엔티티를 그대로 반환했음
- api를 만들땐 !절대! 엔티티를 반환하면 안됨
    - 엔티티에 로직을 추가하면 api 스펙이 변해버림

![Untitled](/pictures/007.png)

***상품 등록 및 조회는 회원 때랑 같음**

![Untitled](/pictures/008.png)

**상품 수정**

- 수정은 변경감지, 병합 두 가지 방법 중 선택
    
    ```java
    @GetMapping("/items/{itemId}/edit")
        public String updateIdForm(@PathVariable("itemId") Long id, Model model)
    ```
    
    이런식으로 path에 pathVariable을 넣어 줄 수 있음
    
- Id를 조심해야함 (조작 될 수 있음) → 권한체크 로직이 있어야 함 (or 세션)

머지가 도대체 머지? → merge는 실무에서 안 씀

![Untitled](/pictures/009.png)

- 준영속 엔티티
    - 엔티티가 영속성 컨텍스트에 관리 될때 값이 바뀌면 커밋 시점에 디비에 올려줌 → 이번 실습에서도 많이 이용된 성질임 (dirty checking)
    - 준영속 엔티티란 영속성 컨텍스트가 더이상 관리하지 않는 엔티티
    - 위에 수정의 경우에서 Id 값을 통해 접근했으니깐 DB에 들어갔다 나온 친구라 영속성 컨텍스트가 더이상 관리하지 않는 준영속 엔티티임
    - 임의로 만들어낸 엔티티여도 기존의 식별자를 가지고 있다 → 준영속 엔티티 → JPA가 관리하지 않음
    - 그럼 어떻게 데이터를 관리 할 수 있을까?
    1. 변경감지(dirty checking)
        - 영속성 컨텍스트에서 엔티티 다시 조회한 후 데이터 수정
    2. 병합
        - 준영속 상태의 엔티티를 영속으로 변경해줌
    
    ![Untitled](/pictures/010.png)
    
    - merge에서 병합된 엔티티는 다시 영속성으로 안 바뀜
    - 변경 감지는 원하는 속성만 변경 가능하지만, 병합은 모든 속성이 변경 됨
        
        → 만약 값이 없다면 null 업데이트가 될 수도 있음
        
        → **변경 감지를 써야한다~**
        
    - 의미 있는 메서드를 써야지 set을 남발하면 안됨 (코드 역추적 가능)
    - 컨트롤러에서 어설프게 엔티티를 생성 X
        - 메서드를 만들어서 폼의 값을 넘김
        - 서비스 계층에 dto 를 하나 만듦
        - 트랜잭션이 있는 서비스 계층에서 영속 상태의 엔티티를 조회하고, 엔티티의 데이터를 직접 변경해야함

**주문 생성**

- `@RequestParam` 어노테이션은 받은 request의 메세지 와 바인딩해줌
- 바깥에서 엔티티를 찾아도 되지만 이렇게 받고 서비스 단위에서 로직이 진행되는게 훨씬 더 깔끔함 ( 영속성을 유지할 수 있음)

![Untitled](/pictures/011.png)

**주문 목록 검색, 취소**

- 정말 단순한 기능(조회처럼)은 service 안 거치고 repo → controller 하는거 생각해봐도 좋음

![Untitled](/pictures/012.png)








- **문제 만들어 보기**
    - 문제 1. sql 과 jpql 의 차이
        
        → sql은 테이블을 대상으로 쿼리를 날리지만 jpql은 엔티티를 대상으로 쿼리를 날림
        
    
    문제 2, 3
    
    ```java
    @Service
    @Transactional(readOnly = true)  //jpa에서 어떤 로직들은 다 트랜젝션 안에서 이뤄져야 함
    @Annotation
    public class MemberService {
    
        private final MemberRepository memberRepository;
    
        //회원 가입
        @Transactional
        public Long join(Member member){
            validateDuplicateMember(member);
            memberRepository.save(member);
            return member.getId(); // persist 하면 id가 생김 → id 값이 있다는게 보장됨
        }
    
        private void validateDuplicateMember(Member member) {
            List<Member> findMembers = memberRepository.findByName(member.getName());
            if(!findMembers.isEmpty()){
                throw new IllegalStateException("이미 존재하는 회원입니다.")
            } //진짜 혹시 모를 상황을 대비해 DB에 멤버의 name을 unique조건으로 달아 놓는걸 추천
        }
    
        //회원 전체 조회
        public List<Member> findMembers(){
            return memberRepository.findAll();
        }
    
        public Member findOne(Long memberId){
            return memberRepository.findOne(memberId);
        }
    }
    ```
    
    - 문제 2. 위의 @Annotation 위치에 들어갈 어노테이션은?
        
        → @RequiredArgsConstructor : final 이 붙은 친구들의 생성자를 만들어줌
        
        - 생성자 인젝션 해줌
    
    - 문제 3. 위에서 @Transactional(readOnly = true) 로 한 이유, readonly 기능을 쉽게 쓰기 위한 방법
        
        → 조회만 하는 데이터의 경우에는 readOnly 기능을 쓰는게 안정성이 더 높음
        
        → 클래스 단위로 readOnly = true로 해놓고, 데이터를 직접 건드려야 하는 경우엔 따로 설정을 해줌