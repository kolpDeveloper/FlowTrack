package by.kolp.myappweb;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*
@ComponentScan(basePackages = {
        "by.kolp.myappcore",
        "by.kolp.myappsecurity",
        "by.kolp.myappservice",
        "by.kolp.myappweb"
})
*/

@SpringBootApplication(scanBasePackages = {
        "by.kolp.myappweb",
        "by.kolp.myappservice",
        "by.kolp.myappcore",
        "by.kolp.myappsecurity"
})
@EntityScan("by.kolp.myappcore.model.entity")
@EnableJpaRepositories("by.kolp.myappcore.repository.interfaces")
public class TestApplication {
    public static void main(String[] args) {

    }
}
