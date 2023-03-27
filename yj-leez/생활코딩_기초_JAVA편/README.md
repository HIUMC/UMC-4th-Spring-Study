제어문
- boolean data type

  : true, false

- comparison operator

  : boolean data type을 만들어내는 유일한 연산자, >, <, >=, <=.


- conditional statement: 조건에 따라서 실행되는 순서를 제어

    ```java
    public class AuthApp {
     
        public static void main(String[] args) {
             
            String id = "egoing";
            String inputId = args[0];
             
            String pass = "1111";
            String inputPass = args[1];
             
            System.out.println("Hi.");
    
            //if(inputId==id && inputPass==pass) 
            if(inputId.equals(id) && inputPass.equals(pass)) {
                System.out.println("Master!");
            } else {
                System.out.println("Who are you?");
            }       
     
        }
     
    }
    ```

    - primitive 타입의 데이터: 더 이상 쪼갤 수 없는 데이터. 이미 값이 Heap에 저장되어 있다면 새로 만든 변수는 이미 저장되어 있는 값을 가리킨다. 예외적으로 String도 이미 저장되어 있는 값을 가리킨다. boolean, int, double, short, long, float, char
    - non primitive 데이터: String, Array, Date, File.
    - ==연산자는 값이 아닌 값이 같은 곳을 가리키고 있는지 비교하므로 primitive 데이터는 ==로 계산해도 되지만 non primitive 데이터는 값이 같은지 내부적으로 계산하는 equals method를 써야 한다.

- looping statement
```java
public class LoopArray {
 
    public static void main(String[] args) {
        /*
         *  <li>egoing</li>
         *  <li>jinhuck</li>
         *  <li>youbin</li>
         */
         
        String[] users = new String[3];
        users[0] = "egoing";
        users[1] = "jinhuck";
        users[2] = "youbin";
         
        for(int i=0; i<users.length; i++) {
            System.out.println(users[i]+",");
        }
         
    }
 
}
```

method
- 이미 익숙한 메소드

  main method

    ```java
    public static void main (String[] args) {} 
    ```

  System class의 println method

    ```java
    System.out.println("Hello Method");
    ```

  Math class의 floor method

    ```java
    System.out.println(Math.floor(1.1));
    ```


- 메소드의 기본 형식

  메소드로 그룹핑하여 정리하고 메소드명으로 어떤 코드인지 바로 파악이 가능하다.

  코드들을 메소드화 할 때 메인함수 밖, 클래스 내에 public static void 메소드명()으로 정의한다.


- 메소드의 입력 값

  arguments(인자): 함수 안으로 주입한 구체적인 값.

  parameter(매개변수): 메소드 사용하는 쪽에서 주입한 값을 메소드 안으로 흘려보내주는 매개자.


- 메소드의 출력

  메소드의 리턴값은 메소드의 실행결과.

  메소드의 리턴값은 메소드를 종료시키는 역할.

  메소드는 리턴값이 어떤 데이터인지 정해줘야한다. void는 리턴값이 없음을 의미한다.

- 메소드의 활용

  코드들을 메소드로 하나로 묶어서 처리를 하면 이 메소드에서 오류가 났을 때 메소드만 수정하여 한 번에 유지보수가 가능한 장점이 있다.


- access level modifier
    - public: 다른 클래스에서도 접근 가능한 메소드
    - private: 같은 클래스에서만 접근 가능한 메소드

- static

  static이라는 키워드가 붙은 메소드는 class method이다.

    ```java
    public static void a(String delimiter) {
            System.out.println(delimiter);
            System.out.println("a");
            System.out.println("a");
     }
    --------------------------------
    Print.a("-");
    ```

  static이 없는 메소드는 instance method이다.

    ```java
    public void a() {
            System.out.println(this.delimiter);
            System.out.println("a");
            System.out.println("a");
        }
    --------------------------------
    Print t1 = new Print();
    t1.delimiter = "-";
    t1.a();
    ```


객체지향프로그래밍
- 클래스

  : 연관된 메소드와 변수들을 묶어 하나의 이름을 갖는 객체이다.


- 인스턴스

  : 인스턴스는 클래스를 복제하여 해당 클래스의 기능을 기반으로 작동하는 새로운, 하지만 복제본인 객체.

  변경 사항이 있을 때 일일이 값을 변경하지 않아도 클래스에서 수정하여 인스턴스에 공통반영하던지

  변경사항에 맞는 새로운 인스턴스를 만들어 작업을 용이하게 할 수 있다.


- static

  ![스크린샷 2023-03-25 오후 7.43.33.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/4a8d0f01-c1a4-4007-a741-78e280bafa4a/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2023-03-25_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_7.43.33.png)

  static 변수나 메소드를 바꾸면 클래스와 모든 인스턴스에 변화가 적용된다.

  static이 없는 변수나 메소드는 독립적으로 적용된다.

- 생성자와 this

  생성자 함수 : 클래스와 똑같은 이름의 method를 정의한 함수

  클래스가 인스턴스화 될 때 실행되어야 할 것을 construct method 안에 작성함으로써 초기화의 목적을 달성한다.

  this. : 인스턴스 변수를 가리키는 특수한 단어

상속
: 어떤 클래스가 가지고 있는 메소드와 변수를 상속해서 다른 클래스가 갖도록 하는 것.

상속함으로써 코드의 재사용성, 유지보수 편의성 그리고 가독성을 높이고, 코드의 양을 줄일 수 있다.

- Overriding & Overloading

  Overriding : 부모 클래스의 메소드 재정의.

  Overloading : 상속과 관계 없이 이름은 같지만 매개변수/ 리턴타입이 다른 메소드.


- this & super

  this. : 자기 class 내의 메소드, 필드를 지칭할 때 사용.

  super. : 자기 Parent class의 메소드, 필드를 지칭할 때 사용.

  자식 클래스가 부모 클래스를 overriding하였으나 필요에 의해 부모 클래스의 method나 변수를 호출할 때 super를 사용하면 된다.

- 상속과 생성자

  제대로 계승했다면 부모 클래스에 생성자가 있는 경우, 자식 클래스의 생성자는 반드시 부모 클래스의 생성자를 호출한다.
