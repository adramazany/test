package bip.test.ws.swagger.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by ramezani on 5/6/2020.
 */
@ComponentScan(basePackages = "bip.test.ws.swagger")
@EnableSwagger2
public class WSSwaggerConfiguration {
    private static Logger logger= LoggerFactory.getLogger(WSSwaggerConfiguration.class);

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
