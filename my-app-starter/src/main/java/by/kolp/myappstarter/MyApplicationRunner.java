package by.kolp.myappstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
@ComponentScan(basePackages = {
        "by.kolp.myappstarter",
        "by.kolp.myappcore",
        "by.kolp.myappsecurity",
        "by.kolp.myappservice",
        "by.kolp.myappweb"
})
@EntityScan(basePackages = "by.kolp.myappcore.model.entity")
@SpringBootApplication

public class MyApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(MyApplicationRunner.class);

    }


}
