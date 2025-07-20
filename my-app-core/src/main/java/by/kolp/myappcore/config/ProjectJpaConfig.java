package by.kolp.myappcore.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages =
        "by.kolp.myappcore.repository.interfaces"
)
public class ProjectJpaConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
