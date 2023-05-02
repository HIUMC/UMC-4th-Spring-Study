package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan( //@ 이 붙은 클래스를 찾아서 스프링 빈에 등록
        basePackages = {"hello.core","hello,core.member"}, // 이 패키지부터 스캔 시작(히위 패키지로 스캔 진행)
        basePackageClasses = AutoAppConfig.class, // 지정한 클래스에 해당하는 패키지를 탐색 위치로 지정
        // 위의 설정을 하지 않으면 ? => @ComponentScan 을 붙인 클래스에 해당하는 패키지부터 탐색 시작
        // 권장하는 방법 => 설정 정보 클래스의 위치를 프로젝트 최상단에 두는 것
        excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION,classes = Configuration.class)
)
public class AutoAppConfig {
//
//    @Bean(name="memoryMemberRepository")
//    MemberRepository memberRepository(){
//        return new MemoryMemberRepository();
//    }
}
