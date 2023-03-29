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

-section2 

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

--section3

회원 서비스 테스트는 JUNIT이라는 테스트 프레임 워크를 이용한다.

refactoring 단축키는 ctrl+alt+M으로 사용 가능

Optional을 사용하면 NULL을 감쌀수 있다는 장점이 있고 NULL을 감쌀 수 있다면 CLient 에 장점이 있다고 한다.

Optional에서 값을 꺼낼때 get을 사용하면 된다.

테스트를 먼저 만들고 구현을 나중에 한것을 TDD라고 한다.

MemberService 입장에서 MemberRepository를 외부에서 넣어주는 것이 Dependency Injection 의존성 주입이라고 한다.

