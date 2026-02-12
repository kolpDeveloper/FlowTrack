package by.kolp.financeservice.filter;

import by.kolp.financeservice.dto.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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
public class GatewayHeaderAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String userId = request.getHeader("X-USER-ID");
        String username = request.getHeader("X-USER-NAME");
        String role = request.getHeader("X-USER-AUTHORITIES");

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            List<SimpleGrantedAuthority> authorities = (role != null && !role.isBlank()
                    ? Arrays.stream(role.split(","))
                    .map(SimpleGrantedAuthority::new)
                    .toList()
                    : List.of());


            UUID userUUID;
            try {
                userUUID = UUID.fromString(userId);
            } catch (IllegalArgumentException e) {
                log.warn("Illegal UUID passed to X-USER-ID: {}", userId);
                response.setStatus(401);
                return;
            }

            UserPrincipal principal = new UserPrincipal(userUUID, username, authorities);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal, null, authorities);

            auth.setDetails(principal);
            SecurityContextHolder.getContext().setAuthentication(auth);
            log.debug("User {} authenticated", username);

        }
        filterChain.doFilter(request, response);
    }
}
