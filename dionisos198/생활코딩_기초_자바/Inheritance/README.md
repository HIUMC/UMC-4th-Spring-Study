-수업소개
한 클래스가 한 클래스의 메소드를 복사해서 사용하면 버그개선,변화 등에 약하기 때문에
상속이 필요하다.
상속의 사용 예)
class Cal3 extends Cal{
//cal 클래스의 메소드 변수 모두 사용 가능하다.
}
-기능의 개선과 발전
Cal3를 만든 이유는 Cal에 다가 특정 기능을 추가하고 싶기 때문이다.
또한 Cal에 있는 메소드를 Cal3에  똑같이 작성해서 부모가 가지고 있는 메소드를 재정의 하였다->Overriding이라고 한다.
->Overriding vs Overloading
부모 클래스의 기능을 재정의 한 것은 Overriding
Overloading은 상속과는 상관이 없다. 같은 이름의 메소드를 여러개 과적 가능 하다 ( 형태가 다르면)
ex)public int sum(int v1,int v2,int v3)
puliic int sum(int v1,int v2)
-this& super
this -자기자신 super은 부모
자식클래스에서 오버라이딩 할떄 부모 클래스의 메소드를 이용하고 싶다면 super를 이용한다.
사용 예)
//Overriding
public int sum(int v1,int v2){
System.out.println("Cal3");
return super.sum(v1,v2);
}
this는 자기자신의 instance를 나타낸다.
-상속과 생성자
상속받은 클래스의 부모가 생성자가 있다면 자식은 반드시 부모생성자를 실행시키도록 강제하고 있다.
ex)class Cal3 extends Cal{
Cal3(int v1,int v2){
super(v1,v2);
System.out.println("Cal3 init!!");
}
}
-수업을 마치며
클래스를 다른 클래스 로 교체하는 것이 편리하게 하기 위해서 다형성을 적용을 하는데
ParentClass obj=new ChildClass(); 자식클래스로 동작하도록 한다.
public,protected,defualt,private
Fianl 상속못하게,메소드 오버라이딩 하지 못하게,변수를 수정하지 못하게 해줌
Abstract 어떤 특정한 메소드는 꼭 구현해라 그러면 상속자가 직접 구현해야 하는 기능을 강제할 수 있다.


