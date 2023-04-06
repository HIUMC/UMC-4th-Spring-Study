# 📌스프링 빈과 의존관계
[스프링 빈을 등록하는 2가지 방법]

- 컴포넌트 스캔과 자동 의존관계 설정
  (@control, @service, @repository방식)
- 자바 코드로 직접 스프링 빈 등록하기

## 컴포넌트 스캔과 자동 의존관계 설정

화면을 붙이기 위해선 controller와 view template을 만들어야한다.

Member Controller은 Member Sevice를 통해서 회원가입과 회원 조회를 하기 때문에 이 둘은 **의존관계**가 있다고 표현한다.

스프링을 관리를 하려면 모두 스프링 컨테이너에 등록을 하고, 컨테이너로부터 받아서 써야 한다.

new를 사용할 경우 여러 서비스에서 이 클래스를 사용할 때 이 객체 자체에 기능이 별로 없기 때문에 new를 통한 개별보다 공용으로 사용하는 것이 좋다

따라서 new 사용대신 스프링 컨테이너에 등록하여 많은 서비스가 하나의 객체를 **공용**으로 사용할 수 있게 한다.

```java
private final MemberService memberService;//객체 생성

//생성자
@Autowired
public MemberController(MemberService memberService) {
    this.memberService = memberService;
}
```

위와 같이 객체를 한 뒤 생성자를 만든다.

그 후 @Autowired를 하여 생성자에 스프링 컨테이너에 있는 값들을 가져다 연결해준다

### @Autowired

@Autowired : **생성자**에 스프링 컨테이너에 있는 값들을 가져다 연결해준다

멤버컨트롤러가 생성될 때 Spring bean에 등록되어있는 Member control/ service/ repository 객체를 불러준다

### @control, @service, @repository

각 Controller, Service, Repository 클래스 위에 기능에 따라 추가하면, Spring이 뜰 때 Controller, Service, Repository를 가지고 온다

참고 - @control, @service, @repository는 컴포넌트에 해당한다.

> Dependency Injection(DI)
>
- new를 통해 새로 객체(repository 등)을 생성하는 것이 아닌 외부로부터 데이터를 가져오는 것, 의존관계를 주입해준다.
- 생성자에서 @Autowired를 쓰면 Member Controller가 생성될 때 Spring bean에 등록되어 있는 멤버 서비스 객체를 가져다 넣어준다.

[컴포넌트 스캔 원리]

`@Component` - 애노테이션이 있으면 스프링 빈으로 자동 등록된다.
`@Controller` - 컨트롤러가 스프링 빈으로 자동 등록된 이유도 컴포넌트 스캔 때문이다.
`@Component` - 를 포함하는 다음 애노테이션도 스프링 빈으로 자동 등록된다.

`@Controller`, `@Service`,`@Repository`

- 우리가 실행시키는 HelloSpringApplication의 하위들의 component들만 찾아 Spring bean으로 등록된다. 따라서 동일하거나 상위에 위치한 파일들의 component는 컴포넌트 스캔되지 않는다

<aside>
💡 참고: 스프링은 스프링 컨테이너에 스프링 빈을 등록할 때, 기본으로 **싱글톤**으로 등록한다(유일하게 하나만 등록해서 공유한다) 따라서 같은 스프링 빈이면 모두 같은 인스턴스다. 설정으로 싱글톤이 아니게 설정할 수 있지만, 특별한 경우를 제외하면 대부분 싱글톤을 사용한다

</aside>

--- 

# 📌회원 관리 예제 - 웹 MVC 개발

url이 입력될 때 우선순위는 관련 컨트롤러를 먼저 찾고, 없다면 static 파일을 찾는다.

### Get방식과 Post방식/데이터 등록 과

get방식 : url을 직접 치고 들어가는 것,

```java
//MemberController
@GetMapping("/members/new")
    public String createForm(){
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

`"/members/new"`에 들어갈 경우, @GetMapping에 의해 컨트롤러로 들어가, `"members/createMemberForm"`로 이동한다.

→return하였으므로 template에서 `createMemberForm.html`을 찾는다

→template가 렌더링하여 촐력된다.

이때 <form></form> 태그란 값을 입력할 수 있는 태그로

```java
//createMemberForm.html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org"><body>
<div class="container">
    <form action="/members/new" method="post">
        <div class="form-group">
            <label for="name">이름</label>
            <input type="text" id="name" name="name" placeholder="이름을 입력하세요">
        </div>
        <button type="submit">등록</button>
    </form>

</div> <!-- /container -->
</body>
</html>
```

“/members/new”에 들어가면 이름을 입력할 수 있는 box가 있는데, `<form>`태그 안의 `<input>`에 의해 만들어진 것이다. 또한 **name=”name”의 “name”은 서버로 넘어갈 때의 key에 해당한다**

그 후 등록 버튼을 누르면 `<form action="/members/new" method="post">`의 action에 저장된 주소로 post 방식으로 넘어간다.

→Controller에 있는 `@PostMapping`을 찾아 그 아래의 create 함수를 호출한다.

매개변수에 MemberForm이 있는데, MemberForm클래스에 저장되어있는 name에 입력한 이름이(ex spring)이 들어가게 된다

이때 input 태그의 `name=”name”`을 보고 spring이 이름을 name에 넣어주는 것이다.

- 데이터를 전달, 등록할 때는 Post방식
- 데이터를 조회할 때는 Get방식

---

# 📌스프링 DB 접근 기술

springboot를 테스트하기 위해(DB와 잘 연동이 되는지 확인)서 Test용 클래스에
`@SpringBootTest` 를 추가한다.

Test를 진행할 때 테스트를 위해 등록할 함수가 DB에 있는 경우 오류가 발생한다

DB에 테스트용 데이터와 같은 값이 없을 경우 테스트용 데이터는 DB에 저장되는 문제가 발생한다.

이를 방지하기 위해 `@Transactional`를 컴포넌트에 추가 가능하다.

- @SpringBootTest : **스프링 컨테이너**와 **테스트**를 함께 실행한다.
- @Transactional : 테스트 케이스에 이 애노테이션이 있으면, 테스트 시작 전에 **트랜잭션**을 시작하고, 테스트 완료 후에 항상 **롤백**한다. 이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.

스프링 데이터 JPA를 사용하면,  리포지토리에 구현 클래스 없이 인터페이스 만으로 개발을 완료할 수 있습니다. 그리고 반복 개발해온 기본 CRUD 기능도 스프링 데이터 JPA가 모두 제공합니다.

JPA=객체 + ORN(object relational mapping)

**@Entity** : JPA가 관리하는 Entity

@Id : pk

```java
public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }
```

JPA를 사용하기 위해서는 `EntityManager`를 주입 받아야 한다

```java
//before
List<Member> result = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        return result;
//after
//ctrl+alt+n
return em.createQuery("select m from Member m", Member.class)
                .getResultList();
```

result와 같이변수와 return값이 같을 경우 단축키 `ctrl+alt+n`을 통해 인라인할 수 있다

`"select m from Member m"`를 보면 이전 db쿼리의 경우 id, name 등을 각각 select해야 했다면 이 문장의 경우 m이라는 객체 자체를 select할 수 있게 된다.

인터페이스가 인터페이스를 받을때는 import가 아닌 extends를 써준다

---

# 📌AOP

기능별 시간을 측정하려 할 때, 시간을 측정하는 로직은 **공통 관심 사항(crosscutting concern)**이지만, **핵심 관심 사항(core concern)**은 아니다.

```java
public long join(Member member){

        long start = System.currentTimeMillis();

        try {
            //같은 이름이 있는 중복 회원X
            validateDuplicateMember(member);//중복회원 검증 메서드
            memberRepository.save(member);
            return member.getId();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish -start;
            System.out.println("join = " + timeMs + "ms");
        }
    }
```

이것과 같이 기능 안에 시간 측정 로직을 넣는 것은 핵심 비즈니스의 로직과 섞여 유지보수의 어려움이 있으며, 모든 기능을 하나하나 넣어야 하는 문제가 있다.

시간 측정과 같은 공통 관심 사항을 한번에 관리하기 위해 우리는 **AOP**를 사용할 수 있다.

## AOP설명 및 @Aspect

AOP : Aspect Oriented Programming

- 공통 관심 사항(crosscutting concern) vs 핵심 관심 사항(core concern)을 분리
- 공통 관심 사항을 한 군데 넣고, 원하는 곳에 적용시킨다.
- `@Aspect` : AOP용 컴포넌트

```java
package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Component
public class TimeTraceAop {
    
    @Around("execution(* hello.hello.spring..*(..))")    
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start=System.currentTimeMillis();
        System.out.println("START: "+joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs=finish-start;
            System.out.println("END: "+joinPoint.toString()+" "+timeMs+"ms");
        }
    }
}
```

TimeTraceAop .java를 만든 후 SpringBean에 등록해줘야 한다.

방법

1. SpringConfig에 @Bean으로 등록
2. TimeTraceAop클래스 위에 @Component로 등록

`@Around("execution(* hello.hello.spring..*(..))")` : hello.hello.spring 하위의 모든 파일에게 적용한다

AOP장점

- 회원가입, 회원 조회등 핵심 관심사항과 시간을 측정하는 공통 관심 사항을 분리한다
- 시간을 측정하는 로직을 별도의 공통 로직으로 만들었다.
- 핵심 관심 사항을 깔끔하게 유지할 수 있다.
- 변경이 필요하면 이 로직만 변경하면 된다.
- 원하는 적용 대상을 선택할 수 있다.