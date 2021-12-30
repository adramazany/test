package bip.test.jdbctemplate;

import bip.test.jdbctemplate.model.Test;
import bip.test.jdbctemplate.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ramezani on 8/9/2018.
 */
@SpringBootApplication
public class JdbcTemplateApp implements CommandLineRunner{


    @Autowired
    TestService testService;


    public static void main(String[] args) {
        SpringApplication.run(JdbcTemplateApp.class,args);
    }

    public void run(String... strings) throws Exception {
        System.out.println("JdbcTemplateApp.run");

/*
        int id=testService.insert(new Test(null,"123","ABC"));
        System.out.println("insert id="+id);
        List<Test> tests = testService.findAll();
        for (Test t:tests) {
            System.out.println(t.getId()+","+t.getName()+","+t.getCode());
        }
*/
//        int id = testService.update("insert into Test (id,code,name)values(hibernate_sequence.nextval,'100','AAAA')",null,"id");
/*
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("code","101");
        map.put("name","BBBB");
        int id = testService.update("insert into Test (id,code,name)values(hibernate_sequence.nextval,:code,:name)",map,"id");
*/
/*
        Test test = new Test(null,"102","CCCC");
        int id = testService.update("insert into Test (id,code,name)values(hibernate_sequence.nextval,:code,:name)",test,"id");
*/
//        int id = testService.update("insert into Test (id,code,name)values(hibernate_sequence.nextval,'104','DDDD')",null,"id");
        Integer id = testService.update("delete from Test where id=61",null,null);
        System.out.println("id="+id);
        System.out.println("JdbcTemplateApp.run:finished.");
        JdbcTemplate t;
        //t.update()


    }


}
