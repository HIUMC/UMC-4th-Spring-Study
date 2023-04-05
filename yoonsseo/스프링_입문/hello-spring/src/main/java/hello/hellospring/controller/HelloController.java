package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello")
    //웹 어플리케이션에서 /hello 라고 들어오면 해당 메소드 호출
    public String hello(Model model) {
        model.addAttribute("data", "hello spring!");
        return "hello";
        //resources/templates/hello.html과 연결되어 렌더링
    }

    @GetMapping("hello-mvc")
    public String helloMVC(@RequestParam("name") String name, Model model) {
        //외부, 웹에서 파라미터 받겠다 -> @RequestParam
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    //html말고 http에서의 헤더와 바디
    //이 바디에 이 데이터를 직접 넣어주겠다는 의미
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        //static 클래스는 클래스 안에서 또 클래스 생성 가능
        //HelloController.Hello
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
