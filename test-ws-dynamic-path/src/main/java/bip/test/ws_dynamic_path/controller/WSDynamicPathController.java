package bip.test.ws_dynamic_path.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ramezani on 3/15/2020.
 */
@RestController()
@RequestMapping("${testpath}")
public class WSDynamicPathController {

    @Value("${testpath}")
    String testpath;

    @GetMapping("/hello")
    public String hello(){
        System.out.println("testpath="+testpath);
        return "Hello World!";
    }
}
