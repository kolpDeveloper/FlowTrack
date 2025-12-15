package by.kolp.user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(UserApplicationRunner.class, args);
    }
}
