package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);

    }

    static class TestBean{

        @Autowired(required = false) // 의존관계가 없으면? 호출이 아예 안됨 => 아무것도 출력 안 됨
        public void setNoBean1(Member noBean1){
            System.out.println("noBean1 = " + noBean1);
        }// 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안됨
        @Autowired // noBean2 = null 로 출력
        public void setNoBean2(@Nullable Member noBean2){
            System.out.println("noBean2 = " + noBean2);
        } // 자동 주입할 대상이 없으면 null 입력됨
        @Autowired //noBean3 = Optional.empty 로 출력 (자바 문법)
        public void setNoBean3(Optional<Member> noBean3){
            System.out.println("noBean3 = " + noBean3);
        } // 자동 주입할 대상이 없으면 Optional.empty 가 입력됨
    }
}
