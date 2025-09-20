package by.kolp.myappweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication(scanBasePackages = {
        "by.kolp.myappweb",
        "by.kolp.myappservice",
        "by.kolp.myappcore",
        "by.kolp.myappsecurity"
}, exclude = {SecurityAutoConfiguration .class})
@EntityScan("by.kolp.myappcore.model.entity")
@EnableJpaRepositories("by.kolp.myappcore.repository.interfaces")
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
    @Bean
    @Profile("test")
    @Primary
    public PasswordEncoder testPasswordencoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
