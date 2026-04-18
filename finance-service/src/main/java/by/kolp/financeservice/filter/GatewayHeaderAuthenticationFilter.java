package by.kolp.financeservice.filter;

import by.kolp.commonexceptions.util.JWTUtils;
import by.kolp.financeservice.dto.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
@ComponentScan("by.kolp.commonexceptions.util")
public class GatewayHeaderAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            if(jwtUtils.isTokenValid(token)) {
                UUID user_id = UUID.fromString(jwtUtils.extractUserId(token));
                String username = jwtUtils.extractUsername(token);
                String role = jwtUtils.extractRole(token);

                List<SimpleGrantedAuthority> authorities = (role != null && !role.isBlank()
                        ? Arrays.stream(role.split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList()
                        : List.of());

                UserPrincipal principal = new UserPrincipal(user_id, username, authorities);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                log.warn("Invalid JWT token");
            }
        }
        filterChain.doFilter(request, response);
    }
}
