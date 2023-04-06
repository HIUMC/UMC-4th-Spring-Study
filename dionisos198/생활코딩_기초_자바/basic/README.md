-boolean data type

System.out.println("ONe")->STring 형

System.out.println(1)->int 형

불리언은 단 구체적 데이터 2개

System.out.println(true);

System.out.println(false);

true,false->변수 이름으로 사용할 수 없다.

String foo="hello world"일 경우
System.out.println(foo.contains("world")) 이런 메소드가 있을 경우: world가 있으면 true 반환 ,아니면 false 반환

-Comparison Operator

System.out.println(1>1);->false

System.out.println(1==1);->true

System.out.println(1>=1)->true


-Conditinoal Statement(조건문)

if(boolean 값이 들어가야 함){
boolean이 true->괄호 안 내용 실행,boolean->false이면 괄호안 내용 실행 X
}+else,조건문 안에 또다른 조건문 들어갈 수 있다.->else if 로 코드 간결화 가능

-조건문 응용

String id="egoing"
String inputId=args[0]
System.out.println("Hi")
if(inputId==id){
System.out.println("Master!");
}else{
System.out.println("who are you");
}->false가 나온다. 왜? inputId.equals(id))로 바꿔야 한다.
추가적으로 중첩해서 더 복잡한 명령어를 수행할 수 있고,&&연산자를 사용해서 더 간단하게 설계할 수도 있다.
-비교 연산자- ==vs equals
primitive->boolean,int,double,short, 등등 기초적 데이터 타입(더이상 쪼개 질 수 없다)
non primitive->String,Array,Date 등등
String o1="java"와 String o2=new String("java")는 서로 다르게 취급한다.
->원시 데이터 타입이 아닌 것들을 비교하려면 .eqauls(내용이 같으냐)를 활용한다.
단 String o3="java2" 와 String o4="java2"인 경우는 o3==o4 가 true 이다!
-logical operator
&&->왼쪽,오른쪽이 true면 결과 true. 한가지라도 false 이면 false
||->왼쪽 오른쪽이 하나라도 true이면 true 이다.
!(boolean)->boolean의 반대
-반복문
1)while(boolean Datatype)->boolean 값이 false일때까지 계속 반복한다.
2)for(초깃값,불리언값,반복당 실행코드)
for문은 필수적인 요소가 하나로 응집되어 있기 때문에 몇번 반복해 이런건 while문 보다는 for문이 좋다.
-배열
String []users=new String[3];->사용예(users[0]="egoing"으로 삽입 가능)
int []scores={10,20,30}으로 바로 초기화 가능하고 users.length 사용 가능하다.
-반복문 + 배열
반복문 + 배열을 사용함으로써 편리하게 삽입,출력 등 편리하게 사용가능(users.length->편리하게 사용 가능)
-종합응용
반복문에서 break 하면 종료,continue 하면 계속하기