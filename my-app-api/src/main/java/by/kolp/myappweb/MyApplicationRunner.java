package by.kolp.myappweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "by.kolp.myappweb",
        "by.kolp.myappcore",
        "by.kolp.myappproducer"
})
@EntityScan("by.kolp.myappcore.model.entity")
@EnableJpaRepositories("by.kolp.myappcore.repository")
public class MyApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(MyApplicationRunner.class, args);
    }
}
