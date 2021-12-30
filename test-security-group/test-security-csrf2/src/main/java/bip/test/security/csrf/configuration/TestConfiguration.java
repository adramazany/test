package bip.test.security.csrf.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by ramezani on 7/20/2020.
 */

@Configuration
@ImportResource({"classpath*:applicationContext.xml"})
@EnableWebSecurity
public class TestConfiguration extends
        WebSecurityConfigurerAdapter{

/*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.csrf().disable();
        http.csrf().configure(http);
        System.out.println("TestConfiguration.configure.csrf.....................................");
    }
*/
}
