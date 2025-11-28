package by.kolp.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplicationRunner.class, args);
    }
}
