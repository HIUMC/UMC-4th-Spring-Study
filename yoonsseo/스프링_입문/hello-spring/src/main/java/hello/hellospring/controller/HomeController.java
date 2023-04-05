package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    //이 슬래시는 localhost 8080으로 들어오면 home(home.html)이 호출됨
    public String home() {
        return "home";
    }
}
