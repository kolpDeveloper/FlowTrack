package by.kolp.myappweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableCaching
@SpringBootApplication(scanBasePackages = {
        "by.kolp.myappweb",
        "by.kolp.myappcore",
        "by.kolp.myappproducer"
})
@EntityScan(basePackages = {"by.kolp.myappcore.model.entity", "by.kolp.myappproducer.model.entity"})
@ComponentScan(basePackages = {
        "by.kolp.myappweb.controller",
        "by.kolp.myappweb.security",
        "by.kolp.myappcore.mapper",
        "by.kolp.myappcore.service",
        "by.kolp.myappcore.repository",
        "by.kolp.myappcore.model.entity",
        "by.kolp.myappproducer.producer",
        "by.kolp.myappproducer.consumer",
        "by.kolp.myappproducer.service",
        "by.kolp.myappproducer.config",
        "by.kolp.myappcore.jwt"
})
@EnableJpaRepositories(basePackages = {"by.kolp.myappcore.repository", "by.kolp.myappproducer.model.repository"} )
public class MyApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(MyApplicationRunner.class, args);
    }
}
