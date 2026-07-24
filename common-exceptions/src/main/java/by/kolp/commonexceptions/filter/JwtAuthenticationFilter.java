package by.kolp.commonexceptions.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CommonJWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtil, userDetailsService);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();
        log.debug("uri: {}", uri);

        final String authorizationHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authorizationHeader.substring(7);
        try {
            username = jwtUtil.validateToken(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (userDetailsService != null) {
                    UserDetails UDetails = userDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(UDetails, null, UDetails.getAuthorities());

                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    log.debug("User {} successfully authenticated", username);
                } else {
                    log.debug("User {} validated by token, but no UserDetailsService provided for full authentication", username);
                }
            }
        } catch (Exception e) {
            log.error("Some problems with JWT: ", e);
        }
        filterChain.doFilter(request, response);
    }
}
