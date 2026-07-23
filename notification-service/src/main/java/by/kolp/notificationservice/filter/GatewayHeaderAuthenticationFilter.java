package by.kolp.notificationservice.filter;

import by.kolp.commonexceptions.util.JWTUtils;
import by.kolp.notificationservice.model.dto.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
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
public class GatewayHeaderAuthenticationFilter extends OncePerRequestFilter implements Ordered {

    private final JWTUtils jwtUtils;

    @Order(Ordered.HIGHEST_PRECEDENCE + 10)
    public GatewayHeaderAuthenticationFilter gatewayHeaderAuthenticationFilter() {
        return new GatewayHeaderAuthenticationFilter(jwtUtils);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String userIdStr = request.getHeader("X-USER-ID");
        String username = request.getHeader("X-USER-NAME");
        String authoritiesStr = request.getHeader("X-USER-AUTHORITIES");

        if(userIdStr != null  && !userIdStr.isBlank()){
            try{
                UUID userId = UUID.fromString(userIdStr);
                List<SimpleGrantedAuthority> authority = !authoritiesStr.isBlank()
                        ? Arrays.stream(authoritiesStr
                        .split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList() : List.of();

                UserPrincipal userPrincipal = new UserPrincipal(userId, username, authority);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, authority);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            } catch (IllegalArgumentException e) {
                log.warn("Invalid userId in request {}", request.getRequestURI());
            }


        }
            filterChain.doFilter(request, response);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
