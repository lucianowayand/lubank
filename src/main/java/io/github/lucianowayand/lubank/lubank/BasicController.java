package io.github.lucianowayand.lubank.lubank;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    @RequestMapping("/")
    public String getHello() {
        return "Hello World";
    }
}
