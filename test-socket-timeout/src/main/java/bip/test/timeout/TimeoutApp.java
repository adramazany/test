package bip.test.timeout;

import bip.test.timeout.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import java.util.List;
import java.util.Map;

/**
 * Created by ramezani on 8/9/2018.
 */
@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
public class TimeoutApp implements CommandLineRunner{


    @Autowired
    TestService testService;


    public static void main(String[] args) {
        SpringApplication.run(TimeoutApp.class,args);
    }

    public void run(String... strings) throws Exception {
        System.out.println("TimeoutApp.run");

        testService.update("update topics set f_section=0 where topics_id= 1736",null,null);
/*
        List<Map<String, Object>> result =testService.queryForList("select * from topics ",null);
        for (Map<String, Object> row:result) {
            for (String key:row.keySet()) {
                System.out.print(key+"="+row.get(key)+",");
            }
            System.out.println();

        }
*/

        System.out.println("TimeoutApp.run:finished.");


    }


}
