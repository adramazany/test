package bip.test.cloud.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@EnableEurekaClient
//@EnableSwagger2
public class TestCloudZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestCloudZuulApplication.class, args);
	}

/*
	@Bean
	public Docket productsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("product-api")
				.select()
				.apis(RequestHandlerSelectors.basePackage("bip.test.cloud.product"))
				//.apis(RequestHandlerSelectors.any())
				//.paths(PathSelectors.regex(".*"))
				.build();
	}
*/

/*
	@Bean
	public Docket customersApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("customer-api")
				.select()
				*/
/*.apis(RequestHandlerSelectors.basePackage("com.tutorialspoint.swaggerdemo"))*//*

				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.regex("/customers/.*"))
				.build();
	}
*/
}
