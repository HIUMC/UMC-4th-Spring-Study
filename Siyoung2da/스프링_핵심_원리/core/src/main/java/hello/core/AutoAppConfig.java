package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "hello.core",
        // 예제 유지할려고
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)

public class AutoAppConfig {
    /*
    @Bean(name = "memoryMemberRepository")  // 자동 빈 겹칠 때는 오류! 수동 빈 vs 자동 빈 만나면 수동 빈이 오버라이딩함.
    MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
    */
}
