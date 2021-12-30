package bip.test.data_version.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ramezani on 8/7/2018.
 */
@Configuration
public class HikariConfiguration {

    @Bean
    HikariConfig hikariConfig(){
        return new HikariConfig("/db.properties");
    }
    @Bean
    HikariDataSource dataSource(){
        return new HikariDataSource(hikariConfig());
    }




}
