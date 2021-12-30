package bip.test.springboot.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

@SpringBootApplication
public class TestSecurityCSRFApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestSecurityCSRFApplication.class, args);
	}


	@Bean
	public FilterRegistrationBean<UrlRewriteFilter> urlRewriteFilter(){
		FilterRegistrationBean<UrlRewriteFilter> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(new UrlRewriteFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(1);

		return registrationBean;
	}
}
