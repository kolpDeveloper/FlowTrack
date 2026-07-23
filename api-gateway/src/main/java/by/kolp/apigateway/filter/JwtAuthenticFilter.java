package by.kolp.apigateway.filter;

import by.kolp.commonexceptions.util.JWTUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticFilter extends AbstractGatewayFilterFactory<JwtAuthenticFilter.Config> {

    private final JWTUtils jwtUtils;

    public JwtAuthenticFilter(JWTUtils jwtUtils) {
        super(Config.class);
        this.jwtUtils = jwtUtils;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return stopWithStatus(exchange, HttpStatus.UNAUTHORIZED);
            }

            String token = authHeader.substring(7);

            try {
                if (!jwtUtils.isTokenValid(token)) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }


                String userId = jwtUtils.extractUserId(token);
                String role = jwtUtils.extractRole(token);
                String username = jwtUtils.extractUsername(token);


                ServerHttpRequest request = exchange.getRequest().mutate()
                        .headers(del -> {
                            del.remove("X-USER-ID");
                            del.remove("X-USER-NAME");
                            del.remove("X-USER-AUTHORITIES");
                                })
                        .header("X-USER-ID", userId)
                        .header("X-USER-AUTHORITIES", role)
                        .header("X-USER-NAME", username)
                        .build();

                return chain.filter(exchange.mutate().request(request).build());

            } catch (Exception e) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        });
    }

    private Mono<Void> stopWithStatus(ServerWebExchange exchange, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        return exchange.getResponse().setComplete();
    }

    public static class Config {}
}

