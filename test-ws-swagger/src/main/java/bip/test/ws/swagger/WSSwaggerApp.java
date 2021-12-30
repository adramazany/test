package bip.test.ws.swagger;

import bip.test.ws.swagger.configuration.WSSwaggerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

/**
 * Created by ramezani on 5/6/2020.
 */
@SpringBootApplication
@EnableSwagger2
public class WSSwaggerApp {
    private static Logger logger= LoggerFactory.getLogger(WSSwaggerApp.class);

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(WSSwaggerApp.class, args);
    }

    //@Override
    public void run(String... args) throws Exception {
        System.out.println("***********************************************");
        System.out.println("********* Application is started *********");
        System.out.println("spring active profile: " + Arrays.asList(applicationContext.getEnvironment().getActiveProfiles()));
        System.out.println("***********************************************");
    }


    @Bean
    public Docket testApi() {
        logger.info("test api docket ");

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("test-api").select().apis(RequestHandlerSelectors.any())
                //.paths(PathSelectors.regex("/api/sam/configmgmt/v1/common/.*"))
                .paths(PathSelectors.regex("/api/.*"))
                .build();
    }

}
