package by.kolp.myappweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "by.kolp.myappweb",
        "by.kolp.myappcore",
        "by.kolp.myappproducer"
})
@EntityScan("by.kolp.myappcore.model.entity")
@ComponentScan(basePackages = {
        "by.kolp.myappweb.controller",
        "by.kolp.myappweb.security",
        "by.kolp.myappcore.mapper",
        "by.kolp.myappcore.service",
        "by.kolp.myappcore.repository",
        "by.kolp.myappcore.model.entity"
})
@EnableJpaRepositories("by.kolp.myappcore.repository")
public class MyApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(MyApplicationRunner.class, args);
    }
}
