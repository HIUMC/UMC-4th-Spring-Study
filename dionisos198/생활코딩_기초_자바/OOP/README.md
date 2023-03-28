-수업소개
메소드를 이용해 작은 부품을 만들고 이걸 결합하여 큰 프로그램을 만드는것이 procedure programming
그루핑 하고 정리 정돈을 위해서 class를 만들고 class를 중심으로 하는 것이 객체 지향 프로그래밍
-남의 클래스& 남의 인스턴스
Math.PI,Math.floor,Math.ceil->Math라는 것은 클래스이고 floor,cil은 메소드이고 PI는 변수이다.
Filewriter f1 =new FileWriter("data.txt")->data.txt 에 파일을 저장하겠다.,여기서 FIleWriter은 클래스이다.
class 의 예)System,Math,FileWriter.instance의 예)f1
-변수와 메소드
변수를 정의해서 반복되는 작업을 훨씬 수월하게 할 수 있다 ex)printA(delimeter),String delimiter="-----";
메소드 안에 있는 정의되어 있는 변수는 메소드에서만 사용가능하기 떄문에 다른 곳에서도 사용하고 싶다면
public static String delimeter="----";->고려한다.
하지만 조금더 깔끔히 정리 가능하길 원한다.
-클래스의 존재 이유와 기본형식
클래스를 사용하면 훨씬 단정한 형식으로 같은 결과를 출력할 수 있다, 그리고 서로 관련되어 있다는 것을 알 수 있다.
-클래스 형식
public,private,protected->접근제어자
하나의 파일 안 에서 여러개의 클래스를 만들면 각각의 클래스가 파일로써 존재하게 된다.
-인스턴스
원형이 되는 것을 복제해서 새로운 것을 만들 수 있고,그것을 사용할 수 있다.
여기서 원형은 class,복제품은 instance라고 할 수 있다.
Print p1=new Print();
p1.delimter="****"->이제 delimter는 instance의 소속
-static
static 붙어 있으면 class 소속,static 이 붙어있지 않으면 instance 소속
클래스를 통해서 static 변수에 접근 가능하나 static이 붙어 있지 않으면 접근 불가이다.
이는 static 메소드도 똑같은데 static 메소드에서 instance 변수에 접근 할 수는 없다.
instance 메소드는 클래스 변수와 instance 변수 모두 접근 가능하다.
인스턴스 에서 static 을 바꾸면 class 도 바꾸지만 non static 을 바꾼다고 class의 값이 바뀌지는 않는다.
-생성자와 ,this
인스턴스가 생성될때 반드시 처리해야할 어떤 작업을 하고 싶을 때 생성자를 이용해서 이런 문제를 해결 가능
ex)public Print(String _delimeter){
delimeter=_delimiter; 혹은 this.delimeter=delimeter;로 사용할 수 있다.(this는 클래스가 instance화 되었을때 그 instance를 가리킨다)
}-->생성자는 클래스의 이름과 똑같아야한다.
-클래스화
메소드와 클래스를 활용해서 서로 연관된 메소드를 모음으로써 코드를  더 깔끔하게 처리할 수 있다.
-인스턴스화
한번 세팅이 되고 그 세팅이 고정되어서 사용되고 끝이라면 클래스 만으로도 사용가능하지만
클래스의 상태가 계속해서 바뀌어 되는 상태가 있다면 instance 화 해서 사용하는 것이 편리하다.
별도의 instance 마다 고유한 상태를 주면 편리하게 제어가 가능. 이때 static 메소드나 변수를 빼서 사용해야 함.
static 을 그래도 사용하는 이유가 이게 맞는가? static 없어도 public double vatRate=0.1이 된다고 알고 있는데!
-마치며
Inheritance
한 클래스의 메소드를 복사해서 다른 클래스에서 사용할 수 있지만 메소드가 바뀌면 다시 복사해야해서 귀찮
->inheritance필요
사용 예)class Parent{
public void method1()
},,,class Child extends Parent(){
void method2()
}
Interface에는 메소드의 이름 형식은 있지만 내용은 적지 않는다.
class Concreate1 implements Contract 라면 Contract 의 구체적인 메소드를 구현해야
-패키지
같은 이름의 클래스가 존재하기 위해서 서로다른 패키지로 담는다.
또 이 패키지는 클래스를 정리 정돈 할 수 있는 장점이 있다.
