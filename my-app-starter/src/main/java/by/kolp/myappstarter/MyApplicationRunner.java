package by.kolp.myappstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {
        "by.kolp.myappstarter",
        "by.kolp.myappcore",
        "by.kolp.myappsecurity",
        "by.kolp.myappservice",
        "by.kolp.myappweb",
        "by.kolp.myappproducer",
        "by.kolp.client",
        "by.kolp.myappdataapi",
        "by.kolp.client",
        "by.kolp.myappserviceimpl"
        })
@EntityScan(basePackages = "by.kolp.myappcore.model.entity")
@EnableJpaRepositories(basePackages = "by.kolp.myappcore.repository.interfaces")
@SpringBootApplication
public class MyApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(MyApplicationRunner.class);

    }


}
