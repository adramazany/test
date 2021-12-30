package bip.test.filter.configuration;

import bip.test.filter.XSSFilterJersey1;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;

@Configuration
public class XSSFilterConfiguration {

//    @Bean
//    public FilterRegistrationBean someFilterRegistration() {
//
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(someFilter());
//        registration.addUrlPatterns("/url/*");
//        registration.addInitParameter("paramName", "paramValue");
//        registration.setName("someFilter");
//        registration.setOrder(1);
//        return registration;
//    }
//
//    public Filter someFilter() {
//        return new XSSFilterJersey1();
//    }

//    @Bean
//    public  ServletRegistrationBean<ContainerRequestFilter> countryServlet() {
//        ServletRegistrationBean<ContainerRequestFilter> servRegBean = new ServletRegistrationBean<>();
//        servRegBean.addInitParameter("com.sun.jersey.spi.container.ContainerRequestFilters","bip.test.filter.XSSFilterJersey1");
//        servRegBean.setServlet(new XSSFilterJersey1());
//        servRegBean.addUrlMappings("/api/*");
//        servRegBean.setLoadOnStartup(1);
//        return servRegBean;
//    }
}
