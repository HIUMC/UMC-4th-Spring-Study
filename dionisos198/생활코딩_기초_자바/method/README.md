메소드라는 것->함수
-이미 익숙한 메소드
Math.floor,prinltn 모두 메소드이다.
psvm도 역시 메소드이다.
-메소드의 기본 형식
반복되는 코드를 간편하게 사용할 수 있다.
예)public static void printTwoTimesA(){
sout,sout,sout;
}
->코드의 의미파악 가능하게 메소드이름 작성,코드양 획기적 줄어듬.
refactoring->다시 공장에 보낸다. 선택한 코드를 메소드로 추출 가능
-메소드의 입력
public static void printTwoTimes(Stirng text){
sout("-");
sout(text);
sout(text);
}
main이란 메소드를 실행시켜야 자바는 메인이란 메소드를 실행시킨다.
String[]->문자열 배열 ,args->자바가 실행할떄 입력값,사용자가 실행할떄 입력해 준 변수를 main안에서 사용할 수 있다.
함수안으로 주입한 구체적 값->인자,함수에서 사용하는 값->매개변수
-메소드의 출력
public static String a()->실행했을 때 String 형 나온다. ->return "a"->메소드 끝날때도 사용
public static int one()->실행 했을때 int 형 나온다. return 1;->메소드 끝날때도 사용
void->return 값이 없다.
메소드 의 출력은 return, 입력은 패러미터
-메소드의 활용
메소드를 이용해서 처리 방법의 이름을 붙이면 읽기 쉽게 바뀌는 효과가 있다.코드의 재사용성이 올라간다.유지보수가 획기적이다.
-access level modifiers
public : 같은 클래스 뿐 아니라 다른 클래스 안에서도 사용 가능
private :같은 클래스 안에서만 사용 가능,내부적으로 사용
protected :
defualt(생략가능)
-static
static -> class method, non-static->instance method
static ->Print.a() 가능 non-static->Print p=new Print();p.a() 가능
