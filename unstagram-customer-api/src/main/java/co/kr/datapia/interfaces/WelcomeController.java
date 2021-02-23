package co.kr.datapia.interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    // 처음 localhost의 화면을 띄워준다.
    @GetMapping("/")
    public String hello() {
        return "Hello World~";
    }
}
