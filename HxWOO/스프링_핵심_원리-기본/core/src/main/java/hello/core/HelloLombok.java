package hello.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter // 게터
@Setter // 세터
@ToString // 투스트링 다 만들어줌
public class HelloLombok {

    private String name;
    private int age;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();
        helloLombok.setName("name");
        System.out.println("helloLombok = " + helloLombok);
    }

}
