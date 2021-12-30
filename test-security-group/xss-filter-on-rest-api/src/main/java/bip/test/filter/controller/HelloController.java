package bip.test.filter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;

@RestController()
public class HelloController {

    @GetMapping("/api/hello")
    public String hello(String name){
        return "Hello   "+name;
    }
}
