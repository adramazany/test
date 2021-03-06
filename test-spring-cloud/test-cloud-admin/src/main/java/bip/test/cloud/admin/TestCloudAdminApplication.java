package bip.test.cloud.admin;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class TestCloudAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestCloudAdminApplication.class, args);
	}

}
