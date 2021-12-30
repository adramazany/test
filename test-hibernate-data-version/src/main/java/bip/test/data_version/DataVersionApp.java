package bip.test.data_version;

import bip.test.data_version.model.Test;
import bip.test.data_version.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

/**
 * Created by ramezani on 8/7/2018.
 */
@SpringBootApplication
public class DataVersionApp implements CommandLineRunner{
    Logger logger= LoggerFactory.getLogger(DataVersionApp.class);

    @Autowired
    TestService testService;

    public static void main(String[] args) {
        SpringApplication.run(DataVersionApp.class,args);
    }

    public void run(String... args) throws Exception {
        logger.info("run: starting......................");

        //testService.save(new Test("AAAAAA","111"));
        Test t = testService.findById(2);
        testService.save(new Test("AAAAAA","111"));

        logger.info("run: end.");
    }
}
