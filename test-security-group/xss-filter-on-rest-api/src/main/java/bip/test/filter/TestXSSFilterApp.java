package bip.test.filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestXSSFilterApp {

    public static void main(String[] args) {
        SpringApplication.run(TestXSSFilterApp.class,args);
    }

    public void run(String... strings) throws Exception {
        System.out.println("TestXSSFilterApp.run");


    }


}
