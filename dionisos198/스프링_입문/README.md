<h1>section1</h1>

MAVEN과 GRADLE는 무엇인가?

라이브러리를 가져오고 빌드하는 라이프 사이클까지 관리하는 툴

GROUP에는 주로 기업 도메인 명을 쓰고

artifact에는 빌드된 결과물을 쓴다.

Tymeleaf란 HTML을 만들어 주는 template 엔진 

main은 JAVA와 resources로 구성되어 있는데 

resources에는 자바를 제외한 properties,xml,HTML을 저장할수 있따

.git ignore->깃에는 소스코드만 올라가게 관리해준다.

repositories(){
mavenCentral()->이 사이트에서 라이브러리를 다운로드 받아라라는 뜻.
}

SpringBoot 가 실행되면서(TOmcat이 웹서버 내장) 

톰켓이 웹서버를 띄우며 스프링 부트가 자체적으로 올라온다.

-<h1>section2</h1> 

스프링 부트에서 새로운 기술 찾기:

1)spring .io에 접속

2)projects 들어간다.

3)Springboot해당 분야에서 찾는다.

템플릿엔진 이란?

템플릿은 보통 HTML,CSS,JAVASCRIPT 등으로 작성된 문서와 구조의 레이아웃을 정의한 것인데,템플릿 엔진은 이런 템플릿에 동적으로 데이터를 삽입할 수 있다.

"th:text"는 해당 element 내의 텍스트를 동적으로 변경하는데 사용된다!.

서버를 배포할 떄는 hello-spring-0.0.1-SNAPSHOT.jar 같은 파일을 복사해서 사용한다.

-스프링 웹 개발 기초

1)정적 컨텐츠:서버에서 하는 것없이 파일을 그냥 내리는 것

2)MVC와 템플릿 엔진:웹을 개발하는 가장 많이 하는 방식,서버에서 바꿔서 내려준다.

3)API: 아이폰,안드로이드랑 개발하려면 JSON이라는 데이터 구조 포맷으로 클라이언트에게 데이터로 전달하는게 API 방식이다.

-스프링 컨테이너에서 html을 받을 때 일어나는 일 순서

1)컨트롤러를 먼저 뒤진다.

2)그 다음에 정적 파일을 확인한다.

API방식에서 핵심은 @ResponseBody를 확인해야 함.

--<h1>section3</h1>

회원 서비스 테스트는 JUNIT이라는 테스트 프레임 워크를 이용한다.

refactoring 단축키는 ctrl+alt+M으로 사용 가능

Optional을 사용하면 NULL을 감쌀수 있다는 장점이 있고 NULL을 감쌀 수 있다면 CLient 에 장점이 있다고 한다.

Optional에서 값을 꺼낼때 get을 사용하면 된다.

테스트를 먼저 만들고 구현을 나중에 한것을 TDD라고 한다.

MemberService 입장에서 MemberRepository를 외부에서 넣어주는 것이 Dependency Injection 의존성 주입이라고 한다.

--<h1>section4</h1>

스프링 빈을 등록하는 2가지 방법이 있다

1)컴포넌트 스캔과 의존관계 설정

2)자바코드로 직접 스프링 빈 등록

MemberController 에서 MemberService 객체를 new 로 생성해서 쓸수 있으나 스프링이 관리하면 다 스프링 컨테이너로 등록하고 거기서 받아서 쓰도록 해야한다. 또한 스프링 컨테이너로 등록함으로써 얻는 이점 역시 있다.

컴포넌트 스캔 방식은 스프링이 올라올때 Component 관련 애노테이션이 있다면 다 객체를 하나씩 생성해서 스프링 컨테이너에 등록한다.

MemberController가 생성이 될 때 스프링 빈에 등록 되어 있는 멤버 서비스의 객체를 가져다가 넣어준다.  이걸 Dependency Injection이라고 함.

main이 있는 패키지 하위에 있는 @Component만 스프링 에 등록한다.

DI에 필드 주입 ,setter 주입 ,생성자 주입이 있는데 생성자 주입을 권장함.

--<h1>section5</h1>

컨트롤러가 정적 파일보다 우선순위가 높다.

<input type="text" id="name" name="name" placeholder="이름을 입력하세요"에서 name="name"부분을 보고 setName을 통해서 MemberForm에 넣어준다.
Memberform 의 field가 name123이면 name="name123"이 되어야 한다.

HTML로 데이터를 받아서 객체에 넘길때 @PostMapping 사용한다. 또한 매개변수가 필요하다(매개변수는 객체)

PostMapping 은 보통 데이터를 폼에 넣어서 전달할때 쓰고 GetMapping 은 조회할때 쓴다.

${member.id}와 ${member.name} 은 member.getId()와 member.getName()을 사용한다.

--<h1>section6</h1>

h2database 에서 jdbc:h2:~/test로 최초한번만 실행하고 이후부터는 jdbc:h2:tcp://localhost/~/test으로 소켓을 통해 접근을 통해  충돌가능성을 낮춘다.

파일 ddl.sql에서 sql 적어두면 git에서 파악하기 편리하기 때문에 적어준다.

Test코드에 @SpringBootTest와 @Tranactional을 붙이고 @Transactional 을 붙인다면 롤백을 시킬 수 있다는 장점이 있다.

JPA를 통해서 SQL과 데이터 중심의 데이터 설계에서 객체 중심의 설계로 전환 가능

implementation 'org.springframework.boot:spring-boot-starter-data-jpa'붙여야한다.

JPA 는 인터페이스 ,하이버네이트는 구현체라고 이해하자.

spring.jpa.show-sqp=true//JPA가 날리는 SQL보기 가능
spring.jpa.hibernate.ddl-auto=none//테이블 자동 생성 기능 끄기

JPA를 쓰려면 항상 transactional이 필요하다.

스프링 데이터 JPA를 활용하면 인터페이스 만으로 편리하게 DB에 왔다갔다 가능하다.

<h1>section7</h1>

AOP란:공통 관심 사항과 핵심 관심 사항을 분리할 수 있는 기술,중간에 인터셉팅해서 풀 수 있는 기술이다.

aop를 쓰기 위해서는 @Aspect 를 써야 한다.

memberController 가 호출하는 것은 진짜 memerService 가 아닌 프록시라는 기술로 발생하는 가짜 멤버서비스 이다.  프록시를 사용하는 것은 DI의 장점이다.  DI를 하면 PROXY를 가능하게 하고 AOP가 가능하게 한다.
