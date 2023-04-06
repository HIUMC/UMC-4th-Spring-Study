package hello.hello_spring.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name1") String name){
        Hello hello=new Hello();
        hello.setName(name);
        hello.setAge(25);
        return hello;
    }
    static class Hello{
        private String name;
        private int age;
        public String getName(){
            return name;
        }
        public void setName(String name){
            this.name=name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

}
