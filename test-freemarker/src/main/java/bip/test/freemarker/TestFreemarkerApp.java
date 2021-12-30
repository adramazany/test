package bip.test.freemarker;

import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;

/**
 * Created by ramezani on 8/8/2018.
 */
@SpringBootApplication
//@Configuration
public class TestFreemarkerApp implements CommandLineRunner{

    @Autowired
    freemarker.template.Configuration freemarkerConfig;

    public static void main(String[] args) {
        SpringApplication.run(TestFreemarkerApp.class,args);
    }

    public void run(String... args) throws Exception {
        System.out.println("TestFreemarkerApp.run ....");

        Template template = freemarkerConfig.getTemplate("hello.ftl");
        HashMap<String,String> model = new HashMap<String, String>();
        model.put("name","ali mohammad");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template,model);
        System.out.println("freemarker html="+html);

        System.out.println("TestFreemarkerApp.run finished.");
    }

    @Bean
    FreeMarkerConfigurationFactoryBean freeMarkerConfiguration(){
        FreeMarkerConfigurationFactoryBean result = new FreeMarkerConfigurationFactoryBean();
        result.setTemplateLoaderPath("/");
        return result;
    }
}
