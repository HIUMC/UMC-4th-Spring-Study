package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component //스프링빈으로 등록
//@Component 쓰기도 하는데 스프링빈에 직접 등록 더 선호
public class TimeTraceAop {
    @Around("execution(* hello.hellospring..*(..))")
    //@Around로 이 공통 관심 사항을 어디에 적용할 지 설정, 타겟팅
    //이것도 메뉴얼대로 하면 된다. 뭐가 많은데 쓰던 거만 쓰게 된다
    //실행하는 패키지명, 밑에 하위 전체로 설정
    //타겟은 따로 다 설정할 수 있음
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START : " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END : " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
