# 스프링 입문

실무 개발에 꼭 필요한 내용 학습

학습 방법
 - 직접 코딩하기

- 프로젝트 생성
    - start.spring.io 접속
        
        -Gradle-Groovy 로 선택
        
        -프로젝트명 : hello-spring
        
        -디펜덴시 - spring-web, thymeleaf
        
    - 생긴 파일들 해설
        
        -gradle 은 버젼 설정하고 라이브러리 땡겨옴
        
        -repository : 라이브러리를 다운 받을 곳을 적어 놓음 (수정도 가능)
        
        -dependencies : 기본적으로 들어가는거 + 아까 설정한거
        
        -말고도 git 이나 gradle 관련 파일들
        
    - springboot 실행
        
        main 실행 → springbootApplication 내장 Tomcat 서버로 서버 실행
        
- 라이브러리 살피기
    
    -gradle이 의존관계에 있는 라이브러리를 다 땡겨와 줌
    
    -따라서 스프링부트 라이브러리를 땡기면 관련 라이브러리를 다 땡겨와줌 (코어, embeded 서버 등등)
    
    *현업에선 system.out.println() 말고, logging을 이용해야 함 (나중에 모아볼수도 있고 다재다능)
    
    - 스프링 부트 라이브러리
        - spring-boot-starter-web
            - spring-boot-starter-tomcat: 톰캣 (웹서버)
            - spring-webmvc: 스프링 웹 MVC
        - spring-boot-starter-thymeleaf: 타임리프 템플릿 엔진(View)
        - spring-boot-starter(공통): 스프링 부트 + 스프링 코어 + 로깅
            - spring-boot
                - spring-core
            - spring-boot-starter-logging
                - logback, slf4j
    - 테스트 라이브러리
        
        spring-boot-starter-test
         - junit: 테스트 프레임워크
         - mockito: 목 라이브러리
         - assertj: 테스트 코드를 좀 더 편하게 작성하게 도와주는 라이브러리
         - spring-test: 스프링 통합 테스트 지원
        
- view 환경설정
    - welcome 페이지 만들기 (index.html)
        
        ```jsx
        <html>
        <head>
         <title>Hello</title>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        </head>
        <body>
        Hello
        <a href="/hello">hello</a>
        </body>
        </html>
        ```
        
    
    [spring.io](http://spring.io) 에서 boot 들어가서 검색하면 됨
    
    템플릿 엔진에서 검색해서 페이지를 동적으로 만들 수 있음
    
    mvc = model view controller
    
    ![Untitled](%E1%84%89%E1%85%B3%E1%84%91%E1%85%B3%E1%84%85%E1%85%B5%E1%86%BC%20%E1%84%8B%E1%85%B5%E1%86%B8%E1%84%86%E1%85%AE%E1%86%AB%20d6caccb1948f495588423175550db424/Untitled.png)
    
- 스프링 웹개발 기초
    - 정적 컨텐츠 - like 웰컴 페이지, 그냥 파일을 브라우져에 내려줌.
        - 스프링 부트는 자동 제공, static contents라고 검색해보셈
        - resouces에 있는 static 폴더
        
        ![Untitled](%E1%84%89%E1%85%B3%E1%84%91%E1%85%B3%E1%84%85%E1%85%B5%E1%86%BC%20%E1%84%8B%E1%85%B5%E1%86%B8%E1%84%86%E1%85%AE%E1%86%AB%20d6caccb1948f495588423175550db424/Untitled%201.png)
        
    - MVC와 템플릿 엔진 - html을 그냥 주는게 아니라 서버에서 조금 동적으로 바꿔 줌 (이걸 위해서 전에 컨트롤러 이런거 했던거임)
        - Model View Controller 임. 과거엔 뷰와 컨트롤러가 구분 X
        - 그런데 현재는 구분함 → 뷰는 화면구현에 모든 역량을 집중해야 함, 컨트롤러는 뒷단 로직에 집중하고 모델에 화면과 관련된걸 담아서 보내줌
        - 예를 들어, 뷰파일 하나에 다 박으면 나중에 다른 사람은 구분도 못함
        - RequestParam(”name”) 했을때 주소창에 /경로?name=”입력” 으로 입력했음
        
        ![Untitled](%E1%84%89%E1%85%B3%E1%84%91%E1%85%B3%E1%84%85%E1%85%B5%E1%86%BC%20%E1%84%8B%E1%85%B5%E1%86%B8%E1%84%86%E1%85%AE%E1%86%AB%20d6caccb1948f495588423175550db424/Untitled%202.png)
        
    - API - 다른 OS, 서버끼리 데이터 흐를때 요즘은 json이란 포맷으로 client한테 보내줌
        - data 로 바로 내린다, html 방식은 소스를 봤을 때 입력을 html 템플릿 포맷으로 갔다면 API는 data 그대로, 그래서 data를 달라는 요청을 할때 많이 씀
        - @ResponseBody → https 프로토콜의 body msg 부분에 이 데이터를 직접 넣겠다는 의미
        - 요즘은 json 방식으로 함 (key 와 밸류) - 심플하기 때문
        - @ResponseBody 사용원리
        - 객체가 넘어오면 Json 작동, 그냥 String 객체면 StringConverter
        
        ![Untitled](%E1%84%89%E1%85%B3%E1%84%91%E1%85%B3%E1%84%85%E1%85%B5%E1%86%BC%20%E1%84%8B%E1%85%B5%E1%86%B8%E1%84%86%E1%85%AE%E1%86%AB%20d6caccb1948f495588423175550db424/Untitled%203.png)
        
- 회원 관리 예제 개발
    - 비즈니스 요구사항 정리
        
        데이터 : 회원 ID, 이름
        
        기능 : 회원 등록, 조회
        
        아직 DB가 선정되지 않음
        
        ![Untitled](%E1%84%89%E1%85%B3%E1%84%91%E1%85%B3%E1%84%85%E1%85%B5%E1%86%BC%20%E1%84%8B%E1%85%B5%E1%86%B8%E1%84%86%E1%85%AE%E1%86%AB%20d6caccb1948f495588423175550db424/Untitled%204.png)
        
        ![Untitled](%E1%84%89%E1%85%B3%E1%84%91%E1%85%B3%E1%84%85%E1%85%B5%E1%86%BC%20%E1%84%8B%E1%85%B5%E1%86%B8%E1%84%86%E1%85%AE%E1%86%AB%20d6caccb1948f495588423175550db424/Untitled%205.png)
        
    - 회원 도메인과 레포지토리
        1. hello.hellospring 패키지에 domain 패키지를 생성
            - domain 패키지에 Member 클래스 생성 → Id, name 있음. 각각 get, set 함수 있음
        2. hello.hellospring 패키지에 repository 패키지 생성 후
        
         그곳에 MemberRepository 인터페이스 생성
        
        - save, findByID, findByName, findall 함수 선언
            - findBy는 Optional<Member>로 선언해서 NULL반환시 Optional로 감쌀 수 있게 함
        1. repository 패키지에 MemoryMemberRepository 클래스 생성
            - implements 통해서 위에 인터페이스에 선언했던것들 구현
            
            `private static Map<Long, Member> *store* = new HashMap<>();`
            
            `private static Long *sequence* = 0L;`
            
            멤버 생성 - store 엔 멤버 찾은 결과를 저장하고, sequence는 멤버에 ID 부여.
            
        
        느낀점 - java의 문법에 더 익숙해져야 할 듯 (람다식 같은거 …)
        
    - 회원 리포지토리 테스트 케이스 작성
        
        테스트 케이스 : 방금 만든 클래스가 정상적으로 작동하는지 검증하는 방법
        
        메인 메소드를 이용해서 테스트를 하게되면 너무 오래걸리고, 반복하기 어려움
        
        → 자바는 JUnit이라는 프레임워크로 테스트를 해서 이런 문제 해결
        
        - 테스트 관례 : 만들때 “테스트할파일+test”
        - 메소드는 순서에 의존적으로 설계하면 안 됨
        - @AfterEach : 매 테스트 후 그 메소드를 실행하게 함
        - test 부터 작성해놓고 그 뒤에 repository를 만들 수도 있음 : tdd(테스트주도개발)
    - 회원 서비스 개발
        
        회원 repository, domain 활용해서 실제 비즈니스 로직을 작성함
        
        service 클래스는 좀 더 비즈니스 의존적으로 설계해야 함 (네이밍부터 …)
        
    - 회원 서비스 테스트
        
        command+shift+T 로 테스트 생성가능
        
        test는 직관적으로 한글로 해도 됨.
        
        given, when, then 문법으로 설계
        
        DI 를 통해 애매한 부분을 해결해줌
        
        → 일단 객체 생성하고 나중에 new를 직접 지정
        
- 스프링 빈과 의존관계
    - 컴포넌트 스캔 이용 빈 등록, 자동 의존관계 설정
        
        화면을 붙임 (회원가입, 결과 html로 뿌려주기) → 컨트롤러, 뷰 템플릿 필요
        
        →멤버 컨트롤러 필요 : 멤버 서비스 통해 회원가입, 데이터 조회 (컨트롤러가 서비스를 의존)
        
        @Controller 만들면 spring 컨테이너 에 그 컨트롤러의 객체(빈)를 넣어둠
        
        객체를 여러번 new하지 말고 스프링 컨테이너에 등록하고 불러서 씀
        
        → @Autowired 로 자동으로 연결해줌
        
        @Component 하면 스프링 빈 자동으로 등록 - Controller, Service, Repository
        
        →이게 컴포넌트 스캔 (스프링 빈 등록하는 법 1)
        
        →기본적으로 Application 패키지 포함 하위에 있는 패키지들만 컴포넌트 스캔 가능
        
        →싱글톤(유일)으로 등록해서 공유
        
        애지간한건 다 스프링 빈으로 등록해서 써야 이점이 많음
        
    - 자바 코드로 직접 스프링 빈 등록하기
        
        SpringConfig 파일 만들기 →@Configuration 선언 →@Bean 선언(Bean등록할거야~)
        
        ```jsx
        @Configuration
        public class SpringConfig {
        
            @Bean
            public MemberService memberService(){
                return new MemberService(memberRepository());
            }
        
            @Bean
            public MemoryMemberRepository memberRepository(){
                return new MemoryMemberRepository();
            }
        }
        ```
        
    
    각각의 장단점 있음
    
    ![요즘은 생성자 주입이 대세 / 현재 시나리오에선 상황에 따라 레포지토리 바꿔야 해서 설정으로 스프링 빈 해놨음](%E1%84%89%E1%85%B3%E1%84%91%E1%85%B3%E1%84%85%E1%85%B5%E1%86%BC%20%E1%84%8B%E1%85%B5%E1%86%B8%E1%84%86%E1%85%AE%E1%86%AB%20d6caccb1948f495588423175550db424/Untitled%206.png)
    
    요즘은 생성자 주입이 대세 / 현재 시나리오에선 상황에 따라 레포지토리 바꿔야 해서 설정으로 스프링 빈 해놨음
    
- 회원 웹 기능
    - 홈 화면 추가
        
        HomeController , home.html 추가해서 홈화면 넘김
        
        ```jsx
        @Controller
        public class HomeController {
        
            @GetMapping("/")
            public String home(){
                return "home";
            }
        }
        ```
        
        URL에 / 를 받기 때문에 바로 GetMapping으로 넘어가서 index.html이 아닌 home.html이 불러와짐
        
    - 등록
        
        data를 어딘가 폼같은데에 넣어서 전달할 때 post 방식 → @PostMapping
        
        get 방식은 뭔가 조회 할때 → @GetMapping
        
        - 같은 url 이더라도 get, post 두 방식으로 다르게 매핑 할 수 있음
        
        ```jsx
        @GetMapping("/members/new")
            public String createForm() {
                return "members/createMemberForm";
            }
        
            @PostMapping("/members/new")
            public String create(MemberForm form){
                Member member = new Member();
                member.setName(form.getName());
        
                memberService.join(member);
        
                return "redirect:/";
            }
        ```
        
        input 받은 name이 form의 name으로 넘어가고 그걸 이용해서 Member 클래스의 객체 생성해서 join 시킴
        
    - 조회
        - 코드
            
            ```jsx
            @GetMapping("/members")
                public String list(Model model){
                    List<Member> members = memberService.findMembers();
                    model.addAttribute("members", members);
                    return "members/memberList";
                }
            ```
            
            ```jsx
            <!DOCTYPE HTML>
            <html xmlns:th="http://www.thymeleaf.org">
            <body>
            <div class="container">
              <div>
                <table>
                  <thead>
                  <tr>
                    <th>#</th>
                    <th>이름</th> </tr>
                  </thead>
                  <tbody>
                  <tr th:each="member : ${members}">
                    <td th:text="${member.id}"></td>
                    <td th:text="${member.name}"></td>
                  </tr>
                  </tbody>
                </table>
              </div>
            </div> <!-- /container -->
            </html>
            </body>
            ```
            
        
        html은 타임리프 문법으로 알아서 잘 model 안에 있는 값들을 전달받음
        
        멤버 밸류를 넘기기 위해 model 만들어서 잘 포장해서 넘겼음
        
    
    하지만, 지금까지 했던 자료들은 모두 메모리 안에 저장되기 때문에 자바를 재실행하면 다 날라간다는 점…
    
- 스프링 DB 접근 기술
    - H2 데이터베이스 설치
        
        교육용으로 좋음, 용량 적고, 가볍고, 웹으로 어드민 화면도 줌
        
    - 순수 JDBC
        1. build.gradle 파일에 jdbc, h2 데이터베이스 관련 라이브러리 추가
        2. 스프링 부트 데이터베이스 연결 설정 추가
        3. 연결 됐으면 멤버 넘기기 위한 jdbc 레포지토리 구현
            1. db에 붙기 위한 datasource 선언
            2. datasource를 통해 커넥션을 받아서 받은 data를 db에 날림
            3. 우리가 MemoryMemberRepository에 했던 함수들을 jdbc멤레포 에 옮기기 위한 방대한 양의 코드들을 씀. ex) save메소드에서 db에서 sequence를 받아옴 …
            4. db와 만들었던 커넥션은 다 쓰고나면 제때제때 끊어줘야 함 → 클난다
            5. 쿼리를 sql로 날린다 첨 할때
            6. 커넥션은 DataSourceUtils를 통해 주고받아야 함
        4. SpringConfig 설정 → 아까 MemoryMemberRepositoy 였던 부분만 JdbcMemberRepository로 바꾸고 스프링 자체 데이터소스만 연결해주면 됨
            1. 이게 스프링이 좋은 이유임 → 스프링이 지원해줌
            2. 어플리케이션을 조립하는 코드만 손대면 나머진 손 안대도 됨
        
        ![Untitled](%E1%84%89%E1%85%B3%E1%84%91%E1%85%B3%E1%84%85%E1%85%B5%E1%86%BC%20%E1%84%8B%E1%85%B5%E1%86%B8%E1%84%86%E1%85%AE%E1%86%AB%20d6caccb1948f495588423175550db424/Untitled%207.png)
        
    - 스프링 통합 테스트
        - 이전의 테스트들은 스프링과 관련 X, 현재는 그럴 수 없으니 테스트를 스프링과 엮어서 함
        - test case할땐 그냥 편한대로 하셈 like) 필드 @Autowired
        - @SpringBootTest : 스프링 컨테이너와 테스트를 함께 실행한다.
        - @Transactional : 테스트 케이스에 이 애노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고, 테스트 완료 후에 항상 롤백한다. 이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다. // 이런식으로 테스트 케이스에 붙었을 때만 항상 롤백하도록 동작
        - test 전용 db 따로해서 test
        - 가능한 한 순수한 단위 테스트(코드만으로 할 수 있는)가 젤 좋을 수 있음
    - 스프링 JdbcTemplate
        - jdbc랑 동일하게 설정하면 됨 . 실무에서도 많이 씀
        - 스프링 JdbcTemplate과 MyBatis 같은 라이브러리는 JDBC API에서 본 반복 코드를 대부분 제거해준다. 하지만 SQL은 직접 작성해야 한다.
        
        *생성자 하나일때 @Autowired 생략 가능
        
        - 쿼리를 RowMapper를 이용해 간단하게 가능
        - JdbcTemplateMemberRepository 코드
            
            ```jsx
            package hello.hellospring.repository;
            
            import hello.hellospring.domain.Member;
            import org.springframework.jdbc.core.JdbcTemplate;
            import org.springframework.jdbc.core.RowMapper;
            import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
            import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
            
            import javax.sql.DataSource;
            import java.util.*;
            
            public class JdbcTemplateMemberRepository implements MemberRepository {
            
                private final JdbcTemplate jdbcTemplate;
            
                public JdbcTemplateMemberRepository(DataSource dataSource) {
                    jdbcTemplate = new JdbcTemplate(dataSource);
                }
            
                @Override
                public Member save(Member member) {
                    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
                    jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
            
                    Map<String, Object> parameters = new HashMap<>();
                    parameters.put("name", member.getName());
            
                    Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
                    member.setId(key.longValue());
                    return member;
                }
            
                @Override
                public Optional<Member> findById(Long id) {
                    List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
                    return result.stream().findAny();
                }
            
                @Override
                public Optional<Member> findByName(String name) {
                    List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
                    return result.stream().findAny();
                }
            
                @Override
                public List<Member> findall() {
                    return jdbcTemplate.query("select * from member",memberRowMapper());
                }
            
                private RowMapper<Member> memberRowMapper(){
                    return (rs, rowNum) -> {
                        Member member = new Member();
                        member.setId(rs.getLong("id"));
                        member.setName(rs.getString("name"));
                        return member;
                    };
                }
            
            }
            ```
            
        - 자만하지 말고 테스트 잘 작성해서 하기 !
    - JPA
        - JPA를 들어가며
            - 기존의 반복 코드는 물론이고, 기본적인 SQL도 JPA가 직접 만들어서 실행해준다.
            - SQL과 데이터 중심의 설계 → 객체 중심의 설계로 패러다임을 전환을 할 수 있다.
            - 개발 생산성을 크게 높일 수 있다.
            - jpa라는 것은 인터페이스 제공 → 자바진영의 표준 인터페이스 - 구현은 업체들이
            - 영한씨의 책 덕분에 대한민국에서 널리 퍼짐
        - JPA 환경설정
            - build-gradle 서 dependencies 추가하고 library 다운
            - spring boot 에 sql 보이기, 테이브 자동 생성 끄기
            - 엔티티를 매핑 해야함 - 여기부터 이어서
            - ORM임 Object 와 relational db의 테이블을 매핑함 - 이게 엔티티
            - DB 에 값을 저장하면 DB에서 ID 자동으로 생성하는거 - IDENTITY 전략
        - JPA 사용
            - EntityManager를 이용해서 함. 이건 매핑하면 스프링부트가 알아서 만들어줌
                - 전에 썼던 데이터소스나 이런걸 내부적으로 다 들고 있어서 알아서 다 처리가능
            - String형을 가지고 놀땐 jpql 이라는걸 작성해야 함 → sql과 비슷하지만 객체 자체를 지정
            - @Transactional 이 있어야 함. 데이터를 저장하거나 변경할 땐 → 모든게 저 안에서 실행
            - JpaMemberRepository의 코드
                
                ```jsx
                package hello.hellospring.repository;
                
                import hello.hellospring.domain.Member;
                import jakarta.persistence.EntityManager;
                import org.springframework.transaction.annotation.Transactional;
                
                import java.util.List;
                import java.util.Optional;
                
                @Transactional
                public class JpaMemberRepository implements MemberRepository{
                
                    private final EntityManager em;
                
                    public JpaMemberRepository(EntityManager em) {
                        this.em = em;
                    }
                
                    @Override
                    public Member save(Member member) {
                        em.persist(member);
                        return member;
                    }
                
                    @Override
                    public Optional<Member> findById(Long id) {
                        Member member = em.find(Member.class, id);
                        return Optional.ofNullable(member);
                    }
                
                    @Override
                    public Optional<Member> findByName(String name) {
                        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                                .setParameter("name", name)
                                .getResultList();
                        return result.stream().findAny();
                    }
                
                    @Override
                    public List<Member> findall() {
                        return em.createQuery("select m from Member m", Member.class)
                                .getResultList();
                    }
                }
                ```
                
                - 확연히 간결해지고 jpql을 사용하는 것을 볼 수 있음
    - 스프링 데이터 JPA
        - 인터페이스만으로도 개발 완료 가능, 반복적인 기본 CRUD 기능도 다 제공
        - “금상첨화”의 ‘화’ → 핵심 비즈니스 로직을 개발하는데, 집중 할 수 있게 해줌
        - jpa를 편리하게 사용하도록 도와주는 것이기 때문에 jpa공부가 선행돼야 함
        - SpringDataJpaMemberRepository 의 코드
            - 굉장히 간결해진 코드, JpaRepository에서 기본 CRUD 다 줘서 거기서 안 주는 findByName 만 선언해줌 → 스프링데이터JPA가 알아서 가져다 씀
            - ex) findByName을 쓰면
            
            →  select m from Member m where [m.name](http://m.name) = ? 이런 jpql로 알아써 짜서 줌
            
            ```jsx
            package hello.hellospring.repository;
            
            import hello.hellospring.domain.Member;
            import org.springframework.data.jpa.repository.JpaRepository;
            
            import java.util.Optional;
            
            public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
            
                Optional<Member> findByName(String name);
            }
            ```
            
        - 단순한건 인터페이스만으로 끝나네 ㄹㅇ…
        - 제공 기능
            
            인터페이스를 통한 기본적인 CRUD
            findByName() , findByEmail() 처럼 메서드 이름 만으로 조회 기능 제공 
            
            페이징 기능 자동 제공
            
        
        -findAll() 메서드를 findall()로 작성했다가 오류남, 그 규칙을 잘 지켜줘야 할 듯
        
    
    실무에선 이 모든 기술들을 두루두루 다 알고 써야 함 → 대접의 이유
    
- AOP
    
    Aspect Oriented Programming - 공통 관심사 분리
    
    ![Untitled](%E1%84%89%E1%85%B3%E1%84%91%E1%85%B3%E1%84%85%E1%85%B5%E1%86%BC%20%E1%84%8B%E1%85%B5%E1%86%B8%E1%84%86%E1%85%AE%E1%86%AB%20d6caccb1948f495588423175550db424/Untitled%208.png)
    
    - AOP가 필요한 상황
        - 공통관심사항 : 앱의 기능에 전반적으로 공통적으로 사용되는 기능들
        - 핵심관심사항 : 한 메서드를 수행할 때 공통관심사항과 달리 그 기능을 수행할때 핵심이 되는 로직
        - 둘을 나눠서 관리해야 더 편함 → 이때 AOP 적용하면 됨
    - AOP 적용
        - AOP는 빈에 직접 등록해주는게 좋음 ← 남이 봤을 때 “아! AOP 쓰고있구나!?”
        - 호출이 될 때마다 joinPoint 라는데에서 조작을 막 함
        - AOP 동작 원리
            
            일단 적용 전에는 컨트롤러→서비스 이렇게 그냥 의존 함
            
            적용 후에는 프록시 → 컨트롤러→프록시(가짜로 만든 서비스) → joinPoint.proceed() 하면 → 실제 서비스 / 이런 방식임
            
            AOP가 프록시를 알아서 DI(빈 만들기) 해줌. → AOP 가능해짐
            
            - AOP코드
                
                ```jsx
                package hello.hellospring.aop;
                
                import org.aspectj.lang.ProceedingJoinPoint;
                import org.aspectj.lang.annotation.Around;
                import org.aspectj.lang.annotation.Aspect;
                import org.springframework.stereotype.Component;
                
                @Component
                @Aspect
                public class TimeTraceAop {
                
                    @Around("execution(* hello.hellospring..*(..))")
                    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
                
                        long start = System.currentTimeMillis();
                
                        System.out.println("Start : " + joinPoint.toString());
                
                        try{
                            return joinPoint.proceed();
                        } finally {
                            long finish = System.currentTimeMillis();
                            long timeMs = start - finish;
                
                            System.out.println("END : " + joinPoint.toString() + " " + timeMs + "ms");
                        }
                
                    }
                }
                ```
                
            

![Untitled](%E1%84%89%E1%85%B3%E1%84%91%E1%85%B3%E1%84%85%E1%85%B5%E1%86%BC%20%E1%84%8B%E1%85%B5%E1%86%B8%E1%84%86%E1%85%AE%E1%86%AB%20d6caccb1948f495588423175550db424/Untitled%209.png)