package by.kolp.commonexceptions.config;

import by.kolp.commonexceptions.filter.CommonJWTUtil;
import by.kolp.commonexceptions.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@ComponentScan("by.kolp.commonexceptions.filter")
public class LibConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(CommonJWTUtil jwtUtil, UserDetailsService userDetailsService) {
        return new JwtAuthenticationFilter(jwtUtil, userDetailsService);
    }
}
