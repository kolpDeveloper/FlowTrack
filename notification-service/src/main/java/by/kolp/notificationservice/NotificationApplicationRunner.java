package by.kolp.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NotificationApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplicationRunner.class, args);
    }
}
