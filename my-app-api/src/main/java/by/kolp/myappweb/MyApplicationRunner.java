package by.kolp.myappweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableCaching
@SpringBootApplication(scanBasePackages = {"by.kolp"})
@EntityScan(basePackages = {"by.kolp.myappcore.model.entity", "by.kolp.myappproducer.model.entity"})
@EnableJpaRepositories(basePackages = {"by.kolp.myappcore.repository", "by.kolp.myappproducer.model.repository"} )
public class MyApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(MyApplicationRunner.class, args);
    }
}
