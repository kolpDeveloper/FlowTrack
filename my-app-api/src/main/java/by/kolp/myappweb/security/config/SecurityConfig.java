package by.kolp.myappweb.security.config;

import by.kolp.myappweb.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(@Lazy JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-resources/**").permitAll()
                        .requestMatchers("/webjars/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/auth/registration").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()

                        .requestMatchers(HttpMethod.DELETE, "/api/user/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/user").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/user").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/user/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/value").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/value/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/value/sum").permitAll()

                        .requestMatchers(HttpMethod.GET, "/error").permitAll()
                        .requestMatchers(HttpMethod.POST, "/send").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/sum/all").permitAll()
                        .requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/admin/send").permitAll()
                        .requestMatchers(HttpMethod.POST, "/admin/send/bulk").permitAll()
                        .requestMatchers(HttpMethod.POST, "/admin/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/message/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/admin/send/bulk/html").permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}