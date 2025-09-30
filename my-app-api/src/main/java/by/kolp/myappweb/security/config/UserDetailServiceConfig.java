package by.kolp.myappweb.security.config;

import by.kolp.myappcore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration

public class UserDetailServiceConfig {

    private final UserService userService;

    public UserDetailServiceConfig(UserService userService) {
        this.userService = userService;
    }

    public UserDetailsService getUserDetailsService() {
        return username -> userService.findByUsername(username)
                .map(UsersDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
