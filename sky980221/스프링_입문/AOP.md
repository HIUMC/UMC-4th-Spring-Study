AOP
====

1. AOP가 필요한 상황

AOP(Aspect Oriented Programming)가 필요한 상황

모든 메소드의 호출 시간을 측정하고 싶다면?
공통 관심 사항(cross-cutting concern) vs 핵심 관심 사항(core concern) 
회원 가입 시간, 회원 조회 시간을 측정하고 싶다면?

AOP를 사용하면 유지보수가 쉬워진다. 

공통 관심 사항(cross-cutting concern) vs 핵심 관심 사항(core concern) 분리

해결

회원가입, 회원 조회등 핵심 관심사항과 시간을 측정하는 공통 관심 사항을 분리한다. 시간을 측정하는 로직을 별도의 공통 로직으로 만들었다.
핵심 관심 사항을 깔끔하게 유지할 수 있다.
변경이 필요하면 이 로직만 변경하면 된다.
원하는 적용 대상을 선택할 수 있다

동작 방식 : AOP를 적용하면 서비스가 지정이 되고 
가짜 멤버 서비스 (프록시, Proxy)를 만들어내고 프록시가 먼저 실행되고 joinPoint가 proceed 되면 그 다음에 실제 메소드가 실행된다.
